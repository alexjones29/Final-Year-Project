package main;

/**
 * The Class RunApplication.
 */
public class RunApplication
{

	/**
	 * The main method that runs the program.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		
		if (args.length < 2)
		{
			System.out.println("-------------------------------------------------------------------------\n" +					
					"Usage: java CipherCracker.jar filename scoreThreshold seed(optional)\n" +
					"-------------------------------------------------------------------------");
		}
		else 
		{
		String filename = args[0];
		double threshold = Double.valueOf(args[1]);
		long seed = 0;
		if (args.length>2)
		{
			seed = Long.valueOf(args[2]);
		}
		
		CrackerApplication app = new CrackerApplication();
		app.runApplication(filename, threshold, seed);
		}
	}

}
