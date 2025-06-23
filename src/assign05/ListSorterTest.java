package assign05;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListSorterTest {

    @Test
    void mergesortDescending() {
        List<Integer> test = ListSorter.generateDescending(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        ListSorter.mergesort(test);
        assertEquals(expected, test);
    }

    @Test
    void mergesortPermuted(){
        List<Integer> test = ListSorter.generatePermuted(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        ListSorter.mergesort(test);
        assertEquals(expected, test);
    }

    @Test
    void mergesortSmall(){
        List<Integer> test = ListSorter.generateDescending(2);
        List<Integer> expected = ListSorter.generateAscending(2);
        ListSorter.mergesort(test);
        assertEquals(expected, test);
    }

    @Test
    void mergeSortDuplicates(){
        List<Integer> test = ListSorter.generateDescending(50);
        test.add(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        expected.add(50);
        ListSorter.mergesort(test);
        assertEquals(expected, test);
    }
    @Test
    void mergeSortNegs(){
        List<Integer> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test.add(-i - 1); // Start from -1 and go downwards
        }
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expected.add(-10 + i);
        }
        ListSorter.mergesort(test);
        assertEquals(expected, test);

    }

    @Test
    void mergesortSize0(){
        List<Integer> test = new ArrayList<>();
        ListSorter.mergesort(test);
        assertTrue(test.isEmpty());
    }

    @Test
    void mergeSortIsNull(){
        List<Integer> test = null;
        ListSorter.mergesort(test);
        assertNull(test);
    }


    @Test
    void quicksortDescending() {
        List<Integer> test = ListSorter.generateDescending(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        ListSorter.quicksort(test);
        assertEquals(expected, test);
    }

    @Test
    void quicksortPermutedMedian(){
        List<Integer> test = ListSorter.generatePermuted(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        ListSorter.quicksort(test);
        assertEquals(expected, test);
    }
    @Test
    void quicksortPermutedRandom(){
        List<Integer> test = ListSorter.generatePermuted(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        ListSorter.setPivotStrategy("random");
        ListSorter.quicksort(test);
        assertEquals(expected, test);
    }
    @Test
    void quicksortPermutedMiddle(){
        List<Integer> test = ListSorter.generatePermuted(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        ListSorter.setPivotStrategy("middle");
        ListSorter.quicksort(test);
        assertEquals(expected, test);
    }

    @Test
    void quicksortSmall(){
        List<Integer> test = ListSorter.generateDescending(2);
        List<Integer> expected = ListSorter.generateAscending(2);
        ListSorter.quicksort(test);
        assertEquals(expected, test);
    }

    @Test
    void quickSortDuplicates(){
        List<Integer> test = ListSorter.generateDescending(50);
        test.add(50);
        List<Integer> expected = ListSorter.generateAscending(50);
        expected.add(50);
        ListSorter.quicksort(test);
        assertEquals(expected, test);
    }
    @Test
    void quickSortNegs()
    {
        List<Integer> test = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test.add(-i - 1); // Start from -1 and go downwards
        }
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            expected.add(-10 + i);
        }
        ListSorter.quicksort(test);
        assertEquals(expected, test);

    }

    @Test
    void quicksortSize0(){
        List<Integer> test = new ArrayList<>();
        ListSorter.quicksort(test);
        assertTrue(test.isEmpty());
    }

    @Test
    void quickSortIsNull(){
        List<Integer> test = null;
        ListSorter.quicksort(test);
        assertNull(test);
    }

    @Test
    void ascendingTest(){
        List<Integer> test = ListSorter.generateAscending(6);
        System.out.println(test);
        assertEquals(6, test.size());
    }

    @Test
    void descendingTest(){
        List<Integer> test = ListSorter.generateDescending(3);
        System.out.println(test);
        assertEquals(3, test.size());
    }

    @Test
    void permutedTest(){
        List<Integer> test = ListSorter.generatePermuted(10);
        System.out.println(test);
        assertEquals(10, test.size());
    }

}