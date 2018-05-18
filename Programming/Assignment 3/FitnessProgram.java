import java.util.*;

/**
 * Maintains a list of Fitness Class objects
 * The list is initialised in order of start time
 * The methods allow objects to be added and deleted from the list
 * In addition an array can be returned in order of average attendance
 */
public class FitnessProgram {

	//Instance variables
	private final int MAX_CLASSES = 7;
	private FitnessClass[] classList;
	private int classCount = 0;

	//Default constructor
	public FitnessProgram() {

		//Instantiate empty array
		classList = new FitnessClass[MAX_CLASSES];
	}

	/**
	 * Adds a class to the schedule
	 * @param fitnessClass
	 * @return true if the class was added, false if the class could not be added
	 */
	public boolean addClass(FitnessClass fitnessClass) {

		//Get the start time of the class and use it to calculate the array index
		int startTime = fitnessClass.getStartTime();
		int index = startTime - 9;

		//If a start time has not been set, add to the first available slot in the timetable
		if(startTime == 0) {

			//Loop through array
			for (int i = 0; i < classList.length; i++) {

				//Find first free slot
				if (classList[i] == null) {

					//Add the class to the classList and increment the class counter
					classList[i] = fitnessClass;
					classCount++;

					//Set the startTime class variable and return true
					fitnessClass.setStartTime(i+9);
					return true;
				}
			}
		}
		//A start time has been set
		//If the index position is null there is no class currently booked at that time
		else if (classList[index] == null) {

			//Add the class, increment the count and return true
			classList[index] = fitnessClass;
			classCount++;
			return true;
		}

		//Adding operation was not successful, return false
		return false;
	}

	/**
	 * Deletes a class from the schedule
	 * @param fitnessClass
	 * @return true if the class has been deleted, false if the class could not be found
	 */
	public boolean deleteClass(String ID) {

		//Loop through classList to search for ID
		for (int i = 0; i < classList.length; i++) {

			//If there is a class at that index position and the ID matches
			if ((classList[i] != null) && classList[i].getID().equals(ID)) {

				//Delete the class 
				classList[i] = null;
				//Decrease the class count
				classCount --;
				//Return true
				return true;

			}
		}

		//There is no class to delete
		return false;
	}

	/**
	 * Set the attendance of classes from the data in the input text file
	 * @param A complete line from AttendanceIn.txt
	 */
	public void populateAttendance(String attendanceData) {

		//Split the line of data into tokens 
		String[] tokens = attendanceData.split("\\s+");
		Arrays.deepToString(tokens);

		//Save tokens to relevant variables
		String id = tokens[0];
		int weekOne = Integer.parseInt(tokens[1]);
		int weekTwo = Integer.parseInt(tokens[2]);
		int weekThree = Integer.parseInt(tokens[3]);
		int weekFour = Integer.parseInt(tokens[4]);
		int weekFive = Integer.parseInt(tokens[5]);

		//Loop through array to find the class which the data refers to
		for (int i = 0; i < classList.length; i++) {

			//If there is a class at that index position and the id matches
			if ((classList[i] != null) && classList[i].getID().equals(id)) {

				//Set the attendance data for the class
				classList[i].setWeekOneAttendance(weekOne);
				classList[i].setWeekTwoAttendance(weekTwo);
				classList[i].setWeekThreeAttendance(weekThree);
				classList[i].setWeekFourAttendance(weekFour);
				classList[i].setWeekFiveAttendance(weekFive);
				break;
			}
		}
	}

	/**
	 * Return an array of FitnessClasses that is ordered by average attendance
	 * @return sorted array
	 */
	private FitnessClass[] getSortedArray() {

		//Create a FitnessClass array to be sorted. The array is the size of the number of classes
		FitnessClass[] sortedArray = new FitnessClass[classCount];
		//Counter for indexing
		int counter = 0;

		//Loop through the list of classes
		for (int i = 0; i < classList.length; i++) {

			//Add all scheduled classes to the new array
			if (classList[i] != null) {

				//Add to array and increment counter
				sortedArray[counter] = classList[i];
				counter++;
			}
		}

		//Sort the new array
		Arrays.sort(sortedArray);

		//Return the sorted array
		return sortedArray;

	}

	/**
	 * Method to get a formatted timetable string containing all the scheduled classes
	 * @return Formatted timetable string
	 */
	public String getTimetableString() {

		//Arrays to save names and tutors of the scheduled classes
		String[] nameArray = new String[MAX_CLASSES];
		String[] tutorArray = new String[MAX_CLASSES];

		//Loop through the fitness classes to fill the names/tutors arrays
		for (int i = 0; i < classList.length; i++) {

			//There is no class in the time slot
			if (classList[i] == null) {

				//Assign strings to slots which don't have classes
				nameArray[i] = "Available";
				tutorArray[i] = "";
			}
			//There is a class scheduled
			else {

				//Get the name and tutor of the scheduled classes
				nameArray[i] = classList[i].getName();
				tutorArray[i] = classList[i].getTutor();
			}
		}

		//Format each line of the timetable
		String header = String.format("%5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %n", "", "9-10", "10-11", "11-12", "12-13", "13-14", "14-15", "15-16");
		String classNames = String.format("%5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %n", "", nameArray[0], nameArray[1], nameArray[2], nameArray[3], nameArray[4], nameArray[5], nameArray[6]);
		String classTutors = String.format("%5s %-10s %-10s %-10s %-10s %-10s %-10s %-10s", "", tutorArray[0], tutorArray[1], tutorArray[2], tutorArray[3], tutorArray[4], tutorArray[5], tutorArray[6]);

		//Return the combined string
		return header + classNames + classTutors;
	}

	/**
	 * Method to return the FitnessClass in a specified index position
	 * @param index position
	 * @return FitnessClass
	 */
	public FitnessClass getClassByIndex(int index) {

		//Check that there is a class at the index position
		if (classList[index] != null) {

			//Return the scheduled class
			return classList[index];
		}
		//There is no class at the index position
		else {

			//Return null
			return null;
		}
	}

	/**
	 * Method to return the FitnessClass at a specified start time
	 * @param startTime
	 * @return FitnessClass
	 */
	public FitnessClass getClassByTime(int startTime) {

		//Check that there is a class scheduled at the specified time
		if (classList[startTime - 9] != null) {

			//Return the scheduled class
			return classList[startTime - 9];
		}
		//There is no class schedules
		else {

			//Return null
			return null;
		}
	}

	/**
	 * Method to get a class by by its id
	 * @param ID
	 * @return The fitness class with the specified id, null if there is no fitness class with that id
	 */
	public FitnessClass getClassByID(String ID) {

		//Loop through classList to search for the ID
		for (int i = 0; i < classList.length; i++) {

			//If the index position is not null and the ID matches
			if ((classList[i] != null) && classList[i].getID().equals(ID)) {

				//Return the class that matches
				return classList[i];
			}
		}

		//Return null if there is not a match
		return null;
	}

	/**
	 * Method to return the start time of the first scheduled class
	 * @return The first start time
	 */
	public int getFirstStartTime() {

		//Loop through classList to get the earliest class
		for (int i = 0; i < classList.length; i++) {

			//Earliest class will be first in list that is not null
			if (classList[i] != null) {

				//Return the start time
				int startTime = i + 9;
				return startTime;
			}
		}

		//Return 0 if there are no classes scheduled
		return 0;
	}

	/**
	 * Method to return the average attendance of all the scheduled fitness classes
	 * @return The average attendance
	 */
	private double getAverageAttendance() {

		//If there are classes booked, calculate the overall average attendance
		if (classCount > 0) {

			//Sum of the individual average attendances
			double total = 0;

			//Loop through array
			for (int i = 0; i < classList.length; i++) {

				//If the element is not null there is a fitness class
				if (classList[i] != null) {

					//Add the class average to the total
					total += classList[i].getAverageAttendance();
				}
			}

			//return the overall average attendance
			return total/classCount;
		}
		else {

			//There are no classes. Return 0
			return 0;
		}
	}

	/**
	 * Method to get a formatted string to print to the output file
	 * String contains information for each of the scheduled classes
	 * @return formatted string
	 */
	public String getOutputFileString() {

		//The complete output string to be written to the file
		String output = "";

		//Loop through all the array positions
		for (int i = 0; i < classList.length; i++) {

			//Get the output string for each of the scheduled classes
			if (classList[i] != null) {

				//Add the line to the overall output string
				output += classList[i].getOutputFileLine();
			}
		}

		//Return the output string
		return output;
	}

	public String getAttendanceReport () {

		//Get an array of FitnessClass objects sorted in order of increasing average attendance
		FitnessClass[] sortedArray = getSortedArray();

		//Format the header for the attendance report
		String header = String.format("%-5s %-10s %-10s %-30s %-5s %n", "ID", "Name", "Tutor", "Attendances", "Average Attendance");
		//The final attendance report string. Initialised as empty
		String report = "";

		//Loop through the sorted array
		for (int i = 0; i < sortedArray.length; i++) {

			//Add the attendance record for each class to the report string
			report += sortedArray[i].getAttendanceRecord();
		}

		//Format a string to display the overall average attendance
		String average = String.format("%n Overall average: %.2f", getAverageAttendance());

		//Return the concatenated attendance report
		return header + report + average;
	}
	
	/**
	 * Method to return the number of classes currently scheduled
	 * @return the number of classes
	 */
	public int getClassCount() {

		return classCount;
	}
	
	/**
	 * Method to get the total number of classes that can be scheduled in the day
	 * @return maximum number of classes
	 */
	public int getMaxClasses(){

		return MAX_CLASSES;
	}
	
	/**
	 * Method to return an array of all the scheduled classes
	 * The time each class is scheduled is index position + 9
	 * @return class list
	 */
	public FitnessClass[] getClassList() {
		
		return classList;
	}
}