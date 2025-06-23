package assign03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ArraySetTest {
    private ArraySet<Double> set;
    private ArraySet<String> setWithComparator;
    @BeforeEach
    public void setUp()
    {
        // Initialize an ArraySet with natural ordering
        set = new ArraySet<>();

        // Initialize an ArraySet with a custom comparator for strings (case insensitive)
        Comparator<String> caseInsensitiveComparator = String::compareToIgnoreCase;
        setWithComparator = new ArraySet<>(caseInsensitiveComparator);
    }
    // Add method tests
    @Test
    public void addReg()
    {
        set.add(12.0);
        set.add(0.0);
        set.add(1.1);
        set.add(2.2);
        assertTrue(set.contains(12.0));
        assertTrue(set.contains(0.0));
        assertTrue(set.contains(1.1));
        assertTrue(set.contains(2.2));
        assertFalse(set.contains(3.2));

    }
    @Test
    public void addDuplicate()
    {
        set.add(12.0);
        set.add(0.0);
        set.add(1.1);
        set.add(2.2);
        set.add(2.2);
        assertTrue(set.contains(12.0));
        assertTrue(set.contains(0.0));
        assertTrue(set.contains(1.1));
        assertTrue(set.contains(2.2));
        assertEquals(4,set.size(),0.000001);
    }

    @Test
    public void addEarlyDuplicate() {
        set.add(12.0);
        set.add(12.0);
        set.add(1.1);
        set.add(1.1);
        set.add(2.2);
        assertEquals(3, set.size());
    }


    @Test
    public void addWithResizing()
    {
        int times = 100000;
        for(int i = 0; i < times; i++)
        {
            set.add((double) i);
        }
        //check duplication
        times = 200;
        for(int i = 0; i < times; i++)
        {
            set.add((double) i);
        }
        assertEquals(100000, set.size());
    }

    @Test
    public void duplicatesDontResize (){
        set.add(12.0);
        set.add(2.0);
        set.add(3.0);
        set.add(3.0);
        assertEquals(set.size(), 3);
    }

    //remove tests
    @Test
    public void removeReg() {
        set.add(12.0);
        set.add(0.0);
        set.add(1.1);
        set.add(2.2);
        set.remove(1.1);
        assertTrue(set.contains(12.0));
        assertTrue(set.contains(0.0));
        assertFalse(set.contains(1.1));
        assertTrue(set.contains(2.2));

    }
    @Test
    public void removeIfNotInSet() {
        set.add(12.0);
        set.add(0.0);
        set.add(2.2);
        assertNull(set.remove(1.1));
    }
    @Test
    public void removeSmol() {
        set.add(12.0);
        set.add(0.0);
        set.add(2.2);
        set.remove(12.0);
        assertEquals(set.size(), 2);
    }
    //contains testing
    @Test
    public void containsDoes() {
        set.add(12.0);
        assertTrue(set.contains(12.0));
    }
    @Test
    public void containsDoesnt() {
        set.add(12.0);
        set.add(0.0);
        set.add(3.0);
        assertFalse(set.contains(2.2));
    }
    //containsAll testing
    @Test
    public void containsAllReg()
    {
        Collection<Double> collection = new ArrayList<>();
        collection.add(1.0);
        collection.add(2.0);
        collection.add(3.0);
        collection.add(4.0);
        collection.add(5.0);
        set.add(12.0);
        set.add(4.0);
        set.add(1.0);
        set.add(2.2);
        set.add(2.2);
        set.add(12.0);
        set.add(2.0);
        set.add(1.1);
        set.add(3.0);
        set.add(5.0);
        assertTrue(set.containsAll(collection));
        collection.add(6.0);
        assertFalse(set.containsAll(collection));
    }
    //toArray testing
    @Test
    public void toArrayPlusStrings()
    {
        ArraySet<String> set = new ArraySet<>();
        set.add("this");
        set.add("Is");
        set.add("brutal");

        // Convert to array and check contents
        Object[] array = set.toArray();
        assertArrayEquals(new Object[]{"Is", "brutal", "this"}, array);
    }
    @Test
    public void toArrayPlusDoubles()
    {
        set.add(3.0);
        set.add(80.4);
        set.add(1.0);
        // Convert to array and check contents
        Object[] array = set.toArray();
        assertArrayEquals(new Object[]{1.0, 3.0, 80.4}, array);
    }
    //empty testing
    @Test
    public void isEmptyItIs() {
        assertTrue(set.isEmpty());
    }
    @Test
    public void isEmptyTisnt() {
        set.add(1.1);
        assertFalse(set.isEmpty());
    }
    // clear testing
    @Test
    public void clear() {
        set.add(12.0);
        set.add(0.0);
        set.add(2.2);
        set.clear();
        assertEquals(set.size(), 0);
    }

}