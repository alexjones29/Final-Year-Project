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
	private InitialKey initialKey = new InitialKey();

	/**
	 * Instantiates a new cracker application.
	 */
	public CrackerApplication() {

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
		cipherText = initialKey.createInitialKey(cipherText, letters);
		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getPlaintextValue());
		}

		// testSearch();
	}

	private void testSearch()
	{
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a string: ");
		String input = scan.next().toLowerCase();
		System.out.println();

		if (trie.contains(input))
		{
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
	 * @param cipherFile
	 *            the cipher file
	 */
	public void readInCiphertextAndDictionary(File cipherFile)
	{
		cipherText = cipherReader.readInCipherText(cipherFile);
		// dictionary.readInDictionary(dictionaryWords);
		trie = dictionary.readInDictionary(trie);
	}

	public void hillClimb()
	{
		char twoCharsPrev = 0;
		char previous = 0;
		ScoreHandler scorer = new ScoreHandler();
		
		for (int i=0;i<cipherText.size();i++)
		{
			char currentBestLetter = cipherText.get(i).getPlaintextValue();
			int currentBestScore = cipherText.get(i).getBestScore();
			int currentScore = 0;
			char globalBestLetter = cipherText.get(i).getPlaintextValue();
			int globalBestScore = cipherText.get(i).getPlaintextValue();
			
			for (Letter letter : letters)
			{
				if(twoCharsPrev!=0)
				{
					currentScore = scorer.calculateScore(twoCharsPrev, previous, letter.getValue());
					
				}
				else if (previous!=0 && twoCharsPrev==0)
				{
					currentScore = scorer.calculateScore(previous, letter.getValue());
				}
				else 
				{
					currentScore = scorer.calculateScore(letter.getValue());
				}
				
				if (currentScore > currentBestScore)
				{
					currentBestScore = currentScore;
					currentBestLetter = letter.getValue();
				}
			}
			
			if (currentIsBest(currentBestScore,cipherText.get(i).getBestScore()))
			{
				cipherText.get(i).setBestScore(currentBestScore);
				cipherText.get(i).setPlaintextValue(currentBestLetter);
				globalBestLetter = currentBestLetter;
				globalBestScore = currentBestScore;
			}
			
			for (CipherSymbol sym : cipherText)
			{
				if (sym.getSymbolValue() == cipherText.get(i).getSymbolValue())
				{
					if (!currentIsBest(currentBestScore, sym.getBestScore()))
					{
						globalBestScore = sym.getBestScore();
						globalBestLetter = sym.getPlaintextValue();
					}
				}
			}
			
			setSameSymbols(i, globalBestLetter, globalBestScore);
			
			twoCharsPrev = previous;
			previous = globalBestLetter;
		}
	}

	private void setSameSymbols(int i, char globalBestLetter, int globalBestScore)
	{
		for (CipherSymbol sym : cipherText)
		{
			if (sym.getSymbolValue() == cipherText.get(i).getSymbolValue())
			{
				sym.setBestScore(globalBestScore);
				sym.setPlaintextValue(globalBestLetter);
			}
		}
	}

	private boolean currentIsBest(int current, int bestScore)
	{
		if (current > bestScore || bestScore == 0)
		{
			return true;
		}
		return false;
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
