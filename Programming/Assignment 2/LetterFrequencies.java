/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;

	/** Count for each letter */
	private int [] alphaCounts;

	/** The alphabet */
	private char [] alphabet; 

	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
			0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
			6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars = 0;

	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{

		//Fill array with letters in alphabetical order
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}

		//Initially fill alphaCounts with zeros
		alphaCounts = new int [SIZE];
		for (int i = 0; i < SIZE; i++) {

			alphaCounts[i] = 0;
		}

	}

	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{

		//Get input argument
		char inputChar = ch;

		try  {

			//Find the index of the letter in the alphabet array
			int index = (inputChar - 'A');
			//Increment the count of that letter in the alphaCounts array
			alphaCounts[index] ++;
			//Increment the total number of characters
			totChars++;
		}
		//Catch all letters that are not between A and Z
		catch (ArrayIndexOutOfBoundsException e) {

			//Do nothing
		}
	}

	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
	{
		
		//Initially set the max index to 0
		int maxIndex = 0;
		
		//Searches the alphaCounts array for the most common letter
		for (int i = 0; i < SIZE; i++) {
			
			//If the count of the current index is equal or larger to the previous max
			if (alphaCounts[i] >= alphaCounts[maxIndex]) {
				
				//Set new max index
				maxIndex = i;
			}
		}
		
		//Set the maxCh instance variable
		maxCh = alphabet[maxIndex];
		//Returns the percentage frequency of the most common character as a double
		return ((double) alphaCounts[maxIndex]/totChars*100); 
	}

	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{

		//Format the table header and print to the file
		String header = String.format("%10s %10s %10s %10s %10s %n", "Letter", "Freq", "Freq%", "AvgFreq%", "Diff");
		
		//Variable to store the report that will be returned
		String report = header;

		//Get the letter frequencies for each letter in the alphabet
		for (int i = 0; i < SIZE; i++) {

			//Letter in the alphabet
			char alph = alphabet[i];
			//Get the count of that letter from the alphaCounts array
			int frequency = alphaCounts[i];
			//Calculate the percentage frequency of the letter
			double percentFreq = (double) frequency/totChars*100;
			//Get the average frequency from the avgCounts array
			double avgFreq = avgCounts[i];
			//Calculate the difference between the percentage frequency and the average frequency
			double diff =  percentFreq - avgFreq;

			//Format all the frequency information into columns
			String row = String.format("%10c %10d %10.1f %10.1f %10.1f %n", alph, frequency, percentFreq, avgFreq, diff);
			//Add row to report string
			report += row;
		}
		
		//Get the percentage frequency of the letter that occurs the most
		double maxFreq = getMaxPC();
		//Format the max frequency statement and add it to the report
		String maxString = String.format("%nThe most frequent letter is %c at %.1f%%", maxCh, maxFreq);
		report += maxString;

		//Return the report string
		return report;
	}
}
