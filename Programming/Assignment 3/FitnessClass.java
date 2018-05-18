
/** Defines an object representing a single fitness class
 */
public class FitnessClass implements Comparable<FitnessClass> {

	//Instance variables
	private final int ATTENDANCE_PERIOD = 5;
	private int startTime;
	private int weekOneAttendance, weekTwoAttendance, weekThreeAttendance, weekFourAttendance, weekFiveAttendance;
	private String ID;
	private String name;
	private String tutor;

	/**
	 * Default constructor
	 */
	public FitnessClass(){

	}

	/**
	 * Constructor for classes added by the input file
	 * @param classData
	 */
	public FitnessClass(String classData) {

		//Split the line of data into tokens 
		String[] tokens = classData.split("\\s+");

		//Save tokens to relevant instance variables
		this.ID = tokens[0];
		this.name = tokens[1];
		this.tutor = tokens[2];
		this.startTime = Integer.parseInt(tokens[3]);
	}
	
	/**
	 * Constructor for classes added by the user
	 * @param ID
	 * @param name
	 * @param tutor
	 */
	public FitnessClass(String ID, String name, String tutor) {
		
		//Assign values to the instance variables
		this.ID = ID;
		this.name = name;
		this.tutor = tutor;
		
		//New classes do not have attendance records
		weekOneAttendance = 0;
		weekTwoAttendance = 0;
		weekThreeAttendance = 0;
		weekFourAttendance = 0;
		weekFiveAttendance = 0;
	}

	/**
	 * Method to get the number of weeks the attendance record is over
	 * @return number of weeks
	 */
	public int getAttendancePeriod() {

		return ATTENDANCE_PERIOD;
	}

	/**
	 * Method to get the start time of the class
	 * @return start time
	 */
	public int getStartTime() {

		return startTime;
	}

	/**
	 * Method to get the first week attendance figure
	 * @return first week attendance
	 */
	public int getWeekOneattendance() {

		return weekOneAttendance;
	}

	/**
	 * Method to get the second week attendance figure
	 * @return second week attendance
	 */
	public int getWeekTwoAttendance() {

		return weekTwoAttendance;
	}

	/**
	 * Method to get the third week attendance figure
	 * @return third week attendance
	 */
	public int getWeekThreeAttendance() {

		return weekThreeAttendance;
	}

	/**
	 * Method to get the fourth week attendance figure
	 * @return week four attendance
	 */
	public int getWeekFourAttendance() {

		return weekFourAttendance;
	}

	/**
	 * Method to get the fifth week attendance figure
	 * @return week five attendance
	 */
	public int getWeekFiveAttendance() {

		return weekFiveAttendance;
	}

	/**
	 * Method to get the course id
	 * @return course id
	 */
	public String getID() {

		return ID;
	}
	
	/**
	 * Method to get the name of the course
	 * @return course name
	 */
	public String getName() {
		
		return name;
	}

	/**
	 * Method to get the name of the course tutor
	 * @return course tutor
	 */
	public String getTutor() {

		return tutor;
	}

	/**
	 * Method to the average attendance for the course across the whole time period
	 * @return average attendance
	 */
	public double getAverageAttendance() {

		//Calculate the average attendance from the time period and cast it to a double. return the double
		return (double)(weekOneAttendance+weekTwoAttendance+weekThreeAttendance+weekFourAttendance+weekFiveAttendance)/5;
	}

	/**
	 * Method to get a string containing attendance data
	 * The string is formatted in the correct way for output to the report frame
	 * @return attendance record string
	 */
	public String getAttendanceRecord() {

		//Create a string in the correct format for the attendance report. Contains all attendance data
		String attendanceRecord = String.format("%-5s %-10s %-10s %-5d %-5d %-5d %-5d %-10d %-5.2f %n", 
				ID, name, tutor, weekOneAttendance, weekTwoAttendance, weekThreeAttendance, weekFourAttendance, weekFiveAttendance, getAverageAttendance());
		//Return the string
		return attendanceRecord;
	}
	
	/**
	 * Method to get a string containing the course information
	 * The string is formatted in the correct way for output the ClassesOut text file
	 * @return output file line
	 */
	public String getOutputFileLine() {
		
		//Return the course information string and format it for output to the text file
		String output = String.format("%s %s %s %d %n", ID, name, tutor, startTime);
		return output;
	}

	/**
	 * Method to set the course attendance for the first week
	 * @param first week attendance
	 */
	public void setWeekOneAttendance(int attendance) {

		weekOneAttendance = attendance;
	}

	/**
	 * Method to set the course attendance for the second week
	 * @param second week attendance
	 */
	public void setWeekTwoAttendance(int attendance) {

		weekTwoAttendance = attendance;
	}

	/**
	 * Method to set the course attendance for the third week
	 * @param third week attendance
	 */
	public void setWeekThreeAttendance(int attendance) {

		weekThreeAttendance = attendance;
	}

	/**
	 * Method to set the course attendance for the fourth week
	 * @param fourth week attendance
	 */
	public void setWeekFourAttendance(int attendance) {

		weekFourAttendance = attendance;
	}

	/**
	 * Method to set the course attendance for fifth week
	 * @param fifth week attendance
	 */
	public void setWeekFiveAttendance(int attendance) {

		weekFiveAttendance = attendance;
	}

	/**
	 * Method to set the course id
	 * @param course id
	 */
	public void setID(String ID) {

		this.ID = ID;
	}

	/**
	 * Method to set the course start time
	 * @param course start time
	 */
	public void setStartTime(int startTime) {

		this.startTime = startTime;
	}

	/**
	 * Method to set the course tutor name
	 * @param course tutor
	 */
	public void setTutor(String tutor) {

		this.tutor = tutor;
	}
	
	/**
	 * Method to compare fitness classes
	 * @param other fitness class
	 * @return true if the id's match, false if they do not
	 */
	public boolean equals(FitnessClass other) {
		
		//The id's of the two courses match
		if (this.ID.equals(other.getID())) {
			
			//Return true
			return true;
		}
		//The id's do not match
		else {
			
			//Return false
			return false;
		}
	}

	/**
	 * Method to compare fitness classes
	 * Used for sorting a fitness class array
	 * Classes are compared based upon average attendance
	 */
	public int compareTo(FitnessClass other) {
		
		//The average attendance is less than the other fitness class
		if(this.getAverageAttendance() < other.getAverageAttendance()){
			
			return 1;
		}
		//The average attendance is the same as the other class
		else if(this.getAverageAttendance() == other.getAverageAttendance()) {
			
			return 0;
		}
		//The average attendance is more than the other class
		else {
			
			return -1;
		}
	}
}

