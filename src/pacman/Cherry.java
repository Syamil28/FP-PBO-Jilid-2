package pacman;

/**
 * Objek tile berupa cherry.
 */
public class Cherry extends Tile {

	public Cherry(int x, int y) {
		super(x, y);
	}

	@Override
	protected void initTile() {
        loadImage("src/resources/cherry.png");
        getImageDimensions();
	}
}
