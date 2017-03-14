package main;

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
	
	public char[] findSwappableNodes(List<CipherSymbol> ciphertext, List<Letter> letters, double errorRate)
	{
		char belowToSwap = '0';
		char aboveToSwap = '0';
	
		for (CipherSymbol symbol : ciphertext)
		{
			
			
			
		}
		
		return null;
		
	}
}
