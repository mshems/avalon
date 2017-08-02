import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Matt on 7/29/2017.
 */
public class PlayerButtonListener implements ActionListener{
    private AvalonWindow parentWindow;
    private String playerKey;

    public PlayerButtonListener(String playerKey, AvalonWindow parentWindow){
        this.playerKey = playerKey;
        this.parentWindow = parentWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        parentWindow.displayPlayer(playerKey);
    }
}
