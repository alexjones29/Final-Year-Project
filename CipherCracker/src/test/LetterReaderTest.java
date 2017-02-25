package test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;

import main.Letter;
import main.LetterReader;

public class LetterReaderTest
{
	
	List<Letter> letters = new ArrayList<Letter>();
	LetterReader reader = new LetterReader();
	
	private void whenReadInLetterFileIsCalled()
	{
		letters = reader.readInLetterFile(); 
	}
	
	private void thenTheLettersAndTheirFrequenciesAreReadIn()
	{
		assertTrue(letters.size()==26);
		assertTrue(letters.get(10).getValue()==('k'));
		assertTrue(letters.get(10).getFrequency()==0.81);
	}

	@Test
	public void testLetterFileIsReadInCorrectly()
	{
		whenReadInLetterFileIsCalled();
		thenTheLettersAndTheirFrequenciesAreReadIn();
	}

}
