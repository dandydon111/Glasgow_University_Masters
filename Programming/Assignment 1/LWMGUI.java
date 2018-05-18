import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LWMGUI extends JFrame implements ActionListener {

	//instance variables are components of JFrame
	private JButton sale, refund; //sale and refund buttons
	private JTextField nameInput, quantityInput, priceInput; //inputs regarding wine 
	private JLabel nameLabel, quantityLabel, priceLabel; //labels regarding wine
	private JTextField transactionText, balanceText; //where transaction details will be displayed
	private JLabel transactionLabel, balanceLabel, wineTypeLabel, wineType; //labels for transaction details
	private JPanel bottomLeft, bottomRight; //JPanels for layout purposes 

	//define customer class
	private CustomerAccount customer;

	//constructor. Sets up gui and calls method to fill the gui with components
	public LWMGUI(CustomerAccount account) {

		this.customer = account;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(customer.getName());
		this.setSize(600,250);
		this.setLayout(new GridLayout(6, 2));
		this.setLayoutComponents();
	}

	//assigns all components of the gui and adds them to the frame
	private void setLayoutComponents() {

		//assign buttons and add action listeners
		sale = new JButton("Process Sale");
		refund = new JButton("Process Refund");
		sale.addActionListener(this);
		refund.addActionListener(this);

		//assign the text inputs
		nameInput = new JTextField(30);
		quantityInput = new JTextField(30);
		priceInput = new JTextField(30);

		//add text to input labels and justify to the right
		nameLabel = new JLabel("name:");
		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		quantityLabel = new JLabel("quantity:");
		quantityLabel.setHorizontalAlignment(JLabel.RIGHT);
		priceLabel = new JLabel("price (£):");
		priceLabel.setHorizontalAlignment(JLabel.RIGHT);

		//assign the transaction details labels. Justify right
		wineTypeLabel = new JLabel("Name of wine: ");
		wineTypeLabel.setHorizontalAlignment(JLabel.RIGHT);
		wineType = new JLabel("");
		transactionLabel = new JLabel("Cost of transaction (£):");
		transactionLabel.setHorizontalAlignment(JLabel.RIGHT);
		balanceLabel = new JLabel("Customer balance (£):");
		balanceLabel.setHorizontalAlignment(JLabel.RIGHT);

		//assign the text box's where the balance and transaction cost will be displayed
		//make them uneditable
		transactionText = new JTextField(10);
		transactionText.setEditable(false);
		balanceText = new JTextField(10);
		balanceText.setEditable(false);

		//assign the layout panels and set them to a grid layout
		bottomLeft = new JPanel();
		bottomLeft.setLayout(new GridLayout(1, 2));
		bottomRight = new JPanel();
		bottomRight.setLayout(new GridLayout(1, 2));

		//add the transaction and balance elements to the panels
		bottomLeft.add(transactionLabel);
		bottomLeft.add(transactionText);
		bottomRight.add(balanceLabel);
		bottomRight.add(balanceText);

		//Add all components to the JFrame
		this.add(nameLabel);
		this.add(nameInput);
		this.add(quantityLabel);
		this.add(quantityInput);
		this.add(priceLabel);
		this.add(priceInput);
		this.add(sale);
		this.add(refund);
		this.add(wineTypeLabel);
		this.add(wineType);
		this.add(bottomLeft);
		this.add(bottomRight);

		//get the initial customer balance and display it in the gui
		setBalance();
	}

	//event listener
	public void actionPerformed(ActionEvent e) {

		//define variables
		String name;
		double price;
		int quantity;

		//only create wine object and continue logic if user inputs are valid
		if (validateInput()) {

			//assigns variables to user input
			price = Double.parseDouble(priceInput.getText().trim());
			quantity = Integer.parseInt(quantityInput.getText().trim());
			name = nameInput.getText().trim();

			//create wine object for the most recent transaction
			Wine wine = new Wine(name, price, quantity);

			//called when the sale button is pressed
			if (e.getSource() == sale) {

				//passes wine object to sale method
				wineSale(wine);
			}

			//called when the refund button is pressed
			else if (e.getSource() == refund) {

				//passes wine object to refund method
				wineRefund(wine);
			}
		}
		else {

			//clear input text boxes in the case of an invalid input
			clearInputs();
		}
	}

	//method to validate user input in the name, price and quantity fields
	private boolean validateInput() {

		//define variables
		double price;
		int quantity;
		String name;

		try {

			//get price from user input
			price = Double.parseDouble(priceInput.getText().trim());

			try {

				//get name and quantity values from user input
				quantity = Integer.parseInt(quantityInput.getText().trim());
				name = nameInput.getText().trim();

				if (quantity < 1) {

					//if quantity is negative, display error and return false
					JOptionPane.showMessageDialog(this, "Please enter a positive quantity", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}	
				else if(quantity > 500) {

					//Set the maximum quantity to 500. If larger return false
					JOptionPane.showMessageDialog(this, "The maximum quantity is 500", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else if (price < 1) {

					//if price is negative, display error and return false
					JOptionPane.showMessageDialog(this, "Please enter a positive price", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else if (price > 500) {

					//set the maximum price for a bottle to £500. If larger return false
					JOptionPane.showMessageDialog(this, "The maximum price is £500", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else if (name.equals("")) {

					//prompt user for the name of the wine if not entered. Return false
					JOptionPane.showMessageDialog(this, "Please enter the name of the wine", "Error", JOptionPane.ERROR_MESSAGE);
					return false;
				}
				else {

					//return true if all user user inputs are valid
					return true;
				}
			}

			catch (NumberFormatException nfx) {

				//if the quantity is in the wrong format, display error pop up and return false
				JOptionPane.showMessageDialog(this, "Please enter the quantity as a positive integer", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}

		catch (NumberFormatException nfx){

			//if the price is in the wrong format, display error pop up and return false
			JOptionPane.showMessageDialog(this, "Please enter a valid price", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

	}

	//processes the wine sale
	private void wineSale(Wine wine) {

		double total;
		
		//update customer balance and get the total cost of the transaction as a double
		total = customer.processSale(wine.getPrice(), wine.getQuantity());
		//updates gui with transaction info
		updateDisplay(total, wine);
	}

	//processes the wine refund
	private void wineRefund(Wine wine) {

		double total;
		
		//updates customer balance and gets the total cost of the transaction as a double
		total = customer.processRefund(wine.getPrice(), wine.getQuantity());
		//updates gui with transaction info
		updateDisplay(total, wine);
	}

	//updates the gui after a transaction has taken place
	private void updateDisplay(double total, Wine wine) {

		//display transaction details
		//show the name of the wine purchased
		wineType.setText(wine.getName());
		//display the total cost of the transaction to 2 decimal places
		transactionText.setText(String.format("%.2f", total));

		//clear input text fields
		clearInputs();

		//sets the updated customer balance text field
		setBalance();
	}
	
	//clears all the text field inputs
	private void clearInputs() {

		//clear wine info text fields
		nameInput.setText("");
		quantityInput.setText("");
		priceInput.setText("");
	}

	//formats and displays the current customer balance
	private void setBalance() {

		//Display the balance and whether customer is in credit
		if (customer.getBalance() < 0) {
			//customer is in credit. Add CR string and display balance as positive. 2 decimal places
			balanceText.setText(String.format("%.2f CR", -customer.getBalance()));
		}

		else {
			//positive balance displayed to 2 decimal places
			balanceText.setText(String.format("%.2f", customer.getBalance()));
		}
	}

}
