package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * The Class CrackerApplication.
 */
public class CrackerApplication
{

	List<CipherSymbol> cipherText = new ArrayList<CipherSymbol>();
	List<Letter> letters = new ArrayList<Letter>();
	Set<String> dictionaryWords = new HashSet<String>();
	private CiphertextReader cipherReader;
	private DictionaryHandler dictionary = new DictionaryHandler();
	private Trie trie;
	/**
	 * Instantiates a new cracker application.
	 */
	public CrackerApplication()
	{

	}
	
	/**
	 * Run application.
	 */
	public void runApplication()
	{
		trie = new Trie(false);
		cipherReader = new CiphertextReader();
		File cipherFile = new File("resources/encryptedpassage.txt");
		readInLettersAndFrequencies();
		readInCiphertextAndDictionary(cipherFile);
		calculateFrequency();
		testSearch();
	}
	
	private void testSearch()
	{
		  Scanner scan = new Scanner(System.in);
		    System.out.print("Enter a string: ");
		    String input = scan.next().toLowerCase();
		    System.out.println();
		    
		    if(trie.contains(input)){
		    	System.out.println("match found:" + input);
		    }
	}

	/**
	 * Read in letters and frequencies.
	 */
	public void readInLettersAndFrequencies()
	{
	   LetterReader letterReader = new LetterReader();
	   letters = letterReader.readInLetterFile();
	}

	/**
	 * Read in ciphertext.
	 *
	 * @param cipherFile the cipher file
	 */
	public void readInCiphertextAndDictionary(File cipherFile)
	{
		cipherText = cipherReader.readInCipherText(cipherFile);
//		dictionary.readInDictionary(dictionaryWords);
		trie = dictionary.readInDictionary(trie);
	}

	/**
	 * Calculates the symbol frequency of the cipher text.
	 */
	private void calculateFrequency()
	{
		Frequency frequency = new Frequency();
		frequency.calculateSymbolFrequency(cipherText);
	}

	
}
