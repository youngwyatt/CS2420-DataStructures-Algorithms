package assign10;

import java.util.NoSuchElementException;

/**
 * This interface represents the priority queue abstract data type,
 * defining the operations and running times expected of any data
 * structure used to implement a priority queue.
 * <p>
 * NOTE: The item with the highest priority is the "maximum" item.
 *
 * @param <E>
 * @author Prof. Parker
 * @version July 11, 2024
 */
public interface PriorityQueue<E> {

    /**
     * Adds the given item to this priority queue.
     * O(1) in the average case, O(log N) in the worst case
     *
     * @param item - to be added to this priority queue
     */
    public void add(E item);

    /**
     * Returns, but does not remove, the highest priority item this priority queue.
     * O(1)
     *
     * @return the highest priority item
     * @throws NoSuchElementException if this priority queue is empty
     */
    public E peek() throws NoSuchElementException;

    /**
     * Extracts (removes) the highest priority item this priority queue.
     * O(log N)
     *
     * @return the highest priority item
     * @throws NoSuchElementException if this priority queue is empty
     */
    public E extract() throws NoSuchElementException;

    /**
     * Determines the number of items in this priority queue.
     * O(1)
     *
     * @return the number of items in this priority queue
     */
    public int size();

    /**
     * Determines if this priority queue is empty.
     * O(1)
     *
     * @return true if this priority queue is empty, false otherwise
     */
    public boolean isEmpty();

    /**
     * Empties this priority queue of items.
     * O(1)
     */
    public void clear();

    /**
     * Generates an array of the items in this priority queue,
     * in the same order they appear in the backing array.
     * O(N)
     * <p>
     * (NOTE: This method is needed for grading purposes. The root item
     * must be stored at index 0 in the returned array, regardless of
     * whether it is in stored there in the backing array.)
     *
     * @return array of items in this priority queue
     */
    public Object[] toArray();
}
