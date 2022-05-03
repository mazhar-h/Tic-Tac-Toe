package core;

import java.util.LinkedList;

import ai.AIObject;

public class TTTEngine implements AIObject {
	
	public static final String GAME_NAME = "Tic-Tac-Toe";
	public static final double RATIONALITY_RATE = .55;
	public static final char PLAYER_1 = 'X';
	public static final char PLAYER_2 = 'O';
	private static final char PLACEHOLDER = '_';
	public static final int TURN_GAMEOVER = 10;
	public static final int BOARD_SIZE = 3;
	private char[][] board;
	private int turn;
	private boolean isAIDifficultyHard;
	private Object maximizingPlayer;
	
	public TTTEngine(){
		board = new char[BOARD_SIZE][BOARD_SIZE];
		
		for(int row = 0; row < board.length; row++)
			for(int column = 0; column < board[0].length; column++)
				board[row][column] = PLACEHOLDER;

		turn = 1;
		isAIDifficultyHard = false;
		maximizingPlayer = null;
	}
	
	public TTTEngine(TTTEngine c) {
		char[][] copy = new char[BOARD_SIZE][BOARD_SIZE];
		char[][] boardCopy = c.getBoard();
		
		for(int row = 0; row < boardCopy.length; row++)
				System.arraycopy(boardCopy[row], 0, copy[row], 0, boardCopy.length);
		
		this.board = copy;
		this.turn = c.getTurn();
		this.isAIDifficultyHard = c.isAIDifficultyHard;
		this.maximizingPlayer = c.maximizingPlayer;
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
	
	@Override
	public TTTEngine clone() {
		return new TTTEngine(this);
	}
	
	@Override
	public Move[] getAvailableMoves() {
		LinkedList<Move> list = new LinkedList<>();
		
		for (int row = 0; row < BOARD_SIZE; row++)
			for (int col = 0; col < BOARD_SIZE; col++) {
				Move m = new Move(row, col);
				
				if ( isEmpty( m ) )
					list.add( m );
			}
		
		Move[] moves = new Move[list.size()];
		
		for (int i = 0; i < moves.length; i++)
			moves[i] = list.pop();
		
		return moves;
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
	
	@Override
	public Object getMaximizingPlayer() {
		return maximizingPlayer;
	}
	
	public char getPreviousPlayer() {
		return ( (turn - 1) % 2 == 0 ) ? PLAYER_2 : PLAYER_1;
	}
	
	public int getTurn(){
		return turn;
	}
	
	public boolean isAIDifficultyHard() {
		return isAIDifficultyHard;
	}
	
	@Override
	public boolean isDraw() {		
		if ( !isWin(PLAYER_1) && !isWin(PLAYER_2) && turn == TURN_GAMEOVER )
			return true;
		
		return false;
	}
	
	public boolean isEmpty(Move m) {
		if (board[m.getRow()][m.getColumn()] == PLACEHOLDER)
			return true;
		
		return false;
	}
	
	@Override
	public boolean isGameOver() {
		if ( isWin(PLAYER_1) || isWin(PLAYER_2) || turn == TURN_GAMEOVER )
			return true;
		
		return false;
	}
	
	@Override
	public boolean isWin(Object player) {
		if ( checkHorizontals((char) player) 
				|| checkVerticals((char) player) 
				|| checkDiagonals((char) player) )
			return true;
		
		return false;
	}

	@Override
	public void makeMove(Move m) {		
		board[m.getRow()][m.getColumn()] = getCurrentPlayer();
		turn++;
	}
	
	public void setAIDifficultyHard(boolean bool) {
		isAIDifficultyHard = bool;
	}
	
	public void setMaximizingPlayer(Object o) {
		maximizingPlayer = o;
	}
}
