
public class TTTDemo {
	public static void main(String[] args) {
		for (String arg : args) 
		{
			if ( arg.toUpperCase().equals("GUI") )
			{
				new GUIEngine();
				break;
			}
			else if ( arg.toUpperCase().equals("CLI") ) 
			{
				new CLIEngine(args);
				break;
			}			
		}
	}
}
