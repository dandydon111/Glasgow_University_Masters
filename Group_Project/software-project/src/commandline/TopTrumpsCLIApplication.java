package commandline;

import game.*;
import DBHandler.*;
import statisticsPrint.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	
	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
	 * @param args
	 */
	public static void main(String[] args) {

		PostgresSQL con = new PostgresSQL(); // SQL object, to be used for interaction with the database
		boolean writeGameLogsToFile = false; // Should we write game logs to file?

		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection

		//Create a scanner object to read user input
		Scanner scanner = new Scanner(System.in);

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			//Ask user if they want to view statistics
			promptStats(scanner);

			//Get the number of players the user wishes to play against
			int numPlayers = chooseAIPlayers(scanner);

			//Start the game logic
			Game game = new Game();
			game.initGame(writeGameLogsToFile, numPlayers);

			//retrieve new game number from database 
			game.setGameID(con.setCurrentGameNo());

			//Play rounds until there is a winner
			roundLogic(scanner, game);

			//The game is over. Update Database
			// Update game table
			con.insertIntoGameTable(game.getGameID(), game.getRoundCounter(), game.getDrawCounter(), game.getWinner().getName());
			
			// Update players' tables
			for (int i=0 ; i < game.getStartingPlayers().size(); i ++) {
				con.insertPlayersTables(game.getGameID(), game.getStartingPlayers().get(i).getWinCounter(), game.getStartingPlayers().get(i).getName() );
			}

			//The game is over. Display winner
			System.out.println("\n------ Game Over ------ \nThe winner is " + game.getWinner() +"\n\nWould you like to play a new game? Type q to quit the game or anything else to continue playing.");
			
			//Ask user whether they wish to continue with game
			String keepPlaying = scanner.nextLine().toLowerCase();

			//Keep playing?
			if (keepPlaying.equals("q")) {

				userWantsToQuit=true;
			}
			else continue;
		}
	}

	/**
	 * Plays rounds until a there is a winner
	 * @param scanner
	 * @param game
	 */
	private static void roundLogic(Scanner scanner, Game game) {
		
		boolean skipGame = false;

		while (game.continueGame()) {

			//Whose turn is it to play
			Player currentPlayer = game.getCurrentPlayer();
			//Draw cards and add them to players hands
			game.drawCards();
			//Get all the remaining players for output purposes
			ArrayList<Player> players = game.getPlayers();

			//Display which round we are in
			System.out.println("\nRound " + (game.getRoundCounter()+1));
			
			//If the human player is still in the game
			if (!players.get(0).isAI()) {

				//The human will always be at index 0 if they are in the game. Display their card	
				System.out.println("Your card is " + players.get(0).getCurrentCard());
			}

			//Get the attribute that is to be compared
			String selectedAttribute = getSelectedAttribute(scanner, currentPlayer);

			//Display everyones cards
			displayCards(players);

			//Get the winner of the round and display in the console
			Player winner = game.playRound(selectedAttribute);
			displayWinner(winner);

			//Get a list of all eliminated players and display in the console
			ArrayList<Player> eliminated = game.clearPlayers();
			displayEliminatedPlayers(eliminated);
			
			//If the human player is still in the game
			if (!players.get(0).isAI()) {
				//Wait for user input to start the next round
				System.out.println("Type anything to play the next round");
				String nextRound = scanner.nextLine();
			}
			//If human is out play the remaining rounds without input
			else {
				//Only wait for input immediately after the player has been eliminated
				while (!skipGame) {
					
					System.out.println("Sorry you're out. Type anything to skip the rest of the game.");
					skipGame = true;
					String nextRound = scanner.nextLine();
				}
			}
		}
	}

	/**
	 * Ask the user whether they wish to see the stats of previous games before they play
	 * @param scanner
	 */
	private static void promptStats(Scanner scanner) {

		//Loop until the user wants to play the game
		for (;;) { 

			//Prompt user whether they wish to see stats
			System.out.println("Type s to see stats or type anything else to play a new game!");
			//Wait for user input and put to lower case
			String stats = scanner.nextLine().toLowerCase();

			//User has chosen to view stats
			if (stats.equals("s")) {

				// Print statistics
				StatsPrinter pr = new StatsPrinter();
				System.out.println(pr.printStatsStart());

			}
			else {

				//User wants to play the game. Return to main loop
				return;
			}
		}
	}

	/**
	 * Allows the user to choose how many AI player they wish to play against
	 * @param scanner
	 * @return the number of AI players the user has chosen
	 */
	private static int chooseAIPlayers(Scanner scanner) {

		//Prompt user to choose the number of AI players
		System.out.println("How many AI players would you like to play against (Max 4)");

		//Loop until the user enters a valid response
		for(;;) {

			try {

				//Get the user response
				int numPlayers = scanner.nextInt();

				//Check that it is between 1 and 4
				if (numPlayers < 1 || numPlayers > 4) {

					//Inform user that the number is out of range
					System.out.println("Please enter a number between 1 and 4");
				}
				//If valid, return the integer
				else {

					System.out.println("You have chosen " + numPlayers + " AI players. Let the games begin.");
					
					//Use up carriage return
					scanner.nextLine();
					return numPlayers;
				}
			}
			//Catch any non integer that is entered
			catch (InputMismatchException e) {

				//Tell user input needs to be an integer
				System.out.println("Please enter an integer");
				//Use up carriage return
				scanner.nextLine();
			}	
		}
	}

	/**
	 * Gets the attribute that will be compared in the round
	 * @param scanner
	 * @param currentPlayer
	 * @return the chosen attribute
	 */
	private static String getSelectedAttribute(Scanner scanner, Player currentPlayer) {

		String selectedAttribute;

		//The current player is an AI
		if (currentPlayer.isAI()) {

			//Tell the user which AI is playing 
			System.out.println(currentPlayer + " to play");
			//Select the strongest attribute on the AI's card
			selectedAttribute = currentPlayer.getBestAttribute();
			//Tell the user which category the AI has chosen
			System.out.println(currentPlayer + " chooses the catagory " + selectedAttribute);
		}
		//It is the users turn to play
		else {

			//Get user input
			selectedAttribute = getUserAttributeInput(scanner);
		}

		//Retunr the attribute
		return selectedAttribute;
	}

	/**
	 * Get the users input for which attribute they wish to choose
	 * @param scanner
	 * @return the chosen category
	 */
	private static String getUserAttributeInput(Scanner scanner) {

		//Prompt the user to pick a category
		System.out.println("It is your turn. Pick a catagory to compare.");

		String selectedAttribute;

		//Loop until the user provides a valid input
		for (;;) {

			//Read the users input
			selectedAttribute = scanner.nextLine();

			//Check the input is valid
			if (!(selectedAttribute.toLowerCase().equals("speed") || selectedAttribute.toLowerCase().equals("firepower")||
					selectedAttribute.toLowerCase().equals("size")|| selectedAttribute.toLowerCase().equals("cargo")||
					selectedAttribute.toLowerCase().equals("range"))) {

				//If the input is not valid, tell user and ask again
				System.out.println("Selected attribute " + selectedAttribute + " does not exist. Please enter one of the following attributes: Speed - Cargo - Firepower - Size - Range.");
			}
			else {

				//The input is valid. Tell user and break the loop
				System.out.println("You have selected " + selectedAttribute + ".");
				break;
			}
		}

		//Return the chosen attribute
		return selectedAttribute;
	}

	/**
	 * Display the winner of the round
	 * @param winner
	 */
	private static void displayWinner(Player winner){

		//No winner. The round was a draw, inform user
		if (winner == null) {
			System.out.println("The round was a draw. Cards added to the communal pile.");
		} else {		
			//Print the winner
		 System.out.println(winner.getName() + " won the round.");
		}
	}

	/**
	 * Displays any players that have been eliminated from the game
	 * @param eliminated players
	 */
	private static void displayEliminatedPlayers(ArrayList<Player> eliminated) {

		//If there have been players eliminated
		if (!(eliminated == null)) {
			
			//Loop through all eliminated players
			for (int i = 0; i < eliminated.size(); i++) {

				//Get the player from the arrayList
				Player elimPlayer = eliminated.get(i);

				//If the eliminated player is an AI
				if (elimPlayer.isAI()) {

					//Display to the user which AI has been eliminated
					System.out.println(elimPlayer + " has been eliminated");	
				}
				//If the user has been eliminated
				else {
					
					//Inform the user they are out
					System.out.println("You have been eliminated");
				}
			}
		}
	}

	/**
	 * Displays all the cards currently in play
	 * @param players
	 */
	private static void displayCards(ArrayList<Player> players) {

		System.out.println("Everbody shows their cards");

		//Print out the current cards of all the players
		for (int i = 0; i < players.size(); i++) {

			System.out.println(players.get(i) + " - " + players.get(i).getCurrentCard());
		}
	}
}
