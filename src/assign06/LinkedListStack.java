package assign06;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic stack implementation backed by a singly-linked list.
 *
 * @author Brian Keller & Wyatt Young
 * @version June 16, 2024
 */
public class LinkedListStack<E> implements Stack<E>
{
    private SinglyLinkedList<E> list;
    /**
     * Constructs an empty stack.
     */
    public LinkedListStack()
    {
        list = new SinglyLinkedList<>();
    }
    /**
     * Removes all of the elements from the stack.
     */
    @Override
    public void clear()
    {
        list.clear();
    }
    /**
     * @return true if the stack contains no elements; false, otherwise.
     */
    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }
    /**
     * Returns, but does not remove, the element at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E peek() throws NoSuchElementException
    {
        if(list.isEmpty()){throw new NoSuchElementException();}
        return list.getFirst();
    }
    /**
     * Returns and removes the item at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public E pop() throws NoSuchElementException
    {
        if(list.isEmpty()){throw new NoSuchElementException();}
        return list.deleteFirst();
    }
    /**
     * Adds a given element to the stack, putting it at the top of the stack.
     *
     * @param element - the element to be added
     */
    @Override
    public void push(E element)
    {list.insertFirst(element);
    }
    /**
     * @return the number of elements in the stack
     */
    @Override
    public int size()
    {
        return list.size();
    }
    /**
     * returns an iterator for the stack
     *
     * @return an iterator
     */
    Iterator<E> iterator(){
        return list.iterator();
    }
}
