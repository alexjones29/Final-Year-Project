package main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InitialKey
{
	
	public InitialKey()
	{
		
	}
	
	/**
	 * Read in letter file containing letter value and frequency as a percentage,
	 * creates the object and adds it to the list to be returned.
	 *
	 * @return the list of letter objects
	 */
	public List<CipherSymbol> readInFixedLetterFile(List<CipherSymbol> ciphertext)
	{
		HashMap<String, String> fixedLetters = new HashMap<String, String> ();
		try
		{
			FileInputStream fstream = new FileInputStream("resources/fixedLetters.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				String[] tokens = strLine.split(" : ");
				String symb = tokens[0];
				String plain = tokens[1];
				fixedLetters.put(symb, plain);
			}
			in.close();
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
		
		for (CipherSymbol cipher : ciphertext)
		{
			for (Map.Entry<String, String> entry : fixedLetters.entrySet())
			{
				String key = entry.getKey();
				if (key.charAt(0) == (cipher.getSymbolValue()))
				{
					cipher.setPlaintextValue(entry.getValue().charAt(0));
					cipher.setFixed(true);
				}
			}
		}
		
		return ciphertext;

	}
	
	/**
	 * Creates the initial key using roulette selection.
	 *
	 * @param symbols the symbols
	 * @param letters the letters
	 * @return the list
	 */
	public List<CipherSymbol> createInitialKey(List<CipherSymbol> symbols, List<Letter> letters)
	{
		double[] weight = new double[26];
		
		for (int i=0;i<letters.size();i++)
		{
			double temp = letters.get(i).getFrequency();
			weight[i]=temp;
		}
		
		for (CipherSymbol symbol : symbols)
		{
			if (symbol.getPlaintextValue()==0 && !symbol.isFixed())
			{
				int location = rouletteSelect(weight);
				Letter letter = letters.get(location);
				for (CipherSymbol sym : symbols)
				{
					if (sym.getSymbolValue()==symbol.getSymbolValue())
					{
						sym.setPlaintextValue(letter.getValue());
					}
				}
				symbol.setPlaintextValue(letter.getValue());
			}
		}
		return symbols;
		
	}
	
	/**
	 * Roulette wheel selection.
	 * Calculates the total weight and locates the random value based on the weight.
	 *
	 * @param weight the weight
	 * @return the int
	 */
	public int rouletteSelect(double[] weight) {
		double weight_sum = 0;
		for(int i=0; i<weight.length; i++) {
			weight_sum += weight[i];
		}
		double value = randUniformPositive() * weight_sum;	
		for(int i=0; i<weight.length; i++) {		
			value -= weight[i];		
			if(value <= 0) return i;
		}
		return weight.length - 1;
	}

	/**
	 * Returns a uniformly distributed double value between 0.0 and 1.0Rand uniform positive.
	 *
	 * @return the double
	 */
	private double randUniformPositive() {
		return new Random().nextDouble();
	}

}
