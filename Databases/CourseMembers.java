/**
 * Frame to display all the members booked on the current course
 */

import javax.swing.*;

public class CourseMembers extends JFrame {

	//Array of members taking the course
	private Member[] courseMembers;
	//Table for displaying names
	private JTable table;

	public CourseMembers(Member[] members) {

		//Assign instance variables
		courseMembers = members;
		
		//Set up the JFrame
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setTitle("Members booked on course");
		this.setSize(300,300);
		this.setLayoutComponents();
	}

	/**
	 * Set the layout components and add data to the table
	 */
	private void setLayoutComponents() {

		//Create String array with table titles
		String[] columnNames = {"Member Name", "Member ID"};
		//Create 2D object array for table data
		Object[][] data = new Object[courseMembers.length][2];

		//Populate the table with the members taking the course
		for (int i = 0; i < courseMembers.length; i++) {

			//Make full name string with first and last names
			String name = courseMembers[i].getFName() + " " + courseMembers[i].getSName();
			//Get the users id
			int id = courseMembers[i].getID();

			//populate table with names and ids
			data[i][0] = name;
			data[i][1] = new Integer(id);
		}

		//Insert data into table and put table in a scroll pane
		table = new JTable(data, columnNames);
		////Add the scroll pane to the JFrame
		this.add(new JScrollPane(table));
	}
}