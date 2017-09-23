import java.io.File;

public class InstantRunoffElection {

	/** For simplicity, this is fixed for all elections. */
	private static final String[] CANDIDATE_NAMES = { "Akiko", "Bob", "Carlos", "Danielle" };

	// List of candidates to ignore for instant_runoff elections
	private static String[] IGNORE_CANDIDATES = {};

	/** Number of votes for each candidate. */
	private int[] votes;

	// TODO: Modify this to actually do instant_runoff
	public InstantRunoffElection(String directory) {
		votes = new int[CANDIDATE_NAMES.length];
		File[] ballots = new File(directory).listFiles();

		// Add least popular candidate to ignore list and count votes until a
		// victor is found
		while (!isWinner()) {
			
			for (File f : ballots) {
				Ballot b = new Ballot(directory + f.getName());

				// Loop through names on the ballot
				for (int i = 0; i < b.size(); i++) {

					// If "--", votes are done
					if (b.get(i).equals("--")) {
						break;
					}

					// Ignore candidates on IgnoreList
					// if (!b.get(i).equals(CANDIDATE_NAMES[i]
					
					for (int j = 0; j < CANDIDATE_NAMES.length; j++) {
						
						// If vote is for a candidate, increment vote count for the candidate
						if (b.get(i).equals(CANDIDATE_NAMES[j])) {
							votes[j]++;
							break;
						}

					}
				}
			}
			
			addLoserToIgnoreList();
		}
	}

	private boolean isWinner() {
		// Return false if winner != "Nobody". True otherwise.
		return winner() != "Nobody";
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

	/** Returns the loser of this election **/
	public String addLoserToIgnoreList() {
		int lowestCount = 5000;
		String result = "Nobody";
		for (int i = 0; i < votes.length; i++) {
			if (votes[i] < lowestCount) {
				result = CANDIDATE_NAMES[i];
				lowestCount = votes[i];
			}
		}
		return result;
	}

	// Returns a String array of candidates who have lost previous elections
	public static String[] getIGNORE_CANDIDATES() {
		return IGNORE_CANDIDATES;
	}

	// Allows us to eliminate unpopular candidates from consideration
	public static void setIGNORE_CANDIDATES(String[] iGNORE_CANDIDATES) {
		IGNORE_CANDIDATES = iGNORE_CANDIDATES;
	}

}
