package main;

import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The Class ScoreHandler.
 */
public class ScoreHandler
{

	/**
	 * Instantiates a new score handler.
	 */
	public ScoreHandler() 
	{

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
	 * Calculate score.
	 *
	 * @param current
	 *            the current
	 * @param previousCharacters
	 *            the previous characters
	 * @param bigrams
	 *            the bigrams
	 * @param trigrams
	 *            the trigrams
	 * @param trie
	 *            the trie
	 * @param fullWords
	 *            the full words
	 * @return the double
	 */
	public double calculateScore(char current, ArrayList<Character> previousCharacters, HashMap<String, Double> bigrams,
			HashMap<String, Double> trigrams, HashSet<String> fullWords)
	{
		double score = 0;
		String wordToFind = formatString(previousCharacters, current, previousCharacters.size());
		boolean consecutive = consecutiveLetters(wordToFind);
		if (previousCharacters.size() == 0 || consecutive)
		{
			return 0;
		}

		if (previousCharacters.size() >= 2)
		{
			String trigramInput = formatString(previousCharacters, current, 2);
			score += mapSearch(trigrams, trigramInput);
			score += search(wordToFind, fullWords);
		}

		if (previousCharacters.size() >= 1)
		{
			String bigramInput = formatString(previousCharacters, current, 1);
			score += mapSearch(bigrams, bigramInput);
		}
		return score;
	}

	/**
	 * Format string.
	 *
	 * @param previousCharacters
	 *            the previous characters
	 * @param current
	 *            the current
	 * @param length
	 *            the length
	 * @return the string
	 */
	private String formatString(ArrayList<Character> previousCharacters, char current, int length)
	{
		StringBuilder stringBuilder = new StringBuilder();
		int start = previousCharacters.size() - length;
		for (int i = start; i < previousCharacters.size(); i++)
		{
			stringBuilder.append(previousCharacters.get(i));
		}
		stringBuilder.append(current);
		return stringBuilder.toString();
	}

	/**
	 * Returns true if there are consecutive letters.
	 *
	 * @param word
	 *            the word
	 * @return true, if there are consecutive letters
	 */
	private boolean consecutiveLetters(String word)
	{
		if (word.length() == 1)
		{
			return true;
		} else if (word.length() > 2)
		{
			char a = word.charAt(word.length() - 1);
			char b = word.charAt(word.length() - 2);
			char c = word.charAt(word.length() - 3);

			if (a == b)
			{
				if (b == c)
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Search.
	 *
	 * @param input
	 *            the input
	 * @param dictionary
	 *            the dictionary
	 * @param words
	 *            the words
	 * @param results
	 *            the results
	 */
	public double search(String input, Set<String> dictionary)
	{
		double score = 0;
		for (int i = 0; i < input.length() - 2; i++)
		{
			String substring = input.substring(i, input.length());

			if (dictionary.contains(substring))
			{
				score = substring.length();
				break;
			} else if (StringUtils.getLevenshteinDistance(substring, input) <= 1)
			{

			}
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
	public double mapSearch(HashMap<String, Double> ngram, String input)
	{
		int multiplier = 2;
		double score = 0;
		if (input.length() == 3)
		{
			multiplier = 4;
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
