package assign10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BinaryMaxHeapTest {
    private BinaryMaxHeap<Integer> intHeap;
    private List<Integer> list;
    private List<String> stringList;

    @SuppressWarnings("unchecked")
    private <T> int compare(Object a, Object b) {
        return ((Comparable<T>) a).compareTo((T) b);
    }

    private Comparator<String> cmp;

    @BeforeEach
    void setUp() {
        intHeap = new BinaryMaxHeap<Integer>();
        Random rng = new Random();
        list = new ArrayList<>();
        stringList = new ArrayList<String>();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 100; i++) {
            intHeap.add(i);
            list.add(i);
            int randomLength = 5 + rng.nextInt(11);
            StringBuilder sb = new StringBuilder(randomLength);
            for (int j = 0; j < randomLength; j++) {
                sb.append(characters.charAt(rng.nextInt(characters.length())));
            }
            stringList.add(sb.toString());
        }

        cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };

    }

    @Test
    void buildHeap() {
        for (int k = 0; k < 10000; k++) {
            Collections.shuffle(list);
            BinaryMaxHeap<Integer> heap = new BinaryMaxHeap<>(list);
            Object[] Heap = heap.toArray();
            for (int i = 0; i < Heap.length / 2; i++) {
                int left = 2 * i + 1;
                int right = left + 1;
                if (left < heap.size()) {
                    Integer curr = (Integer) Heap[ i ];
                    Integer leftChild = (Integer) Heap[ left ];
                    assertTrue(compare(curr, leftChild) > 0);
                }
                if (right < heap.size()) {
                    Integer curr = (Integer) Heap[ i ];
                    Integer rightChild = (Integer) Heap[ right ];
                    assertTrue(compare(curr, rightChild) > 0);
                }
            }
        }
    }

    @Test
    void buildHeapCustomCompare() {
        BinaryMaxHeap<String> test = new BinaryMaxHeap<>(cmp);
        test.add("A");
        test.add("AAA");
        test.add("AA");
        assertEquals(3, test.size());
        assertEquals("AAA", test.peek());
        test.add("AAA");
        assertEquals("AAA", test.peek());
        assertEquals(4, test.size());
    }

    @Test
    void buildHeapFromListCustomCompare() {
        BinaryMaxHeap<String> test = new BinaryMaxHeap<>(stringList, cmp);
        assertEquals(100, test.size());
        assertEquals(15, test.peek().length());
    }

    @Test
    void add() {
        intHeap.add(101);
        assertEquals(101, intHeap.size());
        assertEquals(101, intHeap.peek());
    }

    @Test
    void addDupes() {
        intHeap.add(102);
        intHeap.add(102);
        assertEquals(102, intHeap.size());
        assertEquals(102, intHeap.peek());
    }

    @Test
    void addNeg() {
        intHeap.add(-1);
        assertEquals(101, intHeap.size());
    }

    @Test
    void addEmpty() {
        intHeap.clear();
        intHeap.add(-69);
        assertEquals(-69, intHeap.peek());
    }

    @Test
    void peek() {
        assertEquals(99, intHeap.peek());
    }

    @Test
    void peekEmpty() {
        intHeap.clear();
        assertThrows(NoSuchElementException.class, () -> intHeap.peek());
    }

    @Test
    void peekClearAddRemove() {
        intHeap.clear();
        intHeap.add(1);
        intHeap.extract();
        assertEquals(0, intHeap.size());
        intHeap.add(2);
        assertEquals(2, intHeap.peek());
    }

    @Test
    void peekSmall() {
        intHeap.clear();
        intHeap.add(2);
        intHeap.add(1);
        intHeap.add(3);
        assertEquals(3, intHeap.peek());
    }

    @Test
    void extract() {
        intHeap.add(105);
        assertEquals(intHeap.extract(), 105);
        intHeap.clear();
        assertThrows(NoSuchElementException.class, () -> intHeap.extract());
        intHeap.add(-12);
        assertEquals(intHeap.extract(), -12);
    }

    @Test
    void size() {
        assertEquals(intHeap.size(), 100);
        intHeap.clear();
        assertEquals(intHeap.size(), 0);
    }

    @Test
    void isEmpty() {
        assertFalse(intHeap.isEmpty());
        intHeap.clear();
        assertTrue(intHeap.isEmpty());
    }

    @Test
    void toArray() {
        intHeap.clear();
        Comparable[] array = new Comparable[]{8, 7, 6, 4, 3, 2, 5, 1};
        intHeap.add(1);
        intHeap.add(2);
        intHeap.add(3);
        intHeap.add(4);
        intHeap.add(5);
        intHeap.add(6);
        intHeap.add(7);
        intHeap.add(8);
        assertArrayEquals(array, intHeap.toArray());
    }

    @Test
    void toArrayEmpty() {
        intHeap.clear();
        Comparable[] array = new Comparable[]{};
        assertArrayEquals(array, intHeap.toArray());
    }

    @Test
    void toArrayCustomCmp() {
        BinaryMaxHeap<String> test = new BinaryMaxHeap<>(cmp);
        test.add("A");
        test.add("AA");
        test.add("AAA");
        test.add("AAA");
        test.add("AAAA");
        String[] expected = new String[]{"AAAA", "AAA", "AA", "A", "AAA"};
        assertArrayEquals(expected, test.toArray());
    }
}