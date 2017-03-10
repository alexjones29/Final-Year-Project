package main;

import java.util.ArrayList;
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


	public double calculateScore(char current, ArrayList<Character> previousCharacters, HashMap<String, Double> bigrams,
			HashMap<String, Double> trigrams, Trie trie)
	{
		double score = 0;
		if (previousCharacters.size() == 0)
		{
			return 0;
		}
		
		if (previousCharacters.size()>=2)
		{
			String trigramInput = formatString(previousCharacters, current, 2);
			score += mapSearch(trigrams, trigramInput);
		}
		
		if (previousCharacters.size()>=1)
		{
			String wordToFind = formatString(previousCharacters, current, previousCharacters.size());
			score += trieSearch(trie, wordToFind, score);
			String bigramInput = formatString(previousCharacters, current, 1);
			score += mapSearch(bigrams, bigramInput);
		}
		return score;
	}
	
	private String formatString(ArrayList<Character> previousCharacters, char current, int length)
	{
		StringBuilder stringBuilder = new StringBuilder();
		int start = previousCharacters.size() - length;
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
	
	private double nearMatch(Trie trie)
	{
		
		
		
		return 0;
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
