package assign08;

import java.util.NoSuchElementException;

/**
 * An interface for representing a sorted set of generically-typed items. By
 * definition, a set contains no duplicate items. The items are ordered using
 * their natural ordering (i.e., each item must be Comparable). Note that this
 * interface is much like Java's SortedSet, but simpler.
 * 
 * @author Prof. Parker
 * @version June 27, 2024
 */
public interface SortedSet<E extends Comparable<? super E>> {

	/**
	 * Ensures that this set contains the specified item.
	 * 
	 * @param item - the item whose presence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is,
	 *         if the item was actually inserted); otherwise, returns false
	 */
	public boolean add(E item);

	/**
	 * Removes all items from this set. The set will be empty after this method
	 * call.
	 */
	public void clear();

	/**
	 * Determines if there is an item in this set that is equal to the specified
	 * item.
	 * 
	 * @param item - the item sought in this set
	 * @return true if there is an item in this set that is equal to the given item;
	 *         otherwise, returns false
	 */
	public boolean contains(E item);

	/**
	 * Determines the first (i.e., smallest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 * @return the first (i.e., smallest) item in this set
	 */
	public E first() throws NoSuchElementException;

	/**
	 * Determines if this set contains any items.
	 * 
	 * @return true if this set contains no items; otherwise returns false
	 */
	public boolean isEmpty();

	/**
	 * Determines the last (i.e., largest) item in this set.
	 * 
	 * @throws NoSuchElementException if the set is empty
	 * @return the last (i.e., largest) item in this set
	 */
	public E last() throws NoSuchElementException;

	/**
	 * Ensures that this set does not contain the specified item.
	 * 
	 * @param item - the item whose absence is ensured in this set
	 * @return true if this set changed as a result of this method call (that is, if
	 *         the input item was actually removed); otherwise, returns false
	 */
	public boolean remove(E item);

	/**
	 * Determines the number of items in this set.
	 * 
	 * @return the number of items in this set.
	 */
	public int size();

	/**
	 * Generates a basic array containing all of the items in this set, in sorted
	 * order.
	 * 
	 * @return a basic array containing all of the items in this set
	 */
	public Object[] toArray();
	
	/**
	 * Generates a basic array containing all of the items in this set that are in the range
	 * begin to end (inclusive), in sorted order.
	 * 
	 * For a BST, this operation must work by efficiently traversing the tree and may not
	 * sort the items. I.e., do not visit parts of the tree that are known to be out of
	 * range, and collect the items in order to avoid sorting.
	 * 
	 * @return a basic array containing all of the items in this set that are in the range
	 *         begin to end (inclusive)
	 */
	public Object[] toArrayRange(E begin, E end);	
}
