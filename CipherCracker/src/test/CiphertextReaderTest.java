package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import main.CipherSymbol;
import main.CiphertextReader;


/**
 * The Class CiphertextReaderTest.
 */
public class CiphertextReaderTest
{
	
	private CiphertextReader reader = new CiphertextReader();
	private File testFile;
	private List<CipherSymbol> text = new ArrayList<CipherSymbol>();
	
	/**
	 * Given file.
	 */
	public void givenFile()
	{
		testFile = new File("resources/340.txt");
	}
	
	/**
	 * Then the file is read into the list correctly.
	 */
	public void thenTheFileIsReadIntoTheListCorrectly()
	{
		text = reader.readInCipherText(testFile);
		assertTrue(text.size()>0);
		
		for (CipherSymbol symbol : text)
		{
			assertNotNull(symbol.getSymbolValue());
		}
	}

	/**
	 * Test that the file is read in correctly with assigned symbol values.
	 */
	@Test
	public void testThatTheFileIsReadInCorrectlyWithAssignedSymbolValues()
	{
		givenFile();
		thenTheFileIsReadIntoTheListCorrectly();
	}

}
