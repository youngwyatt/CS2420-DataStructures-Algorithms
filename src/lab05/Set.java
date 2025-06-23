package lab05;

import java.util.Collection;

/**
 * A set that supports operations to add, remove, or query elements.
 * 
 * @author Eric Heisler
 * @version 2024-5-22
 * 
 * @param <E> - the type of elements contained in this set
 */
public interface Set<E> {
	/**
	 * Adds an element to the set.
	 * 
	 * @param item - the element to add
	 */
	public void add(E item);
	
	/**
	 * Removes and returns an element equal to the given item if 
	 * such an element is present.
	 * 
	 * @param item - an object equal to the element to be removed
	 * @return the element that was removed or null if it isn't present
	 */
	public E remove(E item);
	
	/**
	 * Indicates whether this set contains an element equal to the 
	 * given item.
	 * 
	 * @param item - the object to be checked for containment in this set
	 * @return true if the item is contained in this set
	 */
	public boolean contains(E item);
	
	/**
	 * Indicates whether this set contains all of the elements in 
	 * the collection. If the collection is empty, this returns true.
	 * 
	 * @param items - a collection of objects to be checked for containment in this set
	 * @return true if all items are contained in this set
	 */
	public boolean containsAll(Collection<? extends E> items);
	
	/**
	 * Returns all of the elements in the set in an array.
	 * 
	 * @return array containing all elements in the set
	 */
	public Object[] toArray();

	/**
	 *
	 * The size is the number of elements contained in the set.
	 * 
	 * @return the number of elements
	 */
	public int size();

	/**
	 * The set is empty if it contains no elements.
	 * 
	 * @return true if this set is empty, false otherwise
	 */
	public boolean isEmpty();

	/**
	 * Removes all of the elements from this set. The set will be
	 * empty when this call returns.
	 */
	public void clear();
}