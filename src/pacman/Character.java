package pacman;

/**
 * Abstract class for Character objects(Ghost and Pacman).
 */
public abstract class Character extends Tile {
	
	enum Move {Up, Right, Down, Left, Stay}	
    protected boolean moving;
    protected int dx, dy;
    protected int moveCount;
    protected int frames;

    public Character(int x, int y) {
        super(x, y);
        this.moving = false;
        this.dx = 0;
        this.dy = 0;
        this.frames = 8;
        this.moveCount = 0;

        initTile();
    }
    
    protected void initTile(){    
    	initCharacter();
    }
    
    /**
     * Moving the character by changing x and y position.
     * Set moving status by incrementing moveCount.
     * If moveCount is equal to frames, stop moving.
     */
	public void move() {
		if (moving) {
			x += dx;
			y += dy;
			moveCount++;
		}
		
		if (moveCount == frames) {
			moving = false;
		}
	}
	
    protected abstract void initCharacter();
}
