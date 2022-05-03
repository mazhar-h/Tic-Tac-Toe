import cli.CLIEngine;
import gui.GUIEngine;

/**
*Tic-Tac-Toe
*
*Description: Tic-Tac-Toe playable in CLI or GUI. X goes first.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.80
*/
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
