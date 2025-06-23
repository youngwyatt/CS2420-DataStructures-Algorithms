package lab02;

/**
 * This program loops 100 times looking for changes in the current time.  If
 * time advances in one-millisecond increments, then this code should complete
 * in 100 milliseconds.
 * 
 * @author Erin Parker and Eric Heisler
 * @version 2024-5-21
 */
public class TimingExperiment01 {

	public static void main(String[] args) {
		long lastTime = System.currentTimeMillis();
		int advanceCount = 0;
		int checkCount = 1; 
		while (advanceCount < 100) {
			long currentTime = System.currentTimeMillis();
			if (currentTime == lastTime) {
				checkCount++;
				continue;
			}
			System.out.println("Time advanced " + (currentTime - lastTime) + " milliseconds. Time was checked " + checkCount + " times");
			lastTime = currentTime;
			advanceCount++;
			checkCount = 1;
		}
	}
}
