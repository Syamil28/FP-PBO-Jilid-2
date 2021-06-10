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
	protected final int MINDISTANCEFROMPACMAN = 15;

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

	/**
	 * Memilih move selanjutnya berdasarkan status ghost.
	 * Move selanjutnya dipilih berdasarkan method bersangkutan.
	 * Setelah didapatkan move, ubah dx dan dy.
	 * 
	 * @param tile Maze permainan
	 * @param pacman Objek pacman
	 */
	public void chooseNextMove(int[][] tile, Pacman pacman) {
		
		if(!moving) {
		
			Move nextMove;
			Position pos = this.getPosition();
			if(!scared) nextMove = getMove(searchPacman(tile, pacman));
			else nextMove = getMove(runFromPacman(tile, pacman));
			
			if(nextMove == null) {
				nextMove = Move.Stay;
			}
		
			switch (nextMove) {
				case Up:
					dx = 0;
					dy = (tile[pos.x()-1][pos.y()] != 1) ? (int)(-1 * Level.TILESIZE / frames) : 0;
					break;
			
				case Right:
					dx = (tile[pos.x()][pos.y()+1] != 1) ? (int)(1 * Level.TILESIZE / frames) : 0;
					dy = 0;
					break;
				
				case Down:
					dx = 0;
					dy = (tile[pos.x()+1][pos.y()] != 1) ? (int)(1 * Level.TILESIZE / frames) : 0;
					break;
				
				case Left:
					dx = (tile[pos.x()][pos.y()-1] != 1) ? (int)(-1 * Level.TILESIZE / frames) : 0;
					dy = 0;
					break;
					
				case Stay:
					dx = 0;
					dy = 0;
					break;
					
				default:
					break;
			}
			moving = true;
			moveCount = 0;
		}
	}

	/**
	 * Mengembalikan move selanjutnya berdasarkan posisi node 
	 * relatif dengan posisi pacman saat ini.
	 * 
	 * @param node Node move selanjutnya
	 * @return Move selanjutnya.
	 */
	private Move getMove(TreeNode node) {
		TreeNode nextMoveNode = getNextMoveNode(node);
		
		int nodeX = nextMoveNode.data.x();
		int nodeY = nextMoveNode.data.y();
		int x = this.getPosition().x();
		int y = this.getPosition().y();
		
		if (nodeX == x-1) return Move.Up;
		if (nodeX == x+1) return Move.Down;
		if (nodeY == y-1) return Move.Left;
		if (nodeY == y+1) return Move.Right;
		return null;
	}

	/**
	 * Mendapatkan node move selanjutnya dengan 
	 * mengiterasi parent dari node hasil search.
	 * 
	 * @param node Node hasil search
	 * @return node move selanjutnya.
	 */
	private TreeNode getNextMoveNode(TreeNode node) {
		
		TreeNode parent = node.parent;
		
		while(parent != null && !parent.data.equals(this.getPosition())) {
			node = parent;
			parent = node.parent;
		}
		
		return node;
	}

	/**
	 * Mendapatkan move tersedia dari posisi saat ini.
	 * 
	 * @param pos Posisi saat ini
	 * @param parent Parent dari node saat ini
	 * @param tile Maze permainan
	 * @return ArrayList Posisi-posisi move yang tersedia.
	 */
	private ArrayList<Position> getAvailableMoves(Position pos, TreeNode parent, int[][] tile) {
		ArrayList<Position> availableMoves = new ArrayList<Position>();
		Position par = (parent != null) ? parent.data : new Position(0, 0);
		int idxX = pos.x();
		int idxY = pos.y();
		
		if (idxX-1 >= 0 && tile[idxX-1][idxY] != 1 && !(idxX-1 == par.x() && idxY == par.y()) ) 
			availableMoves.add(new Position(idxX-1, idxY));
		
		if (idxX+1 <= Level.TILES_Y && tile[idxX+1][idxY] != 1 && !(idxX+1 == par.x() && idxY == par.y())) 
			availableMoves.add(new Position(idxX+1, idxY));
		
		if (idxY-1 >= 0 && tile[idxX][idxY-1] != 1 && !(idxX == par.x() && idxY-1 == par.y())) 
			availableMoves.add(new Position(idxX, idxY-1));
		
		if (idxY+1 <= Level.TILES_X && tile[idxX][idxY+1] != 1 && !(idxX == par.x() && idxY+1 == par.y())) 
			availableMoves.add(new Position(idxX, idxY+1));
		
		return availableMoves;
	}

	/**
	 * Mencari pacman dengan BFS.
	 * 
	 * @param tile Maze permainan
	 * @param pacman Objek pacman
	 * @return node saat pacman ditemukan.
	 */
	private TreeNode searchPacman(int[][] tile, Pacman pacman) {	
		Position pos = this.getPosition();
		Position pacmanPos = pacman.getPosition();
		
		TreeNode node = new TreeNode(pos);
		Tree searchTree = new Tree(node);
		Deque<TreeNode> fringe = new ArrayDeque<TreeNode>();
		HashSet<Position> visited = new HashSet<Position>();
		fringe.add(node);
		
		while (!fringe.isEmpty()) {
			node = fringe.pop();
			visited.add(node.data);
			System.out.printf("\nnode: %d %d\n", node.data.x(), node.data.y());
			
			if (node.data.equals(pacmanPos)) {
				return node;
			}
			
			ArrayList<Position> children = getAvailableMoves(node.data, node.parent, tile);
			Iterator<Position> i = children.iterator();
			
			while (i.hasNext()) {
				Position childPos = i.next();
				System.out.printf("child: %d %d\n", childPos.x(), childPos.y());
				
				if(!visited.contains(childPos)) {
					TreeNode child = new TreeNode(childPos);
					fringe.addLast(child);
					searchTree.insert(child, node);	
				}
			}
			System.out.printf("fringe size: %d\n", fringe.size());
		}
		return node;
	}

	/**
	 * Mencari posisi untuk lari dengan BFS.
	 * 
	 * @param tile Maze permainan
	 * @param pacman Objek pacman
	 * @return node saat posisi lari ditemukan.
	 */
	private TreeNode runFromPacman(int[][] tile, Pacman pacman) {	
		Position pacmanPos = pacman.getPosition();
		
		TreeNode node = new TreeNode(this.getPosition());
		Tree searchTree = new Tree(node);
		Deque<TreeNode> fringe = new ArrayDeque<TreeNode>();
		HashSet<Position> visited = new HashSet<Position>();
		fringe.add(node);
		
		while (!fringe.isEmpty()) {
			node = fringe.pop();
			visited.add(node.data);
			
			if (Position.getDistance(node.data, pacmanPos) >= MINDISTANCEFROMPACMAN) {
				return node;
			}
			
			ArrayList<Position> children = getAvailableMoves(node.data, node.parent, tile);
			Iterator<Position> i = children.iterator();
			
			while (i.hasNext()) {
				Position childPos = i.next();
				
				if(!visited.contains(childPos)) {
					TreeNode child = new TreeNode(childPos);
					fringe.addLast(child);
					searchTree.insert(child, node);	
				}
			}
		}
		return node;
	}

}