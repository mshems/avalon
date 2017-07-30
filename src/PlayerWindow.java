import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matt on 7/29/2017.
 */
public class PlayerWindow extends JPanel{
    private Player player;
    private JPanel parentWindow;
    private JScrollPane contentPane;
    private JPanel statsPane;

    public PlayerWindow(String playerKey, JPanel parentWindow){
        this.player = avalon.playerList.get(playerKey);
        this.parentWindow = parentWindow;
        //this.setBackground(new Color(184,207,229));
        this.setPreferredSize(new Dimension(this.parentWindow.getWidth()-10, this.parentWindow.getHeight()-10));
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel(this.player.getPlayerName());
        this.add(label, BorderLayout.NORTH);
        statsPane = new JPanel();
        statsPane.setLayout(new BoxLayout(statsPane, BoxLayout.Y_AXIS));
        JLabel stats = new JLabel(
                convertToMultiline(String.format(
                        "Winrate...............%.2f%%" + "\n" +
                        "Winrate (good)......%.2f%%" + "\n" +
                        "Winrate (bad).......%.2f%%" + "\n" +
                        "Games Won.............%.0f wins" + "\n" +
                        "Games Won (good)....%.0f wins" + "\n" +
                        "Games Won (bad).....%.0f wins" + "\n" +
                        "Total games...........%.0f games" + "\n" +
                        "Total games (good)..%.0f games" + "\n" +
                        "Total games (bad)...%.0f games" + "\n",
                        player.winrate(), player.winrateGood(), player.winrateBad(),
                        player.gamesWon, player.gamesWonGood, player.gamesWonBad,
                        player.gamesTotal, player.timesGood, player.timesBad
                )));
        String roleString="";
        for(Role r : player.rolesTable.values()){
            roleString+=" | "+r.toString()+"\n\n";
        }
        JLabel roles = new JLabel(convertToMultiline(roleString));

        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPane.add(stats);
        statsPane.add(roles);
        //statsPane.setBackground(Color.WHITE);
        contentPane = new JScrollPane(statsPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(contentPane, BorderLayout.CENTER);
    }

    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
