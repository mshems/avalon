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
    private Player player;
    private RoleType role;

    public Role(String roleName, Player player){
        this.roleName = roleName;
        this.role = RoleType.parsePlayerRole(roleName);
        gamesWon = 0;
        gamesPlayed = 0;
        this.player = player;
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
    }

    private double roleRate(){
        try{
            if(player.getGamesTotal() == 0){
                return 0;
            }
            return (gamesPlayed/player.getGamesTotal())*100.0;
        } catch (ArithmeticException e){
            return 0;
        }
    }

    public void addGame(){
        this.gamesPlayed++;
    }

    public void winGame(){
        this.gamesWon++;
    }

    private double winRate(){
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

    public String getXMLName(){
        return this.roleName.toLowerCase().trim();
    }

    public String toString(){
        String newLine = System.lineSeparator();
        return String.format(
                //"%sWinrate: %03.0f%%     Total Games: %02.0f     Games Won: %02.0f", roleName.toUpperCase(), winRate(), gamesPlayed, gamesWon);
                "%s" + newLine +
                " | - Played in %.0f%% of all games" + newLine +
                " | - Winrate........%.0f%%" + newLine +
                " | - Games won......%.0f wins" + newLine +
                " | - Total games....%.0f games"
                , roleName, roleRate(), winRate(), gamesWon, gamesPlayed
        );
    }
}
