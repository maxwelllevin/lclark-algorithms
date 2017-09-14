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
		model.waterStep(44, 81);
		assertEquals(FallingSandModel.WATER, model.get(141, 67));
		assertEquals(FallingSandModel.METAL, model.get(140, 67));				
		assertEquals(FallingSandModel.METAL, model.get(141, 66));				
		assertEquals(FallingSandModel.METAL, model.get(142, 67));				
	}
	
	@Test
	public void waterDisplacesSand() {
		model.setMode(FallingSandModel.SAND);
		model.placeParticle(128, 12);
		model.setMode(FallingSandModel.WATER);
		model.placeParticle(128, 11);
		model.sandStep(128, 12);
		assertEquals(FallingSandModel.WATER, model.get(128, 12));
		assertEquals(FallingSandModel.SAND, model.get(128, 11));
	}

}
