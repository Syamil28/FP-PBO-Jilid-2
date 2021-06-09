package pacman;

/**
 * Objek tile (wall, point, cherry).
 */
public abstract class Tile extends Sprite {

    public Tile(int x, int y) {
        super(x, y);

        initTile();
    }
    
	/**
	 * Mendapatkan posisi dari objek berdasarkan grid
	 * yang diambil dari posisi koordinat menurut pixel.
	 * 
	 * @return posisi tile berdasarkan grid.
	 */
    public Position getPosition() {
		int x = (getY() - Level.TILEBASE_X) / Level.TILESIZE;
		int y = (getX() - Level.TILEBASE_Y) / Level.TILESIZE;
		Position idx = new Position(x, y);

		return idx;
    }
    
	/**
	 * Mendapatkan array yang berisi tile-tile disekitarnya,
	 * yaitu: atas, kanan, bawah, kiri, 
	 * kanan atas, kiri atas, kanan bawah, dan kiri bawah.
	 * 
	 * @return array tile-tile disekitarnya.
	 */
    public static int[] getAdjacentTiles(int x, int y, int[][] tile) {
		int[] tiles = new int[8];

		int boundX = Level.TILES_Y - 1;
		int boundY = Level.TILES_X - 1;
		boolean xL = (x-1 >= 0) ? true : false;
		boolean xR = (x+1 <= boundX) ? true : false;
		boolean yL = (y-1 >= 0) ? true : false;
		boolean yR = (y+1 <= boundY) ? true : false;

		tiles[0] = (xL) ? tile[x-1][y] : 0;			//Up
		tiles[1] = (yR) ? tile[x][y+1] : 0;			//Right	
		tiles[2] = (xR) ? tile[x+1][y] : 0;			//Down
		tiles[3] = (yL) ? tile[x][y-1] : 0;			//Left
	    tiles[4] = (xL && yL) ? tile[x-1][y-1] : 0;	//TopLeft
		tiles[5] = (xL && yR) ? tile[x-1][y+1] : 0;	//TopRight
        tiles[6] = (xR && yL) ? tile[x+1][y-1] : 0;	//BottomLeft
		tiles[7] = (xR && yR) ? tile[x+1][y+1] : 0;	//BottomRight
		
		return tiles;
    }

	protected abstract void initTile(); 
}
