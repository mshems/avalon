import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Matt on 8/2/2017.
 */
public class StatsButtonListener implements ActionListener{
    private AvalonWindow parentWindow;

    public StatsButtonListener(AvalonWindow parentWindow){
        this.parentWindow = parentWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        parentWindow.displayGameStats();
    }
}
