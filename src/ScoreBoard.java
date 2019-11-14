import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class ScoreBoard extends VBox {
	
	/*
	 * This class represents the scoreboard that appears at the bottom of the GUI.
	 * It displays things such as the winner of a given round,
	 * the running scores of all players, and ties.
	 */

	private Label outcome;
	private Label[] scoreSection;

	public ScoreBoard(int playerNumber) {

		// Hbox to store all player scores in a horizontal line. Player 1 Score: 0
		// Player 2 Score: 0
		HBox playerScores = new HBox();
		playerScores.setPrefSize(50, 20);
		playerScores.setAlignment(Pos.TOP_CENTER);
		playerScores.setStyle("-fx-spacing: 50");

		scoreSection = new Label[playerNumber + 1];
		// ***** Player # Score: 0
		for (int i = 1; i <= playerNumber; i++) {
			scoreSection[i] = new Label("Player " + i + " Score: " + 0);
			playerScores.getChildren().add(scoreSection[i]);
		}

		// ***** Dealer Score: 0
		scoreSection[0] = new Label("Dealer Score: " + 0);
		outcome = new Label("");
		HBox dealerScoreOutcome = new HBox(scoreSection[0], outcome);
		dealerScoreOutcome.setSpacing(350);
		dealerScoreOutcome.setTranslateX(37);

		this.getChildren().add(playerScores);
		this.getChildren().add(dealerScoreOutcome);
		this.setAlignment(Pos.BASELINE_LEFT);
	}

	// USed to update the message across from the Dealer's score.
	// Used to declare winners, ties and pushes.
	public void updateOutcome(String message) {
		outcome.setText(message);
	}

	public void updateScore(int playerNumber, int score) {
		if (playerNumber == 0) {
			scoreSection[playerNumber].setText("Dealer Score: " + score);
		} else {
			scoreSection[playerNumber].setText("Player " + playerNumber + " Score: " + score);
		}
	}

}
