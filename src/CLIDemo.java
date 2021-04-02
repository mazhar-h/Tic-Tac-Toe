/**
*Tic-Tac-Toe Game (CLI)
*
*Description: Playable Tic-Tac-Toe on CLI. Player vs player mode 
* and player vs AI mode. X goes first. O goes second.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.52
*/
import java.util.Scanner;

public class CLIDemo {
	
	private static final char PLAYER_X = 'X';
	private static final char PLAYER_O = 'O';
	private static GameEngine game;
	private static Scanner keyboard;	//player input
	private static AI ai;				//AI input
	private static boolean normalMode;	//AI difficulty
	private static int turn = 1;		//current turn
	private static char icon;			//current icon
	
	public static void main(String[] args){
		game = new GameEngine();
		keyboard = new Scanner(System.in);
		boolean enableAI = false;	//AI mode
		
		//AI check
		if ( args.length > 0 && args[0].toUpperCase().equals("AI") )
		{
			ai = new AI(PLAYER_O, PLAYER_X);
			enableAI = true;
			
			if ( args.length > 1 && args[1].toLowerCase().equals("hard") )
				normalMode = false;
			else
				normalMode = true;
			
		}
				
		System.out.println("Welcome to Tic-Tac-Toe!!\n");
			
		while ( turn < 10 )
		{				
			//determine current player (X or O)
			icon = ( isOddTurn() ) ? PLAYER_X : PLAYER_O;
			
			System.out.printf("Turn %d\n", turn);
			printBoard();
		
			//determine if AI or player move
			game = ( enableAI && !isOddTurn() ) ? aiMove() : playerMove();

			printBoard();
			
			checkGameOver();
			
			//increment game state
			turn++;
			
		}//end while
		
		keyboard.close();
	}
	
	/*
	 * @return Boolean value if the current turn state is odd or even.
	 */
	private static boolean isOddTurn(){
		return (turn % 2 == 0) ? false : true;
	}
		
	/*
	 * Prints out a prompt and stores an integer value from input.
	 * 
	 * @return Integer value for either a row or column of a move.
	 */
	private static int promptEnterInteger(String valueName){
		String valueStr;
		int valueInt;
		
		//get value
		System.out.printf("Enter %s[1-3]: ", valueName);
		valueStr = keyboard.next();
		
		//check if number
		while( !valueStr.matches("\\d") )
		{
			System.out.printf("Invalid! Re-Enter %s[1-3]: ", valueName);
			valueStr = keyboard.next();
		}
		
		//convert to integer
		valueInt = Integer.parseInt(valueStr);
		
		//check if in range
		while( valueInt < 1 || valueInt > 3 )
		{
			System.out.printf("Invalid! Re-Enter %s[1-3]: ", valueName);
			valueInt = keyboard.nextInt();
		}
		
		return valueInt;
	}
	
	/*
	 * Prompts the player to enter the row and column of a move.
	 * 
	 * @return Integer array of size 2 containing the player's move coordinate.
	 * Index 0 is the row value and index 1 is the column value.
	 */
	private static int[] promptPlayerMove(){
		
		int[] input = new int[2];
		
		//display current player
		System.out.printf("Player %c\n", icon);
		
		input[0] = promptEnterInteger("row");
		input[1] = promptEnterInteger("column");
		
		//adjust input so the game can understand it
		input[0]--;
		input[1]--;
		System.out.println();
		
		return input;
	}
	
	/*
	 * Prompts the player if they want to play again.
	 */
	private static boolean promptNewGame(){
				
		System.out.print("Play again? (y/n): ");
		String input = keyboard.next().toLowerCase();
		System.out.println();
		
		if ( input.equals("y") )
			return true;
		else
			return false;
	}
	
	/*
	 * AI makes its best move.
	 * 
	 * @return GameEngine object containing the AI's move.
	 */
	private static GameEngine aiMove(){
		
		//.32 normal mode
		//0 hard mode
		double p = ( normalMode ) ? .32 : 0;
		int[] moveCoordinate;
		
		if ( Math.random() < p )
			moveCoordinate = ai.randomMove(game);
		else
			moveCoordinate = ai.getBestMove(game, 9-turn);
		
		game.makeMove( icon, moveCoordinate[0], moveCoordinate[1] );
		
		return game;
	}
	
	/*
	 * Prompts the player to make a move.
	 * 
	 * @return GameEngine object containing the added player's move.
	 */
	private static GameEngine playerMove(){
		
		int[] moveCoordinate;	//position of move
		boolean validMove;		//if valid move
				
		
		//get user input, make move, and check if valid move
		moveCoordinate = promptPlayerMove();
		validMove = game.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
		
		//re-do move if invalid
		while ( !validMove )
		{
			System.out.printf("Turn %d\n", turn);
			printBoard();
			
			System.out.printf("That position is occupied!\n\n");
			moveCoordinate = promptPlayerMove();
			validMove = game.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
		}
		
		return game;
	}
	
	/*
	 * Checks for a win or draw and resets the game state accordingly.
	 */
	private static void checkGameOver(){
		
		if ( game.isWin(icon) )
			System.out.printf("Player %c Wins!!!\n\n", icon);

		else if ( game.isDraw() )
			System.out.printf("Draw!!!\n\n");
		
		if ( game.isGameOver() )
		{
			if ( promptNewGame() )
			{
				//create new game
				turn = 0;
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
	
	/*
	 * Prints out the current state of the board.
	 */
	public static void printBoard(){
		
		char[][] board = game.getBoard();
		
		for(int row = 0; row < board.length; row++)
		{
			for(int column = 0; column < board[0].length; column++)
				System.out.printf("%c ", board[row][column]);
			
			System.out.println();
		}
		System.out.println();
	}
	
}
