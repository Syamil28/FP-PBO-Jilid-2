package pacman;

import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Project Quiz 2 DAA E
 * Main class for this project.
 * 
 * @author Daanii Kusnanta
 * @author M. Nur Abdurrauf
 * @author Syamil Dhifaul Haq Syukur
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

	public static final int WIDTH = Level.TILESIZE*(2 + Level.TILES_X);
    public static final int HEIGHT = Level.TILESIZE*(5 + Level.TILES_Y);
    public static CardLayout cardLayout;
    public static JPanel mainPanel;

    public Main() {
        
        initUI();
    }
    
    private void initUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new Level(), "level");
        
        add(mainPanel);
        
        setResizable(false);
        pack();
        
        setTitle("Pacman");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main ex = new Main();
            ex.setVisible(true);
        });
    }
}