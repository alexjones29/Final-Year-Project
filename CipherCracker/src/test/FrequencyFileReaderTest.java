package test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import main.FrequencyFileReader;
import main.Letter;

/**
 * The Class LetterReaderTest.
 */
public class FrequencyFileReaderTest
{
	
	private List<Letter> letters = new ArrayList<Letter>();
	private FrequencyFileReader reader = new FrequencyFileReader();
	private HashMap<String, Double> ngram = new HashMap<String, Double>();
	private File testFile;
	
	/**
	 * Given bigram file.
	 */
	public void givenBigramFile()
	{
		testFile = new File("resources/bigramfrequencies.txt");
	}
	
	/**
	 * Given trigram file.
	 */
	public void givenTrigramFile()
	{
		testFile = new File("resources/trigramfrequencies.txt");
	}
	
	/**
	 * When the readInLetterFile method is called.
	 */
	private void whenReadInLetterFileIsCalled()
	{
		letters = reader.readInLetterFile(); 
	}
	
	/**
	 * When N gram file reader is called.
	 */
	private void whenNGramFileReaderIsCalled()
	{
		ngram = reader.readInNGramFiles(testFile);
	}
	
	/**
	 * Then the letters and their frequencies are read in.
	 */
	private void thenTheLettersAndTheirFrequenciesAreReadIn()
	{
		assertTrue(letters.size()==26);
		assertTrue(letters.get(10).getValue()==('k'));
		assertTrue(letters.get(10).getFrequency()==0.81);
	}
	
	/**
	 * Then N gram has been read in.
	 */
	private void thenNGramHasBeenReadIn()
	{
		assertTrue(ngram.size()>0);
	}

	/**
	 * Test letter file is read in correctly.
	 */
	@Test
	public void testLetterFileIsReadInCorrectly()
	{
		whenReadInLetterFileIsCalled();
		thenTheLettersAndTheirFrequenciesAreReadIn();
	}
	
	/**
	 * Test bigram file is read in correctly.
	 */
	@Test
	public void testBigramFileIsReadInCorrectly()
	{
		givenBigramFile();
		whenNGramFileReaderIsCalled();
		thenNGramHasBeenReadIn();
	}

	/**
	 * Test trigram file is read in correctly.
	 */
	@Test
	public void testTrigramFileIsReadInCorrectly()
	{
		givenTrigramFile();
		whenNGramFileReaderIsCalled();
		thenNGramHasBeenReadIn();
	}
}
