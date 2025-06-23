package comprehensive;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * A generic stack implementation backed by an array list.
 *
 * @author Brian Keller & Wyatt Young
 * @version July 24, 2024
 */

public class ArrayStack<E> {
    private ArrayList<E> stack;
    private int size;

    /**
     * constructs an empty stack
     */
    public ArrayStack(){
        stack = new ArrayList<>();
    }

    /**
     * removes all elements from the stack
     */
    public void clear() {
        stack.clear();
    }

    /**
     * checks if the stack is empty
     * @returns true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    /**
     * Returns the element at the top of the stack
     * @returns the top element of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    public E peek() throws NoSuchElementException {
        if(isEmpty()){throw new NoSuchElementException("Stack is empty");}
        return stack.get(stack.size()-1);
    }

    /**
     * Returns and removes the element at the top of the stack
     * @returns the top element of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    public E pop() throws NoSuchElementException {
        if(isEmpty()){throw new NoSuchElementException("Stack is empty");}
        E temp = stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        size--;
        return temp;
    }

    /**
     * Adds the specified element to the stack, putting at the top of the stack (or the end of the backing array)
     * @param element - the element to be added
     */
    public void push(E element) {
        stack.add(element);
        size++;
    }
    /**
     * adds list of elements to stack in reverse order
     * @param elements list of elements to be pushed onto stack
     */
    public void pushAllReverse(List<E> elements)
    {
        for(int i = elements.size() - 1; i >= 0; i--)
        {
            stack.add(elements.get(i));
            size++;
        }
    }

    /**
     * getter for the size of the stack
     * @return returns the number of elements in the stack
     */
    public int size() {
        return size;
    }
}
