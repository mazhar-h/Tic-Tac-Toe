/**
*Tic-Tac-Toe Game
*
*Description: Playable Tic-Tac-Toe on CLI. Player vs player mode 
* and player vs AI mode. X goes first. O goes second.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.18
*/
import java.util.Scanner;

public class CLIDemo {
	
	private static final char PLAYER1 = 'X';
	private static final char PLAYER2 = 'O';
	private static GameEngine game;
	private static Scanner keyboard;//player input
	private static AI ai;			//AI input
	private static int turn = 1;	//current turn
	private static int player;		//current player
	private static char icon;		//current icon
	
	public static void main(String[] args){
		game = new GameEngine();
		keyboard = new Scanner(System.in);
		boolean enableAI = false;	//AI mode
		
		//AI check
		if ( args.length > 0 && args[0].toUpperCase().equals("AI") )
		{
			ai = new AI(PLAYER2, PLAYER1);
			enableAI = true;
		}
		
		System.out.println("Welcome to Tic-Tac-Toe!!\n");
			
		while ( turn < 10 )
		{				
			//determine current player
			if ( isOddTurn() )
			{
				player = 1;
				icon = PLAYER1;
			}
			else{
				player = 2;
				icon = PLAYER2;
			}
			
			//if AI else player input
			if ( enableAI && !isOddTurn() )
			{
				game = aiMove();
			}else{
				game = playerMove();
			}
			
			checkGameState();
			
			turn++;	//increment game state
			
		}//end while
		
		keyboard.close();
	}
	
	private static boolean isOddTurn(){
		return (turn % 2 == 0) ? false : true;
	}
	
	private static int promptEnterInteger(String valueName){
		String valueStr;
		int valueInt;
		
		//get value
		System.out.printf("Enter %s: ", valueName);
		valueStr = keyboard.next();
		
		//check if number
		while( !valueStr.matches("\\d") )
		{
			System.out.printf("Invalid value! Re-Enter %s: ", valueName);
			valueStr = keyboard.next();
		}
		
		//convert to integer
		valueInt = Integer.parseInt(valueStr);
		
		//check if in range
		while( valueInt < 1 || valueInt > 3 )
		{
			System.out.printf("Invalid value! Re-Enter %s: ", valueName);
			valueInt = keyboard.nextInt();
		}
		
		return valueInt;
	}
			
	private static int[] promptPlayerMove(){
		
		int[] input = new int[2];
		
		//display current player
		System.out.printf("Player %d\n", player);
		
		input[0] = promptEnterInteger("row");
		input[1] = promptEnterInteger("column");
		
		//adjust input so the game can understand it
		input[0]--;
		input[1]--;
		System.out.println();
		
		return input;
	}
		
	private static boolean promptNewGame(){
				
		System.out.print("Play again? (y/n): ");
		String input = keyboard.next().toLowerCase();
		System.out.println();
		
		if ( input.equals("y") )
			return true;
		else
			return false;
	}
	
	private static GameEngine aiMove(){
		
		//32% chance to choose random move
		//68% chance to choose optimal move
		if ( Math.random() < .32 )
		{
			int[] randomMoveCoordinate = ai.randomMove(game);
			game.makeMove(icon, randomMoveCoordinate[0], randomMoveCoordinate[1]);
		}else
		{
			int[] aiMoveCoordinate = ai.getBestMove(game, 9-turn);
			game.makeMove(icon, aiMoveCoordinate[0], aiMoveCoordinate[1]);
		}
		return game;
	}
	
	private static GameEngine playerMove(){
		
		int[] moveCoordinate;	//position of move
		boolean validMove;		//if valid move
				
		System.out.printf("Turn %d\n", turn);
		game.printBoard();
		
		//get user input, make move, and check if valid move
		moveCoordinate = promptPlayerMove();
		validMove = game.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
		
		//re-do move if invalid
		while ( !validMove )
		{
			System.out.printf("Turn %d\n", turn);
			game.printBoard();
			
			System.out.printf("That position is occupied!\n\n");
			moveCoordinate = promptPlayerMove();
			validMove = game.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
		}
		
		return game;
	}
	
	private static void checkGameState(){
		boolean win;
		
		//check if winning or draw game state
		if ( win = game.isWin(icon) )
		{
			game.printBoard();
			System.out.printf("Player %d Wins!!!\n\n", player);
		}
		else if ( turn == 9 )
		{
			game.printBoard();
			System.out.printf("Draw!!!\n\n");
		}
		
		//check if game over
		if ( win || turn == 9 )
		{
			//create new game
			if (promptNewGame())
			{
				turn = 1;
				game = new GameEngine();
			}
			else
			{
				//exit
				turn = 9;
				System.out.println("GG and Goodbye!!");
			}
		}
	}
	
}
