import java.util.ArrayList;

/**
 * Created by Matt on 7/21/2017.
 */
public class Game{
    private GameResult gameResult;
    private ArrayList<PlayerRolePair> playerRolePairs;

    public Game(String result, ArrayList<PlayerRolePair> playerRolePairs){
        this.gameResult = GameResult.parseGameResult(result);
        this.playerRolePairs = playerRolePairs;
    }

    public GameResult getResult(){
        return gameResult;
    }

    public void addToStats(){
        for(PlayerRolePair prp : playerRolePairs){
            Player player = prp.getPlayer();
            Role playerRole = player.getRole(prp.getRoleName());
            player.addGame(playerRole, this.gameResult);
        }
    }

}
