package pacman;

/**
 * Objek wall/dinding dari maze pacman.
 */
public class Wall extends Tile {
	
	protected Integer up, right, down, left;
	protected String imageName;

	/**
	 * Inisialisasi dan konstruktor objek wall.
	 * Menggunakan paramater untuk menentukan asset yang tepat.
	 * 
	 * @param x	posisi x
	 * @param y	posisi y
	 * @param up tile atas
	 * @param right	tile kanan
	 * @param down tile bawah
	 * @param left tile kiri
	 * @param topL tile kiri atas
	 * @param topR tile kanan atas
	 * @param bottomL tile kiri bawah
	 * @param bottomR tile kanan bawah
	 */
	public Wall(int x, int y, int up, int right, int down, int left, int topL, int topR, int bottomL, int bottomR) {
		super(x, y);
		this.up = (up == 1 && (topR != 1 || topL != 1 || (left != 1 || right != 1))) ? 1 : 0;
		this.right = (right == 1 && (topR != 1 || bottomR != 1 || (up != 1 || down != 1))) ? 1 : 0;
		this.down = (down == 1 && (bottomR != 1 || bottomL != 1 || (left != 1 || right != 1))) ? 1 : 0;
		this.left = (left == 1 && (topL != 1 || bottomL != 1  || (up != 1 || down != 1))) ? 1 : 0;
		this.imageName = "src/resources/wall_" + 
				this.up.toString() + this.right.toString() + 
				this.down.toString() + this.left.toString() + ".png";
		if (up + right + down + left + topL + topR + bottomL + bottomR == 8) {
			this.imageName = "src/resources/wall_empty.png";
		}
		
		initTile();
	}
	
	@Override
	protected void initTile() {
		loadImage(imageName);
        getImageDimensions();
	}	
}