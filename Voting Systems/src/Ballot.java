
public class Ballot {
	// TODO: Add writes-ins, eliminate illegal ballots
	// Gets votes from file and stores them privately

	// String array to contain votes for a ballot
	private static String[] vote_storage;

	public Ballot(String path) {
		// Constructor reads a plain text file
		In file = new In(path);
		vote_storage = file.readAllLines();
	}

	public int size() {
		// Returns the number of candidates listed on a ballot
		return vote_storage.length;
	}

	public String get(int i) {
		// Returns the vote stored at 'i' position in vote_storage
		return vote_storage[i];
	}

}
