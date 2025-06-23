package assign10;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class represents a Binary Max Heap backed by an array and implements the
 * PriorityQueue class. Used to find the k largest items in a list.
 *
 * @author Brian Keller & Wyatt Young
 * @version July 14, 2024
 */
public class BinaryMaxHeap<E> implements PriorityQueue<E> {
    private E[] array;
    private int size;
    private Comparator<? super E> cmp;

    /**
     * Constructor that assumes elements are ordered using their natural ordering
     */
    public BinaryMaxHeap() {
        this.array = (E[]) new Object[10];
        this.size = 0;
    }

    /**
     * Constructor that assumes elements are ordered using provided Comparator object
     *
     * @param cmp Comparator object that defines unique ordering of elements
     */
    public BinaryMaxHeap(Comparator<? super E> cmp) {
        this();
        this.cmp = cmp;
    }

    /**
     * Constructor that builds Binary max Heap from given list, assumes elements are
     * ordered using natural ordering
     * Uses buildHeap function to build binary heap in O(N) time
     */
    public BinaryMaxHeap(List<? extends E> list) {
        this();
        buildHeap(list);
    }

    /**
     * Constructor that builds Binary Max Heap from provided list as well as orders based on
     * provided comparator object
     *
     * @param cmp  Comparator object that defines unique ordering of elements
     * @param list of E elements to be inserted into heap
     */
    public BinaryMaxHeap(List<? extends E> list, Comparator<? super E> cmp) {
        this();
        this.cmp = cmp;
        buildHeap(list);
    }

    /**
     * Adds the given item to this priority queue.
     * O(1) in the average case, O(log N) in the worst case
     *
     * @param item - to be added to this priority queue
     */
    @Override
    public void add(E item) {
        // structure
        if (size + 1 >= this.array.length) {
            resizeArray();
        }
        this.array[size + 1] = item;
        size++;
        // order
        percolateUp(size);
    }

    /**
     * Returns, but does not remove, the highest priority item this priority queue.
     * O(1)
     *
     * @return the highest priority item
     * @throws NoSuchElementException if this priority queue is empty
     */
    @Override
    public E peek() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        return this.array[1];
    }

    /**
     * Extracts (removes) the highest priority item this priority queue.
     * O(log N)
     *
     * @return the highest priority item
     * @throws NoSuchElementException if this priority queue is empty
     */
    @Override
    public E extract() throws NoSuchElementException {
        if (this.size == 0) {
            throw new NoSuchElementException("Queue is empty!");
        }
        E root = this.array[1];
        // fulfill structure
        swapItems(1, size);
        size--;
        // fulfill order
        percolateDown(1);
        return root;
    }

    /**
     * Determines the number of items in this priority queue.
     * O(1)
     *
     * @return the number of items in this priority queue
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Determines if this priority queue is empty.
     * O(1)
     *
     * @return true if this priority queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Empties this priority queue of items.
     * O(1)
     */
    @Override
    public void clear() {
        this.array = (E[]) new Object[ size ];
        size = 0;
    }

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
    @Override
    public E[] toArray() {
        Object[] arr = new Object[this.size];
        System.arraycopy(this.array, 1, arr, 0, this.size);
        return (E[]) arr;
    }

    /**
     * buildHeap private helper method.
     * Constructs a Binary heap from a given list of elements in O(N) time
     *
     * @param list of elements to be inserted into heap
     */
    private void buildHeap(List<? extends E> list) {
        //fulfill structure
        while (list.size() >= this.array.length) {
            this.resizeArray();
        }
        for (int i = 0; i < list.size(); i++) {
            this.array[i + 1] = list.get(i);
            this.size++;
        }
        //fulfill order
        for (int i = size / 2; i > 0; i--) {
            this.percolateDown(i);
        }

    }

    /**
     * private helper method to perform "up percolation" i.e proper swaps to determine
     * placing of an item in heap.
     *
     * @param index of item to be moved
     */
    private void percolateUp(int index) {
        int parent = index / 2;
        while ((index > 1 && innerCompare(this.array[ index ], this.array[ parent ]) > 0)) {
            swapItems(index, parent);
            index = parent;
            parent = index / 2;
        }
    }

    /**
     * private helper method to perform "down percolation" i.e proper swaps to determine
     * placing of an item in heap.
     *
     * @param index of item to be moved
     */
    private void percolateDown(int index) {
        int largerChild;
        E temp = array[index];

        for (; index * 2 <= size; index = largerChild) {
            int leftChild = index * 2;
            int rightChild = leftChild + 1;
            largerChild = leftChild;
            if (leftChild != size && innerCompare(array[rightChild], array[leftChild]) > 0) {
                largerChild = rightChild;
            }
            if (innerCompare(array[largerChild], temp) > 0) {
                array[index] = array[largerChild];
            } else {
                break;
            }
        }
        array[index] = temp;
    }

    /**
     * private helper method used to determine whether to use Comparable compareTo
     * or a Comparator passed in.
     *
     * @param left  item to be compared
     * @param right item to be compared
     */
    private int innerCompare(E left, E right) {
        if (this.cmp != null) {
            return cmp.compare(left, right);
        } else {
            return (((Comparable<? super E>) left).compareTo(right));
        }
    }

    /**
     * private helper method for swapping the contents of two indices
     *
     * @param index1 first index to swap
     * @param index2 first index to swap
     */
    private void swapItems(int index1, int index2) {
        E temp = this.array[index1];
        this.array[index1] = this.array[index2];
        this.array[index2] = temp;
    }

    /**
     * private helper method for resizing array
     */
    private void resizeArray() {
        E[] newArray = (E[]) new Object[ this.array.length * 2 ];
        System.arraycopy(this.array, 1, newArray, 1, size);
        this.array = newArray;
    }

}
