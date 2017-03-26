package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frequency
{
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
		Map<Character, Integer> occurences = new HashMap<Character, Integer>();
		double total = 0;
		for (CipherSymbol cipher : ciphertext)
		{
			if (occurences.containsKey(cipher.getSymbolValue()))
			{
				int temp = occurences.get(cipher.getSymbolValue());
				temp++;
				occurences.put(cipher.getSymbolValue(), temp);
			} else
			{
				occurences.put(cipher.getSymbolValue(), 1);
				total++;
			}
		}

		for (Map.Entry<Character, Integer> entry : occurences.entrySet())
		{
			for (CipherSymbol cipher : ciphertext)
			{
				if (cipher.getSymbolValue() == entry.getKey())
				{
					double value = ((entry.getValue() * 100) / total);
					cipher.setFrequency(value);
				}
			}
		}
	}

	public void calculatePlaintextFrequency(List<CipherSymbol> ciphertext)
	{

		Map<Character, Integer> occurences = new HashMap<Character, Integer>();
		double total = 0;
		for (CipherSymbol cipher : ciphertext)
		{
			if (occurences.containsKey(cipher.getPlaintextValue()))
			{
				int temp = occurences.get(cipher.getPlaintextValue());
				temp++;
				occurences.put(cipher.getPlaintextValue(), temp);
			} else
			{
				occurences.put(cipher.getPlaintextValue(), 1);
				total++;
			}
		}

		for (Map.Entry<Character, Integer> entry : occurences.entrySet())
		{
			for (CipherSymbol cipher : ciphertext)
			{
				if (cipher.getPlaintextValue() == entry.getKey())
				{
					double value = ((entry.getValue() * 100) / total);
					cipher.setPlaintextFrequency(value);
				}
			}
		}
	}

	public List<CipherSymbol> findSwappableNodes(List<CipherSymbol> ciphertext, List<Letter> letters, double errorRate)
	{
		char aboveToSwap = '0';
		boolean multipleAbove = false;
		boolean multipleBelow = false;
		double amountAbove = 0;
		char belowToSwap = '0';
		double amountBelow = 0;

		while (amountAbove == 0 && amountBelow == 0)
		{
			for (Letter letter : letters)
			{
				List<Character> multiple = new ArrayList<Character>();
				double freq = 0;
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
				
				freq = (freq/ciphertext.size()) * 100;

 				if ((freq)> letter.getFrequency())
				{
					if (freq > amountAbove)
					{
						amountAbove = freq;
						aboveToSwap = letter.getValue();
						multipleAbove = false;
						if (multiple.size() > 1)
						{
							multipleAbove = true;
						}

					}
				} else if (freq < letter.getFrequency())
				{
					if (freq < amountAbove)
					{
						multipleBelow = false;
						amountBelow = freq;
						belowToSwap = letter.getValue();
						if (multiple.size() > 1)
						{
							multipleBelow = true;
						}
					}
				}
			}
		}

		if (aboveToSwap == '0' && belowToSwap == '0')
		{
			return null;
		} else if (amountBelow == 0)
		{
			if (!multipleAbove)
			{
				return null;
			}
			char lowestSym = findLowestFrequency(ciphertext, aboveToSwap);
			ciphertext = setSymbolToNewPlaintext(ciphertext, lowestSym, belowToSwap);
			return ciphertext;
		}
		
		char lowestBelow = findLowestFrequency(ciphertext, aboveToSwap);
		char lowestAbove = findLowestFrequency(ciphertext, belowToSwap);

		ciphertext = setSymbolToNewPlaintext(ciphertext, lowestBelow, belowToSwap);
		ciphertext = setSymbolToNewPlaintext(ciphertext, lowestAbove, aboveToSwap);

		return ciphertext;
	}

	private boolean containsCharacter(char[] unique, char toFind)
	{
		for (char c : unique)
		{
			if (c == toFind)
			{
				return true;
			}
		}
		return false;
	}

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
