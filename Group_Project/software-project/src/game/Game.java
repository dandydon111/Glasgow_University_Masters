package game;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Game {

	//Class instance variables
	private Deck deck = new Deck();
	private int numberOfPlayers; // Number of starting players
	private ArrayList<Player> startingPlayers = new ArrayList<Player>(); //ArrayList to hold the initial players
	private ArrayList<Player> players = new ArrayList<Player>(); // ArrayList to hold the players still in game
	private CardPile communalPile = new CardPile(); // Variable for the communal pile
	private Player currentPlayer; // Variable to hold the player whose turn it is
	private Player winner; // Variable for the winner of the Game
	private int roundCounter = 0; // Variable for the number of rounds
	private int drawCounter = 0; // Variable for the number of draws
	private int gameID; // The ID of the game

	//Initialise the test log variables
	private boolean testlogMode = false;
	private Testlog testlog;


	/**
	 * Default Constructor 
	 */
	public Game() {

	}


	/**
	 * Method that initialises deck and sets up game
	 * @param testlogMode: whether the user wants a log file to be created
	 * @param AIPlayers: the number of AI players chosen by the user
	 */
	public void initGame(boolean testlogMode, int AIPlayers) {

		this.testlogMode=testlogMode;

		//If the user wishes to print to the test log, open a print writer
		if (testlogMode) testlog = new Testlog();

		//Populate the deck
		populateDeck();

		//If testlog mode is active, print the initial deck to the log
		if (testlogMode) testlog.printDeckToLog(deck, "Initial deck");

		//Shuffle the deck
		deck.shuffle();

		//If testlog mode is active, print the shuffled deck to the log
		if (testlogMode) testlog.printDeckToLog(deck, "shuffled deck");

		//Set the number of AI players
		setAIPlayers(AIPlayers);

		//Deal cards to all players
		deal();

		//Choose a random first player
		setFirstPlayer();
	}


	/**
	 * Populates the deck with cards from the input file
	 */
	private void populateDeck() {

		//File in which card info is contained
		String filename = "StarCitizenDeck.txt";

		//Initialise scanner and reader to null
		FileReader reader = null;
		Scanner scanner = null;

		try {

			try {

				//Set up reader and scanner
				reader = new FileReader(filename);
				scanner = new Scanner(reader);

				//Counter to skip first line
				int lineNumber = 0;

				//Scan all lines in the file
				while (scanner.hasNextLine()) {

					//Get a complete line from the file
					String data = scanner.nextLine();

					//If it is not the first line of the file
					if (lineNumber != 0) {

						//Create a new card object
						Card card = new Card(data);

						//Add the card to the deck
						deck.add(card);

					}

					//Increment the line number
					lineNumber++;
				}
			}

			finally {

				//Close the reader and scanner if they have been opened
				if (reader != null) {
					//close the reader
					reader.close();
				}
				if (scanner != null) {
					//close the scanner
					scanner.close();
				}
			}
		}

		catch (IOException e) {

			//File loading error
			System.err.println("Error loading file");
			System.exit(0);
		}
		catch (InputMismatchException e) {

			//File reading error
			System.err.println("Error reading file");
			System.exit(0);
			
		}
	}


	/**
	 * Initialises the Player ArrayLists given the number of AI players
	 * @param p: the number of AI players selected by the user
	 */
	private void setAIPlayers(int p) {

		// Total players is AI player + human player
		numberOfPlayers = p + 1;

		// Adds all players to the Player ArrayLists
		for (int i = 0; i <= (numberOfPlayers-1); i++) {

			// First player is always human, create a human Player object and add it in the first index of the ArrayLists
			if (i==0) { 

				Player player = new Player("You", false);
				players.add(0, player);
				startingPlayers.add(0, player);
			}

			else {

				// The rest are AI Players. Create an AI player object and add to the ArrayLists
				Player player = new Player("Player " + i,true);
				players.add(player);
				startingPlayers.add(player);
			}
		}	
	}


	/**
	 * Deals the cards to each player
	 */
	private void deal() {

		// Deal cards from the deck to the player
		deck.dealCards(players);

		// If the user wishes to print to the testlog, print each hand
		if (testlogMode) testlog.printHandsToLog(players);
	}


	/**
	 * Returns the random first player
	 * @return first player
	 */
	private Player setFirstPlayer() {

		// Integer to store random index
		int fp;

		// Generate random number
		Random rn = new Random();

		// Get a random integer in the correct range
		fp = rn.nextInt(numberOfPlayers);

		// Set the first player as the current player.
		currentPlayer = players.get(fp);

		// Return the first player
		return players.get(fp);
	}


	/**
	 * Draws the first card of all remaining players hands so that it is ready to play.
	 */
	public void drawCards() {

		//Each player draws a new card	
		for (int i=0;i<players.size();i++) {

			//Draw a new card
			players.get(i).drawCard();
		} 

		//If test log mode is active, write the cards in play to the file
		if (testlogMode) { 
			testlog.printRoundSeparator(roundCounter+1);
			testlog.printCurrentCardsToLog(players);
		}
	}


	/**
	 * Method with the logic of playing each round
	 * @param attribute: the attribute that has been selected to compare
	 * @return roundWinner: the player who has won the round
	 */
	public Player playRound(String attribute) {

		// Increase roundCounter
		roundCounter++;

		// Variable for the attribute the user chooses to compare
		String selectedAttribute = attribute.toLowerCase();

		// If testlog mode is active, print the selected attribute to the testlog 
		if (testlogMode) testlog.printSelectedAttributeToLog(currentPlayer, selectedAttribute);

		// Variable to store the winner of the round
		Player roundWinner = findWinner(selectedAttribute);

		// Allocate the cards to the winner/communal pile
		allocateDeck(roundWinner);

		// Set the winner to go next
		if(roundWinner!=null)
			currentPlayer = roundWinner;

		// If testlog is active
		if (testlogMode) {
			testlog.printRoundWinnerToLog(roundWinner);

			//Print the communal pile to the log
			testlog.printDeckToLog(communalPile, "Contents of communal pile after round");

			//Print players hands after the round to log
			testlog.printHandsToLog(players);
		}

		// Update game statistics
		updateGameStats(roundWinner);

		return roundWinner;
	}


	/**
	 * Finds the player that won
	 * @param selectedAttribute: the attribute that has been selected to compare
	 * @return roundWinner: the player who has won the round OR null if it is a draw
	 */
	private Player findWinner(String selectedAttribute) {

		// Variable for the highest value of selected attribute
		int max = 0;

		// Variable to store the winner of the round
		Player roundWinner = null;

		// Variable for the value of the attribute that is currently being tested
		int value = -1;

		// Variable to store how many times highest value was found 
		int counter = 0;

		// Go through each player's first card, find the max and hold the player that has it
		for (int i=0;i<players.size();i++) { 
			value = players.get(i).getCurrentCard().getAttribute(selectedAttribute);

			if (value>max) {
				max = value; // if the currently-tested value is greater than max, set max to currently-tested value
				roundWinner = players.get(i); // set player with the highest value as the round winner
			}
		}

		// Check if more than one card has the highest value 
		for (int j=0; j<players.size(); j++) {
			if (players.get(j).getCurrentCard().getAttribute(selectedAttribute) == max)
				counter++;
		}

		// Check if it's a draw. Return null if it is.
		if (counter>1) {
			roundWinner = null;
		}

		return roundWinner;
	}


	/**
	 * Allocates the cards to the right place, according to the winner
	 * @param roundWinner: the player that won the round, or null if it's a draw
	 */
	private void allocateDeck (Player roundWinner) {

		// If it is a draw, put all current cards in the communalPile
		if (roundWinner==null) {
			for (int i = 0; i < players.size(); i++)
				communalPile.add(players.get(i).getCurrentCard());
		}

		// If there is a winner
		else {
			// Add all the current cards to his hand
			for (int i = 0; i < players.size(); i++) {
				roundWinner.addToHand(players.get(i).getCurrentCard());
			}
			// Add the cards from communalPile (if any) to his hand 
			if (communalPile.getCardCount()>0) {
				for (int i = 0; i< communalPile.getCardCount(); i++) {
					roundWinner.addToHand(communalPile.getCard(i));
				}

				// empty communal Pile
				clearCommunalPile();
			}		
		}
	}


	/**
	 * Method to remove all cards from communal pile
	 */
	private void clearCommunalPile() {
		for (int i = (communalPile.getCardCount()-1); i > -1 ; i--) {
			communalPile.remove(i);
		}
	}


	/**
	 * Method to determine if the game is over: checks if more than one players have cards left
	 * @return continueGame: TRUE or FALSE. If game should continue
	 */
	/**
	 * Method to determine if the game is over: checks if more than one players have cards left
	 * @return if game should continue
	 */
	public boolean continueGame() {

		// Flag variable that shows if game should continue
		boolean continueGame = true;

		// If there is only one player left, end game
		if (players.size()<2) {
			continueGame = false;

			// The winner will be the only player left in the players array
			winner = players.get(0);

			// If testlog is active, print the winner and then close the writer
			if (testlogMode) {
				testlog.printWinnerToLog(winner);
				testlog.closeLogWriter();
			}
		}

		return continueGame;
	}


	/**
	 * Method to remove eliminated players from the list of players
	 * Must be called at the end of every round
	 * @return eliminated: an ArrayList of all the eliminated players
	 */
	public ArrayList<Player> clearPlayers() {

		// ArrayList to store all of the eliminated players
		ArrayList<Player> eliminated = new ArrayList<Player>();

		// Loop through all remaining players
		for (int i=(players.size()-1); i >=0 ; i--) {

			// If the player has no cards remaining
			if (players.get(i).getRemainingCards() == 0 ) {

				// Add to the list of eliminated players
				eliminated.add(players.get(i));

				// Remove the player from the list of remaining players 
				players.remove(i);
			}
		}

		// Return the list of eliminated players
		return eliminated;
	}


	/**
	 * In the end of every round, updates the number of rounds played
	 * and returns a String if player was AI or Human
	 * @param p : the winning player
	 * @return s : "AI won" / "Human won"
	 */
	private void updateGameStats(Player p) {

		// If there is a winner for the round, increase winner's winCounter 
		if (p != null) 
			p.setWinCounter(p.getWinCounter()+1);

		// If the round has no winner (is a draw), increase drawCounter
		else drawCounter++;
	}


	/*
	 *  Getters & Setters
	 */

	/**
	 * Method to get the gameID
	 * @return gameID
	 */
	public int getGameID() {
		return gameID;
	}


	/**
	 * Method to set the gameID
	 * @param gameID
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}


	/**
	 * Method to get the number of rounds
	 * @return roundCounter: the number of rounds
	 */
	public int getRoundCounter() {
		return roundCounter;
	}


	/**
	 * Method to get the number of draws
	 * @return roundCounter: the number of draws
	 */
	public int getDrawCounter() {
		return drawCounter;
	}


	/**
	 * Method to get the content of the communal pile
	 * @return communalPile: the communal pile
	 */
	public CardPile getCommunalPile() {
		return communalPile;
	}


	/**
	 * Method to get the player who's turn it is to play
	 * @return the player who's turn it is
	 */
	public Player getCurrentPlayer() {

		return currentPlayer;
	}


	/**
	 * Method to get a list with the starting Players
	 * @return startingPlayers: ArrayList with the starting Players
	 */
	public ArrayList<Player> getStartingPlayers() {
		return startingPlayers;
	}


	/**
	 * Method to get a list of all the remaining players in the game
	 * @return list of remaining players
	 */
	public ArrayList<Player> getPlayers() {

		return players;
	}


	/**
	 * Method to get the number of players still in the game
	 * @return number of players still in game
	 */
	public int getNumberOfPlayer() {

		return players.size();
	}


	/**
	 * Returns the winner of the game
	 * @return winner: the winning player
	 */
	public Player getWinner(){

		return winner;
	}
}
