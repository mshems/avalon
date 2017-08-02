import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matt on 7/29/2017.
 */
public class AvalonWindow extends JFrame{
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 500;
    private ArrayList<PlayerView> childWindows;
    private JScrollPane rightPanel;
    private JPanel buttonPanel;
    private JPanel centerPanel;
    private JPanel topPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private HashMap<String, PlayerButton> buttonMap;

    public AvalonWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Avalon Stats");
        this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        this.setLayout(new BorderLayout());

        centerPanel = new JPanel();
            centerPanel.setBackground(Color.YELLOW);
            centerPanel.setLayout(new GridLayout());

        initRightPanel();



        topPanel = new JPanel();
            topPanel.add(new JLabel("Top Panel"));
            topPanel.setBackground(Color.RED);

        leftPanel = new JPanel();
            leftPanel.add(new JLabel("Left Panel"));
            leftPanel.setBackground(Color.GREEN);

        bottomPanel = new JPanel();
            bottomPanel.add(new JLabel("Bottom Panel"));
            bottomPanel.setBackground(Color.CYAN);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);

        this.add(leftPanel, BorderLayout.WEST);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void initRightPanel(){
        rightPanel = new JScrollPane(initButtonPanel());
        rightPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.setBorder(BorderFactory.createEmptyBorder());
        this.add(rightPanel, BorderLayout.EAST);

    }

    private JPanel initButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLUE);
        BoxLayout layout = new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS);
        buttonPanel.setLayout(layout);
        addPlayerButtons();
        buttonPanel.add(Box.createVerticalGlue());
        AvalonButton ab = new AvalonButton("All");
        ab.addActionListener(new StatsButtonListener(this));
        JPanel gameButtonPanel = new JPanel();
        gameButtonPanel.setLayout(new BorderLayout());
        gameButtonPanel.setMaximumSize(new Dimension(getWidth(), 25));
        gameButtonPanel.add(ab,BorderLayout.CENTER);
        buttonPanel.add(gameButtonPanel);
        return buttonPanel;
    }

    private void addPlayerButtons(){
        this.buttonMap = new HashMap<>();
        for (String key : avalon.playerList.keySet()){
            PlayerButton playerButton = new PlayerButton(avalon.getPlayer(key).getPlayerName(), key, this);
            buttonMap.put(key, playerButton);
            JPanel playerButtonPanel = new JPanel();
                playerButtonPanel.setLayout(new BorderLayout());
                playerButtonPanel.setMaximumSize(new Dimension(getWidth(), 25));
                playerButtonPanel.add(playerButton, BorderLayout.CENTER);
            buttonPanel.add(playerButtonPanel);
        }
    }

    public void displayPlayer(String playerKey){
        centerPanel.removeAll();
        centerPanel.add(new PlayerView(avalon.getPlayer(playerKey)));
        centerPanel.validate();
        centerPanel.repaint();
    }

    public void displayGameStats(){
        centerPanel.removeAll();
        centerPanel.add(new JLabel("STATS"));
        centerPanel.validate();
        centerPanel.repaint();
    }

    public void repaint(){
        super.repaint();

    }
}
