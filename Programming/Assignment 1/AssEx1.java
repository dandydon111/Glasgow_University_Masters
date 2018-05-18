/* Matthew Henderson 2031944H
 * Programming Assignment 1
 * Due 7/11/2017
 */


import javax.swing.*;

public class AssEx1 {

	public static void main(String[] args) {

		//get name string from JOptionPane
		String name = JOptionPane.showInputDialog(null, "Enter Customer Name");

		//exit the programme if cancel/close is pressed or if the user does not input a name
		if (name == null || name.equals("")) {

			System.exit(0);
		}

		//infinite loop so user can re-enter balance upon invalid entry
		for(;;) {

			try {

				//define variables and get balance input as a string
				String balanceString = JOptionPane.showInputDialog(null, "Enter Current Balance (£)");
				double balance;

				//Exit programme if cancel/close is pressed
				if (balanceString == null) {

					System.exit(0);
				}

				//convert the balance to a double
				balance = Double.parseDouble(balanceString);

				//check balance is within acceptable range
				if (balance <= 10000 && balance >= -10000 ) {

					//instantiate customer object
					CustomerAccount customer = new CustomerAccount(name.trim(), balance);

					//make and display the gui object. Break the infinite loop
					LWMGUI gui = new LWMGUI(customer);
					gui.setVisible(true);
					break;
				}
				else {
					
					//if balance is outside £10,000 limit, display error message
					JOptionPane.showMessageDialog(null, "The maximum balance is +/-£10,000", "Error", JOptionPane.ERROR_MESSAGE);
				}

			} 
			catch (NumberFormatException nfx) {

				//display error message if balance is in the wrong format
				JOptionPane.showMessageDialog(null, "Please enter a valid balance", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
