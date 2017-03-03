/*
 * 
 */
package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import main.CipherSymbol;
import main.CiphertextReader;
import main.CrackerApplication;
import main.DictionaryHandler;
import main.Frequency;
import main.Letter;
import main.FrequencyFileReader;
import main.Trie;

/**
 * The Class CiphertextReaderTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class CrackerApplicationTest
{
	@InjectMocks
	private CrackerApplication app;
	@Mock
	private CiphertextReader cipherReader;
	@Mock
	private Frequency frequency;
	@Mock
	private DictionaryHandler diction;
	@Mock 
	private FrequencyFileReader letterReader;
	private File testFile;
	private List<CipherSymbol> cipherText;
	private List<Letter> letters;
	//unintialised as in the class as can't call the private initialise object methods
	private Trie trie;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup()
	{
		testFile = new File("resources/encryptedpassage.txt");
		cipherText = new ArrayList<CipherSymbol>();
//		trie = new Trie(false);
		CipherSymbol symbol1 = new CipherSymbol('w');
		CipherSymbol symbol2 = new CipherSymbol('q');
		CipherSymbol symbol3 = new CipherSymbol('%');
		cipherText.add(symbol1);
		cipherText.add(symbol2);
		cipherText.add(symbol3);
	}
	
	private void givenMethodCallsAreMocked()
	{
		when(cipherReader.readInCipherText(testFile)).thenReturn(cipherText);
		when(diction.readInDictionary(trie)).thenReturn(trie);
		List<Letter> lttrs = new ArrayList<Letter>();
		Letter l = new Letter('a', 2.2);
		lttrs.add(l);
		when(letterReader.readInLetterFile()).thenReturn(lttrs);
	}

	/**
	 * When read in cipher text is called.
	 *
	 * @param testFile the test file
	 */
	private void whenReadInCipherTextIsCalled(File testFile)
	{
		app.readInCiphertextAndDictionary(testFile);
	}
	
	/**
	 * When read in letters and frequencies is called.
	 */
	private void whenReadInLettersAndFrequenciesIsCalled()
	{
		letters = letterReader.readInLetterFile();
	}
	
	/**
	 * Then the cipher text is read in.
	 */
	private void thenTheCipherTextIsReadIn()
	{
		verify(cipherReader, times(1)).readInCipherText(testFile);
	}
	
	/**
	 * Then dictionary is read in to A trie.
	 */
	private void thenDictionaryIsReadInToATrie()
	{
		verify(diction, times(1)).readInDictionary(trie);
	}
	
	/**
	 * Then the list of letters is returned.
	 */
	private void thenTheListOfLettersIsReturned()
	{
		assertTrue(letters.size()==1);
		assertNotNull(letters.get(0));
	}

	/**
	 * Test cipherReader and dictionaryHandler are called correctly.
	 */
	@Test
	public void testReadCipherTextAndDictionaryIsCalled()
	{
		givenMethodCallsAreMocked();
		whenReadInCipherTextIsCalled(testFile);
		thenTheCipherTextIsReadIn();
		thenDictionaryIsReadInToATrie();
	}
	
	/**
	 * Test letters are returned from call.
	 */
	@Test
	public void testLettersAreReturnedFromCall()
	{
		givenMethodCallsAreMocked();
		whenReadInLettersAndFrequenciesIsCalled();
		thenTheListOfLettersIsReturned();
	}
	
	

}
