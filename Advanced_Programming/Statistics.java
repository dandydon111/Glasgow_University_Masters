
public class Statistics {

	/**
	 * Prints the statistics for each traffic manager thread to the console
	 */
	public void printStats(int[] times, String threadName) {

		//Print the statistics to the console, converting from milliseconds to seconds
		System.out.println("--------" + threadName + "--------");
		System.out.println("Maximum time for a vehicle to travel through the grid: " + Math.round((double) getMax(times)/1000) + "s");
		System.out.println("Minimum time for a vehicle to travel through the grid: " + Math.round((double) getMin(times)/1000) + "s");
		System.out.println("The mean time for a vehicle to travel through the grid: " + Math.round((double) getMean(times)/1000) + "s");
		System.out.println("The variance in time is: " + Math.round((double) getVariance(times)/1000));
	}

	/**
	 * Get max time taken for a vehicle to move through the grid
	 * @param array of all the times to move through the grid
	 * @return the maximum time it took to move through the grid
	 */
	private int getMax(int[] times) {

		//Initially set max to the first value
		int max = times[0];

		//Loop through all times in the array
		for (int i = 0; i < times.length; i++) {

			//Find max
			if (times[i]>max) {

				//Update max
				max = times[i];
			}
		}

		return max;
		//Print the max time to the console

	}

	/**
	 * Get the minimum time it taken for a vehicle to move through the grid
	 * @param array of all the times to move through the grid
	 * @return the minimum time it took to move through the grid
	 */
	private int getMin(int[] times) {

		//Initially set min to the first value
		int min = times[0];

		//Loop through all times in the array
		for (int i = 0; i < times.length; i++) {

			//Set the minimum
			if (times[i]<min) {

				min = times[i];
			}
		}

		return min;
	}

	/**
	 * Get the variance in the times for all the vehicles
	 * @param array of all the times to move through the grid
	 * @return the variance in time
	 */
	private int getVariance (int[] times) {

		//Get the mean time of all the vehicles
		int mean = getMean(times);
		//Variable to store the sum of squares
		int sumOfSquares = 0;

		//Loop through all times in the array
		for (int i = 0; i < times.length; i++) {

			//Calculate the sum of squares
			sumOfSquares += Math.pow((times[i] - mean), 2);
		}

		//Calculate and return the variance 
		int variance = sumOfSquares/times.length;
		return variance;
	}

	/**
	 * The mean time for a vehicle to move through the grid
	 * @param array of all the times to move through the grid
	 * @return the mean time to move through the grid
	 */
	private int getMean(int[] times) {

		//Initialise mean and sum to 0
		int mean = 0;
		int sum = 0;

		//Loop through all the times
		for (int i = 0; i < times.length; i++) {

			//Add to the sum
			sum += times[i];
		}

		//Calculate and return the mean
		mean = sum/times.length;
		return mean;
	}
}
