package pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Kelas ini adalah objek level saat permainan.
 * Kelas ini mengatur update setiap objek dalam permainan juga inisialisasinya
 * serta maze dari pacman.
 */
@SuppressWarnings("serial")
public class Level extends JPanel implements ActionListener {
    protected Timer timer;
    protected Sprite title;
    protected String playerName;
    protected List<Tile> tiles;
    protected boolean ingame;

    protected final static int TILESIZE = 16;
    protected final static int TILEBASE_X = TILESIZE * 4;
    protected final static int TILEBASE_Y = TILESIZE;
    protected final static int TILES_X = 21;
    protected final static int TILES_Y = 14;
    protected Ghost ghost;
    protected Pacman pacman;
    protected final int IGHOST_X = TILEBASE_X + 9 * TILESIZE;
    protected final int IGHOST_Y = TILEBASE_Y + 11 * TILESIZE;
    protected final int IPACMAN_X = TILEBASE_X + 14 * TILESIZE;
    protected final int IPACMAN_Y = TILEBASE_Y + 14 * TILESIZE;
    
    protected final int DELAY = 15;
    protected int pointCount = 0;
    protected int totalCherry;
    protected int score;
    protected JButton backButton;

    private int[][] tile = {
	  // 1  2  3  4  5  6  7  8  9 10 11 12 13 14 15 16 17 18 19 20 21  
   		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //1
   		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //2
   		{1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1}, //3
   		{1, 2, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 2, 1}, //4
   		{1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1}, //5
   		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, //6
   		{1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1}, //7
   		{1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1}, //8
   		{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1}, //9
   		{1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 1}, //10
   		{1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1}, //11
   		{1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1}, //12
   		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1}, //13
   		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}}; //14
        
    public Level() {
        initLevel();
	}

    /**
     * Inisialisasi level (mmebuat objek, inisialisasi timer, dll)
     */
    protected void initLevel() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(new Color(30, 30, 30));
        playerName = "";
        ingame = true;
        score = 0;
        title = new Sprite(TILESIZE, TILESIZE, "src/resources/title.png");

        setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

        initTiles();

        pacman = new Pacman(IPACMAN_X, IPACMAN_Y, totalCherry, pointCount);
        ghost = new Ghost(IGHOST_X, IGHOST_Y);       

        timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Inisialisasi tile.
     */
    public void initTiles() {
        tiles = new ArrayList<>();          
        for (int i = 0; i < TILES_Y; i++) {
            for (int j = 0; j < TILES_X; j++) {
                tiles.add(newTile(i, j));
            }
        }
    }

    /**
     * Menggambar objek.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (ingame) {
            drawObjects(g);
        } else { 
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Menggambar semua objek.
     * 
     * @param g Graphic
     */
    private void drawObjects(Graphics g) {
        g.drawImage(title.getImage(), title.getX(), title.getY(), this); 
        
        for (Tile t : tiles) {
            if (t.isVisible()) {
                g.drawImage(t.getImage(), t.getX(), t.getY(), this);
            }
        }

        if (ghost.isVisible()) {
            g.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(),
                    this);
        }

        if (pacman.isVisible()) {
            g.drawImage(pacman.getImage(), pacman.getX(), pacman.getY(),
                    this);
        }   
    }

    /**
     * Menggambar screen game over.
     * 
     * @param g graphics.
     */
    private void drawGameOver(Graphics g) {   	
        if (pacman.isChasing() || pointCount <= 0) {
            setBackground(new Color(24, 50, 36));
            if(pointCount <= 0) ghost.setVisible(true);

        } else {
            ghost.setVisible(true);
            pacman.setVisible(false);
            setBackground(new Color(50, 24, 24));
        }
        drawObjects(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {    
        inGame();   

        updatePacman();
        updateGhost();  

        checkCollisions();  

        repaint();
    }

    /**
     * Cek status ingame.
     */
    private void inGame() { 
        if (!ingame) {
            timer.stop();
        }
    }

    /**
     * Update objek pacman
     */
    private void updatePacman() {
        if (pacman.isVisible()) {
            pacman.move();    

            if (!pacman.updateChase()) {
                ghost.setScared(false);
            }
        }
    }

    /**
     * Update objek ghost
     */
    private void updateGhost() {
        if (ghost.isVisible()) {
            ghost.chooseNextMove(tile, pacman);
            ghost.move();       
        }
    }

    /**
     * Membuat tile.
     * 
     * @param x index x
     * @param y index y
     * @return objek Tile baru
     */
    private Tile newTile(int x, int y) {
        int posY = TILEBASE_X + x * TILESIZE;
        int posX = TILEBASE_Y + y * TILESIZE;

        if (tile[x][y] == 1) {

            int[] adjTile = Tile.getAdjacentTiles(x, y, tile);
            
            return new Wall(posX, posY, adjTile[0], adjTile[1], adjTile[2], adjTile[3], 
                    adjTile[4], adjTile[5], adjTile[6], adjTile[7]);

        } else if (tile[x][y] == 0) {
            pointCount++;
            return new Point(posX, posY);

        } else if (tile[x][y] == 2) {
            totalCherry++;
            return new Cherry(posX, posY);

        } else {
            return null;
        }
    }

    /**
     * Cek collision setiap objek.
     */
    public void checkCollisions() {
    
        if (pacman.checkGhostCollision(ghost)) {
            ingame = false;
            return;
        }

        for (Tile t : tiles) {
        
            int collStat = pacman.checkTileCollision(t);
        
            if (collStat == 1) {
                pointCount--;
                Position pos = t.getPosition();

                tile[pos.x()][pos.y()] = -1;
            
            } else if (collStat == 2) {
                ghost.setScared(true);
                pacman.setChasing();
                Position pos = t.getPosition();

                tile[pos.x()][pos.y()] = -1;
            }
        
            if (pointCount <= 0) {
                ingame = false;
                break;
            }
        }
    }

    /**
     * Kelas adapter untuk keyboard
     */
    private class TAdapter extends KeyAdapter { 
        
        @Override
        public void keyPressed(KeyEvent e) {
            pacman.keyPressed(e, tile);
        }
    }
}