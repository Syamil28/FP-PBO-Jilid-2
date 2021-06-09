package pacman;

import java.util.ArrayList;

/**
 * Node untuk {@link pacman.Tree}
 */
public class TreeNode {
	protected Position data;
	protected TreeNode parent;
	protected ArrayList<TreeNode> children;
	
	/**
	 * Konstruktor dengan 1 parameter.
	 * 
	 * @param data	Data dari node
	 */
	public TreeNode(Position data) {
		this.data = data;
		this.parent = null;
		this.children = new ArrayList<TreeNode>();
	}
	
	/**
	 * Menambahkan child.
	 * 
	 * @param node Node anak yang ditambahkan.
	 */
	public void addChild(TreeNode node) {
		this.children.add(node);
		node.parent = this;
	}
}
