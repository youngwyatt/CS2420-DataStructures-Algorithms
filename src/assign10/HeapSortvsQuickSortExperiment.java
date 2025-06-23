package assign10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Template class which can be used as a skeleton for perfoming timing tests
 * Set the experiment parameters at the top.
 * Look for "FILL IN" comments.
 * 
 * @Author: Eric Heisler
 * @version 2024/5/10
 */
public class HeapSortvsQuickSortExperiment {
	
	// Set these numbers for your experiment ///////////////////////////////
	private static final int firstK = 0; // smallest value of N
	private static final int incrementForK= 5000; // how much N increases by each step
	private static final int numberOfKValues = 21; // how many steps (values of N)
	// Increase timesToLoop to get more accurate, smoother results.
	private static final int timesToLoop = 10;

	private static final int N = 100000;
	////////////////////////////////////////////////////////////////////////
	
	/**
	 * The main method runs the timing experiment, which prints results to the console.
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime, beforeHeap, beforeSort,  afterTimedCode, afterCompensationLoop, afterHeapCompensation;
		// You can also manually set values in the array if desired.
	    int[] problemSizes = new int[numberOfKValues];
	    problemSizes[0] = firstK;
	    for(int i = 1; i < numberOfKValues; i++) {
	    	problemSizes[i] = problemSizes[i - 1] + incrementForK;
	    }
	    
	    // Print a header
		System.out.printf("%8s%20s%20s\n", "size(K)", "heap_time(ns)", "sort_time(ns)");
        
        // Run for each value of problem size
        for(int k : problemSizes) {
			List<Integer> list = new ArrayList<>();
			for(int i = 0; i < N; i++){
				list.add(i);
			}
			Collections.shuffle(list);
			List<Integer> shuffledList = new ArrayList<>(list);

			// Warm up (garbage collection, etc.)
			startTime = System.nanoTime();
			while(System.nanoTime() - startTime < 1000000000) {} // empty loop for waiting

			// Time the code you are interested in
			beforeHeap = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++) {
				list = new ArrayList<>(shuffledList);
				FindKLargest.findKLargestHeap(list, k);
			}
			beforeSort = System.nanoTime();
			for(int i  = 0; i < timesToLoop; i++){
				list = new ArrayList<>(shuffledList);
				FindKLargest.findKLargestSort(list, k);
			}
			afterTimedCode = System.nanoTime();

			// Compensation time to subtract overhead costs
			for(int i = 0; i < timesToLoop; i++) {
				list = new ArrayList<>(shuffledList);
			}
			afterCompensationLoop = System.nanoTime();

			long compensationTime = afterCompensationLoop - afterTimedCode;
			long sortTime = afterTimedCode - beforeSort;
			long heapTime = beforeSort - beforeHeap;
			double averageSortTime = (double)(sortTime - compensationTime)/timesToLoop;
			double averageHeapTime = (double)(heapTime - compensationTime)/timesToLoop;

			// Here you can print out or process the result for this value of N
			System.out.printf("%8d%20.2f%20.2f\n", k, averageHeapTime, averageSortTime);
        }
	}
}
