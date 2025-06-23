package assign07;

import java.util.LinkedList;
import java.util.Iterator;

/**
 * This generic class represents a vertex (AKA node) in a directed graph.
 * 
 * @author Wyatt Young
 * @version June 21, 2024
 */
public class Vertex<T> {
	// used to id the Vertex
	private T name;
	// boolean to mark visited in sort methods
	private boolean visited;
	// vertex where current vertex came from
	private Vertex<T> previousVertex;
	// counter for in-degree of a vertex
	private int inDegree;
	// adjacency list
	private LinkedList<Edge> adj;
	/**
	 * Creates a new Vertex object, using the given name.
	 * 
	 * @param name - string used to identify this Vertex
	 */
	public Vertex(T name)
	{
		this.name = name;
		this.adj = new LinkedList<Edge>();
		this.visited = false;
		this.inDegree = 0;
	}
	/**
	 * @return the string used to identify this Vertex
	 */
	public T getName() {
		return name;
	}
	/**
	 * Adds a directed edge from this Vertex to another.
	 * 
	 * @param otherVertex - the Vertex object that is the destination of the edge
	 */
	public void addEdge(Vertex otherVertex)
	{
		adj.add(new Edge(otherVertex));
		if(!this.equals(otherVertex)){
			otherVertex.inDegree++;
		}
	}
	/**
	 * @return a iterator for accessing the edges for which this Vertex is the source
	 */
	public Iterator<Edge> edges() {
		return adj.iterator();
	}
	/**
	 * helper method to set visited
	 */
	protected void setVisited(boolean visited)
	{
		this.visited = visited;
	}
	/**
	 *  helper method to get whether a vertex is visited
	 */
	protected boolean getVisited()
	{
		return this.visited;
	}
	/**
	 * helper method to set previous vertex
	 */
	protected void setPreviousVertex(Vertex<T> previousVertex)
	{
		this.previousVertex = previousVertex;
	}
	/**
	 *  helper method to get previous vertex
	 */
	protected Vertex<T> getPreviousVertex()
	{
		return this.previousVertex;
	}
	/**
	 * helper method to get indegree of a vertex
	 */
	protected int getInDegree()
	{return this.inDegree;}
	/**
	 * helper method to increment indegree
	 */
	protected void incrementInDegree()
	{
		this.inDegree++;
	}
	/**
	 * helper method to decrement indegree
	 */
	protected void decrementInDegree()
	{
		this.inDegree--;
	}
	/**
	 * Generates and returns a textual representation of this Vertex.
	 */
	public String toString() {
		return name.toString();
	}
}