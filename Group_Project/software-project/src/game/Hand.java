package game;

public class Hand extends CardPile{
	
	/**
	 * Default constructor calls super constructor
	 */
	public Hand() {
		
		super();
	}
	
	/**
	 * Returns the first card from the players hand and deletes it from the pile
	 * @return The first card in the players hand
	 */
	public Card draw() {
		
		//Get the card at the first index position
		Card card = pile.get(0);
		//Remove the card from the players hand
		pile.remove(0);
		//Return the card
		return card;
	}
}
