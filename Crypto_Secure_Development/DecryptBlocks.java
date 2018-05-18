/**
* Matthew Henderson 2031944H
* Crytology and Secure Development Assignment
*/

import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

/**
* Class to decrypt all blocks of a message file
*/
public class DecryptBlocks{

  /**
  * Decrypt all blocks of a message file with a given key
  * @param the name of the file containing the cipher text - filename
  * @param the cipher key - key
  */
  public static String decrypt(String filename, int key){

    //Initiate reader and scanner to null
    FileReader reader = null;
    Scanner scanner = null;
    //String to store the message
    String message = "";

    try {

      try{

        //Assign reader and scanner
        reader = new FileReader(filename);
        scanner = new Scanner(reader);

        //Loop through the whole file
        while (scanner.hasNextLine()){

          //Decrypt the cipher text
          String block = scanner.nextLine();
          int cipherInt = Hex16.convert(block);
          int plainInt = Coder.decrypt(key, cipherInt);

          //Add decrypted block to the message
          message += BlockToText.toText(plainInt);
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

    //Return the message
    return message;
  }
}
