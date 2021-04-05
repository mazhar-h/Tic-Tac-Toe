/**
*Tic-Tac-Toe Game (CLI)
*
*Description: Playable Tic-Tac-Toe on CLI. Player vs player mode 
* and player vs AI mode. X goes first. O goes second.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.56
*/
import java.util.Scanner;

public class CLIDemo {
	
	private static final char PLAYER_1 = 'X';
	private static final char PLAYER_2 = 'O';
	private static GameEngine game;
	private static int turn;			//turn state
	private static Scanner keyboard;	//player input
	private static AI ai;			//AI input
	private static boolean normalMode;	//AI difficulty
	
	public static void main(String[] args){
		
		game = new GameEngine();
		keyboard = new Scanner(System.in);
		boolean enableAI = false;	//AI mode
		turn = game.getTurn();
		
		//AI check
		if ( args.length > 0 && args[0].toUpperCase().equals("AI") )
		{
			ai = new AI(PLAYER_2, PLAYER_1);
			enableAI = true;
			
			if ( args.length > 1 && args[1].toLowerCase().equals("hard") )
				normalMode = false;
			else
				normalMode = true;
			
		}
				
		System.out.println("Welcome to Tic-Tac-Toe!!\n");
			
		while ( turn < 10 )
		{						
			System.out.printf("Turn %d\n", turn);
			
			printBoard();
		
			//determine if AI or player move
			if ( enableAI && game.getCurrentPlayer() == PLAYER_2 )
				game = moveAI();
			else 
				game = movePlayer();
			
			turn = checkGameOver();
		}
		
		keyboard.close();
	}
	
	/*
	 * Checks for a win or draw and advances or resets accordingly.
	 * If the game state is not a game over, the turn state is advanced.
	 * 
	 * @return	the advanced turn state.
	 */
	private static int checkGameOver(){
				
		if ( game.isGameOver() )
		{
			System.out.printf("Turn %d\n", turn);
			
			printBoard();
			
			if ( game.isWin() )
				System.out.printf("Player %c Wins!!!\n\n", game.getCurrentPlayer());
			if ( game.isDraw() )
				System.out.printf("Draw!!!\n\n");
			if ( promptNewGame() )
			{
				game = new GameEngine();
				return game.getTurn();
			}
			else
			{
				System.out.println("GG and Goodbye!!");
				return 10;
			}
		}
		
		return game.advanceTurn();
	}
	
	/*
	 * AI makes its best move.
	 * 
	 * @return	new game state containing the AI's move.
	 */
	private static GameEngine moveAI(){
		
		//.32 normal mode
		//0 hard mode
		double p = ( normalMode ) ? .32 : 0;
		int[] moveCoordinate;
		
		if ( Math.random() < p )
			moveCoordinate = ai.randomMove(game);
		else
			moveCoordinate = ai.getBestMove(game, 9-turn);
		
		game.makeMove( moveCoordinate[0], moveCoordinate[1] );
		
		return game;
	}
	
	/*
	 * Prompts the player to make a move.
	 * 
	 * @return	new game state containing the added player's move.
	 */
	private static GameEngine movePlayer(){
		int[] moveCoordinate;	//store row and column
		boolean validMove;		//if valid move
				
		
		//get user input, make move, and check if valid move
		moveCoordinate = promptPlayerMove();
		validMove = game.makeMove( moveCoordinate[0], moveCoordinate[1] );
		
		//re-do move if invalid
		while ( !validMove )
		{
			System.out.printf("Turn %d\n", turn);
			printBoard();
			
			System.out.printf("That position is occupied!\n\n");
			moveCoordinate = promptPlayerMove();
			validMove = game.makeMove( moveCoordinate[0], moveCoordinate[1] );
		}
		
		return game;
	}
		
	/*
	 * Prints out a prompt and stores an integer value from input.
	 * 
	 * @param	valueName	the input name that is being taken.
	 *
	 * @return	an integer value that may represent the row or column.
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
	 * Prompts the player if they want to play again.
	 * 
	 * @return	is true if new game else false if quit.
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
	 * Prompts the player to enter the row and column of a move.
	 * 
	 * @return	the player's move coordinate. Index 0 is the row value 
	 *		and index 1 is the column value.
	 */
	private static int[] promptPlayerMove(){
		
		int[] input = new int[2];
		
		System.out.printf("Player %c\n", game.getCurrentPlayer());
		
		input[0] = promptEnterInteger("row");
		input[1] = promptEnterInteger("column");
		
		//adjust input so the game can understand it
		input[0]--;
		input[1]--;
		System.out.println();
		
		return input;
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
