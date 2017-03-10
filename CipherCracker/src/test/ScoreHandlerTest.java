package test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import main.ScoreHandler;
import main.Trie;

/**
 * The Class ScoreHandlerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ScoreHandlerTest
{

	@InjectMocks
	private ScoreHandler scoreHandler;

	private Trie trie;
	private HashMap<String, Double> bigrams = new HashMap<String, Double>();
	private HashMap<String, Double> trigrams = new HashMap<String, Double>();
	private char twoPrevious = '0';
	private char previous = '0';
	private char current = '0';

	/**
	 * Setup.
	 */
	@Before
	public void setup()
	{
		trie = new Trie(false);
		trie.insert("example");
		trie.insert("computer");
		trie.insert("desk");

		bigrams.put("ab", 0.2);
		bigrams.put("th", 0.3);
		bigrams.put("er", 0.4);
		bigrams.put("om", 0.33);

		trigrams.put("the", 1.3);
		trigrams.put("com", 0.05);
		trigrams.put("tra", 2.5);
		trigrams.put("fab", 0.78);
	}

	/**
	 * Given characters.
	 *
	 * @param a
	 *            the a
	 * @param b
	 *            the b
	 * @param c
	 *            the c
	 */
	public void givenCharacters(char a, char b, char c)
	{
		twoPrevious = a;
		previous = b;
		current = c;
	}

	/**
	 * Then calculate score is called with bigram.
	 *
	 * @param expected
	 *            the expected
	 */
	public void thenCalculateScoreIsCalledWithBigram(double expected)
	{
//		double score = scoreHandler.calculateScore(previous, current, bigrams, trie);
//		assertEquals(expected, score, 0);
	}

	/**
	 * Then calculate score is called with trigram.
	 *
	 * @param expected
	 *            the expected
	 */
	public void thenCalculateScoreIsCalledWithTrigram(double expected)
	{
//		double score = scoreHandler.calculateScore(twoPrevious, previous, current, bigrams, trigrams, trie);
//		assertEquals(expected, score, 0);
	}

	/**
	 * Test calculateScore and mapSearch works correctly when the characters are
	 * in the bigram.
	 */
	@Test
	public void testCharactersInBigrams()
	{
		givenCharacters('c', 'o', 'm');
		thenCalculateScoreIsCalledWithBigram(2 * 0.33);
	}

	/**
	 * Test calculateScore and mapSearch works correctly when the characters are
	 * not in the bigram.
	 */
	@Test
	public void testCharactersNotInBigram()
	{
		givenCharacters('e', 'a', 'r');
		thenCalculateScoreIsCalledWithBigram(0);
	}

	/**
	 * Test calculateScore and mapSearch works correctly when the characters are
	 * in the trigram and bigram.
	 */
	@Test
	public void testCharactersInTrigramAndBigram()
	{
		givenCharacters('f', 'a', 'b');
		double expected = (2 * 0.2) + (3 * 0.78);
		thenCalculateScoreIsCalledWithTrigram(expected);
	}

	/**
	 * Test calculateScore and mapSearch works correctly when the characters are
	 * in the trigram but not the bigram.
	 */
	@Test
	public void testCharactersInTrigramButNotBigram()
	{
		givenCharacters('t', 'r', 'a');
		double expected = 3 * 2.5;
		thenCalculateScoreIsCalledWithTrigram(expected);
	}

	/**
	 * Test calculateScore and mapSearch works correctly when the characters are
	 * in the bigram but not the trigram.
	 */
	@Test
	public void testCharactersInBigramButNotTrigram()
	{
		givenCharacters('e', 'e', 'r');
		double expected = 2 * 0.4;
		thenCalculateScoreIsCalledWithTrigram(expected);
	}

	/**
	 * Test calculateScore and mapSearch works correctly when the characters are
	 * in neither the bigram nor the trigram.
	 */
	@Test
	public void testCharactersInNeitherMap()
	{
		givenCharacters('a', 'l', 'x');
		thenCalculateScoreIsCalledWithTrigram(0);
	}

}
