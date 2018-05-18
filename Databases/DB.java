/**
 * Class to create/close database connections and to perform queries
 */

import java.sql.*;

public class DB {

	//Connection object
	private Connection connection =null;

	/**Set up the connection to the database
	 * @return true if connection has been made, false if not
	 */
	public boolean connect() {

		//connection details
		String dbname = "m_17_2031944h";
		String username = "m_17_2031944h";
		String password = "2031944h";

		try {
			//Try to connect to the database
			connection = DriverManager.getConnection("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + dbname, username, password);
		}
		catch (SQLException e) {
			//Exception thrown, return false
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return false;
		}
		if (connection != null) {
			//Connection is successful, return true
			System.out.println("Connection successful");
			return true;
		}
		else {
			//Connection was not successful, return false
			System.err.println("Failed to make connection!");
			return false;
		}
	}

	/**Close the connection to the database
	 * @return true if connection has been closed, false if not
	 */
	public boolean close() {

		try {
			//Close the connection and return true
			connection.close();
			System.out.println("Connection closed");
			return true;
		}
		catch (SQLException e) {
			//Connection could not be closed, return false
			e.printStackTrace();
			System.out.println("Connection could not be closed – SQLexception");
			return false;
		}
	}

	/**Get an array of all the courses on offer
	 * @return Array of course objects
	 */
	public Course[] getCourseList(){

		//Get the number of courses on offer
		int courseCount = getCourseCount();
		//Make a course array of that size
		Course[] courseList = new Course[courseCount];

		//Set statement to null
		Statement stmt = null;
		//Prepare SQL query
		String query = "SELECT name, courseID FROM Courses";

		try {
			//Create statement 
			stmt = connection.createStatement();
			//Create result set and execute query
			ResultSet rs = stmt.executeQuery(query);
			int index = 0;

			//Loop while there are more courses in the results set
			while (rs.next()) {

				//Get course name and id from results set
				int id = rs.getInt("courseID");
				String name = rs.getString("name");

				//Create course object and put it in array
				courseList[index] = new Course(id, name);
				//Increment index
				index++;
			}
			//Return the course array
			return courseList;
		}
		catch (SQLException e ) {
			//Could not get results, return an empty array
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return courseList;
		}
	}

	/**Get an array of all the members of the gym
	 * @return array of member objects
	 */
	public Member[] getMemberList(){

		//Get the number of members in the gym
		int memberCount = getMemberCount();
		//Create a member array of that size
		Member[] memberArray = new Member[memberCount];

		//Set statement to null
		Statement stmt = null;
		//Prepare SQL query
		String query = "SELECT fname, sname, memberID FROM Member";

		try {
			//Create statement 
			stmt = connection.createStatement();
			//Create result set and execute query
			ResultSet rs = stmt.executeQuery(query);
			int index = 0;

			//Loop while there are more members in the results set
			while (rs.next()) {

				//Get members first name, last name and id 
				String fname = rs.getString("fname");
				String sname = rs.getString("sname");
				int id = rs.getInt("memberID");

				//Create member object and add to array
				memberArray[index] = new Member(fname, sname, id);
				//Increment index
				index++;
			}

			//Return the member array
			return memberArray;
		}
		catch (SQLException e ) {
			//Could not get results, return an empty array
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return memberArray;
		}
	}

	/**Get the number of members in the gym
	 * @return Total member in the gym
	 */
	private int getMemberCount() {

		//Set statement to null
		Statement stmt = null;
		int count = 0;
		//Prepare SQL query
		String query = "SELECT COUNT(memberID) FROM Member";

		try {
			//Create statement object
			stmt = connection.createStatement();
			//Create result set and execute the query
			ResultSet rs = stmt.executeQuery(query);

			//There is an item in the results set
			while (rs.next()) {

				//Get the count of members from the result set
				count = rs.getInt(1);
			}

			//Return the count
			return count;
		}
		catch (SQLException e ) {
			//Could not get results, return 0
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return count;
		}

	}

	/**Get the number of courses on offer
	 * @return Total number of courses
	 */
	private int getCourseCount() {

		//Set statement to null
		Statement stmt = null;
		int count = 0;
		//Prepare SQL query
		String query = "SELECT COUNT(courseID) FROM Courses";

		try {
			//Create statement object
			stmt = connection.createStatement();
			//Create result set and execute the query
			ResultSet rs = stmt.executeQuery(query);

			//There is an item in the results set
			while (rs.next()) {

				//Get the count of members from the result set
				count = rs.getInt(1);
			}

			//Return the count
			return count;
		}
		catch (SQLException e ) {
			//Could not get results, return 0
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return count;
		}

	}

	/**Get the full details of a course
	 * @param Course object
	 */
	public void getCourseDetails(Course course) {

		//Get the course id from the course object
		int courseID = course.getID();
		//Get the number of people booked on the course
		int noBooked = getNoBooked(courseID);

		//Set statement to null
		Statement stmt = null;
		//Prepare SQL query
		String query = "SELECT day, time, cost, capacity, fname, sname "
				+ "FROM Courses "
				+ "INNER JOIN Instructor "
				+ "ON instructorID = instructor "
				+ "WHERE courseID='" + courseID + "'";
		try {
			//Create statement object
			stmt = connection.createStatement();
			//Create results set and execute theory
			ResultSet rs = stmt.executeQuery(query);

			//While there is results in the result set
			while (rs.next()) {

				//Get course details from the results set and set them in the course object
				String day = rs.getString("day");
				course.setDay(day);

				int time = rs.getInt("time");
				course.setTime(time);

				double cost = rs.getDouble("cost");
				course.setCost(cost);

				int capacity = rs.getInt("capacity");
				course.setCapacity(capacity);

				String instructor = rs.getString("fname") + " " + rs.getString("sname");
				course.setInstructor(instructor);

				course.setNoBooked(noBooked);
			}
		}
		catch (SQLException e ) {
			//Query was not successful
			e.printStackTrace();
			System.err.println("error executing query " + query);
		}
	}

	/**Get the number of places booked in a course
	 * @param courseID
	 * @return The number of places that have been booked
	 */
	public int getNoBooked(int courseID) {

		int id = courseID;
		int noBooked = 0;

		//Set statement to null
		Statement stmt = null;
		//Prepare SQL query
		String query = "SELECT COUNT(member) "
				+ "FROM MemberCourses "
				+ "WHERE course = '" + id + "'";

		try {
			//Create statement object
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			//While there is results in the result set
			while(rs.next()) {

				//Get the number of people booked on the course from the results set
				noBooked = rs.getInt(1);
			}

			//Return the number of people booked in the course
			return noBooked;
		}
		catch (SQLException e ) {
			//Query was not successful, return 0
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return noBooked;
		}
	}

	/**Get the details of the members booked on a course
	 * @param courseID
	 * @return Array of all the members booked on the course
	 */
	public Member[] getClassDetails(int courseID) {

		int id = courseID;
		//Create a Member array the size of the number of people booked on the course
		Member[] classList = new Member[getNoBooked(id)];
		int index = 0;

		//Set statement to null
		Statement stmt = null;
		//Prepare SQL query
		String query = "SELECT memberID, fname, sname "
				+ "FROM Member "
				+ "INNER JOIN MemberCourses "
				+ "ON memberID = member "
				+ "WHERE course = '" + id + "'";

		try {
			//Create statement object
			stmt = connection.createStatement();
			//Create results set and execute the query 
			ResultSet rs = stmt.executeQuery(query);

			//Loop while there is more results in the results set
			while(rs.next()) {

				//Get the members first name, last name and id from the results set
				String fname = rs.getString("fname");
				String sname = rs.getString("sname");
				int memberID = rs.getInt("memberID");

				//Create a member object and add to the Member array
				classList[index] = new Member(fname, sname, memberID);
				index ++;
			}

			//Return array of all the members booked on the course
			return classList;
		}
		catch (SQLException e ) {
			//Query was not successful, return an empty array
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return classList;
		}
	}

	/**Book a member on a course
	 * @param memberID
	 * @param courseID
	 * @return Whether the booking has been successful
	 */
	public boolean bookMemberOnCourse(int memberID, int courseID) {

		//Set statement to null
		Statement stmt = null;
		//Prepare SQL query
		String query = "INSERT INTO MemberCourses(member, course) Values (" + memberID + "," + courseID + ")";

		try {
			//Create statement object
			stmt = connection.createStatement();
			//Execute update and get the number of rows that were effected
			int rowsAffected = stmt.executeUpdate(query);

			//If any rows were affected the query was successful, return true
			if (rowsAffected > 0) {

				return true;
			}
			//No rows were affected, return false
			else {

				return false;
			}
		}
		catch (SQLException e ) {
			//Exception was thrown, return false
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return false;
		}
	}

	/**Get the total number of booking that have been made across all courses
	 * @return total number of bookings
	 */
	public int getTotalBookings() {

		int totalBookings = 0;
		//Set statement to null
		Statement stmt = null;
		//Prepare SQL query
		String query = "SELECT COUNT(booking_number) FROM MemberCourses";


		try {
			//Create statement object
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			//While there is results in the result set
			while(rs.next()) {

				//Get the number of people booked on courses
				totalBookings = rs.getInt(1);
			}

			//Return the total number of bookings
			return totalBookings;
		}
		catch (SQLException e ) {
			//Query was not successful, return 0
			e.printStackTrace();
			System.err.println("error executing query " + query);
			return totalBookings;
		}
	}
}
