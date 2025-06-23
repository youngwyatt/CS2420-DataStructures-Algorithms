package lab02;

/**
 * This program calls nanoTime 1 million times while measuring how long that takes.
 * 
 * @author Eric Heisler
 * @version 2024-5-21
 */
public class TimingExperiment03 {

	public static void main(String[] args) {
		long callCount = 1000000;
		long endTime = 0;
		long beginTime = System.nanoTime();
		while(callCount-- > 0) {
			endTime = System.nanoTime();
		}
		
		System.out.println("It took " + (endTime - beginTime) + " nanoseconds to call nanoTime 1 million times");
		System.out.println("On average, " + ((endTime - beginTime) / 1000000) + " nanoseconds per call");
	}
}
