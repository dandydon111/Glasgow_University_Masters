import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Grid {


	private Vehicle[][] trafficGrid; //The grid of vehicles
	private int rows; //Number of rows in the grid
	private int columns; // Number of columns in the grid

	//Lock and condition
	private ReentrantLock vehicleLock = new ReentrantLock();
	private Condition spaceOccupied = vehicleLock.newCondition();

	public Grid (int rows, int columns) {

		this.rows = rows;
		this.columns = columns;
		trafficGrid = new Vehicle[rows][columns];
	}
	
	/**
	 * Method for testing
	 * Returns the vehicle occupying a specific space in the grid
	 */
	public Vehicle getVehicleFromGrid(int row, int col) {
		
		return trafficGrid[row][col];
	}

	/**
	 * Method to get the number of rows in the grid
	 * @return the number of rows
	 */
	public int getRows(){

		return rows;
	}

	/**
	 * Method to return the number of columns in the grid
	 * @return the number of columns
	 */
	public int getColumns() {

		return columns;
	}

	/**
	 * Adds a vehicle into the grid
	 * @param vehicle
	 */
	public void addToGrid(Vehicle vehicle) {

		//Vehicle will move east
		if (vehicle.getDirection() == 1 && !vehicle.isReverse()) {

			addToLeft(vehicle);
			//Vehicle will move west
		} else if (vehicle.getDirection() == 1 && vehicle.isReverse()) {

			addToRight(vehicle);
			//Vehicle will move south
		} else if ((vehicle.getDirection() == 0 && !vehicle.isReverse())){

			addToTop(vehicle);
			//Vehicle will north
		} else {

			addToBottom(vehicle);
		}
	}

	/**
	 * Add a vehicle to the first column of the grid
	 * @param vehicle
	 */
	private void addToLeft(Vehicle vehicle) {

		//Get the randomly assigned row
		int entry = vehicle.getRow();

		//Acquire the lock
		vehicleLock.lock();

		try {
			//While the space in the grid is occupied by another vehicle
			while (trafficGrid[entry][0] != null) {

				//Wait until signalled
				spaceOccupied.await();
			}

			//Add the vehicle to the first column and signal all other waiting vehicles
			trafficGrid[entry][0] = vehicle;
			spaceOccupied.signalAll();
		} catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}
	}

	/**
	 * Adds a vehicle to the last column of the grid
	 * @param vehicle
	 */
	private void addToRight(Vehicle vehicle) {

		//Get the randomly assigned row
		int entry = vehicle.getRow();

		//Acquire the lock
		vehicleLock.lock();

		try {
			//While the space in the grid is occupied by another vehicle
			while (trafficGrid[entry][columns-1] != null) {

				//Wait until signalled
				spaceOccupied.await();
			}

			//Add the vehicle to the last column and signal all other waiting vehicles
			trafficGrid[entry][columns-1] = vehicle;
			vehicle.setColumn(columns-1);
			spaceOccupied.signalAll();
		} catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}
	}

	/**
	 * Adds a vehicle to the top row of the grid
	 * @param vehicle
	 */
	private void addToTop(Vehicle vehicle) {

		//Get the randomly assigned column
		int entry = vehicle.getColumn();

		//Acquire the lock
		vehicleLock.lock();

		try {
			//While the space in the grid is occupied by another vehicle
			while (trafficGrid[0][entry] != null) {

				//Wait until signalled
				spaceOccupied.await();
			}

			//Add the vehicle to the first row and signal all other waiting vehicles
			trafficGrid[0][entry] = vehicle;
			spaceOccupied.signalAll();
		} catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}
	}

	/**
	 * Adds a vehicle to the bottom row of the grid
	 * @param vehicle
	 */
	private void addToBottom(Vehicle vehicle) {

		//Get the randomly assigned column
		int entry = vehicle.getColumn();

		//Acquire the lock
		vehicleLock.lock();

		try {
			//While the space in the grid is occupied by another vehicle
			while (trafficGrid[rows-1][entry] != null) {

				//Wait until signalled
				spaceOccupied.await();
			}

			//Add the vehicle to the last row and signal all other waiting vehicles
			trafficGrid[rows-1][entry] = vehicle;
			vehicle.setRow(rows-1);
			spaceOccupied.signalAll();
		} catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}
	}

	/**
	 * Moves the vehicle along one space in the grid
	 * Direction is determined by the vehicle's instance variables
	 * @param vehicle
	 */
	public void move(Vehicle vehicle) {

		if (vehicle.getDirection() == 1 && !vehicle.isReverse()) {

			moveEast(vehicle);
		} else if (vehicle.getDirection() == 1 && vehicle.isReverse()){

			moveWest(vehicle);
		} else if (vehicle.getDirection() == 0 && !vehicle.isReverse()) {

			moveSouth(vehicle);
		} else {

			moveNorth(vehicle);
		}
	}

	/**
	 * Moves the vehicle one space east in the grid
	 * @param vehicle
	 */
	private void moveEast(Vehicle vehicle) {

		//Get the row and column the vehicle currently occupies
		int vRow = vehicle.getRow();
		int vCol = vehicle.getColumn();

		try {

			//Acquire the lock
			vehicleLock.lock();

			//Check if the vehicle is in the last column
			if (vCol < columns-1) {

				//While the next space in the grid is occupied by another vehicle
				while (trafficGrid [vRow][vCol+1] != null) {

					//Wait until signalled
					spaceOccupied.await();
				}

				//Delete the vehicle from the current space 
				trafficGrid [vRow][vCol] = null;
				//Add the vehicle to the next space
				trafficGrid [vRow][vCol+1] = vehicle;
				//Increment the position record in the vehicle object
				vehicle.incrementColumn();

				//Signal all waiting vehicles
				spaceOccupied.signalAll();
			}

			else {

				//Vehicle has got to the end of the grid, remove it and end thread
				trafficGrid [vRow][vCol] = null;
				vehicle.setFinished();
				spaceOccupied.signalAll();
			}
		} catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}		
	}

	/**
	 * Moves the vehicle west one space in the grid
	 * @param vehicle
	 */
	private void moveWest(Vehicle vehicle) {

		//Get the row and column the vehicle currently occupies
		int vRow = vehicle.getRow();
		int vCol = vehicle.getColumn();

		try {

			//Acquire the lock
			vehicleLock.lock();

			//Check if the vehicle is in the first column
			if (vCol > 0) {

				//While the next space in the grid is occupied by another vehicle
				while (trafficGrid [vRow][vCol-1] != null) {

					//Wait until signalled
					spaceOccupied.await();
				}

				//Delete the vehicle from the current space 
				trafficGrid [vRow][vCol] = null;
				//Add the vehicle to the next space
				trafficGrid [vRow][vCol-1] = vehicle;
				//Decrement the position record in the vehicle object
				vehicle.decrementColumn();

				//Signal all waiting vehicles
				spaceOccupied.signalAll();
			} 
			else {

				//Vehicle has got to the end of the grid, remove it and end thread
				trafficGrid [vRow][vCol] = null;
				vehicle.setFinished();
				spaceOccupied.signalAll();
			}
		}
		catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}		
	}

	/**
	 * Moves the vehicle south one space in the grid
	 * @param vehicle
	 */
	private void moveSouth(Vehicle vehicle) {

		//Get the row and column the vehicle currently occupies
		int vRow = vehicle.getRow();
		int vCol = vehicle.getColumn();

		try {

			//Acquire the lock
			vehicleLock.lock();

			//Check if the vehicle is in the last row
			if (vRow < rows-1) {


				//While the next space in the grid is occupied by another vehicle
				while (trafficGrid [vRow+1][vCol] != null) {

					//Wait until signalled
					spaceOccupied.await();
				}

				//Delete the vehicle from the current space 
				trafficGrid [vRow][vCol] = null;
				//Add the vehicle to the next space
				trafficGrid [vRow+1][vCol] = vehicle;
				//Increment the position record in the vehicle object
				vehicle.incrementRow();

				//Signal all waiting vehicles
				spaceOccupied.signalAll();
			} 
			else {

				//Vehicle has got to the end of the grid, remove it and end thread
				trafficGrid [vRow][vCol] = null;
				vehicle.setFinished();
				spaceOccupied.signalAll();
			}
		}
		catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}
	}

	/**
	 * Moves the vehicle north one space in the grid
	 * @param vehicle
	 */
	private void moveNorth(Vehicle vehicle) {

		//Get the row and column the vehicle currently occupies
		int vRow = vehicle.getRow();
		int vCol = vehicle.getColumn();




		try {

			//Acquire the lock
			vehicleLock.lock();

			//Check if the vehicle is in the first row
			if (vRow > 0) {

				//While the next space in the grid is occupied by another vehicle
				while (trafficGrid [vRow-1][vCol] != null) {

					//Wait until signalled
					spaceOccupied.await();
				}

				//Delete the vehicle from the current space 
				trafficGrid [vRow][vCol] = null;
				//Add the vehicle to the next space
				trafficGrid [vRow-1][vCol] = vehicle;
				//Increment the position record in the vehicle object
				vehicle.decrementRow();

				//Signal all waiting vehicles
				spaceOccupied.signalAll();
			}

			else {

				//Vehicle has got to the end of the grid, remove it and end thread
				trafficGrid [vRow][vCol] = null;
				vehicle.setFinished();
				spaceOccupied.signalAll();
			}
		}
		catch (InterruptedException e) {

			//Display error if interrupted
			e.printStackTrace();
		} finally {

			//Ensure that the lock is released
			vehicleLock.unlock();
		}
	}

	/**
	 * Return a string representation of the current state of the grid
	 */
	public String toString() {

		//String to store the grid
		String gridString = "";

		//Loop through the whole array
		for (int i=0; i<rows; i++) {

			for (int j=0; j<columns; j++) {

				//Add the left hand boundary to the grid
				if (j== 0) {

					gridString += "|";
				} 

				//Add an empty space if there isn't a vehicle
				if (trafficGrid[i][j]==null) {

					gridString += " |";
				}
				//Print the vehicle if there is one in the square
				else {

					gridString += trafficGrid[i][j] + "|";
				}

				//End of the line, add a return
				if (j==columns-1) {

					gridString += System.lineSeparator();
				}
			}
		}

		return gridString;
	}
}
