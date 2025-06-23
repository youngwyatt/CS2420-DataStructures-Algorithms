package assign04;

import assign04.AnagramChecker;

import java.util.ArrayList;
import java.util.Random;

/**
 * Template class which can be used as a skeleton for perfoming timing tests
 * Set the experiment parameters at the top.
 * Look for "FILL IN" comments.
 * 
 * @Author: Eric Heisler
 * @version 2024/5/10
 */
public class AnagramTimer {
	
	// Set these numbers for your experiment ///////////////////////////////
	private static final int firstN = 10000; // smallest value of N
	private static final int incrementForN = 10000; // how much N increases by each step
	private static final int numberOfNValues = 20; // how many steps (values of N)
	// Increase timesToLoop to get more accurate, smoother results.
	private static final int timesToLoop = 5;
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
			StringBuilder sb1 = new StringBuilder((int) N);
			StringBuilder sb2 = new StringBuilder((int) N);
			String alphabet = "abcdefghijklmnopqrstuvwxyz";
			Random rand = new Random();
			// Set up for this value of N
        	for(int i = 0; i < N; i++)
			{
				sb1.append(alphabet.charAt(rand.nextInt(alphabet.length())));
				sb2.append(alphabet.charAt(rand.nextInt(alphabet.length())));
			}
			String str1 = sb1.toString();
			String str2 = sb2.toString();

            // Warm up (garbage collection, etc.)
            startTime = System.nanoTime();
            while(System.nanoTime() - startTime < 1000000000) {} // empty loop for waiting

            // Time the code you are interested in
            startTime = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++)
			{
				AnagramChecker.areAnagrams(str1, str2);
            }
            afterTimedCode = System.nanoTime();
            
            // Compensation time to subtract overhead costs
            for(int i = 0; i < timesToLoop; i++) {
				int size = str1.length();
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
