/**
 * Database assignment
 * Due 28/1/17
 * @author Matthew Henderson - 2031944H
 *
 */

public class Main {

	public static void main(String[] args) {

		//Create database object
		DB db = new DB();
		//Create gui object and pass it the database object
		GUI gui = new GUI(db);
		//Make the gui visible
		gui.setVisible(true);
	}

}
