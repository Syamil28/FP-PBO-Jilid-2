package pacman;

/**
 * Object position for tile position berdasarkan grid.
 */
public class Position {
	private int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position() {
		this.x = 0;
		this.y = 0;
	}

	public int x() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int y() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get distance from 2 position.
	 * 
	 * @param from first position
	 * @param to last position
	 * @return distance between 2 position
	 */
	public static int getDistance(Position from, Position to) {
		return Math.abs(from.x() - to.x())
				+ Math.abs(from.y() - to.y());
	}
	
	@Override
	public boolean equals(Object obj) {
		Position b = (Position) obj;
		if (this.x() == b.x() && this.y() == b.y()) return true;
		return false;
	}
}
