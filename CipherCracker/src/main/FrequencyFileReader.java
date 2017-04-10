package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The Class LetterReader.
 */
public class FrequencyFileReader
{

	/**
	 * Instantiates a new letter reader.
	 */
	public FrequencyFileReader()
	{

	}

	/**
	 * Read in letter file containing letter value and frequency as a percentage,
	 * creates the object and adds it to the list to be returned.
	 *
	 * @return the list of letter objects
	 */
	public List<Letter> readInLetterFile()
	{
		List<Letter> letters = new ArrayList<Letter>();
		try
		{
			FileInputStream fstream = new FileInputStream("resources/letters.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				String[] tokens = strLine.split(" : ");
				char value = tokens[0].charAt(0);
				double freq = Double.parseDouble(tokens[1]);
				Letter letter = new Letter(value, freq);
				letters.add(letter);
			}
			in.close();
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
		return letters;

	}
	
	/**
	 * Reads in the NGram files to a hashmap.
	 *
	 * @param file the file
	 * @return the hash map
	 */
	public HashMap<String, Double> readInNGramFiles(File file)
	{
		HashMap<String, Double> nGram = new HashMap<String, Double>();
		try
		{
			FileInputStream fstream = new FileInputStream(file.getPath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				String[] tokens = strLine.split(" : ");
				String value = tokens[0].toLowerCase();
				double freq = Double.parseDouble(tokens[1]);
				nGram.put(value, freq);
			}
			in.close();
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
		return nGram;
		
	}

}
