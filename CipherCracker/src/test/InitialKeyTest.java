package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.CipherSymbol;
import main.InitialKey;
import main.Letter;

/**
 * The Class InitialKeyTest.
 */
public class InitialKeyTest
{
	

	List<CipherSymbol> ciphertext = new ArrayList<CipherSymbol>();
	List<Letter> letters = new ArrayList<Letter>();
	
	CipherSymbol symbol1;
	CipherSymbol symbol2;
	CipherSymbol symbol3;
	CipherSymbol symbol4;
	CipherSymbol symbol5;
	CipherSymbol symbol6;
	CipherSymbol symbol7;
	CipherSymbol symbol8;
	
	
	/**
	 * Given list of symbols and letters.
	 */
	private void givenListOfSymbolsAndLetters()
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
		
		letters = readInLetterFile();
	}
	

	/**
	 * Read in letter file.
	 *
	 * @return the list
	 */
	private List<Letter> readInLetterFile()
	{
		List<Letter> letters = new ArrayList<Letter>();
		try
		{
			FileInputStream fstream = new FileInputStream("resources/letters.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)
			{
				String[] tokens = strLine.split(" : ");
				char value = tokens[0].charAt(0);
				double freq = Double.parseDouble(tokens[1]);
				Letter letter = new Letter(value, freq);
				letters.add(letter);
			}
			in.close();
		} catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
		return letters;
	}


	
	/**
	 * When create initial key is called.
	 */
	private void whenCreateInitialKeyIsCalled()
	{
		InitialKey key = new InitialKey();
		
		ciphertext = key.createInitialKey(ciphertext, letters,0);
		
	}
	
	/**
	 * Then symbols are assigned letters.
	 */
	private void thenSymbolsAreAssignedLetters()
	{
		
		for (CipherSymbol cipher : ciphertext)
		{
			assertNotNull(cipher.getPlaintextValue());
		}
		
		assertEquals(ciphertext.get(0).getPlaintextValue(),ciphertext.get(1).getPlaintextValue());
		assertEquals(ciphertext.get(3).getPlaintextValue(),ciphertext.get(7).getPlaintextValue());
	}
	
	/**
	 * Test initial key assigns letters to symbols correctly.
	 */
	@Test
	public void testInitialKeyAssignsLettersToSymbolsCorrectly()
	{
		givenListOfSymbolsAndLetters();
		whenCreateInitialKeyIsCalled();
		thenSymbolsAreAssignedLetters();
	}
}
