package DBHandler;
import java.sql.*;

public class PostgresSQL {

	private static Connection connection = null;

	/**
	 * Default constructor
	 */
	public PostgresSQL() {

	}

	/**
	 * Method to establish connection to the database
	 */
	private static void sqlConnection() {	

		// Database credentials
		String databaseName = "m_17_2352834c";
		String userName = "m_17_2352834c";
		String password = "2352834c";

		try {
			connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + databaseName,
					userName, password);
		}

		catch (SQLException e) {

			System.err.println("Connection Failed");
			e.printStackTrace();
			return;
		}
		if (connection == null) {
			System.err.println("Failed to make connection!");
		}
	}

	/**
	 * Method to close the connection to the database
	 */
	private static void close() {	//Close the connection to the database

		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection could not be closed: SQL exception");
		}
	}


	/**
	 * Method that looks in the database for the highest game number, and returns it incremented by one
	 * @return newGameNo: the game number to be used
	 */
	public int setCurrentGameNo() {

		// Variable for the greatest game number in the database.
		int currentGameNo = 0;

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT * FROM toptrumps.game ORDER BY gameno DESC LIMIT 1;";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// Getting the highest entry game number in the table
			while (rs.next()) {
				String gameno = rs.getString("gameno");
				currentGameNo = Integer.parseInt(gameno); 
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}

		// Close database connection
		close(); 

		// Set new game number by incrementing the highest found, by one
		int newGameNo = (currentGameNo + 1);

		return newGameNo;
	}


	/**
	 * Method that inserts the statistics in the Game table of the database
	 * @param currentGameNo: the current game number
	 * @param totalRounds: the number of rounds of the specific game
	 * @param totalDraws: the number of draws of the specific game
	 * @param winner: the winner of the game
	 */
	public void insertIntoGameTable(int currentGameNo, int totalRounds, int totalDraws, String winner) {			

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "INSERT INTO toptrumps.game(gameno, totalrounds, totaldraws, winner) VALUES ('" + currentGameNo + "','" + totalRounds + "','" + totalDraws + "','" + winner + "') ;";

		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();
	}


	/**
	 * Method that returns the number of draws for a given game, from the database
	 * @param currentGameNo: the ID of game of interest
	 * @return noOfDraws: the number of draws of the given game
	 */
	public String viewNoOfDraws(int currentGameNo) {	
		// Variable for the number of draws
		String noOfDraws = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT totaldraws FROM toptrumps.game WHERE gameno='" + currentGameNo + "' ;"; 

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				noOfDraws = rs.getString("totaldraws");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			noOfDraws = ("error executing query " + query);
		} 

		// Close database connection
		close();

		return noOfDraws;
	}


	/**
	 * Method that returns the winner of a given game
	 * @param currentGameNo: the ID of game of interest
	 * @return winner: the winner of the game of interest
	 */
	public String viewWinner(int currentGameNo) {

		// Variable for the winner of the game
		String winner = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT winner FROM toptrumps.game WHERE gameno='" + currentGameNo + "';";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				winner = rs.getString("winner");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();

		return winner;
	}


	/**
	 * Method that returns the number of rounds of a given game
	 * @param currentGameNo: the ID of game of interest
	 * @return rounds: the number of rounds
	 */
	public String viewTotalRounds(int currentGameNo) {	

		// Variable for the number of rounds
		String rounds = ""; 

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT totalrounds FROM toptrumps.game WHERE gameno='" + currentGameNo + "';";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				rounds = rs.getString("totalrounds");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();

		return rounds;
	}


	/**
	 * Method to insert data in players' tables, given the player's name
	 * @param currentGameNo: the ID of game of interest
	 * @param roundsWon: the number of times the given player won
	 * @param playerName: the player whose statistics we are updating
	 */	 
	public void insertPlayersTables(int currentGameNo, int roundsWon, String playerName) {

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;		
		String query = ""; // The query to be executed depends on the parameters

		// Set query according to the parameters of the method
		if (playerName.equals("You"))
			query = "INSERT INTO toptrumps.human (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')";

		else if  (playerName.equals("Player 1"))
			query = "INSERT INTO toptrumps.ai1 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')";

		else if  (playerName.equals("Player 2"))
			query = "INSERT INTO toptrumps.ai2 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')";

		else if  (playerName.equals("Player 3"))
			query = "INSERT INTO toptrumps.ai3 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')";

		else if  (playerName.equals("Player 4"))
			query = "INSERT INTO toptrumps.ai4 (gameno, roundswon) VALUES ('" + currentGameNo + "', '" + roundsWon + "')";

		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();
	}


	/**
	 * Method that returns the number of rounds in the given game, that the given player won
	 * @param currentGameNo: the ID of game of interest
	 * @param playerName: the player whose statistics we are looking for
	 * @return roundsWon: the number of rounds the given player won
	 */
	public String playerRoundsWon(int currentGameNo, String playerName) {	

		// Variable for the rounds won
		String roundsWon = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = ""; // The query to be executed depends on the parameters

		// Set query according to the parameters of the method
		if (playerName.equals("You"))
			query = "SELECT roundswon FROM toptrumps.human WHERE gameno='" + currentGameNo + "';";

		else if (playerName.equals("Player 1"))
			query = "SELECT roundswon FROM toptrumps.ai1 WHERE gameno='" + currentGameNo + "';";

		else if (playerName.equals("Player 2"))
			query = "SELECT roundswon FROM toptrumps.ai2 WHERE gameno='" + currentGameNo + "';";

		else if (playerName.equals("Player 3"))
			query = "SELECT roundswon FROM toptrumps.ai3 WHERE gameno='" + currentGameNo + "';";

		else if (playerName.equals("Player 4"))
			query = "SELECT roundswon FROM toptrumps.ai4 WHERE gameno='" + currentGameNo + "';";

		try {
			stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				roundsWon = rs.getString("roundswon");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();

		return roundsWon;
	}


	/**
	 * Method that returns the total number of games that have been recorded in the database
	 * @return noOfGamesPlayed: the total number of games recorded in the database 
	 */
	public static String noOfGamesPlayed() { 

		// Variable for the number of games
		String noOfGamesPlayed = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT COUNT (gameno) FROM toptrumps.game;";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				noOfGamesPlayed = rs.getString("count");

			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 	

		// Close database connection
		close();

		return noOfGamesPlayed;
	}


	/**
	 *  Method that returns the total number of games that AI players have won
	 *  @return noOfAIWins: the total number of games that AI players have won
	 */
	public static String noOfAIWins() {
		// Variable for the number of AI wins
		String noOfAIWins = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT COUNT (winner) FROM toptrumps.game WHERE winner = 'Player 1' OR winner = 'Player 2' OR winner = 'Player 3' OR winner = 'Player 4';";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				noOfAIWins = rs.getString("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();

		return noOfAIWins;
	}


	/**
	 *  Method that returns the total number of games that the user has won
	 *  @return noOfHumanWins: the total number of games that the user has won
	 */
	public static String noOfHumanWins() {
		// Variable for the number of Human wins
		String noOfHumanWins = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT COUNT (winner) FROM toptrumps.game WHERE winner = 'You';";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				noOfHumanWins = rs.getString("count");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}

		// Close database connection
		close();

		return noOfHumanWins;
	}


	/**
	 *  Method that returns the average number of draws of the games recorded in the database
	 *  @return avgDraws: the average number of draws
	 */
	public static String avgNoOfDraws() {

		// Variable for the average number of draws
		String avgDraws = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT AVG (totaldraws) FROM toptrumps.game;";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				avgDraws = rs.getString("avg");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();

		return avgDraws;
	}


	/**
	 *  Method that returns the highest number of rounds in a single game recorded in the database
	 *  @return maxRounds: the highest number of rounds in a single game
	 */
	public static String maxNoOfRoundsPlayed() {

		// Variable for the maximum number of rounds
		String maxRounds = "";

		// Connect to the database
		sqlConnection();

		// Execute query
		Statement stmt = null;
		String query = "SELECT MAX (totalrounds) FROM toptrumps.game;";

		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				maxRounds = rs.getString("max");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("error executing query " + query);
		} 

		// Close database connection
		close();

		return maxRounds;
	}
}
