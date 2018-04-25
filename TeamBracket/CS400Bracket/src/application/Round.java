package application;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Round {

	Challenger[] challengers; // Holds Challengers
	Button[] submitButtons; // Holds buttons
	int count = 0;
	static Pattern p = Pattern.compile("^[0-9]");

	//Constructor
	public Round(ObservableList<String> teamNames) {
		this.count = teamNames.size();
		this.challengers = new Challenger[teamNames.size()];
		for (int i = 0; i < teamNames.size(); i++) {
			challengers[i] = new Challenger();
		}
		this.submitButtons = new Button[teamNames.size() / 2];
		for (int i = 0; i < teamNames.size() / 2; i++) {
			submitButtons[i] = new Button();
			submitButtons[i].setText("Submit");
		}
	}

	/**
	 * Used to fill the teams array with the actual names of the teams
	 * Does seeding of teams so input file reads 1,2,3,4 -> this function puts that into challenger array as 1,4,2,3
	 * @param teamNames Observable list of names from input file
	 */
	public void fillFirstRound(ObservableList<String> teamNames) {
		boolean seed = false;
		for (int i = 0, j = teamNames.size() - 1, k = 0; k < teamNames.size() ; k++ ) {
			if (seed == false) {
				challengers[k].setName(teamNames.get(i));
				seed = true;
				i++;
			} else {
				challengers[k].setName(teamNames.get(j));
				seed = false;
				j--;
			}

		}
	}

	/**
	 * Used to fill other rounds that are not currently in use
	 * @param teams
	 */
	public void fillRound(ObservableList<String> teams) {
		for (int i = 0; i < teams.size(); i++) {
			challengers[i].setName("TBD");
		}
	}


	/**
	 * Gets the higher score of the two challengers and returns the winner
	 * 
	 * Important note: If nothing is entered in the textfield the score is considered 0
	 * 
	 * Important note: If the scores are equal, random winner is chosen
	 * 
	 * @param team1
	 * @param team2
	 * @return team with the higher score
	 */
	public Challenger Play(Challenger team1, Challenger team2) {
		int score1;
		int score2;

		//Check if scores are numbers, otherwise set them to 0 because invalid
		if (!isValid(team1.getScore())) {
			score1 = 0;
		} else {
			score1 = Integer.parseInt(team1.getScore());
		}
		if (!isValid(team2.getScore())) {
			score2 = 0;
		} else {
			score2 = Integer.parseInt(team2.getScore());
		}

		if (score1 > score2) {
			return team1;
		} else if (score1 < score2) {
			return team2;
		} else {
			// Same score means random winner
			Random rand = new Random(); 
			int value = rand.nextInt(2); // 0 or 1
			if (value == 0) {
				return team1;
			} else {
				return team2;
			}
		}
	}

	/**
	 * Should only be called on Final Four Round
	 * 
	 * Important note: tie for third is randomly chosen
	 * 
	 * @param winner
	 * @param second
	 * @return String of third place name
	 */
	public String getThird(Challenger winner, Challenger second) {
		ArrayList<Challenger> ThirdandFourth = new ArrayList<>();
		Challenger third = new Challenger();
		
		// Puts Third and Fourth place into an arraylist
		for (int i = 0; i < 4; i++) {
			if (challengers[i].getName() != winner.getName() && challengers[i].getName() != second.getName()) {
				ThirdandFourth.add(challengers[i]);
			}
		}
		
		//Compares them and gets third 
		//If tie random chosen
		third.setName(Play(ThirdandFourth.get(0), ThirdandFourth.get(1)).getName());
		
		return third.getName();
	}
	

	/**
	 * Checks if a TexField entered by the user has only numbers
	 * 
	 * 0-9 = valid
	 * Anything else = not valid and score is set to 0
	 * 
	 * @param  input String to check for any symbols
	 * @return true if string is valid, false otherwise
	 */
	static boolean isValid(String input) {
		//Check input for valid
		Matcher m = p.matcher(input);
		if (input.startsWith(" ")) {
			return false;
		}
		if (m.find()) {
			return true;
		}
		return false;
	}
}
