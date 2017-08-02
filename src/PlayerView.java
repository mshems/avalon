import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matt on 7/29/2017.
 */
public class PlayerView extends JPanel{
    private Player player;

    public PlayerView(Player player){
        this.player = player;
        this.setBackground(new Color(184,207,229));
        ///////////////
        if(player!=null){
            JLabel stats = new JLabel(statsToString());
            this.add(stats);
        }
        ///////////////

    }

    private String statsToString(){
        return convertToMultiline(String.format(
                "%s" +"\n"+
                        "Winrate...............%.2f%%" + "\n" +
                        "Winrate (good)......%.2f%%" + "\n" +
                        "Winrate (bad).......%.2f%%" + "\n" +
                        "Games Won.............%.0f wins" + "\n" +
                        "Games Won (good)....%.0f wins" + "\n" +
                        "Games Won (bad).....%.0f wins" + "\n" +
                        "Total games...........%.0f games" + "\n" +
                        "Total games (good)..%.0f games" + "\n" +
                        "Total games (bad)...%.0f games" + "\n",
                player.getPlayerName(),player.winrate(), player.winrateGood(), player.winrateBad(),
                player.gamesWon, player.gamesWonGood, player.gamesWonBad,
                player.gamesTotal, player.timesGood, player.timesBad
        ));
    }
    private static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
