package main;

/**
 * The cipherSymbol object.
 */
public class CipherSymbol
{
	private char symbolvalue;
	private char plaintextvalue = 0;
	private double plaintextFrequency = 0;
	private double percentagefrequency;
	private double bestScore = 0;
	private boolean inWord = false;
	
	/**
	 * Gets the best score.
	 *
	 * @return the best score
	 */
	public double getBestScore()
	{
		return bestScore;
	}

	/**
	 * Sets the best score.
	 *
	 * @param bestScore the new best score
	 */
	public void setBestScore(double bestScore)
	{
		this.bestScore = bestScore;
	}

	/**
	 * Instantiates a new cipher symbol with the ascii value.
	 *
	 * @param symbolValue
	 *            the symbol value
	 */
	public CipherSymbol(char symbolValue) {
		this.symbolvalue = symbolValue;
	}

	/**
	 * Instantiates a new cipher symbol with a plaintextValue if it is known or
	 * suspected.
	 *
	 * @param symbolValue
	 *            the symbol value
	 * @param plaintextValue
	 *            the plaintext value
	 */
	public CipherSymbol(String symbolValue, char plaintextValue) {
		this.plaintextvalue = plaintextValue;
	}

	/**
	 * Gets the symbol value.
	 *
	 * @return the symbol value
	 */
	public char getSymbolValue()
	{
		return symbolvalue;
	}

	/**
	 * Sets the symbol value.
	 *
	 * @param symbolValue
	 *            the new symbol value
	 */
	public void setSymbolValue(char symbolValue)
	{
		this.symbolvalue = symbolValue;
	}

	/**
	 * Gets the plaintext value of the symbol.
	 *
	 * @return the plaintext value
	 */
	public char getPlaintextValue()
	{
		return plaintextvalue;
	}

	/**
	 * Sets the plaintext value.
	 *
	 * @param plaintextValue
	 *            the new plaintext value
	 */
	public void setPlaintextValue(char plaintextValue)
	{
		this.plaintextvalue = plaintextValue;
	}

	/**
	 * Gets the frequency of the symbol in the cipher text.
	 *
	 * @return the frequency
	 */
	public double getFrequency()
	{
		return percentagefrequency;
	}

	/**
	 * Sets the frequency of the symbol.
	 *
	 * @param frequency
	 *            the new frequency
	 */
	public void setFrequency(double frequency)
	{
		this.percentagefrequency = frequency;
	}

	public boolean isInWord()
	{
		return inWord;
	}

	public void setInWord(boolean inWord)
	{
		this.inWord = inWord;
	}

	public double getPlaintextFrequency()
	{
		return plaintextFrequency;
	}

	public void setPlaintextFrequency(double plaintextFrequency)
	{
		this.plaintextFrequency = plaintextFrequency;
	}

}
