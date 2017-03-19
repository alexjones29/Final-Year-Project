package main;

import java.util.HashMap;
import java.util.HashSet;
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
	
	public HashMap<Character,Character> findSwappableNodes(List<CipherSymbol> ciphertext, List<Letter> letters, double errorRate)
	{
		HashMap<Character, Character> toSwap = new HashMap<>();
		char aboveToSwap = '0';
		double amountAbove = 0;
		char belowToSwap = '0';
		double amountBelow = 0;
		for (Letter letter : letters)
		{
			double freq = 0;
			for (CipherSymbol symbol : ciphertext)
			{
				if (symbol.getPlaintextValue()==letter.getValue())
				{
					freq += symbol.getFrequency();
				}
				
			}
			
			if ((freq-errorRate) > letter.getFrequency() )
			{
				
				if ((freq-errorRate) > amountAbove)
				{
					amountAbove = freq;
					aboveToSwap = letter.getValue();
				}
			}
			else if ((freq+errorRate) < letter.getFrequency() )
			{
				
				if ((freq+errorRate) < amountAbove)
				{
					amountBelow = freq;
					belowToSwap = letter.getValue();
				}
			}
		}
		
		return findNodes(ciphertext, toSwap, aboveToSwap, belowToSwap);
	}

	
	private HashMap<Character,Character> findNodes(List<CipherSymbol> ciphertext, HashMap<Character,Character> toSwap, char aboveToSwap, char belowToSwap)
	{
		if (belowToSwap == '0' && aboveToSwap == '0')
		{
			return toSwap;
		}
		else if (belowToSwap != '0' && aboveToSwap == '0')
		{
			
			for (CipherSymbol symbol : ciphertext)
			{
				
				double closestFrequency = 0;
				
				if (symbol.getPlaintextValue()==belowToSwap)
				{
					toSwap.put(symbol.getSymbolValue(), symbol.getPlaintextValue());
//					if (symbol.getFrequency() )
				}
			}
		}
		else if (belowToSwap == '0' && aboveToSwap != '0')
		{
			for (CipherSymbol symbol : ciphertext)
			{
				
				double closestFrequency = 0;
				
				if (symbol.getPlaintextValue()==aboveToSwap)
				{
					toSwap.put(symbol.getSymbolValue(), symbol.getPlaintextValue());
//					if (symbol.getFrequency() )
				}
			}
		}
		else 
		{
			for (CipherSymbol symbol : ciphertext)
			{
				double closestFrequency = 0;
				
				if (symbol.getPlaintextValue()==belowToSwap)
				{
					toSwap.put(symbol.getSymbolValue(), symbol.getPlaintextValue());
				}
				else if (symbol.getPlaintextValue()==aboveToSwap)
				{
					toSwap.put(symbol.getSymbolValue(), symbol.getPlaintextValue());
//					if (symbol.getFrequency() )
				}
			}
		}
		
		return toSwap;
	}
}
