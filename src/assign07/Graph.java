package assign07;

import java.sql.Array;
import java.util.*;

/**
 * Represents a sparse, unweighted, directed graph (a set of vertices and a set of edges). 
 *
 * @author Wyatt Young
 * @version June 21, 2024
 */
public class Graph <T>{
	// the graph -- a set of vertices, generic name mapped to a Vertex instance
	private HashMap<T, Vertex<T>> vertices;
	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<T, Vertex<T>>();
	}
	/**
	 * Adds to the graph a directed edge from the vertex with name "name1" 
	 * to the vertex with name "name2".  (If either vertex does not already 
	 * exist in the graph, it is added.)
	 * 
	 * @param name1 - generic name for source vertex
	 * @param name2 - generic name for destination vertex
	 */
	public void addEdge(T name1, T name2) {
		Vertex<T> vertex1;
		// if vertex already exists in graph, get its object
		if(vertices.containsKey(name1))
			vertex1 = vertices.get(name1);
		// else, create a new object and add to graph
		else {
			vertex1 = new Vertex<>(name1);
			vertices.put(name1, vertex1);
		}
		Vertex<T> vertex2;
		if(vertices.containsKey(name2))
			vertex2 = vertices.get(name2);
		else {
			vertex2 = new Vertex<>(name2);
			vertices.put(name2, vertex2);
		}
		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}
	/**
	 * Generates the DOT encoding of this graph as string, which can be 
	 * pasted into http://www.webgraphviz.com to produce a visualization.
	 */
	public String generateDot() {
		StringBuilder dot = new StringBuilder("digraph d {\n");
		// for every vertex 
		for(Vertex<T> v : vertices.values()) {
			// for every edge
			Iterator<Edge> edges = v.edges();
			while(edges.hasNext()) 
				dot.append("\t\"" + v.getName() + "\" -> \"" + edges.next() + "\"\n");
		}
		return dot.toString() + "}";
	}
	/**
	 * depth-first search implementation
	 *
	 * @param current vertex being searched from
	 * @param goal vertex to get path to
	 * @return a boolean for if the goal vertex has been found from the current vertex neighbors
	 */
	private boolean depthFirstSearchImp(Vertex<T> current, Vertex<T> goal, List<Vertex<T>> path)
	{
		current.setVisited(true);
		path.add(current);
		if(current.equals(goal))
		{
			return true;
		}
		Iterator<Edge> edges = current.edges();
		while(edges.hasNext())
		{
			Vertex<T> neighborVertex = edges.next().getOtherVertex();
			if(!neighborVertex.getVisited())
			{
				neighborVertex.setPreviousVertex(current);
				if(depthFirstSearchImp(neighborVertex, goal, path)){
					return true;
				}
			}
		}
		path.remove(path.size() - 1);
		return false;
	}
	/**
	 *  depth-first search driver method
	 *
	 * @param startVertex vertex to start path from
	 * @param goal vertex to build path to
	 * @throws IllegalArgumentException if start or goal isn't contained in the graph
	 * @return path list of vertices from start vertex to goal vertex or empty list if no path is found
	 */
	public List<T> depthFirstSearch(T startVertex, T goal)
	{
		if(!vertices.containsKey(startVertex) || !vertices.containsKey(goal))
		{
			throw new IllegalArgumentException("Start or goal not found in Graph");
		}
		Vertex<T> start = vertices.get(startVertex);
		Vertex<T> goalVertex = vertices.get(goal);
		resetVisited();
		List<Vertex<T>> path = new ArrayList<>();
		if(depthFirstSearchImp(start, goalVertex, path))
		{
			List<T> result = new ArrayList<>();
			for(Vertex<T> vertex : path)
			{
				result.add(vertex.getName());
			}
			return result;
		}
		return new ArrayList<>();
	}
	/**
	 * Breadth-first search implementation
	 *
	 * @param startName the vertex to start path from
	 * @param goalName the vertex to get the shortest path too
	 * @throws IllegalArgumentException if start or goal isn't contained in the graph
	 * @return path list of vertices that represent the shortest path from the start vertex to the goal
	 * vertex or an empty list if no path is found
	 */
	public LinkedList<T> breadthFirstSearch(T startName, T goalName) {
		if (!vertices.containsKey(startName) || !vertices.containsKey(goalName)) {
			throw new IllegalArgumentException("Start or goal not found in Graph");
		}
		Vertex<T> startVertex = vertices.get(startName);
		Vertex<T> goalVertex = vertices.get(goalName);
		Queue<Vertex<T>> queue = new LinkedList<>();
		resetVisited();
		queue.offer(startVertex);
		startVertex.setVisited(true);
		while (!queue.isEmpty()) {
			Vertex<T> current = queue.poll();
			if (current.equals(goalVertex)) {
				return backtrackPath(goalVertex);
			}
			Iterator<Edge> neighbors = current.edges();
			while (neighbors.hasNext()) {
				Vertex<T> neighbor = neighbors.next().getOtherVertex();
				if (!neighbor.getVisited()) {
					neighbor.setVisited(true);
					neighbor.setPreviousVertex(current);
					queue.offer(neighbor);
				}
			}
		}
		return new LinkedList<>();
	}
	/**
	 * topological sort implementation
	 * graph must be directed and acyclic
	 *
	 * @return a sorted ArrayList
	 * @throws IllegalArgumentException if graph contains a cycle
	 */
	public ArrayList<T> topologicalSort()
	{
		ArrayList<T> sorted = new ArrayList<>();
		Queue<Vertex<T>> queue = new LinkedList<>();
		for(Vertex<T> vertex : vertices.values())
		{
			if(vertex.getInDegree() == 0)
			{
				queue.offer(vertex);
			}
		}
		while(!queue.isEmpty())
		{
			Vertex<T> current = queue.poll();
			sorted.add(current.getName());
			Iterator<Edge> neighbors = current.edges();
			while(neighbors.hasNext())
			{
				Vertex<T> neighbor = neighbors.next().getOtherVertex();
				neighbor.decrementInDegree();
				if(neighbor.getInDegree() == 0)
				{
					queue.offer(neighbor);
				}
			}
		}
		if(sorted.size() != vertices.size() && vertices.size() > 1)
		{
			throw new IllegalArgumentException("Graph has at least one cycle");
		}
		return sorted;
	}
	/**
	 * private helper method to reset all visited flags to false prior to search
	 */
	protected void resetVisited()
	{
		for(Vertex<T> vertex : vertices.values())
		{
			vertex.setVisited(false);
		}
	}
	/**
	 * private helper method to construct a path from BFS
	 *
	 * @param goal vertex to get path to from BFS
	 * @return the shortest path to the inputted goal
	 */
	private LinkedList<T> backtrackPath(Vertex<T> goal)
	{
		LinkedList<T> path = new LinkedList<>();
		Vertex<T> current = goal;
		while(current != null)
		{
			path.addFirst(current.getName());
			current = current.getPreviousVertex();
		}
		return path;
	}
	/**
	 * Generates a simple textual representation of this graph.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		for(Vertex<T> v : vertices.values())
			result.append(v + "\n");
		
		return result.toString();
	}
}