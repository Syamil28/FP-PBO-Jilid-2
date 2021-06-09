package pacman;

/**
 * Kelas untuk struktur data tree.
 */
public class Tree {
	protected TreeNode root;
	
	public Tree() {
		this.root = null;
	}
	
	public Tree(TreeNode root) {
		this.root = root;
	}
	
	/**
	 * Memasukkan node ke tree juga set parent untuk node tersebut.
	 * 
	 * @param node Node yang dimasukkan
	 * @param parent Parent dari node yang dimasukkan
	 */
	public void insert(TreeNode node, TreeNode parent) {
		if (parent != null) {
			parent.addChild(node);
		}
	}	
}