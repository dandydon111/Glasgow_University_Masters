package game;
import java.util.ArrayList;

public class CardPile {

	// Class instance variables
	protected ArrayList<Card> pile;
	private int cardCount;


	/**
	 * Constructor initialises the card array
	 */
	public CardPile() {

		pile = new ArrayList<Card>();
		cardCount = 0;
	}


	/**
	 * Method that adds a card to the pile
	 * @param card
	 */
	public void add(Card card) {

		pile.add(card);
		cardCount++;
	}


	/**
	 * Method that removes the card at index i from the pile
	 * @param i
	 */
	public void remove(int i) {

		pile.remove(i);
		cardCount--;
	}


	/*
	 * Getters and Setters
	 */

	/**
	 * Method that returns the number of cards in the pile
	 * @return card count
	 */
	public int getCardCount() {
		cardCount = pile.size();
		return cardCount;
	}


	/**
	 * Method that returns Card object at index position i
	 * @param i: the index
	 * @return Card at index position i
	 */
	public Card getCard(int i) {

		return pile.get(i);
	}


	/**
	 * Prints the contents of the deck to the log file
	 */
	public ArrayList<Card> getPile() {

		//This needs to be changes to print to the log instead of the console
		return pile;
	}
}
