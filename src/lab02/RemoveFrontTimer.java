package lab02;

import java.util.ArrayList;

public class RemoveFrontTimer
{
    // Set these numbers for your experiment ///////////////////////////////
    private static final int firstN = 1000; // smallest value of N
    private static final int incrementForN = 1000; // how much N increases by each step
    private static final int numberOfNValues = 10; // how many steps (values of N)
    // Increase timesToLoop to get more accurate, smoother results.
    private static final int timesToLoop = 10000;
    ////////////////////////////////////////////////////////////////////////

    /**
     * The main method runs the timing experiment, which prints results to the console.
     * @param args
     */
    public static void main(String[] args) {
        long startTime, afterTimedCode, afterCompensationLoop;
        ArrayList<Integer> list = new ArrayList<>();

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

            // Set up for this value of N
            // FILL IN ////////////////////////////////////////////////

            ///////////////////////////////////////////////////////////

            // Warm up (garbage collection, etc.)
            startTime = System.nanoTime();
            while(System.nanoTime() - startTime < 1000000000) {} // empty loop for waiting

            // Time the code you are interested in
            startTime = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++) {
                // FILL IN ////////////////////////////////////////////////

                ///////////////////////////////////////////////////////////
            }
            afterTimedCode = System.nanoTime();

            // Compensation time to subtract overhead costs
            for(int i = 0; i < timesToLoop; i++) {
                // FILL IN ////////////////////////////////////////////////

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
