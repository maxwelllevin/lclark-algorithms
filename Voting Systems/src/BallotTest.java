import static org.junit.Assert.*;

import org.junit.Test;

public class BallotTest {

	private Ballot ballot;

	@Test
	public void ballotCountedProperly() {
		ballot = new Ballot("elections/election0/ballot0");
		assertEquals(3, ballot.size());
		ballot = new Ballot("elections/election3/ballot2");
		assertEquals(5, ballot.size());
	}

	@Test
	public void getReturnsCorrectName() {
		ballot = new Ballot("elections/election1/ballot6");
		assertEquals("Akiko", ballot.get(3));
		ballot = new Ballot("elections/election3/ballot10");
		assertEquals("--", ballot.get(1));
	}

}
