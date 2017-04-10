package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

//	private Trie trie;
	private HashMap<String, Double> bigrams = new HashMap<String, Double>();
	private HashMap<String, Double> trigrams = new HashMap<String, Double>();
	private Set<String> dictionary = new HashSet<String>();
	private char twoPrevious = '0';
	private char previous = '0';
	private char current = '0';
	private boolean consecutive = false;
	private double score = 0;
	private ArrayList<Character> previousCharacters = new ArrayList<Character>();
	private String formatted;

	/**
	 * Setup.
	 */
	@Before
	public void setup()
	{
//		trie = new Trie(false);
//		trie.insert("example");
//		trie.insert("computer");
//		trie.insert("desk");

		bigrams.put("ab", 0.2);
		bigrams.put("th", 0.3);
		bigrams.put("er", 0.4);
		bigrams.put("om", 0.33);

		trigrams.put("the", 1.3);
		trigrams.put("com", 0.05);
		trigrams.put("tra", 2.5);
		trigrams.put("fab", 0.78);
		
		dictionary.add("example");
		dictionary.add("an");
		dictionary.add("words");
		dictionary.add("computer");
		dictionary.add("desk");
		dictionary.add("bike");

		previousCharacters.add('r');
		previousCharacters.add('a');
		previousCharacters.add('t');
		previousCharacters.add('t');
		previousCharacters.add('l');
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
	 * When consecutive letters is called with a word.
	 *
	 * @param word the word
	 */
	public void whenConsecutiveLettersIsCalled(String word)
	{
		consecutive = scoreHandler.consecutiveLetters(word);
	}
	
	/**
	 * When format string is called.
	 *
	 * @param current the current
	 * @param length the length
	 */
	public void whenFormatStringIsCalled(char current, int length)
	{
		formatted = scoreHandler.formatString(previousCharacters, current, length);
	}
	
	
	/**
	 * When search is called.
	 *
	 * @param word the word
	 */
	public void whenSearchIsCalled(String word)
	{
		score += scoreHandler.search(word, dictionary);
	}
	
	/**
	 * Then the list is formatted correctly.
	 *
	 * @param expected the expected
	 * @param expectedLength the expected length
	 */
	public void thenTheListIsFormattedCorrectly(String expected, int expectedLength)
	{
		assertEquals(expected, formatted);
		assertEquals(expectedLength, formatted.length());
	}
	
	/**
	 * Then search returns the correct score.
	 *
	 * @param expected the expected
	 */
	public void thenTheCorrectScoreIsReturned(double expected)
	{
		assertEquals(expected, score, 0);
	}
	
	
	/**
	 * Then consecutive returns.
	 *
	 * @param expected the expected
	 */
	public void thenConsecutiveReturns(boolean expected)
	{
		assertEquals(expected, consecutive);
	}

	/**
	 * Then calculate score is called with bigram.
	 *
	 * @param input the input
	 */
	public void whenMapSearchIsCalledWithBigram(String input)
	{
		score += scoreHandler.mapSearch(bigrams, input);
	}

	/**
	 * Then calculate score is called with trigram.
	 *
	 * @param expected
	 *            the expected
	 */
	public void whenMapSearchIsCalledWithTrigram(String input)
	{
		score += scoreHandler.mapSearch(trigrams, input);
	}

	/**
	 * Test mapSearch works correctly when the characters are
	 * in the bigram.
	 */
	@Test
	public void testCharactersInBigrams()
	{
		whenMapSearchIsCalledWithBigram("om");
		thenTheCorrectScoreIsReturned(2*0.33);
	}

	/**
	 * Test mapSearch works correctly when the characters are
	 * not in the bigram.
	 */
	@Test
	public void testCharactersNotInBigram()
	{
		whenMapSearchIsCalledWithBigram("ar");
		thenTheCorrectScoreIsReturned(0);
	}

	/**
	 * Test mapSearch works correctly when the characters are
	 * in the trigram and bigram.
	 */
	@Test
	public void testCharactersInTrigramAndBigram()
	{
		double expected = (2 * 0.2) + (3 * 0.78);
		whenMapSearchIsCalledWithBigram("ab");
		whenMapSearchIsCalledWithTrigram("fab");
		thenTheCorrectScoreIsReturned(expected);
	}

	/**
	 * Test mapSearch works correctly when the characters are
	 * in the trigram but not the bigram.
	 */
	@Test
	public void testCharactersInTrigramButNotBigram()
	{
		double expected = 3 * 2.5;
		whenMapSearchIsCalledWithBigram("ra");
		whenMapSearchIsCalledWithTrigram("tra");
		thenTheCorrectScoreIsReturned(expected);
	}

	/**
	 * Test mapSearch works correctly when the characters are
	 * in the bigram but not the trigram.
	 */
	@Test
	public void testCharactersInBigramButNotTrigram()
	{
		double expected = 2 * 0.4;
		whenMapSearchIsCalledWithBigram("er");
		whenMapSearchIsCalledWithTrigram("eer");	
		thenTheCorrectScoreIsReturned(expected);
	}

	/**
	 * Test mapSearch works correctly when the characters are
	 * in neither the bigram nor the trigram.
	 */
	@Test
	public void testCharactersInNeitherMap()
	{
		whenMapSearchIsCalledWithBigram("lx");
		whenMapSearchIsCalledWithTrigram("alx");	
		thenTheCorrectScoreIsReturned(0);
	}
	
	/**
	 * Test consecutive letters returns true when consecutive.
	 */
	@Test
	public void testConsecutiveLettersReturnsTrueWhenConsecutive()
	{
		whenConsecutiveLettersIsCalled("testtt");
		thenConsecutiveReturns(true);
	}
	
	/**
	 * Test consecutive letters returns false when not consecutive.
	 */
	@Test
	public void testConsecutiveLettersReturnsFalseWhenNotConsecutive()
	{
		whenConsecutiveLettersIsCalled("test");
		thenConsecutiveReturns(false);
	}
	
	/**
	 * Test consecutive letters returns true when word length is one.
	 */
	@Test
	public void testConsecutiveLettersReturnsTrueWhenWordLengthIsOne()
	{
		whenConsecutiveLettersIsCalled("t");
		thenConsecutiveReturns(true);
	}
	
	/**
	 * Test search returns correct score when word found amongst a string.
	 */
	@Test
	public void testSearchReturnsCorrectScoreWhenWordFoundInString()
	{
		whenSearchIsCalled("spexample");
		thenTheCorrectScoreIsReturned(7);
	}
	
	/**
	 * Test search returns correct score when word found.
	 */
	@Test
	public void testSearchReturnsCorrectScoreWhenWordFound()
	{
		whenSearchIsCalled("bike");
		thenTheCorrectScoreIsReturned(4);
	}
	
	/**
	 * Test search returns correct score when word not found.
	 */
	@Test
	public void testSearchReturnsCorrectScoreWhenWordNotFound()
	{
		whenSearchIsCalled("call");
		thenTheCorrectScoreIsReturned(0);
	}
	
	/**
	 * Test format string returns formatted string.
	 */
	@Test 
	public void testFormatStringReturnsFormattedString()
	{
		whenFormatStringIsCalled('e', 5);
		thenTheListIsFormattedCorrectly("rattle", 6);
	}
	
	/**
	 * Test format string returns formatted string with shorter length.
	 */
	@Test 
	public void testFormatStringReturnsFormattedStringWithShorterLength()
	{
		whenFormatStringIsCalled('d', 2);
		thenTheListIsFormattedCorrectly("tld", 3);
	}
	
	@Test
	public void testCalculateScoreReturnsCorrectScore()
	{
		
	}

}
