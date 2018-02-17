import static org.junit.Assert.*;

import org.junit.Test;

public class RubiksCube3x3Test {

	/** Represent colors by integer values for easy comparisons. */
	public static final int WHITE = 0;
	public static final int GREEN = 1;
	public static final int RED = 2;
	public static final int BLUE = 3;
	public static final int ORANGE = 4;
	public static final int YELLOW = 5;

	private RubiksCube3x3 cube;

	@Test
	public void cubeInitiallySolved() {
		cube = new RubiksCube3x3();
		assertTrue(cube.isSolved());
	}

	@Test
	/** Could fail sometimes, but HIGHLY improbable */
	public void randomCubeIsNotSolved() {
		cube = new RubiksCube3x3();
		cube.depth = 27;
		cube.shuffle();
		assertFalse(cube.isSolved());
	}

	@Test
	public void solveMethodSolvesCube() {
		cube = new RubiksCube3x3();
		cube.depth = 5;
		cube.shuffle();
		cube.solve();
		assertTrue(cube.isSolved());
	}

	@Test
	public void solveCubeIncrementsNumMoves() {
		cube = new RubiksCube3x3();
		cube.depth = 5;
		cube.shuffle();
		cube.solve();
		assertTrue(cube.numMoves >= 1);
	}

	@Test
	public void solveFindsShortestSolution() {
		cube = new RubiksCube3x3();
		cube.rightSideRotate();
		cube.rightSideRotate();
		cube.rightSideRotate();
		cube.downSideRotate();
		cube.solve();
		assertTrue(cube.moves.get(0) == 0);
		assertTrue(cube.moves.get(1) == 9);
	}

	@Test
	public void inverseRotationsCancel() {
		cube = new RubiksCube3x3();
		cube.rightSideRotate();
		cube.leftSideRotate();
		cube.backSideRotate();
		cube.downSideRotate();
		cube.frontSideRotate();
		cube.upSideRotate();
		cube.iUpSideRotate();
		cube.iFrontSideRotate();
		cube.iDownSideRotate();
		cube.iBackSideRotate();
		cube.iLeftSideRotate();
		cube.iRightSideRotate();
		assertTrue(cube.isSolved());
	}

	@Test
	public void rightSideRotateRotatesRightSide() {
		cube = new RubiksCube3x3();
		cube.rightSideRotate();
		assertTrue(cube.whiteSide[2][0] == RED && cube.whiteSide[2][1] == RED && cube.whiteSide[2][2] == RED);
		assertTrue(cube.orangeSide[0][2] == WHITE && cube.orangeSide[0][1] == WHITE && cube.orangeSide[0][0] == WHITE);
		assertTrue(
				cube.yellowSide[2][0] == ORANGE && cube.yellowSide[2][1] == ORANGE && cube.yellowSide[2][2] == ORANGE);
		assertTrue(cube.redSide[2][0] == YELLOW && cube.redSide[2][1] == YELLOW && cube.redSide[2][2] == YELLOW);
	}

	@Test
	public void leftSideRotateRotatesLeftSide() {
		cube = new RubiksCube3x3();
		cube.leftSideRotate();
		assertTrue(cube.whiteSide[0][0] == ORANGE && cube.whiteSide[0][1] == ORANGE && cube.whiteSide[0][2] == ORANGE);
		assertTrue(cube.redSide[0][0] == WHITE && cube.redSide[0][1] == WHITE && cube.redSide[0][2] == WHITE);
		assertTrue(cube.yellowSide[0][0] == RED && cube.yellowSide[0][1] == RED && cube.yellowSide[0][2] == RED);
		assertTrue(
				cube.orangeSide[2][2] == YELLOW && cube.orangeSide[2][1] == YELLOW && cube.orangeSide[2][0] == YELLOW);
	}

	@Test
	public void backSideRotateRotatesBackSide() {
		cube = new RubiksCube3x3();
		cube.backSideRotate();
		assertTrue(cube.whiteSide[0][2] == BLUE && cube.whiteSide[1][2] == BLUE && cube.whiteSide[2][2] == BLUE);
		assertTrue(cube.blueSide[2][0] == YELLOW && cube.blueSide[2][1] == YELLOW && cube.blueSide[2][2] == YELLOW);
		assertTrue(cube.yellowSide[0][0] == GREEN && cube.yellowSide[1][0] == GREEN && cube.yellowSide[2][0] == GREEN);
		assertTrue(cube.greenSide[0][0] == WHITE && cube.greenSide[0][1] == WHITE && cube.greenSide[0][2] == WHITE);
	}

	@Test
	public void downSideRotateRotatesDownSide() {
		cube = new RubiksCube3x3();
		cube.downSideRotate();
		assertTrue(cube.redSide[0][0] == GREEN && cube.redSide[1][0] == GREEN && cube.redSide[2][0] == GREEN);
		assertTrue(cube.blueSide[0][0] == RED && cube.blueSide[1][0] == RED && cube.blueSide[2][0] == RED);
		assertTrue(cube.orangeSide[0][0] == BLUE && cube.orangeSide[1][0] == BLUE && cube.orangeSide[2][0] == BLUE);
		assertTrue(cube.greenSide[0][0] == ORANGE && cube.greenSide[1][0] == ORANGE && cube.greenSide[2][0] == ORANGE);
	}

	@Test
	public void frontSideRotateRotatesFrontSide() {
		cube = new RubiksCube3x3();
		cube.frontSideRotate();
		assertTrue(cube.whiteSide[0][0] == GREEN && cube.whiteSide[1][0] == GREEN && cube.whiteSide[2][0] == GREEN);
		assertTrue(cube.blueSide[0][2] == WHITE && cube.blueSide[0][1] == WHITE && cube.blueSide[0][0] == WHITE);
		assertTrue(cube.yellowSide[0][2] == BLUE && cube.yellowSide[1][2] == BLUE && cube.yellowSide[2][2] == BLUE);
		assertTrue(cube.greenSide[2][0] == YELLOW && cube.greenSide[2][1] == YELLOW && cube.greenSide[2][2] == YELLOW);
	}

	@Test
	public void upSideRotateRotatesUpSide() {
		cube = new RubiksCube3x3();
		cube.upSideRotate();
		assertTrue(cube.redSide[0][2] == BLUE && cube.redSide[1][2] == BLUE && cube.redSide[2][2] == BLUE);
		assertTrue(cube.blueSide[0][2] == ORANGE && cube.blueSide[1][2] == ORANGE && cube.blueSide[2][2] == ORANGE);
		assertTrue(cube.orangeSide[0][2] == GREEN && cube.orangeSide[1][2] == GREEN && cube.orangeSide[2][2] == GREEN);
		assertTrue(cube.greenSide[0][2] == RED && cube.greenSide[1][2] == RED && cube.greenSide[2][2] == RED);
	}

}
