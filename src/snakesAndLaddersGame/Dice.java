package snakesAndLaddersGame;

import java.util.Random;

import javafx.scene.layout.StackPane;

public class Dice extends StackPane {
public static Random random;
	
	/**
	 * Initialize the fields
	 */
	public Dice(){
		random = new Random();
	}
	
	
	/**
	 * Rolls a D6 and returns the value.
	 * @return An random int value between 1-6 (inclusive)
	 */
	public static int rollD6(){
		return random.nextInt(6)+1;
	}
}
