package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.CipherSymbol;
import main.Frequency;

/**
 * The Class FrequencyTest.
 */
public class FrequencyTest
{
	
	private Frequency frequency = new Frequency();
	private List<CipherSymbol> ciphertext = new ArrayList<CipherSymbol>();
	private List<CipherSymbol> alteredCiphertext = new ArrayList<CipherSymbol>();
	private CipherSymbol symbol1;
	private CipherSymbol symbol2;
	private CipherSymbol symbol3;
	private CipherSymbol symbol4;
	private CipherSymbol symbol5;
	private CipherSymbol symbol6;
	private CipherSymbol symbol7;
	private CipherSymbol symbol8;
	private double cipherTextSize = 0;
	private char lowest = '0';

	/**
	 * Setup.
	 */
	@Before
	public void setup()
	{
		lowest = '0';
		
		symbol1 = new CipherSymbol('a');
		symbol2 = new CipherSymbol('c');
		symbol3 = new CipherSymbol('k');
		symbol4 = new CipherSymbol('#');
		symbol5 = new CipherSymbol('a');
		symbol6 = new CipherSymbol('g');
		symbol7 = new CipherSymbol('z');
		symbol8 = new CipherSymbol('#');
		
		symbol1.setPlaintextValue('e');
		symbol2.setPlaintextValue('f');
		symbol3.setPlaintextValue('e');
		symbol4.setPlaintextValue('t');
		symbol5.setPlaintextValue('e');
		symbol6.setPlaintextValue('t');
		symbol7.setPlaintextValue('i');
		symbol8.setPlaintextValue('t');
		
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
	 * Given frequencies are calculated and set.
	 */
	private void givenFrequenciesAreCalculatedAndSet()
	{
		frequency.calculatePlaintextFrequency(ciphertext);
		frequency.calculateSymbolFrequency(ciphertext);
	}

	/**
	 * When calculate symbol frequency is called with A list of symbols.
	 */
	private void whenCalculateSymbolFrequencyIsCalledWithAListOfSymbols()
	{
		frequency.calculateSymbolFrequency(ciphertext);
	}
	
	/**
	 * When set symbol to new plaintext is called.
	 *
	 * @param symbol the symbol
	 * @param plaintext the plaintext
	 */
	private void whenSetSymbolToNewPlaintextIsCalled(char symbol, char plaintext)
	{
		alteredCiphertext = frequency.setSymbolToNewPlaintext(ciphertext, symbol, plaintext);
	}
	/**
	 * When lowest frequency is called.
	 *
	 * @param toSwap the to swap
	 */
	private void whenLowestFrequencyIsCalled(char toSwap)
	{
		lowest = frequency.findLowestFrequency(ciphertext, toSwap);
	}
	
	/**
	 * When calculate plaintext frequency is called.
	 */
	private void whenCalculatePlaintextFrequencyIsCalled()
	{
		frequency.calculatePlaintextFrequency(ciphertext);
	}
	
	/**
	 * Then lowest is returned.
	 *
	 * @param expected the expected
	 */
	private void thenLowestIsReturned(char expected)
	{
		assertEquals(String.valueOf(expected), String.valueOf(lowest));
	}
	
	/**
	 * Then symbols are set correctly.
	 *
	 * @param symbol the symbol
	 * @param plaintext the plaintext
	 */
	private void thenSymbolsAreSetCorrectly(char symbol, char plaintext)
	{
		for (CipherSymbol sym : alteredCiphertext)
		{
			if (sym.getSymbolValue() == symbol)
			{
				assertEquals(String.valueOf(plaintext), String.valueOf(sym.getPlaintextValue()));
			}
		}
	}

	/**
	 * Then the frequencies are set.
	 */
	private void thenTheFrequenciesAreSet()
	{
		assertEquals((2*100/cipherTextSize),symbol1.getFrequency(), 0);
		assertEquals((1*100/cipherTextSize),symbol2.getFrequency(), 0);
		assertEquals((1*100/cipherTextSize),symbol3.getFrequency(), 0);
		assertEquals((2*100/cipherTextSize),symbol4.getFrequency(), 0);
		assertEquals((2*100/cipherTextSize),symbol5.getFrequency(), 0);
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
		assertEquals((3*100)/cipherTextSize ,symbol4.getPlaintextFrequency(),0);
		assertEquals((3*100)/cipherTextSize ,symbol5.getPlaintextFrequency(),0);
		assertEquals((3*100)/cipherTextSize ,symbol6.getPlaintextFrequency(),0);
		assertEquals((1*100)/cipherTextSize ,symbol7.getPlaintextFrequency(),0);
		assertEquals((3*100)/cipherTextSize ,symbol8.getPlaintextFrequency(),0);
	}

	/**
	 * Test frequencies are calculated and assigned to the objects correctly.
	 */
	@Test
	public void testSymbolFrequenciesAreCalculatedAndSetCorrectly()
	{
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
		givenPlaintextCount();
		whenCalculatePlaintextFrequencyIsCalled();
		thenPlaintextFrequenciesAreSet();
	}
	
	/**
	 * Test find lowest frequency finds lowest.
	 */
	@Test
	public void testFindLowestFrequencyFindsLowest()
	{
		givenFrequenciesAreCalculatedAndSet();
		whenLowestFrequencyIsCalled('e');
		thenLowestIsReturned('k');
	}
	
	/**
	 * Symbols are set to new plaintext.
	 */
	@Test
	public void symbolsAreSetToNewPlaintext()
	{
		whenSetSymbolToNewPlaintextIsCalled('k','i');
		thenSymbolsAreSetCorrectly('k', 'i');
	}

		
}
