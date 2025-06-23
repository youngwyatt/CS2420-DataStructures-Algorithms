package lab05;

import assign03.Set;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArraySet<E> implements Set<E>, Iterable<E>
{
    private E[] elements; // this array set
    private int size; //size of array, number of elements(not capacity)
    private Comparator<? super E> comparator;

    /**
     * constructor, one of two for ArraySet class.
     * assumes elements are ordered using their natural ordering
     */
    public ArraySet(){
        elements = (E[]) new Object[5];
        comparator = null;
        size = 0;
    }

    /**
     * second constructor that is used when elements are ordered using the provided
     * Comparator object.
     * compare method will return 0 only when the two items are equal according
     * to their equals method
     */
    public ArraySet(Comparator<? super E> cmp){
        elements = (E[]) new Object[5];
        comparator = cmp;
        size = 0;
    }


    /**
     * Adds an element to the set.
     * @param item - the element to add
     */
    @Override
    public void add(E item)
    {
       //start the array
        if(size == 0){
            elements[0] = item;
            size++;
            return;
        }
        if(size == 1 && compare(elements[0], item) != 0){
            int comparison = compare(elements[0],item);
            if(comparison < 0){
                elements[1] = item;
            }
            else{
                elements[1] = elements[0];
                elements[0] = item;
            }
            size++;
            return;
        }
        // check duplicates
        if(contains(item))
        {
            return;
        }

        // if array is at max size, double it in size
        if(size == elements.length)
        {
          resize();
        }
        // add item at correct index
        int index = binarySearch(item);
        if(index < 0){
            index = -index-1;
        }
        // shift items to the right
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = item;
        size ++;
    }

    /**
     * Removes and returns an element equal to the given item if
     * such an element is present.
     *
     * @param item - an object equal to the element to be removed
     * @return the element that was removed or null if it isn't present
     */
    @Override
    public E remove(E item)
    {
        // return null if item not in set
        if(!contains(item))
        {
            return null;
        }
        // extract index of item
        int index = binarySearch(item);
        // shift elements back in set to maintain ordering
        for(int i = index; i < size-1; i++)
        {
            elements[i] = elements[i + 1];
        }
        elements[size-1] = null;
        size--;
        return item;
    }

    /**
     * Indicates whether this set contains an element equal to the
     * given item.
     *
     * @param item - the object to be checked for containment in this set
     * @return true if the item is contained in this set
     */
    @Override
    public boolean contains(E item)
    {
        if(binarySearch(item) >= 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Indicates whether this set contains all of the elements in
     * the collection. If the collection is empty, this returns true.
     *
     * @param items - a collection of objects to be checked for containment in this set
     * @return true if all items are contained in this set
     */
    @Override
    public boolean containsAll(Collection<? extends E> items)
    {
        // loop through each item in collection
        for(E item: items)
        {
            if (!contains(item))
            {
                // if one item in collection isn't found return false
                return false;
            }
        }
        // if all found return true
        return true;
    }

    /**
     * Returns all of the elements in the set in an array.
     *
     * @return array containing all elements in the set
     */
    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        for(int i = 0; i < size(); i++){
            array[i] = elements[i];
        }
        return array;
    }

    /**
     * The size is the number of elements contained in the set.
     *
     * @return the number of elements
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * The set is empty if it contains no elements.
     *
     * @return true if this set is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        if(size <= 0)
        {
            return true;
        }
        return false;
    }

    /**
     * Removes all of the elements from this set. The set will be
     * empty when this call returns.
     */
    @Override
    public void clear()
    {
        elements = (E[])new Object[elements.length];
        size = 0;
    }
    /**
     * Private helper method for binary search algorithm
     */
    private int binarySearch(E item)
    {
        //Binary search algo
        int left = 0;
        int right = size-1;
        while(left <= right)
        {
            int center = left + (right - left) / 2;
            if(compare(elements[center],item) == 0)
            {
                return center;//center is item
            }
            else if(compare(elements[center],item) < 0)
            {
                left = center + 1;
            }
            else
            {
                right = center - 1;
            }
        }
        return -left-1;
    }
    /**
     * private comparison method for establishing means of comparison
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        } else {
            return ((Comparable<? super E>) e1).compareTo(e2);
        }
    }
    /**
     * Resizes arraylist by doubling its length
     */
    private void resize() {
        E[] temp = (E[]) new Object[elements.length * 2];
        System.arraycopy(elements, 0, temp, 0, elements.length);
        elements = temp;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new nested();
    }
    private class nested implements Iterator<E>
    {
        private int index = 0;
        private boolean next = false;
        @Override
        public boolean hasNext()
        {
            return index < size;
        }

        @Override
        public E next()
        {
            if(!hasNext()){throw new NoSuchElementException();}
            next = true;
            return elements[index++];
        }

        public void remove()
        {
            if(!next){throw new IllegalStateException();}
            ArraySet.this.remove(elements[--index]);
            index --;
            next = false;
        }
    }
}
