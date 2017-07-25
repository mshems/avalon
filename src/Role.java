import java.io.Serializable;

/**
 * Created by Matt on 7/21/2017.
 */
public class Role implements Serializable{
    private static final long serialVersionUID = avalon.VERSION;
    private String roleName;
    private double gamesWon;
    private double gamesPlayed;
    private boolean good;
    private PlayerRole role;

    public enum PlayerRole {MERLIN, PERCIVAL, KNIGHT, ASSASSIN, MORGANA, MORDRED, OBERON}

    public Role(String roleName){
        this.roleName = roleName;
        this.role = parsePlayerRole(roleName);
        gamesWon = 0;
        gamesPlayed = 0;
    }

    public boolean isGood(){
        switch(this.role){
            case MERLIN:
            case PERCIVAL:
            case KNIGHT:
                return true;
            default:
                return false;
        }
        /*switch (this.roleName.toLowerCase()){
            case "merlin":
            case "percival":
            case "knight":
                return true;
            default:
                return false;
        }*/
    }

    public void addGame(){
        this.gamesPlayed++;
    }

    public void winGame(){
        this.gamesWon++;
    }

    public double winRate(){
        try{
            if(gamesPlayed == 0){
                return 0;
            }
            return (gamesWon/gamesPlayed)*100.0;
        } catch (ArithmeticException e){
            return 0;
        }
    }

    public double getGamesWon(){
        return gamesWon;
    }

    public void setGamesWon(double gamesWon){
        this.gamesWon = gamesWon;
    }

    public double getGamesPlayed(){
        return gamesPlayed;
    }

    public void setGamesPlayed(double gamesPlayed){
        this.gamesPlayed = gamesPlayed;
    }

    public static PlayerRole parsePlayerRole(String roleName){
        switch (roleName.toLowerCase().trim()){
            case "merlin":
            case "m":
                return PlayerRole.MERLIN;
            case "percival":
            case "p":
                return PlayerRole.PERCIVAL;
            case "knight":
            case "k":
                return PlayerRole.KNIGHT;
            case "assassin":
            case "a":
                return PlayerRole.ASSASSIN;
            case "morgana":
            case "mg":
                return PlayerRole.MORGANA;
            case "mordred":
            case "md":
                return PlayerRole.MORDRED;
            case "oberon":
            case "o":
                return PlayerRole.OBERON;
            default:
                return null;
        }
    }

    public String getXMLName(){
        return this.roleName.toLowerCase().trim();
    }

    public String toString(){
        return String.format("%sWinrate: %03.0f%%     Total Games: %02.0f     Games Won: %02.0f", roleName, winRate(), gamesPlayed, gamesWon);
    }
}
