package pacman;

import java.util.ArrayList;

/**
 * Node for {@link pacman.Tree}
 */
public class TreeNode {
	protected Position data;
	protected TreeNode parent;
	protected ArrayList<TreeNode> children;
	
	/**
	 * constructor with 1 parameter.
	 * 
	 * @param data	Data from node
	 */
	public TreeNode(Position data) {
		this.data = data;
		this.parent = null;
		this.children = new ArrayList<TreeNode>();
	}
	
	/**
	 * Add child.
	 * 
	 * @param node Added child node.
	 */
	public void addChild(TreeNode node) {
		this.children.add(node);
		node.parent = this;
	}
}
