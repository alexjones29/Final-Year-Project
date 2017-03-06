package test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;

import main.Letter;
import main.FrequencyFileReader;

/**
 * The Class LetterReaderTest.
 */
public class FrequencyFileReaderTest
{
	
	List<Letter> letters = new ArrayList<Letter>();
	FrequencyFileReader reader = new FrequencyFileReader();
	
	/**
	 * When the readInLetterFile method is called.
	 */
	private void whenReadInLetterFileIsCalled()
	{
		letters = reader.readInLetterFile(); 
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
	 * Test letter file is read in correctly.
	 */
	@Test
	public void testLetterFileIsReadInCorrectly()
	{
		whenReadInLetterFileIsCalled();
		thenTheLettersAndTheirFrequenciesAreReadIn();
	}

}
