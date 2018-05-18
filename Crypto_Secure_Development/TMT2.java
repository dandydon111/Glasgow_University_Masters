/**
* Matthew Henderson 2031944H
* Crytology and Secure Development Assignment
* Time memory trade of attack - Part 2
* Breaking the code
*/

import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.util.InputMismatchException;

public class TMT2 {

  public static void main(String[] args) {

    //Files with cipher text and TMT table respectively
    String cipherFile = "Part3Ciphertext.txt";
    String tableFile = "TMT_table";

    //Convert the known text to an int
    String knownText = "0x5365";
    int knownInt = Hex16.convert(knownText);

    //Initiate scanners and readers to null
    FileReader cipherReader = null;
    FileReader tableReader = null;
    Scanner cipherScanner = null;
    Scanner tableScanner = null;

    //Chain length and number of chains
    int l = 256;
    int n = 256;

    try {

      try{

        //Prepare to read in table file
        tableReader = new FileReader(tableFile);
        tableScanner = new Scanner(tableReader);
        //Create a new hashmap table
        Table table = new Table();

        //Loop through entire file
        while (tableScanner.hasNextLine()){

          //Get next line in file
          String line = tableScanner.nextLine();
          //Splint the start of the chain from the end of the chain
          String[] lineTokens = line.split(" ");

          //Chain start is first token
          int chainStart = Integer.parseInt(lineTokens[0]);
          //Chain end is second token
          int chainEnd = Integer.parseInt(lineTokens[1]);

          //Add the chain to the table
          table.add(chainEnd, chainStart);
        }

        //Prepare to read in cipher file
        cipherReader = new FileReader(cipherFile);
        cipherScanner = new Scanner(cipherReader);

        //Get the first line of the file and convert to an int
        String firstBlock = cipherScanner.nextLine();
        int cipherInt = Hex16.convert(firstBlock);

        //Variables for looping
        boolean foundKey = false;
        int position = l-1;
        int chainStart = 0;

        //Only search while the key has not been found and there are chain positions left to search
        while (foundKey == false && position >= 0){

          //Look for a match in the table
          chainStart = table.find(cipherInt);

          //No match, move to next position along chain
          if (chainStart==-1){
            position--;
            //Encrypt with the known int to get next position along
            cipherInt = Coder.encrypt(cipherInt, knownInt);
          }
          //There is a match
          else{
            //break loop
            foundKey = true;
          }
        }

        //Variable to store the encryption key
        int encryptionKey = 0;

        //Find the key based on its position in the chain
        for (int i = 0; i < position; i++){

          //Encrpt with known int to move along the chain
          if (i==0)encryptionKey = Coder.encrypt(chainStart, knownInt);
          else encryptionKey = Coder.encrypt(encryptionKey, knownInt);
        }

        //If a key was found, display it to the user and decipher file
        if (foundKey == true) {

          //Decode the file with the discovered key
          String message = DecryptBlocks.decrypt(cipherFile, encryptionKey);
          //Display the encryption key and the decoded message
          System.out.println("The encryption key is: " + encryptionKey);
          System.out.println("The decrypted message is: " + message);
        }
        else {

          //There was no match found for the key, inform user
          System.out.println("Could not find the encryption key. Try remaking table.");
        }
      }
      finally {

        //Close the reader and scanner if they have been opened
        if (tableReader != null) {
          //close the reader
          tableReader.close();
        }
        if (tableScanner != null) {
          //close the scanner
          tableScanner.close();
        }
        //Close the reader and scanner if they have been opened
        if (cipherReader != null) {
          //close the reader
          cipherReader.close();
        }
        if (cipherScanner != null) {
          //close the scanner
          cipherScanner.close();
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
