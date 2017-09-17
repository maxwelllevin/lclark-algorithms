
//Falling Sand Model Test including original tests for mercury, lava, and obsidian by Journie Ma-Johnson and Maxwell Levin
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FallingSandModelTest {

	private FallingSandModel model;

	@Before
	public void setUp() throws Exception {
		model = new FallingSandModel();
	}

	@Test
	public void inGridAcceptsPointInDiagram() {
		assertTrue(model.inGrid(99, 4));
	}

	@Test
	public void inGridRejectsPointsOutsideDiagram() {
		assertFalse(model.inGrid(FallingSandModel.MODEL_SIZE, 8));
		assertFalse(model.inGrid(-1, 15));
		assertTrue(model.inGrid(0, 16));
		assertFalse(model.inGrid(23, -1));
		assertFalse(model.inGrid(42, FallingSandModel.MODEL_SIZE));
	}

	@Test
	public void gridInitiallyEmpty() {
		assertEquals(0, model.get(18, 91));
	}

	@Test
	public void particleIsPlaced() {
		model.placeParticle(50, 60);
		assertEquals(FallingSandModel.METAL, model.get(50, 60));
	}

	@Test
	public void particlePlacedDependsOnMode() {
		model.setMode(FallingSandModel.EMPTY);
		model.placeParticle(99, 3);
		assertEquals(FallingSandModel.EMPTY, model.get(99, 3));
	}

	@Test
	public void sandMovesDownward() {
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(121, 29);
		model.sandStep(121, 29);
		assertEquals(FallingSandModel.EMPTY, model.get(121, 29));
		assertEquals(FallingSandModel.SAND, model.get(121, 28));
	}

	@Test
	public void metalBlocksSand() {
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(44, 81);
		model.setMode(FallingSandModel.METAL);
		model.placeParticle(44, 80);
		model.sandStep(44, 81);
		assertEquals(FallingSandModel.SAND, model.get(44, 81));
		assertEquals(FallingSandModel.METAL, model.get(44, 80));
	}

	@Test
	public void waterMoves() {
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(14, 199);
		model.waterStep(14, 199);
		assertEquals(FallingSandModel.EMPTY, model.get(14, 199));
		boolean left = (model.get(13, 199) == FallingSandModel.WATER);
		boolean down = (model.get(14, 198) == FallingSandModel.WATER);
		boolean right = (model.get(15, 199) == FallingSandModel.WATER);
		assertTrue(left || down || right);
	}

	@Test
	public void metalBlocksWater() {
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(141, 67);
		model.setMode(FallingSandModel.METAL);
		model.placeParticle(140, 67);
		model.placeParticle(141, 66);
		model.placeParticle(142, 67);
		model.waterStep(141, 67);
		assertEquals(FallingSandModel.WATER, model.get(141, 67));
		assertEquals(FallingSandModel.METAL, model.get(140, 67));
		assertEquals(FallingSandModel.METAL, model.get(141, 66));
		assertEquals(FallingSandModel.METAL, model.get(142, 67));
	}

	@Test
	public void sandDisplacesWater() {
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(128, 11);
		model.sandStep(128, 12);
		assertEquals(FallingSandModel.WATER, model.get(128, 12));
		assertEquals(FallingSandModel.SAND, model.get(128, 11));
	}

	// ============================================ORIGINAL TESTS START
	// HERE=====================================
	@Test
	public void mercuryMoves() {
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(14, 199);
		model.mercuryStep(14, 199);
		assertEquals(FallingSandModel.EMPTY, model.get(14, 199));
		boolean left = (model.get(13, 199) == FallingSandModel.MERCURY);
		boolean down = (model.get(14, 198) == FallingSandModel.MERCURY);
		boolean right = (model.get(15, 199) == FallingSandModel.MERCURY);
		assertTrue(left || down || right);
	}

	@Test
	public void metalBlocksMercury() {
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(141, 67);
		model.setMode(FallingSandModel.METAL);
		model.placeParticle(140, 67);
		model.placeParticle(141, 66);
		model.placeParticle(142, 67);
		model.mercuryStep(141, 67);
		assertEquals(FallingSandModel.MERCURY, model.get(141, 67));
		assertEquals(FallingSandModel.METAL, model.get(140, 67));
		assertEquals(FallingSandModel.METAL, model.get(141, 66));
		assertEquals(FallingSandModel.METAL, model.get(142, 67));
	}

	@Test
	public void mercuryBlocksWater() {
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(141, 67);
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(140, 67);
		model.placeParticle(141, 66);
		model.placeParticle(142, 67);
		model.waterStep(141, 67);
		assertEquals(FallingSandModel.WATER, model.get(141, 67));
		assertEquals(FallingSandModel.MERCURY, model.get(140, 67));
		assertEquals(FallingSandModel.MERCURY, model.get(141, 66));
		assertEquals(FallingSandModel.MERCURY, model.get(142, 67));
	}

	@Test
	public void mercuryBlocksSand() {
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(44, 81);
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(44, 80);
		model.sandStep(44, 81);
		assertEquals(FallingSandModel.SAND, model.get(44, 81));
		assertEquals(FallingSandModel.MERCURY, model.get(44, 80));
	}

	@Test
	public void mercuryBlocksObsidian() {
		model.setMode(FallingSandModel.OBSIDIAN);
		model.placeParticle(44, 81);
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(44, 80);
		model.obsidianStep(44, 81);
		assertEquals(FallingSandModel.OBSIDIAN, model.get(44, 81));
		assertEquals(FallingSandModel.MERCURY, model.get(44, 80));
	}

	@Test
	public void mercuryDisplacesWater() {
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(128, 11);
		model.placeParticle(127, 12);
		model.placeParticle(129, 12);
		model.mercuryStep(128, 12);
		assertEquals(FallingSandModel.WATER, model.get(128, 12));
		boolean left = (model.get(127, 12) == FallingSandModel.MERCURY);
		boolean down = (model.get(128, 11) == FallingSandModel.MERCURY);
		boolean right = (model.get(129, 12) == FallingSandModel.MERCURY);
		assertTrue(left || down || right);

	}

	@Test
	public void sandBlocksMercury() {
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(44, 81);
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(44, 80);
		model.placeParticle(43, 81);
		model.placeParticle(45, 81);
		model.mercuryStep(44, 81);
		assertEquals(FallingSandModel.MERCURY, model.get(44, 81));
		assertEquals(FallingSandModel.SAND, model.get(44, 80));
	}

	@Test
	public void lavaMoves() {
		model.setMode(FallingSandModel.LAVA);
		model.placeParticle(14, 199);
		model.lavaStep(14, 199);
		assertEquals(FallingSandModel.EMPTY, model.get(14, 199));
		boolean left = (model.get(13, 199) == FallingSandModel.LAVA);
		boolean down = (model.get(14, 198) == FallingSandModel.LAVA);
		boolean right = (model.get(15, 199) == FallingSandModel.LAVA);
		assertTrue(left || down || right);
	}

	@Test
	public void obsidianMoves() {
		model.setMode(FallingSandModel.OBSIDIAN);
		model.placeParticle(121, 29);
		model.obsidianStep(121, 29);
		assertEquals(FallingSandModel.EMPTY, model.get(121, 29));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(121, 28));
	}

	@Test
	public void lavaBlocksObsidian() {
		model.setMode(FallingSandModel.OBSIDIAN);
		model.placeParticle(44, 81);
		model.setMode(FallingSandModel.LAVA);
		model.placeParticle(44, 80);
		model.obsidianStep(44, 81);
		assertEquals(FallingSandModel.OBSIDIAN, model.get(44, 81));
		assertEquals(FallingSandModel.LAVA, model.get(44, 80));
	}

	@Test
	public void obsidianBlocksWater() {
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(141, 67);
		model.setMode(FallingSandModel.OBSIDIAN);
		model.placeParticle(140, 67);
		model.placeParticle(141, 66);
		model.placeParticle(142, 67);
		model.waterStep(141, 67);
		assertEquals(FallingSandModel.WATER, model.get(141, 67));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(140, 67));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(141, 66));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(142, 67));
	}

	@Test
	public void obsidianBlocksSand() {
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(44, 81);
		model.setMode(FallingSandModel.OBSIDIAN);
		model.placeParticle(44, 80);
		model.sandStep(44, 81);
		assertEquals(FallingSandModel.SAND, model.get(44, 81));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(44, 80));
	}

	@Test
	public void obsidianBlocksMercury() {
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(141, 67);
		model.setMode(FallingSandModel.OBSIDIAN);
		model.placeParticle(140, 67);
		model.placeParticle(141, 66);
		model.placeParticle(142, 67);
		model.mercuryStep(141, 67);
		assertEquals(FallingSandModel.MERCURY, model.get(141, 67));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(140, 67));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(141, 66));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(142, 67));
	}

	@Test
	public void sandDisplacesLava() {
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.LAVA);
		model.placeParticle(128, 11);
		model.sandStep(128, 12);
		assertEquals(FallingSandModel.LAVA, model.get(128, 12));
		assertEquals(FallingSandModel.SAND, model.get(128, 11));
	}

	@Test
	public void obsidianDisplacesWater() {
		model.setMode(FallingSandModel.OBSIDIAN);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(128, 11);
		model.obsidianStep(128, 12);
		assertEquals(FallingSandModel.WATER, model.get(128, 12));
		assertEquals(FallingSandModel.OBSIDIAN, model.get(128, 11));
	}

	@Test
	public void lavaMeltsMetal() {
		model.setMode(FallingSandModel.LAVA);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.METAL);
		model.placeParticle(128, 11);
		model.placeParticle(127, 12);
		model.placeParticle(129, 12);
		model.lavaStep(128, 12);
		assertEquals(FallingSandModel.EMPTY, model.get(128, 12));
		boolean left = (model.get(127, 12) == FallingSandModel.LAVA);
		boolean down = (model.get(128, 11) == FallingSandModel.LAVA);
		boolean right = (model.get(129, 12) == FallingSandModel.LAVA);
		assertTrue(left || down || right);
	}

	@Test
	public void lavaMeltsMercury() {
		model.setMode(FallingSandModel.LAVA);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.MERCURY);
		model.placeParticle(128, 11);
		model.placeParticle(127, 12);
		model.placeParticle(129, 12);
		model.lavaStep(128, 12);
		assertEquals(FallingSandModel.EMPTY, model.get(128, 12));
		boolean left = (model.get(127, 12) == FallingSandModel.LAVA);
		boolean down = (model.get(128, 11) == FallingSandModel.LAVA);
		boolean right = (model.get(129, 12) == FallingSandModel.LAVA);
		assertTrue(left || down || right);
	}

	@Test
	public void waterLavaBecomesObsidian() {
		// when water is moving and hits lava, it becomes obsidian
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.LAVA);
		model.placeParticle(128, 11);
		model.placeParticle(127, 12);
		model.placeParticle(129, 12);
		model.waterStep(128, 12);
		assertEquals(FallingSandModel.EMPTY, model.get(128, 12));
		boolean left = (model.get(127, 12) == FallingSandModel.OBSIDIAN);
		boolean down = (model.get(128, 11) == FallingSandModel.OBSIDIAN);
		boolean right = (model.get(129, 12) == FallingSandModel.OBSIDIAN);
		assertTrue(left || down || right);
	}

	@Test
	public void lavaWaterBecomesObsidian() {
		// when lava is moving and hits water, it becomes obsidian
		model.setMode(FallingSandModel.LAVA);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(128, 11);
		model.placeParticle(127, 12);
		model.placeParticle(129, 12);
		model.lavaStep(128, 12);
		assertEquals(FallingSandModel.EMPTY, model.get(128, 12));
		boolean left = (model.get(127, 12) == FallingSandModel.OBSIDIAN);
		boolean down = (model.get(128, 11) == FallingSandModel.OBSIDIAN);
		boolean right = (model.get(129, 12) == FallingSandModel.OBSIDIAN);
		assertTrue(left || down || right);
	}
}
