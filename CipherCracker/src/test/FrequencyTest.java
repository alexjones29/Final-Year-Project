package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Test;

import main.CipherSymbol;
import main.Frequency;

/**
 * The Class FrequencyTest.
 */
public class FrequencyTest
{
	
	List<CipherSymbol> ciphertext = new ArrayList<CipherSymbol>();
	CipherSymbol symbol1;
	CipherSymbol symbol2;
	CipherSymbol symbol3;
	CipherSymbol symbol4;
	CipherSymbol symbol5;
	CipherSymbol symbol6;
	CipherSymbol symbol7;
	CipherSymbol symbol8;
	double cipherTextSize = 0;

	/**
	 * Given there are cipher symbols.
	 */
	private void givenThereAreCipherSymbols()
	{
		symbol1 = new CipherSymbol('a');
		symbol2 = new CipherSymbol('a');
		symbol3 = new CipherSymbol('k');
		symbol4 = new CipherSymbol('#');
		symbol5 = new CipherSymbol('v');
		symbol6 = new CipherSymbol('g');
		symbol7 = new CipherSymbol('z');
		symbol8 = new CipherSymbol('#');
		
		symbol1.setPlaintextValue('e');
		symbol2.setPlaintextValue('f');
		symbol3.setPlaintextValue('e');
		symbol4.setPlaintextValue('t');
		symbol5.setPlaintextValue('y');
		symbol6.setPlaintextValue('t');
		symbol7.setPlaintextValue('i');
		symbol8.setPlaintextValue('e');
		
		ciphertext.add(symbol1);
		ciphertext.add(symbol2);
		ciphertext.add(symbol3);
		ciphertext.add(symbol4);
		ciphertext.add(symbol5);
		ciphertext.add(symbol6);
		ciphertext.add(symbol7);
		ciphertext.add(symbol8);
		cipherTextSize = ciphertext.size();
	}
	
	/**
	 * Given symbol count.
	 */
	private void givenSymbolCount()
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
	}
	
	/**
	 * Given plaintext count.
	 */
	private void givenPlaintextCount()
	{
		Map<Character, Integer> occurences = new HashMap<Character, Integer>();
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
			}
		}
	}

	/**
	 * When calculate symbol frequency is called with A list of symbols.
	 */
	private void whenCalculateSymbolFrequencyIsCalledWithAListOfSymbols()
	{
		Frequency frequency = new Frequency();
		frequency.calculateSymbolFrequency(ciphertext);
	}
	
	/**
	 * When calculate plaintext frequency is called.
	 */
	private void whenCalculatePlaintextFrequencyIsCalled()
	{
		Frequency frequency = new Frequency();
		frequency.calculatePlaintextFrequency(ciphertext);
	}

	/**
	 * Then the frequencies are set.
	 */
	private void thenTheFrequenciesAreSet()
	{
		assertEquals((2*100/cipherTextSize),symbol1.getFrequency(), 0);
		assertEquals((2*100/cipherTextSize),symbol2.getFrequency(), 0);
		assertEquals((1*100/cipherTextSize),symbol3.getFrequency(), 0);
		assertEquals((2*100/cipherTextSize),symbol4.getFrequency(), 0);
		assertEquals((1*100/cipherTextSize),symbol5.getFrequency(), 0);
		assertEquals((1*100/cipherTextSize),symbol6.getFrequency(), 0);
		assertEquals((1*100/cipherTextSize),symbol7.getFrequency(), 0);
		assertEquals((2*100/cipherTextSize),symbol8.getFrequency(), 0);
	}
	
	/**
	 * Then plaintext frequencies are set.
	 */
	private void thenPlaintextFrequenciesAreSet()
	{
		assertEquals((3*100)/cipherTextSize ,symbol1.getPlaintextFrequency(),0);
		assertEquals((1*100)/cipherTextSize ,symbol2.getPlaintextFrequency(),0);
		assertEquals((3*100)/cipherTextSize ,symbol3.getPlaintextFrequency(),0);
		assertEquals((2*100)/cipherTextSize ,symbol4.getPlaintextFrequency(),0);
		assertEquals((1*100)/cipherTextSize ,symbol5.getPlaintextFrequency(),0);
		assertEquals((2*100)/cipherTextSize ,symbol6.getPlaintextFrequency(),0);
		assertEquals((1*100)/cipherTextSize ,symbol7.getPlaintextFrequency(),0);
		assertEquals((3*100)/cipherTextSize ,symbol8.getPlaintextFrequency(),0);
	}

	/**
	 * Test frequencies are calculated and assigned to the objects correctly.
	 */
	@Test
	public void testSymbolFrequenciesAreCalculatedAndSetCorrectly()
	{
		givenThereAreCipherSymbols();
		givenSymbolCount();
		whenCalculateSymbolFrequencyIsCalledWithAListOfSymbols();
		thenTheFrequenciesAreSet();

	}
	
	/**
	 * Test plaintext frequencies are calculated and set correctly.
	 */
	@Test
	public void testPlaintextFrequenciesAreCalculatedAndSetCorrectly()
	{
		givenThereAreCipherSymbols();
		givenPlaintextCount();
		whenCalculatePlaintextFrequencyIsCalled();
		thenPlaintextFrequenciesAreSet();
	}
}
