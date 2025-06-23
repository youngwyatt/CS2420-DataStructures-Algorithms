package assign08;

/**
 * Generically typed Node class used by BinarySearchTree class to represent a node
 * in the BST.
 *
 * @version June 30, 2024
 * @author Brian Keller & Wyatt Young
 */
public class BinaryNode<T>
{
    private T data;
    private BinaryNode<T> leftChild;
    private BinaryNode<T> rightChild;
    /**
     * Constructor for a BinaryNode given references to left and right children
     * set to null if no children below
     *
     * @param data data to be contained in node
     * @param leftChild left child node reference
     * @param rightChild right child node reference
     */
    public BinaryNode(T data, BinaryNode<T> leftChild, BinaryNode<T> rightChild)
    {
        this.data = data;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    /**
     * Constructor for binary node with no references to children
     * @param data data to be contained in node
     */
    public BinaryNode(T data)
    {
        this(data, null, null);
    }
    /**
     * Getter for the data housed in this node.
     *
     * @return the node's data
     */
    public T getData() {
        return this.data;
    }
    /**
     * Setter for the data housed in this node.
     *
     * @param data - the node's data to be (re)set
     */
    public void setData(T data) {
        this.data = data;
    }
    /**
     * Getter for this node's left child reference.
     *
     * @return reference to this node's left child
     */
    public BinaryNode<T> getLeftChild() {
        return this.leftChild;
    }

    /**
     * Setter for this node's left child reference.
     *
     * @param leftChild - reference of this node's left child to be (re)set
     */
    public void setLeftChild(BinaryNode<T> leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Getter for this node's right child reference.
     *
     * @return reference to this node's right child
     */
    public BinaryNode<T> getRightChild() {
        return rightChild;
    }

    /**
     * Setter for this node's right child reference.
     *
     * @param rightChild - reference of this node's right child to be (re)set
     */
    public void setRightChild(BinaryNode<T> rightChild) {
        this.rightChild = rightChild;
    }
    /**
     * Getter for the leftmost node in the binary tree rooted at this node.
     *
     * @return reference to the leftmost node in the binary tree rooted at this node
     */
    public BinaryNode<T> getLeftmostNode()
    {
        if(this.getLeftChild() == null){return this;}
        return leftChild.getLeftmostNode();
    }
    /**
     * Getter for the rightmost node in the binary tree rooted at this node.
     *
     * @return reference to the rightmost node in the binary tree rooted at this node
     */
    public BinaryNode<T> getRightmostNode()
    {
       if(this.getRightChild() == null){return this;}
       return rightChild.getRightmostNode();
    }
    /**
     * Determines the height of the binary tree rooted at this node.
     * The height of a tree is the length of the longest path to a leaf
     * node. A tree with a single node is considered to have a height of zero.
     *
     * @return the height of the binary tree rooted at this node
     */
    public int height()
    {
        int leftCount = 0;
        if(this.getLeftChild() != null)
        {
            leftCount = leftChild.height();
        }
        int rightCount = 0;
        if(this.getRightChild() != null)
        {
            rightCount = rightChild.height();
        }
        return Math.max(leftCount, rightCount) + 1;
    }
}
