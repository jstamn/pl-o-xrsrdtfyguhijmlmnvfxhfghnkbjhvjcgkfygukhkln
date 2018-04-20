import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

public class GraphProcessorTest {
    
    GraphProcessor gproc;
    String expected = null;
    String actual = null;

	/**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        gproc = new GraphProcessor();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
	/**
	 * Checks if the populateGraph(String ) works fine and does not throw an error
	 * 	when called upon a file that contains words without any duplicates
	 * Passes if number of unique words are added i.e., 
	 * 	the value returned by populateGraph(String ) is equal to the number of
	 * 	unique words in the file
	 */
	@Test
	public void test01_total_vertices_on_small_graph_without_duplicates() {;
		expected = "" + 12; // pre-calculated number of words in the file
		actual = "" + gproc.populateGraph("smallDictionary.txt");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Checks if the populateGraph(String ) works fine and does not throw an error
	 * 	when called upon a file that contains 441 words with 14 duplicates
	 * Therefore, only 427 must be added
	 * Passes if number of unique words are added i.e., 
	 * 	the value returned by populateGraph(String ) is equal to the number of
	 * 	unique words in the file
	 */
	@Test
	public void test02_total_vertices_on_medium_graph_with_duplicates() {
		expected = "" + 427;
		// the file mediumDictionary.text contains 441 words but 14 of them are repeated and hence won't be added
		actual = "" + gproc.populateGraph("mediumDictionary.txt");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Checks if the populateGraph(String ) adds appropriate number of vertices to
	 * the graph when a large file (containing 2643 words) is used to populate the 
	 * graph. Only 2466 of the words are added since 177 of them are duplicates. 
	 * Ensures that the code does not fail with larger file sizes
	 * Passes if number of unique words are added i.e., 
	 * 	the value returned by populateGraph(String ) is equal to the number of
	 * 	unique words in the file
	 */
	@Test
	public void test03_total_vertices_on_large_graph_with_duplicates() {
		expected = "" + 2466;
		// the file largeDictionary contains 2643 words but 177 of them are duplicates 
		actual = "" + gproc.populateGraph("largeDictionary.txt");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Checks the edge case where the getShortestPath() is called on
	 * an empty graph
	 * Passes if an empty array list is returned by getShortestPath(String , String)
	 */
	@Test
	public void test04_path_on_empty_graph() {
		expected = (new ArrayList<String>()).toString();
		actual = "" + gproc.getShortestPath("no", "words");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Checks if the expected path between two particular words
	 * matches the actual path computed by the GraphProcessor class
	 */
	@Test
	public void test05_path_on_small_graph() {
		gproc.populateGraph("smallDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "[CAT, HAT, HEAT, WHEAT]"; // pre calculated
		actual = "" + gproc.getShortestPath("cat", "wheat");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Checks if the expected path between two particular words
	 * 	matches the actual path computed by the GraphProcessor class.
	 * Here, the size of the input is deliberately long so that it can 
	 * 	test the robustness of the code. 
	 * 	
	 */
	@Test
	public void test06_path_on_medium_graph() {
		gproc.populateGraph("mediumDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "[CHARGE, CHANGE, CHANCE, CHANCY, CHANTY, SHANTY, SHANNY, SHINNY, WHINNY, WHINEY,"
				+ " WHINER, SHINER, SHIVER, SHAVER, SHARER, SCARER, SCALER, SEALER, HEALER, HEADER, READER]";
		// pre calculated
		actual = "" + gproc.getShortestPath("charge", "reader");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Checking the edge case when getShortestDistance(String, String)
	 * 	is called on an empty graph
	 */
	@Test
	public void test07_shortest_path_length_on_empty_graph() {
		expected = "" + -1;
		actual = "" + gproc.getShortestDistance("not", "existent");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Tests the getShortestDistance(String, String) on two words
	 * 	in the small dictionary
	 */
	@Test
	public void test08_shortest_path_length_on_small_graph() {
		gproc.populateGraph("smallDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "" + 3; // pre-calculated
		actual = "" + gproc.getShortestDistance("at", "neat");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Tests the getShortestDistance(String, String) on two words
	 * 	in the medium dictionary
	 * The test has been repeated for multiple pre-computed values
	 */
	@Test
	public void test09_shortest_path_length_on_medium_graph_1() {
		gproc.populateGraph("mediumDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "" + 49; // pre-calculated
		actual = "" + gproc.getShortestDistance("COMEDO", "CHARGE");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Tests the getShortestDistance(String, String) on two words
	 * 	in the medium dictionary
	 * The test has been repeated for multiple pre-computed values
	 */
	@Test
	public void test10_shortest_path_length_on_medium_graph_2() {
		gproc.populateGraph("mediumDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "" + 78;
		actual = "" + gproc.getShortestDistance("CHARGE", "GIMLETS");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Tests the getShortestDistance(String, String) on two words
	 * 	in the medium dictionary
	 * The test has been repeated for multiple pre-computed values
	 */
	@Test
	public void test11_shortest_path_length_on_medium_graph_3() {
		gproc.populateGraph("mediumDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "" + 2;
		actual = "" + gproc.getShortestDistance("BELLIES", "JOLLIES");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Tests the getShortestDistance(String, String) on two words
	 * 	in the medium dictionary
	 * The test has been repeated for multiple pre-computed values
	 */
	@Test
	public void test12_shortest_path_length_on_medium_graph_4() {
		gproc.populateGraph("mediumDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "" + 26;
		actual = "" + gproc.getShortestDistance("DEFINE", "SHINNY");
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * tests on whether we get the same path on inverting the arguments/parameters
	 * 	in getShortestPath(String, String)
	 */
	@Test
	public void test13_same_path_same_arguments_different_order_on_getShortestPath() {
		gproc.populateGraph("mediumDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "[CHARGE, CHANGE, CHANCE, CHANCY, CHANTY, SHANTY, SHANNY, SHINNY, WHINNY, WHINEY,"
				+ " WHINER, SHINER, SHIVER, SHAVER, SHARER, SCARER, SCALER, SEALER, HEALER, HEADER, READER]";
		actual = "" + gproc.getShortestPath("charge", "reader");
		assertEquals("Expected: " + expected + ", Actual: " + actual + " PATH is incorrect", expected, actual);
		String actual2 = "" + gproc.getShortestPath("reader", "charge");
		boolean result = false;
		if(actual2.equals(actual))
			result = true;
		assertEquals("Expected: " + actual + ", Actual: " + actual2 + " PATH is incorrect", true, result);
	}
	
	/**
	 * tests on whether we get the same path on inverting the arguments/parameters
	 * 	in getShortestDistance(String, String)
	 */
	@Test
	public void test14_same_path_same_arguments_different_order_on_getShortestDistance() {
		gproc.populateGraph("mediumDictionary.txt");
		gproc.shortestPathPrecomputation();
		expected = "20";
		actual = "" + gproc.getShortestDistance("charge", "reader");
		assertEquals("Expected: " + expected + ", Actual: " + actual + " PATH is incorrect", expected, actual);
		String actual2 = "" + gproc.getShortestDistance("reader", "charge");
		boolean result = false;
		if(actual2.equals(actual))
			result = true;
		assertEquals("Expected: " + actual + ", Actual: " + actual2 + " PATH distance is incorrect", true, result);
	}
	/**
	 * Checking if a path exists between every pair of the vertices
	 * @file pathExists.txt contains all vertices which have a path linked to each other
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test15_checking_if_path_exists_between_each_word() throws FileNotFoundException {
		gproc.populateGraph("pathExists.txt");
		gproc.shortestPathPrecomputation();
		ArrayList<String> str = new ArrayList<String>();
		File file = new File("pathExists.txt");
		Scanner in = new Scanner(file);
		while(in.hasNextLine()) {
			str.add(in.nextLine());
		}
		// checking for every file
		for(int i = 0; i < str.size(); i++)
			for(int j = 0; j < str.size(); j++) {
				try {
					if(i == j)
						continue;
					int value = gproc.getShortestDistance(str.get(i), str.get(j));
					if(value < 1)
						fail("path not found for words " + str.get(i) + " and " + str.get(j));
				}
				catch(Exception e) {
					fail("Exception encountered");
				}
			}
		in.close();
	}
	
	/**
	 * Tests the code on a file that contains all vertices
	 * 	without edges
	 * Therefore, no path must be found
	 * @throws FileNotFoundException 
	 */
	@Test
	public void test16_no_path_found() throws FileNotFoundException {
		int count = gproc.populateGraph("noEdges.txt");
		gproc.shortestPathPrecomputation();
		ArrayList<String> str = new ArrayList<String>();
		File file = new File("noEdges.txt");
		Scanner in = new Scanner(file);
		while(in.hasNextLine()) {
			str.add(in.nextLine());
		}
		// checking for all vertex pairs
		for(int i = 0; i < count; i++)
			for(int j = 0; j < count; j++) {
				// checking if path exists or not
				if(gproc.getShortestDistance(str.get(i), str.get(j)) != -1)
					fail("Edge found between " + str.get(i) + " and " + " " + str.get(j));
			}
		in.close();
	}
	
	/**
	 * Checks if the WordProcessor.getWordSteam(String) properly trims and 
	 * 	changes words to Upper Case
	 */
	@Test
	public void test17_shortest_path_after_removing_some_words() {
		Stream<String> stream;
        try {
            stream = WordProcessor.getWordStream("mediumDictionary.txt");
            List<String> str = stream.collect(Collectors.toList());
            for(int i = 0; i < str.size(); i++)
            	if(!str.get(i).toUpperCase().equals(str.get(i)) || !str.get(i).trim().equals(str.get(i)))
            		fail(str.get(i) + " is Not trimmed or is in Lower case");
        } catch (IOException e) {
        }
	}
	
	/**
	 * Testing all the possible combinations with WordProcessor.isAdjacent(String, String)
	 */
	@Test
	public void test18_isAdjacent_WordProcessor() {
		assertEquals("Expected: " + true + ", Actual: " + false + " calling on WHAT and WHA", true, WordProcessor.isAdjacent("WHAT", "WHA"));
		assertEquals("Expected: " + true + ", Actual: " + false + " calling on WHA and WHAT", true, WordProcessor.isAdjacent("WHA", "WHAT"));
		assertEquals("Expected: " + true + ", Actual: " + false + " calling on WHAT and WHAR", true, WordProcessor.isAdjacent("WHAT", "WHAR"));
		assertEquals("Expected: " + true + ", Actual: " + false + " calling on WHRA and WHA", true, WordProcessor.isAdjacent("WHAR", "WHA"));
		assertEquals("Expected: " + true + ", Actual: " + false + " calling on WRHA and WHA", true, WordProcessor.isAdjacent("WRHA", "WHA"));
		assertEquals("Expected: " + true + ", Actual: " + false + " calling on RWHA and WHA", true, WordProcessor.isAdjacent("RWHA", "WHA"));
		assertEquals("Expected: " + false + ", Actual: " + true + " calling on WHAT and ASD", false, WordProcessor.isAdjacent("WHAT", "ASD"));
	}
	
	/**
	 * Tests if populateGraph(String ) could still function properly i.e., add 
	 * 	appropriate number of vertices even when called with two different files
	 */
	@Test
	public void test19_adding_words_to_populated_graph() {
		int num = gproc.populateGraph("smallDictionary.txt");
		gproc.shortestPathPrecomputation();
		num += gproc.populateGraph("mediumDictionary.txt");
		actual = "" + num;
		expected = "437"; // total number of unique digits between the two files
		assertEquals("Expected: " + expected + ", Actual: " + actual, expected, actual);
	}
	
	/**
	 * Checks the edge case that on passing an non existent file,
	 * 	the populateGraph(String) returns -1 without throwing any
	 * 	exceptions
	 */
	@Test
	public void test20_populating_graph_with_nonexistent_file() {
        int actual = gproc.populateGraph("nonexistent_file.txt");
        gproc.shortestPathPrecomputation();
        int expected = -1;
        if (expected != actual) {
            System.out.println("Failed: expected: " + expected + " actual: " + actual);
            fail("expected: " + expected + " actual: " + actual);
        }
    }
	
	/**
	 * Tests the WordProcessor.getWordStream(String) on whether it
	 * 	throws an error on an existent file
	 */
	@Test
	public void test21_getWordStream_throws_IOException_on_nonexistent_file() {
        Stream<String> stream;
        try {
            stream = WordProcessor.getWordStream("nonexistent_file.txt");
            System.out.println("Failed: expected: " + "exception thrown" + " actual: " + "no exception thrown");
            fail("expected: " + "exception thrown" + " actual: "+"no exception thrown");   
        } catch (IOException e) {
        }
    }
	
	/**
	 * Tests if the getShortestPath(String, String) gives the appropriate result
	 * 	when the same strings are passed
	 */
	@Test
	public void test22_getShortestPath_returns_empty_on_equal_parameters() { 
        gproc.populateGraph("smallDictionary.txt"); 
        gproc.shortestPathPrecomputation();
        List<String> actualList = gproc.getShortestPath("dog","dog");      
        List<String> expectedList = new ArrayList<String>();
        if (expectedList.size() != actualList.size()) {
            System.out.println("Failed: expected: " + expectedList + " actual: " + actualList);
            fail("expected: " + expectedList + " actual: " + actualList);
        }
    }
}
