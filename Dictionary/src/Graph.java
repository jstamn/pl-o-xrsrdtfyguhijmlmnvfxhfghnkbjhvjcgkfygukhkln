import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

////////////////////////////////////////////////////////////////////////////
// Semester:         CS400 Spring 2018
// PROJECT:          P3 Dictionary Graph
// FILES:            Graph.java
//                   GraphProcessor.java
//                   GraphTest.java
//                   WordProcessor.java
//
// USER:             tschmidt6@wisc.edu | Teryl Schmidt
//		    		 alsilverman3@wisc.edu | Avi Silverman
//		             jsoukup2@wisc.edu | Joe Soukup
//		    		 ssrivastav26@wisc.edu | Shashwat Srivastava
//					 jstamn@wisc.edu | Joshua Stamn
//
//
// Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs:             no known bugs
//
// Due:				2018 Apr 16, 2018 Graph.java 
//////////////////////////// 80 columns wide //////////////////////////////////

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    /**
     * Instance variables and constructors
     */
	final private HashMap<E, Set<E>> adjacencyList;
	
 	public Graph() {
		this.adjacencyList = new HashMap<>();
	}

	/**
	 * Add new vertex to the graph
	 * 
	 * Valid argument conditions:
	 * 1. vertex should be non-null
	 * 2. vertex should not already exist in the graph 
	 * 
	 * @param vertex the vertex to be added
	 * @return vertex if vertex added, else return null if vertex can not be added (also if valid conditions are violated)
	 */
    @Override
    public E addVertex(E vertex) {
    	// Can't add null vertex
        if (vertex == null) {
        	return null;
        }
	// No duplicate vertices
	if (this.adjacencyList.containsKey(vertex)) {
		return null;
	}
	// Add vertex
	this.adjacencyList.put(vertex, new HashSet<E>());
	return vertex;
    }

    /**
	 * Remove the vertex and associated edge associations from the graph
	 * 
	 * Valid argument conditions:
	 * 1. vertex should be non-null
	 * 2. vertex should exist in the graph 
	 *  
	 * @param vertex the vertex to be removed
	 * @return vertex if vertex removed, else return null if vertex and associated edges can not be removed (also if valid conditions are violated)
	 */
    @Override
    public E removeVertex(E vertex) {
	// Can't remove null
    	if (vertex == null) {
        	return null;
        }
	// Must exist in graph to remove
	if (!this.adjacencyList.containsKey(vertex)) {
		return null;
	}
	// Remove vertex
	this.adjacencyList.remove(vertex);
	for (E currentVertex: this.getAllVertices()) {
		this.adjacencyList.get(currentVertex).remove(vertex);
	}
	return vertex;
        
    }

    /**
	 * Add an edge between two vertices (edge is undirected and unweighted)
	 * 
	 * Valid argument conditions:
	 * 1. both the vertices should exist in the graph
	 * 2. vertex1 should not equal vertex2
	 *  
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @return true if edge added, else return false if edge can not be added (also if valid conditions are violated)
	 */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
	// Check validity of vertices before testing
	if (vertex1 == null || vertex2 == null || vertex1 == vertex2) {
		return false;
	}
	// Vertices must be in the graph to be able to tell if edge between them
	if (!this.adjacencyList.containsKey(vertex1) || !this.adjacencyList.containsKey(vertex2)) {
		return false;
	}
	//Undirected graph implementation so adding edge from V1 -> V2 also adds edge from V1 <- V2
	this.adjacencyList.get(vertex1).add(vertex2);
	this.adjacencyList.get(vertex2).add(vertex1);
	return true;  
    }    

    /**
	 * Remove the edge between two vertices (edge is undirected and unweighted)
	 * 
	 * Valid argument conditions:
	 * 1. both the vertices should exist in the graph
	 * 2. vertex1 should not equal vertex2
	 *  
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @return true if edge removed, else return false if edge can not be removed (also if valid conditions are violated)
	 */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
	// Check validity of vertices before testing
    	if (vertex1 == null || vertex2 == null || vertex1 == vertex2) {
		return false;
	}
	// Vertices must be in the graph to be able to remove edge between them
	if (!this.adjacencyList.containsKey(vertex1) || !this.adjacencyList.containsKey(vertex2)) {
		return false;
	}
	//Undirected graph implementation so removing edge from V1 -> V2 also removes edge from V1 <- V2
	this.adjacencyList.get(vertex1).remove(vertex2);
	this.adjacencyList.get(vertex2).remove(vertex1);
	return true;
        
    }

    /**
	 * Check whether the two vertices are adjacent
	 * 
	 * Valid argument conditions:
	 * 1. both the vertices should exist in the graph
	 * 2. vertex1 should not equal vertex2
	 *  
	 * @param vertex1 the first vertex
	 * @param vertex2 the second vertex
	 * @return true if both the vertices have an edge with each other, else return false if vertex1 and vertex2 are not connected (also if valid conditions are violated)
	 */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
	// Check validity of vertices before testing
    	if (vertex1 == null || vertex2 == null || vertex1 == vertex2) {
		return false;
	}  
	// Vertices must be in the graph to be able to tell if they are adjacent
	if (!this.adjacencyList.containsKey(vertex1) || !this.adjacencyList.containsKey(vertex2)) {
		return false;
	}
	return this.adjacencyList.get(vertex1).contains(vertex2);
    }

    /**
	 * Get all the neighbor vertices of a vertex
	 * 
	 * Valid argument conditions:
	 * 1. vertex is not null
	 * 2. vertex exists
	 * 
	 * @param vertex the vertex
	 * @return an iterable for all the immediate connected neighbor vertices
	 */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        return this.adjacencyList.get(vertex);
    }

    /**
	 * Get all the vertices in the graph
	 * 
	 * @return an iterable for all the vertices
	 */
    @Override
    public Iterable<E> getAllVertices() {
        return this.adjacencyList.keySet();
    }

}
