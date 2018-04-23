package application;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Round {
	
	int count = 0;
	Label[] teams; // Holds names of teams
	TextField[] scores; //Holds text fields to get scores
	static Pattern p = Pattern.compile("^[0-9]");
	//TODO try array of buttons here to prevent large statements
	//TODO Try make challenger class so can make array of challengers here rather than teams and scores array
	
	//Constructor
	public Round(ObservableList<String> teamNames) {
		this.count = teamNames.size();
		this.teams = new Label[teamNames.size()];
		this.scores = new TextField[teamNames.size()];
		for (int i = 0; i < teamNames.size(); i++) {
			teams[i] = new Label();
		}
		for (int i = 0; i < teamNames.size(); i++) {
			scores[i] = new TextField();
			scores[i].setMaxSize(60, 20);
		}
	}

	/**
	 * Used to fill the teams array with the actual names of the teams
	 * Does seeding of teams so input file reads 1,2,3,4 - this function puts that into teams array as 1,4,2,3
	 * Didn't work with regular array list I saw in TA's code used observable list
	 * @param teamNames
	 */
	public void fillFirstRound(ObservableList<String> teamNames) { //Bug in indexing, but dosen't seem to affect it
		boolean seed = false;
		for (int i = 0, j = teamNames.size() - 1; i < teamNames.size() ;) {
			if (seed == false) {
				teams[i].setText(teamNames.get(i));
				seed = true;
				i++;
			} else {
				teams[i].setText(teamNames.get(j));
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
			this.teams[i].setText("TBD");
		}
	}

	/**
	 * Gets the winners name of two teams by getting what is in the two textFields and comparing them
	 * then returning the name of the teams which score is higher
	 * 
	 * Important note: If nothing is entered in the textfield the score is considered 0
	 * 
	 * Important note: If the scores are equal, random winner is chosen
	 * 
	 * Index 1 and index 2 are probrably not needed, but made it easier to code
	 * @param team1 Textfield of one team which the user entered the score into
	 * @param index1 convinience making it easier to get the name of the team the user entered the score for
	 * @param team2 Textfield of the other team which the user entered the score into
	 * @param index2
	 * @return team1 or team2 depending on which score is higher
	 */
	public String PlayGame(TextField team1, int index1, TextField team2, int index2) {
		int score1 = 0;
		int score2 = 0;
		
		//Checks if team1 and team2 scores are actual number, otherwise set to 0
		if (!isValid(team1.getText())) {
			score1 = 0;
		} else {
			score1 = Integer.parseInt(team1.getText());
		}
		
		if (!isValid(team2.getText())) {
			score2 = 0;
		} else {
			score2 = Integer.parseInt(team2.getText());
		}

		
		if (score1 > score2) {
			return teams[index1].getText();
		} else if (score1 < score2){
			return teams[index2].getText();
		} else {
			// Same score means random winner
			Random rand = new Random(); 
			int value = rand.nextInt(2); // 0 or 1
			if (value == 0) {
				return teams[index1].getText();
			} else {
				return teams[index2].getText();
			}
		}
	}
	
	/**
	 * Convinience method for getting third place
	 * Gets the Top Four teams array, First and Second place are taken out of the array because they can't also be third place
	 * Compares the last to teams scores and returns team name of highest one
	 * @param scoreArray
	 * @param teamArray
	 * @param winner Team in first
	 * @param second Team in second
	 * @return team name of third place
	 */
	public String getRank(TextField[] scoreArray, Label[] teamArray, Label winner, Label second) {
		Integer temp[] = new Integer[2];
		String[] rank = new String[2];
		for (int i = 0, j = 0; i < 4; i++) {
			if (teamArray[i].getText() != winner.getText() && teamArray[i].getText() != second.getText()) {
				rank[j] = teamArray[i].getText();
				temp[j] = j;
				j++;
			}
		}
		//If tie for third place prints both names out
		if (Integer.parseInt(scoreArray[temp[0]].getText()) == Integer.parseInt(scoreArray[temp[1]].getText())) {
			return teamArray[temp[0]].getText() + " and " + teamArray[temp[1]].getText();
		} else  if (Integer.parseInt(scoreArray[temp[0]].getText()) > Integer.parseInt(scoreArray[temp[1]].getText())) {
			return teamArray[temp[0]].getText();
		} else {
			return teamArray[temp[1]].getText();
		}
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
