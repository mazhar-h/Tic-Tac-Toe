import java.util.Scanner;

public class CLIEngine {
	
	private AI ai;
	private TTTEngine game;
	private Scanner keyboard;
	
	public CLIEngine(String[] args) {
		game = new TTTEngine();
		keyboard = new Scanner(System.in);
		configureGame(args);
		startGame();
	}
	
	private void configureGame(String[] args) {
		for (String arg : args) {
			if ( arg.toLowerCase().equals("normal") )
				ai = new AI(game, false, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);		
			else if ( arg.toLowerCase().equals("hard") )
				ai = new AI(game, true, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
		}
	}
	
	private void startGame() {
		boolean gameLoop = true;
		
		System.out.printf("Welcome to %s!!\n\n", TTTEngine.GAME_NAME);
		
		while ( gameLoop ) 
		{
			while ( !isGameOver() )
			{		
				movePlayer();

				if ( ai != null && game.getCurrentPlayer() == TTTEngine.PLAYER_2 )
					moveAI(); 
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
		int row, col;
		int[] moveCoordinate = ai.getMove();
		
		row = moveCoordinate[0];
		col = moveCoordinate[1];

		game.makeMove( row, col );		
	}
	
	private void movePlayer() {
		int[] moveCoordinate;
		int row, col;
		boolean validMove;

		System.out.printf("Turn %d\n", game.getTurn());
		printBoard();				
		moveCoordinate = promptPlayerMove();
		row = moveCoordinate[0];
		col = moveCoordinate[1];
		validMove = game.makeMove( row, col );
		
		while ( !validMove )
		{
			System.out.printf("Turn %d\n", game.getTurn());
			printBoard();
			System.out.printf("That position is occupied!\n\n");
			moveCoordinate = promptPlayerMove();
			row = moveCoordinate[0];
			col = moveCoordinate[1];
			validMove = game.makeMove( row, col );
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
			ai = new AI(game, ai.isDifficultyHard(), TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
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
	
	private void printBoard() {	
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
