import java.util.Arrays;

/**
 * Programming AE2
 * Contains monoalphabetic cipher and methods to encode and decode a character.
 */
public class MonoCipher
{
	
	//Instance variables
	private final int SIZE = 26; //The size of the alphabet
	private char [] alphabet; //The alphabet
	private char [] cipher; //The cipher array

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{

		//The length of the keyword
		int length = keyword.length();

		//Fill array with letters in alphabetical order
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}

		//Instantiate the array
		cipher = new char [SIZE];
		
		//Fills the cipher array
		for (int i = 0; i<length; i++) {

			//Put the keyword in the first positions of the array
			cipher[i] = keyword.charAt(i);
		}

		int counter = 0;

		//Fill the rest of the array
		for (int i = 0; i<SIZE; i++) {

			//boolean to test for match with keyword letter
			boolean match = false;
			//Letter of the alphabet starting at Z and working backwards
			char alphLetter = (char)('Z' - i);

			for (int j = 0; j<length; j++) {

				//Try each keyword letter against alphabet letter
				char keyLetter = keyword.charAt(j);

				//Keyword letter matches alphabet letter
				if (keyLetter == alphLetter){

					//Increment counter
					counter++;
					//Set match to true
					match = true;
				}
				//There is no match between keyword letter and alphabet letter, and all keyword letters have been tested
				if (match == false && j == (length-1)) {

					/*
					 * Add alphabet letter to the cipher array
					 * Counter corrects index of the letters to account for keyword letters
					 */
					cipher[i + length - counter] = alphLetter;
				}
			}	
		}

		//Print cipher array for testing
		System.out.println("Monoalphabetic cipher array: " + Arrays.toString(cipher));

	}

	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{

		//Declare variables
		char decoded = ch;
		char encoded;
		//Index position in alphabet array
		int index = (decoded - 'A');

		try {
			
			//Get corresponding letter in the cipher array
			encoded = cipher[index];
			return encoded;
		}
		/*If character does not encode, then return decoded character
		 *Used to return spaces, comas, etc
		 */
		catch(ArrayIndexOutOfBoundsException e) {
			
			return decoded;
		}

	}

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{

		//Declare variables
		char encoded = ch;
		char decoded;
		//Variables for searching the array
		boolean charFound = false;
		int index = 0;

		try {

			//Search for character in the cipher array
			while (!charFound) {

				//Character found
				if (cipher[index] == encoded) {
					
					//Break loop
					charFound = true;
				}
				else {

					//Character not found, increment index
					index++;
				}
			}

			//get the decoded character from the equivalent position in the alphabet array
			decoded = alphabet[index];

			//return the decoded character
			return decoded;

		}
		/*If character does not decode, then return encoded character
		 *Used to return spaces, comas, etc
		 */
		catch (ArrayIndexOutOfBoundsException e) {

			return encoded;
		}
	}
}
