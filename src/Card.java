import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {
	
	/*
	 * This class represents a card object, with the main data field
	 * being the Face, which the values and extended image are derived from.
	 */
	private static String[] FACES = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
	private static int HEIGHT = 130;

	private String face;
//	private ImageView cardImage;
	private Image image;

	Card(String face) {
		this.face = face;
		try {
			image = new Image("file:images/" + face + ".png");

			this.setImage(image);

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} finally {
			this.setFitHeight(HEIGHT);
			this.setPreserveRatio(true);
		}
	}

	public String getFace() {
		return face;
	}

	// This handles the resetting of an Ace to a value of 1, by giving it a face of
	// 1.
	public void setFace(String face) {
		this.face = face;
	}

	// Evaluates the value of the card based on the card's face, and return it.
	public int valueOf() {

		switch (face) {
		case "A":
			return 11;
		// 1 handles Aces that have been turned to ones.
		case "1":
			return 1;
		case "2":
			return 2;
		case "3":
			return 3;
		case "4":
			return 4;
		case "5":
			return 5;
		case "6":
			return 6;
		case "7":
			return 7;
		case "8":
			return 8;
		case "9":
			return 9;
		case "10":
		case "J":
		case "Q":
		case "K":
			return 10;
		default:
			return -1;
		}
	}
}
