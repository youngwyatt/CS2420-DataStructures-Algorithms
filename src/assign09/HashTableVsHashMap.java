package assign09;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Template class which can be used as a skeleton for performing timing tests
 * Set the experiment parameters at the top.
 * Look for "FILL IN" comments.
 *
 * @Author: Eric Heisler
 * @version 2024/5/10
 */
public class HashTableVsHashMap {

    // Set these numbers for your experiment ///////////////////////////////
    private static final int firstN = 1000; // smallest value of N
    private static final int incrementForN = 1000; // how much N increases by each step
    private static final int numberOfNValues = 20; // how many steps (values of N)
    // Increase timesToLoop to get more accurate, smoother results.
    private static final int timesToLoop = 50;
    ////////////////////////////////////////////////////////////////////////

    /**
     * The main method runs the timing experiment, which prints results to the console.
     * @param args
     */
    public static void main(String[] args) {
        long startTime, endTime, customHashTime, hashMapTime;
        ArrayList<String> list = new ArrayList<>();
        HashTable<String, Double> customHashTable;
        HashMap<String, Double> hashMap;

        // You can also manually set values in the array if desired.
        long[] problemSizes = new long[numberOfNValues];
        problemSizes[0] = firstN;
        for (int i = 1; i < numberOfNValues; i++) {
            problemSizes[i] = problemSizes[i - 1] + incrementForN;
        }

        // Print a header
        System.out.println("size(N)\tCustom Time(ns)\tHashMap Time(ns)");

        // Run for each value of problem size
        for (long N : problemSizes) {
            customHashTable = new HashTable<>();
            hashMap = new HashMap<>();
            ArrayList<String> keys = new ArrayList<>((int) N);
            char first = 'A';
            char last = 'A';

            // Set up for this value of N
            for (int i = 0; i < N; i++) {
                String key = String.valueOf(first) + String.valueOf(last);
                keys.add(key);
                if (last < 'Z') {
                    last++;
                } else {
                    last = 'A';
                    if (first < 'Z') {
                        first++;
                    } else {
                        first = 'A';
                    }
                }
            }

            // Warm up (garbage collection, etc.)
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 2000000000) {} // empty loop for waiting

            // Test custom hash table
            startTime = System.nanoTime();
            for (int i = 0; i < timesToLoop; i++) {
                for (String key : keys) {
                    customHashTable.put(key, Math.random());
                }
            }
            endTime = System.nanoTime();
            customHashTime = (endTime - startTime) / timesToLoop;

            // Test Java HashMap
            startTime = System.nanoTime();
            for (int i = 0; i < timesToLoop; i++) {
                for (String key : keys) {
                    hashMap.put(key, Math.random());
                }
            }
            endTime = System.nanoTime();
            hashMapTime = (endTime - startTime) / timesToLoop;

            System.out.printf("%-10d %-15.2f %-15.2f\n", N, (double) customHashTime, (double) hashMapTime);
        }
    }
}