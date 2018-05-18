package statisticsPrint;

import DBHandler.*;
import game.Game;

public class StatsPrinter {

	
	/**
	 * Default Constructor
	 */
	public StatsPrinter() {

	}
	

	/**
	 *  Method to return a ready-to-display String with the total number of games played so far
	 * @returns s: the String to be displayed
	 */
	private String printSumGames() {

		// The string that will contain the information asked
		String s = "";

		// String with the total number of games, returned by the database
		String sumGames = PostgresSQL.noOfGamesPlayed();	

		// Set the string in its final form
		s = "The total number of games played so far is: " + sumGames + ".";

		return s;
	}

	
	/**
	 *  Method to return a ready-to-display String with the total number of AI wins
	 * @returns s: the String to be displayed
	 */
	private String printAiWins() {

		// The string that will contain the information asked
		String s = "";

		// String with the total number of AI wins, returned by the database
		String AiWins = PostgresSQL.noOfAIWins();	

		// Set the string in its final form
		s = "The computer has won : " + AiWins + " times.";

		return s;
	}


	/**
	 *  Method to return a ready-to-display String with the total number of Human wins
	 * @returns s: the String to be displayed
	 */
	private String printHumanWins() {

		// The string that will contain the information asked
		String s = "";
		
		// String with the total number of AI wins, returned by the database
		String humanWins = PostgresSQL.noOfHumanWins();	

		// Set the string in its final form
		s = "Human players have won : " + humanWins + " times.";

		return s;
	}

	
	/**
	 * Method to return a ready-to-display String with the average number of draws per game
	 * @return s: the String to be displayed
	 */
	private String printAvgDraws() {

		// The string that will contain the information asked
		String s = "";

		// String with the average number of draws returned by the database
		String drawsAvg = PostgresSQL.avgNoOfDraws();

		// Extract double and format it for display
		String drawsAvgDouble = String.format("%.2f", Double.parseDouble(drawsAvg));

		// Set the string in its final form
		s = "There are " + drawsAvgDouble + " draws per game on average." ;

		return s;
	}

	
	/**
	 *  Method to return a ready-to-display String with the longest game, in terms of rounds played
	 * @returns s: the String to be displayed
	 */
	private String printMaxRounds() {

		// The string that will contain the information asked
		String s = "";

		// String with the total number of AI wins, returned by the database
		String maxRounds = PostgresSQL.maxNoOfRoundsPlayed();	

		// Set the string in its final form
		s = "The longest game had "+ maxRounds + " rounds played!";

		return s;
	}


	/**
	 * Method to print all the statistics in the beginning of the game
	 * @return s: the String containing all the statistics formatted and ready to display
	 */
	public String printStatsStart() {

		// The string that will contain the information asked
		String s = "";

		s= printSumGames() + "\n" +
				printAiWins()+ "\n" +
				printHumanWins()+ "\n" +
				printAvgDraws()+ "\n" +
				printMaxRounds()+ "\n";

		return s;
	}
	
}
