package assign06;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SinglyLinkedListTest {
    private SinglyLinkedList<Object> test = new SinglyLinkedList<>();

    @Test
    void insertFirstEmpty() {
        test.insertFirst(1);
        assertEquals(1, test.getFirst());
        assertEquals(1, test.size());
    }

    @Test
    void insertFirstMultiple(){
        test.insertFirst(1);
        test.insertFirst(2);
        assertEquals(2, test.getFirst());
        assertEquals(2, test.size());
    }

    @Test
    void insertEnd() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.insert(2, 3);
        assertEquals(3, test.get(2));
        assertEquals(3, test.size());
    }

    @Test
    void insertMid(){
        test.insertFirst(1);
        test.insertFirst(2);
        test.insert(1, 3);
        assertEquals(3, test.get(1));
        assertEquals(1,test.get(2));
        assertEquals(3, test.size());
    }

    @Test
    void insertNegIndex(){
        assertThrows(IndexOutOfBoundsException.class, ()->{test.insert(-1, 2);});
    }

    @Test
    void insertTooDeep(){
        assertThrows(IndexOutOfBoundsException.class, ()->{test.insert(1,1);});

    }

    @Test
    void getFirst() {
        test.insertFirst(1);
        assertEquals(1, test.getFirst());
        assertEquals(1, test.size());
    }

    @Test
    void getFirstEmpty(){
        assertThrows(NoSuchElementException.class, ()->{test.getFirst();});
    }
    @Test
    void get() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.insertFirst(3);
        assertEquals(2, test.get(1));
    }

    @Test
    void getNegIndex(){
        test.insertFirst(1);
        assertThrows(IndexOutOfBoundsException.class, ()->{test.get(-1);});
    }

    @Test
    void getIndexTooBig(){
        test.insertFirst(1);
        assertThrows(IndexOutOfBoundsException.class, ()->{test.get(2);});
    }

    @Test
    void deleteFirst() {
        test.insertFirst(1);
        test.insertFirst(2);
        assertEquals(2, test.deleteFirst());
        assertEquals(1, test.getFirst());
        assertEquals(1, test.size());
    }

    @Test
    void deleteFirstEmpty(){
        assertThrows(NoSuchElementException.class, ()->test.deleteFirst());
    }

    @Test
    void delete() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.insertFirst(3);
        assertEquals(2, test.delete(1));
        assertEquals(1, test.get(1));
        assertEquals(2, test.size());
    }

    @Test
    void deleteNegIndex(){
        test.insertFirst(1);
        assertThrows(IndexOutOfBoundsException.class, ()->test.delete(-1));
    }

    @Test
    void deleteIndexTooBig(){
        test.insertFirst(1);
        assertThrows(IndexOutOfBoundsException.class, ()->test.delete(3));
    }

    @Test
    void indexOf() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.insertFirst(3);
        assertEquals(1, test.indexOf(2));
    }

    @Test
    void indexOfNotPresent(){
        test.insertFirst(1);
        test.insertFirst(3);
        assertEquals(-1, test.indexOf(2));
    }

    @Test
    void sizeInsertFirst() {
        test.insertFirst(1);
        test.insertFirst(2);
        assertEquals(2, test.size());
    }

    @Test
    void sizeInsert() {
        test.insert(0,1);
        test.insert(1,2);
        assertEquals(2, test.size());
    }

    @Test
    void sizeDeleteFirst() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.deleteFirst();
        assertEquals(1, test.size());
    }

    @Test
    void sizeDelete() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.delete(1);
        assertEquals(1, test.size());
    }

    @Test
    void isEmpty() {
        assertTrue(test.isEmpty());
    }

    @Test
    void isEmptyIsNot(){
        test.insertFirst(1);
        assertFalse(test.isEmpty());
    }

    @Test
    void isEmptyAfterInsertionAndDeletion(){
        test.insertFirst(1);
        test.deleteFirst();
        assertTrue(test.isEmpty());
    }

    @Test
    void isEmptyAfterDeletionAtSpecificIndex(){
        test.insertFirst(1);
        test.delete(0);
        assertTrue(test.isEmpty());
    }

    @Test
    void clear() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.insertFirst(3);
        test.clear();
        assertTrue(test.isEmpty());
        assertEquals(0, test.size());
        assertThrows(IndexOutOfBoundsException.class, ()->test.delete(1));
        assertThrows(IndexOutOfBoundsException.class, ()->test.get(1));
        assertThrows(NoSuchElementException.class, ()->{test.getFirst();});
    }

    @Test
    void toArray() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.insertFirst(3);
        Object[] arr = new Object[] {3, 2, 1};
        assertArrayEquals(arr, test.toArray());
    }

    @Test
    void toArrayString(){
        test.insertFirst("a");
        test.insertFirst("b");
        test.insertFirst("c");
        Object[] arr = new Object[]{"c","b","a"};
        assertArrayEquals(arr, test.toArray());
    }

    @Test
    void iterator() {
        test.insertFirst(1);
        test.insertFirst(2);
        test.insertFirst(3);
        Iterator<Object> iterator = test.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @Test
    void iteratorRemove(){
        test.insertFirst(1);
        test.insertFirst(2);
        test.insertFirst(3);
        Iterator<Object> iterator = test.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.remove();
        assertEquals(2, test.size());
        assertEquals(2, test.get(1));
        assertEquals(3, test.get(0));

    }
}