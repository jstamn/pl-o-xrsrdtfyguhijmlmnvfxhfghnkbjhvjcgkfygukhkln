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


public class BacketGUI extends Application {
	
	static ObservableList<String> teamNames = FXCollections.observableArrayList(); //Got from TA's example
	private int count = teamNames.size();
	String winner = "TBD";
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

		if (count == 0) {
			Label label = new Label();
			label.setText("There are no teams!");
			gPane.add(label, 0, 0);
		}

		// SPECIAL CASE 1: Only one name in text file

		// Make First Second and Third place labels outside of if statements below so can be accessed (note: could make global variables like winnerlabel is)
		Label WinnerLabel = new Label();
		Label SecondPlaceLabel = new Label();
		Label SecondPlaceInfo = new Label();
		Label ThirdPlaceLabel = new Label();
		Label ThirdPlaceInfo = new Label();
		
		SecondPlaceInfo.setText("2nd: ");
		ThirdPlaceInfo.setText("3rd: ");
		
		WinnerLabel.setAlignment(Pos.CENTER);
		WinnerLabel.setMinHeight(25);
		if (count == 1) {
			WinnerLabel.setText(teamNames.get(0)); //If one team it is the winner
		} else {
			WinnerLabel.setText("TBD"); //Otherwise winner has not been determined yet
		}
		
		if (count >= 1) {
			gPane.add(WinnerLabel, 10, 11);
		}
		//If only one team then there is no second and third place
		if (count > 1) {
			gPane.add(SecondPlaceLabel, 10, 13);
			gPane.add(SecondPlaceInfo, 9, 13);
			gPane.add(ThirdPlaceLabel, 10, 14);
			gPane.add(ThirdPlaceInfo, 9, 14);
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
					WinnerLabel.setText(TopTwoRound.PlayGame(TopTwoRound.scores[0], 0,TopTwoRound.scores[1], 1));
					//Get second place
					if (WinnerLabel.getText() == TopTwoRound.teams[0].getText()) {
						SecondPlaceLabel.setText(TopTwoRound.teams[1].getText());
					} else {
						SecondPlaceLabel.setText(TopTwoRound.teams[0].getText());
					}
					//Get third place
					if (count > 2) {
						ThirdPlaceLabel.setText(FinalFourRound.getRank(FinalFourRound.scores, FinalFourRound.teams, WinnerLabel, SecondPlaceLabel));
					}
				}
			});
			
			//If two names in text file there is no third place
			SecondPlaceLabel.setText("TBD");
			if (count > 2) {
				ThirdPlaceLabel.setText("TBD");
			} else {
				ThirdPlaceLabel.setText("None");
			}
			
			gPane.add(TopTwoRound.teams[0], 8, 11);
			gPane.add(TopTwoRound.teams[1], 12, 11);
			gPane.add(TopTwoRound.scores[0], 9, 11);
			gPane.add(TopTwoRound.scores[1], 11, 11);
			gPane.add(TopTwoButton, 10, 12);
		}

		// FINAL FOUR
		
		if (count > 2) {
			
			if (count == 4) {
				FinalFourRound.fillFirstRound(teamNames); // If 4 teams fill with actual names
			} else {
				FinalFourRound.fillRound(teamNames); // Otherwise fill with "TBD"
			}

			ArrayList<Button> FinalFourButtons = new ArrayList<>(); //Button array should go in Round class preventing these long statements below
			for (int i = 0; i < 2; i++) {
				FinalFourButtons.add(new Button());
				FinalFourButtons.get(i).setText("Submit");
			}

			//Using lambda here, shorter version of above code on line 126
			FinalFourButtons.get(0).setOnAction(actionEvent -> TopTwoRound.teams[0].setText(FinalFourRound.PlayGame(FinalFourRound.scores[0], 0, FinalFourRound.scores[3], 3)));
			
			FinalFourButtons.get(1).setOnAction(actionEvent -> TopTwoRound.teams[1].setText(FinalFourRound.PlayGame(FinalFourRound.scores[1], 1, FinalFourRound.scores[2], 2)));

			// i is used for left side
			// j is used for right side
			// k is used as padding (push the names down so they are not all at the top)
			for (int i = 0, j = 1, k = 0; i < 4; i+=3, j++, k+=2) {
				gPane.add(FinalFourRound.teams[i], 6, k + 10); //Left side
				gPane.add(FinalFourRound.scores[i], 7, k + 10);
				gPane.add(FinalFourRound.teams[j], 14, k + 10); //Right side
				gPane.add(FinalFourRound.scores[j], 13, k + 10);
			}

			gPane.add(FinalFourButtons.get(0), 6 ,11);
			gPane.add(FinalFourButtons.get(1), 14 ,11);

		}

		// ELITE EIGHT

		if (count > 4) {
			
			if (count == 8) {
				EliteEightRound.fillFirstRound(teamNames);
			} else {
				EliteEightRound.fillRound(teamNames);
			}
			
			ArrayList<Button> EliteEightButtons = new ArrayList<>(); 
			for (int i = 0; i < 4; i++) {
				EliteEightButtons.add(new Button());
				EliteEightButtons.get(i).setText("Submit");
			}
			
			EliteEightButtons.get(0).setOnAction(actionEvent -> FinalFourRound.teams[0].setText(EliteEightRound.PlayGame(EliteEightRound.scores[0], 0, EliteEightRound.scores[7], 7)));
			EliteEightButtons.get(1).setOnAction(actionEvent -> FinalFourRound.teams[1].setText(EliteEightRound.PlayGame(EliteEightRound.scores[2], 2, EliteEightRound.scores[5], 5)));
			
			EliteEightButtons.get(3).setOnAction(actionEvent -> FinalFourRound.teams[2].setText(EliteEightRound.PlayGame(EliteEightRound.scores[3], 3, EliteEightRound.scores[4], 4)));
			EliteEightButtons.get(2).setOnAction(actionEvent -> FinalFourRound.teams[3].setText(EliteEightRound.PlayGame(EliteEightRound.scores[1], 1, EliteEightRound.scores[6], 6)));

			//k is used to put the items in the right row, otherwise all at top
			//TODO make printChallengers method in Round class to prevent this
			for (int i = 0, j = 2, k = 0; i < 2; i++, j++, k+=3) {
				gPane.add(EliteEightRound.teams[i], 4, k + 9);// Upper left
				gPane.add(EliteEightRound.scores[i], 5, k + 9);
				gPane.add(EliteEightRound.teams[j], 16, k + 9); //Upper right
				gPane.add(EliteEightRound.scores[j], 15, k + 9);
			}
			
			for (int i = 7, j = 5, k = 0; i > 5; i--, j--, k+=3) {
				gPane.add(EliteEightRound.teams[i], 4, k + 11); // Lower left
				gPane.add(EliteEightRound.scores[i], 5, k + 11);
				gPane.add(EliteEightRound.teams[j], 16, k + 11); // Lower right
				gPane.add(EliteEightRound.scores[j], 15, k + 11);
			}			

			for (int i = 0, j = 1, k = 10; i <= 2; i+=2, j+=2, k+=3) {
				gPane.add(EliteEightButtons.get(i), 4 ,k);
				gPane.add(EliteEightButtons.get(j), 16 ,k);
			}
		}
		
		//SWEET SIXTEEN
		
		if (count > 8) {
			
			if (count == 16) {
				SweetSixteenRound.fillFirstRound(teamNames);
			} else {
				SweetSixteenRound.fillRound(teamNames);
			}
			
			ArrayList<Button> SweetSixteenButtons = new ArrayList<>();
			for (int i = 0; i < 8; i++) {
				SweetSixteenButtons.add(new Button());
				SweetSixteenButtons.get(i).setText("Submit");
			}
			
			//TODO make button array in Round class so I don't have to do this
			// Just did this for now for basic idea
			SweetSixteenButtons.get(0).setOnAction(actionEvent -> EliteEightRound.teams[0].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[0], 0, SweetSixteenRound.scores[15], 15)));
			SweetSixteenButtons.get(2).setOnAction(actionEvent -> EliteEightRound.teams[7].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[1], 1, SweetSixteenRound.scores[14], 14)));
			SweetSixteenButtons.get(4).setOnAction(actionEvent -> EliteEightRound.teams[1].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[2], 2, SweetSixteenRound.scores[13], 13)));
			SweetSixteenButtons.get(6).setOnAction(actionEvent -> EliteEightRound.teams[6].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[3], 3, SweetSixteenRound.scores[12], 12)));
			
			SweetSixteenButtons.get(1).setOnAction(actionEvent -> EliteEightRound.teams[2].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[4], 4, SweetSixteenRound.scores[11], 11)));
			SweetSixteenButtons.get(3).setOnAction(actionEvent -> EliteEightRound.teams[5].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[5], 5, SweetSixteenRound.scores[10], 10)));
			SweetSixteenButtons.get(5).setOnAction(actionEvent -> EliteEightRound.teams[3].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[6], 6, SweetSixteenRound.scores[9], 9)));
			SweetSixteenButtons.get(7).setOnAction(actionEvent -> EliteEightRound.teams[4].setText(SweetSixteenRound.PlayGame(SweetSixteenRound.scores[7], 7, SweetSixteenRound.scores[8], 8)));

			//TODO make printChallengers method in Round class to prevent this
			for (int i = 0, j = 4, k = 0; i < 4; i++, j++, k+=3) {
				gPane.add(SweetSixteenRound.teams[i], 2, k + 6);
				gPane.add(SweetSixteenRound.scores[i], 3, k + 6);
				gPane.add(SweetSixteenRound.teams[j], 18, k + 6);
				gPane.add(SweetSixteenRound.scores[j], 17, k + 6);
			}	
			
			for (int i = 15, j = 11, k = 0; i > 11; i--, j--, k+=3) {
				gPane.add(SweetSixteenRound.teams[i], 2, k + 8);
				gPane.add(SweetSixteenRound.scores[i], 3, k + 8);
				gPane.add(SweetSixteenRound.teams[j], 18, k + 8);
				gPane.add(SweetSixteenRound.scores[j], 17, k + 8);
			}	
			
			for (int i = 0, j = 1, k = 0; i <= 6; i+=2, j+=2, k+=3) {
				gPane.add(SweetSixteenButtons.get(i), 2 ,k + 7);
				gPane.add(SweetSixteenButtons.get(j), 18 ,k + 7);
			}
		}

		//TOP THIRTY-TWO

		if (count > 16) {
			
			if (count == 32) {
				TopThirtyTwoRound.fillFirstRound(teamNames);
			} else {
				TopThirtyTwoRound.fillRound(teamNames);
			}
			
			ArrayList<Button> TopThirtyTwoButtons = new ArrayList<>(); //Button array should go in Round class preventing long statements below
			for (int i = 0; i < 16; i++) {
				TopThirtyTwoButtons.add(new Button());
				TopThirtyTwoButtons.get(i).setText("Submit");
			}

			//TODO make button array in Round class so I don't have to do this
			// Just did this for now for basic idea
			TopThirtyTwoButtons.get(0).setOnAction(actionEvent -> SweetSixteenRound.teams[0].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[0], 0, TopThirtyTwoRound.scores[31], 31)));
			TopThirtyTwoButtons.get(2).setOnAction(actionEvent -> SweetSixteenRound.teams[15].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[1], 1, TopThirtyTwoRound.scores[30], 30)));
			TopThirtyTwoButtons.get(4).setOnAction(actionEvent -> SweetSixteenRound.teams[1].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[2], 2, TopThirtyTwoRound.scores[29], 29)));
			TopThirtyTwoButtons.get(6).setOnAction(actionEvent -> SweetSixteenRound.teams[14].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[3], 3, TopThirtyTwoRound.scores[28], 28)));
			TopThirtyTwoButtons.get(8).setOnAction(actionEvent -> SweetSixteenRound.teams[2].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[4], 4, TopThirtyTwoRound.scores[27], 27)));
			TopThirtyTwoButtons.get(10).setOnAction(actionEvent -> SweetSixteenRound.teams[13].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[5], 5, TopThirtyTwoRound.scores[26], 26)));
			TopThirtyTwoButtons.get(12).setOnAction(actionEvent -> SweetSixteenRound.teams[3].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[6], 6, TopThirtyTwoRound.scores[25], 25)));
			TopThirtyTwoButtons.get(14).setOnAction(actionEvent -> SweetSixteenRound.teams[12].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[7], 7, TopThirtyTwoRound.scores[24], 24)));

			TopThirtyTwoButtons.get(1).setOnAction(actionEvent -> SweetSixteenRound.teams[4].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[8], 8, TopThirtyTwoRound.scores[23], 23)));
			TopThirtyTwoButtons.get(3).setOnAction(actionEvent -> SweetSixteenRound.teams[11].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[9], 9, TopThirtyTwoRound.scores[22], 22)));
			TopThirtyTwoButtons.get(5).setOnAction(actionEvent -> SweetSixteenRound.teams[5].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[10], 10, TopThirtyTwoRound.scores[21], 21)));
			TopThirtyTwoButtons.get(7).setOnAction(actionEvent -> SweetSixteenRound.teams[10].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[11], 11, TopThirtyTwoRound.scores[20], 20)));
			TopThirtyTwoButtons.get(9).setOnAction(actionEvent -> SweetSixteenRound.teams[6].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[12], 12, TopThirtyTwoRound.scores[19], 19)));
			TopThirtyTwoButtons.get(11).setOnAction(actionEvent -> SweetSixteenRound.teams[9].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[13], 13, TopThirtyTwoRound.scores[18], 18)));
			TopThirtyTwoButtons.get(13).setOnAction(actionEvent -> SweetSixteenRound.teams[7].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[14], 14, TopThirtyTwoRound.scores[17], 17)));
			TopThirtyTwoButtons.get(15).setOnAction(actionEvent -> SweetSixteenRound.teams[8].setText(TopThirtyTwoRound.PlayGame(TopThirtyTwoRound.scores[15], 15, TopThirtyTwoRound.scores[16], 16)));

			//TODO make printChallengers method in Round class to prevent this
			for (int i = 0, j = 31, k = 0; i < 8; i++, j--, k+=3) {
				gPane.add(TopThirtyTwoRound.teams[i], 0, k );
				gPane.add(TopThirtyTwoRound.scores[i], 1, k  );
				gPane.add(TopThirtyTwoRound.teams[j], 0, k +2 );
				gPane.add(TopThirtyTwoRound.scores[j], 1, k +2);
			}	
			
			for (int i = 8, j = 23, k = 0; i < 16; i++, j--, k+=3) {
				gPane.add(TopThirtyTwoRound.teams[i], 20, k );
				gPane.add(TopThirtyTwoRound.scores[i], 19, k  );
				gPane.add(TopThirtyTwoRound.teams[j], 20, k +2 );
				gPane.add(TopThirtyTwoRound.scores[j], 19, k +2);
			}
			
			for (int i = 0, j = 1, k = 1; i < 16; i+=2, j+=2, k+=3) {
				gPane.add(TopThirtyTwoButtons.get(i), 0 , k );
				gPane.add(TopThirtyTwoButtons.get(j), 20 , k );
			}
			
		}

		gPane.setGridLinesVisible(false);
		primaryStage.setScene(scene);
		primaryStage.show();	
	}

	public static void main(String[] args) {

		//Got this from TA's example
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