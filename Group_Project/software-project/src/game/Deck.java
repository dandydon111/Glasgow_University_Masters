package game;
import java.util.ArrayList;
import java.util.Collections;

public class Deck extends CardPile {
	
	/**
	 * Default constructor calls super constructor
	 */
	public Deck() {
		
		super();
	}

	/**
	 * Shuffles the deck into a random order
	 */
	public void shuffle() {
		
		//Call the shuffle function in the collections class
		Collections.shuffle(pile);
	}
	
	
	/**
	 * Deal the cards out to the players
	 * @param players
	 */
	public void dealCards(ArrayList<Player> players) {
		
		//Counter for looping
		int counter = 0;
		
		//Loop through the deck of cards
		for (int i = 0; i < pile.size(); i++) {
			
			//Add the card to the players hand
			players.get(counter).addToHand(pile.get(i));
			
			//If all players have been dealt to, start again at first player
			if (counter == players.size() - 1) {
				
				counter = 0;
			}
			//Else increment counter
			else {
				
				counter++;
			}
		}
	}
}
