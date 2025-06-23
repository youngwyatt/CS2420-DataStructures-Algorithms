package assign10;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Template class which can be used as a skeleton for perfoming timing tests
 * Set the experiment parameters at the top.
 * Look for "FILL IN" comments.
 *
 * @Author: Brian Keller and Wyatt Young
 * @version July 17, 2024
 */
public class HeapOperationsExperiment {

	// Set these numbers for your experiment ///////////////////////////////
	private static final int firstN = 100000; // smallest value of N
	private static final int incrementForN = 10000; // how much N increases by each step
	private static final int numberOfNValues = 20; // how many steps (values of N)
	// Increase timesToLoop to get more accurate, smoother results.
	private static final int timesToLoop = 1000;
	////////////////////////////////////////////////////////////////////////

	/**
	 * The main method runs the timing experiment, which prints results to the console.
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime, beforePeek, beforeAdd, beforeExtract, afterTimedCode, afterCompensationLoop, afterPeek, afterExtract, afterAdd, afterExtractCompensation;
		ArrayList<Integer> list = new ArrayList<>();
		BinaryMaxHeap<Integer> test;
		// You can also manually set values in the array if desired.
	    long[] problemSizes = new long[numberOfNValues];
	    problemSizes[0] = firstN;
	    for(int i = 1; i < numberOfNValues; i++) {
	    	problemSizes[i] = problemSizes[i - 1] + incrementForN;
	    }

	    // Print a header
		System.out.println("size(N)    peek_time(ns)    extract_time(ns)    add_time(ns)");

        // Run for each value of problem size
        for(long N : problemSizes) {
			for(int i = 0; i < N; i++){
				list.add(i);
			}
			Collections.shuffle(list);

            // Warm up (garbage collection, etc.)
            startTime = System.nanoTime();
            while(System.nanoTime() - startTime < 1000000000) {} // empty loop for waiting

            // Time the code you are interested
			test = new BinaryMaxHeap<>(list);
            beforePeek = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++) {
            	test.peek();
            }
			afterPeek = System.nanoTime();
			beforeExtract = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++){
				test.extract();
			}
			afterExtract = System.nanoTime();
			beforeAdd = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++){
				for(int j = 0; j < N; j++){
					test.add(j);
				}
			}
            afterTimedCode = System.nanoTime();

            // Compensation time to subtract overhead costs
            for(int i = 0; i < timesToLoop; i++) {
            }
            afterCompensationLoop = System.nanoTime();

            long compensationTime = afterCompensationLoop - afterTimedCode;
            long peekTime = afterPeek - beforePeek;
			long extractTime = afterExtract - beforeExtract;
			long addTime = afterTimedCode - beforeAdd;
            double averagePeekTime = (double)(peekTime - compensationTime)/timesToLoop;
			double averageExtractTime = (double)(extractTime - compensationTime)/timesToLoop;
			double averageAddTime = (double)((addTime - compensationTime)/timesToLoop)/N;

            // Here you can print out or process the result for this value of N
			System.out.printf("%-10d%-15.2f%-18.2f%.2f%n", N, averagePeekTime, averageExtractTime, averageAddTime);
        }
	}
}
