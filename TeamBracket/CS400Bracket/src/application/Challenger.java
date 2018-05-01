package application;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

public class Challenger {

	private Label name;
	private TextField score;
	
	public Challenger() {
		this.name = new Label();
		this.score = new TextField();
		this.score.setMaxSize(60, 20);
	}
	
	/**
	 * @return label for formatting
	 */
	public Label getLabel() {
		return name;
	}
	
	/**
	 * @return TextField for formatting
	 */
	public TextField getTextField() {
		return score;
	}

	/**
	 * @return the labels text
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * @param name the set the label to the string
	 */
	public void setName(String name) {
		this.name.setText(name);
	}

	/**
	 * @return the score as a String
	 */
	public String getScore() {
		return score.getText();
	}	
}
