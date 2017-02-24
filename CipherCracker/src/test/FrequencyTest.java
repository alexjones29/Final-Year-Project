package test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;

import main.CipherSymbol;
import main.Frequency;

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

	}

	private void whenCalculateSymbolFrequencyIsCalledWithAListOfSymbols()
	{
		Frequency frequency = new Frequency();
		frequency.calculateSymbolFrequency(ciphertext);
	}

	private void thenTheFrequenciesAreSet()
	{
		assertEquals(2,symbol1.getFrequency());
		assertEquals(2,symbol2.getFrequency());
		assertEquals(1,symbol3.getFrequency());
		assertEquals(2,symbol4.getFrequency());
		assertEquals(1,symbol5.getFrequency());
		assertEquals(1,symbol6.getFrequency());
		assertEquals(1,symbol7.getFrequency());
		assertEquals(2,symbol8.getFrequency());
	}

	@Test
	public void testFrequenciesAreCalculatedAndSetCorrectly()
	{
		givenThereAreCipherSymbols();
		whenCalculateSymbolFrequencyIsCalledWithAListOfSymbols();
		thenTheFrequenciesAreSet();

	}

}