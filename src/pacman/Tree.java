package pacman;

/**
 * Class for data strcuture tree.
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
	 * Insert node to tree also set parent for the node.
	 * 
	 * @param node Inserted Node
	 * @param parent Parent from inserted node
	 */
	public void insert(TreeNode node, TreeNode parent) {
		if (parent != null) {
			parent.addChild(node);
		}
	}	
}