package assign09;

import java.sql.Array;
import java.util.ArrayList;

/**
 * Template class which can be used as a skeleton for perfoming timing tests
 * Set the experiment parameters at the top.
 * Look for "FILL IN" comments.
 * 
 * @Author: Eric Heisler
 * @version 2024/5/10
 */
public class HashTableTimer {
	
	// Set these numbers for your experiment ///////////////////////////////
	private static final int firstN = 1000; // smallest value of N
	private static final int incrementForN = 1000; // how much N increases by each step
	private static final int numberOfNValues = 20; // how many steps (values of N)
	// Increase timesToLoop to get more accurate, smoother results.
	private static final int timesToLoop = 50;
	////////////////////////////////////////////////////////////////////////
	
	/**
	 * The main method runs the timing experiment, which prints results to the console.
	 * @param args
	 */
	public static void main(String[] args) {
		long startTime, beforeGood, beforeMedium, afterMedium, beforeBad, afterBad, afterTimedCode, afterCompensationLoop;
		ArrayList<Integer> list = new ArrayList<>();
		HashTable<StudentBadHash, Double> badTable;
		HashTable<StudentMediumHash, Double> mediumTable;
		HashTable<StudentGoodHash, Double> goodTable;

		// You can also manually set values in the array if desired.
	    long[] problemSizes = new long[numberOfNValues];
	    problemSizes[0] = firstN;
	    for(int i = 1; i < numberOfNValues; i++) {
	    	problemSizes[i] = problemSizes[i - 1] + incrementForN;
	    }
	    
	    // Print a header
		System.out.println("size(N)\tBad Time(ns)\tBad Collisions\tMedium Time(ns)\tMedium Collisions\tGood Time(ns)\tGood Collisions");
        
        // Run for each value of problem size
        for(long N : problemSizes) {
        	badTable = new HashTable<>();
			mediumTable = new HashTable<>();
			goodTable = new HashTable<>();
			ArrayList<StudentBadHash> badStudents = new ArrayList<>((int) N);
			ArrayList<StudentMediumHash> medStudents = new ArrayList<>((int) N);
			ArrayList<StudentGoodHash> goodStudents = new ArrayList<>((int) N);
			char first = 'A';
			char last = 'A';
			int uidFloor = 1000000;
        	// Set up for this value of N
        	// FILL IN ////////////////////////////////////////////////
        	for(int i = 0; i < N; i++)
			{
				int uid = uidFloor + i;
				String firstName = String.valueOf(first);
				String lastName = String.valueOf(last);
				double GPA = 2.0 + (i % 3);
				StudentBadHash studentBad = new StudentBadHash(uid, firstName, lastName);
				badStudents.add(studentBad);
				badTable.put(studentBad, GPA);
				StudentMediumHash studentMed = new StudentMediumHash(uid, firstName, lastName);
				medStudents.add(studentMed);
				mediumTable.put(studentMed, GPA);
				StudentGoodHash studentGood = new StudentGoodHash(uid, firstName, lastName);
				goodStudents.add(studentGood);
				goodTable.put(studentGood, GPA);
				if(last < 'Z')
				{
					last++;
				}
				else
				{
					last = 'A';
					if(first < 'Z')
					{
						first++;
					}
					else
					{
						first = 'A';
					}
				}
			}
        	///////////////////////////////////////////////////////////

            // Warm up (garbage collection, etc.)
            startTime = System.nanoTime();
            while(System.nanoTime() - startTime < 2000000000) {} // empty loop for waiting

			badTable.resetCollisions();
			beforeBad = System.nanoTime();
            for(int i = 0; i < timesToLoop; i++) {
            	for(StudentBadHash stud : badStudents)
				{
					badTable.containsKey(stud);
				}
            }
			afterBad = System.nanoTime();

			mediumTable.resetCollisions();
			beforeMedium = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++)
			{
				for(StudentMediumHash stud : medStudents)
				{
					mediumTable.containsKey(stud);
				}
			}
			afterMedium = System.nanoTime();

			goodTable.resetCollisions();
			beforeGood = System.nanoTime();
			for(int i = 0; i < timesToLoop; i++)
			{
				for(StudentGoodHash stud : goodStudents)
				{
					goodTable.containsKey(stud);
				}
			}

            afterTimedCode = System.nanoTime();
            
            // Compensation time to subtract overhead costs
			for(int i = 0; i < timesToLoop; i++) {
				for(int j = 0; j < N; j++) {
					list.add(2);
				}
			}
            afterCompensationLoop = System.nanoTime();
            
            long compensationTime = afterCompensationLoop - afterTimedCode;
			long badTime = afterBad - beforeBad;
			long medTime = afterMedium - beforeMedium;
			long goodTime = afterTimedCode - beforeGood;
            double averageBadTime = (double) (badTime - compensationTime) / timesToLoop;
			double averageMedTime = (double) (medTime - compensationTime) / timesToLoop;
			double averageGoodTime = (double) (goodTime - compensationTime) / timesToLoop;
			long badColls = badTable.getCollisions() / timesToLoop;
			long medColls = mediumTable.getCollisions() / timesToLoop;
			long goodColls = goodTable.getCollisions() / timesToLoop;

			System.out.printf("%-10d %-15.2f %-15d %-15.2f %-15d %-15.2f %-15d\n", N, averageBadTime, badColls, averageMedTime, medColls, averageGoodTime, goodColls);}
	}
}
