package main;

import java.util.List;
import java.util.Random;

/**
 * The Class InitialKey.
 */
public class InitialKey
{
	
	private Random rand;
	
	public InitialKey()
	{
		
	}
	
	/**
	 * Creates the initial key using roulette selection.
	 *
	 * @param symbols the symbols
	 * @param letters the letters
	 * @param randomSeed the random seed
	 * @return the list
	 */
	public List<CipherSymbol> createInitialKey(List<CipherSymbol> symbols, List<Letter> letters, long randomSeed)
	{
		double[] weight = new double[26];
		if (randomSeed == 0)
		{
			rand = new Random();
		}
		else 
		{
			rand = new Random(randomSeed);
		}
		for (int i=0;i<letters.size();i++)
		{
			double temp = letters.get(i).getFrequency();
			weight[i]=temp;
		}
		
		for (CipherSymbol symbol : symbols)
		{
			if (symbol.getPlaintextValue()==0)
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
		return rand.nextDouble();
	}

}
