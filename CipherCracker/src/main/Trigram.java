package main;

/**
 * The Class Trigram.
 */
public class Trigram
{
	private String value;
	private double frequency;

	/**
	 * Instantiates a new trigram.
	 *
	 * @param value the value
	 * @param frequency the frequency
	 */
	public Trigram(String value, double frequency) 
	{
		this.value = value;
		this.frequency = frequency;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	
	/**
	 * Gets the frequency.
	 *
	 * @return the frequency
	 */
	public double getFrequency()
	{
		return frequency;
	}
	
	/**
	 * Sets the frequency.
	 *
	 * @param frequency the new frequency
	 */
	public void setFrequency(double frequency)
	{
		this.frequency = frequency;
	}
}
