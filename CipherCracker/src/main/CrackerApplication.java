package main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * The Class CrackerApplication.
 */
public class CrackerApplication
{

	private List<CipherSymbol> cipherText = new ArrayList<CipherSymbol>();
	private List<Letter> letters = new ArrayList<Letter>();
	// private Set<String> dictionaryWords = new HashSet<String>();
	private CiphertextReader cipherReader;
	private DictionaryHandler dictionary = new DictionaryHandler();
	private Trie trie;
	private InitialKey initialKey = new InitialKey();
	private HashMap<String, Double> bigrams = new HashMap<String, Double>();
	private HashMap<String, Double> trigrams = new HashMap<String, Double>();

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
		File cipherFile = initialiseObjects();
		letters = readInLettersAndFrequencies();
		readInCiphertextAndDictionary(cipherFile);
		calculateFrequency();
		cipherText = initialKey.createInitialKey(cipherText, letters);
		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getPlaintextValue());
		}
	}

	/**
	 * Initialise objects.
	 *
	 * @return the file
	 */
	private File initialiseObjects()
	{
		trie = new Trie(false);
		cipherReader = new CiphertextReader();
		File cipherFile = new File("resources/encryptedpassage.txt");
		return cipherFile;
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
	 * @return a list of letters
	 */
	public List<Letter> readInLettersAndFrequencies()
	{
		FrequencyFileReader letterReader = new FrequencyFileReader();
		File bigramFile = new File("resources/bigramfrequencies.txt");
		bigrams = letterReader.readInNGramFiles(bigramFile);
		File trigramFile = new File("resources/trigramfrequencies.txt");
		trigrams = letterReader.readInNGramFiles(trigramFile);
		return letterReader.readInLetterFile();
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
		trie = dictionary.readInDictionary(trie);
	}

	/**
	 * This method performs the basic hill climbing
	 */
	public void hillClimb()
	{
		char twoCharsPrev = 0;
		char previous = 0;
		ScoreHandler scorer = new ScoreHandler();

		for (int i = 0; i < cipherText.size(); i++)
		{
			char currentBestLetter = cipherText.get(i).getPlaintextValue();
			int currentBestScore = cipherText.get(i).getBestScore();
			int currentScore = 0;
			char globalBestLetter = cipherText.get(i).getPlaintextValue();
			int globalBestScore = cipherText.get(i).getPlaintextValue();

			for (Letter letter : letters)
			{
				if (twoCharsPrev != 0)
				{
					currentScore = scorer.calculateScore(twoCharsPrev, previous, letter.getValue());

				} else if (previous != 0 && twoCharsPrev == 0)
				{
					currentScore = scorer.calculateScore(previous, letter.getValue());
				} else
				{
					currentScore = scorer.calculateScore(letter.getValue());
				}

				if (currentScore > currentBestScore)
				{
					currentBestScore = currentScore;
					currentBestLetter = letter.getValue();
				}
			}

			if (currentIsBest(currentBestScore, cipherText.get(i).getBestScore()))
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

	/**
	 * Sets the same symbols to have the global best score and letter.
	 *
	 * @param i the i
	 * @param globalBestLetter the global best letter
	 * @param globalBestScore the global best score
	 */
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

	/**
	 * Returns true if the first number passed in(the current one) is greater
	 * than the second one, or if the bestScore is 0.
	 *
	 * @param current
	 *            the current
	 * @param bestScore
	 *            the best score
	 * @return true, if successful
	 */
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
