package lab07;

/**
 * Represents a generically-typed binary tree node. Each binary node contains
 * data, a reference to the left child, and a reference to the right child.
 * 
 * @author Prof. Parker and Wyatt Young
 * @version June 28, 2024
 */
public class BinaryNode<Type> {

	private Type data;

	private BinaryNode<Type> leftChild;

	private BinaryNode<Type> rightChild;

	/**
	 * Creates a new binary node, given references to children.
	 * 
	 * @param data - data to be housed in this node
	 * @param leftChild - reference to this node's left child
	 * @param rightChild - reference to this node's right child
	 */
	public BinaryNode(Type data, BinaryNode<Type> leftChild, BinaryNode<Type> rightChild) {
		this.data = data;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	/**
	 * Creates a new binary node, with null references to children.
	 * 
	 * @param data - data to be housed in this node
	 */
	public BinaryNode(Type data) {
		this(data, null, null);
	}

	/**
	 * Getter for the data housed in this node.
	 * 
	 * @return the node's data
	 */
	public Type getData() {
		return data;
	}

	/**
	 * Setter for the data housed in this node.
	 * 
	 * @param data - the node's data to be (re)set
	 */
	public void setData(Type data) {
		this.data = data;
	}

	/**
	 * Getter for this node's left child reference.
	 * 
	 * @return reference to this node's left child
	 */
	public BinaryNode<Type> getLeftChild() {
		return leftChild;
	}

	/**
	 * Setter for this node's left child reference.
	 * 
	 * @param leftChild - reference of this node's left child to be (re)set
	 */
	public void setLeftChild(BinaryNode<Type> leftChild) {
		this.leftChild = leftChild;
	}

	/**
	 * Getter for this node's right child reference.
	 * 
	 * @return reference to this node's right child
	 */
	public BinaryNode<Type> getRightChild() {
		return rightChild;
	}

	/**
	 * Setter for this node's right child reference.
	 * 
	 * @param rightChild - reference of this node's right child to be (re)set
	 */
	public void setRightChild(BinaryNode<Type> rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * Getter for the leftmost node in the binary tree rooted at this node.
	 * 
	 * @return reference to the leftmost node in the binary tree rooted at this node
	 */
	public BinaryNode<Type> getLeftmostNode() {
		if(leftChild == null){return this;}
		return leftChild.getLeftmostNode();
	}

	/**
	 * Getter for the rightmost node in the binary tree rooted at this node.
	 *
	 * @return reference to the rightmost node in the binary tree rooted at this node
	 */
	public BinaryNode<Type> getRightmostNode()
	{
		if(rightChild == null){return this;}
		return rightChild.getRightmostNode();
	}

	/**
	 * Determines the height of the binary tree rooted at this node.
	 * The height of a tree is the length of the longest path to a leaf
	 * node. A tree with a single node is considered to have a height of zero.
	 * 
	 * @return the height of the binary tree rooted at this node
	 */
	public int height() {
		int leftHeight = 0;
		if(leftChild != null)
		{
			leftHeight = leftChild.height();
		}
		int rightHeight = 0;
		if(rightChild != null)
		{
			rightHeight = rightChild.height();
		}
		if(leftChild == null && rightChild == null)
		{
			return 0;
		}
		return Math.max(leftHeight, rightHeight) + 1;
	}
}