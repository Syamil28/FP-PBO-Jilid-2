package pacman;

import java.awt.Image;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 * Character object for ghost.
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
	 * Object initialization.
	 * Get 2 images from resources for normal and scared ghost.
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
	 * Set ghost's state to scared or normal
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
	 * Choosing next move depending on ghost's state.
	 * The next move is chosen by the concerned method.
	 * After the move is obtained, change the value of dx and dy.
	 * 
	 * @param tile Maze level
	 * @param pacman Pacman object
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
	 * Returning the next move based on node relative position
	 * to the current position of pacman.
	 * 
	 * @param node Next move node
	 * @return Next move.
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
	 * Returning the next move node by
	 * iterating the parent pointers of a node 
	 * that returned by a search algorithm.
	 * 
	 * @param node Node returned by search algorithm 
	 * @return Next move node.
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
	 * Returning tne available moves of the current position
	 * 
	 * @param pos Current position
	 * @param tile Maze level
	 * @return ArrayList with available moves position
	 */
	private ArrayList<Position> getAvailableMoves(Position pos, int[][] tile) {
		ArrayList<Position> availableMoves = new ArrayList<Position>();
		int idxX = pos.x();
		int idxY = pos.y();
		
		if (idxX-1 >= 0 && tile[idxX-1][idxY] != 1) 
			availableMoves.add(new Position(idxX-1, idxY));
		
		if (idxX+1 <= Level.TILES_Y && tile[idxX+1][idxY] != 1) 
			availableMoves.add(new Position(idxX+1, idxY));
		
		if (idxY-1 >= 0 && tile[idxX][idxY-1] != 1) 
			availableMoves.add(new Position(idxX, idxY-1));
		
		if (idxY+1 <= Level.TILES_X && tile[idxX][idxY+1] != 1) 
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
		ArrayList<Position> visited = new ArrayList<Position>();
		fringe.add(node);
		
		while (!fringe.isEmpty()) {
			node = fringe.pop();
			visited.add(node.data);
			System.out.printf("\nnode: %d %d\n", node.data.x(), node.data.y());
			
			if (node.data.equals(pacmanPos)) {
				return node;
			}
			
			ArrayList<Position> children = getAvailableMoves(node.data, tile);
			Iterator<Position> i = children.iterator();
			
			while (i.hasNext()) {
				Position childPos = i.next();
				System.out.printf("child: %d %d\n", childPos.x(), childPos.y());
				
				if(!visited.contains(childPos)) {
					System.out.println("Inserted");
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
		ArrayList<Position> visited = new ArrayList<Position>();
		fringe.add(node);
		
		while (!fringe.isEmpty()) {
			node = fringe.pop();
			visited.add(node.data);
			
			if (Position.getDistance(node.data, pacmanPos) >= MINDISTANCEFROMPACMAN) {
				return node;
			}
			
			ArrayList<Position> children = getAvailableMoves(node.data, tile);
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