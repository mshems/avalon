import java.util.ArrayList;

/**
 * Created by Matt on 7/21/2017.
 */
public class Game{
    private GameResult gameResult;
    private ArrayList<PlayerRolePair> playerRolePairs;

    public enum GameResult {GOOD_WIN, BAD_WIN, ASSASSIN_WIN}


    public Game(String result, ArrayList<PlayerRolePair> playerRolePairs){
        this.gameResult = parseGameResult(result);
        this.playerRolePairs = playerRolePairs;
    }

    public GameResult getResult(){
        return gameResult;
    }

    public static GameResult parseGameResult(String result){
        switch(result){
            case "g":
            case "good":
            case "pass":
                return GameResult.GOOD_WIN;
            case "b":
            case "bad":
            case "fail":
                return GameResult.BAD_WIN;
            case "a":
            case "assassin":
                return GameResult.ASSASSIN_WIN;
            default:
                return null;
        }
    }

    public void addToStats(){
        for(PlayerRolePair prp : playerRolePairs){
            Player player = prp.getPlayer();
            Role playerRole = player.getRole(prp.getRoleName());
            player.addGame(playerRole, this.gameResult);
        }
    }

}
