package assign07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Contains several methods for solving problems on generic, directed, unweighted, sparse graphs.
 * 
 * @author CS 2420 instructors & Wyatt Young
 * @version June 25, 2024
 */
public class GraphUtility {

	/**
	 * This method uses the recursive depth first search method provided in the Graph class
	 * to determine whether there is a path from the vertex srcData to dstData.
	 *
	 * @param sources List of source vertices
	 * @param destinations List of destination vertices
	 * @param srcData Source vertex to start the search from
	 * @param dstData Destination vertex to search too
	 * @return true if there is a path from srcData to dstData, false o/w
	 * @throws IllegalArgumentException if the sizes of sources and destinations do not match,
	 *         or if srcData or dstData do not exist in the graph.
	 */
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
			throws IllegalArgumentException {
		if(sources.size() != destinations.size()){throw new IllegalArgumentException("Input Size Mismatch");}
		Graph<Type> graph = new Graph<>();
		for(int i=0; i < sources.size(); i++)
		{
			graph.addEdge(sources.get(i), destinations.get(i));
		}
		List<Type> result = graph.depthFirstSearch(srcData, dstData);
		return !result.isEmpty();
	}
	/**
	 * This method uses the breadth first search method to find the shortest path from the
	 * vertex srcData to the vertex dstData in the graph.
	 *
	 * @param sources List of source vertices.
	 * @param destinations List of destination vertices.
	 * @param srcData Source vertex data to start the search from.
	 * @param dstData Destination vertex data to search for.
	 * @return A list of vertex data representing the shortest path from srcData to dstData.
	 * @throws IllegalArgumentException if the sizes of sources and destinations do not match,
	 *         if srcData or dstData do not exist in the graph, or if no path exists between
	 *         srcData and dstData.
	 */
	public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData, Type dstData)
			throws IllegalArgumentException {
		if(sources.size() != destinations.size()){throw new IllegalArgumentException("Input Size Mismatch");}
		Graph<Type> graph = new Graph<>();
		for(int i=0; i < sources.size(); i++)
		{
			graph.addEdge(sources.get(i), destinations.get(i));
		}
		List<Type> result = (List<Type>) graph.breadthFirstSearch(srcData, dstData);
		if(result.isEmpty()){throw new IllegalArgumentException("No Path Found");}
		return result;
	}
	/**
	 * This method uses the topological sort method to generate a sorted ordering
	 * of the vertices in the graph.
	 * Note: Topological sort implementation contains check for cycles in inputted graph
	 * Note: If graph with a single vertex, returns list containing that node.
	 * @param sources List of source vertices.
	 * @param destinations List of destination vertices.
	 * @return A list of vertex data representing the topologically sorted order of the vertices.
	 * @throws IllegalArgumentException if the sizes of sources and destinations do not match,
	 *         or if the graph contains a cycle.
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException {
		if(sources.size() != destinations.size()){throw new IllegalArgumentException("Input Size Mismatch");}
		Graph<Type> graph = new Graph<>();
		for(int i = 0; i < sources.size(); i++)
		{
			graph.addEdge(sources.get(i), destinations.get(i));
		}
		List<Type> result = graph.topologicalSort();
		return result;
	}
	/**
	 * Builds "sources" and "destinations" lists according to the edges
	 * specified in the given DOT file (e.g., "a -> b"). Assumes that the vertex
	 * data type is String.
	 * 
	 * Accepts many valid "digraph" DOT files (see examples posted on Canvas).
	 * --accepts \\-style comments 
	 * --accepts one edge per line or edges terminated with ; 
	 * --does not accept attributes in [] (e.g., [label = "a label"])
	 * 
	 * @param filename - name of the DOT file
	 * @param sources - empty ArrayList, when method returns it is a valid
	 *        "sources" list that can be passed to the public methods in this
	 *        class
	 * @param destinations - empty ArrayList, when method returns it is a valid
	 *        "destinations" list that can be passed to the public methods in
	 *        this class
	 */
	public static void buildListsFromDot(String filename, ArrayList<String> sources, ArrayList<String> destinations) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		scan.useDelimiter(";|\n");
		// Determine if graph is directed (i.e., look for "digraph id {").
		String line = "", edgeOp = "";
		while (scan.hasNext()) {
			line = scan.next();
			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");
			if (line.indexOf("digraph") >= 0) {
				edgeOp = "->";
				line = line.replaceFirst(".*\\{", "");
				break;
			}
		}
		if (edgeOp.equals("")) {
			System.out.println("DOT graph must be directed (i.e., digraph).");
			scan.close();
			System.exit(0);
		}
		// Look for edge operator -> and determine the source and destination
		// vertices for each edge.
		while (scan.hasNext()) {
			String[] substring = line.split(edgeOp);

			for (int i = 0; i < substring.length - 1; i += 2) {
				// remove " and trim whitespace from node string on the left
				String vertex1 = substring[0].replace("\"", "").trim();
				// if string is empty, try again
				if (vertex1.equals("")) {
					continue;
				}
				// do the same for the node string on the right
				String vertex2 = substring[1].replace("\"", "").trim();
				if (vertex2.equals("")) {
					continue;
				}
				// indicate edge between vertex1 and vertex2
				sources.add(vertex1);
				destinations.add(vertex2);
			}
			// do until the "}" has been read
			if (substring[substring.length - 1].indexOf("}") >= 0) {
				break;
			}
			line = scan.next();
			// Skip //-style comments.
			line = line.replaceFirst("//.*", "");
		}
		scan.close();
	}
}
