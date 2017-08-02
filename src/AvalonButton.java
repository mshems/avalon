import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Matt on 8/2/2017.
 */
public class AvalonButton extends JButton{

    public AvalonButton(String label){
        super(label);
        //this.parentWindow = parentWindow;
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBackground(Color.white);
    }
}
