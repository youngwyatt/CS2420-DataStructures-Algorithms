package assign06;

public class LinkedStackArrayStackTimingPush {
    // Set these numbers for your experiment
    private static final int firstN = 10000; // smallest value of N
    private static final int incrementForN = 10000; // how much N increases by each step
    private static final int numberOfNValues = 15; // how many steps (values of N)
    // Increase timesToLoop to get more accurate, smoother results.
    private static final int timesToLoop = 50;

    /**
     * The main method runs the timing experiment, which prints results to the console.
     * @param args
     */
    public static void main(String[] args) {
        long startTime, endTime, beforeArrayStack, afterArrayStack, beforeListStack, afterListStack, arrayStackPushStart, arrayStackPushEnd, listStackPushStart, listStackPushEnd;

        long[] problemSizes = new long[numberOfNValues];
        problemSizes[0] = firstN;
        for (int i = 1; i < numberOfNValues; i++) {
            problemSizes[i] = problemSizes[i - 1] + incrementForN;
        }
        // Print a header
        System.out.println("Number of Items Popped   LinkedListStack    ArrayStack");

        // Run for each value of problem size
        for (int i = 0; i < problemSizes.length; i++) {
            // Warm up (garbage collection, etc.)
            System.gc(); // Request garbage collection
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1000000000) {
            } // empty loop for waiting

            // Time the ArrayStack code
            beforeArrayStack = System.nanoTime();
            for (int k = 0; k < timesToLoop; k++) {
                // Create a new array stack and fill it
                ArrayStack<Integer> arr = new ArrayStack<>();
                for (int j = 0; j < problemSizes[i]; j++) {
                    arr.push(j);
                }
                for (long n = 0; n < problemSizes[i]; n++) {
                    arr.pop();
                }
            }
            afterArrayStack = System.nanoTime();

            // Compensation for push time for ArrayStack
            System.gc(); // Request garbage collection again to minimize GC impact
            arrayStackPushStart = System.nanoTime();
            for (int k = 0; k < timesToLoop; k++) {
                ArrayStack<Integer> arr = new ArrayStack<>();
                for (int j = 0; j < problemSizes[i]; j++) {
                    arr.push(j);
                }
            }
            arrayStackPushEnd = System.nanoTime();

            long arrayStackPushTime = arrayStackPushEnd - arrayStackPushStart;
            long arrayStackTotalTime = arrayStackPushEnd - beforeArrayStack;
            double averageArrayTime = (double) (arrayStackTotalTime - arrayStackPushTime) / timesToLoop;
            System.gc(); // Request garbage collection
            startTime = System.nanoTime();
            while (System.nanoTime() - startTime < 1000000000) {
            } // empty loop for waiting
            // Time the LinkedListStack code
            beforeListStack = System.nanoTime();
            for (int k = 0; k < timesToLoop; k++) {
                // Create a new linked list stack and fill it
                LinkedListStack<Integer> list = new LinkedListStack<>();
                for (int j = 0; j < problemSizes[i]; j++) {
                    list.push(j);
                }
                for (long n = 0; n < problemSizes[i]; n++) {
                    list.pop();
                }
            }
            afterListStack = System.nanoTime();

            // Compensation for push time for LinkedListStack
            System.gc(); // Request garbage collection again to minimize GC impact
            listStackPushStart = System.nanoTime();
            for (int k = 0; k < timesToLoop; k++) {
                LinkedListStack<Integer> list = new LinkedListStack<>();
                for (int j = 0; j < problemSizes[i]; j++) {
                    list.push(j);
                }
            }
            listStackPushEnd = System.nanoTime();

            long listStackPushTime = listStackPushEnd - listStackPushStart;
            long listStackTotalTime = listStackPushEnd - beforeListStack;
            double averageListTime = (double) (listStackTotalTime - listStackPushTime) / timesToLoop;

            // Print out the result for this value of N
            System.out.printf("%-12d %-18.2f %-18.2f\n", problemSizes[i], averageListTime, averageArrayTime);
        }
    }
}
