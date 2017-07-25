/**
 * Created by Matt on 7/25/2017.
 */
public enum GameResult{

    GOOD_WIN, BAD_WIN, ASSASSIN_WIN;

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
}
