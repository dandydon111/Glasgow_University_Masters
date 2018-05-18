/**
* Matthew Henderson 2031944H
* Crytology and Secure Development Assignment
* Cipher text only attack
*/

import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Arrays;

public class CTO {

  //Common english language digrams and trigrams. Based on stats from http:/academic.regis.edu/jseibert/Crypto/Frequency.pdf
  private static String[] COMMON_TRIGRAMS = {"the","ing","and","her", "ere", "ent", "tha", "nth", "was", "eth", "for", "dth"} ;
  private static String[] COMMON_DIGRAMS = {"th","he","in","er","an","re","ed","on","es","st","en","at","to","nt","ha","nd","ou","ea","ng","as","or",
  "ti","is","et","it","ar","te","se","hi","of"};

  public static void main(String[] args) {

    String filename = "Part2Ciphertext.txt"; //File containing cipher text
    int cipherKey = 0; //The decryption key
    int maxCount= 0; //Common digram/trigram count

    //Initiate reader and scanner to null
    FileReader reader = null;
    Scanner scanner = null;

    //Loop through all of the possible keys
    for (int i=1;i<=65536;i++){

      try {

        try{

          //Initialise reader and scanner
          reader = new FileReader(filename);
          scanner = new Scanner(reader);

          //The number of cipher text blocks to decrypt
          int numberOfBlocks = 10;

          //String to store the message
          String message = "";

          //Decrypt blocks with the key
          for (int j = 0; j < numberOfBlocks; j++) {

            //Get the next block of cipher text
            String block = scanner.nextLine();

            //Convert the block to an int and then decrypt
            int key = i;
            int cipherInt = Hex16.convert(block);
            int plainInt = Coder.decrypt(key, cipherInt);

            //Add decrpted text to the message string
            message += BlockToText.toText(plainInt);
          }

          //Does the message only contain english language characters
          boolean english = isEnglishCharacter(message);

          //Count of matching digrams and trigrams
          int count = 0;

          //If the message only contains english language characters
          if (english){

            //Get the count of common trigrams and trigrams
            count = getDigramTrigramCount(message);
          }

          //If the count of matching digrams and trigrams is higher than the previous max
          if (count > maxCount) {
            //Set the max count and the cipher key
            maxCount = count;
            cipherKey = i;
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

    //Display the cipher key on the console
    System.out.println("The cipher key is: " + cipherKey);
    //Decrypt the message and display on the console
    String message = DecryptBlocks.decrypt(filename, cipherKey);
    System.out.println("The decrypted message is: " + message);
  }

  /**
  * Method to detect whether the message only contains english characters
  * @return whether the message contains english language characters
  */
  private static boolean isEnglishCharacter(String message){

    //Does the message only contain english language characters
    boolean english = true;

    //Loop through every character in the decrypted text
    for(int k = 0; k < message.length(); k++){

      //Get the character
      char alphChar = message.charAt(k);
      //The character is not a common english language character
      if (!(alphChar >= 'a' && alphChar <= 'z') && !(alphChar >= 'A' && alphChar <= 'Z')
       && !(alphChar == '.') && !(alphChar == ',') && !(alphChar == '?')&& !(alphChar == '!')
       && !(alphChar == ';') && !(alphChar == ':') && !Character.isSpaceChar(alphChar)) {

        english = false;
      }
    }

    return english;
  }

  /**
  * Method to get the count of common digrams and trigrams in the message text
  * @return the digram/trigram count
  */
  private static int getDigramTrigramCount(String message){

    //Digram/Trigram count
    int count = 0;

    //Loop through all trigrams in the message
    for(int k = 0; k < message.length()-3; k++){

      //Get all trigrams from the message
      String trigram = "" + message.charAt(k) + message.charAt(k+1) + message.charAt(k+2);
      //If it matches a common english trigram, increment counter
      if (Arrays.asList(COMMON_TRIGRAMS).contains(trigram.toLowerCase())) count++;
    }
    //Loop through all digrams in the message
    for(int k = 0; k < message.length()-2; k++){

      //Get all digrams from the message
      String digram = "" + message.charAt(k) + message.charAt(k+1);
      //If it matches a common english digram, increment counter
      if (Arrays.asList(COMMON_DIGRAMS).contains(digram.toLowerCase())) count++;
    }

    return count;
  }
}
