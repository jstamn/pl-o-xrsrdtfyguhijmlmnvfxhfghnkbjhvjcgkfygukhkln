package application;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
