package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frequency
{

	public void calculateSymbolFrequency(List<CipherSymbol> ciphertext)
	{
		Map<Character, Integer> occurences = new HashMap<Character, Integer>();
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
			}
		}

		for (Map.Entry<Character, Integer> entry : occurences.entrySet())
		{
			for (CipherSymbol cipher : ciphertext)
			{
				if (cipher.getSymbolValue()==entry.getKey())
				{
					cipher.setFrequency(entry.getValue());
				}
			}
		}
	}
}