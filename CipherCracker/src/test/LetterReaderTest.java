package test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;

import main.Letter;
import main.LetterReader;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterReaderTest.
 */
public class LetterReaderTest
{
	
	List<Letter> letters = new ArrayList<Letter>();
	LetterReader reader = new LetterReader();
	
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
