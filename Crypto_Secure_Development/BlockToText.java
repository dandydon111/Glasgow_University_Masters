/**
* Matthew Henderson 30/1/18
* Class to convert a hex block to text
* Adapted from the sample class provided by Ron Poet
*/

public class BlockToText {

  /**
  *Converts the integer representation of a hex block into plain text
  *@param the integer representation of a hex block - blockInt
  *@return the plain text string
  */
  public static String toText (int blockInt){

    String text;

    //the first char of the plain text
    int	ch1 = blockInt / 256;
    //the second char of the plain text
    int	ch2 = blockInt % 256;

    //cast the first char to a string
    text = "" + (char)ch1;

    //if the second char is not a filler char, add to the string
    if (ch2 != 0)
      text += ((char)ch2);

    //return the string
    return text;
  }
}
