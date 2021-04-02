/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe Game Engine
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.52
*/

public class GameEngine {
	
	private static final char PLACEHOLDER = '_';
	private char[][] board = new char[3][3];
	
	public GameEngine(){
		
		//fill board with PLACEHOLDER
		for(int row = 0; row < board.length; row++)
			for(int column = 0; column < board[0].length; column++)
				board[row][column] = PLACEHOLDER;
	}
	
	public GameEngine(char[][] board){
		
		char[][] copy = new char[3][3];	//deep copy board
		
		for(int row = 0; row < board.length; row++)
				System.arraycopy(board[row], 0, copy[row], 0, board.length);
		
		this.board = copy;
	}
	
	/*
	 * @return Char array of the current game board.
	 */
	public char[][] getBoard(){
		
		char[][] copy = new char[3][3];	//deep copy board
		
		for(int row = 0; row < board.length; row++)
				System.arraycopy(board[row], 0, copy[row], 0, board.length);
		
		return copy;
	}
	
	/*
	 * Checks the cell state specified by row and column on the board.
	 * 
	 * @return Boolean value is true if empty else false if not empty.
	 */
	public boolean isEmpty(int row, int column){
		
		if (board[row][column] == PLACEHOLDER)
			return true;
		else
			return false;
	}
	
	/* Marks the cell at the specified row and column.
	 * 
	 * @return Boolean value is false if cell is occupied.
	 */
	public boolean makeMove(char icon, int row, int column){
		
		if (!isEmpty(row, column))
			return false;
		else
			board[row][column] = icon;
		
		return true;
	}
	
	/*
	 * Checks for a winning state at horizontal positions.
	 * 
	 * @return Boolean value is true if winning state else false if not.
	 */
	private boolean checkHorizontals(char icon){
		
		int count = 0;
		
		//check every possible horizontal win condition
		for (int row = 0; row < board.length; row++)
		{
			for (int column = 0; column < board[0].length; column++)
			{
				if ( !(board[row][column] == icon) )
					count += 0;
				else
					count++;
			}
		
			if ( count == 3 )
				return true;
			else
				count = 0;
		}
		
		return false;
	}
	
	/*
	 * Checks for a winning state at vertical positions.
	 * 
	 * @return Boolean value is true if winning state else false if not.
	 */
	private boolean checkVerticals(char icon){	
		
		int count = 0;
		
		//check every possible vertical win condition
		for (int column = 0; column < board.length; column++)
		{
			for (int row = 0; row < board[0].length; row++)
			{
				if ( !(board[row][column] == icon) )
					count += 0;
				else
					count++;
			}
		
			if ( count == 3 )
				return true;
			else
				count = 0;
		}
		
		return false;
	}
	
	/*
	 * Checks for a winning state diagonal positions.
	 * 
	 * @return Boolean value is true if winning state else false if not.
	 */
	private boolean checkDiagonals(char icon){
		
		int count = 0;

		//check
		// #
		//  #
		//   #
		count += (board[0][0] == icon) ? 1 : 0;
		count += (board[1][1] == icon) ? 1 : 0;
		count += (board[2][2] == icon) ? 1 : 0;
		
		if ( !(count == 3) )
			count = 0;
		else
			return true;
		
		//check
		//   #
		//  #
		// #
		count += (board[0][2] == icon) ? 1 : 0;
		count += (board[1][1] == icon) ? 1 : 0;
		count += (board[2][0] == icon) ? 1 : 0;

		if ( !(count == 3) )
			return false;
		else
			return true;
	}
	
	/*
	 * Checks for a winning game state.
	 * 
	 * @return Boolean value is true if winning state else false if note.
	 */
	public boolean isWin(char icon){
		if ( checkHorizontals(icon) || checkVerticals(icon) || checkDiagonals(icon) )
			return true;
		else
			return false;
	}
	
	/*
	 * Checks for a draw games state.
	 * 
	 * @return Boolean value is true if draw else false if not.
	 */
	public boolean isDraw(){
		
		//check for empty spots on the board
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[0].length; j++)
			{
				if( board[i][j] == '_' )
					return false;
			}
		
		if ( isWin('X') || isWin('O') )
			return false;
		else
			return true;
	}
	
	/*
	 * Checks if the game is over determined by a win or draw state.
	 * 
	 * @return Boolean value is true game is over else false if not.
	 */
	public boolean isGameOver(){
		
		if ( isWin('X') || isWin('O') )
			return true;
		
		//check for empty spots on the board
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[0].length; j++)
			{
				if( board[i][j] == '_' )
					return false;
			}
		
		return true;
	}
}
