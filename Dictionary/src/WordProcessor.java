import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains some utility helper methods
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
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
public class WordProcessor {
	
	/**
	 * Gets a Stream of words from the filepath.
	 * 
	 * The Stream should only contain trimmed, non-empty and UPPERCASE words.
	 * 
	 * @see <a href="http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html">java8 stream blog</a>
	 * 
	 * @param filepath file path to the dictionary file
	 * @return Stream<String> stream of words read from the filepath
	 * @throws IOException exception resulting from accessing the filepath
	 */
	public static Stream<String> getWordStream(String filepath) throws IOException {
		/**
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html">java.nio.file.Files</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Paths.html">java.nio.file.Paths</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html">java.nio.file.Path</a>
		 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">java.util.stream.Stream</a>
		 * 
		 * class Files has a method lines() which accepts an interface Path object and 
		 * produces a Stream<String> object via which one can read all the lines from a file as a Stream.
		 * 
		 * class Paths has a method get() which accepts one or more strings (filepath),  
		 * joins them if required and produces a interface Path object
		 * 
		 * Combining these two methods:
		 *     Files.lines(Paths.get(<string filepath>))
		 *     produces
		 *         a Stream of lines read from the filepath
		 * 
		 * Once this Stream of lines is available, you can use the powerful operations available for Stream objects to combine 
		 * multiple pre-processing operations of each line in a single statement.
		 * 
		 * Few of these features:
		 * 		1. map( )      [changes a line to the result of the applied function. Mathematically, line = operation(line)]
		 * 			-  trim all the lines
		 * 			-  convert all the lines to UpperCase
		 * 			-  example takes each of the lines one by one and apply the function toString on them as line.toString() 
		 * 			   and returns the Stream:
		 * 			        streamOfLines = streamOfLines.map(String::toString) 
		 * 
		 * 		2. filter( )   [keeps only lines which satisfy the provided condition]  
		 *      	-  can be used to only keep non-empty lines and drop empty lines
		 *      	-  example below removes all the lines from the Stream which do not equal the string "apple" 
		 *                 and returns the Stream:
		 *      			streamOfLines = streamOfLines.filter(x -> x != "apple");
		 *      			 
		 * 		3. collect( )  [collects all the lines into a java.util.List object]
		 * 			-  can be used in the function which will invoke this method to convert Stream<String> of lines to List<String> of lines
		 * 			-  example below collects all the elements of the Stream into a List and returns the List:
		 * 				List<String> listOfLines = streamOfLines.collect(Collectors::toList); 
		 * 
		 * Note: since map and filter return the updated Stream objects, they can chained together as:
		 * 		streamOfLines.map(...).filter(a -> ...).map(...) and so on
		 */
		//Went over in lecture
		// Remove White Space
		Stream<String> wordStream = Files.lines(Paths.get(filepath)).map(String::trim);
		//Remove empty lines
		wordStream = wordStream.filter(x -> x != null && !x.equals(""));
		//Change to upper case
		wordStream = wordStream.map(String::toUpperCase);
		return wordStream;
	}
	
	/**
	 * Adjacency between word1 and word2 is defined by:
	 * if the difference between word1 and word2 is of
	 * 	1 char replacement
	 *  1 char addition
	 *  1 char deletion
	 * then 
	 *  word1 and word2 are adjacent
	 * else
	 *  word1 and word2 are not adjacent
	 *  
	 * Note: if word1 is equal to word2, they are not adjacent
	 * 
	 * @param word1 first word
	 * @param word2 second word
	 * @return true if word1 and word2 are adjacent else false
	 */
	public static boolean isAdjacent(String word1, String word2) {
		if(word1 == null || word2 == null) 
			return false;
		//tests equal
		if(word1.equals(word2)) {
			return false;
		}
		boolean result = false;
		// converting the words word1 and word2 into an array
		char[] word1Array = word1.toCharArray();
		char[] word2Array = word2.toCharArray(); 	
		
		//checking for 1 char replacement
		if (word1Array.length == word2Array.length) {
			int difference = 0;
			/*
			 * Checking if the difference in the two words is only at
			 * 	a single character.
			 */
			for (int i = 0; i < word1Array.length; i++) {
				if (word1Array[i] != word2Array[i]) {
					difference += 1;
				}
			}
			if (difference == 1) {
				return true;
			} else {
				return false;
			}
		}
		// checking if there is only 1 char addition or deletion
		if(word1Array.length - word2Array.length == 1)
			result = checkAdditionOrDeletion(word1Array, word2Array);
		if(word2Array.length - word1Array.length == 1)
			result = checkAdditionOrDeletion(word2Array, word1Array);
		return result;	
	}
	
	/**
	 * @precondition the length of the two arrays must differ by only 1
	 * @param biggerWord array containing the bigger word
	 * @param smallerWord array containing the smaller word
	 * @return true if there is a difference of just a single character in
	 * 			the two words
	 */
	private static boolean checkAdditionOrDeletion(char[] biggerWord, char[] smallerWord) {
		int j = 0;
		// traversing through each each element of both the words
		for (int i = 0; i < biggerWord.length; i++) {
			// handling the situation when the difference is only in one last alphabet
			// which might otherwise result in an ArrayIndexOutOfBoundsError 
			if(i == biggerWord.length - 1) {
				if(biggerWord.length - 1 - j == 0)
					break;
			}
			// incrementing when the alphabets that are present in both the words  
			if (biggerWord[i] == smallerWord[j]) {
				j++;
			}
		}
		if (biggerWord.length - j == 1){
			return true;
		}
		return false;
	}
}
