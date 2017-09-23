import static org.junit.Assert.*;

import org.junit.Test;

public class InstantRunoffElectionTest {

	private InstantRunoffElection election;

	@Test
	public void election0CountedCorrectly() {
		System.out.println("Election0");
		election = new InstantRunoffElection("elections/election0/");
		assertEquals("Akiko", election.winner());
	}

	@Test
	public void election1CountedCorrectly() {
		System.out.println("Election1");
		election = new InstantRunoffElection("elections/election1/");
		assertEquals("Bob", election.winner());
	}

	@Test
	public void election2CountedCorrectly() {
		System.out.println("Election2");
		election = new InstantRunoffElection("elections/election2/");
		assertEquals("Carlos", election.winner());
	}

	@Test
	public void election3CountedCorrectly() {
		System.out.println("Election3");
		election = new InstantRunoffElection("elections/election3/");
		assertEquals("Danielle", election.winner());
	}

}
