import java.util.ArrayList;
import java.util.Random;

public class Player {
	/*
	 * This class represents a player object.
	 * This handles the deck and hand of the player.
	 */
	
	private ArrayList<Card> hand = new ArrayList<Card>(12);
	private static Deck masterDeck = new Deck();
	private int playerNumber;
	private int score = 0;

	// Generates a random boolean for stand(), to determine a 50:50 stand chance
	// if player is 16 or higher.
	private Random random = new Random();

	//Constructor
	Player(int number) {
		this.playerNumber = number;
	}

	//Returns the player's hand as an ArrayList.
	public ArrayList<Card> getHand() {
		return hand;
	}

	//Used to "reshuffle" the deck in between games.
	public void resetDeck() {
		masterDeck.reset();
	}

	//Adds a card from masterDeck to the player's hand.
	public void hit() {
		hand.add(masterDeck.dealCard());
	}

	//Updates the player's score.
	public int getUpdateScore() {
		score++;
		return score;
	}

	// Determines if the dealer will stand or
	boolean stand(int otherPlayerValue) {
//			//Doesn't even play if the max player number is >= 19.
//		if (otherPlayerValue >= 19) {
//			return true;
//			//50:50 chance that the dealer will play the round if
//			//the maximum player value is >= 16, but < 19.
//		} else if (otherPlayerValue >= 16) {
//			if (random.nextBoolean()) {
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
		return false;
	}

	// Cycles through the player's hand and returns the value of
	// the player's hand.
	public int valueOfHand() {
		int temp = 0;
		if (hand.size() == 0) {
			return 0;
		}
		for (int i = 0; i < hand.size(); i++) {
			temp += hand.get(i).valueOf();
		}

		// If a player is going to go over 21, it goes through, and changes
		// Aces faces to a face of 1, and checks to see if it's still over.
		// If not, continue.
		// If its < 21, break.
		if (temp > 21) {
			for (int i = 0; i < hand.size(); i++) {
				if ((hand.get(i).getFace()).equals("A")) {
					hand.get(i).setFace("1");
					temp -= 10;
					if (temp <= 21) {
						return temp;
					}
				}
			}
		}

		return temp;
	}

	// Clears the players hand.
	public void clearHand() {
		hand.clear();
	}

	// Checks the value of the player's hand and determines if they go
	// over 21, bust.
	public boolean bust() {
		if (valueOfHand() > 21) {
			return true;
		} else {
			return false;
		}
	}
}
