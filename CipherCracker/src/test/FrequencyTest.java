package test;

import java.util.ArrayList;
import java.util.List;
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
	double total;
	
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

		ciphertext.add(symbol1);
		ciphertext.add(symbol2);
		ciphertext.add(symbol3);
		ciphertext.add(symbol4);
		ciphertext.add(symbol5);
		ciphertext.add(symbol6);
		ciphertext.add(symbol7);
		ciphertext.add(symbol8);
		total = 6;
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
	 * Then the frequencies are set.
	 */
	private void thenTheFrequenciesAreSet()
	{
		assertEquals((2*100/total),symbol1.getFrequency(), 0);
		assertEquals((2*100/total),symbol2.getFrequency(), 0);
		assertEquals((1*100/total),symbol3.getFrequency(), 0);
		assertEquals((2*100/total),symbol4.getFrequency(), 0);
		assertEquals((1*100/total),symbol5.getFrequency(), 0);
		assertEquals((1*100/total),symbol6.getFrequency(), 0);
		assertEquals((1*100/total),symbol7.getFrequency(), 0);
		assertEquals((2*100/total),symbol8.getFrequency(), 0);
	}

	/**
	 * Test frequencies are calculated and assigned to the objects correctly.
	 */
	@Test
	public void testFrequenciesAreCalculatedAndSetCorrectly()
	{
		givenThereAreCipherSymbols();
		whenCalculateSymbolFrequencyIsCalledWithAListOfSymbols();
		thenTheFrequenciesAreSet();

	}

}
