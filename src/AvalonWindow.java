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
    private ArrayList<PlayerWindow> childWindows;
    private JScrollPane rightPanel;
    private JPanel playerListPanel;
    private JPanel topPanel;
    JPanel centerPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JPanel contents;
    HashMap<String, PlayerButton> buttonMap;
    String activePlayerKey;

    public AvalonWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Avalon Stats");
        this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        this.contents = new JPanel();
        this.contents.setLayout(new BorderLayout());

        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        centerPanel.setBackground(Color.WHITE);
        ////////////////
        playerListPanel = new JPanel();
        //playerListPanel.setBackground(Color.BLUE);

        BoxLayout layout = new BoxLayout(playerListPanel, BoxLayout.PAGE_AXIS);
        playerListPanel.setLayout(layout);
        //JLabel playersLabel = new JLabel("Players:");
        //playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //playerListPanel.add(playersLabel);

        this.buttonMap = new HashMap<>();
        for(String key:avalon.playerList.keySet()){
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());
            //JButton playerButton = new JButton(key);
            PlayerButton playerButton = new PlayerButton(avalon.playerList.get(key).getPlayerName(), key,  this);
            playerButton.setBorderPainted(false);
            playerButton.setFocusPainted(false);
            playerButton.setBackground(Color.white);
            //playerButton.addActionListener(new PlayerButtonListener(avalon.playerList.get(key), this));
            buttonMap.put(key,playerButton);
            buttonPanel.setMaximumSize(new Dimension(getWidth(), 25));
            buttonPanel.add(playerButton, BorderLayout.CENTER);
            playerListPanel.add(buttonPanel);
        }
        rightPanel = new JScrollPane(playerListPanel);
        rightPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        rightPanel.setViewportView(playerListPanel);
        rightPanel.setBorder(BorderFactory.createEmptyBorder());
        ////////////////
        topPanel = new JPanel();
        //topPanel.setBackground(Color.RED);
        ////////////////
        leftPanel = new JPanel();
        //leftPanel.setBackground(Color.WHITE);
        ////////////////
        bottomPanel = new JPanel();
        //bottomPanel.setBackground(Color.CYAN);
        ////////////////
        this.contents.add(topPanel, BorderLayout.NORTH);
        this.contents.add(centerPanel, BorderLayout.CENTER);
        this.contents.add(rightPanel, BorderLayout.EAST);
        this.contents.add(leftPanel, BorderLayout.WEST);
        this.contents.add(bottomPanel, BorderLayout.SOUTH);

        /*for(String s:names){
            this.playerList.add(new PlayerWindow(avalon.playerList.get(s), this));
        }*/

        this.add(contents);
        //this.pack();
    }

    public void repaint(){
        super.repaint();

    }
}
