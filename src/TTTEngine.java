/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe Game Engine
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.61
*/

public class TTTEngine {
	
	public static final String GAME_NAME = "Tic-Tac-Toe";
	public static final char PLAYER_1 = 'X';
	public static final char PLAYER_2 = 'O';
	private static final char PLACEHOLDER = '_';
	public static final int TURN_GAMEOVER = 10;
	public static final int BOARD_SIZE = 3;
	private char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
	private int turn;
	
	public TTTEngine(){
		for(int row = 0; row < board.length; row++)
			for(int column = 0; column < board[0].length; column++)
				board[row][column] = PLACEHOLDER;

		turn = 1;
	}
	
	public TTTEngine(char[][] board, int turn) {
		char[][] copy = new char[BOARD_SIZE][BOARD_SIZE];
		
		for(int row = 0; row < board.length; row++)
				System.arraycopy(board[row], 0, copy[row], 0, board.length);
		
		this.board = copy;
		this.turn = turn;
	}
		
	private boolean checkHorizontals(char player) {
		for (int row = 0; row < board.length; row++)
		{
			int count = 0;

			for (int column = 0; column < board[0].length; column++)
				if ( board[row][column] == player )
					count++;
		
			if ( count == BOARD_SIZE )
				return true;
		}
		return false;
	}
	
	private boolean checkVerticals(char player) {	
		for (int column = 0; column < board.length; column++)
		{
			int count = 0;

			for (int row = 0; row < board[0].length; row++)
				if ( board[row][column] == player )
					count++;
		
			if ( count == BOARD_SIZE )
				return true;
		}
		return false;
	}
	
	private boolean checkDiagonals(char player) {
		int count = 0;

		//check
		// #
		//  #
		//   #
		count += (board[0][0] == player) ? 1 : 0;
		count += (board[1][1] == player) ? 1 : 0;
		count += (board[2][2] == player) ? 1 : 0;
		
		if ( count == BOARD_SIZE )
			return true;
		
		count = 0;
	
		//check
		//   #
		//  #
		// #
		count += (board[0][2] == player) ? 1 : 0;
		count += (board[1][1] == player) ? 1 : 0;
		count += (board[2][0] == player) ? 1 : 0;

		if ( count == BOARD_SIZE )
			return true;
		
		return false;
	}
	
	public char[][] getBoard() {
		char[][] copy = new char[BOARD_SIZE][BOARD_SIZE];
		
		for(int row = 0; row < board.length; row++)
				System.arraycopy(board[row], 0, copy[row], 0, board.length);
		
		return copy;
	}
	
	public char getCurrentPlayer() {
		return ( turn % 2 == 0 ) ? PLAYER_2 : PLAYER_1;
	}
	
	public char getPreviousPlayer() {
		return ( (turn - 1) % 2 == 0 ) ? PLAYER_2 : PLAYER_1;
	}
	
	public int getTurn(){
		return turn;
	}
	
	public boolean isDraw() {		
		if ( !isWin(PLAYER_1) && !isWin(PLAYER_2) && turn == TURN_GAMEOVER )
			return true;
		
		return false;
	}
	
	public boolean isEmpty(int row, int column) {
		if (board[row][column] == PLACEHOLDER)
			return true;
		
		return false;
	}
	
	public boolean isGameOver() {
		if ( isWin(PLAYER_1) || isWin(PLAYER_2) || turn == TURN_GAMEOVER )
			return true;
		
		return false;
	}
	
	public boolean isWin(char player) {
		if ( checkHorizontals(player) || checkVerticals(player) || checkDiagonals(player) )
			return true;
		
		return false;
	}
	
	public boolean makeMove(int row, int column) {
		if ( !isEmpty(row, column) )
			return false;
		
		board[row][column] = getCurrentPlayer();
		turn++;
		
		return true;
	}
}
