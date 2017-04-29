package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * The Class CrackerApplication.
 */
public class CrackerApplication
{

	private List<Letter> letters = new ArrayList<Letter>();
	private List<String> cribs = new ArrayList<String>();
	private CiphertextReader cipherReader;
	private DictionaryHandler dictionary = new DictionaryHandler();
	private InitialKey initialKey = new InitialKey();
	private HashMap<String, Double> bigrams = new HashMap<String, Double>();
	private HashMap<String, Double> trigrams = new HashMap<String, Double>();
	private HashSet<String> fullWords = new HashSet<String>();
	private long startTime;
	
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
		File crib = new File("resources/cribs.txt");
		if(crib.exists() && !crib.isDirectory()) { 
			cribs = dictionary.readInCrib(crib);
		}
		cipherText = readInCiphertextAndDictionary(cipherFile, cipherText);
		cipherText = calculateFrequency(cipherText);
		cipherText = initialKey.readInFixedLetterFile(cipherText);
		cipherText = initialKey.createInitialKey(cipherText, letters);
		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getSymbolValue() + " : " + sym.getPlaintextValue());
		}
		cipherText = performHillClimb(cipherText);
		
		System.out.println("DONE");
		System.out.println("==================");
		for (CipherSymbol sym : cipherText)
		{
			System.out.println(sym.getPlaintextValue());
		}
	}

	/**
	 * Performs the main hill climb.
	 *
	 * @param cipherText the cipher text
	 * @return the list
	 */
	private List<CipherSymbol> performHillClimb(List<CipherSymbol> cipherText)
	{
		Frequency freq = new Frequency();
		int consecutive = 0;
		double bestScore = scoreRunThrough(cipherText);
		appendScore(bestScore);
		List<CipherSymbol> newCipherText = new ArrayList<CipherSymbol>();
		while (bestScore < 255)
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
				appendScore(bestScore);
				consecutive = 0;
				for (CipherSymbol sym : cipherText)
				{
					System.out.println(sym.getPlaintextValue());
				}
			} else
			{
				consecutive++;
				if (consecutive >= 2000)
				{
					consecutive = 0;
					cipherText = randomRestart(cipherText, bestScore);
					bestScore = scoreRunThrough(cipherText);
					appendScore(bestScore);
				}
			}
		}
		printOutText(cipherText, bestScore);
		return cipherText;
	}
	
	public void appendScore(double score)
	{
		try(FileWriter fw = new FileWriter("resources/scores.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw))
			{
				long currentTime = System.nanoTime();
				long elapsed = currentTime - startTime;
				double seconds = (double)elapsed / 1000000000.0;
			    out.println(score + ":" + seconds);
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
	}

	/**
	 * Prints the out text.
	 *
	 * @param cipherText the cipher text
	 * @param bestScore the best score
	 */
	private void printOutText(List<CipherSymbol> cipherText, double bestScore)
	{
		try
		{
			PrintWriter out = new PrintWriter("resources/attempts/" +bestScore+"attempt.txt");
			String text = convertListToString(cipherText);
			out.println(text);
			out.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}

		
	}

	/**
	 * Convert list to string.
	 *
	 * @param cipherText the cipher text
	 * @return the string
	 */
	private String convertListToString(List<CipherSymbol> cipherText)
	{
		StringBuilder text = new StringBuilder();
		for (CipherSymbol symbol: cipherText)
		{
			text.append(symbol.getPlaintextValue());
		}
		return text.toString();
	}

	/**
	 * Random restart.
	 *
	 * @param cipherText the cipher text
	 * @param bestScore the best score
	 * @return the list
	 */
	private List<CipherSymbol> randomRestart(List<CipherSymbol> cipherText, double bestScore)
	{
		List<CipherSymbol> text = cipherText;
		double score = 0;
		for (int i = 0; i<100; i++)
		{
			text = hillClimb(text);
			score = scoreRunThrough(cipherText);

			if (score > bestScore)
			{
				return text;
			}
			
		}
		return text;
	}

	/**
	 * Initialise objects.
	 *
	 * @return the file
	 */
	private File initialiseObjects()
	{
		cipherReader = new CiphertextReader();
		File cipherFile = new File("resources/408cipher.txt");
		return cipherFile;
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
		fullWords = dictionary.readInDictionary(fullWords);
		return cipherText;
	}

	/**
	 * This method performs the basic hill climbing.
	 */
	public List<CipherSymbol> hillClimb(List<CipherSymbol> cipherText)
	{
		ScoreHandler scorer = new ScoreHandler();
		int randomPosition = 0;
		randomPosition = getRandomPosition(cipherText);
		
		while (cipherText.get(randomPosition).isFixed())
		{
			randomPosition = getRandomPosition(cipherText);
		}
		
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
			currentScore = scorer.calculateScore(letter.getValue(), previousCharacters, bigrams, trigrams,
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

	/**
	 * Gets the random position.
	 *
	 * @param cipherText the cipher text
	 * @return the random position
	 */
	private int getRandomPosition(List<CipherSymbol> cipherText)
	{
		Random rand = new Random();
		int randomPosition = rand.nextInt(cipherText.size());
		return randomPosition;
	}

	/**
	 * Returns the specified amount of previous letters.
	 *
	 * @param symbols the symbols
	 * @param position the position
	 * @param amount the amount
	 * @return the array list
	 */
	private ArrayList<Character> previousLetters(List<CipherSymbol> symbols, int position, int amount)
	{
		ArrayList<Character> characters = new ArrayList<Character>();
		position--;
		for (int counter = position; counter >= 0; counter--)
		{
			if (characters.size() >= amount)
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
	 * Calculates the symbol frequency of the cipher text.
	 * 
	 * @return the ciphertext
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
	 * @return the ciphertext
	 */
	private List<CipherSymbol> calculatePlaintextFrequency(List<CipherSymbol> cipherText)
	{
		Frequency frequency = new Frequency();
		frequency.calculatePlaintextFrequency(cipherText);
		return cipherText;
	}

	/**
	 * Scores the hill climb run through.
	 *
	 * @param cipherText the cipher text
	 * @return the score
	 */
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
		for (String crib: cribs)
		{
			if (fullText.contains(crib))
			{
				score+=60;
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
