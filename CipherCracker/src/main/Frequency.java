package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The Class Frequency.
 */
public class Frequency
{
	
	/**
	 * Instantiates a new frequency.
	 */
	public Frequency() {

	}

	/**
	 * Calculate symbol frequency of the ciphertext and assigns it to each
	 * symbol object.
	 *
	 * @param ciphertext
	 *            the ciphertext to examine
	 */
	public void calculateSymbolFrequency(List<CipherSymbol> ciphertext)
	{
		Map<Character, Double> occurences = new HashMap<Character, Double>();
		for (CipherSymbol cipher : ciphertext)
		{
			if (occurences.containsKey(cipher.getSymbolValue()))
			{
				double temp = occurences.get(cipher.getSymbolValue());
				temp++;
				occurences.put(cipher.getSymbolValue(), temp);
			} else
			{
				occurences.put(cipher.getSymbolValue(), 1.0);
			}
		}

		for (Map.Entry<Character, Double> entry : occurences.entrySet())
		{
			for (CipherSymbol cipher : ciphertext)
			{
				if (cipher.getSymbolValue() == entry.getKey())
				{
					double value = ((entry.getValue() * 100) / ciphertext.size());
					cipher.setFrequency(value);
				}
			}
		}
	}

	/**
	 * Calculates plaintext letter frequencies and assigns it to the relevant
	 * filed on the symbol object.
	 *
	 * @param ciphertext
	 *            the ciphertext
	 */
	public void calculatePlaintextFrequency(List<CipherSymbol> ciphertext)
	{

		Map<Character, Double> occurences = new HashMap<Character, Double>();
		for (CipherSymbol cipher : ciphertext)
		{
			if (occurences.containsKey(cipher.getPlaintextValue()))
			{
				double temp = occurences.get(cipher.getPlaintextValue());
				temp++;
				occurences.put(cipher.getPlaintextValue(), temp);
			} else
			{
				occurences.put(cipher.getPlaintextValue(), 1.0);
			}
		}

		for (Map.Entry<Character, Double> entry : occurences.entrySet())
		{
			for (CipherSymbol cipher : ciphertext)
			{
				if (cipher.getPlaintextValue() == entry.getKey())
				{
					double value = ((entry.getValue() * 100) / ciphertext.size());
					cipher.setPlaintextFrequency(value);
				}
			}
		}
	}

	/**
	 * Finds the swappable nodes based on expected letter frequency vs actual
	 * frequency. Swaps a random symbol on a random letter that is above its
	 * expected frequency with a random symbol on a random letter that is below
	 * its expected frequency.
	 * 
	 * If there is no appropriate swap then returns null
	 *
	 * @param ciphertext
	 *            the ciphertext
	 * @param letters
	 *            the letters
	 * @param errorRate
	 *            the error rate
	 * @return the list
	 */
	public List<CipherSymbol> findSwappableNodes(List<CipherSymbol> ciphertext, List<Letter> letters, double errorRate)
	{
		HashMap<Character, Integer> possibleAbove = new HashMap<Character, Integer>();
		HashMap<Character, Integer> possibleBelow = new HashMap<Character, Integer>();
		for (Letter letter : letters)
		{
			List<Character> multiple = new ArrayList<Character>();
			double freq = 0;
			double errorBound = errorRate;
			for (CipherSymbol symbol : ciphertext)
			{
				if (symbol.getPlaintextValue() == letter.getValue())
				{
					freq++;
					if (!multiple.contains(symbol.getSymbolValue()))
					{
						multiple.add(symbol.getSymbolValue());
					}
				}
			}
			freq = (freq / ciphertext.size()) * 100;

			if (letter.getFrequency()<5)
			{
				errorBound = errorBound/2;
				
				if (letter.getFrequency()<2)
				{
					errorBound = errorBound/2;
				}
			}
			
			if ((freq-errorBound) > letter.getFrequency())
			{
				possibleAbove.put(letter.getValue(), multiple.size());
			} else if ((freq+errorBound) < letter.getFrequency())
			{
				if (multiple.isEmpty())
				{
					possibleBelow.put(letter.getValue(), 0);
				} else
				{
					possibleAbove.put(letter.getValue(), multiple.size());
				}
			}
		}

		char aboveToSwap = getRandomKey(possibleAbove);
		char belowToSwap = getRandomKey(possibleBelow);

		if (aboveToSwap == '0' || belowToSwap == '0')
		{
			return null;
		} else if (possibleBelow.get(belowToSwap) == 0)
		{
			if (possibleAbove.get(aboveToSwap) < 2)
			{
				return null;
			}
			char lowestSym = findLowestFrequency(ciphertext, aboveToSwap);
			ciphertext = setSymbolToNewPlaintext(ciphertext, lowestSym, belowToSwap);
			return ciphertext;
		}

		char bSwap = findRandomFrequency(ciphertext, aboveToSwap);
		char aSwap = findRandomFrequency(ciphertext, belowToSwap);

		ciphertext = setSymbolToNewPlaintext(ciphertext, bSwap, belowToSwap);
		ciphertext = setSymbolToNewPlaintext(ciphertext, aSwap, aboveToSwap);

		return ciphertext;
	}

	/**
	 * Gets a random key from the map.
	 *
	 * @param toSwap the to swap
	 * @return the random key
	 */
	private char getRandomKey(HashMap<Character, Integer> toSwap)
	{
		if (toSwap.size() == 0)
		{
			return '0';
		}
		Random random = new Random();
		List<Character> keys = new ArrayList<Character>(toSwap.keySet());
		char randomKey = keys.get(random.nextInt(keys.size()));
		return randomKey;
	}

	/**
	 * Find a random symbol based on frequency.
	 *
	 * @param ciphertext the ciphertext
	 * @param aboveToSwap the above to swap
	 * @return the char
	 */
	private char findRandomFrequency(List<CipherSymbol> ciphertext, char aboveToSwap)
	{
		List<Character> multiple = new ArrayList<Character>();
		for (CipherSymbol symbol : ciphertext)
		{
			if (symbol.getPlaintextValue() == aboveToSwap)
			{
				if (multiple.isEmpty() || !multiple.contains(symbol.getSymbolValue()))
				{
					multiple.add(symbol.getSymbolValue());
				}
			}
		}

		Random rand = new Random();
		char randomInt = multiple.get(rand.nextInt(multiple.size()));
		return randomInt;
	}

	/**
	 * Find lowest frequency.
	 *
	 * @param ciphertext the ciphertext
	 * @param aboveToSwap the above to swap
	 * @return the char
	 */
	private char findLowestFrequency(List<CipherSymbol> ciphertext, char aboveToSwap)
	{
		char currentSymbol = '0';
		double lowestFrequency = 0;
		for (CipherSymbol symbol : ciphertext)
		{
			if (symbol.getPlaintextValue() == aboveToSwap)
			{
				if (lowestFrequency == 0 || symbol.getFrequency() < lowestFrequency)
				{
					currentSymbol = symbol.getSymbolValue();
				}
			}
		}
		return currentSymbol;
	}

	/**
	 * Sets the same symbols to the new plaintext value.
	 *
	 * @param ciphertext the ciphertext
	 * @param symbol the symbol
	 * @param plaintext the plaintext
	 * @return the list
	 */
	private List<CipherSymbol> setSymbolToNewPlaintext(List<CipherSymbol> ciphertext, char symbol, char plaintext)
	{
		for (CipherSymbol symb : ciphertext)
		{
			if (symb.getSymbolValue() == symbol)
			{
				symb.setPlaintextValue(plaintext);
			}
		}

		return ciphertext;
	}
}
