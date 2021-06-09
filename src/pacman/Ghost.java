package pacman;

import java.awt.Image;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 * Objek karakter ghost.
 */
public class Ghost extends Character {
	
	protected Image imageScared;
	protected Image imageNormal;
	protected int buffFrames;
	protected boolean scared;
	protected final int MINDISTANCEFROMPACMAN = 10;

	public Ghost(int x, int y){
		super(x, y);
		this.frames = 16;
		this.buffFrames = 16;
	}

	/**
	 * Inisialisasi objek.
	 * Membuat 2 image untuk ghost normal dan scared.
	 */
	@Override
	protected void initCharacter() {
		ImageIcon ii = new ImageIcon("src/resources/ghost.png");
        this.imageNormal = ii.getImage();
        ii = new ImageIcon("src/resources/ghost_scared.png");
        this.imageScared = ii.getImage();
        
		this.image = imageNormal;
        getImageDimensions();		
	}

	public boolean isScared() {
		return scared;
	}

	/**
	 * Mengubah status ghost menjadi scared atau normal.
	 */
	public void setScared(boolean scared) {
		this.scared = scared;

		if (this.scared) {
			this.image = imageScared;
			buffFrames = 32;
		} else {
			this.image = imageNormal;
			buffFrames = 16;
		}
	}

}