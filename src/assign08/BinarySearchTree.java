package assign08;

import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * This class represents a Binary Search Tree used to store a dictionary of generically
 * typed items. Mainly a list of Strings used to spell check documents or .txt files.
 * Uses BinaryNode class to represent nodes in the BST.
 * Note: compares dictionary items case-insensitive
 * @version June 30, 2024
 * @author Brian Keller & Wyatt Young
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {
    private BinaryNode<T> root;
    private int size;
    /**
     * constructor for a BST
     */
    public BinarySearchTree(){
        this.root = null;
        this.size = 0;
    }
    /**
     * Ensures that this set contains the specified item.
     *
     * @param item - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is,
     * if the item was actually inserted); otherwise, returns false
     */
    @Override
    public boolean add(T item) {
        if (root == null) {
            root = new BinaryNode(item);
            size++;
            return true;
        }
        return addRecursive(root,item);
    }
    /**
     * recursive method for ensuring that a set contains a specified item
     * @param item - item being added/searched for
     * @param node - current node being examined for item's presence
     * @return true if the item was actually inserted, false otherwise
     */
    private boolean addRecursive(BinaryNode<T> node, T item){
        int compare = item.compareTo(node.getData());
        if(compare == 0)
            return false;
        else if(compare < 0){
            if(node.getLeftChild() == null){
                node.setLeftChild(new BinaryNode<>(item));
                size++;
                return true;
            }
            else return addRecursive(node.getLeftChild(), item);
        }
        else {
            if(node.getRightChild() == null){
                node.setRightChild(new BinaryNode<>(item));
                size++;
                return true;
            }
            else return addRecursive(node.getRightChild(), item);
        }
    }
    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        this.root = null;
        size = 0;
    }
    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item - the item sought in this set
     * @return true if there is an item in this set that is equal to the given item;
     * otherwise, returns false
     */
    @Override
    public boolean contains(T item) {
        return containsRecursive(root, item);
    }
    /**
     * recursive method for the contains method
     * @param node being checked
     * @param item data being searched for
     * @return true if there is an item equal to the input
     */
    private boolean containsRecursive(BinaryNode<T> node, T item){
        if(node == null) return false;
        int compare = item.compareTo(node.getData());
        if(compare == 0){
            return true;
        }
        else if(compare < 0){
            return containsRecursive(node.getLeftChild(), item);
        }
        else{
            return containsRecursive(node.getRightChild(), item);
        }
    }
    /**
     * Determines the first (i.e., smallest) item in this set.
     *
     * @return the first (i.e., smallest) item in this set
     * @throws NoSuchElementException if the set is empty
     */
    @Override
    public T first() throws NoSuchElementException {
        if (root == null)
            throw new NoSuchElementException("Set is Empty");
        else return root.getLeftmostNode().getData();
    }
    /**
     * Determines if this set contains any items.
     *
     * @return true if this set contains no items; otherwise returns false
     */
    @Override
    public boolean isEmpty() {
        if(size == 0)
            return true;
        else return false;
    }
    /**
     * Determines the last (i.e., largest) item in this set.
     *
     * @return the last (i.e., largest) item in this set
     * @throws NoSuchElementException if the set is empty
     */
    @Override
    public T last() throws NoSuchElementException {
        if (size==0)
            throw new NoSuchElementException("Set is Empty");
        else return root.getRightmostNode().getData();
    }
    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     * the input item was actually removed); otherwise, returns false
     */
    @Override
    public boolean remove(T item) {
        if(root == null) return false;
        return removeRecursive(root, root, item);
    }
    /**
     * recursive method for ensuring a set does not contain a specified item
     * @param parent - parent of the current node being examined. Tracked in order to adjust child references appropriately
     * @param current - current node being examined
     * @param item - item whose absence must be ensured
     * @return - true if the set was changed as a result of the method call, false otherwise
     */
    public boolean removeRecursive(BinaryNode<T> parent, BinaryNode<T> current, T item){
        if(current == null) return false;
        int compare = item.compareTo(current.getData());
        if(compare < 0) return removeRecursive(current, current.getLeftChild(), item);
        if(compare > 0) return removeRecursive(current, current.getRightChild(), item);
        else {
            if(current.getRightChild() == null && current.getLeftChild() == null) {
                if (parent.getLeftChild() == current) {
                    parent.setLeftChild(null);
                } else {
                    parent.setRightChild(null);
                }
                size--;
            }
            else if(current.getLeftChild() != null && current.getRightChild() != null){
                BinaryNode<T> successor = current.getRightChild().getLeftmostNode();
                current.setData(successor.getData());
                successor = null;
                size--;
            }
            else if(current.getLeftChild() != null || current.getRightChild() !=null){
                if(parent.getRightChild() == current) {
                    parent.setRightChild(current.getRightChild());
                }
                else{
                    parent.setLeftChild(current.getLeftChild());
                }
                size--;
            }
            return true;
        }
    }
    /**
     * Determines the number of items in this set.
     *
     * @return the number of items in this set.
     */
    @Override
    public int size() {
        return size;
    }
    /**
     * Generates a basic array containing all of the items in this set, in sorted
     * order.
     *
     * @return a basic array containing all of the items in this set
     */
    @Override
    public Object[] toArray() {
        ArrayList list = new ArrayList<>();
        inOrder(root, list);
        Object[] arr = list.toArray();
        return arr;
    }
    /**
     * recursive method for traversing the list in order
     * @param node - current node being considered for addition or children
     * @param list array(list) being built
     */
    private void inOrder(BinaryNode<T> node, ArrayList list){
        if(node != null){
            inOrder(node.getLeftChild(), list);
            list.add(node.getData());
            inOrder(node.getRightChild(), list);
        }
    }
    /**
     * Generates a basic array containing all of the items in this set that are in the range
     * begin to end (inclusive), in sorted order.
     * <p>
     * For a BST, this operation must work by efficiently traversing the tree and may not
     * sort the items. I.e., do not visit parts of the tree that are known to be out of
     * range, and collect the items in order to avoid sorting.
     *
     * @param begin - beginning value of range (inclusive)
     * @param end - end value of range (inclusive)
     * @return a basic array containing all of the items in this set that are in the range
     * begin to end (inclusive)
     */
    @Override
    public Object[] toArrayRange(T begin, T end) {
        ArrayList list = new ArrayList<>();
        inOrderRange(root, list, begin, end);
        Object[] arr = list.toArray();
        return arr;
    }
    /**
     * recursize method for generating array of data within specified range
     * @param node - current node being considered, either for addition to the list or for any children
     * @param list - list being built from node values within specified range
     * @param begin - beginning value of range (inclusive)
     * @param end - end value of range (inclusive)
     */
    private void inOrderRange(BinaryNode<T> node, ArrayList list, T begin, T end){
        if(node != null){
            if (node.getData().compareTo(begin) > 0) {
                inOrderRange(node.getLeftChild(), list, begin, end);
            }
            if (node.getData().compareTo(begin) >= 0 && node.getData().compareTo(end) <= 0) {
                list.add(node.getData());
            }
            if (node.getData().compareTo(end) < 0) {
                inOrderRange(node.getRightChild(), list, begin, end);
            }
        }
    }
}
