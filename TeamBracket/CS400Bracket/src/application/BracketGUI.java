package application;
//package cs400;

import java.awt.ScrollPane;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import javax.net.ssl.ExtendedSSLSession;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

////////////////////////////////////////////////////////////////////////////
//Semester:         CS400 Spring 2018
//PROJECT:          A Team Exercise Team Bracket
//FILES:            BracketGUI.java
//					Challenger.java
//					Main.java
//					Round.java
//
//USER:             tschmidt6@wisc.edu | Teryl Schmidt
//					alsilverman3@wisc.edu | Avi Silverman
//					jsoukup2@wisc.edu | Joe Soukup
//					smulvey2@wisc.edu |	Steven Mulvey
//					jstamn@wisc.edu | Joshua Stamn
//
//
//Instructor:       Deb Deppeler (deppeler@cs.wisc.edu)
//Bugs:             no known bugs
//
//Due:				May 3, 2018 GraphProcessor.java 
////////////////////////////80 columns wide //////////////////////////////////

public class BracketGUI extends Application {

	static ObservableList<String> teamNames = FXCollections.observableArrayList();
	private int count = teamNames.size();
	Round TopTwoRound = new Round(teamNames);
	Round FinalFourRound = new Round(teamNames);
	Round EliteEightRound = new Round(teamNames);
	Round SweetSixteenRound = new Round(teamNames);
	Round TopThirtyTwoRound = new Round(teamNames);

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Bracket");
		GridPane gPane = new GridPane();
		Scene scene = new Scene(gPane, 1300, 900, Color.DARKGRAY);

		// SPECIAL CASE 0: Nothing in text file

		Challenger Winner = new Challenger();
		if (count == 0) {
			Winner.setName("There are no teams!");
			gPane.add(Winner.getLabel(), 0, 0);
		}

		// SPECIAL CASE 1: Only one name in text file

		Challenger Second = new Challenger();
		Challenger Third = new Challenger();

		Label SecondPlaceInfo = new Label();
		Label ThirdPlaceInfo = new Label();
		SecondPlaceInfo.setText("2nd: ");
		ThirdPlaceInfo.setText("3rd: ");

		Winner.getLabel().setAlignment(Pos.CENTER);
		if (count == 1) {
			Winner.setName(teamNames.get(0)); //If one team it is the winner
		} else if (count >= 1){
			Winner.setName("TBD"); //Otherwise winner has not been determined yet
		}

		if (count >= 1) {
			gPane.add(Winner.getLabel(), 10, 15);
		}
		//If only one team then there is no second and third place
		if (count > 1) {
			gPane.add(Second.getLabel(), 10, 17);
			gPane.add(SecondPlaceInfo, 9, 17);
			gPane.add(Third.getLabel(), 10, 18);
			gPane.add(ThirdPlaceInfo, 9, 18);
		}

		// TOP TWO

		if (count > 1) {

			if (count == 2) {
				TopTwoRound.fillFirstRound(teamNames); //If 2 names then fill that round with actual names
			} else {
				TopTwoRound.fillRound(teamNames); //Otherwise fill it with "TBD"
			}

			Button TopTwoButton = new Button();

			TopTwoButton.setText("Submit");
			TopTwoButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					//Get winner
					Winner.setName(TopTwoRound.Play(TopTwoRound.challengers[0], TopTwoRound.challengers[1]).getName());
					//Get second place
					if (Winner.getName().equals(TopTwoRound.challengers[0].getName())) {
						Second.setName((TopTwoRound.challengers[1].getName()));
					} else {
						Second.setName((TopTwoRound.challengers[0].getName()));
					}
					//Get third place
					if (count > 2) {
						Third.setName(FinalFourRound.getThird(Winner, Second));
					}
				}
			});

			//If two names in text file there is no third place
			Second.setName("TBD");
			if (count > 2) {
				Third.setName("TBD");
			} else {
				Third.setName("None");
			}

			gPane.add(TopTwoRound.challengers[0].getLabel(), 8, 15);
			gPane.add(TopTwoRound.challengers[1].getLabel(), 12, 15);
			gPane.add(TopTwoRound.challengers[0].getTextField(), 9, 15);
			gPane.add(TopTwoRound.challengers[1].getTextField(), 11, 15);
			gPane.add(TopTwoButton, 10, 16);
		}

		// FINAL FOUR

		if (count > 2) {

			if (count == 4) {
				FinalFourRound.fillFirstRound(teamNames); // If 4 teams fill with actual names
			} else {
				FinalFourRound.fillRound(teamNames); // Otherwise fill with "TBD"
			}

			//Using lambda here, shorter version of above code on line 126
			FinalFourRound.submitButtons[0].setOnAction(actionEvent -> TopTwoRound.challengers[0].setName(FinalFourRound.Play(FinalFourRound.challengers[0], FinalFourRound.challengers[1]).getName()));

			FinalFourRound.submitButtons[1].setOnAction(actionEvent -> TopTwoRound.challengers[1].setName(FinalFourRound.Play(FinalFourRound.challengers[2], FinalFourRound.challengers[3]).getName()));

			//Put Labels and Textfields in scene
			for (int i = 0, j = 2, k = 14; i < 2; i++, j++, k+=2) {
				gPane.add(FinalFourRound.challengers[i].getLabel(), 6, k);
				gPane.add(FinalFourRound.challengers[i].getTextField(), 7, k);
				gPane.add(FinalFourRound.challengers[j].getLabel(), 14, k); 
				gPane.add(FinalFourRound.challengers[j].getTextField(), 13, k);
			}

			//Put buttons in scene
			gPane.add(FinalFourRound.submitButtons[0], 6 ,15);
			gPane.add(FinalFourRound.submitButtons[1], 14 ,15);
		}

		// ELITE EIGHT 

		if (count > 4) {

			if (count == 8) {
				EliteEightRound.fillFirstRound(teamNames);
			} else {
				EliteEightRound.fillRound(teamNames);
			}

			EliteEightRound.submitButtons[0].setOnAction(actionEvent -> FinalFourRound.challengers[0].setName(EliteEightRound.Play(EliteEightRound.challengers[0], EliteEightRound.challengers[1]).getName()));
			EliteEightRound.submitButtons[1].setOnAction(actionEvent -> FinalFourRound.challengers[1].setName(EliteEightRound.Play(EliteEightRound.challengers[2], EliteEightRound.challengers[3]).getName()));

			EliteEightRound.submitButtons[2].setOnAction(actionEvent -> FinalFourRound.challengers[2].setName(EliteEightRound.Play(EliteEightRound.challengers[4], EliteEightRound.challengers[5]).getName()));
			EliteEightRound.submitButtons[3].setOnAction(actionEvent -> FinalFourRound.challengers[3].setName(EliteEightRound.Play(EliteEightRound.challengers[6], EliteEightRound.challengers[7]).getName()));


			for (int i = 0, j = 4, k = 12; i < 4; i++, j++, k+=2) {
				gPane.add(EliteEightRound.challengers[i].getLabel(), 4, k);
				gPane.add(EliteEightRound.challengers[i].getTextField(), 5, k);
				gPane.add(EliteEightRound.challengers[j].getLabel(), 16, k);
				gPane.add(EliteEightRound.challengers[j].getTextField(), 15, k);
			}

			for (int i = 0, j = 2, k = 13; i < 2; i++, j++, k+=4) {
				gPane.add(EliteEightRound.submitButtons[i], 4, k);
				gPane.add(EliteEightRound.submitButtons[j], 16, k);
			}
		}

		//SWEET SIXTEEN

		if (count > 8) {

			if (count == 16) {
				SweetSixteenRound.fillFirstRound(teamNames);
			} else {
				SweetSixteenRound.fillRound(teamNames);
			}

			SweetSixteenRound.submitButtons[0].setOnAction(actionEvent -> EliteEightRound.challengers[0].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[0], SweetSixteenRound.challengers[1]).getName()));
			SweetSixteenRound.submitButtons[1].setOnAction(actionEvent -> EliteEightRound.challengers[1].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[2], SweetSixteenRound.challengers[3]).getName()));
			SweetSixteenRound.submitButtons[2].setOnAction(actionEvent -> EliteEightRound.challengers[2].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[4], SweetSixteenRound.challengers[5]).getName()));
			SweetSixteenRound.submitButtons[3].setOnAction(actionEvent -> EliteEightRound.challengers[3].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[6], SweetSixteenRound.challengers[7]).getName()));

			SweetSixteenRound.submitButtons[4].setOnAction(actionEvent -> EliteEightRound.challengers[4].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[8], SweetSixteenRound.challengers[9]).getName()));
			SweetSixteenRound.submitButtons[5].setOnAction(actionEvent -> EliteEightRound.challengers[5].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[10], SweetSixteenRound.challengers[11]).getName()));
			SweetSixteenRound.submitButtons[6].setOnAction(actionEvent -> EliteEightRound.challengers[6].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[12], SweetSixteenRound.challengers[13]).getName()));
			SweetSixteenRound.submitButtons[7].setOnAction(actionEvent -> EliteEightRound.challengers[7].setName(SweetSixteenRound.Play(SweetSixteenRound.challengers[14], SweetSixteenRound.challengers[15]).getName()));

			for (int i = 0, j = 8, k = 8; i < 8; i++, j++, k+=2) {
				gPane.add(SweetSixteenRound.challengers[i].getLabel(), 2, k);
				gPane.add(SweetSixteenRound.challengers[i].getTextField(), 3, k);
				gPane.add(SweetSixteenRound.challengers[j].getLabel(), 18, k);
				gPane.add(SweetSixteenRound.challengers[j].getTextField(), 17, k);
			}

			for (int i = 0, j = 4, k = 9; i < 4; i++, j++, k+=4) {
				gPane.add(SweetSixteenRound.submitButtons[i], 2 , k);
				gPane.add(SweetSixteenRound.submitButtons[j], 18 , k);
			}
		}

		//TOP THIRTY-TWO

		if (count > 16) {

			if (count == 32) {
				TopThirtyTwoRound.fillFirstRound(teamNames);
			} else {
				TopThirtyTwoRound.fillRound(teamNames);
			}

			TopThirtyTwoRound.submitButtons[0].setOnAction(actionEvent -> SweetSixteenRound.challengers[0].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[0], TopThirtyTwoRound.challengers[1]).getName()));
			TopThirtyTwoRound.submitButtons[1].setOnAction(actionEvent -> SweetSixteenRound.challengers[1].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[2], TopThirtyTwoRound.challengers[3]).getName()));
			TopThirtyTwoRound.submitButtons[2].setOnAction(actionEvent -> SweetSixteenRound.challengers[2].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[4], TopThirtyTwoRound.challengers[5]).getName()));
			TopThirtyTwoRound.submitButtons[3].setOnAction(actionEvent -> SweetSixteenRound.challengers[3].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[6], TopThirtyTwoRound.challengers[7]).getName()));
			TopThirtyTwoRound.submitButtons[4].setOnAction(actionEvent -> SweetSixteenRound.challengers[4].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[8], TopThirtyTwoRound.challengers[9]).getName()));
			TopThirtyTwoRound.submitButtons[5].setOnAction(actionEvent -> SweetSixteenRound.challengers[5].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[10], TopThirtyTwoRound.challengers[11]).getName()));
			TopThirtyTwoRound.submitButtons[6].setOnAction(actionEvent -> SweetSixteenRound.challengers[6].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[12], TopThirtyTwoRound.challengers[13]).getName()));
			TopThirtyTwoRound.submitButtons[7].setOnAction(actionEvent -> SweetSixteenRound.challengers[7].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[14], TopThirtyTwoRound.challengers[15]).getName()));

			TopThirtyTwoRound.submitButtons[8].setOnAction(actionEvent -> SweetSixteenRound.challengers[8].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[16], TopThirtyTwoRound.challengers[17]).getName()));
			TopThirtyTwoRound.submitButtons[9].setOnAction(actionEvent -> SweetSixteenRound.challengers[9].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[18], TopThirtyTwoRound.challengers[19]).getName()));
			TopThirtyTwoRound.submitButtons[10].setOnAction(actionEvent -> SweetSixteenRound.challengers[10].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[20], TopThirtyTwoRound.challengers[21]).getName()));
			TopThirtyTwoRound.submitButtons[11].setOnAction(actionEvent -> SweetSixteenRound.challengers[11].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[22], TopThirtyTwoRound.challengers[23]).getName()));
			TopThirtyTwoRound.submitButtons[12].setOnAction(actionEvent -> SweetSixteenRound.challengers[12].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[24], TopThirtyTwoRound.challengers[25]).getName()));
			TopThirtyTwoRound.submitButtons[13].setOnAction(actionEvent -> SweetSixteenRound.challengers[13].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[26], TopThirtyTwoRound.challengers[27]).getName()));
			TopThirtyTwoRound.submitButtons[14].setOnAction(actionEvent -> SweetSixteenRound.challengers[14].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[28], TopThirtyTwoRound.challengers[29]).getName()));
			TopThirtyTwoRound.submitButtons[15].setOnAction(actionEvent -> SweetSixteenRound.challengers[15].setName(TopThirtyTwoRound.Play(TopThirtyTwoRound.challengers[30], TopThirtyTwoRound.challengers[31]).getName()));

			for (int i = 0, j = 16, k = 0; i < 16; i++, j++, k+=2) {
				gPane.add(TopThirtyTwoRound.challengers[i].getLabel(), 0, k);
				gPane.add(TopThirtyTwoRound.challengers[i].getTextField(), 1, k);
				gPane.add(TopThirtyTwoRound.challengers[j].getLabel(), 20, k);
				gPane.add(TopThirtyTwoRound.challengers[j].getTextField(), 19, k);
			}
			
			for (int i = 0, j = 8, k = 1; i < 8; i++, j++, k+=4) {
				gPane.add(TopThirtyTwoRound.submitButtons[i], 0 , k);
				gPane.add(TopThirtyTwoRound.submitButtons[j], 20 , k);
			}

		}

		gPane.setGridLinesVisible(false);
		primaryStage.setScene(scene);
		primaryStage.show();	
	}

	public static void main(String[] args) {

		String fileName = "teams32.txt" ; // Testing just set to filename to "teams04.txt" or "teams32.txt"
		File inputFile = null;
		Scanner sc = null;

		try {
			inputFile = new File(fileName);
			sc = new Scanner(inputFile);
			while(sc.hasNextLine()) {
				String name = sc.nextLine();
				teamNames.add(name);
			}
			sc.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}

		launch(args);
	}
}
