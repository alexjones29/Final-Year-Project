package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
//		for (CipherSymbol sym : cipherText)
//		{
//			System.out.println(sym.getPlaintextValue());
//		}
		hillClimb();
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
	 * 
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
	 * This method performs the basic hill climbing.
	 */
	public void hillClimb()
	{
		char twoCharsPrev = 0;
		char previous = 0;
		ScoreHandler scorer = new ScoreHandler();

		for (int i = 0; i < cipherText.size(); i++)
		{
			char currentBestLetter = 0;
			double currentBestScore = 0;
			double currentScore = 0;
			char globalBestLetter = cipherText.get(i).getPlaintextValue();
			double globalBestScore = cipherText.get(i).getBestScore();

			for (Letter letter : letters)
			{
				ArrayList<Character> previousCharacters = new ArrayList<Character>();
				previousCharacters = previousLetters(cipherText, i);
				currentScore = scorer.calculateScore(letter.getValue(), previousCharacters, bigrams, trigrams, trie);
//				if (twoCharsPrev != 0)
//				{
//					currentScore = scorer.calculateScore(twoCharsPrev, previous, letter.getValue(), bigrams, trigrams,
//							trie);
//
//				} else if (previous != 0 && twoCharsPrev == 0)
//				{
//					currentScore = scorer.calculateScore(previous, letter.getValue(), bigrams, trie);
//				}

				if (currentScore > currentBestScore)
				{
					currentBestScore = currentScore;
					currentBestLetter = letter.getValue(); 
				}
			}

			if (currentIsBest(currentBestScore, cipherText.get(i).getBestScore()))
			{
				globalBestLetter = currentBestLetter;
				globalBestScore = currentBestScore;
			}

			if (globalBestScore == 0)
			{
				double[] weight = new double[26];

				for (int pos = 0; pos < letters.size(); pos++)
				{
					double temp = letters.get(pos).getFrequency();
					weight[pos] = temp;
				}
				int position = initialKey.rouletteSelect(weight);
				globalBestLetter = letters.get(position).getValue();
			}

			cipherText.get(i).setBestScore(globalBestScore);
			cipherText.get(i).setPlaintextValue(globalBestLetter);

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
		System.out.println("round 2");

		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getPlaintextValue());
		}
	}
	
	private ArrayList<Character> previousLetters(List<CipherSymbol> symbols, int position)
	{
		// Position need to be -1????? to ignore the current letter we are trying to find?
		ArrayList<Character> characters = new ArrayList<Character>();
		for (int counter = position; counter >=0; counter--)
		{
			if (characters.size() >= 6 || !symbols.get(counter).isInWord())
			{
				break;
			}
			else 
			{
				characters.add(symbols.get(counter).getPlaintextValue());
			}
			
		}
		Collections.reverse(characters);
		return characters;
		
	}

	/**
	 * Sets the same symbols to have the global best score and letter.
	 *
	 * @param position
	 *            the position
	 * @param globalBestLetter
	 *            the global best letter
	 * @param globalBestScore
	 *            the global best score
	 */
	private void setSameSymbols(int position, char globalBestLetter, double globalBestScore)
	{
		for (CipherSymbol sym : cipherText)
		{
			if (sym.getSymbolValue() == cipherText.get(position).getSymbolValue())
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
	private boolean currentIsBest(double current, double bestScore)
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
