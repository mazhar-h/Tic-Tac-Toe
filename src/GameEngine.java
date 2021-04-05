/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe Game Engine
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.56
*/

public class GameEngine {
	
	private static final char PLAYER_1 = 'X';
	private static final char PLAYER_2 = 'O';
	private static final char PLACEHOLDER = '_';
	private char[][] board = new char[3][3];	//board state
	private int turn;				//turn state
	
	/*
	 * Constructor
	 */
	public GameEngine(){
		
		//fill board with PLACEHOLDER
		for(int row = 0; row < board.length; row++)
			for(int column = 0; column < board[0].length; column++)
				board[row][column] = PLACEHOLDER;

		turn = 1;

	}
	
	/*
	 * Constructor
	 * 
	 * @param	board	the game board.
	 * @param	turn	the turn state.
	 */
	public GameEngine(char[][] board, int turn){
				
		char[][] copy = new char[3][3];	//deep copy board
		
		for(int row = 0; row < board.length; row++)
				System.arraycopy(board[row], 0, copy[row], 0, board.length);
		
		this.board = copy;
		this.turn = turn;
	}
	
	/*
	 * Advances the turn state.
	 * 
	 * @return	the advanced turn state.
	 */
	public int advanceTurn(){
		return ++turn;
	}
	
	/*
	 * Checks for a horizontal winning game state.
	 * 
	 * @param	player	the icon of the current player.
	 * 
	 * @return	true if winning state, false if not.
	 */
	private boolean checkHorizontals(char player){
				
		//check every possible horizontal win condition
		for (int row = 0; row < board.length; row++)
		{
			int count = 0;

			for (int column = 0; column < board[0].length; column++)
				if ( board[row][column] == player )
					count++;
		
			if ( count == 3 )
				return true;
		}
		
		return false;
	}
	
	/*
	 * Checks for a vertical winning game state.
	 * 
	 * @param	player	the icon of the current player.
	 * 
	 * @return	true if winning state, false if not.
	 */
	private boolean checkVerticals(char player){	
				
		//check every possible vertical win condition
		for (int column = 0; column < board.length; column++)
		{
			int count = 0;

			for (int row = 0; row < board[0].length; row++)
				if ( board[row][column] == player )
					count++;
		
			if ( count == 3 )
				return true;
		}
		
		return false;
	}
	
	/*
	 * Checks for a diagonal winning game state.
	 * 
	 * @param	player	the icon of the current player.
	 * 
	 * @return	true if winning state, false if not.
	 */
	private boolean checkDiagonals(char player){
		
		int count = 0;

		//check
		// #
		//  #
		//   #
		count += (board[0][0] == player) ? 1 : 0;
		count += (board[1][1] == player) ? 1 : 0;
		count += (board[2][2] == player) ? 1 : 0;
		
		if ( count == 3 )
			return true;
		else
			count = 0;
	
		//check
		//   #
		//  #
		// #
		count += (board[0][2] == player) ? 1 : 0;
		count += (board[1][1] == player) ? 1 : 0;
		count += (board[2][0] == player) ? 1 : 0;

		if ( count == 3 )
			return true;
		else
			return false;
	}
	
	/*
	 * @return	the current game board.
	 */
	public char[][] getBoard(){
		
		char[][] copy = new char[3][3];	//deep copy board
		
		for(int row = 0; row < board.length; row++)
				System.arraycopy(board[row], 0, copy[row], 0, board.length);
		
		return copy;
	}
	
	/*
	 * @return	current player icon. X or O.
	 */
	public char getCurrentPlayer(){
		 // Odd turn goes first, even turn goes second
		return ( turn % 2 == 0 ) ? PLAYER_2 : PLAYER_1;
	}
	
	/*
	 *@return	the current turn state.
	 */
	public int getTurn(){
		return turn;
	}
	
	/*
	 * Checks for a draw games state.
	 * 
	 * @return	true if draw state, false if not.
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
	 * Checks if the cell at the specified row and column is empty.
	 * 
	 * @param	row	row value on the board [0-2].
	 * @param	column	column value on the board [0-2].
	 * 
	 * @return	true if empty, false if not empty.
	 */
	public boolean isEmpty(int row, int column){
		
		if (board[row][column] == PLACEHOLDER)
			return true;
		else
			return false;
	}
	
	/*
	 * Checks if the game is over determined by a win or draw game state.
	 * 
	 * @return	true game is over, false if not.
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
	
	/*
	 * Checks for a winning game state.
	 * 
	 * @param	player	the icon of the current player.
	 * 
	 * @return	true if winning state, false if not.
	 */
	public boolean isWin(char player){
		if ( checkHorizontals(player) || checkVerticals(player) || checkDiagonals(player) )
			return true;
		else
			return false;
	}
	
	/*
	 * Checks for a winning game state for the current player.
	 * 
	 * @return	true if winning state, false if not.
	 */
	public boolean isWin(){
		return isWin( getCurrentPlayer() );
	}
	
	/* Marks the cell at the specified row and column.
	 * 
	 * @param	row	row value on the board [0-2].
	 * @param	column	column value on the board [0-2].
	 * 
	 * @return	false if cell is occupied.
	 */
	public boolean makeMove(int row, int column){
		
		if (!isEmpty(row, column))
			return false;
		else
			board[row][column] = getCurrentPlayer();
		
		return true;
	}
}
