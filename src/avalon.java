import com.sun.org.apache.xpath.internal.SourceTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Created by Matt on 7/21/2017.
 */
public class avalon{
    public static final long VERSION = 101L;
    private static final long serialVersionUID = VERSION;

    private static double totalGames;
    private static double totalGoodWins;
    private static double totalBadWins;
    private static double totalAssassinations;

    private static LinkedHashMap<String, Player> playerList;
    private static Scanner scanner;

    public static void main(String[] args){
        scanner = new Scanner(System.in);
        checkDirs();
        loadPlayerStats();
        loadGameStats();

        if(args.length > 0){
            switch(args[0]){
                case "new":
                    enterNewGameStats();
                    break;
                case "stats":
                    printStats();
                    break;
                case "player":
                    String playerName="";
                    if(args.length>=2){
                        for(int i=1; i<args.length; i++){
                            playerName +=args[i]+" ";
                        }
                        playerName = playerName.trim();
                    } else {
                        System.out.print("Enter name of player: ");
                        playerName = scanner.nextLine().trim();
                    }
                    Player player = playerList.get(playerName.toLowerCase());
                    if(player == null){
                        System.out.println("No player by that name");
                    } else {
                        System.out.println(player);
                    }
                    break;
                default:
                    break;
            }
        }
        savePlayerStats();
        saveGameStats();

        System.out.print("Press 'ENTER' to exit...");
        scanner.nextLine();
    }

    private static void enterNewGameStats(){
        int players = getValidInt("Enter number of players: ");
        ArrayList<PlayerRolePair> playerRolePairs = new ArrayList<>();

        for(int i=1; i<=players; i++){
            System.out.print("Enter name of player #" + i + ": ");
            String playerName = scanner.nextLine().trim();
            Player player;
            if (playerList.containsKey(playerName.toLowerCase())){
                player = playerList.get(playerName.toLowerCase());
            } else {
                player = new Player(playerName);
                playerList.put(playerName.toLowerCase(), player);
            }
            String roleName = getValidRoleName("Enter role for player #" + i + ": ");
            playerRolePairs.add(new PlayerRolePair(player, roleName.toLowerCase()));
        }
            System.out.print("Enter game result [good/bad/assassin]: ");
            String gameResult = scanner.nextLine();
            Game game = new Game(gameResult, playerRolePairs);
            game.addToStats();

            totalGames++;
            switch (game.getResult()){
                case GOOD_WIN:
                    totalGoodWins++;
                case BAD_WIN:
                    totalBadWins++;
                case ASSASSIN_WIN:
                    totalBadWins++;
                    totalAssassinations++;
                default:
                    break;
            }

            System.out.println("Done.");
    }

    public static double winrateGood(){
        try{
            if(totalGames == 0){
                return 0;
            }
            return (totalGoodWins/totalGames)*100.0;
        } catch (ArithmeticException e){
            return 0;
        }
    }

    public static double winrateBad(){
        try{
            if(totalGames == 0){
                return 0;
            }
            return (totalBadWins/totalGames)*100.0;
        } catch (ArithmeticException e){
            return 0;
        }
    }

    public static double winrateAssassin(){
        try{
            if(totalGames == 0){
                return 0;
            }
            return (totalAssassinations/totalGames)*100.0;
        } catch (ArithmeticException e){
            return 0;
        }
    }



    private static int getValidInt(String message){
        int val;
        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()){
                val = scanner.nextInt();
                scanner.nextLine();
                break;
            } else {
                System.out.println("ERROR: Not an Integer");
                scanner.nextLine();
            }
        }
        return val;
    }

    private static String getValidRoleName(String message){
        while(true){
            System.out.print(message);
            String roleName = scanner.nextLine();
            if(Role.parsePlayerRole(roleName)!=null){
                return roleName;
            } else {
                System.out.println("Not a valid role name");
            }
        }
    }

    private static void printStats(){
        String newLine = System.lineSeparator();
        System.out.println(String.format("Total Games Played:   %.0f" +
                newLine+"Total Good Team Wins:  %.0f    (%.2f%%)" +
                newLine+"Total Bad Team Wins:   %.0f    (%.2f%%)" +
                newLine+"Total Assassinations:  %.0f    (%.2f%%)" + newLine,
                totalGames, totalGoodWins, winrateGood(), totalBadWins, winrateBad(), totalAssassinations, winrateAssassin()));

        for(Player player:playerList.values()){
            System.out.println(player);
        }
    }

    private static void checkDirs(){
        File dataDir = new File("./data");
        if(!dataDir.exists()){
            try{
                Files.createDirectories(Paths.get("./data"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File playerDir = new File("./data/players");
        if(!playerDir.exists()){
            try{
                Files.createDirectories(Paths.get("./data/players"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadGameStats(){
        File gameDatafile = new File("./data/gamedata.avalon");
        if(gameDatafile.exists()){
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try{
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document gamedata = documentBuilder.parse(gameDatafile);
                totalGames = Double.parseDouble(gamedata.getElementsByTagName("totalGames").item(0).getTextContent());
                totalGoodWins = Double.parseDouble(gamedata.getElementsByTagName("totalGoodWins").item(0).getTextContent());
                totalBadWins = Double.parseDouble(gamedata.getElementsByTagName("totalBadWins").item(0).getTextContent());
                totalAssassinations = Double.parseDouble(gamedata.getElementsByTagName("totalAssassinations").item(0).getTextContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveGameStats(){
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document gameDataFile = documentBuilder.newDocument();

            Element root = gameDataFile.createElement("avalon");
            gameDataFile.appendChild(root);

            Element totalGamesElement = gameDataFile.createElement("totalGames");
            totalGamesElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(totalGames))
            );
            root.appendChild(totalGamesElement);

            Element totalGoodWinsElement = gameDataFile.createElement("totalGoodWins");
            totalGoodWinsElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(totalGoodWins))
            );
            root.appendChild(totalGoodWinsElement);

            Element totalBadWinsElement = gameDataFile.createElement("totalBadWins");
            totalBadWinsElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(totalBadWins))
            );
            root.appendChild(totalBadWinsElement);

            Element totalAssassinationsElement = gameDataFile.createElement("totalAssassinations");
            totalAssassinationsElement.appendChild(
                    gameDataFile.createTextNode(Double.toString(totalAssassinations))
            );
            root.appendChild(totalAssassinationsElement);

            try{
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                tr.transform(new DOMSource(gameDataFile), new StreamResult(new FileOutputStream("./data/gamedata.avalon")));
            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadPlayerStats(){
        playerList = new LinkedHashMap<>();

        File playerFilesDir = Paths.get("./data/players").toFile();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        for(File playerDatafile:playerFilesDir.listFiles()){
            if (playerDatafile.toString().endsWith(".player")){
                try{
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document playerdata = documentBuilder.parse(playerDatafile);

                    String playerName = playerdata.getElementsByTagName("playerName").item(0).getTextContent();

                    Player player = new Player(playerName);

                    player.setGamesTotal(Double.parseDouble(playerdata.getElementsByTagName("gamesPlayed").item(0).getTextContent()));
                    player.setGamesWon(Double.parseDouble(playerdata.getElementsByTagName("gamesWon").item(0).getTextContent()));
                    player.setGamesWonGood(Double.parseDouble(playerdata.getElementsByTagName("gamesWonGood").item(0).getTextContent()));
                    player.setGamesWonBad(Double.parseDouble(playerdata.getElementsByTagName("gamesWonBad").item(0).getTextContent()));
                    player.setTimesGood(Double.parseDouble(playerdata.getElementsByTagName("timesGood").item(0).getTextContent()));
                    player.setTimesBad(Double.parseDouble(playerdata.getElementsByTagName("timesBad").item(0).getTextContent()));

                    NodeList nodeList = playerdata.getElementsByTagName("role");
                    for (int i = 0; i < nodeList.getLength(); i++){
                        Node node = nodeList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE){
                            Element element = (Element) node;

                            String roleName = element.getElementsByTagName("roleName").item(0).getTextContent();
                            Role role = player.getRole(roleName);

                            role.setGamesPlayed(Double.parseDouble(element.getElementsByTagName("gamesPlayed").item(0).getTextContent()));
                            role.setGamesWon(Double.parseDouble(element.getElementsByTagName("gamesWon").item(0).getTextContent()));
                        }

                    }
                    playerList.put(playerName.toLowerCase(), player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void savePlayerStats(){
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            for(Player player:playerList.values()){
                Document playerFile = documentBuilder.newDocument();
                Element root = playerFile.createElement("player");
                playerFile.appendChild(root);

                Element playerName = playerFile.createElement("playerName");
                playerName.appendChild(
                        playerFile.createTextNode(player.getPlayerName())
                );
                root.appendChild(playerName);

                Element gamesPlayed = playerFile.createElement("gamesPlayed");
                gamesPlayed.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesTotal()))
                );
                root.appendChild(gamesPlayed);

                Element gamesWon = playerFile.createElement("gamesWon");
                gamesWon.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesWon()))
                );
                root.appendChild(gamesWon);

                Element gamesWonGood = playerFile.createElement("gamesWonGood");
                gamesWonGood.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesWonGood()))
                );
                root.appendChild(gamesWonGood);

                Element gamesWonBad = playerFile.createElement("gamesWonBad");
                gamesWonBad.appendChild(
                        playerFile.createTextNode(Double.toString(player.getGamesWonBad()))
                );
                root.appendChild(gamesWonBad);

                Element timesGood = playerFile.createElement("timesGood");
                timesGood.appendChild(
                        playerFile.createTextNode(Double.toString(player.getTimesGood()))
                );
                root.appendChild(timesGood);

                Element timesBad = playerFile.createElement("timesBad");
                timesBad.appendChild(
                        playerFile.createTextNode(Double.toString(player.getTimesBad()))
                );
                root.appendChild(timesBad);

                for(Role role:player.getRolesTable().values()){
                    Element roleElement = playerFile.createElement("role");

                    Element roleName = playerFile.createElement("roleName");
                    roleName.appendChild(
                            playerFile.createTextNode(role.getXMLName())
                    );
                    roleElement.appendChild(roleName);

                    Element gamesPlayedinRole = playerFile.createElement("gamesPlayed");
                    gamesPlayedinRole.appendChild(
                            playerFile.createTextNode(Double.toString(role.getGamesPlayed()))
                    );
                    roleElement.appendChild(gamesPlayedinRole);

                    Element gamesWoninRole = playerFile.createElement("gamesWon");
                    gamesWoninRole.appendChild(
                            playerFile.createTextNode(Double.toString(role.getGamesWon()))
                    );
                    roleElement.appendChild(gamesWoninRole);

                    root.appendChild(roleElement);
                }
                try {
                    Transformer tr = TransformerFactory.newInstance().newTransformer();
                    tr.setOutputProperty(OutputKeys.INDENT, "yes");
                    tr.setOutputProperty(OutputKeys.METHOD, "xml");
                    tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                    tr.transform(new DOMSource(playerFile),
                            new StreamResult(new FileOutputStream("data/players/"+player.getPlayerName()+".player")));
                } catch (TransformerException te) {
                    System.out.println(te.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
