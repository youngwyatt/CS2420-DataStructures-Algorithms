package comprehensive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PhraseGeneratorKExperiment {

    // Set these numbers for your experiment ///////////////////////////////
    private static final int firstN = 10000; // smallest value of N
    private static final int incrementForN = 10000; // how much N increases by each step
    private static final int numberOfNValues = 20; // how many steps (values of N)
    // Increase timesToLoop to get more accurate, smoother results.
    private static final int timesToLoop = 10;
    ////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) {
        long startTime, endTime, afterCompensation;
        RandomPhraseGenerator generator = new RandomPhraseGenerator();
        long[] problemSizes = new long[numberOfNValues];
        problemSizes[0] = firstN;
        for (int i = 1; i < numberOfNValues; i++) {
            problemSizes[i] = problemSizes[i - 1] + incrementForN;
        }

        System.out.println("size(N)    generate_time(ns)");

        for (long N : problemSizes)
        {
            System.gc();
            startTime = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++){
                // Generate phrases
                generator.mainMethod((int) N);
            }
            endTime = System.nanoTime();
            // compensation loop
            for(int i = 0; i < timesToLoop; i++){new RandomPhraseGenerator();}
            afterCompensation = System.nanoTime();
            double compTime = afterCompensation - endTime;
            double generateTime = endTime - startTime;
            double averageTime = (double) (generateTime - compTime) / timesToLoop;
            System.out.printf("%-10d%-15.2f%n", N, averageTime);
        }
    }
}