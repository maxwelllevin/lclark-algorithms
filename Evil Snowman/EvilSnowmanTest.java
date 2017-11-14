import static org.junit.Assert.*;

import org.junit.Test;

public class EvilSnowmanTest {

	
	@Test
	public void constructorSetsWordLength() {
		EvilSnowman game = new EvilSnowman(10, 20);
		boolean correctWordLength = game.getWordLength() == 10;
		assertTrue(correctWordLength);
	}
	
	@Test
	public void constructorSetsNumGuesses() {
		EvilSnowman game = new EvilSnowman(10, 20);
		boolean correctNumGuesses = game.getNumGuesses() == 20;
		assertTrue(correctNumGuesses);
	}
	
	@Test
	public void dictionaryIsNotEmpty() {
		EvilSnowman game = new EvilSnowman(10, 20);
		assertFalse(game.getDictionary().isEmpty());
	}
	
	
	@Test
	public void guaranteedToLoseTheGameWithFewGuesses() {
		String guesses = "abcde";
		EvilSnowman game = new EvilSnowman(10, 5);
		game.run(guesses);
		assertFalse(game.hasUserWon);
	}

}
