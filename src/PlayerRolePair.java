/**
 * Created by Matt on 7/21/2017.
 */
public class PlayerRolePair{
    private Player player;
    private String roleName;

    public PlayerRolePair(Player player, String roleName){
        this.player = player;
        this. roleName = roleName;
    }

    public Player getPlayer(){
        return player;
    }

    public String getRoleName(){
        return roleName;
    }
}
