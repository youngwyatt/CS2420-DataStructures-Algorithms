package assign10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FindKLargestTest {

    private List<Integer> list;
    private List<String> stringList;
    private Comparator<String> cmp;

    @BeforeEach
    void setUp() {
        Random rng = new Random();
        list = new ArrayList<>();
        stringList = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        };

    }

    @Test
    void findKLargestHeapTest() {
        List<Integer> test = FindKLargest.findKLargestHeap(list, 10);
        List<Integer> expected = new ArrayList<>();
        for (int i = 999; i > 989; i--) {
            expected.add(i);
        }
        Integer[] testArray = test.toArray(new Integer[ 0 ]);
        Integer[] expectedArray = expected.toArray(new Integer[ 0 ]);
        assertArrayEquals(expectedArray, testArray);
    }

    @Test
    void findKLargestDupes() {
        List<Integer> dupeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dupeList.add(10);
        }
        List<Integer> testList = FindKLargest.findKLargestHeap(dupeList, 5);
        Integer[] test = testList.toArray(new Integer[ 0 ]);
        Integer[] expected = new Integer[]{10, 10, 10, 10, 10};
        assertArrayEquals(expected, test);
    }

    @Test
    void findKLargestSomeDupes() {
        List<Integer> dupeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dupeList.add(10);
        }
        dupeList.add(11);
        dupeList.add(11);
        dupeList.add(12);
        List<Integer> testList = FindKLargest.findKLargestHeap(dupeList, 5);
        Integer[] test = testList.toArray(new Integer[ 0 ]);
        Integer[] expected = new Integer[]{12, 11, 11, 10, 10};
        assertArrayEquals(expected, test);
    }

    @Test
    void testFindKLargestHeapCustomCMP() {
        stringList.add("E");
        stringList.add("DD");
        stringList.add("CCC");
        stringList.add("BBBB");
        stringList.add("AAAAA");
        List<String> testList = FindKLargest.findKLargestHeap(stringList, 3, cmp);
        String[] test = testList.toArray(new String[ 0 ]);
        String[] expected = new String[]{"AAAAA", "BBBB", "CCC"};
        assertArrayEquals(expected, test);

    }

    @Test
    void testFindKLargestHeapCustomCMPandDupes() {
        stringList.add("E");
        stringList.add("DD");
        stringList.add("CCC");
        stringList.add("BBBB");
        stringList.add("AAAAA");
        stringList.add("FFFFF");
        List<String> testList = FindKLargest.findKLargestHeap(stringList, 3, cmp);
        String[] test = testList.toArray(new String[ 0 ]);
        String[] expected = new String[]{"AAAAA", "FFFFF", "BBBB"};
        assertArrayEquals(expected, test);

    }

    @Test
    void findKLargestSort() {
        List<Integer> test = FindKLargest.findKLargestSort(list, 10);
        List<Integer> expected = new ArrayList<>();
        for (int i = 999; i > 989; i--) {
            expected.add(i);
        }
        Integer[] testArray = test.toArray(new Integer[ 0 ]);
        Integer[] expectedArray = expected.toArray(new Integer[ 0 ]);
        assertArrayEquals(expectedArray, testArray);
    }

    @Test
    void testFindKLargestSortCustomCMP() {
        stringList.add("E");
        stringList.add("DD");
        stringList.add("CCC");
        stringList.add("BBBB");
        stringList.add("AAAAA");
        List<String> testList = FindKLargest.findKLargestSort(stringList, 3, cmp);
        String[] test = testList.toArray(new String[ 0 ]);
        String[] expected = new String[]{"AAAAA", "BBBB", "CCC"};
        assertArrayEquals(expected, test);
    }

    @Test
    void FindKLargestExcpetions(){
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestHeap(list, -1) );
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestHeap(list, 1069));
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestHeap(stringList, -1, cmp) );
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestHeap(stringList, 1069, cmp));
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestSort(list, -1) );
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestSort(list, 1069));
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestSort(stringList, -1, cmp) );
        assertThrows(IllegalArgumentException.class, () -> FindKLargest.findKLargestSort(stringList, 1069, cmp));
    }
}