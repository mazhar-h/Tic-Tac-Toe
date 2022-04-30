/**
*Tic-Tac-Toe Game (CLI)
*
*Description: Playable Tic-Tac-Toe on CLI. Player vs player mode 
* and player vs AI mode. X goes first. O goes second.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.60
*/
import java.util.Scanner;

public class CLIEngine {
	
	private TTTEngine game = new TTTEngine();
	private Scanner keyboard = new Scanner(System.in);
	private AI ai;
	private boolean aiDifficultyHard;
	
	public CLIEngine(String[] args) {
		game = new TTTEngine();
		keyboard = new Scanner(System.in);
		aiDifficultyHard = false;
		checkArguments(args);
	}
	
	public void checkArguments(String[] args) {
		for (String arg : args) {
			if ( arg.toUpperCase().equals("AI") )
				ai = new AI(TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);		
			else if ( arg.toLowerCase().equals("hard") )
				aiDifficultyHard = true;
		}
	}
	
	public void startGame() {
		boolean gameLoop = true;
		
		System.out.println("Welcome to Tic-Tac-Toe!!\n");
		
		while ( gameLoop ) 
		{
			while ( !isGameOver() )
			{						
				System.out.printf("Turn %d\n", game.getTurn());
				printBoard();
				
				if ( ai != null && game.getCurrentPlayer() == TTTEngine.PLAYER_2 )
					moveAI();
				else 
					movePlayer();				
			}
			gameLoop = promptNewGame();
		}
		keyboard.close();
	}
	
	private boolean isGameOver() {
		if ( game.isGameOver() )
		{
			System.out.printf("Turn %d\n", game.getTurn());
			printBoard();
			
			if ( game.isWin(game.getPreviousPlayer()) )
				System.out.printf("Player %c Wins!!!\n\n", game.getPreviousPlayer());
			else if ( game.isDraw() )
				System.out.printf("Draw!!!\n\n");
			
			return true;
		}
		return false;
	}
	
	private void moveAI() {
		int[] moveCoordinate;
		double p = ( !aiDifficultyHard ) ? .55 : 0;
		
		if ( Math.random() < p )
			moveCoordinate = ai.randomMove(game);
		else
			moveCoordinate = ai.getBestMove(game, 9-game.getTurn());
		
		game.makeMove( moveCoordinate[0], moveCoordinate[1] );		
	}
	
	private void movePlayer() {
		int[] moveCoordinate;
		boolean validMove;
						
		moveCoordinate = promptPlayerMove();
		validMove = game.makeMove( moveCoordinate[0], moveCoordinate[1] );
		
		while ( !validMove )
		{
			System.out.printf("Turn %d\n", game.getTurn());
			printBoard();
			
			System.out.printf("That position is occupied!\n\n");
			moveCoordinate = promptPlayerMove();
			validMove = game.makeMove( moveCoordinate[0], moveCoordinate[1] );
		}		
	}
		
	private int promptEnterInteger(String valueName) {
		String valueStr;
		int valueInt;
		
		System.out.printf("Enter %s[1-%d]: ", valueName, TTTEngine.BOARD_SIZE);
		valueStr = keyboard.next();
		
		while( !valueStr.matches("\\d") )
		{
			System.out.printf("Invalid! Re-Enter %s[1-%d]: ", valueName, TTTEngine.BOARD_SIZE);
			valueStr = keyboard.next();
		}
		
		valueInt = Integer.parseInt(valueStr);
		
		while( valueInt < 1 || valueInt > TTTEngine.BOARD_SIZE )
		{
			System.out.printf("Invalid! Re-Enter %s[1-%d]: ", valueName, TTTEngine.BOARD_SIZE);
			valueInt = keyboard.nextInt();
		}
		
		return valueInt;
	}
	
	private boolean promptNewGame() {
				
		System.out.print("Play again? (y/n): ");
		String input = keyboard.next();
		System.out.println();
		
		if ( input.toLowerCase().equals("y") ) 
		{
			game = new TTTEngine();
			return true;
		}
		
		System.out.println("GG and Goodbye!!");
		
		return false;
	}
	
	private int[] promptPlayerMove() {
		
		int[] input = new int[2];
		
		System.out.printf("Player %c\n", game.getCurrentPlayer());
		
		input[0] = promptEnterInteger("row") - 1;
		input[1] = promptEnterInteger("column") - 1;
		System.out.println();
		
		return input;
	}
	
	public void printBoard() {
		
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
