package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class CrackerApplication
{

	List<CipherSymbol> cipherText = new ArrayList<CipherSymbol>();
	Set<String> dictionary = new HashSet<String>();
	private CiphertextReader cipherReader;

	public CrackerApplication()
	{

	}
	
	public void runApplication()
	{
		cipherReader = new CiphertextReader();
		File cipherFile = new File("resources/340cipherascii.txt");
		readInCiphertext(cipherFile);
	}

	public void readInCiphertext(File cipherFile)
	{

		cipherText = cipherReader.readInCipherText(cipherFile);
		readInDictionary();
		Frequency frequency = new Frequency();
		frequency.calculateSymbolFrequency(cipherText);
		
	}

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

	public void readInDictionary()
	{
		// load the dictionary into a set for fast lookups
		Scanner filescan;
		try
		{
			filescan = new Scanner(new File("resources/dictionary.txt"));
			while (filescan.hasNext())
			{
				dictionary.add(filescan.nextLine().toLowerCase());
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
//		// scan for input
//		Scanner scan = new Scanner(System.in);
//		System.out.print("Enter a string: ");
//		String input = scan.next().toLowerCase();
//		System.out.println();
//
//		// place to store list of results, each result is a list of strings
//		List<List<String>> results = new ArrayList<List<String>>();
//
//		// start the search, pass empty stack to represent words found so far
//		search(input, dictionary, new Stack<String>(), results);
//
//		// list the results found
//		for (List<String> result : results)
//		{
//			for (String word : result)
//			{
//				System.out.print(word + " ");
//			}
//			System.out.println("(" + result.size() + " words)");
//		}
//		System.out.println();
	}
}
