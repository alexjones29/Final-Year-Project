package main;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ScoreHandler.
 */
public class ScoreHandler
{

	public ScoreHandler() {

	}

	/**
	 * Calculate score.
	 *
	 * @param value the value
	 * @return the double
	 */
	public double calculateScore(char value)
	{
		return 0;
	}

	/**
	 * Calculates the score if there is using bigram.
	 *
	 * @param previous the previous
	 * @param value the value
	 * @param bigrams 
	 * @param trie 
	 * @return the double
	 */
	public double calculateScore(char previous, char value, HashMap<String,Double> bigrams, Trie trie)
	{
		String input = new StringBuilder().append(previous).append(value).toString();
		double score = mapSearch(bigrams, input);
//		score = trieSearch(trie, input, score);
		
		return score;

	}

	/**
	 * Calculates the score if there is using bigram and trigram.
	 *
	 * @param twoCharsPrev the two chars prev
	 * @param previous the previous
	 * @param value the value
	 * @param trigrams 
	 * @param bigrams 
	 * @param trie 
	 * @return the double
	 */
	public double calculateScore(char twoCharsPrev, char previous, char value, HashMap<String,Double> bigrams, HashMap<String,Double> trigrams, Trie trie)
	{
		
		String bigramInput = new StringBuilder().append(previous).append(value).toString();
		String trigramInput = new StringBuilder().append(twoCharsPrev).append(previous).append(value).toString();
		double score = mapSearch(bigrams, bigramInput);
		// do this call from cracker app instead, after calculate score 
//		score += trieSearch(trie, bigramInput, score);
//		score += trieSearch(trie, trigramInput, score);
		score += mapSearch(trigrams, trigramInput);
		return score;

	}

	/**
	 * Searches the trie for the given word and returns a score based on its presence and length.
	 *
	 * @param trie the trie
	 * @param bigramInput the bigram input
	 * @param score the score
	 * @return the double
	 */
	private double trieSearch(Trie trie, String bigramInput, double score)
	{
		if (trie.contains(bigramInput))
		{
			score+=bigramInput.length();
		}
		return score;
	}

	/**
	 * Searches the map passed in for the given input. If the input is found
	 * then the score is increased based on the value and a multiplier, 2 if it
	 * is a bigram and 3 if it is a trigram.
	 *
	 * @param ngram
	 *            the ngram
	 * @param input
	 *            the input
	 * @return the double
	 */
	private double mapSearch(HashMap<String, Double> ngram, String input)
	{
		int multiplier = 2;
		double score = 0;
		if (input.length() == 3)
		{
			multiplier = 3;
		}

		for (Map.Entry<String, Double> entry : ngram.entrySet())
		{
			String key = entry.getKey();
			if (key.equals(input))
			{
				double value = entry.getValue();
				score = value * multiplier;
			}
		}
		return score;
	}

}
