import java.io.File;

public class InstantRunoffElection {

	/** For simplicity, this is fixed for all elections. */
	private static final String[] CANDIDATE_NAMES = { "Akiko", "Bob", "Carlos", "Danielle" };

	/** Tells us if a candidate has been eliminated. Changes every election. */
	private boolean[] ELIMINATED_CANDIDATES = { false, false, false, false };

	/** Number of votes for each candidate. */
	private int[] votes;

	/** Runs our Instant Runoff Election */
	public InstantRunoffElection(String directory) {
		votes = new int[CANDIDATE_NAMES.length];
		File[] ballots = new File(directory).listFiles();

		// Reset our eliminated candidates array for every election
		for (int i = 0; i < ELIMINATED_CANDIDATES.length; i++) {
			ELIMINATED_CANDIDATES[i] = false;
		}

		// Run our election until we have a winner
		while (true) {

			// Cycle through all the ballots
			for (File f : ballots) {
				Ballot b = new Ballot(directory + f.getName());

				// Check the ballot for the first uneliminated candidate ( 'i' is the current
				// candidate index )
				for (int i = 0; i < b.size(); i++) {

					// If "--" there is nothing significant left in the ballot
					if (b.get(i).equals("--")) {
						break;
					}
					// If the current candidate has been eliminated, check the next one
					else if (ELIMINATED_CANDIDATES[i]) {
						continue;
					}

					// Check vote against candidate name
					for (int j = 0; j < CANDIDATE_NAMES.length; j++) {
						// Increment the vote count for that candidate, then break
						if (b.get(i).equals(CANDIDATE_NAMES[j])) {
							votes[j]++;
							break;
						}

					}

					// Our if/else if statements ensure we find the first available candidate
					// This break ensures we only consider such a candidate
					break;
				}

			}

			// If someone wins the election is done
			if (!nobodyWinsElection()) {
				break;
			}
			// If nobody wins, eliminate the least popular candidate from the available
			// candidates, run again
			else {
				// TODO: Might be necessary to check that there are uneliminated candidates
				eliminateUnpopularCandidate();
			}
		}
	}

	private void eliminateUnpopularCandidate() {
		// Eliminate the uneliminated candidate with the fewest votes
		int fewestVotes = 1000;
		int indexOfLeastVoted = -1;
		for (int i = 0; i < votes.length; i++) {
			if (votes[i] < fewestVotes && !ELIMINATED_CANDIDATES[i]) {
				fewestVotes = votes[i];
				indexOfLeastVoted = i;
			}
		}
		// This will break if all candidates are eliminated. 
		// Does not function properly if there is a tie ( we assume no ties, however )
		ELIMINATED_CANDIDATES[indexOfLeastVoted] = true;
	}

	private boolean nobodyWinsElection() {
		// Returns true when no victor has emerged
		return winner().equals("Nobody");
	}

	/** Returns the winner of this election. */
	public String winner() {
		int highestCount = -1;
		String result = "Nobody";
		for (int i = 0; i < votes.length; i++) {
			if (votes[i] > highestCount) {
				result = CANDIDATE_NAMES[i];
				highestCount = votes[i];
			}
		}
		// A winner needs more than 50% of votes to win
		if (0.5 < (1.0 * highestCount) / (votes.length)) {
			return "Nobody";
		}
		return result;
	}

}