/**
 * Created by Matt on 7/25/2017.
 */
public enum RoleType{

    MERLIN, PERCIVAL, KNIGHT, ASSASSIN, MORGANA, MORDRED, OBERON;

    public static RoleType parsePlayerRole(String roleName){
        switch (roleName.toLowerCase().trim()){
            case "merlin":
            case "m":
                return RoleType.MERLIN;
            case "percival":
            case "p":
                return RoleType.PERCIVAL;
            case "knight":
            case "k":
                return RoleType.KNIGHT;
            case "assassin":
            case "a":
                return RoleType.ASSASSIN;
            case "morgana":
            case "mg":
                return RoleType.MORGANA;
            case "mordred":
            case "md":
                return RoleType.MORDRED;
            case "oberon":
            case "o":
                return RoleType.OBERON;
            default:
                return null;
        }
    }

}

