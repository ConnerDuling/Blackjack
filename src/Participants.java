import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class Participants extends VBox {

/*This contains the objects that a Participant at the table would have visually.
 * 
 * This handles the VBox that holds player hand values, and the HBox that
 * cards are displayed in.
 */

	private Label participantOutput;
	private HBox participantCardSlot;

	public Participants(String inputTitle, int number) {
		// ***** Player # Hand
		Label participantTitle = new Label(inputTitle + " " + number + " Hand");

		// ***** Hand Value: #
		participantOutput = new Label("Value: 0");

		HBox participantNumbers = new HBox(participantTitle, participantOutput);
		participantNumbers.setPrefSize(600, 20);
		participantNumbers.setAlignment(Pos.TOP_CENTER);
		participantNumbers.setStyle("-fx-spacing: 100");

		// HBox for cards
		participantCardSlot = new HBox();
		participantCardSlot.setPrefSize(600, 150);
		this.getChildren().add(participantNumbers);
		this.getChildren().add(participantCardSlot);
	}

	public void setHand(ArrayList<Card> cards) {
		participantCardSlot.getChildren().clear();
		participantCardSlot.getChildren().addAll(cards);
	}

	public void setValue(int value) {
		participantOutput.setText("Value: " + value);
	}

	public void reset() {
		participantCardSlot.getChildren().clear();
		participantOutput.setText("Value: 0");
	}
}
