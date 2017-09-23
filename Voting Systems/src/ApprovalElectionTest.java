import static org.junit.Assert.*;

import org.junit.Test;

public class ApprovalElectionTest {

	private ApprovalElection election;

	@Test
	public void election0CountedCorrectly() {
		election = new ApprovalElection("elections/election0/");
		assertEquals("Akiko", election.winner());
	}

	@Test
	public void election1CountedCorrectly() {
		election = new ApprovalElection("elections/election1/");
		assertEquals("Bob", election.winner());
	}

	@Test
	public void election2CountedCorrectly() {
		election = new ApprovalElection("elections/election2/");
		assertEquals("Bob", election.winner());
	}

	@Test
	public void election3CountedCorrectly() {
		election = new ApprovalElection("elections/election3/");
		assertEquals("Carlos", election.winner());
	}


}
