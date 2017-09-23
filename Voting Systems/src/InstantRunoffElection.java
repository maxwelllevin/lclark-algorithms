import java.io.File;

public class InstantRunoffElection {

	/** For simplicity, this is fixed for all elections. */
	private static final String[] CANDIDATE_NAMES = { "Akiko", "Bob", "Carlos", "Danielle" };

	/** Tells us if a candidate has been eliminated. Changes every election. */
	boolean[] ELIMINATED_CANDIDATES = { false, false, false, false };

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

			// Reset our votes array for every time we rerun the election
			for (int i = 0; i < votes.length; i++) {
				votes[i] = 0;
			}

			// Cycle through all the ballots
			for (File f : ballots) {
				Ballot b = new Ballot(directory + f.getName());

				// Check the ballot for the first uneliminated candidate ( 'i'
				// is the current
				// candidate index )
				for (int i = 0; i < b.size(); i++) {

					// If "--" there is nothing significant left in the ballot
					// -- JK: Apparently we do want to count ballot votes for
					// candidates that voters do not approve of
					if (b.get(i).equals("--")) {
						continue;
					}
					// If the current candidate has been eliminated, check the
					// next one
					else if (thisCandidateIsEliminated(b.get(i))) {
						continue;
					}

					// Check vote against candidate name
					for (int j = 0; j < CANDIDATE_NAMES.length; j++) {
						// Increment the vote count for that candidate, then
						// break
						if (b.get(i).equals(CANDIDATE_NAMES[j])) {
							votes[j]++;
							break;
						}

					}

					// Our if/else if statements ensure we find the first
					// available candidate
					// This break ensures we only consider such a candidate
					break;
				}

			}

			// TODO: if no victor has been found, remove the least popular
			// candidate
			// If someone wins, exit InstantRunoff
			if (somebodyWinsElection()) {
				break;
			}
			// If nobody wins, eliminate the least popular candidate from the
			// available
			// candidates, run again
			else {
				// TODO: Might be necessary to check that there are uneliminated
				// candidates
				eliminateUnpopularCandidate();
			}
		}
	}

	private boolean thisCandidateIsEliminated(String name) {
		// Gets index in CANDIDATE_NAMES at which we see 'name'
		// Returns ELIMINATED_CANDIDATES[index]
		int index = -1;
		for (int i = 0; i < CANDIDATE_NAMES.length; i++) {
			if (name.equals(CANDIDATE_NAMES[i])) {
				return ELIMINATED_CANDIDATES[i];
			}
		}
		// If it gets here, you'll know
		return ELIMINATED_CANDIDATES[index];
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
		// Does not function properly if there is a tie ( we assume no ties,
		// however )
		System.out.printf("eliminated %s with %d votes\n", CANDIDATE_NAMES[indexOfLeastVoted], fewestVotes);
		ELIMINATED_CANDIDATES[indexOfLeastVoted] = true;
	}

	private boolean somebodyWinsElection() {
		// Returns true when no victor has emerged
		return !winner().equals("Nobody");
	}

	/** Returns the winner of this election. */
	public String winner() {
		int highestCount = -1;
		int totalVotes = 0;
		String result = "Nobody";
		for (int i = 0; i < votes.length; i++) {
			if (votes[i] > highestCount) {
				result = CANDIDATE_NAMES[i];
				highestCount = votes[i];
			}
			totalVotes += votes[i];
		}
		// A winner needs more than 50% of votes to win
		if (0.5 >= (1.0 * highestCount) / totalVotes) {
			return "Nobody";
		}
		return result;
	}

}