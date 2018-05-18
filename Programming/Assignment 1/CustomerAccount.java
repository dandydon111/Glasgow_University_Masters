
public class CustomerAccount {
	
	//Define instance variables
	private String name;
	private int balancePence;
	private final int SERVICE_CHARGE = 20;
	
	public CustomerAccount(String customerName, double balance) {
		
		//Assign instance variables
		name = customerName;
		//convert balance into pence
		balancePence = (int) Math.round(balance*100);
	}
	
	//called when the sale button is pressed
	public double processSale(double costBottle, int numBottle) {
		
		int totalPence;
		double totalSale;
		
		//gets the total cost of the transaction in pence
		totalPence = (int) Math.round(costBottle * numBottle * 100);
		//updates customer balance
		balancePence += totalPence;
		
		//returns the total cost of the transaction as a double
		totalSale = (double) totalPence/100;
		return totalSale;
		
	}
	
	//called when refund button is pressed
	public double processRefund(double costBottle, int numBottle) {
		
		int totalPence;
		double totalReturn;
		
		//gets the total cost of the transaction and subtracts service charge
		totalPence = (int) Math.round(costBottle * numBottle * (100 - SERVICE_CHARGE));
		//updates customer balance
		balancePence -= totalPence;
		
		//returns the total cost of the transaction as a double
		totalReturn = (double) totalPence/100;
		return totalReturn;
	}
	
	
	//returns the customer balance as a double
	public double getBalance() {
		
		double balance;
		
		//converts the balance to pounds and pence
		balance = (double) balancePence/100;
		return balance;
	}
	
	//returns the name of the customer
	public String getName() {
		
		return name;
	}
	
	public int getServiceCharge() {
		
		return SERVICE_CHARGE;
	}

}

