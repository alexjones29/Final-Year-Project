package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	
	public LinkedHashMap<Character,Character> findSwappableNodes(List<CipherSymbol> ciphertext, List<Letter> letters, double errorRate)
	{
		LinkedHashMap<Character, Character> toSwap = new LinkedHashMap<>();
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
					freq ++;
				}
			}
			if (freq > 0)
			{
				freq = freq/ciphertext.size();
			}
			
			if (freq > letter.getFrequency() )
			{
				if (freq > amountAbove)
				{
					amountAbove = freq;
					aboveToSwap = letter.getValue();
				}
			}
			else if (freq < letter.getFrequency())
			{
				
				if (freq < amountAbove)
				{
					amountBelow = freq;
					belowToSwap = letter.getValue();
				}
			}
		}
		
		if (aboveToSwap == '0' && belowToSwap == '0')
		{
			return null;
		}
		else if (amountBelow == 0)
		{
			char lowestSym = findLowestFrequency(ciphertext, aboveToSwap);
//			return setSymbolToNewPlaintext(ciphertext, lowestSym, belowToSwap);
		}
		
		return findNodes(ciphertext, toSwap, aboveToSwap, belowToSwap);
	}

	
	private char findLowestFrequency(List<CipherSymbol> ciphertext, char aboveToSwap)
	{
		char currentSymbol = '0';
		double lowestFrequency = 0;
		for (CipherSymbol symbol : ciphertext)
		{
			if (symbol.getPlaintextValue()==aboveToSwap)
			{
				if (lowestFrequency == 0 || symbol.getFrequency() < lowestFrequency)
				{
					currentSymbol = symbol.getSymbolValue();
				}
			}
		}
		return currentSymbol;
	}
	
	private char findClosestFrequency(List<CipherSymbol> ciphertext, char symbol, double frequency, boolean positive)
	{
		
		
//		 int closest = values[0];
//		    int distance = Math.abs(closest - find);
//		    for(int i: values) {
//		       int distanceI = Math.abs(i - find);
//		       if(distance > distanceI) {
//		           closest = i;
//		           distance = distanceI;
//		       }
//		    }
//		    return closest;
		return symbol;
		
	}
	
	private List<CipherSymbol> setSymbolToNewPlaintext(List<CipherSymbol> ciphertext, char symbol, char plaintext)
	{
		for (CipherSymbol symb : ciphertext)
		{
			if (symb.getSymbolValue()==symbol)
			{
				symb.setPlaintextValue(plaintext);
			}
		}
		
		return ciphertext;
	}
	
	private LinkedHashMap<Character,Character> findNodes(List<CipherSymbol> ciphertext, LinkedHashMap<Character,Character> toSwap, char aboveToSwap, char belowToSwap)
	{
		
			for (CipherSymbol symbol : ciphertext)
			{
				double closestFrequency = 0;
				
				if (symbol.getPlaintextValue()==belowToSwap && !toSwap.containsValue(symbol.getSymbolValue()))
				{
					toSwap.put(symbol.getSymbolValue(), symbol.getPlaintextValue());
				}
				else if (symbol.getPlaintextValue()==aboveToSwap && !toSwap.containsValue(symbol.getSymbolValue()))
				{
					toSwap.put(symbol.getSymbolValue(), symbol.getPlaintextValue());
//					if (symbol.getFrequency() )
				}
				
			}
		
		return toSwap;
	}
}
