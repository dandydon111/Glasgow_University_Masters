
public class Vehicle implements Runnable {

	private Grid grid; //The grid that the vehicles will enter
	
	private int speed; //How fast the vehicle will move through the grid
	private int direction; //The direction of the vehicle. 1 for horizontal, 0 for vertical
	
	//The index vehicles position in the grid
	private int col = 0;
	private int row = 0;

	private boolean finished = false; //Whether the vehicle has exited the grid
	private boolean reverse; //Whether the car is going forward or backwards
	
	private int totalTime; //The total time it takes each vehicle to move through the grid;

	public Vehicle(Grid grid, int direction, int speed, int row, int col, boolean reverse) {

		this.speed = speed;
		this.direction = direction;
		this.grid = grid;
		this.row = row;
		this.col = col;
		this.reverse = reverse;
	}
	
	/**
	 * Get the total time it took the vehicle to move through the grid
	 * @return time to move through grid
	 */
	public int getTotalTime() {
		
		return totalTime;
	}
	
	/**
	 * Returns true if the vehicle is going backwards
	 * False if the vehicle is going forwards
	 * @return Whether the vehicle is going forwards or backwards
	 */
	public boolean isReverse() {
		
		return reverse;
	}
	
	
	/**
	 * Has the vehicle finished moving through the grid
	 * @return true if the vehicle has finished moving through the grid, false if not
	 */
	public boolean isFinished() {
		
		return finished;
	}
	
	/**
	 * Called when the vehicle has reached the end of the grid
	 */
	public void setFinished() {
		
		finished = true;
	}

	/**
	 * Gets the direction of the vehicle
	 * @return 1 for horizontal and 0 for vertical
	 */
	public int getDirection() {

		return direction;
	}

	/**
	 * Gets the index of the column the vehicle is in
	 * @return the column the vehicle is in 
	 */
	public int getColumn() {

		return col;
	}

	/**
	 * Get the index of the row the vehicle is in
	 * @return the row the vehicle is in 
	 */
	public int getRow() {

		return row;
	}

	/**
	 * Set the row that the vehicle is in
	 * @param the row the vehicle is in
	 */
	public void setRow(int row) {

		this.row = row;
	}

	/**
	 * Set the column that the vehicle is in
	 * @param the column that the vehicle is in
	 */
	public void setColumn(int col) {

		this.col = col;
	}
	
	/**
	 * Decrement the vehicles column
	 */
	public void decrementColumn() {
		
		col--;
	}
	
	/**
	 * Decrement the vehicles row
	 */
	public void decrementRow() {
		
		row--;
	}

	/**
	 * Increment the vehicles column
	 */
	public void incrementColumn() {

		col++;
	}

	/**
	 * Increment the vehicles row
	 */
	public void incrementRow() {

		row++;
	}

	/**
	 * Moves the vehicle through the grid
	 */
	public void run() {
		
		//Get the time immediately before the vehicle is added to the grid
		long start = System.currentTimeMillis();

		//Add the vehicle to the grid
		grid.addToGrid(this);

		//Loop until the vehicle has moved all the way through the grid
		while (!finished) {
			
			try {
				//Pause the thread to simulate the speed of the vehicle
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				//Print the error message
				e.printStackTrace();
			}
			
			//Move the vehicle to the next space in the grid
			grid.move(this);
		}
		
		//The time to move through the grid is the difference in time from start to finish
		totalTime = (int) (System.currentTimeMillis() - start);
	}

	/**
	 * Returns a string representation of the vehicle
	 */
	public String toString() {

		//Assign a different character depending upon the direction of the vehicle
		//"o" for vehicles moving vertically and "-" for vehicles moving horizontally
		if (direction == 0) {
			return "o";
		} else {
			return "-";
		}
	}
}
