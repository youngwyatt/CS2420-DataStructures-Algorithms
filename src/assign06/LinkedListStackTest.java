package assign06;

import assign04.AnagramChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListStackTest
{
    private LinkedListStack<Edit> stack;
    @BeforeEach
    void setUp()
    {
        stack = new LinkedListStack<>();
    }
    @Test
    void clear()
    {
        Edit edit1 = new Edit('a', 0, Edit.INSERT);
        Edit edit2 = new Edit('b', 1, Edit.INSERT);
        Edit edit3 = new Edit('c', 2, Edit.INSERT);
        Edit edit4 = new Edit('d',3,Edit.INSERT);
        stack.push(edit1);
        stack.push(edit2);
        stack.push(edit3);
        stack.push(edit4);
        stack.clear();
        assertTrue(stack.isEmpty());
        stack.push(edit1);
        assertFalse(stack.isEmpty());
    }
    @Test
    void isEmpty()
    {
        assertTrue(stack.isEmpty());
        stack.push(new Edit('a',0,Edit.INSERT));
        assertFalse(stack.isEmpty());
    }
    @Test
    void peek()
    {
        Edit edit1 = new Edit('a', 0, Edit.INSERT);
        Edit edit2 = new Edit('b', 1, Edit.INSERT);
        Edit edit3 = new Edit('c', 2, Edit.INSERT);
        Edit edit4 = new Edit('d',3,Edit.INSERT);
        stack.push(edit1);
        stack.push(edit2);
        stack.push(edit3);
        stack.push(edit4);
        Edit element = stack.peek();
        assertEquals(element,edit4);
        assertEquals(4,stack.size());
        stack.pop();
        assertEquals(stack.peek(),edit3);
    }
    @Test
    void peekEmpty()
    {
        assertThrows(NoSuchElementException.class, () -> {stack.peek();});
    }
    @Test
    void pop()
    {
        Edit edit1 = new Edit('a', 0, Edit.INSERT);
        Edit edit2 = new Edit('b', 1, Edit.INSERT);
        Edit edit3 = new Edit('c', 2, Edit.INSERT);
        Edit edit4 = new Edit('d',3,Edit.INSERT);
        stack.push(edit1);
        stack.push(edit2);
        stack.push(edit3);
        stack.push(edit4);
        Edit edit = stack.pop();
        assertEquals(edit4, edit);
        assertEquals(3,stack.size());
    }
    @Test
    void popEmpty()
    {
        assertThrows(NoSuchElementException.class, () -> {stack.pop();});
    }
    @Test
    void push()
    {
        Edit edit1 = new Edit('a', 0, Edit.INSERT);
        Edit edit2 = new Edit('b', 1, Edit.INSERT);
        Edit edit3 = new Edit('c', 2, Edit.INSERT);
        Edit edit4 = new Edit('d',3,Edit.INSERT);
        stack.push(edit1);
        stack.push(edit2);
        stack.push(edit3);
        stack.push(edit4);
        assertEquals(edit4,stack.pop());
        stack.push(edit1);
        assertEquals(edit1,stack.pop());
    }
    @Test
    void size()
    {
        Edit edit1 = new Edit('a', 0, Edit.INSERT);
        Edit edit2 = new Edit('b', 1, Edit.INSERT);
        Edit edit3 = new Edit('c', 2, Edit.INSERT);
        Edit edit4 = new Edit('d',3,Edit.INSERT);
        stack.push(edit1);
        stack.push(edit2);
        stack.push(edit3);
        stack.push(edit4);
        assertEquals(stack.size(), 4);
    }
}