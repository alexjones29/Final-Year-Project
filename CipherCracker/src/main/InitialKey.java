package main;

import java.util.List;
import java.util.Random;

public class InitialKey
{
	
	public InitialKey()
	{
		
	}
	
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
	
	private int rouletteSelect(double[] weight) {
		// calculate the total weight
		double weight_sum = 0;
		for(int i=0; i<weight.length; i++) {
			weight_sum += weight[i];
		}
		// get a random value
		double value = randUniformPositive() * weight_sum;	
		// locate the random value based on the weights
		for(int i=0; i<weight.length; i++) {		
			value -= weight[i];		
			if(value <= 0) return i;
		}
		// when rounding errors occur, we return the last item's index 
		return weight.length - 1;
	}

	// Returns a uniformly distributed double value between 0.0 and 1.0
	private double randUniformPositive() {
		// easiest implementation
		return new Random().nextDouble();
	}

}
