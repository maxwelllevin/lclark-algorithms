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

public class RubiksCube3x3 {

	/** Represent colors by integer values for easy comparisons. */
	private static final int WHITE = 0;
	private static final int GREEN = 1;
	private static final int RED = 2;
	private static final int BLUE = 3;
	private static final int ORANGE = 4;
	private static final int YELLOW = 5;

	/** Colors used for drawing the cube to the buffer. */
	private static final Color[] COLORS = { StdDraw.WHITE, Color.getHSBColor(0.412f, 0.9f, 0.72f), StdDraw.RED,
			StdDraw.BLUE, StdDraw.PRINCETON_ORANGE, StdDraw.YELLOW };

	/** Denote each side by the color of its central cubie. */
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

	/** The integer-valued moves needed to solve the cube. */
	public ArrayList<Integer> moves;

	/**
	 * The number of steps away from the solution our solve method will
	 * consider. Depth should be at most 26. Recommended to set near 7 for quick
	 * computations.
	 */
	public int depth;

	/** Constructor initializes cubie positions and generates graphics. */
	public RubiksCube3x3() {
		depth = 7;
		numMoves = 0;
		timeElapsed = 0;
		instantiateCubiePositions();
		generateInitialGraphics();
	}

	/**
	 * Solves the cube using iterative depth-first search. Faster than regular
	 * depth-first in most cases.
	 */
	public void solve() {
		Stopwatch time = new Stopwatch();
		if (isSolved()) {
			return;
		}
		moves = new ArrayList<Integer>();
		for (int d = 1; d <= depth; d++) {
			moves = depthBruteForce(d, -1);
			if (moves != null) {
				break;
			}
		}
		if (moves == null) {
			return;
		}
		for (int i = moves.size() - 1; i >= 0; i--) {
			performRotation(moves.get(i));
		}
		numMoves = moves.size();
		timeElapsed = time.elapsedTime();
	}

	/**
	 * Recursively finds a solution to the Rubik's Cube via brute force .
	 * 
	 * @return the first list of moves that is a solution.
	 */
	private ArrayList<Integer> depthBruteForce(int d, int rot) {
		if (d == 0 && !isSolved()) {
			return null;
		}
		for (int i = 0; i < 12; i++) {
			if (isSolved()) {
				ArrayList<Integer> base = new ArrayList<Integer>();
				base.add(rot);
				return base;
			}
			performRotation(i);
			ArrayList<Integer> move = depthBruteForce(d - 1, i);
			performRotation((i + 6) % 12);
			if (move != null) {
				if (rot != -1) {
					move.add(rot);
				}
				return move;
			}
		}
		return null;
	}

	/** Return true if the cube is solved, false otherwise */
	public boolean isSolved() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (redSide[i][j] != RED || greenSide[i][j] != GREEN || blueSide[i][j] != BLUE
						|| orangeSide[i][j] != ORANGE || whiteSide[i][j] != WHITE || yellowSide[i][j] != YELLOW) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Set each side to the color of its central cubie to ensure valid starting
	 * point.
	 */
	private void instantiateCubiePositions() {
		whiteSide = new int[3][3];
		greenSide = new int[3][3];
		redSide = new int[3][3];
		blueSide = new int[3][3];
		orangeSide = new int[3][3];
		yellowSide = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
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
	public void shuffle() {
		for (int i = 0; i < depth; i++) {
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
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				StdDraw.setPenColor(COLORS[greenSide[i][j]]);
				StdDraw.filledSquare(.25 / 6 + .25 * i / 3, .5 - .25 / 3 + 0.25 * j / 3, .25 / 6);
				StdDraw.setPenColor(COLORS[redSide[i][j]]);
				StdDraw.filledSquare(.25 / 6 + .25 + .25 * i / 3, .5 - .25 / 3 + .25 * j / 3, .25 / 6);
				StdDraw.setPenColor(COLORS[blueSide[i][j]]);
				StdDraw.filledSquare(.25 / 6 + .50 + .25 * i / 3, .5 - .25 / 3 + .25 * j / 3, .25 / 6);
				StdDraw.setPenColor(COLORS[orangeSide[i][j]]);
				StdDraw.filledSquare(.25 / 6 + .75 + .25 * i / 3, .5 - .25 / 3 + .25 * j / 3, .25 / 6);
				StdDraw.setPenColor(COLORS[whiteSide[i][j]]);
				StdDraw.filledSquare(.25 / 6 + .25 + .25 * i / 3, .75 - .25 / 3 + .25 * j / 3, .25 / 6);
				StdDraw.setPenColor(COLORS[yellowSide[i][j]]);
				StdDraw.filledSquare(.25 / 6 + .25 + .25 * i / 3, .25 - .25 / 3 + .25 * j / 3, .25 / 6);
			}
		}
		StdDraw.show();
	}

	/** Prints the number of moves and seconds taken to solve the cube. */
	public void stats() {
		if (numMoves == 0) {
			StdOut.println("No solution found!");
		} else {
			StdOut.printf(numMoves + " moves taken.\n");
			StdOut.printf(timeElapsed + " seconds taken to compute optimal solution\n");
		}
	}

	/** Animates the solution to the randomized cube. */
	public void animate() {
		if (moves == null && !isSolved()) {
			StdOut.println("No solution found!");
		} else if (moves == null) {
			StdOut.println("Cube is already solved, no animation required!");
		}
		for (int i = 0; i < moves.size(); i++) {
			performRotation((moves.get(i) + 6) % 12);
		}
		drawRubiksCube();
		StdDraw.pause(1000);
		for (int i = moves.size() - 1; i >= 0; i--) {
			performRotation(moves.get(i));
			drawRubiksCube();
			printRotation(moves.get(i));
			StdDraw.pause(1000);
		}
		StdOut.println();
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

	/**
	 * Prints the human-readable notation associated with an integer cube
	 * rotation.
	 */
	public void printRotation(int rot) {
		if (rot == 0)
			StdOut.print("R\t");
		else if (rot == 1)
			StdOut.print("L\t");
		else if (rot == 2)
			StdOut.print("B\t");
		else if (rot == 3)
			StdOut.print("D\t");
		else if (rot == 4)
			StdOut.print("F\t");
		else if (rot == 5)
			StdOut.print("U\t");
		else if (rot == 6)
			StdOut.print("R'\t");
		else if (rot == 7)
			StdOut.print("L'\t");
		else if (rot == 8)
			StdOut.print("B'\t");
		else if (rot == 9)
			StdOut.print("D'\t");
		else if (rot == 10)
			StdOut.print("F'\t");
		else if (rot == 11)
			StdOut.print("U'\t");
	}

	/** Rotates a side clockwise */
	private void rotateSideClockwise(int[][] side) {
		int tempCorner = side[0][0];
		int tempEdge = side[0][1];
		side[0][0] = side[2][0];
		side[2][0] = side[2][2];
		side[2][2] = side[0][2];
		side[0][2] = tempCorner;
		side[0][1] = side[1][0];
		side[1][0] = side[2][1];
		side[2][1] = side[1][2];
		side[1][2] = tempEdge;
	}

	/** Rotates a side counter clockwise */
	private void rotateSideCounterClockwise(int[][] side) {
		int tempCorner = side[0][0];
		int tempEdge = side[0][1];
		side[0][0] = side[0][2];
		side[0][2] = side[2][2];
		side[2][2] = side[2][0];
		side[2][0] = tempCorner;
		side[0][1] = side[1][2];
		side[1][2] = side[2][1];
		side[2][1] = side[1][0];
		side[1][0] = tempEdge;

	}

	/**
	 * Rotate the right side of the cube up BlueSide rotates clockwise R red ->
	 * R white -> L orange -> R yellow -> R red
	 */
	public void rightSideRotate() {
		rotateSideClockwise(blueSide);
		int[] tempRed = { redSide[2][0], redSide[2][1], redSide[2][2] };
		for (int j = 0; j < 3; j++) {
			redSide[2][j] = yellowSide[2][j];
			yellowSide[2][j] = orangeSide[0][2 - j];
			orangeSide[0][2 - j] = whiteSide[2][j];
			whiteSide[2][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the right side of the cube down BlueSide rotates counterclockwise
	 * R red -> R yellow -> L orange -> R white -> R red
	 */
	public void iRightSideRotate() {
		rotateSideCounterClockwise(blueSide);
		int[] tempRed = { redSide[2][0], redSide[2][1], redSide[2][2] };
		for (int j = 0; j < 3; j++) {
			redSide[2][j] = whiteSide[2][j];
			whiteSide[2][j] = orangeSide[0][2 - j];
			orangeSide[0][2 - j] = yellowSide[2][j];
			yellowSide[2][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the left side of the cube down GreenSide rotates clockwise L red
	 * -> L yellow -> R orange -> L white -> L red
	 */
	public void leftSideRotate() {
		rotateSideClockwise(greenSide);
		int[] tempRed = { redSide[0][0], redSide[0][1], redSide[0][2] };
		for (int j = 0; j < 3; j++) {
			redSide[0][j] = whiteSide[0][j];
			whiteSide[0][j] = orangeSide[2][2 - j];
			orangeSide[2][2 - j] = yellowSide[0][j];
			yellowSide[0][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the left side of the cube up GreenSide rotates counterclockwise L
	 * red -> L white -> R orange -> L yellow -> L red
	 */
	public void iLeftSideRotate() {
		rotateSideCounterClockwise(greenSide);
		int[] tempRed = { redSide[0][0], redSide[0][1], redSide[0][2] };
		for (int j = 0; j < 3; j++) {
			redSide[0][j] = yellowSide[0][j];
			yellowSide[0][j] = orangeSide[2][2 - j];
			orangeSide[2][2 - j] = whiteSide[0][j];
			whiteSide[0][j] = tempRed[j];
		}
	}

	/**
	 * Rotate the back side of the cube left Orange side rotates clockwise T
	 * white -> L green -> B yellow -> R blue -> T white
	 */
	public void backSideRotate() {
		rotateSideClockwise(orangeSide);
		int[] tempWhite = { whiteSide[0][2], whiteSide[1][2], whiteSide[2][2] };
		for (int j = 0; j < 3; j++) {
			whiteSide[2 - j][2] = blueSide[2][j];
			blueSide[2][j] = yellowSide[j][0];
			yellowSide[j][0] = greenSide[0][2 - j];
			greenSide[0][2 - j] = tempWhite[2 - j];
		}
	}

	/**
	 * Rotate the back side of the cube right Orange side rotates counter
	 * clockwise T white -> R blue -> B yellow -> L green -> T white
	 */
	public void iBackSideRotate() {
		rotateSideCounterClockwise(orangeSide);
		int[] tempWhite = { whiteSide[0][2], whiteSide[1][2], whiteSide[2][2] };
		for (int j = 0; j < 3; j++) {
			whiteSide[j][2] = greenSide[0][j];
			greenSide[0][j] = yellowSide[2 - j][0];
			yellowSide[2 - j][0] = blueSide[2][2 - j];
			blueSide[2][2 - j] = tempWhite[j];
		}
	}

	/**
	 * Rotate the bottom side of the cube right Yellow side rotates clockwise B
	 * red -> B blue -> B orange -> B green -> B red
	 */
	public void downSideRotate() {
		rotateSideClockwise(yellowSide);
		int[] tempRed = { redSide[0][0], redSide[1][0], redSide[2][0] };
		for (int j = 0; j < 3; j++) {
			redSide[j][0] = greenSide[j][0];
			greenSide[j][0] = orangeSide[j][0];
			orangeSide[j][0] = blueSide[j][0];
			blueSide[j][0] = tempRed[j];
		}
	}

	/**
	 * Rotate the bottom side of the cube left Yellow side rotates
	 * counterclockwise B red -> B green -> B orange -> B blue -> B red
	 */
	public void iDownSideRotate() {
		rotateSideCounterClockwise(yellowSide);
		int[] tempRed = { redSide[0][0], redSide[1][0], redSide[2][0] };
		for (int j = 0; j < 3; j++) {
			redSide[j][0] = blueSide[j][0];
			blueSide[j][0] = orangeSide[j][0];
			orangeSide[j][0] = greenSide[j][0];
			greenSide[j][0] = tempRed[j];
		}
	}

	/**
	 * Rotate the front side of the cube right Red side rotates clockwise B
	 * white -> L blue -> T yellow -> R green -> B white
	 */
	public void frontSideRotate() {
		rotateSideClockwise(redSide);
		int[] tempWhite = { whiteSide[0][0], whiteSide[1][0], whiteSide[2][0] };
		for (int j = 0; j < 3; j++) {
			whiteSide[j][0] = greenSide[2][j];
			greenSide[2][j] = yellowSide[2 - j][2];
			yellowSide[2 - j][2] = blueSide[0][2 - j];
			blueSide[0][2 - j] = tempWhite[j];
		}
	}

	/**
	 * Rotate the front side of the cube left Red side rotates counterclockwise
	 * B white -> R green -> T yellow -> L blue -> B white
	 */
	public void iFrontSideRotate() {
		rotateSideCounterClockwise(redSide);
		int[] tempWhite = { whiteSide[0][0], whiteSide[1][0], whiteSide[2][0] };
		for (int j = 0; j < 3; j++) {
			whiteSide[j][0] = blueSide[0][2 - j];
			blueSide[0][2 - j] = yellowSide[2 - j][2];
			yellowSide[2 - j][2] = greenSide[2][j];
			greenSide[2][j] = tempWhite[j];
		}
	}

	/**
	 * Rotate the top side of the cube left White side rotates clockwise T red
	 * -> T green -> T orange -> T blue -> T red
	 */
	public void upSideRotate() {
		rotateSideClockwise(whiteSide);
		int[] tempRed = { redSide[0][2], redSide[1][2], redSide[2][2] };
		for (int j = 0; j < 3; j++) {
			redSide[j][2] = blueSide[j][2];
			blueSide[j][2] = orangeSide[j][2];
			orangeSide[j][2] = greenSide[j][2];
			greenSide[j][2] = tempRed[j];
		}
	}

	/**
	 * Rotate the top side of the cube right White side rotates counter
	 * clockwise T red -> T blue -> T orange -> T green -> T red
	 */
	public void iUpSideRotate() {
		rotateSideCounterClockwise(whiteSide);
		int[] tempRed = { redSide[0][2], redSide[1][2], redSide[2][2] };
		for (int j = 0; j < 3; j++) {
			redSide[j][2] = greenSide[j][2];
			greenSide[j][2] = orangeSide[j][2];
			orangeSide[j][2] = blueSide[j][2];
			blueSide[j][2] = tempRed[j];
		}
	}

	/** Main method. */
	public static void main(String[] args) {
		RubiksCube3x3 cube = new RubiksCube3x3();
		cube.shuffle();
		cube.drawRubiksCube();
		cube.solve();
		cube.animate();
		cube.stats();
	}
}
