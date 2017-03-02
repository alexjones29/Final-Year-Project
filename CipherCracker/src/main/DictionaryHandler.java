package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * The Class DictionaryHandler.
 */
public class DictionaryHandler
{
	
	/**
	 * Instantiates a new dictionary handler.
	 */
	public DictionaryHandler()
	{
		
	}
	
	/**
	 * Read in dictionary.
	 * @param dictionaryWords 
	 */
	public Set<String> readInDictionary(Set<String> dictionaryWords)
	{
		Scanner filescan;
		try
		{
			filescan = new Scanner(new File("resources/dictionary.txt"));
			while (filescan.hasNext())
			{
				dictionaryWords.add(filescan.nextLine().toLowerCase());
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return dictionaryWords;
	}
	
	/**
	 * Reads in the dictionary to an empty trie and returns the resulting one.
	 *
	 * @param trie the trie
	 * @return the trie
	 */
	public Trie readInDictionary(Trie trie)
	{
		Scanner filescan;
		try
		{
			filescan = new Scanner(new File("resources/dictionary.txt"));
			while (filescan.hasNext())
			{
				trie.insert(filescan.nextLine().toLowerCase());
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		System.out.println(trie.size());
		return trie;
	}
	
	/**
	 * Search.
	 *
	 * @param input the input
	 * @param dictionary the dictionary
	 * @param words the words
	 * @param results the results
	 */
	public void search(String input, Set<String> dictionary, Stack<String> words, List<List<String>> results)
	{

		for (int i = 0; i < input.length(); i++)
		{
			// take the first i characters of the input and see if it is a word
			String substring = input.substring(0, i + 1);

			if (substring.length() > 1 && (!substring.equals('a') || !substring.equals('i')))
			{

				if (dictionary.contains(substring))
				{
					// the beginning of the input matches a word, store on stack
					words.push(substring);

					if (i == input.length() - 1)
					{
						// there's no input left, copy the words stack to
						// results
						results.add(new ArrayList<String>(words));
					} else
					{
						// there's more input left, search the remaining part
						search(input.substring(i + 1), dictionary, words, results);
					}

					// pop the matched word back off so we can move onto the
					// next i
					words.pop();
				}
			}
		}
	}

}
