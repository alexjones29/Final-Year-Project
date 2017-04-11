package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

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
	public HashSet<String> readInDictionary(HashSet<String> dictionaryWords)
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
	 * Read in crib.
	 *
	 * @param crib the crib
	 * @return the list
	 */
	public List<String> readInCrib(File crib)
	{
		List<String> cribs = new ArrayList<String>();
		Scanner filescan;
		try
		{
			filescan = new Scanner(crib);
			while (filescan.hasNext())
			{
				cribs.add(filescan.nextLine().toLowerCase());
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return cribs;
		
	}
}
