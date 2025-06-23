package assign04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * AnagramChecker class that determines if two words are anagrams(i.e 'alert'
 * and 'later') and finds the largest group of anagrams in a list of words.
 * @author  Brian Keller & Wyatt Young
 * @version May 30, 2024
 */
public class AnagramChecker {
    /**
     * Sorting uses implementation of insertion sort
     *
     * @params str; string to be sorted in lexicographic order
     * @returns the version of the input string in which characters
     * are sorted lexicographically.
     */
    public static String sort(String str)
    {
//        str = str.toLowerCase();
        //put characters from input into an array instead of a string
        Character[] characters = new Character[str.length()];
        for(int i = 0; i < str.length(); i++)
        {
            characters[i] = str.charAt(i);
        }

        //define a comparator
        Comparator<Character> cmp = new lexicographicalCharComparator();

        //use insertionsort to sort character array
        insertionSort(characters, cmp);
        // rebuild string
        StringBuilder sb = new StringBuilder();
        for(Character character : characters)
        {
            sb.append(character);
        }
        return sb.toString();
    }

    /**
     * generic method that sorts input array using insertion sort and the
     * inputted comparator object
     *
     * @params array-array to be sorted
     * @params comp- Comparator object used to sort
     * @returns sorted version of input array
     */
    public static <T> void insertionSort(T[] array, Comparator<? super T> comp)
    {
        // start at 1 to start sorted portion
        for(int i = 1; i < array.length; i++)
        {
            // select first unsorted item
            T letter = array[i];
            int j;
            // insert into sorted portion
            for(j = i - 1; j >= 0 && comp.compare(array[j], letter) > 0;j--)
            {
                array[j + 1] = array[j];
            }
            array[j + 1] = letter;
        }
    }

    /**
     * determines if two strings are anagrams of eachother, must call sort(String)
     * method
     *
     * @params strOne, strTwo- two strings being compared
     * @returns true when two input strings are anagrams of each other,
     * o/w return false
     */
    public static boolean areAnagrams(String strOne, String strTwo) {
        strOne = strOne.toLowerCase();
        strTwo = strTwo.toLowerCase();
        strOne = sort(strOne);
        strTwo = sort(strTwo);

        return strOne.equals(strTwo);
    }

    /**
     * given an input array of strings, determines the largest group of anagrams
     * in no particular order. Must call insertionSort with a new Comparator class
     * or lambda expression we design
     *
     * @params array- array of strings w/ potential anagrams
     * @returns group; largest group of anagrams, or an empty array if there are no
     * anagrams in group.
     */
    public static String[] getLargestAnagramGroup(String[] array)
    {
        Comparator<String> cmp = new listSort();
        //sort the list by alphabetical order of each sorted word, grouping anagrams together
        insertionSort(array,cmp);
        int biggestGroup = 1;
        String anagramTarget = " ";
        int anagramCount = 1;
        //determine size of each anagram block
        for(int i = 0; i < array.length-1; i++)
        {
            if(areAnagrams(array[i], array[i+1]))
            {
                anagramCount++;
                if(anagramCount > biggestGroup)
                {
                    anagramTarget = array[i];
                }
            }
            else if(!areAnagrams(array[i], array[i+1]) && anagramCount > biggestGroup)
            {
                anagramTarget = array[i];
                biggestGroup = anagramCount;
                anagramCount = 1;
            }
            else
                anagramCount = 0;
        }
        //create list of largest group of anagrams
        ArrayList<String> anagrams = new ArrayList<>();
        for (String s : array) {
            if (areAnagrams(s, anagramTarget))
                anagrams.add(s);
        }
        return anagrams.toArray(new String[0]);
    }
    /**
     * behaves same as getLargestAnagramGroup, passes filename instead
     * assumed that file contains one word per line. If file does not
     * exist or is empty returns an empty array
     *
     * @params filename; filename of String list
     * @returns largest group of anagrams from input file, or an empty array
     * if file is empty or doesn't exist.
     */
    public static String[] getLargestAnagramGroup(String filename)
    {
        ArrayList<String> array = new ArrayList<>();
        String word;
        try
        {
            File file = new File(filename);
            Scanner in = new Scanner(file);
            while(in.hasNext())
            {
                word = in.next();
                array.add(word);
            }
            in.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }

        return getLargestAnagramGroup(array.toArray(new String[0]));
    }
    /*
    compare method for lexicographic comparator
     */
    public static class lexicographicalCharComparator implements Comparator<Character> {
        @Override
        public int compare(Character ch1, Character ch2) {
//            ch1 = Character.toLowerCase(ch1);
//            ch2 = Character.toLowerCase(ch2);
            return ch1.compareTo(ch2);
        }
    }
    public static class listSort implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return sort(s1.toLowerCase()).compareTo(sort(s2.toLowerCase()));
        }
    }
}
