
public class APSpec2 {
	
public static void main(String[] args) {
		
		//Create a traffic grid with 10 rows and 20 columns
		Grid grid = new Grid(10,20);
		
		//Create the repainter thread and start it, the repainter will continue until interrupted
		Repainter repainter = new Repainter(grid);
		Thread painterThread = new Thread(repainter);
		painterThread.start();
		
		//Arrays of rows in the grid to pass to the traffic managers
		int[] rows1 = {0,1,2,3,4};
		int[] rows2 = {5,6,7,8,9};
		
		//Arrays of columns in the grid to pass to the traffic managers
		int[] columns1 = {0,1,2,3,4,5,6,7,8,9};
		int[] columns2 = {10,11,12,13,14,15,16,17,18,19};
		
		//Create thread and traffic manager arrays
		Thread[] trafficThreads = new Thread[2];
		TrafficManager[] trafficManagers = new TrafficManager[2];
		
		//Create traffic manager objects and add them to the array 
		TrafficManager manager1 = new TrafficManager(grid, 10, 2000, 30, 1300, rows1, columns1, false);
		trafficManagers[0] = manager1;
		TrafficManager manager2 = new TrafficManager(grid, 100, 1000, 40, 1200, rows2, columns2, true);
		trafficManagers[1] = manager2;
		
		//Create threads from the traffic manager objects and added to the array of threads
		trafficThreads[0] = new Thread(manager1);
		trafficThreads[1] = new Thread(manager2);
		
		//Start the traffic manager threads
		trafficThreads[0].start();
		trafficThreads[1].start();
		
		//Wait for the traffic managers to stop before interrupting the repainter and printing statistics
		try {
			trafficThreads[0].join();
			trafficThreads[1].join();
		} catch (InterruptedException e) {
			
			//Display error if interrupted
			e.printStackTrace();
		}
		
		//Traffic manager has finished, interrupt the repainter
		painterThread.interrupt();
		
		//Create statistics class and print statistics to the console
		Statistics statistics = new Statistics();
		for (int i = 0; i < trafficManagers.length; i++) {
			
			statistics.printStats(trafficManagers[i].getVehicleTimes(), "Thread: " + (i+1));
		}
		
	}
}
