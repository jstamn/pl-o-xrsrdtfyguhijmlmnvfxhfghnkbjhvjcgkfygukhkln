# qwertyuiop

# TODO

- ~Graph~

- WordProcessor
  - ~StringStream Method~
  - IsAdjacent Method
  
  
- GraphProcessor
  - ~Populate Method~
  - Get Shortest Path Method
  - ~Get Shortest Distance Method~
  - Precompute all paths Method
  
- GraphProcessorTest

# Project Info

Dictionary Graph
DUE before 10:00 PM on Monday, April 16th

Announcements | Overview | Learning Outcomes | Description | Specifications | Files | Steps | Submission

Announcements (and answers to FAQs)
Corrections, clarifications, and other announcements regarding this programming assignment will be found below.
4/9 GraphProcessor.java: method header updated for populateGraph method.
4/8 GraphProcessor.java: method headers updated for getShortestPath and getShortestDistance methods.
4/4 Links for files: word_list.txt and few_interesting_combos.txt are live now.
word_list.txt is input dictionary file to the program's GraphProcessor->populateGraph() method.
few_interesting_combos.txt contains the shortest distances between a few words available in the word_list.txt
3/22 Program assigned. Watch for further announcements as we get questions from students.
Use Piazza (Links to an external site.)Links to an external site. to ask questions about the assignment.
TAs will be available in the CS Labs (See lab hours schedule)
REMINDER: Students caught posting coursework online (i.e. for seeking help or a paid programmer) will get a zero on the assignment for a first offense and may fail course for multiple offense. (Student Conduct)


Overview:
You will build a graph from a list of words.  In the graph created, each word is a vertex and edges exists between words that differ from each other by one simple transition (as described below).  Once created, you will implement WordProcessor and GraphProcessor that uses a dictionary graph and shortest path algorithm to find the shortest path between two words. 

A GraphTest class is provided to you.  But, you must define a GraphProcessorTest class in addition to the classes needed for your solution.


Learning Outcomes:
Implement generic Graph in Java

Write a program to create a graph of dictionary words

Implement shortest distance path algorithm (Dijkstra's or Floyd-Warshall recommended)

Introduce you to Java's New IO (nio) types
java.nio.file.Files (Links to an external site.)Links to an external site.
java.nio.file.Paths (Links to an external site.)Links to an external site.
Learn and use Java 8 Streams (filters,map,collect) and Lambda expressions.
java.util.stream.Stream (Links to an external site.)Links to an external site.
Oracle: Processing Data with Java SE 8 Streams, Part 1 (Links to an external site.)Links to an external site.
Try Test Driven Development by writing tests in GraphProcessorTest to help develop WordProcessor and GraphProcessor functionality.

Description:
You are given a dictionary of words. You have to implement a graph where the vertices are the words in the dictionary and the edges denote a difference of one character between two vertices. Differences can be any one of the below 3 types:

Substitution of 1 character
Addition of 1 character
Deletion of 1 character
(Note: No duplicate vertices are allowed in the graph.)

In other words, an edge exists between two words if and only if one word can be converted to another with exactly one modification (1 substitution or 1 addition or 1 deletion). Below image shows a diagrammatic representation of graph for a dictionary of 9 words. 

Word graph.png

 

For this assignment, you are required to

implement a generic GraphADT interface in the class Graph.
implement the GraphProcessor class which contains method to create a graph from a dictionary file, method to precompute shortest path data structures and methods to quickly calculate shortest distance/path between 2 words, using the precomputed shortest path data structures. 
implement methods of WordProcessor, a utility class which contains methods to read a stream of words from a dictionary file and to check whether two words are adjacent or not. Implementing them will help you streamline the design for GraphProcessor class.
implement GraphProcessorTest class (described next)
By now, you should be familiar with how to write unit tests. Since no main method is provided, you'll need to test your implementation via the Junit  classes. GraphTest.java is provided to you which tests all the common use-cases for Graph.java. You are welcome to add more tests to GraphTest.java to cover any remaining use-cases.  You must write one JUnit unit test class - GraphProcessorTest.java - to test the public methods of GraphProcessor. 

Go through the source code and the comments to get detailed information for each class.

All files must be named correctly (case-sensitive). You may define and submit other package level (not public or private) classes as needed and you may add private members and methods to your classes. 

Java8 Stream<T>
class WordProcessor has a public static method, getWordStream() which returns a java.util.stream.Stream object (check the code for more details).

Streams were introduced in Java8 to adopt functional programming concepts that help reduce the complexity of the code and make it more intuitive. Even though the names are similar, Java 8 streams are completely different from InputStream and OutputStream from Java I/O.

 java.util.stream.Stream represents a sequence of elements on which one or more operations can be performed. There are intermediate and terminal stream operations. While terminal operations return a result of a certain type, intermediate operations return the stream itself so you can chain multiple method calls in a row.

Intermediate stream operations are lazy. This means that they will be invoked only if it is necessary for the terminal operation execution.

Stream operations can either be executed in sequential or parallel execution mode. In the assignment, you should use the sequential execution.

A wide variety of stream operations are available. We have added comments to help you use these three for this assignment:

filter
map
collect
Javadoc reference (Links to an external site.)Links to an external site. for java.util.stream.Stream

Tutorial reference (Links to an external site.)Links to an external site. for Java 8 Stream API


Files
Note: updates to these files are possible, and will be posted in announcements on this page. p4.zip contains the following files:

GraphADT.java (provided interface - do NOT EDIT)
word_list.txt - a file with words for your program to read
few_interesting_combos.txt - the results you can expect for some word pairs given the above word_list.txt file as input.
Do EDIT and SUBMIT the following:

Graph.java (starter class - do EDIT and SUBMIT) 
GraphTest.java (Junit class - may EDIT and do SUBMIT - covers common testcases)
WordProcessor.java  (starter class - do EDIT and SUBMIT - define utility methods for preprocessing)
GraphProcessor.java  (starter class - do EDIT and SUBMIT - define operations to be performed on a graph with String vertices here)
GraphProcessorTest.java (Junit class - must CREATE and SUBMIT - define testcases for GraphProcessor).  
Test-Driven Development - try writing the tests first and then write the code to pass the tests.  Be sure to submit any data (txt) files required by your test classes.

 


Specifications (requirements)
Write Java source code that is consistent, professional looking, and easy to read:
a file header with 
assignment name
author(s) and email addresses
due date
other source credits
known bugs
comments for all public members of the class
class header comment
field (data member) comments 
method header comments
private members may be added, removed, and edited as you like.   private members should be commented
the inner classes may be added, removed, edited as you desire.
use in-line comments to describe high-level algorithm choices and steps 
Define (code) the Graph<E> class
must be generic type
must implement operations described in the provided GraphADT<E> interface in an efficient way
must handle corner cases (see code for more information)
must document design choices that you make in your implementation
Design and code GraphProcessor class.
[Optional] Supplement tests in GraphTest class.
Design and code GraphProcessorTest class using JUnit4 test framework
must use JUnit4 test framework.  JUnit and Eclipse Getting Started (Links to an external site.)Links to an external site.
test method names must be consistent and descriptive of the functionality being tested, see provided tests in GraphTest for simple test examples, i.e.
test01_isAdjacent_given_nonexistent_vertices
must test all boundary conditions.
Save and submit screen shot of JUnit test results
take a screen shot (jpg or png) of your JUnit test results
submit this image with your java source code.
Windows Users: may wish to download and install this Snipping Tool (Links to an external site.)Links to an external site.
Steps
Read entire assignment.
Review grading rubric. Note: the rubric is subject to some changes.
You may use the Java development environment of your choice. However, all programs must compile and run using JUnit4 and Java 8 from the Linux workstations in the lab computers for grading. If you are going to use the CS lab computers, we recommend that you use Eclipse. You may want to review the Eclipse tutorial to learn the basics. Note that on the Linux lab computers, you should enter "eclipse&" at the prompt instead of what is described in the tutorial.
Create a p4 project for your team's work.  
Create and use a GitHub repository to support multiple programmer submissions by your team members.
Copy the provided source files into your project folder and refresh.
Add JUnit 4 library to your project. (See p2)
Add return stub return statements to any methods that don't compile.
Test-Driven Development 
Work on one test at a time
run GraphTest (likely will fail)
add code to Graph class to pass the test
check that previous tests still pass
Repeat by adding code and getting all tests to pass
Add tests if you notice problem with your graph class that is not being tested yet. 
Reminder: If you are not using the lab computers to develop your program, make sure that you compile and run your program to ensure that it works on the Linux lab computers.
Submit Work to:
 X-Team Exercise #4
After submitting your files, check the contents of the files of your last submission on Canvas.  This is because occasionally students have submitted old or unimplemented source files (because they have multiple backup copies on their computer).   You may need to download the files from your Canvas account and open them to see if they actually have the correct code contents.
