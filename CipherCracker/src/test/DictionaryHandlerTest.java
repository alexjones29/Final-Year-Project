package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;

import org.junit.Test;

import main.DictionaryHandler;

/**
 * The Class DictionaryHandlerTest.
 */
public class DictionaryHandlerTest
{

	private DictionaryHandler dictionary = new DictionaryHandler();
	private File testFile;
	private HashSet<String> dictionaryWords = new HashSet<String>();
	
	/**
	 * Given dictionary file.
	 */
	public void givenDictionaryFile()
	{
		testFile = new File("resources/dictionary.txt");
	}
	
	/**
	 * Then dictionary is read in.
	 */
	public void thenDictionaryIsReadIn()
	{
		dictionaryWords = dictionary.readInDictionary(dictionaryWords);
		assertTrue(dictionaryWords.size()>0);
	}
	
	/**
	 * Test dictionary file reader.
	 */
	@Test
	public void testDictionaryFileReader()
	{
		givenDictionaryFile();
		thenDictionaryIsReadIn();
	}

}
