import javax.swing.*;
import java.awt.*;

/**
 * Created by Matt on 7/29/2017.
 */
public class PlayerButton extends JButton{


    public PlayerButton(String label, String playerKey, AvalonWindow parentWindow){
        super(label);
        this.addActionListener(new PlayerButtonListener(playerKey, parentWindow));
    }

    public void select(){
       this.setBackground(new Color(184,207,229));
    }

    public void deselect(){
        this.setBackground(Color.WHITE);
    }
}
