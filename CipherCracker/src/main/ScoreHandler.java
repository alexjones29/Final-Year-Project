package main;

import java.util.ArrayList;
import java.util.Collections;
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
	 * @param value
	 *            the value
	 * @return the double
	 */
	public double calculateScore(char value)
	{
		return 0;
	}

	/**
	 * Calculates the score if there is using bigram.
	 *
	 * @param previous
	 *            the previous
	 * @param value
	 *            the value
	 * @param bigrams
	 * @param trie
	 * @return the double
	 */
	public double calculateScore(char previous, char value, HashMap<String, Double> bigrams, Trie trie)
	{
		String input = new StringBuilder().append(previous).append(value).toString();
		double score = mapSearch(bigrams, input);
		// score = trieSearch(trie, input, score);

		return score;

	}

	/**
	 * Calculates the score if there is using bigram and trigram.
	 *
	 * @param twoCharsPrev
	 *            the two chars prev
	 * @param previous
	 *            the previous
	 * @param value
	 *            the value
	 * @param trigrams
	 * @param bigrams
	 * @param trie
	 * @return the double
	 */
	public double calculateScore(char twoCharsPrev, char previous, char value, HashMap<String, Double> bigrams,
			HashMap<String, Double> trigrams, Trie trie)
	{

		String bigramInput = new StringBuilder().append(previous).append(value).toString();
		String trigramInput = new StringBuilder().append(twoCharsPrev).append(previous).append(value).toString();
		double score = mapSearch(bigrams, bigramInput);
		// do this call from cracker app instead, after calculate score
		// score += trieSearch(trie, bigramInput, score);
		// score += trieSearch(trie, trigramInput, score);
		score += mapSearch(trigrams, trigramInput);
		return score;

	}

	private double searchBigram(ArrayList<Character> previousCharacters, HashMap<String, Double> bigrams, HashMap<String, Double> trigrams)
	{
		double score = 0;
		
		return 0;
	}

	public double calculateScore(char current, ArrayList<Character> previousCharacters, HashMap<String, Double> bigrams,
			HashMap<String, Double> trigrams, Trie trie)
	{
		double score = 0;
		
		//format into ifs to account for string sizes and that
		String wordToFind = formatString(previousCharacters, current, previousCharacters.size());
		String trigramInput = formatString(previousCharacters, current, 3);
		String bigramInput = formatString(previousCharacters, current, 2);
		
		score += trieSearch(trie, wordToFind, score);
		score += mapSearch(bigrams, bigramInput);
		score += mapSearch(trigrams, trigramInput);
		return 0;
	}
	
	private String formatString(ArrayList<Character> previousCharacters, char current, int length)
	{
		StringBuilder stringBuilder = new StringBuilder();
		int start = previousCharacters.size() - length;
		start++;
		for (int i = start; i<previousCharacters.size();i++)
		{
			stringBuilder.append(previousCharacters.get(i));
		}
		stringBuilder.append(current);
		return stringBuilder.toString();
	}

	/**
	 * Searches the trie for the given word and returns a score based on its
	 * presence and length.
	 *
	 * @param trie
	 *            the trie
	 * @param wordToFind
	 *            the bigram input
	 * @param score
	 *            the score
	 * @return the double
	 */
	private double trieSearch(Trie trie, String wordToFind, double score)
	{
		if (trie.contains(wordToFind))
		{
			score += wordToFind.length();
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
