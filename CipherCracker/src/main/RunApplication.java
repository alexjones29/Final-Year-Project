package main;

public class RunApplication
{

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args)
	{

		if (args.length < 1)
		{
			System.out.println("-------------------------------------------------------------------------\n"
					+ "Usage: java CipherCracker.jar scoreThreshold seed(optional)\n"
					+ "-------------------------------------------------------------------------");
		} else
		{
			double threshold = Double.valueOf(args[0]);
			long seed = 0;
			if (args.length > 1)
			{
				seed = Long.valueOf(args[1]);
			}
			CrackerApplication app = new CrackerApplication();
			app.runApplication(threshold, seed);
		}
	}
}
