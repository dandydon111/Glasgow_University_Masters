/**
* Algorithms and data structures assignment 2018
 * 
 * @author Matthew Henderson 2031944H
 */


//import classes for file input - scanner etc.
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;

import java.util.Set;
import java.util.HashSet;
import java.util.InputMismatchException;

public class WordProcessor {
	
	/**
	 * Return a string representation of a set
	 * @param inputSet
	 * @return string representation
	 */
	private static <E> String displaySet(Set<E> inputSet){

		String returnString = ""; //String to store the output
		int count = 0; //counter to keep track of the number of elements
		
		//Loop through all the elements in the set
		for (E elem: inputSet) {

			//Increment the count
			count++;
			
			//If not the last element, print with comma
			if (count != inputSet.size()) returnString += "(" + elem + "),";
			//Else print without comma
			else returnString += "(" + elem + ")";
			
			//Add line separator after every 5 lines
			if (count%5 == 0) returnString += System.lineSeparator();
		}

		return returnString;
	}

	/**
	 * Create a set with input from files specified in args
	 * @param args
	 */
	public static void main(String[] args) {

		//create a set of type String called wordSet
		//create a set of type CountedElement<String> called countedWordSet 
		HashSet<String> wordSet = new HashSet<String>();
		HashSet<CountedElement<String>> countedWordSet = new HashSet<CountedElement<String>>(); 

		//Initialise scanner and reader to null
		FileReader reader = null;
		Scanner scanner = null;
		
		//Loop through all the input files
		for (int i = 0; i < args.length; i++) {

			try {

				try {

					//Set up reader and scanner
					reader = new FileReader(args[i]);
					scanner = new Scanner(reader);

					//Scan all lines in the file
					while (scanner.hasNextLine()) {
						
						//Get a complete line from the file, each line is a word
						String word = scanner.nextLine().trim();
						//Create a CountedElement using the word
						CountedElement<String> element = new CountedElement<String>(word);

						//If the word is not already in the wordset
						if (!wordSet.contains(word)){

							//Add the word to the worSet and the CountedElement to the countedWordSet
							wordSet.add(word);
							countedWordSet.add(element);
						//The word is already in the set
						} else {
							
							//Find the CountedElement that contains the word
							for (CountedElement<String> elements: countedWordSet) {
								
								//Increment the count
								if (elements.compareTo(element) == 0) elements.increment();
							}
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

			catch (IOException e) {

				//File loading error
				System.err.println("Error loading file");
			}
			catch (InputMismatchException e) {

				//File reading error
				System.err.println("Error reading file");
			}
		}

		//Print out the countedWordSet
		System.out.println(displaySet(countedWordSet));


	}
}
