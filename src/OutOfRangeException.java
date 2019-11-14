//User defined exception used to for the specific case that a user inputs
//< 1 or > 5 players into the Blackjack game start up.
public class OutOfRangeException extends Exception {
	public OutOfRangeException(String message) {
		super(message);
	}
}
