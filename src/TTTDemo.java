
public class TTTDemo {
	public static void main(String[] args){
		GUIEngine gui = new GUIEngine();
		CLIEngine cli = new CLIEngine(args);
		
		for (String arg : args) 
		{
			if ( arg.toUpperCase().equals("GUI") )
			{
				gui.launchGUI();
				break;
			}
			else if ( arg.toUpperCase().equals("CLI") ) 
			{
				cli.startGame();
				break;
			}			
		}
	}
}
