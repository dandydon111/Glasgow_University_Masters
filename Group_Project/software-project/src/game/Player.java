package game;

public class Player {

	//Instance variables
	private Hand hand;
	private Card currentCard;

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	private String name;
	private boolean AI = false;
	private int winCounter = 0;


	/**
	 * Constructor
	 * @param name of player
	 */
	public Player(String name, boolean AI) {

		hand = new Hand();
		this.name = name;
		this.AI = AI;
	}


	/**
	 * Default object string is the player name
	 */
	public String toString() {

		if (AI) {
			
			return name + " (AI)";
		}
		else {
			
			return name;
		}
	}

	/**
	 * Adds a card to the players hand
	 * @param card
	 */
	public void addToHand(Card card) {

		hand.add(card);
	}

	/**
	 * Draw the first card from the players hand
	 * @return card
	 */
	public Card drawCard() {

		currentCard = hand.draw();
		return currentCard;
	}


	/**
	 * getters and setters for all Player attributes
	 * @return
	 */
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAI() {
		return AI;
	}

	public void setAI(boolean aI) {
		AI = aI;
	}

	public int getRemainingCards() {

		return hand.getCardCount();
	}

	public Hand getHand() {

		return hand;
	}

	public String getBestAttribute() {

		return currentCard.getBestAttribute();
	}

	public Card getCurrentCard() {

		return currentCard;
	}
	
	public int getWinCounter() {
		return winCounter;
	}

	public void setWinCounter(int winCounter) {
		this.winCounter = winCounter;
	}
	
}
