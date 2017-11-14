import java.io.*;
import java.util.*;

public class EvilSnowman {

	public boolean hasUserWon = false;

	/** Length of the word */
	private int wordLength;

	/** Number of invalid guesses allowed */
	private int numGuesses;

	/** Used for printing the answer */
	private String answerString = "";

	/** Holds our the most recently guessed character */
	private char mostRecentGuess;

	/** An ArrayList object that stores a dictionary of ~120,000 words */
	private ArrayList<String> dictionary = new ArrayList<String>();

	/** Use an ArrayList to store letters the user has guessed */
	private ArrayList<Character> guessedLetters = new ArrayList<Character>();

	/**
	 * Initializes game : gets a valid user input for wordLength, number of
	 * guesses, and imports the words from the dictionary that have the
	 * specified word length
	 */
	public EvilSnowman() {
		wordLength = askWordLength();
		numGuesses = askNumGuesses();
		initializeGameDict();
	}

	/**
	 * Overloaded method to specify wordLength, numGuesses manually (test class)
	 */
	public EvilSnowman(int l, int g) {
		wordLength = l;
		numGuesses = g;
		initializeGameDict();
	}

	/**
	 * initializeGameDict() reads in words from our dictionary that have the
	 * desired length. It is only called from our constructor.
	 */
	private void initializeGameDict() {
		try (BufferedReader br = new BufferedReader(new FileReader("enable1.txt"))) {
			String line;
			while ((line = br.readLine()) != null)
				if (line.length() == wordLength) {
					dictionary.add(line);
				}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/** Asks user for a valid word length. */
	private int askWordLength() {
		boolean valid = false;
		int l = -1;
		while (!valid) {
			StdOut.println("How many letters would you like in your word?");
			l = StdIn.readInt();
			if (l > 0 && l < 100) {
				valid = true;
				for (int i = 0; i < l; i++)
					answerString += "_";
			}
		}
		return l;
	}

	/** Asks user for a valid number of guesses. */
	private int askNumGuesses() {
		boolean valid = false;
		int g = -1;
		while (!valid) {
			StdOut.println("How many guesses would you like?");
			g = StdIn.readInt();
			if (g > 0) {
				valid = true;
			}
		}
		return g;
	}

	/**
	 * Gets a user guess between (inclusive) 'a' and 'z' such that the guess has
	 * not already been guessed (We're nice)
	 */
	public char getValidGuess() {
		char c = StdIn.readString().charAt(0);
		while (c < 'a' && c > 'z' || guessedLetters.contains(c)) {
			StdOut.println("Please enter a new lowercase letter between a and z");
			c = StdIn.readString().charAt(0);
		}
		return c;
	}

	/** Returns true if most recent guess is in answerString */
	private boolean guessIsCorrect() {
		if (answerString.indexOf(mostRecentGuess) != -1)
			return true;
		return false;
	}

	/**
	 * Our game loop: checks to see if user won the game, asks user to guess,
	 * updates game dictionary, etc. Pass a string as an argument to perform
	 * guessing operation beforehand/for testing. Otherwise guessing performed
	 * manually.
	 */
	public void run() {
		while (numGuesses > 0) {
			if (answerString.equals(dictionary.get(0))) {
				StdOut.println(answerString);
				StdOut.println("Congratulations! You won!");
				hasUserWon = true;
				break;
			}

			StdOut.println("Guessed Letters: " + guessedLetters.toString());
			StdOut.println("Please enter your guess. You have " + numGuesses + " guesses left.");
			StdOut.println(answerString);

			mostRecentGuess = getValidGuess();
			guessedLetters.add(mostRecentGuess);
			guessedLetters.sort(null);
			
			updateGameDict();

			if (!guessIsCorrect()) {
				numGuesses--;
			}
		}
		if (numGuesses == 0) {
			int k = (int) (Math.random() * dictionary.size());
			StdOut.println("You lost ): The word was: " + dictionary.get(k));
		}
	}

	/**
	 * Our game loop: checks to see if user won the game, asks user to guess,
	 * updates game dictionary, etc. Pass a string as an argument to perform
	 * guessing operation beforehand/for testing. Otherwise guessing performed
	 * manually. NOT SAFE: Don't send a string smaller than the number of
	 * guesses desired
	 */
	public void run(String guessForYourTests) {
		int i = 0;
		while (numGuesses > 0) {
			if (answerString.equals(dictionary.get(0))) {
				StdOut.println(answerString);
				StdOut.println("Congratulations! You won!");
				hasUserWon = true;
				break;
			}

			StdOut.println("Guessed Letters: " + guessedLetters.toString());
			StdOut.println("Please enter your guess. You have " + numGuesses + " guesses left.");
			StdOut.println(answerString);

			mostRecentGuess = guessForYourTests.charAt(i);
			guessedLetters.add(mostRecentGuess);

			updateGameDict();

			if (!guessIsCorrect()) {
				numGuesses--;
			}
		}
		if (numGuesses == 0) {
			int k = (int) (Math.random() * dictionary.size());
			StdOut.println("You lost ): The word was: " + dictionary.get(k));
		}
	}

	/**
	 * Update the list of viable words based on user's guesses. I.E. Cheat as
	 * much as you can legally get away with!
	 */
	public void updateGameDict() {
		String ourHashCode = "";
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

		for (String word : dictionary) {
			for (char letter : word.toCharArray()) {
				if (letter == mostRecentGuess) {
					ourHashCode += "1";
				} else {
					ourHashCode += "0";
				}
			}
			if (map.containsKey(ourHashCode)) {
				ArrayList<String> temp = map.get(ourHashCode);
				temp.add(word);
				map.put(ourHashCode, temp);
			} else {
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(word);
				map.put(ourHashCode, temp);
			}
			ourHashCode = "";
		}

		int longestEntry = 0;
		String longestEntryKey = "";
		for (Map.Entry<String, ArrayList<String>> m : map.entrySet()) {
			if (m.getValue().size() > longestEntry) {
				longestEntry = m.getValue().size();
				longestEntryKey = m.getKey();
			}
		}
		dictionary.clear();
		for (String word : map.get(longestEntryKey)) {
			dictionary.add(word);
		}

		for (int i = 0; i < longestEntryKey.toCharArray().length; i++) {
			char binaryNum = longestEntryKey.toCharArray()[i];
			if (binaryNum == '1')
				answerString = answerString.substring(0, i) + mostRecentGuess + answerString.substring(i + 1);
		}
	}

	/** Returns the number of guesses the user has left */
	public int getNumGuesses() {
		return numGuesses;
	}

	/** Returns the length of the word the user has specified */
	public int getWordLength() {
		return wordLength;
	}

	/** Returns the ArrayList of viable words */
	public ArrayList<String> getDictionary() {
		return dictionary;
	}

	/**
	 * Main method. To run the game, make an EvilSnowman object by calling the
	 * constructor. Then call run() to play the game
	 */
	public static void main(String[] args) {
		EvilSnowman game = new EvilSnowman();
		game.run();
	}

}
