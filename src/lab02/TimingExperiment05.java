package lab02;

/**
 * This program tries to determine how long it takes to compute the square root
 * of each number from 1 through 10.
 * 
 * @author Erin Parker and Eric Heisler
 * @version 2024-5-21
 */
public class TimingExperiment05 {

	public static void main(String[] args) {
		long startTime, midpointTime, stopTime;
		
		// First, spin computing stuff until one second has gone by.
		// This allows this thread to stabilize.
		startTime = System.nanoTime();
		while(System.nanoTime() - startTime < 1000000000) { // empty block
		}

		// Now, run the test.
		
		long timesToLoop = 1000;

		startTime = System.nanoTime();

		for (long i = 0; i < timesToLoop; i++) {
			for (double d = 1; d <= 10; d++) {
				Math.sqrt(d);
			}
		}

		midpointTime = System.nanoTime();

		// Run an empty loop to capture the cost of running the loop.

		for(long i = 0; i < timesToLoop; i++) {
		}

		stopTime = System.nanoTime();

		// Compute the time, subtract the cost of running the loop
		// from the cost of running the loop and computing square roots.
		// Average it over the number of runs.

		double averageTime = (double)((midpointTime - startTime) - (stopTime - midpointTime)) / timesToLoop;

		System.out.println("It takes " + averageTime + " nanoseconds to compute the square roots of the numbers 1..10.");
	}
}
