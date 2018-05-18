/**
* Matthew Henderson 2031944H
* Crytology and Secure Development Assignment
* Known plain text attack
*/

import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

public class KPT {

  public static void main(String[] args) {

    String filename = "Part1Ciphertext.txt"; //File containing cipher text
    String knownText = "0x4120"; //The known text
    FileReader reader = null;
    Scanner scanner = null;

    try {

      try{

        //Initialise reader and scanner
        reader = new FileReader(filename);
        scanner = new Scanner(reader);

        //Get the first block of cipher text
        String block = scanner.nextLine();

        //The decryption key
        int key = 0;

        //Loop through all the possible keys
        for (int i=1;i<=65536;i++){

          //Decrypt the cipher text with the key and convert to block
          int cipherInt = Hex16.convert(block);
          int plainInt = Coder.decrypt(i, cipherInt);
          String plainHex = String.format("0x%04x", plainInt);

          //Check to see if the decypted text block matches the known text
          if (plainHex.equals(knownText)) {

            //If the text matches, set the key and print to the console
            key=i;
            System.out.println("Match found. The decryption key is: " + key);
          }
        }
        //Decrypt the entire message and print it to the console
        String message = DecryptBlocks.decrypt(filename, key);
        System.out.print("The decrypted message is: " + message);
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
}
