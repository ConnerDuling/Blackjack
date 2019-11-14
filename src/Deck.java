import java.util.ArrayList;
import java.util.Random;

class Deck {
	
	/*
	 * This Deck class represents a standard deck of 52 cards, all of the
	 * cards of of the suit Spade for simplicity. Though, minor changes
	 * to the repopulate method to accommodate a four suited game.
	 * 
	 * This could be done be using the initial i variable to track suit
	 * 1. Hearts
	 * 2. Spades
	 * 3. Diamonds
	 * 4. Clubs
	 * With the j variable handling the normal card values.
	 */
	
	private ArrayList<Card> cards = new ArrayList<Card>(52);

	// ArrayList of integers to see if card number has been drawn before.
	// If so, program ignores and redraws.
	private ArrayList<Integer> previousDraws = new ArrayList<Integer>();

	// Repopulates when deck is made for the first time.
	Deck() {
		repopulate();
	}

	// Resets the tracker of drawn cards, clears the deck of cards, refill the deck.
	public void reset() {
		previousDraws.clear();
		cards.clear();
		repopulate();
	}

	// Reset all values of the deck.
	public void repopulate() {
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j <= 13; j++) {
				if (j == 1) {
					cards.add(new Card("A"));
				} else if (j > 1 && j <= 10) {
					cards.add(new Card(Integer.toString(j)));
				} else if (j == 11) {
					cards.add(new Card("J"));
				} else if (j == 12) {
					cards.add(new Card("Q"));
				} else {
					cards.add(new Card("K"));
				}
			}
		}
		cards.trimToSize();
	}

	// Draws a card from the deck and returns it.
	public Card dealCard() {
		Card temp;
		Random draw = new Random();
		int index;
		while (true) {
			index = draw.nextInt(52);
			temp = new Card(cards.get(index).getFace());
			if (previousDraws.contains(index)) {
				continue;
			} else {
				previousDraws.add(index);
				return temp;
			}
		}
	}

}
