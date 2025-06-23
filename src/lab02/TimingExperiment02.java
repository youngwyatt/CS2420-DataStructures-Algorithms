package lab02;

/**
 * This program loops 100 times looking for changes in the current time.  If
 * time advances in one-nanosecond increments, then this code should complete
 * in 100 nanoseconds.
 * 
 * @author Erin Parker and Eric Heisler
 * @version 2024-5-21
 */
public class TimingExperiment02 {

	public static void main(String[] args) {
		long lastTime = System.nanoTime();
		int advanceCount = 0;
		int checkCount = 1; 
		while (advanceCount < 100) {
			long currentTime = System.nanoTime();
			if (currentTime == lastTime) {
				checkCount++;
				continue;
			}
			System.out.println("Time advanced " + (currentTime - lastTime) + " nanoseconds. Time was checked " + checkCount + " times");
			lastTime = currentTime;
			advanceCount++;
			checkCount = 1;
		}
	}
}
