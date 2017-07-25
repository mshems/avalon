import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Matt on 7/21/2017.
 */
public class Player implements Serializable{
    private static final long serialVersionUID = avalon.VERSION;
    private String playerName;
    private double gamesWon;
    private double gamesWonGood;
    private double gamesWonBad;
    private double gamesTotal;
    private double timesGood;
    private double timesBad;
    private LinkedHashMap<String, Role> rolesTable;

    public Player(String playerName){
        this.playerName = playerName;
        this.gamesWon = 0;
        this.gamesWonGood = 0;
        this.gamesWonBad = 0;
        this.gamesTotal = 0;
        this.timesGood = 0;
        this.timesBad = 0;

        this.rolesTable = new LinkedHashMap<>();
        rolesTable.put("merlin",    new Role("Merlin",this));
        rolesTable.put("percival",  new Role("Percival",this));
        rolesTable.put("knight",    new Role("Knight",this));
        rolesTable.put("assassin",  new Role("Assassin",this));
        rolesTable.put("morgana",   new Role("Morgana",this));
        rolesTable.put("mordred",   new Role("Mordred",this));
        rolesTable.put("oberon",    new Role("Oberon",this));
    }

    private double winrate(){
        try{
            if(gamesTotal == 0){
                return 0;
            }
            return (this.gamesWon/this.gamesTotal)*100.0;
        } catch (ArithmeticException e){
            return 0;
        }
    }

    private double winrateGood(){
        try{
            if(gamesTotal == 0){
                return 0;
            }
            return (this.gamesWonGood/this.timesGood)*100.0;

        } catch (ArithmeticException e){
            return 0;
        }
    }

    private double winrateBad(){
        try{
            if(gamesTotal == 0){
                return 0;
            }
            return (this.gamesWonBad/this.timesBad)*100.0;
        } catch (ArithmeticException e){
            return 0;
        }
    }

    private void winGame(Role playerRole){
        playerRole.winGame();
        this.gamesWon+=1;
        if(playerRole.isGood()){
            gamesWonGood++;
        } else {
            gamesWonBad++;
        }
    }

    public void addGame(Role playerRole, GameResult gameResult){
        this.gamesTotal+=1;
        playerRole.addGame();

        if(playerRole.isGood()){
            this.timesGood+=1;
        } else {
            this.timesBad+=1;
        }

        switch(gameResult){
            case GOOD_WIN:
                if(playerRole.isGood()){
                    winGame(playerRole);
                }
                break;
            case BAD_WIN:
                if(!playerRole.isGood()){
                    winGame(playerRole);
                }
                break;
            case ASSASSIN_WIN:
                if(!playerRole.isGood()){
                    winGame(playerRole);
                }
                break;
            default:
                break;
        }
    }

    public Role getRole(String roleName){
        return this.rolesTable.get(roleName);
    }

    public String getPlayerName(){
        return playerName;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public double getGamesWon(){
        return gamesWon;
    }

    public void setGamesWon(double gamesWon){
        this.gamesWon = gamesWon;
    }

    public double getGamesWonGood(){
        return gamesWonGood;
    }

    public void setGamesWonGood(double gamesWonGood){
        this.gamesWonGood = gamesWonGood;
    }

    public double getGamesWonBad(){
        return gamesWonBad;
    }

    public void setGamesWonBad(double gamesWonBad){
        this.gamesWonBad = gamesWonBad;
    }

    public double getGamesTotal(){
        return gamesTotal;
    }

    public void setGamesTotal(double gamesTotal){
        this.gamesTotal = gamesTotal;
    }

    public double getTimesGood(){
        return timesGood;
    }

    public void setTimesGood(double timesGood){
        this.timesGood = timesGood;
    }

    public double getTimesBad(){
        return timesBad;
    }

    public void setTimesBad(double timesBad){
        this.timesBad = timesBad;
    }

    public HashMap<String, Role> getRolesTable(){
        return rolesTable;
    }

    public String toString(){
        String newLine = System.lineSeparator();
        String s = String.format(
            " " + playerName.toUpperCase()+ "" + newLine +
            " | Winrate...............%.2f%%" + newLine +
            " | - Winrate (good)......%.2f%%" + newLine +
            " | - Winrate (bad).......%.2f%%" + newLine +
            " | Games Won.............%.0f wins" + newLine +
            " | - Games Won (good)....%.0f wins" + newLine +
            " | - Games Won (bad).....%.0f wins" + newLine +
            " | Total games...........%.0f games" + newLine +
            " | - Total games (good)..%.0f games" + newLine +
            " | - Total games (bad)...%.0f games" + newLine +
            "  " + newLine
            , winrate(), winrateGood(), winrateBad(), gamesWon, gamesWonGood, gamesWonBad, gamesTotal, timesGood, timesBad) +
            //" | :"+ newLine;
            "";

        for(Role r : rolesTable.values()){
            s+=" | "+r.toString()+newLine+newLine;
        }

       //s+= "  ------------------------------------------"; //----------------------";

        return s;
    }
}
