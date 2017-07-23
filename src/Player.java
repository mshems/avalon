import java.util.HashMap;

/**
 * Created by Matt on 7/21/2017.
 */
public class Player{
    private String playerName;
    private double gamesWon;
    private double gamesTotal;
    private double timesGood;
    private double timesBad;
    private HashMap<String, Role> rolesTable;

    public Player(String playerName){
        this.playerName = playerName;
        gamesWon = gamesTotal = timesGood = timesBad = 0;
        rolesTable = new HashMap<>();
        rolesTable.put("merlin",    new Role("Merlin"));
        rolesTable.put("percival",  new Role("Percival"));
        rolesTable.put("knight",    new Role("Knight"));
        rolesTable.put("assassin",  new Role("Assassin"));
        rolesTable.put("morgana",   new Role("morgana"));
        rolesTable.put("mordred",   new Role("Mordred"));
        rolesTable.put("oberon",    new Role("Oberon"));
    }

    public double winrate(){
        try{
            return gamesWon/gamesTotal;
        } catch (ArithmeticException e){
            return 0;
        }
    }
}
