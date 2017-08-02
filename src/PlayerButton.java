import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Matt on 7/29/2017.
 */
public class PlayerButton extends AvalonButton{

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

    public void setActionListener(ActionListener a){
        //this.addActionListener(a);
        this.actionListener = a;
    }
}
