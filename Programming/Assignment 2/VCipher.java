/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
import java.util.*;

public class VCipher
{
	private char [] alphabet;   //The letters of the alphabet
	private char [][] cipher;	//Cipher array
	private final int SIZE = 26;	//The size of the alphabet
	private int encodeCounter = 0;	//Counter for indexing the correct array
	private String keyword = "";	//The cipher keyword

	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		//Set keyword instance variable and get the length
		this.keyword = keyword;
		int length = keyword.length();
		
		//Define the cipher array
		cipher = new char [length][SIZE];

		//Fill alphabet array with letters in alphabetical order
		alphabet = new char [SIZE];
		for (int i = 0; i < SIZE; i++) {
			alphabet[i] = (char)('A' + i);
		}

		//Fill the cipher array
		for (int i = 0; i < length; i++) {

			//Get the ith letter of the keyword and place it in the first index of the ith array of the cipher
			char keyLetter = keyword.charAt(i);
			cipher[i][0] = keyLetter;

			//fill the rest of the array with the remaining letter of the alphabet
			for (int j = 1; j < SIZE; j++) {
	
				//Ensure that only characters between A and Z are put into the array
				if (keyLetter + j > 'Z') {

					//After Z, start at the beginning of the alphabet. Put in the array
					cipher [i][j] = (char) (keyLetter + j - SIZE);
				}
				else {

					//put the letter in the array
					cipher [i][j] = (char) (keyLetter + j);
				}
			}
		}
		//print the cipher array to the console for marking
		System.out.println("Vigenere cipher arrays: " + Arrays.deepToString(cipher));

	}
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{

		//Define variables
		char decoded = ch;
		char encoded;
		//Get the index position of the letter in the alphabet array
		int index = (decoded - 'A');
		int length = keyword.length();

		//Try catch to deal with invalid characters
		try {

			//use encodeCounter instance variable to select the correct array
			encoded = cipher[encodeCounter][index];
			
			//Do not let encodeCounter get larger than the array
			if (encodeCounter == length - 1) {
				
				//Reset encodeCounter to 0
				encodeCounter -= length - 1;
			}
			else {
				
				//increment counter
				encodeCounter ++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){

			//If the character cannot be encoded, return the un-encoded character 
			return decoded;
		}

		//Return the encoded character
		return encoded;
	}

	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
		
		//Define variables
		char encodedChar = ch;
		char decodedChar;
		boolean charFound = false;
		int index = 0;
		int length = keyword.length();
		
		try {
			
			//Search for the character in the cipher array
			while(!charFound) {
				
				//Character found
				if (cipher [encodeCounter][index] == encodedChar) {
					
					//Break loop
					charFound = true;
				}
				else {
					
					//increment the index if not found
					index++;
				}
			}
			
			//get the decoded character from the equivalent position in the alphabet array
			decodedChar = alphabet[index];
			
			//Do not let the encodeCounter get larger than the array
			if (encodeCounter == length - 1) {
				
				//Reset counter to 0
				encodeCounter -= length - 1;
			}
			else {
				
				//Increment counter
				encodeCounter++;
			}

			//Return the decoded character
			return decodedChar;
		}
		//Catches all characters not between A and Z
		catch (ArrayIndexOutOfBoundsException e) {
			
			//If the character cannot be decoded, return the encoded character
			return encodedChar;
		}
	}

	/**
	 * Called after every set of encode/decode operations
	 * Resets the encodeCounter to 0
	 * Not strictly necessary for this programme, but would be useful for different implementations
	 */
	public void finished() {

		encodeCounter = 0;
	}
}
