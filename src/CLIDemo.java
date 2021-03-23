/**
*Tic-Tac-Toe Game
*
*Description: Playable Tic-Tac-Toe on CLI. Default 2-player manual mode
*			  and optional player vs AI mode. Player 1 is X. Player 2 is O.
*			  X goes first. O goes second.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.14
*/
import java.util.Scanner;

public class CLIDemo {
	public static void main(String[] args){
		
		Scanner keyboard = new Scanner(System.in);
		GameEngine game = new GameEngine();
		AI ai = new AI();
		boolean enableAI = false;	//AI mode
		int turn = 1;				//keep track of player
		
		//AI check
		if ( args.length > 0 && args[0].toUpperCase().equals("AI") )
			enableAI = true;
		
		System.out.println("Welcome to Tic-Tac-Toe!!\n");
			
		while ( turn < 10 )
		{	
			int player = 0;			//current player
			char icon = '_';		//current player's icon
			int[] moveCoordinate;	//current move
			boolean validMove;		//if valid move
			boolean win;			//if winning game state
			
			//calculate current player state
			if (turn % 2 == 0)
			{
				player = 2;
				icon = 'O';
			}
			else
			{
				player = 1;
				icon = 'X';
			}
			
			//if AI else manual input
			if ( enableAI && turn % 2 == 0 )
			{
				if ( Math.random() < .32 )
				{
					int[] randomMoveCoordinate = ai.randomMove(game);
					game.makeMove(icon, randomMoveCoordinate[0], randomMoveCoordinate[1]);
				}else
				{
					int[] aiMoveCoordinate = ai.getBestMove(game, 9-turn);
					game.makeMove(icon, aiMoveCoordinate[0], aiMoveCoordinate[1]);
				}
			}else
			{
				System.out.printf("Turn %d\n", turn);
				game.printBoard();
				
				//get user input, make move, and check if valid move
				moveCoordinate = moveCoordinatePrompt(player, keyboard);
				validMove = game.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
				
				//re-do move if invalid
				while ( !validMove )
				{
					System.out.printf("Turn %d\n", turn);
					game.printBoard();
					System.out.printf("That position is occupied!\n\n");
					moveCoordinate = moveCoordinatePrompt(player, keyboard);
					validMove = game.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
				}
			}
			
			win = game.checkWinCondition(icon);
			
			//check if winning or draw game state
			if ( win )
			{
				game.printBoard();
				System.out.printf("Player %d Wins!!!\n\n", player);
			}
			else if ( !win && turn == 9 ){
				game.printBoard();
				System.out.printf("Draw!!!\n\n");
			}
			
			turn++;
			
			//game over
			if ( win || turn == 10 )
			{
				//create new game
				if (newGamePrompt(keyboard))
				{
					turn = 1;
					game = new GameEngine();
				}
				else
				{
					//exit
					System.out.println("GG and Goodbye!!");
					break; 
				}
			}
				
		}//end while
		
		keyboard.close();
	}
	
	public static int[] moveCoordinatePrompt(int player, Scanner keyboard){
		
		int[] input = new int[2];
		
		//display current player
		System.out.printf("Player %d\n", player);
		
		//get row
		System.out.print("Enter row: ");
		input[0] = keyboard.nextInt();
		
		//input checking
		while( input[0] < 1 || input[0] > 3 )
		{
			System.out.print("Invalid value! Re-Enter row: ");
			input[0] = keyboard.nextInt();
		}
		
		//get column
		System.out.print("Enter column: ");
		input[1] = keyboard.nextInt();
		
		//input checking
		while( input[1] < 1 || input[1] > 3 )
		{
			System.out.print("Invalid value! Re-Enter column: ");
			input[1] = keyboard.nextInt();
		}
		
		//adjust input so the game can understand it
		input[0]--;
		input[1]--;
		System.out.println();
		
		return input;
	}
	
	public static boolean newGamePrompt(Scanner keyboard){
				
		System.out.print("Play again? (y/n): ");
		String input = keyboard.next().toLowerCase();
		System.out.println();
		
		if ( input.equals("y") )
			return true;
		else
			return false;
	}
}
