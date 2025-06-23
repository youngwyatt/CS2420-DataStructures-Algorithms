package assign04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class AnagramCheckerTest {


    @BeforeEach
    void setUp()
    {
        String test = "later";
        String testDupes = "llama";

    }

    @Test
    void sortRegular()
    {
        String test = "later";
        assertEquals("aelrt",AnagramChecker.sort(test));
    }
    @Test
    void sortAlreadySortedHaah()
    {
        String test = "aelrt";
        assertEquals("aelrt",AnagramChecker.sort(test));
    }
    @Test
    void sortDuplicates()
    {
        String test = "llama";
        assertEquals("aallm",AnagramChecker.sort(test));
    }
    @Test
    void sortWorstCase()
    {
        String test = "zyxwvutsrqponmlkjihgfedcba";
        assertEquals("abcdefghijklmnopqrstuvwxyz",AnagramChecker.sort(test));
    }
    @Test
    void sortCaseDependent()
    {
        String test = "AlAbAMa";
        assertEquals("AAAMabl",AnagramChecker.sort(test));
    }
    @Test
    void sortMixedCase()
    {
        String test = "CatErS";
        assertEquals("CESart", AnagramChecker.sort(test));
    }
    @Test
    void insertionSortWorst()
    {
        Comparator<Character> cmp = new AnagramChecker.lexicographicalCharComparator();
        Character[] test = {'z', 'y', 'x', 'w', 'v','u'};
        Character[] expected = {'u', 'v', 'w', 'x', 'y', 'z'};
        AnagramChecker.insertionSort(test, cmp);
        assertArrayEquals(expected, test);
    }
    @Test
    void insertionSortBest()
    {
        Comparator<Character> cmp = new AnagramChecker.lexicographicalCharComparator();
        Character[] test = {'u', 'v', 'w', 'x', 'y','z'};
        Character[] expected = {'u', 'v', 'w', 'x', 'y', 'z'};
        AnagramChecker.insertionSort(test, cmp);
        assertArrayEquals(expected, test);
    }
    @Test
    void insertionSortOneElement()
    {
        Comparator<Character> cmp = new AnagramChecker.lexicographicalCharComparator();
        Character[] test = {'a'};
        Character[] expected = {'a'};
        AnagramChecker.insertionSort(test, cmp);
        assertArrayEquals(expected,test);
    }
    @Test
    void insertionSortTwoElement()
    {
        Comparator<Character> cmp = new AnagramChecker.lexicographicalCharComparator();
        Character[] test = {'b','a'};
        Character[] expected = {'a', 'b'};
        AnagramChecker.insertionSort(test, cmp);
        assertArrayEquals(expected,test);
    }
    @Test
    void areAnagramsBasic()
    {
        String test1 = "later";
        String test2 = "alert";
        assertTrue(AnagramChecker.areAnagrams(test1, test2));
    }

    @Test
    void areAnagramsNot()
    {
        String test1 = "later";
        String test2 = "ale";
        assertFalse(AnagramChecker.areAnagrams(test1, test2));
    }

    @Test
    void areAnagramsNull()
    {
        String test1 = null;
        String test2 = null;
        assertThrows(NullPointerException.class, () -> {AnagramChecker.areAnagrams(test1, test2);});
    }

    @Test
    void areAnagramsCaseSensitive()
    {
        String test1 = "CaTeRs";
        String test2 = "crAtES";
        assertTrue(AnagramChecker.areAnagrams(test1, test2));
    }

    @Test
    void getLargestAnagramGroup()
    {
        String[] test = {"hi", "bye", "caters", "crates"};
        assertArrayEquals(new String[]{"caters", "crates"}, AnagramChecker.getLargestAnagramGroup(test));
    }

    @Test
    void getLargestAnagramGroupNone()
    {
        String[] test = {"hi", "bye", "none", "all"};
        assertEquals(0, AnagramChecker.getLargestAnagramGroup(test).length);
    }

    @Test
    void getLargestAnagramGroupMultipleAnagrams()
    {
        String[] test = {"carets", "hi", "caters", "bye", "none", "crates", "caller","all","cellar"};
        assertArrayEquals(new String[]{"carets", "caters","crates"}, AnagramChecker.getLargestAnagramGroup(test));
    }

    @Test
    void getLargestAnagramGroupCapitals()
    {
        String[] test = {"Carets", "hi", "caters", "Bye", "none", "Crates", "caller","all","Cellar"};
        assertEquals(3, AnagramChecker.getLargestAnagramGroup(test).length);
    }

    @Test
    void testGetLargestAnagramGroup()
    {
        String filename = "sample_word_list.txt";
       assertEquals(7, AnagramChecker.getLargestAnagramGroup(filename).length);
    }
}