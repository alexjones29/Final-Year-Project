package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class CiphertextReader.
 */
public class CiphertextReader
{
                                       
	/**
	 * Read in cipher text and store in an arraylist to return to the main
	 * application.
	 * 
	 * @param file 
	 * @return the list
	 */
	public List<CipherSymbol> readInCipherText(File file)
	{
		ArrayList<CipherSymbol> cipherText = new ArrayList<CipherSymbol>();

		try (InputStream in = new FileInputStream(file);
				Reader reader = new InputStreamReader(in, "ASCII");
				Reader buffer = new BufferedReader(reader))
		{
			int r;
			while ((r = reader.read()) != -1)
			{
				char ch = (char) r;
			    CipherSymbol symbol = new CipherSymbol(ch);
			    cipherText.add(symbol);
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return cipherText;

	}
}
