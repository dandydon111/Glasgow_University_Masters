/**
 * The main GUI. View/controller
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener{

	//class for database interactions
	private DB db;

	//Array of all members
	private Member[] memberArray;
	//Names of all the courses
	private Course[] courseList;

	//Layout components
	private JLabel course, member;
	private JButton book, details, showClass;
	private JComboBox courseBox, memberBox;
	private JPanel flowLayoutTop, flowLayoutMiddle, flowLayoutBottom;

	public GUI(DB db) {

		//Assign instance variable
		this.db = db;

		//set up GUI
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Booking system");
		this.setSize(400,200);
		this.setLayout(new GridLayout(3,1));
		this.setLayoutComponents();
		this.updateData();
	}

	/**
	 * Set the layout components of the JFrame
	 */
	private void setLayoutComponents(){

		//Set up course drop down
		course = new JLabel("Course: ");
		courseBox = new JComboBox();
		//Set up buttons and add action listeners
		details = new JButton("Details");
		details.addActionListener(this);
		showClass = new JButton("Class");
		showClass.addActionListener(this);
		//Populate top panel with course components
		flowLayoutTop = new JPanel();
		flowLayoutTop.add(course);
		flowLayoutTop.add(courseBox);
		flowLayoutTop.add(details);
		flowLayoutTop.add(showClass);

		//Set up member drop down
		member = new JLabel("member: ");
		memberBox = new JComboBox();
		//populate middle panel with member components
		flowLayoutMiddle = new JPanel();
		flowLayoutMiddle.add(member);
		flowLayoutMiddle.add(memberBox);

		//Set up the booking button and add action listeners
		book = new JButton("Book");
		book.addActionListener(this);
		//populate bottom panel with the button
		flowLayoutBottom = new JPanel();
		flowLayoutBottom.add(book);

		//Add the panels to the JFrame
		this.add(flowLayoutTop);
		this.add(flowLayoutMiddle);
		this.add(flowLayoutBottom);
	}

	/**
	 * Display all relevant data in the JFrame
	 */
	private void updateData() {

		//connect to the database
		if (db.connect()) {

			//Get array of all the courses in the system
			courseList = db.getCourseList();

			//populate course drop down
			for (int i = 0; i < courseList.length; i++) {

				//fill the JCombo box with available courses
				courseBox.addItem(courseList[i].getName());
			}

			//Get array of all members in the system
			memberArray = db.getMemberList();

			//Populate members drop down
			for (int i = 0; i < memberArray.length; i++) {

				//Get member object from member array
				Member member = memberArray[i];
				//Create name string from first and last name
				String memberName = member.getFName() +  " " + member.getSName();

				//Fill the JCombo box with the members names
				memberBox.addItem(memberName);
			}

			//close the database connection
			db.close();
		}
		else {
			//display error message if connection could not be made
			JOptionPane.showMessageDialog(this, "Could not connect to the database. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Action listeners
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == details) {

			//The details button has been pressed
			setCourseDetails();
		}
		else if (e.getSource() == showClass) {

			//The show class button has been pressed
			setClassDetails();
		}
		else if (e.getSource() == book) {

			//The book button has been pressed
			bookCourse();
		}
	}

	/**
	 * Create a pop up with the details of the selected course
	 */
	private void setCourseDetails() {

		//Connect to the database
		if (db.connect()) {

			//Get the selected course from the JCombo box
			Course course = courseList[courseBox.getSelectedIndex()];

			//Populate the course object with the course details
			db.getCourseDetails(course);
			//Close the database connection
			db.close();

			//Create the course details pop up and set it to visible
			CourseDetails courseDetails = new CourseDetails(course);
			courseDetails.setVisible(true);
		}
		else {

			//display error message if connection could not be made
			JOptionPane.showMessageDialog(this, "Could not connect to the database. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Create a pop up of all the members booked onto the selected course
	 */
	private void setClassDetails() {

		//Connect to the database
		if(db.connect()) {

			//Get the selected course id from the JCombo box 
			int courseID = courseList[courseBox.getSelectedIndex()].getID();

			//Get an array of all the members booked on the selected course
			Member[] classDetails = db.getClassDetails(courseID);
			//Close the database connection
			db.close();

			//Create the members list pop up and set it to visible
			CourseMembers courseMembers = new CourseMembers(classDetails);
			courseMembers.setVisible(true);
		}
		else {

			//display error message if connection could not be made
			JOptionPane.showMessageDialog(this, "Could not connect to the database. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Book a member onto a course
	 */
	private void bookCourse() {

		//Connect to the database
		if(db.connect()) {

			//Get the selected course id from the JCombo box 
			int courseID = courseList[courseBox.getSelectedIndex()].getID();
			//Get the selected member from the JCombo box
			int memberID = memberArray[memberBox.getSelectedIndex()].getID();

			//Get an array of all the members booked on the selected course
			Member[] classDetails = db.getClassDetails(courseID);
			//Get the selected course from the JCombo box
			Course course = courseList[courseBox.getSelectedIndex()];

			//Get the capacity of the selected course 
			int capacity = course.getCapacity();
			//Get the spaces filled on the selected course
			int noBooked = db.getNoBooked(course.getID());

			//Boolean for looping
			boolean match = false;

			//Loop through the class list
			for (int i = 0; i < classDetails.length; i++) {

				//Get the id of each member booked on the class
				int id = classDetails[i].getID();
				//Member is already booked on the course
				if (memberID == id) {
					//Set boolean to true
					match = true;
				}
			}
			
			int totalBookings = db.getTotalBookings();

			//Member is already booked on the course
			if (match) {
				//Show error message
				JOptionPane.showMessageDialog(this, "That member is already booked on the course", "Error", JOptionPane.ERROR_MESSAGE);
			}
			//The course is full
			else if (noBooked >= capacity) {
				//Show error message
				JOptionPane.showMessageDialog(this, "Sorry that course is full", "Error", JOptionPane.ERROR_MESSAGE);
			}
			//The maximum number of 40 bookings has been exceeded
			else if (totalBookings >= 40) {
				//show error message
				JOptionPane.showMessageDialog(this, "Sorry the maximum number of bookings has been exceeded", "Error", JOptionPane.ERROR_MESSAGE);
			}
			//Book the member onto the course
			else if (db.bookMemberOnCourse(memberID, courseID)) {
				//Show confirmation
				JOptionPane.showMessageDialog(this, "Booking Successful", "Success", JOptionPane.PLAIN_MESSAGE);
			}
			//Member booking was not successful
			else {
				
				JOptionPane.showMessageDialog(this, "Member could not be booked on course", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			//Close the database connection
			db.close();
		}
		else {

			//display error message if connection could not be made
			JOptionPane.showMessageDialog(this, "Could not connect to the database. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
