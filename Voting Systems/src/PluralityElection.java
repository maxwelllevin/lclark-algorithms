import java.io.File;

/** Runs an election by plurality voting. */
public class PluralityElection {

	/** For simplicity, this is fixed for all elections. */
	private static final String[] CANDIDATE_NAMES = {"Akiko", "Bob", "Carlos", "Danielle"};
	
	/** Number of votes for each candidate. */
	private int[] votes;
	
	public PluralityElection(String directory) {
		votes = new int[CANDIDATE_NAMES.length];
		File[] ballots = new File(directory).listFiles();
		for (File f : ballots) {
			Ballot b = new Ballot(directory + f.getName());
			if (b.size() > 0) {
				for (int i = 0; i < CANDIDATE_NAMES.length; i++) {
					if (b.get(0).equals(CANDIDATE_NAMES[i])) {
						votes[i]++;
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
