package pacman;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Character object for pacman
 */
public class Pacman extends Character {
	
	protected Image imageNormal;
	protected Image imageMirrored;
	protected boolean chasing;
	protected boolean findingCherry;
	protected int chaseTime;
	protected int cherryInterval;
	protected int cherryFindCount;
	protected int cherryFindCountMax;
	protected final int TOTALCHASINGTIME = 500;

	public Pacman(int x, int y, int totalCherry, int pointCount) {
		super(x, y);		
		
		this.chasing = false;
		this.findingCherry = false;
		this.chaseTime = 0;
		this.cherryInterval = pointCount / (totalCherry + 2);
		this.cherryFindCount = 1;
		this.cherryFindCountMax = totalCherry;
	}

	@Override
	protected void initCharacter() {
		ImageIcon ii = new ImageIcon("src/resources/pacman.png");
        this.imageNormal = ii.getImage();
        ii = new ImageIcon("src/resources/mirroredPacman.png");
        this.imageMirrored = ii.getImage();
        
		this.image = imageNormal;
        getImageDimensions();		
	}

	/**
	 * Control using keyboard event.
	 * Change dx and dy according to the pressed key.
	 * 
	 * @param e KeyEvent that is being detected.
	 * @param tile array tile represent the level maze
	 */
	public void keyPressed(KeyEvent e, int[][] tile) {
		
		if(!moving) {
			Position pos = getPosition();
			int key = e.getKeyCode();
			
			if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
				dx = 0;
				dy = (tile[pos.x()-1][pos.y()] != 1) ? (int)(-1 * Level.TILESIZE / frames) : 0;
			}
			
			else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
				this.image = imageMirrored;
				dx = (tile[pos.x()][pos.y()+1] != 1) ? (int)(1 * Level.TILESIZE / frames) : 0;
				dy = 0;
			}
			
			else if (key == KeyEvent.VK_DOWN  || key == KeyEvent.VK_S) {
				dx = 0;
				dy = (tile[pos.x()+1][pos.y()] != 1) ? (int)(1 * Level.TILESIZE / frames) : 0;
			}
			
			else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
				this.image = imageNormal;
				dx = (tile[pos.x()][pos.y()-1] != 1) ? (int)(-1 * Level.TILESIZE / frames) : 0;
				dy = 0;
			}
			
			moving = true;
			moveCount = 0;
		}
	}
	
	/**
	 * Check collision between pacman and all tiles:
	 * - return 1 when collided with point
	 * - return 2 when collided with cherry
	 * - return -1 when not collided with anything
	 * 
	 * @param tile tile maze
	 * @return value based on collision.
	 */
	public int checkTileCollision(Tile tile) {
		Rectangle t = tile.getBounds();
		Rectangle p = this.getBounds();
			
		if(p.intersects(t) && tile.isVisible()) {
			tile.setVisible(false);
			
			if (tile instanceof Point) {
				return 1;
			} else if (tile instanceof Cherry) {
				findingCherry = false;
				return 2;
			}
		}
		return -1;
	}
	
	/**
	 * Check pacman collision with code.
	 * When collided, make ghost invisible.
	 * 
	 * @param ghost Ghost object
	 * @return boolean collision status.
	 */
	public boolean checkGhostCollision(Ghost ghost) {
		Rectangle g = ghost.getBounds();
		Rectangle p = this.getBounds();
			
		if(p.intersects(g) && ghost.isVisible()) {
			ghost.setVisible(false);
			
			return true;
		}
		return false;
	}
	
	public boolean isChasing() {
		return chasing;
	}
	
	/**
	 * Update chasing status and chase time.
	 * 
	 * @return status if chasing or not.
	 */
	public boolean updateChase() {
		if(chasing) {
			chaseTime++;
		}
		
		if(chaseTime == TOTALCHASINGTIME) {
			this.chasing = false;
			this.chaseTime = 0;
		}
		
		return chasing;
	}
	
	public void setChasing() {
		this.chasing = true;
	}
}
