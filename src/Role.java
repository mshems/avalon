/**
 * Created by Matt on 7/21/2017.
 */
public class Role{
    private String roleName;
    private double gamesWon;
    private double gamesPlayed;
    private boolean good;

    public Role(String roleName){
        this.roleName = roleName;
        gamesWon = gamesPlayed = 0;
    }

    public boolean isGood(){
        switch (this.roleName){
            case "merlin":
            case "percival":
            case "knight":
                return true;
            default:
                return false;
        }
    }
}
