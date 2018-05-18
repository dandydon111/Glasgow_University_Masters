import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of a FitnessProgram object
 * and contains buttons enabling access to the required functionality.
 */
public class SportsCentreGUI extends JFrame implements ActionListener {

	/** The program of scheduled classes */
	private FitnessProgram fitnessProgram;

	/** GUI JButtons */
	private JButton closeButton, attendanceButton;
	private JButton addButton, deleteButton;

	/** GUI JTextFields */
	private JTextField idIn, classIn, tutorIn;

	/** Display of class timetable */
	private JTextArea display;

	/** Display of attendance information */
	private ReportFrame report;

	/** Names of input text files */
	private final String classesInFile = "ClassesIn.txt";
	private final String classesOutFile = "ClassesOut.txt";
	private final String attendancesFile = "AttendancesIn.txt";

	/**
	 * Constructor for AssEx3GUI class
	 */
	public SportsCentreGUI() {

		//Create the fitness program object
		fitnessProgram = new FitnessProgram();

		//Set up the JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Boyd-Orr Sports Centre");
		setSize(700, 300);
		display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		add(display, BorderLayout.CENTER);
		layoutTop();
		layoutBottom();

		//Get the information from the input text files
		initLadiesDay();
		initAttendances();
		
		//Display the updated timetable in the text area
		updateDisplay();
	}

	/**
	 * Creates the FitnessProgram list ordered by start time
	 * using data from the file ClassesIn.txt
	 */
	public void initLadiesDay() {

		//Initialise scanner and reader to null
		FileReader reader = null;
		Scanner scanner = null;

		try {

			try {

				//Set up reader and scanner
				reader = new FileReader(classesInFile);
				scanner = new Scanner(reader);

				//Scan all lines in the file
				while (scanner.hasNextLine()) {

					//Get a complete line from the file
					String data = scanner.nextLine();

					//Create a FitnessClass object using the line from the text file
					FitnessClass fitnessClass = new FitnessClass(data);

					//Add the class. If the class cannot be added to the programme then there is a scheduling error
					if (!fitnessProgram.addClass(fitnessClass)) {

						//Show error message
						JOptionPane.showMessageDialog(this, fitnessClass.getName() + " could not be added because of a scheduling conflict", "Error", JOptionPane.ERROR_MESSAGE);
					}
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

		//Catch exceptions and show relevant error messages
		catch (IOException e) {

			//File loading error
			JOptionPane.showMessageDialog(this, "Error loading file", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (InputMismatchException e) {

			//File reading error
			JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initialises the attendances using data
	 * from the file AttendancesIn.txt
	 */
	public void initAttendances() {

		//Initialise scanner and reader to null
		FileReader reader = null;
		Scanner scanner = null;

		try {

			try {

				//Set up reader and scanner
				reader = new FileReader(attendancesFile);
				scanner = new Scanner(reader);

				//Scan all lines in the file
				while (scanner.hasNextLine()) {

					//Get a complete line from the file
					String data = scanner.nextLine();
					//Populate the classes with the attendance data from the file
					fitnessProgram.populateAttendance(data);
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
		//Catch exceptions and show relevant error messages
		catch (IOException e) {

			//File loading error
			JOptionPane.showMessageDialog(this, "Error loading file", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (InputMismatchException e) {

			//File reading error
			JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Instantiates timetable display and adds it to GUI
	 */
	public void updateDisplay() {

		//Update the timetable
		display.setText(fitnessProgram.getTimetableString());
	}

	/**
	 * adds buttons to top of GUI
	 */
	public void layoutTop() {
		
		//Add buttons and action listeners
		JPanel top = new JPanel();
		closeButton = new JButton("Save and Exit");
		closeButton.addActionListener(this);
		top.add(closeButton);
		attendanceButton = new JButton("View Attendances");
		attendanceButton.addActionListener(this);
		top.add(attendanceButton);
		add(top, BorderLayout.NORTH);
	}

	/**
	 * adds labels, text fields and buttons to bottom of GUI
	 */
	public void layoutBottom() {
		
		// instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(3, 3));

		// add upper label, text field and button
		JLabel idLabel = new JLabel("Enter Class Id");
		bottom.add(idLabel);
		idIn = new JTextField();
		bottom.add(idIn);
		JPanel panel1 = new JPanel();
		addButton = new JButton("Add");
		addButton.addActionListener(this);
		panel1.add(addButton);
		bottom.add(panel1);

		// add middle label, text field and button
		JLabel nmeLabel = new JLabel("Enter Class Name");
		bottom.add(nmeLabel);
		classIn = new JTextField();
		bottom.add(classIn);
		JPanel panel2 = new JPanel();
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		panel2.add(deleteButton);
		bottom.add(panel2);

		// add lower label text field and button
		JLabel tutLabel = new JLabel("Enter Tutor Name");
		bottom.add(tutLabel);
		tutorIn = new JTextField();
		bottom.add(tutorIn);
		
		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Processes adding a class
	 */
	public void processAdding() {

		//Get user inputs from text fields
		String id = idIn.getText();
		String name = classIn.getText();
		String tutor = tutorIn.getText();

		//Validate user inputs
		//The id fields is empty 
		if (id.equals("")) {

			//Show error message
			JOptionPane.showMessageDialog(this, "Please enter the class id", "Error", JOptionPane.ERROR_MESSAGE);
		}
		//The name field is empty
		else if (name.equals("")) {

			//Show error message
			JOptionPane.showMessageDialog(this, "Please enter the class name", "Error", JOptionPane.ERROR_MESSAGE);
		}
		//The tutor field is empty
		else if (tutor.equals("")) {

			//Show error message
			JOptionPane.showMessageDialog(this, "Please enter the tutor name", "Error", JOptionPane.ERROR_MESSAGE);
		}
		//The course name is too long
		else if (name.length() > 10) {
			
			//Show error message 
			JOptionPane.showMessageDialog(this, "The course name must not be more than 10 characters", "Error", JOptionPane.ERROR_MESSAGE);
		}
		//The tutor name is too long
		else if (tutor.length() > 10) {
			
			//Show error message
			JOptionPane.showMessageDialog(this, "The tutor name must not be more than 10 characters", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {

			//Inputs are valid, create a fitness class object
			FitnessClass fitnessClass = new FitnessClass(id, name, tutor);

			//There is already a class of that id booked
			if (fitnessProgram.getClassByID(id) != null) {

				//Show error message
				JOptionPane.showMessageDialog(this, "A class with that id already exists", "Error", JOptionPane.ERROR_MESSAGE);
			}
			//Add the class to the program. If the add operation is successful, update the display
			else if (fitnessProgram.addClass(fitnessClass)) {

				//Update display
				updateDisplay();
				//clear the text fields
				clearInputs();
			}
			//Class could not be added. Timetable must be full
			else {

				//Show error message
				JOptionPane.showMessageDialog(this, "The class timetable is full", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Processes deleting a class
	 */
	public void processDeletion() {

		//Get user inputs from text fields
		String id = idIn.getText();

		//The id field is empty
		if (id.equals("")) {

			//Show error message
			JOptionPane.showMessageDialog(this, "Please enter the class id", "Error", JOptionPane.ERROR_MESSAGE);
		}
		//Inputs are valid
		else {

			//There is no class scheduled with that id
			if (fitnessProgram.getClassByID(id) == null) {

				//Show error message
				JOptionPane.showMessageDialog(this, "There is no class scheduled with that id", "Error", JOptionPane.ERROR_MESSAGE);
			}
			//Delete the file. If the delete operation is successful, update the display
			else if (fitnessProgram.deleteClass(id)) {

				//Update display
				updateDisplay();
				//Clear the text fields
				clearInputs();
			} 
			//Class could not be deleted
			else {

				//Show error message
				JOptionPane.showMessageDialog(this, "The delete operation was not successful", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Instantiates a new window and displays the attendance report
	 */
	public void displayReport() {

		//Create a the ReportFrame object and pass it the fitness program
		report = new ReportFrame(fitnessProgram);
		//Make the frame visible
		report.setVisible(true);
	}

	/**
	 * Writes lines to file representing class name, 
	 * tutor and start time and then exits from the program
	 */
	public void processSaveAndClose() {

		//Initialise the PrintWriter to null
		PrintWriter writer = null;

		try {

			//Set up the PrintWriter
			writer = new PrintWriter(classesOutFile);
			//Write the data to the file
			writer.println(fitnessProgram.getOutputFileString());
		} 
		//Catch exception
		catch (FileNotFoundException e) {
			
			//Show error message
			JOptionPane.showMessageDialog(this, "The file could not be found", "Error", JOptionPane.ERROR_MESSAGE);
		}
		finally {

			//Close the writer if it has been opened
			if (writer != null) {

				//Close the writer
				writer.close();
			}
		}
		
		//Exit the program
		System.exit(0);
		
	}
	
	/**
	 * Clears the input text fields
	 */
	private void clearInputs(){
		
		//Clear all text fields
		idIn.setText("");
		classIn.setText("");
		tutorIn.setText("");
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {

		//The add button has been pressed
		if (ae.getSource() == addButton) {

			//Process adding
			processAdding();
		}
		//The delete button has been pressed
		else if (ae.getSource() == deleteButton) {

			//Process deletion
			processDeletion();
		}
		//The view attendances button has been pressed
		else if (ae.getSource() == attendanceButton) {

			//Create and display the attendance report
			displayReport();
		}
		//The save and exit button has been pressed
		else if (ae.getSource() == closeButton) {

			//Write to the output file and close the program
			processSaveAndClose();
		}
	}
}