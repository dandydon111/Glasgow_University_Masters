
public class Repainter implements Runnable {
	
	//The grid which the vehicles are driving in
	private Grid grid;
	
	public Repainter(Grid grid) {
		
		this.grid = grid;
	}
	
	/**
	 * Repaints the traffic grid until the threads interrupt method is called
	 */
	public void run() {
		
		//Repaint the grid until all vehicles have moved through it
		for (;;){
			
			//Print the grid
			System.out.println(grid);
			try {
				//Sleep and arbitrary amount of time
				Thread.sleep(20);
			} catch (InterruptedException e) {
				
				//Break loop when interrupted method has been called
				break;
			}
		}
	}
}
