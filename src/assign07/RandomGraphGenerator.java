package assign07;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * A set of static methods for generating random graphs with
 * specified properties.
 * 
 * @author Eric Heisler
 * @version 2024-6-19
 */
public class RandomGraphGenerator {
	
	public static void main(String[] args) {
		// Change the file path and numbers as desired
		generateRandomDotFile("test.dot", 5, 3);
	}
	
	/**
	 * Generates a random directed graph by populating lists of source and destination
	 * vertices. To allow repeatable generation, a seed for the random number
	 * generator is used.
	 * 
	 * @param vertexCount total number of vertices in the graph
	 * @param edgesPerVertex should be a small positive number like 2
	 * @param seed for the random number generator
	 * @param sources list
	 * @param destinations list
	 */
	public static void generateRandomGraph(int vertexCount, int edgesPerVertex, long seed,
										ArrayList<String> sources, ArrayList<String> destinations) {
		Random rng = new Random(seed);
		
		// Make sure both lists are starting empty
		sources.clear();
		destinations.clear();
		
		// generate a list of unique vertices
		String[] vertex = new String[vertexCount];
		for (int i = 0; i < vertexCount; i++) {
			vertex[i] = "v" + i;
		}
		
		// randomly connect the vertices using edgesPerVertex * |V| edges
		int[] dsts = new int[edgesPerVertex];
		for (int srci = 0; srci < vertexCount; srci++) {
			for (int dsti = 0; dsti < edgesPerVertex; dsti++) {
				
				// Do not allow edges to self or duplicate edges
				int candidate = rng.nextInt(vertexCount);
				while(candidate == srci || containsBefore(dsts, candidate, dsti))
					candidate = rng.nextInt(vertexCount);
				dsts[dsti] = candidate;
				
				sources.add(vertex[srci]);
				destinations.add(vertex[candidate]);
			}
		}
	}
	
	/**
	 * Writes a randomly generated digraph to a DOT file.
	 * 
	 * @param filename
	 * @param vertexCount
	 * @param edgesPerVertex
	 */
	public static void generateRandomDotFile(String filename, int vertexCount, int edgesPerVertex) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(filename);
		} catch (IOException e) {
			System.out.println(e);
			return;
		}
		
		// Randomly construct a digraph
		ArrayList<String> sources = new ArrayList<String>();
		ArrayList<String> destinations = new ArrayList<String>();
		generateRandomGraph(vertexCount, edgesPerVertex, 27, sources, destinations);
		
		// Write it to the file
		String edgeOp = "->";
		out.println("digraph G {");
		for (int i = 0; i < sources.size(); i++) {
			out.println("\t" + sources.get(i) + edgeOp + destinations.get(i));
		}

		out.println("}");
		out.close();
	}
	
	/**
	 * Helper to determine if the array contains the value at an index less than maxInd.
	 */
	private static boolean containsBefore(int[] arr, int val, int maxInd) {
		for(int i = 0; i < maxInd; i++)
			if(arr[i] == val)
				return true;
		return false;
	}
}
