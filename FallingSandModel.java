// Falling Sand Model by Journie Ma-Johnson and Maxwell Levin
public class FallingSandModel {
	public static final int MODEL_SIZE = 100;
	public static final int METAL = 1;
	public static final int EMPTY = 0;
	public static final int SAND = 2;
	public static final int WATER = 3;
	public static final int MERCURY = 4; // Original particle. Moves like water,
											// displaces water, blocks sand
											// water and obsidian, and is melted
											// by lava
	public static final int LAVA = 5; // original particle. moves like water,
										// becomes obsidian upon contact with
										// water, melts metal and mercury, is
										// blocked by sand and obsidian
	public static final int OBSIDIAN = 6; // original particle. cannot be drawn
											// with and only appears when water
											// meets lava. displaces water,
											// blocks lava

	private int[][] grid;
	private int mode;

	public FallingSandModel() {
		grid = new int[MODEL_SIZE][MODEL_SIZE];
		mode = METAL;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int x) {
		mode = x;
	}

	public int get(int x, int y) {
		return grid[x][y];
	}

	public boolean inGrid(int x, int y) {
		return x >= 0 && x < MODEL_SIZE && y >= 0 && y < MODEL_SIZE;
	}

	public void placeParticle(int x, int y) {
		grid[x][y] = mode;
	}

	public void step() {
		// pick a random spot on the grid and perform the appropriate simulation for the
		// particle there
		int row = StdRandom.uniform(MODEL_SIZE);
		int column = StdRandom.uniform(MODEL_SIZE);
		if (grid[row][column] > 0) {
			if (grid[row][column] == SAND) {
				sandStep(row, column);
			}

			if (grid[row][column] == WATER) {
				waterStep(row, column);
			}
			if (grid[row][column] == MERCURY) {
				mercuryStep(row, column);
			}
			if (grid[row][column] == LAVA) {
				lavaStep(row, column);
			}
			if (grid[row][column] == OBSIDIAN) {
				obsidianStep(row, column);
			}
		}
	}

	public void sandStep(int x, int y) {
		// Simulate sand
		if (inGrid(x, y - 1)) {
			if (grid[x][y - 1] == EMPTY) {
				grid[x][y - 1] = SAND;
				grid[x][y] = EMPTY;
			}
			if (grid[x][y - 1] == WATER) {
				grid[x][y - 1] = SAND;
				grid[x][y] = WATER;
			}
			if (grid[x][y - 1] == LAVA) {
				grid[x][y - 1] = SAND;
				grid[x][y] = LAVA;
			}
		}

	}

	public void waterStep(int x, int y) {
		// simulate water
		int direction = StdRandom.uniform(3);
		// down = 0, left=1, right=2
		// down
		if (direction == 0 && inGrid(x, y - 1)) {
			if (grid[x][y - 1] == EMPTY) {
				grid[x][y - 1] = WATER;
				grid[x][y] = EMPTY;
			}
			if (grid[x][y - 1] == LAVA) {
				grid[x][y - 1] = OBSIDIAN;
				grid[x][y] = EMPTY;
			}

		}
		// left
		if (direction == 1 && inGrid(x - 1, y)) {
			if (grid[x - 1][y] == EMPTY) {
				grid[x - 1][y] = WATER;
				grid[x][y] = EMPTY;
			}
			if (grid[x - 1][y] == LAVA) {
				grid[x - 1][y] = OBSIDIAN;
				grid[x][y] = EMPTY;
			}

		}
		// right
		if (direction == 2 && inGrid(x + 1, y)) {
			if (grid[x + 1][y] == EMPTY) {
				grid[x + 1][y] = WATER;
				grid[x][y] = EMPTY;
			}
			if (grid[x + 1][y] == LAVA) {
				grid[x + 1][y] = OBSIDIAN;
				grid[x][y] = EMPTY;
			}
		}

	}

	public void mercuryStep(int x, int y) {
		// simulate mercury
		int direction = StdRandom.uniform(3);
		// mercury moves down
		if (direction == 0 && inGrid(x, y - 1)) {
			if (grid[x][y - 1] == EMPTY) {
				grid[x][y - 1] = MERCURY;
				grid[x][y] = EMPTY;
			}

			if (grid[x][y - 1] == WATER) {
				grid[x][y - 1] = MERCURY;
				grid[x][y] = WATER;
			}
			if (grid[x][y - 1] == LAVA) {
				grid[x][y] = EMPTY;
			}
		}

		// mercury moves left
		if (direction == 1 && inGrid(x - 1, y)) {
			if (grid[x - 1][y] == EMPTY) {
				grid[x - 1][y] = MERCURY;
				grid[x][y] = EMPTY;
			}

			if (grid[x - 1][y] == WATER) {
				grid[x - 1][y] = MERCURY;
				grid[x][y] = WATER;
			}
			if (grid[x - 1][y] == LAVA) {
				grid[x][y] = EMPTY;
			}
		}

		// mercury moves right
		if (direction == 2 && inGrid(x + 1, y)) {
			if (grid[x + 1][y] == EMPTY) {
				grid[x + 1][y] = MERCURY;
				grid[x][y] = EMPTY;
			}

			if (grid[x + 1][y] == WATER) {
				grid[x + 1][y] = MERCURY;
				grid[x][y] = WATER;
			}
			if (grid[x + 1][y] == LAVA) {
				grid[x][y] = EMPTY;
			}
		}

	}

	// lava: melts metal, blocked by sand, turns into obsidian upon contact with
	// water, moves like water
	public void lavaStep(int x, int y) {
		int direction = StdRandom.uniform(3);
		// down
		if (direction == 0 && inGrid(x, y - 1)) {
			if (grid[x][y - 1] == EMPTY) {
				grid[x][y - 1] = LAVA;
				grid[x][y] = EMPTY;
			}

			if (grid[x][y - 1] == WATER) {
				grid[x][y - 1] = OBSIDIAN;
				grid[x][y] = EMPTY;
			}
			if (grid[x][y - 1] == METAL) {
				grid[x][y - 1] = LAVA;
				grid[x][y] = EMPTY;
			}
			if (grid[x][y - 1] == MERCURY) {
				grid[x][y - 1] = LAVA;
				grid[x][y] = EMPTY;
			}
		}
		// left
		if (direction == 1 && inGrid(x - 1, y)) {
			if (grid[x - 1][y] == EMPTY) {
				grid[x - 1][y] = LAVA;
				grid[x][y] = EMPTY;
			}

			if (grid[x - 1][y] == WATER) {
				grid[x - 1][y] = OBSIDIAN;
				grid[x][y] = EMPTY;
			}
			if (grid[x - 1][y] == METAL) {
				grid[x - 1][y] = LAVA;
				grid[x][y] = EMPTY;
			}
			if (grid[x - 1][y] == MERCURY) {
				grid[x - 1][y] = LAVA;
				grid[x][y] = EMPTY;
			}
		}
		// right
		if (direction == 2 && inGrid(x + 1, y)) {
			if (grid[x + 1][y] == EMPTY) {
				grid[x + 1][y] = LAVA;
				grid[x][y] = EMPTY;
			}

			if (grid[x + 1][y] == WATER) {
				grid[x + 1][y] = OBSIDIAN;
				grid[x][y] = EMPTY;
			}
			if (grid[x + 1][y] == METAL) {
				grid[x + 1][y] = LAVA;
				grid[x][y] = EMPTY;
			}
			if (grid[x + 1][y] == MERCURY) {
				grid[x + 1][y] = LAVA;
				grid[x][y] = EMPTY;
			}
		}
	}

	public void obsidianStep(int x, int y) {
		// only shows up when water meets lava; behaves like sand
		if (inGrid(x, y - 1)) {
			if (grid[x][y - 1] == EMPTY) {
				grid[x][y - 1] = OBSIDIAN;
				grid[x][y] = EMPTY;
			}
			if (grid[x][y - 1] == WATER) {
				grid[x][y - 1] = OBSIDIAN;
				grid[x][y] = WATER;
			}

		}
	}
}
