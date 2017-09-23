import java.io.File;

public class ApprovalElection {

	/** For simplicity, this is fixed for all elections. */
	private static final String[] CANDIDATE_NAMES = { "Akiko", "Bob", "Carlos", "Danielle" };

	/** Number of votes for each candidate. */
	private int[] votes;

	public ApprovalElection(String directory) {
		votes = new int[CANDIDATE_NAMES.length];
		File[] ballots = new File(directory).listFiles();
		for (File f : ballots) {
			Ballot b = new Ballot(directory + f.getName());

			// Loop through names on the ballot
			for (int i = 0; i < b.size(); i++) {

				// If "--" Approval votes are done
				if (b.get(i).equals("--")) {
					break;
				}

				// Check vote against candidate name
				for (int j = 0; j < CANDIDATE_NAMES.length; j++) {
					// If vote is for a candidate, increment vote count for the
					// candidate
					if (b.get(i).equals(CANDIDATE_NAMES[j])) {
						votes[j]++;
						break;
					}

				}
			}

		}
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
		return result;
	}

}
