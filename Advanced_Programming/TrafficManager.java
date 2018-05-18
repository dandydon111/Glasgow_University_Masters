import java.util.Random;

public class TrafficManager implements Runnable {

	private Grid grid;

	//The lanes which the vehicles can drive in
	private int[] rows;
	private int[] columns;

	//Although labelled speed, maxSpeed and minSpeed actually refer to time between vehicles moving squares in the grid
	//Therefore the larger maxSpeed the slower the vehicle will go and the smaller minSpeed the faster the vehicle will go
	private int minSpeed;
	private int maxSpeed;
	
	private int numVehicles;
	private int timeBetweenVehicles;	
	private boolean reverse;
	private Vehicle[] vehicles;

	public TrafficManager(Grid grid, int minSpeed, int maxSpeed, int numVehicles, int timeBetweenVehicles, int[] rows, int[] columns, boolean reverse) {

		this.grid = grid;
		this.minSpeed = minSpeed;
		this.maxSpeed = maxSpeed;
		this.numVehicles = numVehicles;
		this.timeBetweenVehicles = timeBetweenVehicles;
		this.rows = rows;
		this.columns = columns;
		this.reverse = reverse;
		vehicles = new Vehicle[numVehicles];
	}

	public void run() {

		//Create array of threads
		Thread[] vehicleThread = new Thread[numVehicles];

		//Create the specified number of vehicles
		for (int i = 0; i < numVehicles; i++) {

			//Get a random direction for the vehicle to travel in
			int direction = getRandomDirection();

			//Create a vehicle object and add to the array of vehicles
			Vehicle v = new Vehicle(grid, direction, getRandomSpeed(), getStartingRow(direction), getStartingColumn(direction), reverse);
			vehicles[i] = v;
			//Start the vehicle object running in its own thread
			vehicleThread[i] = new Thread(v); 
			vehicleThread[i].start();
			
			//Wait the specified time before creating another vehicle
			try {
				Thread.sleep(timeBetweenVehicles);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		//Wait for all vehicles to get to other side of the grid before ending thread
		for (int i = 0; i < numVehicles; i++) {

			//join the threads to wait for them to finish
			try {
				vehicleThread[i].join();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns an array of all the vehicles that the traffic manager creates
	 * Used primarily for testing in this implementation
	 */
	public Vehicle[] getVehicles() {
		
		return vehicles;
	}

	/**
	 * Generate a random speed for the vehicle to travel at
	 * @return the speed of the vehicle
	 */
	private int getRandomSpeed() {

		//Randomly generate the speed of the vehicle in milliseconds
		Random rand = new Random();
		return rand.nextInt(maxSpeed - minSpeed) + minSpeed;
	}

	/**
	 * Generate a random direction for the vehicle to travel in
	 * 1 for horizontal and 0 for vertical
	 * @return random direction
	 */
	private int getRandomDirection() {

		//Randomly generate the direction of the vehicle
		//1 means vehicles will move to the east, 0 means vehicles will move to the south
		Random rand2 = new Random();
		return rand2.nextInt(2);
	}

	/**
	 * Generate a random starting row for the vehicles travelling horizontally
	 * Sets the starting row to 0 for vehicles travelling vertically
	 * @param the vehicle's direction
	 * @return the starting row
	 */
	private int getStartingRow(int direction) {

		//The vehicle is moving horizontally
		if (direction == 1) {

			//Randomly select starting row from the available lanes
			int rand = new Random().nextInt(rows.length);
			return rows[rand];
		}
		//The vehicle is moving vertically
		else {

			//Set the row index as 0
			return 0;
		}
	}

	/**
	 * Generate a random starting column for vehicles travelling vertically
	 * Sets the starting column to 0 for vehicles travelling horizontally
	 * @param the vehicle's direction
	 * @return the starting column
	 */
	private int getStartingColumn(int direction) {

		//The vehicle is moving vertically
		if (direction == 0) {

			//Randomly select the starting column from the available lanes
			int rand = new Random().nextInt(columns.length);
			return columns[rand];
		}
		//The vehicle is moving horizontally
		else {

			//Set the column index as 0
			return 0;
		}
	}

	/**
	 * Get the time to travel through the grid of every vehicle the traffic manager generates
	 * @return array of vehicle times
	 */
	public int[] getVehicleTimes() {

		//Array to store the times
		int[] vehicleTimes = new int[numVehicles];
		
		//Get the total time to cross the grid for each vehicle
		for (int i = 0; i < numVehicles; i++) {

			//Add time to the array
			vehicleTimes[i] = vehicles[i].getTotalTime();
		}

		return vehicleTimes;
	}
}
