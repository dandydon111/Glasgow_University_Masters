
public class APSpec1 {

	public static void main(String[] args) {
		
		//Vehicle properties
		//Although labelled speed, maxSpeed and minSpeed actually refer to time between vehicles moving squares in the grid
		//Therefore the larger maxSpeed the slower the vehicle will go and the smaller minSpeed the faster the vehicle will go
		int minSpeed = 500;
		int maxSpeed = 2000; 
		int numVehicles = 100;
		int timeBetweenVehicles = 500;
		boolean reverse = false;
		
		//Create a traffic grid with 10 rows and 20 columns
		Grid grid = new Grid(10,20);
		
		//Create the repainter thread and start it, the repainter will continue until interrupted
		Repainter repainter = new Repainter(grid);
		Thread painterThread = new Thread(repainter);
		painterThread.start();
		
		//Arrays of rows and columns the vehicles will drive in
		int[] rows = {0,1,2,3,4,5,6,7,8,9};
		int[] columns = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};
		
		//Create traffic manager threads and start them
		TrafficManager manager = new TrafficManager(grid, minSpeed, maxSpeed, numVehicles, timeBetweenVehicles, rows, columns, reverse);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		
		//Wait for the traffic managers to stop before interrupting the repainter
		try {
			managerThread.join();
		} catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		}
		
		//Interrupt the repainter
		painterThread.interrupt();
	}

}
