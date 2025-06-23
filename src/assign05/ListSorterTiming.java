package assign05;

import java.util.ArrayList;
import java.util.List;

public class ListSorterTiming
{
    // Set these numbers for your experiment ///////////////////////////////
    private static final int firstN = 100000; // smallest value of N
    private static final int incrementForN = 50000; // how much N increases by each step
    private static final int numberOfNValues = 20; // how many steps (values of N)
    // Increase timesToLoop to get more accurate, smoother results.
    private static final int timesToLoop = 10;
    ////////////////////////////////////////////////////////////////////////

    /**
     * The main method runs the timing experiment, which prints results to the console.
     * @param args
     */
    public static void main(String[] args) {
        long startTime, midTime, endTime;

        String[] strategies = new String[]{"first", "random", "median"};
        long[] problemSizes = new long[numberOfNValues];
        problemSizes[0] = firstN;
        for (int i = 1; i < numberOfNValues; i++) {
            problemSizes[i] = problemSizes[i - 1] + incrementForN;
        }

        ArrayList<List<Integer>> permutedLists = new ArrayList<>();
        for (long N : problemSizes) {
            permutedLists.add(ListSorter.generatePermuted((int) N));
        }

        // Print a header
        System.out.println("Strategy    size(N)    time(ns)");

        for (String strategy : strategies) {
            System.out.println(strategy);
            ListSorter.setPivotStrategy(strategy);

            // Run for each value of problem size
            for (int i = 0; i < problemSizes.length; i++) {
                long N = problemSizes[i];
                ArrayList<Integer> test = new ArrayList<>(permutedLists.get(i));

                // Warm up (garbage collection, etc.)
                startTime = System.nanoTime();
                while (System.nanoTime() - startTime < 1000000000) {} // empty loop for waiting

                // Time the code you are interested in
                startTime = System.nanoTime();
                for (int k = 0; k < timesToLoop; k++) {
                    ArrayList<Integer> tempList = new ArrayList<>(test);
                    ListSorter.quicksort(tempList);
                }
                midTime = System.nanoTime();

                // Compensation time to subtract overhead costs
                for (int j = 0; j < timesToLoop; j++) {
                    ArrayList<Integer> tempList = new ArrayList<>(test);
                }
                endTime = System.nanoTime();

                long compensationTime = endTime - midTime;
                long totalTimedCodeTime = midTime - startTime;
                double averageTime = (double) (totalTimedCodeTime - compensationTime) / timesToLoop;

                // Print out the result for this value of N
                System.out.println(strategy + " \t" + N + " \t" + averageTime);
            }
        }
    }
}
