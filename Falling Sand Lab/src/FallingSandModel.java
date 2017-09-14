import java.util.Random;

public class FallingSandModel {

		public static final int MODEL_SIZE = 200;
		
		// integer value to represent empty space
		public static final int EMPTY = 0;
		
		// integer value to represent steel particle
		public static final int METAL = 1;

		// integer value to represent sand particle
		public static final int SAND = 2;
		
		// integer value to represent water particle
		public static final int WATER = 3;


		// two dimensional array to hold 
		private int[][] grid;

		// variable for mode
		private int mode;

		public FallingSandModel() {
			grid = new int[MODEL_SIZE][MODEL_SIZE];
			mode = METAL;
		}
		
		
		public int getMode() {
			// TODO Auto-generated method stub
			return mode;
		}
		
		
		public void setMode(int set) {
			// TODO Auto-generated method stub
			mode = set;
		}
		
		
		public int get(int x, int y) {
			// TODO Auto-generated method stub
			return grid[x][y];
		}

		
		public boolean inGrid(int x, int y) {
			// TODO Auto-generated method stub
			
			return x >= 0 && x < MODEL_SIZE && y >= 0 && y < MODEL_SIZE;
		}

		
		public void placeParticle(int x, int y) {
			// TODO Auto-generated method stub
			grid[x][y] = mode;
		}


		public void step() {
			// TODO Auto-generated method stub
			
		}

	
		public void sandStep(int x, int y) {
			// Sand moves down through empty space
			// Sand moving down into water switches places with the water			
			if (inGrid(x,y-1)) {
				if (grid[x][y-1] == EMPTY) {
					grid[x][y-1] = SAND;
					grid[x][y] = EMPTY;
				}
				
				if (grid[x][y-1] == WATER) {
					grid[x][y] = WATER;
					grid[x][y-1] = SAND;
				}
			}
			
		}

		public void waterStep(int x, int y) {
			// Water moves randomly left, right, or down
			// Cannot displace sand or metal
			
			// Create random directions such that 
			// down = 0, left = 1, right = 2
			Random rand = new Random();
			int direction = rand.nextInt(3);
			
			// Water moves downward
			if (direction == 0 && inGrid(x, y-1) && grid[x][y-1] == EMPTY) {
				grid[x][y-1] = WATER;
				grid[x][y] = EMPTY;
			}
			
			// Water moves left
			if (direction == 1 && inGrid(x-1,y) && grid[x-1][y] == EMPTY) {
				grid[x-1][y] = WATER;
				grid[x][y] = EMPTY;
			}
			
			// Water moves right
			if (direction == 2 && inGrid(x+1,y) && grid[x+1][y] == EMPTY) {
				grid[x+1][y] = WATER;
				grid[x][y] = EMPTY;
			}
			
			
		}
		
		
		
		
		
}
