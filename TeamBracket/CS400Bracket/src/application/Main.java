package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

////////////////////////////////////////////////////////////////////////////
//Semester:         CS400 Spring 2018
//PROJECT:          A Team Exercise Team Bracket
//FILES:            BracketGUI.java
//Challenger.java
//Main.java
//Round.java
//
//USER:             tschmidt6@wisc.edu | Teryl Schmidt
//alsilverman3@wisc.edu | Avi Silverman
//jsoukup2@wisc.edu | Joe Soukup
//smulvey2@wisc.edu |	Steven Mulvey
//jstamn@wisc.edu | Joshua Stamn
//
//
//Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
//Bugs:             no known bugs
//
//Due:				May 3, 2018 GraphProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////

//From TA's example

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
