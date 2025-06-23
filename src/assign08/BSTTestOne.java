package assign08;

import assign07.Graph;
import assign07.GraphUtility;
import assign07.RandomGraphGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Template class which can be used as a skeleton for perfoming timing tests
 * Set the experiment parameters at the top.
 * Look for "FILL IN" comments.
 * 
 * @Author: Eric Heisler
 * @version 2024/5/10
 */
public class BSTTestOne {
	
	// Set these numbers for your experiment ///////////////////////////////
	private static final int firstN = 10000; // smallest value of N
	private static final int incrementForN = 10000; // how much N increases by each step
	private static final int numberOfNValues = 20; // how many steps (values of N)
	// Increase timesToLoop to get more accurate, smoother results.
	private static final int timesToLoop = 50;
	////////////////////////////////////////////////////////////////////////
	
	/**
	 * The main method runs the timing experiment, which prints results to the console.
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime, afterTimedCode, afterCompensationLoop;
		// You can also manually set values in the array if desired.
	    long[] problemSizes = new long[numberOfNValues];
	    problemSizes[0] = firstN;
	    for(int i = 1; i < numberOfNValues; i++) {
	    	problemSizes[i] = problemSizes[i - 1] + incrementForN;
	    }
	    
	    // Print a header
        System.out.println("size(N)    time(ns)");
        
        // Run for each value of problem size
        for(long N : problemSizes) {
			BinarySearchTree<Integer> bst = new BinarySearchTree<>();
			ArrayList<Integer> list = new ArrayList<>();
			for (int i = 0; i < N; i++) {
				list.add(i);
			}
			Collections.shuffle(list);
			for (int j : list) {
				bst.add(j);
			}
            // Warm up (garbage collection, etc.)
			System.gc();
            startTime = System.nanoTime();
            while(System.nanoTime() - startTime < 1000000000) {} // empty loop for waiting

            // Time the code you are interested in
            startTime = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++) {
            	// FILL IN ////////////////////////////////////////////////
				bst.toArrayRange(1, (int)N);
            	///////////////////////////////////////////////////////////
            }
            afterTimedCode = System.nanoTime();
            
            // Compensation time to subtract overhead costs
            for(int i = 0; i < timesToLoop; i++) {
            	// FILL IN ////////////////////////////////////////////////
            	bst.size();
            	///////////////////////////////////////////////////////////
            }
            afterCompensationLoop = System.nanoTime();
            
            long compensationTime = afterCompensationLoop - afterTimedCode;
            long totalTimedCodeTime = afterTimedCode - startTime;
            double averageTime = (double)(totalTimedCodeTime - compensationTime)/timesToLoop;
            
            // Here you can print out or process the result for this value of N
            System.out.println(N + " \t" + averageTime);
        }
	}
}
