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

    public static GameResult parseGameResult(String result){
        switch(result){
            case "g":
                return GameResult.GOOD_WIN;
            case "b":
                return GameResult.BAD_WIN;
            case "a":
                return GameResult.ASSASSIN_WIN;
            default:
                return null;
        }
    }

    public void addToStats(){
        for(PlayerRolePair prp : playerRolePairs){
            
        }
    }

}
