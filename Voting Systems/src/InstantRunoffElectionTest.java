import static org.junit.Assert.*;

import org.junit.Test;

public class InstantRunoffElectionTest {

	private InstantRunoffElection election;

	@Test
	public void election0CountedCorrectly() {
		election = new InstantRunoffElection("elections/election0/");
		assertEquals("Akiko", election.winner());
	}

	@Test
	public void election1CountedCorrectly() {
		election = new InstantRunoffElection("elections/election1/");
		assertEquals("Bob", election.winner());
	}

	@Test
	public void election2CountedCorrectly() {
		election = new InstantRunoffElection("elections/election2/");
		assertEquals("Carlos", election.winner());
	}

	@Test
	public void election3CountedCorrectly() {
		election = new InstantRunoffElection("elections/election3/");
		assertEquals("Danielle", election.winner());
	}

}
