import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textfile filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	private String filename;
	private boolean encode;
	private String keyword;

	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{
		this.setSize(400,150);
		this.setLocation(100,100);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}

	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);

		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);

		//bottom panel is green and contains 2 buttons

		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}

	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e) {

		//The process mono cipher button has been pressed
		if (e.getSource() == monoButton) {

			//Validate the keyword and filename
			if (getKeyword() && processFileName()) {

				//Keyword and filename are valid, process the file
				if(processFile(false)) {
					
					//Terminate the programme upon successful completion (as per specification)
					System.exit(0);
				}
			}
			//If there is an invalid input clear text fields
			else {

				clearInputs();
			}
		}
		//The process vigenere cipher button has been pressed
		else if (e.getSource() == vigenereButton) {

			//Validate the keyword and filename
			if (getKeyword() && processFileName()) {

				//Keyword and filename are valid, process the file
				if (processFile(true)) {

					//Terminate the programme upon successful completion (as per specification)
					System.exit(0);
				}
			}
			//If there is an invalid input clear text fields
			else {

				clearInputs();
			}
		}
	}

	/**
	 * Clears the input textFields
	 */
	private void clearInputs() {

		//clear keyword and filename fields
		keyField.setText("");
		messageField.setText("");
	}

	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{

		//Get the keyword from the input field. Put it to caps
		String key = keyField.getText().toUpperCase();

		//Test for an empty string
		if (key.equals("")) {

			//display error message and return false
			JOptionPane.showMessageDialog(this, "Please enter a keyword", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Test if there is a repeating letter in the keyword
		for (int i=0; i<key.length(); i++) {

			//Get the ith letter in the keyword
			char a = key.charAt(i);

			for (int j=0; j<key.length(); j++) {

				//Compare against all other letter in the keyword
				char b = key.charAt(j);

				//If letters match and are not the same position in the keyword
				if (a == b && !(i == j)) {

					//display error message and return false
					JOptionPane.showMessageDialog(this, "Please ensure the keyword has no duplicate letters", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}

		//Test for invalid characters
		for (int i=0; i<key.length(); i++) {

			//Test each character in keyword
			int keyChar = key.charAt(i);

			//Only allow letters in the keyword
			if (keyChar < 'A' || keyChar > 'Z') {

				//Display error message and return false
				JOptionPane.showMessageDialog(this, "Please include only letters in the keyword", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		//Keyword is valid. Assign keyword to instance variable and return true
		keyword = key;
		return true;
	}

	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{

		//Get the name of the message file from the text field
		String message = messageField.getText();

		//check if the messageField is empty
		if (message.equals("")) {

			//Display error message and return false
			JOptionPane.showMessageDialog(this, "Please enter the filename", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		//Get the last letter of the message string
		String lastLetter = message.substring(message.length() - 1);

		//Check if it ends with P
		if(lastLetter.equals("P") || lastLetter.equals("p")) {

			//The file is to be encoded. Saved as a boolean instance variable
			encode = true;

			//Save root filename to instance variable and return true
			filename = message.substring(0, message.length() - 1);
			return true; 

		}
		//Check if it ends with C
		else if (lastLetter.equals("C") || lastLetter.equals("c")) {

			//File is to be decoded. Save as a boolean instance variable
			encode = false;

			//Save root filename to instance variable and return true
			filename = message.substring(0, message.length() - 1);
			return true;

		}
		else {

			//The filename does not end in P or C. Display error message and return false
			JOptionPane.showMessageDialog(this, "Please ensure the last letter of the filename is either P or C", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}	
	}

	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere) {

		//Create instances of the ciphers and letterFrequencies
		mcipher = new MonoCipher(keyword);
		vcipher = new VCipher(keyword);
		LetterFrequencies frequencies = new LetterFrequencies();

		//Create a fileReader, fileWriter and filePrinter. Set to null
		FileReader reader = null;
		FileWriter writer = null;
		PrintWriter frequencyWriter = null;

		//Try catch for file handling exceptions
		try {

			try {

				//boolean for looping
				boolean done = false;

				//If the file is to be encoded
				if (encode) {

					//Set the name of the file to read and the files to be written to
					reader = new FileReader(filename + "P.txt");
					writer = new FileWriter(filename + "C.txt");
					frequencyWriter = new PrintWriter(filename + "F.txt");

					//Loop through all the characters in the input file
					while(!done) {

						//Read the next character of the input file
						int nextCharInt = reader.read();
						char nextChar = (char) nextCharInt;

						//If the end of file has been reached
						if(nextCharInt == -1) {

							//Call finished
							vcipher.finished();
							//Generate frequency report
							String report = frequencies.getReport();
							//print frequency report to the output file
							frequencyWriter.print(report);
							//Break loop
							done = true;
						}
						//If the vigenere button has been pressed
						else if (vigenere) {

							//Get the encrypted character and write it to the C file
							char encryptedChar = vcipher.encode(nextChar);
							writer.write(encryptedChar);

							//Add the character to the frequency report
							frequencies.addChar(encryptedChar);
						}
						//If the mono button has been pressed
						else {

							//Get the encrypted character and write it to the C file
							char encryptedChar = mcipher.encode(nextChar);
							writer.write(encryptedChar);

							//Add the character to the frequency report
							frequencies.addChar(encryptedChar);
						}
					}
				}
				//If the file is to be decoded
				else {

					//Set the name of the file to read and the files to be written to
					reader = new FileReader(filename + "C.txt");
					writer = new FileWriter(filename + "D.txt");
					frequencyWriter = new PrintWriter(filename + "F.txt");

					//Loop through all the characters in the input file
					while (!done) {

						//Read the next character of the input file
						int nextCharInt = reader.read();
						char nextChar = (char) nextCharInt;

						//If the end of file has been reached
						if (nextCharInt == -1) {

							//Call finished
							vcipher.finished();
							//Generate frequency report
							String report = frequencies.getReport();
							//Print frequency report to the output file
							frequencyWriter.print(report);
							//Break loop
							done = true;
						}
						//If the vigenere button has been pressed
						else if (vigenere) {

							//Get the decrypted character and write it to the D file
							char decryptedChar = vcipher.decode(nextChar);
							writer.write(decryptedChar);

							//Add the character to the frequency report
							frequencies.addChar(decryptedChar);
						}
						//If the mono button has been pressed
						else {

							//Get the decrypted character and write it to the D file
							char decryptedChar = mcipher.decode(nextChar);
							writer.write(decryptedChar);

							//Add the character to the frequency report
							frequencies.addChar(decryptedChar);
						}
					}
				}

				//Return true if encode/decode operation was successful
				return true;
			}
			finally {

				//Close reader and writers (If they have been created)
				if (reader != null) {

					reader.close();
				}
				if (writer != null) {

					writer.close();
				}
				if (frequencyWriter != null) {

					frequencyWriter.close();
				}
			}
		} 
		//catch file handling errors
		catch (IOException exception) {

			//display relevant error message and then return false
			JOptionPane.showMessageDialog(this, "Error processing file: " + exception, "Error", JOptionPane.ERROR_MESSAGE);
			clearInputs();
			return false;
		}

	}
}
