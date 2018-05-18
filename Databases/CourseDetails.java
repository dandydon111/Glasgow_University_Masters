/**
 * Frame to display course details of the selected course
 */

import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class CourseDetails extends JFrame {
	
	//Instance variables are JFrame components 
	private JLabel nameLabel, instructorLabel, capacityLabel, noBookedLabel, dayLabel, timeLabel, costLabel;
	private JTextField nameField, instructorField, capacityField, noBookedField, dayField, timeField, costField;
	private JPanel namePanel, instructorPanel, capacityPanel, noBookedPanel, dayPanel, timePanel, costPanel;
	
	//The course that is being displayed
	private Course course;
	
	public CourseDetails(Course course) {
		
		//Assign instance variable
		this.course = course;
		
		//Set up JFrame
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setTitle("Course Details");
		this.setSize(300,300);
		this.setLayout(new GridLayout(7,1));
		this.setLayoutComponents();
		this.fillData();
	}
	
	/**
	 * Set the layout components
	 */
	private void setLayoutComponents() {
		
		//Set up name components
		nameLabel = new JLabel ("Course name: ");
		nameField = new JTextField();
		nameField.setEditable(false); 
		namePanel = new JPanel();
		//add components to panel
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		//Add panel to the JFrame
		this.add(namePanel);
		
		//Set up instructor components
		instructorLabel = new JLabel ("Instructor: ");
		instructorField = new JTextField();
		instructorField.setEditable(false);
		instructorPanel = new JPanel();
		//Add components to panel
		instructorPanel.add(instructorLabel);
		instructorPanel.add(instructorField);
		//Add panel to the JFrame
		this.add(instructorPanel);
		
		//Set up capacity components
		capacityLabel = new JLabel ("Capacity: ");
		capacityField = new JTextField();
		capacityField.setEditable(false);
		capacityPanel = new JPanel();
		//Add components to panel
		capacityPanel.add(capacityLabel);
		capacityPanel.add(capacityField);
		//Add panel to JFrame
		this.add(capacityPanel);
		
		//Set up the spaces filled components
		noBookedLabel = new JLabel ("Number of spaces filled: ");
		noBookedField = new JTextField();
		noBookedField.setEditable(false);
		noBookedPanel = new JPanel();
		//Add components to panel
		noBookedPanel.add(noBookedLabel);
		noBookedPanel.add(noBookedField);
		//Add panel to JFrame
		this.add(noBookedPanel);
		
		//Set up day components
		dayLabel = new JLabel ("day: ");
		dayField = new JTextField();
		dayField.setEditable(false);
		dayPanel = new JPanel();
		//Add components to panel
		dayPanel.add(dayLabel);
		dayPanel.add(dayField);
		//Add panel to JFrame
		this.add(dayPanel);
		
		//Set up cost components
		timeLabel = new JLabel ("time: ");
		timeField = new JTextField();
		timeField.setEditable(false);
		timePanel = new JPanel();
		//Add components to panel
		timePanel.add(timeLabel);
		timePanel.add(timeField);
		//Add panel to JFrame
		this.add(timePanel);
		
		//Set up cost components
		costLabel = new JLabel ("cost: ");
		costField = new JTextField();
		costField.setEditable(false);
		costPanel = new JPanel();
		//Add components to panel
		costPanel.add(costLabel);
		costPanel.add(costField);
		//Add component to JFrame
		this.add(costPanel);
	}
	
	/**
	 * Add all the course details to the text fields
	 */
	private void fillData() {
		
		//Add the course name to relevant text field
		nameField.setText(course.getName());
		//Add the instructor name to the relevant text field
		instructorField.setText(course.getInstructor());
		//Add the capacity to the relevant text field 
		capacityField.setText(Integer.toString(course.getCapacity()));
		//Add the number of people booked onto the course to the relevant text field
		noBookedField.setText(Integer.toString(course.getNoBooked()));
		//Add the day of the course to the relevant text field
		dayField.setText(course.getDay());
		//Add the time of the course to the relevant text field
		timeField.setText(Integer.toString(course.getTime()));
		//Add the cost of the course to the relevant text field
		costField.setText(String.format("£%.2f", course.getCost()));
	}
}
