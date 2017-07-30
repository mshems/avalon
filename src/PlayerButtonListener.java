import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Matt on 7/29/2017.
 */
public class PlayerButtonListener implements ActionListener{
    private Player player;
    private AvalonWindow parentWindow;
    private String playerKey;

    public PlayerButtonListener(String playerKey, AvalonWindow avalonWindow){
        //this.player = player;
        this.playerKey = playerKey;
        this.parentWindow = avalonWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        parentWindow.centerPanel.removeAll();
        parentWindow.centerPanel.add(new PlayerWindow(playerKey, parentWindow.centerPanel));
        if(parentWindow.activePlayerKey!=null){
            parentWindow.buttonMap.get(parentWindow.activePlayerKey).deselect();
        }
        parentWindow.activePlayerKey = playerKey;
        parentWindow.buttonMap.get(parentWindow.activePlayerKey).select();
        parentWindow.centerPanel.validate();
        parentWindow.centerPanel.repaint();
    }
}
