import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

public class TrafficTest {

	/**
	 * Test that the grid initialises with the correct number of rows/columns
	 */
	@Test
	public void testGridCorrectSize() {

		Grid grid = new Grid(20,20);
		assertEquals("Grid has correct number of rows", 20, grid.getRows());
		assertEquals("Grid has correct number of columns", 20, grid.getColumns());

	}

	/**
	 * Test that a vehicle is successfully added to the top of the grid and moves one space south
	 */
	@Test
	public void testVehicleMovesSouth() {

		Grid grid = new Grid(10,20);
		Vehicle vehicle = new Vehicle(grid, 0, 10, 0, 5, false);

		//Add to the grid
		grid.addToGrid(vehicle);
		assertEquals("Vehicle has been added to the top of the grid", vehicle, grid.getVehicleFromGrid(0,5));

		//Move one space along the grid
		grid.move(vehicle);
		assertEquals("Vehicle only moves 1 space south", 1, vehicle.getRow());
	}

	/**
	 * Tests that a vehicle is successfully added to the bottom of the grid and moves one space north
	 */
	@Test
	public void testVehicleMovesNorth() {

		Grid grid = new Grid(10,20);
		Vehicle vehicle = new Vehicle(grid, 0, 10, 0, 12, true);

		//Add to the grid
		grid.addToGrid(vehicle);
		assertEquals("Vehicle has been added to the bottom of the grid", vehicle, grid.getVehicleFromGrid(9,12));

		//Moves one space to the north
		grid.move(vehicle);
		assertEquals("Vehicle only moves 1 space south", 8, vehicle.getRow());
	}

	/**
	 * Tests that a vehicle is successfully added to the left of the grid and moves one space east
	 */
	@Test
	public void testVehicleMovesEast() {

		Grid grid = new Grid(10,20);
		Vehicle vehicle = new Vehicle(grid, 1, 10, 2, 0, false);

		//Add to the grid
		grid.addToGrid(vehicle);
		assertEquals("Vehicle has been added to the left of the grid", vehicle, grid.getVehicleFromGrid(2,0));

		//Move one space east
		grid.move(vehicle);
		assertEquals("Vehicle only moves 1 space east", 1, vehicle.getColumn());
	}

	/**
	 * Tests that a vehicle is successfully added to the right of the grid and moves one space west
	 */
	@Test
	public void testVehicleMovesWest() {

		Grid grid = new Grid(10,20);
		Vehicle vehicle = new Vehicle(grid, 1, 10, 6, 0, true);

		//Add to the grid
		grid.addToGrid(vehicle);
		assertEquals("Vehicle has been added to the right of the grid", vehicle, grid.getVehicleFromGrid(6,19));

		//Move one space west
		grid.move(vehicle);
		assertEquals("Vehicle only moves 1 space west", 18, vehicle.getColumn());
	}

	/**
	 * Tests that the same number of vehicles come out the grid as goes in the grid
	 * Also tests that all vehicles have a time logged
	 * Normal size grid
	 */
	@Test (timeout = 10000)
	public void testVehiclesOut() {

		Grid grid = new Grid(10,20);

		//Arrays of rows and columns the vehicles will drive in
		int[] rows = {0,1,2,3,4,5,6,7,8,9};
		int[] columns = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};

		//Create traffic manager thread and start it
		TrafficManager manager = new TrafficManager(grid, 10, 20, 15, 5, rows, columns, false);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		try {
			//Wait for all vehicles to finish
			managerThread.join();
		} catch (InterruptedException e) {

			//Fall through
		}
		//Get array of vehicles and times to move through the grid
		Vehicle[] vehicles = manager.getVehicles();
		int[] times = manager.getVehicleTimes();

		//Counters
		int vehiclesOut = 0;
		int timesAccountedFor = 0;

		//Check how many vehicles come out
		for (int i = 0; i < vehicles.length; i++) {

			if(vehicles[i].isFinished()) vehiclesOut++;
		}

		//Check how many vehicles have logged times
		for (int i = 0; i < times.length; i++) {

			if(times[i]>0) timesAccountedFor++;
		}

		assertEquals("Same number of vehicles in as vehicles out", 15, vehiclesOut);
		assertEquals("All vehicles have logged a time to move through the grid", 15, timesAccountedFor);
	}

	/**
	 * Tests that the same number of vehicles come out the grid as goes in the grid
	 * Also tests that all vehicles have a time logged
	 * Heavy traffic
	 */
	@Test (timeout = 10000)
	public void testLotsOfTrafficOut() {

		Grid grid = new Grid(10,20);

		//Arrays of rows and columns the vehicles will drive in
		int[] rows = {0,1,2,3,4,5,6,7,8,9};
		int[] columns = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19};

		//Create traffic manager thread and start it
		TrafficManager manager = new TrafficManager(grid, 5, 10, 1000, 5, rows, columns, false);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		try {
			//Wait for all vehicles to finish
			managerThread.join();
		} catch (InterruptedException e) {

			//Fall through
		}
		//Get array of vehicles and times to move through the grid
		Vehicle[] vehicles = manager.getVehicles();
		int[] times = manager.getVehicleTimes();

		//Counters
		int vehiclesOut = 0;
		int timesAccountedFor = 0;

		//Check how many vehicles come out
		for (int i = 0; i < vehicles.length; i++) {

			if(vehicles[i].isFinished()) vehiclesOut++;
		}

		//Check how many vehicles have logged times
		for (int i = 0; i < times.length; i++) {

			if(times[i]>0) timesAccountedFor++;
		}

		assertEquals("Same number of vehicles in as vehicles out, dense traffic", 1000, vehiclesOut);
		assertEquals("All vehicles have logged a time to move through the grid, dense traffic", 1000, timesAccountedFor);
	}

	/**
	 * Tests that the same number of vehicles come out the grid as goes in the grid
	 * Also tests that all vehicles have a time logged
	 * Large grid
	 */
	@Test (timeout = 20000)
	public void testBigGridOut() {

		Grid grid = new Grid(1000,1000);

		//Arrays of rows and columns the vehicles will drive in
		int[] rows = IntStream.range(0, 1000).toArray();
		int[] columns = IntStream.range(0, 1000).toArray();

		//Create traffic manager thread and start it
		TrafficManager manager = new TrafficManager(grid, 5, 10, 20, 5, rows, columns, false);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		try {
			//Wait for all vehicles to finish
			managerThread.join();
		} catch (InterruptedException e) {

			//Fall through
		}
		//Get array of vehicles and times to move through the grid
		Vehicle[] vehicles = manager.getVehicles();
		int[] times = manager.getVehicleTimes();

		//Counters
		int vehiclesOut = 0;
		int timesAccountedFor = 0;

		//Check how many vehicles come out
		for (int i = 0; i < vehicles.length; i++) {

			if(vehicles[i].isFinished()) vehiclesOut++;
		}

		//Check how many vehicles have logged times
		for (int i = 0; i < times.length; i++) {

			if(times[i]>0) timesAccountedFor++;
		}

		assertEquals("Same number of vehicles in as vehicles out, big grid", 20, vehiclesOut);
		assertEquals("All vehicles have logged a time to move through the grid, big grid", 20, timesAccountedFor);
	}

	/**
	 * Tests that the same number of vehicles come out the grid as goes in the grid
	 * Also tests that all vehicles have a time logged
	 * Small grid
	 */
	@Test(timeout = 10000)
	public void testSmallGridOut() {

		Grid grid = new Grid(1,1);

		//Arrays of rows and columns the vehicles will drive in
		int[] rows = {0};
		int[] columns = {0};

		//Create traffic manager threads and start them
		TrafficManager manager = new TrafficManager(grid, 5, 10, 20, 5, rows, columns, false);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		try {
			//Wait for all vehicles to finish
			managerThread.join();
		} catch (InterruptedException e) {

			//Fall through
		}
		//Get array of vehicles and times to move through the grid
		Vehicle[] vehicles = manager.getVehicles();
		int[] times = manager.getVehicleTimes();

		//Counters
		int vehiclesOut = 0;
		int timesAccountedFor = 0;

		//Check how many vehicles come out
		for (int i = 0; i < vehicles.length; i++) {

			if(vehicles[i].isFinished()) vehiclesOut++;
		}

		//Check how many vehicles have logged times
		for (int i = 0; i < times.length; i++) {

			if(times[i]>0) timesAccountedFor++;
		}

		assertEquals("Same number of vehicles in as vehicles out, small grid", 20, vehiclesOut);
		assertEquals("All vehicles have logged a time to move through the grid, small grid", 20, timesAccountedFor);
	}

	/**
	 * Tests that the same number of vehicles come out the grid as goes in the grid
	 * Also tests that all vehicles have a time logged
	 * Multiple traffic managers with vehicles going in different directions
	 */
	@Test (timeout = 10000)
	public void testMultipleManagerOut() {

		Grid grid = new Grid(10,20);

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
		TrafficManager manager1 = new TrafficManager(grid, 5, 10, 15, 10, rows1, columns1, false);
		trafficManagers[0] = manager1;
		TrafficManager manager2 = new TrafficManager(grid, 10, 20, 15, 10, rows2, columns2, true);
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
		
		//Get vehicle arrays
		Vehicle[] vehicles1 = manager1.getVehicles();
		Vehicle[] vehicles2 = manager1.getVehicles();

		//Get time arrays
		int[] times1 = manager2.getVehicleTimes();
		int[] times2 = manager2.getVehicleTimes();

		//counters
		int vehiclesOut1 = 0;
		int timesAccountedFor1 = 0;
		
		int vehiclesOut2 = 0;
		int timesAccountedFor2 = 0;

		//Check vehicles out and numbered of vehicles that have registered a time
		for (int i = 0; i < vehicles1.length; i++) {

			if(vehicles1[i].isFinished()) vehiclesOut1++;
		}

		for (int i = 0; i < times1.length; i++) {

			if(times1[i]>0) timesAccountedFor1++;
		}
		for (int i = 0; i < vehicles2.length; i++) {

			if(vehicles2[i].isFinished()) vehiclesOut2++;
		}

		for (int i = 0; i < times2.length; i++) {

			if(times2[i]>0) timesAccountedFor2++;
		}


		assertEquals("Same number of vehicles in as vehicles out, thread 1", 15, vehiclesOut1);
		assertEquals("All vehicles have logged a time to move through the grid, thread 1", 15, timesAccountedFor1);
		assertEquals("Same number of vehicles in as vehicles out, thread 2", 15, vehiclesOut2);
		assertEquals("All vehicles have logged a time to move through the grid, thread 2", 15, timesAccountedFor2);
	}
	
	/**
	 * Test that the time to move through the grid is within 5 percent of what it should be
	 */
	@Test (timeout = 10000)
	public void testLoggedTime() {
		
		Grid grid = new Grid(10,10);

		//Arrays of rows and columns the vehicles will drive in
		int[] rows = {0,1,2,3,4,5,6,7,8,9};
		int[] columns = {0,1,2,3,4,5,6,7,8,9,10};

		//Create traffic manager thread and start it
		TrafficManager manager = new TrafficManager(grid, 10, 11, 1, 5, rows, columns, false);
		Thread managerThread = new Thread(manager);
		managerThread.start();
		try {
			//Wait for the vehicle to finish
			managerThread.join();
		} catch (InterruptedException e) {

			//Fall through
		}
		
		//Get array of times
		int[] times = manager.getVehicleTimes();
		
		//Get predicted time (Size of grid x sleep period)
		int predictedTime = (10*10);
		//5 percent of the predicted time
		int fivePercent = predictedTime/20;
		boolean accurate = false;

		//Is the actual time within 5 percent of the predicted time
		if (times[0] > predictedTime - fivePercent && times[0] < predictedTime + fivePercent) accurate = true;

		assertTrue("Time is accurate to within 5 percent", accurate);
	}
	
	/**
	 * Test that the statistics class prints the correct statistics
	 * Should print max 4s, min 1s, mean 3s and variance 1250
	 */
	@Test
	public void testStatistics() {
		
		//Test array
		int[] testTimes = {1000,2000,3000,4000};
		//Print statistics to log for comparison
		Statistics statistics = new Statistics();
		statistics.printStats(testTimes, "test");
	}

	/**
	 * Test what happens when two vehicle collide head on
	 * In the current implementation this should cause a deadlock (timeout exception)
	 */
	@Test (timeout = 10000)
	public void testDeadlock() {

		Grid grid = new Grid(10,20);
		
		//Vehicles in same lane and moving in opposite directions
		Vehicle vehicle = new Vehicle(grid, 1, 10, 0, 0, true);
		Vehicle vehicle2 = new Vehicle(grid, 1, 10, 0, 0, false);

		//Create and start threads
		Thread thread = new Thread(vehicle);
		Thread thread2 = new Thread(vehicle2);
		thread.start();
		thread2.start();

		//What for vehicles to finish
		try {
			thread.join();
			thread2.join();
		} catch (InterruptedException e) {

			//Fall through
		}
		assertFalse("Vehicle deadlock test", vehicle.isFinished());
		assertFalse("Vehicle deadlock test", vehicle2.isFinished());
	}
}
