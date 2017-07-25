import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Created by Matt on 7/21/2017.
 */
public class avalon{
    static final long VERSION = 103L;
    private static final long serialVersionUID = VERSION;

    static double totalGames;
    static double totalGoodWins;
    static double totalBadWins;
    static double totalAssassinations;

    static LinkedHashMap<String, Player> playerList;
    private static Scanner scanner;

    public static void main(String[] args){
        scanner = new Scanner(System.in);

        AvalonXMLReader.checkDirs();
        AvalonXMLReader.readPlayerStats();
        AvalonXMLReader.readGameStats();

        if (args.length > 0){
            switch (args[0]){
                case "new":
                    enterNewGameStats();
                    break;
                case "stats":
                    printStats();
                    break;
                case "player":
                    String playerName = "";
                    if (args.length >= 2){
                        for (int i = 1; i < args.length; i++){
                            playerName += args[i] + " ";
                        }
                        playerName = playerName.trim();
                    } else {
                        System.out.print("Enter name of player: ");
                        playerName = scanner.nextLine().trim();
                    }
                    Player player = playerList.get(playerName.toLowerCase());
                    if (player == null){
                        System.out.println("No player by that name");
                    } else {
                        System.out.println(player);
                    }
                    break;
                case "players":
                    printPlayers();
                    break;
                default:
                    break;
            }
        }

        AvalonXMLWriter.writePlayerStats();
        AvalonXMLWriter.writeGameStats();

        System.out.print("Press 'ENTER' to exit...");
        scanner.nextLine();
    }

    private static void enterNewGameStats(){
        int players = getValidInt("Enter number of players: ");
        ArrayList<PlayerRolePair> playerRolePairs = new ArrayList<>();

        for (int i = 1; i <= players; i++){
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

    private static double winrateGood(){
        try{
            if (totalGames == 0){
                return 0;
            }
            return (totalGoodWins / totalGames) * 100.0;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    private static double winrateBad(){
        try{
            if (totalGames == 0){
                return 0;
            }
            return (totalBadWins / totalGames) * 100.0;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    private static double winrateAssassin(){
        try{
            if (totalGames == 0){
                return 0;
            }
            return (totalAssassinations / totalGames) * 100.0;
        } catch (ArithmeticException e) {
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
        while (true) {
            System.out.print(message);
            String roleName = scanner.nextLine();
            if (RoleType.parsePlayerRole(roleName) != null){
                return roleName;
            } else {
                System.out.println("Not a valid role name");
            }
        }
    }

    private static void printStats(){
        String newLine = System.lineSeparator();
        System.out.println(String.format(" [ GENERAL GAME STATS ]" + newLine + " | Total Games Played......%.0f" + newLine + " | Total Good Team Wins....%.0f  (%.2f%%)" + newLine + " | Total Bad Team Wins.....%.0f  (%.2f%%)" + newLine + " | Total Assassinations....%.0f  (%.2f%%)" + newLine + ""

                , totalGames, totalGoodWins, winrateGood(), totalBadWins, winrateBad(), totalAssassinations, winrateAssassin()));
    }

    private static void printPlayers(){
        for (Player player : playerList.values()){
            System.out.println(player);
        }
    }
}