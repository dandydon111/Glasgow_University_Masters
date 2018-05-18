package game;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Testlog {

	//Initialise the test log variables
	private String filename = "toptrumps.log";
	private PrintWriter writer = null;

	/**
	 * Constructor
	 * Opens the file connection
	 */
	public Testlog() {

		openLogWriter();
	}

	/**
	 * Prints the attribute that has been selected to play and the player that has selected it 
	 * @param player
	 * @param attribute
	 */
	public void printSelectedAttributeToLog(Player player, String attribute) {

		//Print the chosen attribute
		writer.println(System.lineSeparator() + player + " chose the catagory: " + attribute);
	}

	/**
	 * Prints the round winner to the log
	 * @param roundWinner
	 */
	public void printRoundWinnerToLog(Player roundWinner) {

		//If there is no winner print that it has been a draw
		if (roundWinner == null) {
			writer.println(System.lineSeparator() + "The round was a draw.");
		}
		//Else print the round winner
		else {
			writer.println(System.lineSeparator() + roundWinner + " won the round.");
		}
	}

	/**
	 * Prints the winenr of the game to the log
	 * @param winner
	 */
	public void printWinnerToLog(Player winner) {

		//Print the winner
		writer.println(System.lineSeparator() + System.lineSeparator() + winner + " won the game");
	}

	/**
	 * Prints an entire deck to the log with a description of what the deck is
	 * @param deck
	 * @param description
	 */
	public void printDeckToLog(CardPile deck, String description) {

		//Display the description of what the deck is
		writer.print(System.lineSeparator() + description + ": ");
		//Loop through the whole deck
		for (int i=0;i<deck.getPile().size();i++)
			//Print every card in the deck
			writer.print(deck.getPile().get(i).getName() + " ");
		writer.print(System.lineSeparator());
	}

	/**
	 * Prints all the players current cards to the log
	 * @param players
	 */
	public void printCurrentCardsToLog(ArrayList<Player> players){

		writer.println("Cards in play");
		//Loop through all players
		for (int i = 0; i < players.size(); i++) {

			//Prints every players card to the log
			writer.println(players.get(i).getName() + ": " + players.get(i).getCurrentCard());
		}
	}

	/**
	 * Prints every players hand to the testlog file
	 * @param players
	 */
	public void printHandsToLog(ArrayList<Player> players) {

		
		writer.print(System.lineSeparator() + "Players hands after cards have been allocated");
		//Loop through each player in the array list
		for (int i = 0; i < players.size(); i++) {

			//Display the player who the hand belongs to
			writer.print(System.lineSeparator() + players.get(i) + ": ");
			for (int j=0; j<players.get(i).getHand().getPile().size();j++) {
				//Prints the players hand to the test log file
				writer.print(players.get(i).getHand().getPile().get(j).getName() + " ");
			}
		}
	}

	/**
	 * Prints a separator with the current round number into the log file
	 * @param round - round number
	 */
	public void printRoundSeparator(int round) {

		writer.println(System.lineSeparator() + "----------- Round " + round + " -----------");
	}

	/**
	 * Opens a print writer to write to the test log file
	 */
	private void openLogWriter() {

		try {

			//Set up the PrintWriter
			writer = new PrintWriter(filename);
		} 
		//Catch exception
		catch (FileNotFoundException e) {

			//Show error message
			System.out.println("The file could not be found");
		}
	}

	/**
	 * Closes the print writer when it is no longer needed
	 */
	public void closeLogWriter(){

		//Close the writer if it has been opened
		if (writer != null) {
			writer.close();
		}
	}
}
