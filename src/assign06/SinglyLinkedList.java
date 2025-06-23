package assign06;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * A generic singly-linked list implementation of the List interface.
 *
 * @author Brian Keller & Wyatt Young
 * @version June 16, 2024
 *
 */
public class SinglyLinkedList<E> implements List<E>
{
    private Node head;
    private int size;
    class Node{
        public E data;
        public Node next;
        public Node(E item, Node next){
            this.data = item;
            this.next = next;
        }
    }
    /**
     * Constructs empty list
     */
    public SinglyLinkedList()
    {
        head = null;
        size = 0;
    }
    /**
     * Inserts an element at the beginning of the list.
     * O(1) for a singly-linked list.
     *
     * @param element - the element to add
     */
    @Override
    public void insertFirst(E element) {
        head = new Node(element, head);
        size++;
    }
    /**
     * Inserts an element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index   - the specified position
     * @param element - the element to add
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size())
     */
    @Override
    public void insert(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            insertFirst(element);
            return;
        }
        Node current = head;
        int counter = 0;
        while (current != null && counter < index - 1) {
            current = current.next;
            counter++;
        }
        if (current == null || current.next == null && counter < index - 1) {
            throw new IndexOutOfBoundsException();
        }
        current.next = new Node(element, current.next);
        size++;
    }
    /**
     * Gets the first element in the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element in the list
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E getFirst() throws NoSuchElementException {
        if(size==0)throw new NoSuchElementException();
        return head.data;
    }
    /**
     * Gets the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        Node current = head;
        int counter = 0;
        while (current != null && counter < index) {
            current = current.next;
            counter++;
        }
        assert current != null;
        return current.data;
    }
    /**
     * Deletes and returns the first element from the list.
     * O(1) for a singly-linked list.
     *
     * @return the first element
     * @throws NoSuchElementException if the list is empty
     */
    @Override
    public E deleteFirst() throws NoSuchElementException
    {
        if(head==null){
            throw new NoSuchElementException();
        }
        E deleted = head.data;
        head = head.next;
        size--;
        return deleted;
    }

    /**
     * Deletes and returns the element at a specific position in the list.
     * O(N) for a singly-linked list.
     *
     * @param index - the specified position
     * @return the element at the position
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    @Override
    public E delete(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= size()) throw new IndexOutOfBoundsException();
        E deleted;
        if(index == 0){
            deleted = deleteFirst();
            return deleted;
        }
        Node current = head;
        for(int i = 0; i < index-1; i++){
            current = current.next;
        }
        deleted = current.next.data;
        current.next = current.next.next;
        size--;
        return deleted;
    }
    /**
     * Determines the index of the first occurrence of the specified element in the list,
     * or -1 if this list does not contain the element.
     * O(N) for a singly-linked list.
     *
     * @param element - the element to search for
     * @return the index of the first occurrence; -1 if the element is not found
     */
    @Override
    public int indexOf(E element) {
        Node current = head;
        int index = 0;
        while(current != null){
            if(current.data.equals(element))return index;
            current = current.next;
            index++;
        }
        return -1;
    }
    /**
     * O(1) for a singly-linked list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }
    /**
     * O(1) for a singly-linked list.
     *
     * @return true if this collection contains no elements; false, otherwise
     */
    @Override
    public boolean isEmpty() {
        return head == null;
    }
    /**
     * Removes all of the elements from this list.
     * O(1) for a singly-linked list.
     */
    @Override
    public void clear() {
        head = null;
        size = 0;
    }
    /**
     * Generates an array containing all of the elements in this list in proper sequence
     * (from first element to last element).
     * O(N) for a singly-linked list.
     *
     * @return an array containing all of the elements in this list, in order
     */
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        Node current = head;
        for(int i = 0; i < size(); i++){
            arr[i] = current.data;
            current = current.next;
        }
        return arr;
    }
    public Iterator<E> iterator()
    {
        return new singlyLinkedListIterator();
    }
    /**
     * nested iterator class
     */
    public class singlyLinkedListIterator implements Iterator<E>
    {
        private Node current = head;
        private Node returned = null;
        private boolean next = false;
        /**
         * hasNext function for iterator
         * @return true if iterator has next item, false o/w
         */
        public boolean hasNext()
        {
            return current != null;
        }
        /**
         * next function for iterator
         * @return next item in list
         */
        public E next()
        {
            if(!hasNext()){throw new NoSuchElementException();}
            next = true;
            returned = current;
            current = current.next;
            return returned.data;
        }
        /**
         * remove function for iterator
         * removes item last returned by next
         */
        public void remove()
        {
            if(!next){throw new IllegalStateException();}
            SinglyLinkedList.this.delete(indexOf(returned.data));
            next = false;
        }
    }
}
