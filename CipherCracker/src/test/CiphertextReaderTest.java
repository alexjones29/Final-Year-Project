package test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import main.CipherSymbol;
import main.CiphertextReader;
import main.CrackerApplication;
import main.Frequency;

@RunWith(MockitoJUnitRunner.class)
public class CiphertextReaderTest
{
	@InjectMocks
	private CrackerApplication app;
	@Mock
	private CiphertextReader cipherReader;
	@Mock
	private Frequency frequency;
	private File testFile;
	private List<CipherSymbol> cipherText;
	
	private void givenThereIsAFile()
	{
		testFile = new File("resources/340cipherascii.txt");
		cipherText = new ArrayList<CipherSymbol>();
		CipherSymbol symbol1 = new CipherSymbol('w');
		CipherSymbol symbol2 = new CipherSymbol('q');
		CipherSymbol symbol3 = new CipherSymbol('%');
		cipherText.add(symbol1);
		cipherText.add(symbol2);
		cipherText.add(symbol3);
		when(cipherReader.readInCipherText(testFile)).thenReturn(cipherText);
	}

	private void whenReadInCipherTextIsCalled(File testFile)
	{
		app.readInCiphertext(testFile);
	}
	
	private void thenTheCipherTextIsReadIn()
	{
		verify(cipherReader, times(1)).readInCipherText(testFile);
	}

	@Test
	public void testCipherReaderIsCalled()
	{
		givenThereIsAFile();
		whenReadInCipherTextIsCalled(testFile);
		thenTheCipherTextIsReadIn();
	}
	
	

}
