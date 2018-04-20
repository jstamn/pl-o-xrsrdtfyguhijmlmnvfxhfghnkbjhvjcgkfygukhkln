import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
////////////////////////////////////////////////////////////////////////////
//Semester:         CS400 Spring 2018
//PROJECT:          P3 Dictionary Graph
//FILES:            Graph.java
//GraphProcessor.java
//GraphTest.java
//WordProcessor.java
//
//USER:             tschmidt6@wisc.edu | Teryl Schmidt
//alsilverman3@wisc.edu | Avi Silverman
//jsoukup2@wisc.edu | Joe Soukup
//ssrivastav26@wisc.edu | Shashwat Srivastava
//jstamn@wisc.edu | Joshua Stamn
//
//
//Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
//Bugs:             no known bugs
//
//Due:				2018 Apr 16, 2018 GraphProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////
/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and edges.
 * @see #populateGraph(String)
 *  - loads a dictionary of words as vertices in the graph.
 *  - finds possible edges between all pairs of vertices and adds these edges in the graph.
 *  - returns number of vertices added as Integer.
 *  - every call to this method will add to the existing graph.
 *  - this method needs to be invoked first for other methods on shortest path computation to work.
 * @see #shortestPathPrecomputation()
 *  - applies a shortest path algorithm to precompute data structures (that store shortest path data)
 *  - the shortest path data structures are used later to 
 *    to quickly find the shortest path and distance between two vertices.
 *  - this method is called after any call to populateGraph.
 *  - It is not called again unless new graph information is added via populateGraph().
 * @see #getShortestPath(String, String)
 *  - returns a list of vertices that constitute the shortest path between two given vertices, 
 *    computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String)
 *  - returns distance (number of edges) as an Integer for the shortest path between two given vertices
 *  - this is computed using the precomputed data structures computed as part of {@link #shortestPathPrecomputation()}.
 *  - {@link #shortestPathPrecomputation()} must have been invoked once before invoking this method.
 *  
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

	/**
     * @param graph Graph which stores the dictionary words and their associated connections
     * @param paths the data structure that stores the shortest path from every vertex to every other vertex
     * 				Here, I plan to use a 3D array list, which is basically a 2 dimensional array list that 
     * 					contains array lists as values (which are the paths)
     * @param vertices stores the vertices in the graph
     * @param numVertices stores the number of vertices 
     */
	private ArrayList<ArrayList<ArrayList<String>>> paths;
    private GraphADT<String> graph;
    private ArrayList<Vertex<String>> vertices;
    private int numVertices;
    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
        vertices = new ArrayList<>();
        this.paths = new ArrayList<ArrayList<ArrayList<String>>>();
        numVertices = 0;
    }
        
    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the dictionary as vertices
     * and finding and adding the corresponding connections (edges) between 
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph.
     * Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent {@link WordProcessor#isAdjacent(String, String)}
     * If a pair is adjacent, adds an undirected and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filepath) {
        int count = 0;
    	try {
    		// gets the stream of the words from the file
    		Stream<String> stream = WordProcessor.getWordStream(filepath);
    		// converts the stream into a list of words
    		List<String> listOfLines = stream.collect(Collectors.toList());
			/*
			 * for every word:
			 * 	1. a vertex is added to the graph
			 * 	2. an edge is added, if it is adjacent to any other vertices
			 * 	3. a vertex is added to the list vertices
			 * 	4. the value of numVertices is incremented
			 * 	5. adding a new column to the data structure (3D array list)
			 */
			for(String word: listOfLines) {
				String added = graph.addVertex(word);
				if(added != null) { // only when the vertex is successfully added
					Vertex<String> v = new Vertex<String>(word);
					vertices.add(v);
					count++;
					numVertices++;
					// adding an edge if it is adjacent to any other vertex
					for(String vertices: graph.getAllVertices()) {
						if(vertices.equals(word))
							continue;
						if(WordProcessor.isAdjacent(word, vertices)) {
							graph.addEdge(word, vertices);
						}
					}
					// adding a new column to path 
					paths.add(new ArrayList<ArrayList<String>>());
				}
			}
		} catch (IOException e) {
			count = -1; // indicates that an IOException has been encountered
		}
    	return count;
    }

    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             neat
     *             wheat
     *             kit
     *  shortest path between cat and wheat is the following list of words:
     *     [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     */
    public List<String> getShortestPath(String word1, String word2) {
    	// if the graph is empty or contains less than two words
    	if(vertices.size() < 2 || word1 == null || word2 == null) 
    		return new ArrayList<String>();
    	word1 = word1.toUpperCase().trim();
        word2 = word2.toUpperCase().trim();
    	if(word1.equals(word2)
    			|| word1.equals("") || word2.equals("") ) // if the two words are the same
    		return new ArrayList<String>();
        int location1 = -1; // to store the location of word1 in the array vertices
        int location2 = -1; // to store the location of word2 in the array vertices
        int counter = 0; // to track the location of the respective words 
        for(Vertex<String> v: vertices) {
        	if(v.getVal().equals(word1))
        		location1 = counter;
        	if(v.getVal().equals(word2))
        		location2 = counter;
        	counter++;
        }
        // if the any of the two words are not contained in the graph
        if(location1 == -1 || location2 == -1)
        	return null;
        // returns the intersection of the two words in paths, which is basically the
        // shortest path from word1 to word2
        return paths.get(location2).get(location1);
    }
    
    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary,
     *             cat
     *             rat
     *             hat
     *             heat
     *             wheat
     *             kit
     *  distance of the shortest path between cat and wheat, [cat, hat, heat, wheat]
     *   = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance
     */
    public Integer getShortestDistance(String word1, String word2) {
        return getShortestPath(word1, word2).size() - 1;
        // the shortest distance will just be the size of the array list 
        // containing the shortest path - 1
    }
    
    /**
     * Computes shortest paths and distances between all possible pairs of vertices.
     * This method is called after every set of updates in the graph to recompute the path information.
     * Any shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     * 
     * 
     * This method must be called after every addition or removal of vertices
     */
    public void shortestPathPrecomputation() {
    	// adding a new row to path by adding a new cell to each column
    	for(int i = 0; i < vertices.size(); i++) {
			ArrayList<String> aa = new ArrayList<String>();
			for(int j = 0; j < vertices.size(); j++)
			paths.get(i).add(j, aa);
    	}
    	/*
    	 * counter and i contain the same values but serve different purposes
    	 * i is used to iterate over the vertices array list
    	 * counter is used to determine to the elements in the data structure for which
    	 * 	a path needs to be calculated
    	 * (A reflection across the main diagonal of the array list is observed)
    	 */
    	int counter;
		int i = counter = 0;
		/*
		 * For every column:
		 * 	1. calculates the shortest path through Dijkstra's algorithm for every vertex specifically 
		 * 		vertex at i
		 * 	2. then for every row, it builds shortest path from vertex at i to vertex at j 
		 * 		by calling buildPath()
		 */
    	for(ArrayList<ArrayList<String>> v: paths) {
    		dijkstra(vertices.get(i));
    		for(int j = counter; j < v.size(); j++) {
    			ArrayList<String> finalPath = buildPath(vertices.get(i), vertices.get(j));
    				v.set(j, finalPath);
    				paths.get(j).set(i, finalPath);
    		}
    		i++;
    		counter++;
    		// setting the values of the vertices back to their default values
    		// so that the Dijkstra's could be called on them again
    		for(Vertex<String> ver: vertices)
        		ver.setDefault(); 
    	}
    }
    
    /**
     * @precondition start must have been initialized
     * Applies the Djikstra's algorithm to find the shortest path from the starting vertex 
     * 	by updating the predecessors
     * @param start the starting vertex
     */
    private void dijkstra(Vertex<String> start) {
    	start.setWeight(0); // set the weight of the start to zero
    	PriorityQueue<Vertex<String>> pq = new PriorityQueue<Vertex<String>>(new VertexComparator());
    	// Comparator passed as parameter so that the Priority Queue compares elements accordingly
    	pq.add(start); // add the start vertex to the Priority Queue
    	while(!pq.isEmpty()) { // runs until the Priority Queue is empty 
    		Vertex<String> min = pq.poll(); // removes the element of highest priority
    		min.setVisited(true); // sets the removed element to visited
    		for(String str: graph.getNeighbors(min.getVal())) { // for every neighbor of the vertex removed
    			Vertex<String> neighbor = null;
    			/*
    			 * finds the vertex in the vertices array list that corresponds to each neighbor
    			 */
    			for(Vertex<String> v: vertices) {
    				if(v.getVal().equals(str)) {
    					neighbor = v;
    					break;
    				}
    			}
    			// for every unvisited successor/neighbor of min
    			if(!neighbor.isVisited()) {
    				// checks if the weight can be reduced
    				if(neighbor.getWeight() > min.getWeight() + 1) {
    					/*
    					 * updates the weight, predecessor and adds the neighbor to the Priority Queue 
    					 */
    					neighbor.setWeight(min.getWeight() + 1);
    					neighbor.setPred(min);
    					pq.add(neighbor);
    					// if neighbor is already in the PQ, we just update the its total weight
    				}
    			}
    		}
    	}
    }
    
    /**
     * This method builds a path from the start vertex till the end vertex
     * using a Linked List to store the vertices 
     * @param start the vertex from where the path needs to be constructed
     * @param end the vertex till the path needs to be constructed
     * @return the path as an Array List
     */
    private ArrayList<String> buildPath(Vertex<String> start, Vertex<String> end) {
    	Stack<String> stack = new Stack<String>();
    	Vertex<String> current = end;
    	ArrayList<String> array = new ArrayList<String>();
    	// adding the vertices to the beginning of the Linked List
    	while(current != start && current != null) {
    		stack.add(current.getVal());
    		current = current.getPred();
    		}
    	stack.add(start.getVal());
    	// if path does not exist or start and end are the same
    	if(stack.size() == 1 || current == null || current != start)
    		return new ArrayList<String>();
    	while(!stack.isEmpty()) {
    		array.add(stack.pop());
    	}
    	return array;
    }
    
/**
 * The Vertex class represents each vertex of the graph 
 * @param <T> generic data type
 */
    class Vertex<T>{
    	/**
    	 * @param visited keeps track of whether the vertex has been visited or not
    	 * @param val stores the value of the vertex
    	 * @param weight stores the total weight of the vertex
    	 * @param pred stores the predecessor of the vertex
    	 */
    	public Vertex(T val) {
    		visited = false;
    		weight = Integer.MAX_VALUE;
    		pred = null;
    		this.val = val;
    	}
    	// getters and setters for the global data members
    	public T getVal() {
			return val;
		}
    	public boolean isVisited() {
			return visited;
		}
		public void setVisited(boolean visited) {
			this.visited = visited;
		}
		public int getWeight() {
			return weight;
		}
		public void setWeight(int weight) {
			this.weight = weight;
		}
		public Vertex<String> getPred() {
			return pred;
		}
		public void setPred(Vertex<String> pred) {
			this.pred = pred;
		}
		/*
		 * sets all the values to their default values
		 */
		public void setDefault() {
			visited = false;
    		weight = Integer.MAX_VALUE;
    		pred = null;
		}
		private boolean visited;
		private int weight;
    	private Vertex<String> pred;
    	private T val;
	}
     /**
      * This class defines a specific way by which the Vertex elements need to be
      * compared (here, by the Priority Queue)
      */
    class VertexComparator implements Comparator<Vertex<String>> {
		/*
		 * First compares with respect to the weight and then with respect to the
		 * String value that it contains
		 */
    	@Override
		public int compare(Vertex<String> o1, Vertex<String> o2) {
			Integer comp1 = o1.getWeight();
			Integer comp2 = o2.getWeight();
			if(comp1.compareTo(comp2) == 0) {
				return o1.getVal().compareTo(o2.getVal());
			}
			else
				return comp1.compareTo(comp2);
		}
    }
    
}
