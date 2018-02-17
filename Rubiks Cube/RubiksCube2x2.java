import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Maxwell Levin
 * @author Journie Ma-Johnson
 */

public class RubiksCube2x2 {

	/** Represent colors by integer values for easy comparisons. */
	private static final int WHITE = 0;
	private static final int GREEN = 1;
	private static final int RED = 2;
	private static final int BLUE = 3;
	private static final int ORANGE = 4;
	private static final int YELLOW = 5;

	private static final Color[] COLORS = { StdDraw.WHITE, Color.getHSBColor(0.412f, 0.9f, 0.72f), StdDraw.RED,
			StdDraw.BLUE, StdDraw.PRINCETON_ORANGE, StdDraw.YELLOW };

	/** Denote each side by the color of its. */
	public int[][] whiteSide;
	public int[][] greenSide;
	public int[][] redSide;
	public int[][] blueSide;
	public int[][] orangeSide;
	public int[][] yellowSide;

	/** Contains number of moves used to solve the cube */
	public long numMoves;

	/** Contains the time elapsed to solve the cube */
	public double timeElapsed;

	/** Constructor instantiates cubie positions, and generates graphics. */
	public RubiksCube2x2() {
		numMoves = 0;
		timeElapsed = 0;
		instantiateCubiePositions();
		generateInitialGraphics();
	}

	/** Performs random rotations until cube is solved */
	public void bogoSolve() {
		Stopwatch time = new Stopwatch();
		while (!isSolved()) {
			int rot = StdRandom.uniform(0, 12);
			performRotation(rot);
			numMoves++;
		}
		timeElapsed = time.elapsedTime();
	}

	/**
	 * Solves the cube via brute force breadth-first search. Faster than depth-first
	 * in most cases.
	 */
	// TODO: Implement caching
	public void breadthBruteForce() {
		Stopwatch time = new Stopwatch();
		if (isSolved()) {
			return;
		}
		int depth = 14;
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for (int d = 1; d <= depth; d++) {
			moves = depthBruteForce(d, -1);
			if (moves != null) {
				break;
			}
		}
		if (moves == null) {
			return;
		}
		numMoves = moves.size();
		timeElapsed = time.elapsedTime();
		for (int i = moves.size() - 1; i >= 0; i--) {
			performRotation(moves.get(i));
			drawRubiksCube();
			StdOut.printf(moves.get(i) + " ");
			StdDraw.pause(1000);

		}
		StdOut.println();
	}

	/**
	 * Solves the cube via brute force depth-first search. Note: too slow to be used
	 * reliably for more than a 4 - move scramble Note: the maximum depth for a
	 * 2x2x2 should be 14.
	 */
	public void depthBruteForce() {
		Stopwatch time = new Stopwatch();
		if (isSolved()) {
			return;
		}
		RubiksCube2x2 copy = copy();
		int depth = 2;
		ArrayList<Integer> moves = depthBruteForce(depth, copy, -1);
		if (moves == null) {
			return;
		}
		numMoves = moves.size();
		timeElapsed = time.elapsedTime();
		for (int i = moves.size() - 1; i >= 0; i--) {
			performRotation(moves.get(i));
			drawRubiksCube();
			StdDraw.pause(1000);
			StdOut.printf(moves.get(i) + " "); // move printing moves to new method
		}
		StdOut.println();
	}

	/**
	 * Recursively finds a solution to the Rubik's Cube via brute force .
	 * 
	 * @return the first list of moves that is a solution.
	 */
	private ArrayList<Integer> depthBruteForce(int depth, RubiksCube2x2 cube, int rot) {
		if (depth == 0 && !cube.isSolved()) {
			return null;
		}
		for (int i = 0; i < 12; i++) {
			if (cube.isSolved()) {
				ArrayList<Integer> base = new ArrayList<Integer>();
				base.add(rot);
				return base;
			}
			RubiksCube2x2 copy = cube.copy();
			copy.performRotation(i);
			ArrayList<Integer> moves = depthBruteForce(depth - 1, copy, i);
			if (moves != null) {
				if (rot != -1) {
					moves.add(rot);
				}
				return moves;
			}
		}
		return null;
	}

	/**
	 * Recursively finds a solution to the Rubik's Cube via brute force .
	 * 
	 * @return the first list of moves that is a solution.
	 */
	private ArrayList<Integer> depthBruteForce(int depth, int rot) {
		if (depth == 0 && !isSolved()) {
			return null;
		}
		for (int i = 0; i < 12; i++) {
			if (isSolved()) {
				ArrayList<Integer> base = new ArrayList<Integer>();
				base.add(rot);
				return base;
			}
			performRotation(i);
			ArrayList<Integer> moves = depthBruteForce(depth - 1, i);
			performRotation((i + 6) % 12);
			if (moves != null) {
				if (rot != -1) {
					moves.add(rot);
				}
				return moves;
			}

		}
		return null;
	}

	/**
	 * Performs a given rotation 0 = right, 1 = left, etc... NOTE: inverses are
	 * shifted up by 6, i.e. iRight = 6, iLeft = 7, etc...
	 */
	public void performRotation(int rot) {
		if (rot == 0)
			rightSideRotate();
		else if (rot == 1)
			leftSideRotate();
		else if (rot == 2)
			backSideRotate();
		else if (rot == 3)
			downSideRotate();
		else if (rot == 4)
			frontSideRotate();
		else if (rot == 5)
			upSideRotate();
		else if (rot == 6)
			iRightSideRotate();
		else if (rot == 7)
			iLeftSideRotate();
		else if (rot == 8)
			iBackSideRotate();
		else if (rot == 9)
			iDownSideRotate();
		else if (rot == 10)
			iFrontSideRotate();
		else if (rot == 11)
			iUpSideRotate();
	}

	/** Return true if the cube is solved, false otherwise */
	public boolean isSolved() {

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (redSide[i][j] != RED || greenSide[i][j] != GREEN || blueSide[i][j] != BLUE
						|| orangeSide[i][j] != ORANGE || whiteSide[i][j] != WHITE || yellowSide[i][j] != YELLOW) {
					return false;
				}
			}
		}
		return true;
	}

	/** Return true if the face is the same color as its given color */
	public boolean isSolved(int[][] face, int color) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (face[i][j] != color) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Return true if this cube is equal to the given cube, false otherwise
	 */
	public boolean equals(RubiksCube2x2 c) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (c.redSide[i][j] != redSide[i][j] || c.greenSide[i][j] != greenSide[i][j]
						|| c.blueSide[i][j] != blueSide[i][j] || c.orangeSide[i][j] != orangeSide[i][j]
						|| c.whiteSide[i][j] != whiteSide[i][j] || c.yellowSide[i][j] != yellowSide[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/** Returns a copy of the cube. */
	public RubiksCube2x2 copy() {
		RubiksCube2x2 cubeCopy = new RubiksCube2x2();
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				cubeCopy.redSide[i][j] = redSide[i][j];
				cubeCopy.blueSide[i][j] = blueSide[i][j];
				cubeCopy.whiteSide[i][j] = whiteSide[i][j];
				cubeCopy.greenSide[i][j] = greenSide[i][j];
				cubeCopy.yellowSide[i][j] = yellowSide[i][j];
				cubeCopy.orangeSide[i][j] = orangeSide[i][j];
			}
		}
		return cubeCopy;
	}

	/**
	 * Set each side to the color of its central cubie to ensure valid starting
	 * point.
	 */
	private void instantiateCubiePositions() {
		whiteSide = new int[2][2];
		greenSide = new int[2][2];
		redSide = new int[2][2];
		blueSide = new int[2][2];
		orangeSide = new int[2][2];
		yellowSide = new int[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				whiteSide[i][j] = WHITE;
				greenSide[i][j] = GREEN;
				redSide[i][j] = RED;
				blueSide[i][j] = BLUE;
				orangeSide[i][j] = ORANGE;
				yellowSide[i][j] = YELLOW;
			}
		}
	}

	/**
	 * Performs a number of random cube rotations to create a valid random 2x2
	 * Rubik's Cube.
	 */
	public void randomizeCubiePositions(int num) {
		for (int i = 0; i < num; i++) {
			int rotation = StdRandom.uniform(12);
			if (rotation == 0)
				rightSideRotate();
			else if (rotation == 1)
				iRightSideRotate();
			else if (rotation == 2)
				leftSideRotate();
			else if (rotation == 3)
				iLeftSideRotate();
			else if (rotation == 4)
				backSideRotate();
			else if (rotation == 5)
				iBackSideRotate();
			else if (rotation == 6)
				downSideRotate();
			else if (rotation == 7)
				iDownSideRotate();
			else if (rotation == 8)
				frontSideRotate();
			else if (rotation == 9)
				iFrontSideRotate();
			else if (rotation == 10)
				upSideRotate();
			else if (rotation == 11)
				iUpSideRotate();
			else {
				StdOut.println("Error! Failed to generate random number!");
				System.exit(-1);
			}
		}
	}

	/** Draw the screen, draw the positions of the cubies. */
	private void generateInitialGraphics() {
		StdDraw.enableDoubleBuffering();
		StdDraw.clear(StdDraw.BLACK);
		drawRubiksCube();
	}

	/** Draws the Rubik's Cube to screen. */
	public void drawRubiksCube() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				StdDraw.setPenColor(COLORS[greenSide[i][j]]);
				StdDraw.filledSquare(.125 / 2 + .25 * i / 2, 0.5 - .125 / 2 + .25 * j / 2, 0.125 / 2);
				StdDraw.setPenColor(COLORS[redSide[i][j]]);
				StdDraw.filledSquare(.375 - .125 / 2 + .25 * i / 2, 0.5 - .125 / 2 + .25 * j / 2, 0.125 / 2);
				StdDraw.setPenColor(COLORS[blueSide[i][j]]);
				StdDraw.filledSquare(.625 - .125 / 2 + .25 * i / 2, 0.5 - .125 / 2 + .25 * j / 2, 0.125 / 2);
				StdDraw.setPenColor(COLORS[orangeSide[i][j]]);
				StdDraw.filledSquare(.875 - .125 / 2 + .25 * i / 2, 0.5 - .125 / 2 + .25 * j / 2, 0.125 / 2);
				StdDraw.setPenColor(COLORS[whiteSide[i][j]]);
				StdDraw.filledSquare(.375 - .125 / 2 + .25 * i / 2, 0.75 - .125 / 2 + .25 * j / 2, 0.125 / 2);
				StdDraw.setPenColor(COLORS[yellowSide[i][j]]);
				StdDraw.filledSquare(.375 - .125 / 2 + .25 * i / 2, 0.25 - .125 / 2 + .25 * j / 2, 0.125 / 2);
			}
		}
		StdDraw.show();
	}

	/** Rotates a side clockwise */
	public void rotateSideCounterClockwise(int[][] side) {
		int temp = side[0][0];
		side[0][0] = side[0][1];
		side[0][1] = side[1][1];
		side[1][1] = side[1][0];
		side[1][0] = temp;
	}

	/** Rotates a side counter-clockwise */
	public void rotateSideClockwise(int[][] side) {
		int temp = side[0][0];
		side[0][0] = side[1][0];
		side[1][0] = side[1][1];
		side[1][1] = side[0][1];
		side[0][1] = temp;
	}

	/**
	 * Rotate the right side of the cube up BlueSide rotates clockwise R red -> R
	 * white -> L orange -> R yellow -> R red
	 */
	public void rightSideRotate() {
		rotateSideClockwise(blueSide);
		int[] tempRed = { redSide[1][0], redSide[1][1] };
		for (int j = 0; j < 2; j++) {
			redSide[1][j] = yellowSide[1][j];
			yellowSide[1][j] = orangeSide[0][(j + 1) % 2];
			orangeSide[0][(j + 1) % 2] = whiteSide[1][j];
			whiteSide[1][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the right side of the cube down BlueSide rotates counterclockwise R
	 * red -> R yellow -> L orange -> R white -> R red
	 */
	public void iRightSideRotate() {
		rotateSideCounterClockwise(blueSide);
		int[] tempRed = { redSide[1][0], redSide[1][1] };
		for (int j = 0; j < 2; j++) {
			redSide[1][j] = whiteSide[1][j];
			whiteSide[1][j] = orangeSide[0][(j + 1) % 2];
			orangeSide[0][(j + 1) % 2] = yellowSide[1][j];
			yellowSide[1][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the left side of the cube down GreenSide rotates clockwise L red -> L
	 * yellow -> R orange -> L white -> L red
	 */
	public void leftSideRotate() {
		rotateSideClockwise(greenSide);
		int[] tempRed = { redSide[0][0], redSide[0][1] };
		for (int j = 0; j < 2; j++) {
			redSide[0][j] = whiteSide[0][j];
			whiteSide[0][j] = orangeSide[1][(j + 1) % 2];
			orangeSide[1][(j + 1) % 2] = yellowSide[0][j];
			yellowSide[0][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the left side of the cube up GreenSide rotates counterclockwise L red
	 * -> L white -> R orange -> L yellow -> L red
	 */
	public void iLeftSideRotate() {
		rotateSideCounterClockwise(greenSide);
		int[] tempRed = { redSide[0][0], redSide[0][1] };
		for (int j = 0; j < 2; j++) {
			redSide[0][j] = yellowSide[0][j];
			yellowSide[0][j] = orangeSide[1][(j + 1) % 2];
			orangeSide[1][(j + 1) % 2] = whiteSide[0][j];
			whiteSide[0][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the back side of the cube left Orange side rotates clockwise T white
	 * -> L green -> B yellow -> R blue -> T white
	 */
	public void backSideRotate() {
		rotateSideClockwise(orangeSide);
		int[] tempWhite = { whiteSide[0][1], whiteSide[1][1] };
		for (int j = 0; j < 2; j++) {
			whiteSide[(j + 1) % 2][1] = blueSide[1][j];
			blueSide[1][j] = yellowSide[j][0];
			yellowSide[j][0] = greenSide[0][(j + 1) % 2];
			greenSide[0][(j + 1) % 2] = tempWhite[(j + 1) % 2];
		}
	}

	/**
	 * Rotate the back side of the cube right Orange side rotates counter clockwise
	 * T white -> R blue -> B yellow -> L green -> T white
	 */
	public void iBackSideRotate() {
		rotateSideCounterClockwise(orangeSide);
		int[] tempWhite = { whiteSide[0][1], whiteSide[1][1] };
		for (int j = 0; j < 2; j++) {
			whiteSide[(j + 1) % 2][1] = greenSide[0][(j + 1) % 2];
			greenSide[0][(j + 1) % 2] = yellowSide[j][0];
			yellowSide[j][0] = blueSide[1][j];
			blueSide[1][j] = tempWhite[(j + 1) % 2];
		}
	}

	/**
	 * Rotate the bottom side of the cube right Yellow side rotates clockwise B red
	 * -> B blue -> B orange -> B green -> B red
	 */
	public void downSideRotate() {
		rotateSideClockwise(yellowSide);
		int[] tempRed = { redSide[0][0], redSide[1][0] };
		for (int j = 0; j < 2; j++) {
			redSide[j][0] = greenSide[j][0];
			greenSide[j][0] = orangeSide[j][0];
			orangeSide[j][0] = blueSide[j][0];
			blueSide[j][0] = tempRed[j];
		}
	}

	/**
	 * Rotate the bottom side of the cube left Yellow side rotates counterclockwise
	 * B red -> B green -> B orange -> B blue -> B red
	 */
	public void iDownSideRotate() {
		rotateSideCounterClockwise(yellowSide);
		int[] tempRed = { redSide[0][0], redSide[1][0] };
		for (int j = 0; j < 2; j++) {
			redSide[j][0] = blueSide[j][0];
			blueSide[j][0] = orangeSide[j][0];
			orangeSide[j][0] = greenSide[j][0];
			greenSide[j][0] = tempRed[j];
		}
	}

	/**
	 * Rotate the front side of the cube right Red side rotates clockwise B white ->
	 * L blue -> T yellow -> R green -> B white
	 */
	public void frontSideRotate() {
		rotateSideClockwise(redSide);
		int[] tempWhite = { whiteSide[0][0], whiteSide[1][0] };
		for (int j = 0; j < 2; j++) {
			whiteSide[(j + 1) % 2][0] = greenSide[1][(j + 1) % 2];
			greenSide[1][(j + 1) % 2] = yellowSide[j][1];
			yellowSide[j][1] = blueSide[0][j];
			blueSide[0][j] = tempWhite[(j + 1) % 2];
		}
	}

	/**
	 * Rotate the front side of the cube left Red side rotates counterclockwise B
	 * white -> R green -> T yellow -> L blue -> B white
	 */
	public void iFrontSideRotate() {
		rotateSideCounterClockwise(redSide);
		int[] tempWhite = { whiteSide[0][0], whiteSide[1][0] };
		for (int j = 0; j < 2; j++) {
			whiteSide[(j + 1) % 2][0] = blueSide[0][j];
			blueSide[0][j] = yellowSide[j][1];
			yellowSide[j][1] = greenSide[1][(j + 1) % 2];
			greenSide[1][(j + 1) % 2] = tempWhite[(j + 1) % 2];
		}
	}

	/**
	 * Rotate the top side of the cube left White side rotates clockwise T red -> T
	 * green -> T orange -> T blue -> T red
	 */
	public void upSideRotate() {
		rotateSideClockwise(whiteSide);
		int[] tempRed = { redSide[0][1], redSide[1][1] };
		for (int j = 0; j < 2; j++) {
			redSide[j][1] = blueSide[j][1];
			blueSide[j][1] = orangeSide[j][1];
			orangeSide[j][1] = greenSide[j][1];
			greenSide[j][1] = tempRed[j];
		}
	}

	/**
	 * Rotate the top side of the cube right White side rotates counter clockwise T
	 * red -> T blue -> T orange -> T green -> T red
	 */
	public void iUpSideRotate() {
		rotateSideCounterClockwise(whiteSide);
		int[] tempRed = { redSide[0][1], redSide[1][1] };
		for (int j = 0; j < 2; j++) {
			redSide[j][1] = greenSide[j][1];
			greenSide[j][1] = orangeSide[j][1];
			orangeSide[j][1] = blueSide[j][1];
			blueSide[j][1] = tempRed[j];
		}
	}

	/** Main method. */
	public static void main(String[] args) {
		RubiksCube2x2 cube = new RubiksCube2x2();
		cube.randomizeCubiePositions(10);
		cube.drawRubiksCube();
		cube.breadthBruteForce();
		cube.drawRubiksCube();
		StdOut.printf(cube.numMoves + " moves taken.\n");
		StdOut.printf(cube.timeElapsed + " seconds taken to compute optimal solution\n");
	}

}
