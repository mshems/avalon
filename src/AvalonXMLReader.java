import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

/**
 * Created by Matt on 7/25/2017.
 */
public class AvalonXMLReader{

    static void checkDirs(){
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


    static void readGameStats(){
        File gameDatafile = new File("./data/gamedata.avalon");
        if(gameDatafile.exists()){
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try{
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document gamedata = documentBuilder.parse(gameDatafile);
                avalon.totalGames = Double.parseDouble(gamedata.getElementsByTagName("totalGames").item(0).getTextContent());
                avalon.totalGoodWins = Double.parseDouble(gamedata.getElementsByTagName("totalGoodWins").item(0).getTextContent());
                avalon.totalBadWins = Double.parseDouble(gamedata.getElementsByTagName("totalBadWins").item(0).getTextContent());
                avalon.totalAssassinations = Double.parseDouble(gamedata.getElementsByTagName("totalAssassinations").item(0).getTextContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void readPlayerStats(){
        avalon.playerList = new LinkedHashMap<>();

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
                    avalon.playerList.put(playerName.toLowerCase(), player);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
