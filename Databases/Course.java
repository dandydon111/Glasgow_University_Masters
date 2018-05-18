/**
 * Class to store the details of a gym course
 * @author 2031944H
 *
 */
public class Course {

	//Instance variables
	private String name;
	private int id;
	private String day;
	private int time;
	private double cost;
	private int capacity;
	private String instructor;
	private int noBooked;

	public Course(int id, String name){

		//Set instance variables
		this.id = id;
		this.name = name;
	}

	/**
	 * Method to set the day which the course is scheduled
	 * @param day of course
	 */
	public void setDay(String day) {

		this.day = day;
	}

	/**
	 * Method to set the time which the course is scheduled
	 * @param time of course
	 */
	public void setTime(int time) {

		this.time = time;
	}

	/**
	 * Method to set the cost of the course
	 * @param cost of course
	 */
	public void setCost(double cost) {

		this.cost = cost;
	}

	/**
	 * Method to set the capacity of the course
	 * @param course capacity
	 */
	public void setCapacity(int capacity) {

		this.capacity = capacity;
	}

	/**
	 * Method to set the name of the course instructor
	 * @param instructor Instructor's full name
	 */
	public void setInstructor(String instructor) {

		this.instructor = instructor;
	}

	/**
	 * Method to set the number of people already booked onto the course
	 * @param the number of people booked on the course
	 */
	public void setNoBooked(int noBooked) {

		this.noBooked = noBooked;
	}

	/**
	 * Method to get the course ID
	 * @return course ID
	 */
	public int getID() {

		return id;
	}

	/**
	 * Method to get the name of the course 
	 * @return course name
	 */
	public String getName() {

		return name;
	}

	/**
	 * Method to get the day the course is scheduled
	 * @return day of course
	 */
	public String getDay() {

		return day;
	}

	/**
	 * Method to get the time the course is scheduled
	 * @return time of course
	 */
	public int getTime() {

		return time;
	}

	/**
	 * Method to get the cost of the course
	 * @return cost of course
	 */
	public double getCost() {

		return cost;
	}

	/**
	 * Method to get the capacity of the course
	 * @return course capacity
	 */
	public int getCapacity() {

		return capacity;
	}

	/**
	 * Method to get the name of the instructor
	 * @return Instructor full name
	 */
	public String getInstructor() {

		return instructor;
	}

	/**
	 * Method to get the number of people already booked on the course
	 * @return number of people booked on the course
	 */
	public int getNoBooked() {

		return noBooked;
	}

}
