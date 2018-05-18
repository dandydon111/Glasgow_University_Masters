import java.awt.*;
import javax.swing.*;

/**
 * Class to define window in which attendance report is displayed.
 */
public class ReportFrame extends JFrame {
	
	//FitnessProgram instance variable
	private FitnessProgram fitnessProgram;
	
	//Text are to display the attendance report
	private JTextArea reportArea;
	
	/**
	 * Constructor for the Report frame
	 * @param fitnessProgram
	 */
	public ReportFrame(FitnessProgram fitnessProgram) {
		
		//Set the instance variable
		this.fitnessProgram = fitnessProgram;
		
		//Set up the JFrame
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("Attendance Report");
		setSize(700, 300);
		reportArea = new JTextArea();
		reportArea.setFont(new Font("Courier", Font.PLAIN, 14));
		add(reportArea, BorderLayout.CENTER);
		
		//Display the attendance data
		populateReport();
	}
	
	/**
	 * Display the attendance data in  the text area
	 */
	private void populateReport() {
		
		//Get the formatted attendance report string
		String attendanceReport = fitnessProgram.getAttendanceReport();
		
		//Display the string in the text area
		reportArea.setText(attendanceReport);
	}
			
}
