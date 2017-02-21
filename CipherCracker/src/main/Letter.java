package main;

/**
 * The Class letter.
 */
public class Letter
{

	/** The value. */
	private String value;
	
	/** The frequency. */
	private int frequency;
	
	/**
	 * Instantiates a new letter.
	 *
	 * @param value the value
	 */
	public Letter(String value) {
		this.value = value;
	}

	/**
	 * Gets the letter value.
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * Sets the letter value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * Gets the frequency of the letter in the english letter.
	 *
	 * @return the frequency
	 */
	public int getFrequency()
	{
		return frequency;
	}
	
	/**
	 * Sets the frequency of the letter in the english language.
	 *
	 * @param frequency the new frequency
	 */
	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}
}
