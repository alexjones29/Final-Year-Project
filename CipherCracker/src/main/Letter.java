package main;

/**
 * The Class letter.
 */
public class Letter
{

	/** The value. */
	private char value;
	
	/** The frequency. */
	private double frequency;
	
	/**
	 * Instantiates a new letter.
	 *
	 * @param value the value
	 */
	public Letter(char value, double frequency) {
		this.value = value;
		this.frequency = frequency;
	}

	/**
	 * Gets the letter value.
	 *
	 * @return the value
	 */
	public char getValue()
	{
		return value;
	}


	/**
	 * Sets the letter value.
	 *
	 * @param value the new value
	 */
	public void setValue(char value)
	{
		this.value = value;
	}
	
	/**
	 * Gets the frequency of the letter in the english letter.
	 *
	 * @return the frequency
	 */
	public double getFrequency()
	{
		return frequency;
	}
	
	/**
	 * Sets the frequency of the letter in the english language.
	 *
	 * @param frequency the new frequency
	 */
	public void setFrequency(double frequency)
	{
		this.frequency = frequency;
	}
}
