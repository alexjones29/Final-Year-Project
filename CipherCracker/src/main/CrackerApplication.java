package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The Class CrackerApplication.
 */
public class CrackerApplication
{

	private List<Letter> letters = new ArrayList<Letter>();
	private CiphertextReader cipherReader;
	private DictionaryHandler dictionary = new DictionaryHandler();
	private Trie trie;
	private InitialKey initialKey = new InitialKey();
	private HashMap<String, Double> bigrams = new HashMap<String, Double>();
	private HashMap<String, Double> trigrams = new HashMap<String, Double>();
	private HashSet<String> fullWords = new HashSet<String>();

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
		List<CipherSymbol> cipherText = new ArrayList<CipherSymbol>();
		File cipherFile = initialiseObjects();
		letters = readInLettersAndFrequencies();
		cipherText = readInCiphertextAndDictionary(cipherFile, cipherText);
		cipherText = calculateFrequency(cipherText);
		cipherText = initialKey.createInitialKey(cipherText, letters);
		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getSymbolValue() + " : " + sym.getPlaintextValue());
		}
		Frequency freq = new Frequency();
		int consecutive = 0;
		double bestScore = scoreRunThrough(cipherText);
		List<CipherSymbol> newCipherText = new ArrayList<CipherSymbol>();
		while (bestScore < 199)
		{
			cipherText = calculatePlaintextFrequency(cipherText);
			newCipherText = cipherText;
			newCipherText = freq.findSwappableNodes(newCipherText, letters, 2);
			
			if (newCipherText == null || newCipherText.isEmpty())
			{
				newCipherText = hillClimb(cipherText);
			} 
			
			double score = scoreRunThrough(newCipherText);

			if (score > bestScore)
			{
				cipherText = newCipherText;
				bestScore = score;
				consecutive = 0;
				for (CipherSymbol sym : cipherText)
				{
					System.out.println(sym.getPlaintextValue());
				}
			} else
			{
				consecutive++;
				if (consecutive >= 100)
				{
					consecutive = 0;
					cipherText = hillClimb(cipherText);
					System.out.println("got stuck");
					// randomRestart
				}
			}

		}
		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getPlaintextValue());
		}

		// testSearch();
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

		if (trie.containsWord(input))
		{
			System.out.println("match found:" + input);
		}
		System.out.println("match not found");
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
	public List<CipherSymbol> readInCiphertextAndDictionary(File cipherFile, List<CipherSymbol> cipherText)
	{
		cipherText = cipherReader.readInCipherText(cipherFile);
		trie = dictionary.readInDictionary(trie);
		fullWords = dictionary.readInDictionary(fullWords);
		return cipherText;
	}

	/**
	 * This method performs the basic hill climbing.
	 */
	public List<CipherSymbol> hillClimb(List<CipherSymbol> cipherText)
	{
		ScoreHandler scorer = new ScoreHandler();
		int randomPosition = getRandomPosition(cipherText);

		char currentBestLetter = 0;
		double currentBestScore = 0;
		double currentScore = 0;
		ArrayList<Character> previousCharacters = new ArrayList<Character>();
		previousCharacters = previousLetters(cipherText, randomPosition, 6);

		for (Letter letter : letters)
		{
			if (previousCharacters.size() == 0)
			{
				break;
			}
			currentScore = scorer.calculateScore(letter.getValue(), previousCharacters, bigrams, trigrams, trie,
					fullWords);

			if (currentScore >= currentBestScore)
			{
				currentBestScore = currentScore;
				currentBestLetter = letter.getValue();
			}
		}

		if (currentBestScore == 0)
		{
			double[] weight = new double[26];

			for (int pos = 0; pos < letters.size(); pos++)
			{
				double temp = letters.get(pos).getFrequency();
				weight[pos] = temp;
			}
			int position = initialKey.rouletteSelect(weight);
			currentBestLetter = letters.get(position).getValue();
		}

		cipherText.get(randomPosition).setPlaintextValue(currentBestLetter);

		cipherText = setSameSymbols(randomPosition, currentBestLetter, currentBestScore, cipherText);
		System.out.println("round 2");

		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getPlaintextValue());
		}

		return cipherText;
	}

	private int getRandomPosition(List<CipherSymbol> cipherText)
	{
		Random rand = new Random();

		int randomPosition = rand.nextInt(cipherText.size());
		return randomPosition;
	}

	private ArrayList<Character> previousLetters(List<CipherSymbol> symbols, int position, int amount)
	{
		ArrayList<Character> characters = new ArrayList<Character>();
		position--;
		for (int counter = position; counter >= 0; counter--)
		{
			if (characters.size() >= amount || symbols.get(counter).isInWord())
			{
				break;
			} else
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
	 * @return
	 */
	private List<CipherSymbol> setSameSymbols(int position, char globalBestLetter, double globalBestScore,
			List<CipherSymbol> cipherText)
	{
		for (CipherSymbol sym : cipherText)
		{
			if (sym.getSymbolValue() == cipherText.get(position).getSymbolValue())
			{
				sym.setBestScore(globalBestScore);
				sym.setPlaintextValue(globalBestLetter);
			}
		}

		return cipherText;
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
	 * 
	 * @return
	 */
	private List<CipherSymbol> calculateFrequency(List<CipherSymbol> cipherText)
	{
		Frequency frequency = new Frequency();
		frequency.calculateSymbolFrequency(cipherText);
		return cipherText;
	}

	/**
	 * Calculates the symbol frequency of the cipher text.
	 * 
	 * @return
	 */
	private List<CipherSymbol> calculatePlaintextFrequency(List<CipherSymbol> cipherText)
	{
		Frequency frequency = new Frequency();
		frequency.calculatePlaintextFrequency(cipherText);
		return cipherText;
	}

	private double scoreRunThrough(List<CipherSymbol> cipherText)
	{
		ScoreHandler scoreHandler = new ScoreHandler();
		StringBuilder text = new StringBuilder();
		double score = 0;
		for (int i = 0; i < cipherText.size(); i++)
		{
			text.append(cipherText.get(i).getPlaintextValue());
		}

		String fullText = text.toString();
		for (String diction : fullWords)
		{
			if (diction.length() > 2)
			{
				if (fullText.contains(diction))
				{
					score += diction.length();
				}
			}
		}

		HashSet<String> textTrigrams = new HashSet<>();
		for (int position = 2; position < cipherText.size(); position++)
		{
			List<Character> chars = previousLetters(cipherText, position, 2);
			StringBuilder tri = new StringBuilder().append(chars.get(0)).append(chars.get(1))
					.append(cipherText.get(position).getPlaintextValue());
			textTrigrams.add(tri.toString());
		}

		HashSet<String> textBigrams = new HashSet<>();
		for (int position = 1; position < cipherText.size(); position++)
		{
			List<Character> chars = previousLetters(cipherText, position, 1);
			StringBuilder bi = new StringBuilder().append(chars.get(0)).append(cipherText.get(position));
			textBigrams.add(bi.toString());
		}

		for (String tris : textTrigrams)
		{
			score += scoreHandler.mapSearch(trigrams, tris);
		}

		for (String bis : textBigrams)
		{
			score += scoreHandler.mapSearch(bigrams, bis);
		}

		return score;
	}

}
