import static org.junit.Assert.*;

import org.junit.Test;

public class PluralityElectionTest {

	private PluralityElection election;

	@Test
	public void election0CountedCorrectly() {
		election = new PluralityElection("elections/election0/");
		assertEquals("Akiko", election.winner());
	}

	@Test
	public void election1CountedCorrectly() {
		election = new PluralityElection("elections/election1/");
		assertEquals("Akiko", election.winner());
	}

	@Test
	public void election2CountedCorrectly() {
		election = new PluralityElection("elections/election2/");
		assertEquals("Akiko", election.winner());
	}

	@Test
	public void election3CountedCorrectly() {
		election = new PluralityElection("elections/election3/");
		assertEquals("Bob", election.winner());
	}

}
