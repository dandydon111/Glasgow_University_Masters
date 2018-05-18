/**
* Matthew Henderson 2031944H
* Crytology and Secure Development Assignment
* Time memory trade off attack - Part 1
* Creating the table
*/

import java.util.Random;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TMT1 {

  private static String filename = "TMT_table";
  private static PrintWriter writer = null;

  public static void main(String[] args) {

    String knownText = "0x5365";
    int knownInt = Hex16.convert(knownText); //Convert known text to int
    int n = 256; //The number of chains
    int l = 256; //The length of each chain

    //Open PrintWriter to write chains to the file
    openWriter();

    //construct 256 chains each starting with a random number
    for (int i = 0; i < n; i++){
      //Generate random number in the keyspace range
      Random random = new Random();
      int chainStart = random.nextInt(65536);
      int chainNext = 0;

      //Construct 256 links to each of the chains
      for (int j = 0; j < l; j++){

        //First iteration, get the next link of the chain with the first link
        if (j==0) chainNext = Coder.encrypt(chainStart, knownInt);
        //Get the next link of the chain using the last link
        else chainNext = Coder.encrypt(chainNext, knownInt);
      }

      //Write the start and end of each chain to the file
      writer.println(chainStart + " " + chainNext);
    }

    //close PrintWriter
    closeWriter();
  }

  //Method to open the PrintWriter
  private static void openWriter() {

		try {

			//Set up the PrintWriter
			writer = new PrintWriter(filename);
		}
		//Catch exception
		catch (FileNotFoundException e) {

			//Show error message
			System.out.println("The file could not be found");
		}
	}

  //Method to close the PrintWriter
  public static void closeWriter(){

		//Close the writer if it has been opened
		if (writer != null) {
			writer.close();
		}
	}

}
