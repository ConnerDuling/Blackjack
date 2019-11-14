import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.util.Random;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class BlackjackTableGUI extends Application {
	/*
	 * Made by Conner Duling
	 * Created for CS 161 at PFW.
	 * 
	 * Blackjack game that can support 1-4 players and a rudimentary
	 * dealer AI that controls the Dealer.
	 * 
	 */
	public static void main(String[] args) {
		launch(args);
	}

	private int playerCount = 0;
	private String inputToParse = null;
	private ScoreBoard scores;

	// Array of players. Dealer is player at index 0, and accounts for the +1
	private Player[] players;

	// Array of participants (GUI sections for cards and values.
	Participants[] participants;

	// Integer to track who's turn it is when came is in progress with more than one
	// player.
	private int currentPlayer = 1;

	// Buttons for control
	private Button startButton = new Button("Start");
	private Button hitButton = new Button("Hit");
	private Button standButton = new Button("Stand");

	@Override
	public void start(Stage stage) {

		// Recieve input for the number of players to change the default from 1.
		// Loops if invalid input (such as a number < 1 or > 5 is detected.
		do {
			try {
				inputToParse = JOptionPane.showInputDialog(null, "How many players are playing? 1-4", "Blackjack",
						JOptionPane.DEFAULT_OPTION);
				if (inputToParse == null) {
					System.exit(0);
				}
				playerCount = Integer.parseInt(inputToParse);
				checkRange();
			} catch (OutOfRangeException | NumberFormatException e) {
				showInvalidInput();
				playerCount = 0;
			}
		} while (playerCount == 0);

		// Increment player count to account for dealer in future operations.
		playerCount++;

		players = new Player[playerCount];
		participants = new Participants[playerCount];

		// Initializes the array of participants (visuals) for display.
		// Also Initializes the array of players (back end card handling).

		// Randy sets a random number to reference the Dealer by.
		Random Randy = new Random();

		participants[0] = new Participants("Dealer", Randy.nextInt(20) + 1);
		players[0] = new Player(0);
		for (int i = 1; i < playerCount; i++) {
			participants[i] = new Participants("Player", i);

			players[i] = new Player(i);
		}

		// dealerCards.getChildren().clear();
		// Used this to clear out the card fields later.

		// Both the card and text fields for the dealer and player.
		VBox playZone = new VBox();
		for (int i = 0; i < playerCount; i++) {
			playZone.getChildren().add(participants[i]);
		}

		// Buttons .setOnAction
		startButton.setOnAction(new startGame());
		hitButton.setOnAction(new Hit());
		standButton.setOnAction(new Stand());

		HBox buttonZone = new HBox(startButton, hitButton, standButton);
		buttonZone.setAlignment(Pos.TOP_CENTER);
		buttonZone.setSpacing(10);

		hitButton.setDisable(true);
		standButton.setDisable(true);

		// Player score
		// -1 excludes dealer from the scoreboard, as Dealer is in a different section.
		scores = new ScoreBoard(playerCount - 1);
		VBox buttonAndScore = new VBox(buttonZone, scores);
		buttonAndScore.setPrefSize(600, 100);

		// Organizes all visible elements
		BorderPane bp = new BorderPane();
		bp.setCenter(playZone);
		bp.setBottom(buttonAndScore);
		bp.getStyleClass().add("board-background");

		Scene primaryScene = new Scene(bp);
		primaryScene.getStylesheets().add("file:style.css");
		stage.setScene(primaryScene);
		stage.setTitle("Blackjack");
		stage.show();

		for (int i = 0; i < playerCount; i++) {
			players[i] = new Player(i);
		}
	}

	//Displays message for invalid input by user.
	public static void showInvalidInput() {
		JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1-4.");
	}

	// Throws OutOfRangeException exception to trip the try-catch is
	// user input is < 1 or > 5.
	public void checkRange() throws OutOfRangeException {
		if (playerCount < 1 || playerCount > 4) {
			throw new OutOfRangeException("Number is out of the range.");
		}
	}

	// Makes outcome blank, removes player hand and hand value.
	// Resets deck.
	public void clearOldGame() {
		scores.updateOutcome("");
		players[0].resetDeck();
		for (int i = 0; i < playerCount; i++) {
			participants[i].reset();
			players[i].clearHand();
		}

	}

	//Sets up a new game by changing button properties, clearing away the old game, and giving the dealer one card.
	class startGame implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			currentPlayer = 1;
			// Deactivate state button, activate hit and stand.
			startButton.setDisable(true);
			hitButton.setDisable(false);
			standButton.setDisable(false);
			clearOldGame();
			players[0].hit();
			participants[0].setHand(players[0].getHand());
			participants[0].setValue(players[0].valueOfHand());
		}
	}

	//Has dealer decide upon acting, and resets buttons for new game selection.
	public void endGame() {
		dealerWrapUp();
		resetButtons();
	}
	
	//Determines winner/tie, finds which player has highest, legal score,
	//has dealer deal cards if needed.
	public void dealerWrapUp() {
		int highestPlayer = 0;
		int maxValue = 0;
		//Loops through all players, and finds who has the largest value up to  and including 21.
		for(int i = 1; i < playerCount; i++) {
			if(maxValue < players[i].valueOfHand() && !(players[i].bust())) {
				highestPlayer = i;
				maxValue = players[i].valueOfHand();
			}
		}
		//Dealer plays cards as needed to beat the highest valid hand.
		if(!(players[0].stand(players[highestPlayer].valueOfHand()))) {
		while(maxValue >= players[0].valueOfHand()) {
			players[0].hit();
			participants[0].setHand(players[0].getHand());
			participants[0].setValue(players[0].valueOfHand());
		}}
		
		//Tracks how many players have the same highest score.
		//This means that at least two players have tied for winning,
		//and thus no one wins, and it's declared a push.
		int tieTracker = 0;
		for(int t = 0; t < playerCount; t++) {
			if(players[t].valueOfHand() == maxValue) {
				tieTracker++;
			}
		}
		
		//Determines if a tie, dealer win, or player win happens.
		if(tieTracker > 1 || players[0].valueOfHand() == maxValue) {
			scores.updateOutcome("Push! No one wins");
		}
		else if(players[0].valueOfHand() > maxValue && players[0].valueOfHand() <= 21) {
			scores.updateOutcome("Dealer Wins!");
			scores.updateScore(0, players[0].getUpdateScore());
		}
		else if(maxValue > players[0].valueOfHand() || (players[highestPlayer].valueOfHand() <= 21 && players[0].valueOfHand() > 21)){
			scores.updateOutcome("Player "+ highestPlayer +" Wins!");
			scores.updateScore(highestPlayer, players[highestPlayer].getUpdateScore());
		}
	}
	
	//Resets button values.
	public void resetButtons() {
		// Activate start button, deactivate hit and stand.
		startButton.setDisable(false);
		hitButton.setDisable(true);
		standButton.setDisable(true);
	}

	//Draws a card, adds it to player hand, displays new hand.
	//If player busts, increments currentPlayer counter.
	//If last player ends, trigger the end method.
	class Hit implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			players[currentPlayer].hit();
			participants[currentPlayer].setHand(players[currentPlayer].getHand());
			participants[currentPlayer].setValue(players[currentPlayer].valueOfHand());
			if(players[currentPlayer].bust()) {
				currentPlayer++;
			}
			if(currentPlayer > playerCount - 1) {
				endGame();
			}
		}

	}

	//Increments currentPlayer tracker when stand button is hit.
	//Moving onto the next player.
	class Stand implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if (currentPlayer < playerCount - 1) {
				currentPlayer++;
			} else {
				endGame();
			}
		}
	}
}
