
public class Wine {
	
	//declare class variables
	private String name;
	private int pricePence;
	private int quantity;
	
	public Wine(String wineName, double winePrice, int wineQuantity) {
		
		//Assigns instance variables
		name = wineName;
		//converts the price to pence and assigns to variable
		pricePence = (int) Math.round(winePrice*100);
		quantity = wineQuantity;
	}
	
	//returns the name of the wine
	public String getName() {
		
		return name;
	}
	
	//returns the cost of a single bottle as a double
	public double getPrice() {
		
		double price;
		
		//converts to pounds and pence
		price = (double) pricePence/100;
		return price;
	}
	
	//returns the quantity that has been ordered
	public int getQuantity() {
		
		return quantity;
	}
	
	
}