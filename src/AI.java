/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe AI using minimax. Goes second as O.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.56
*/
import java.util.Random;

public class AI {
	
	private char maximizingPlayer;
	private char minimizingPlayer;
	
	/*
	 * Constructor
	 * 
	 * @param	maximizingPlayer	the player who will have the best move determined for.
	 * @param	minimizingPlayer	the player who is playing against the maximizing player.
	 */
	public AI (char maximizingPlayer, char minimizingPlayer){
		this.maximizingPlayer = maximizingPlayer;
		this.minimizingPlayer = minimizingPlayer;
	}
	
	/*
	 * Determines an optimal move given a current game state.
	 * 
	 * @param	board		current game state.
	 * @param	turnsLeft	amount of turns left in the game.
	 * 
	 * @return	the move coordinate where
	 *	index 0 is the row value and index 1 is the column value.
	 */
	public int[] getBestMove(GameEngine board, int turnsLeft){
		
		int[] bestMove = new int[2];
		int value = -999;
		
		/*
		 * for each possible move available
		 * find the best move to out-play the minimizing player
		 */
		for(int row = 0; row < 3; row++)
			for(int column = 0; column < 3; column++)
			{
				//make next available move
				if( board.isEmpty(row, column) )
				{
					char[][] b = board.getBoard();
					b[row][column] = maximizingPlayer;
					GameEngine g = new GameEngine(b, board.getTurn());
					int v = minimax( g, turnsLeft, false );
					
					//if new move is better than current best move
					if ( value <  v )
					{
						value = v;
						bestMove[0] = row;
						bestMove[1] = column;
					}
				}
			}//end for
		
		return bestMove;
	}
	
	/* 
	 * @param	board	current game state.
	 * 
	 * @return	value for the heuristic score.
	 */
	private int getHeuristicValue(GameEngine board){
		
		/*
		 *  O 	: 1
		 * 	DRAW: 0
		 *  X	: -1
		 */
		
		if ( board.isWin(maximizingPlayer) )
			return 1;
		else if ( board.isDraw() )
			return 0;
		else
			return -1;
	}
	
	/*
	 * Checks if current game state is a terminal state (game over)
	 * 
	 * @param	board	current game state.
	 * 
	 * @return	true if terminal else false if not.
	 */
	private boolean isTerminal(GameEngine board){
		
		//check if winning or draw state
		if ( board.isGameOver() )
			return true;
		else
			return false;
	}
	
	/*
	 * Determines whether a successor game state is an optimal choice dictated
	 * by the heuristic score.
	 * 
	 * @param	board 	current game state.
	 * @param 	depth	the depth that minimax will go.
	 * @param	isMaximizingPlayer	determines if maximizing or minimizing player.
	 * 
	 * @return	value of the heuristic score.
	 */
	private int minimax(GameEngine board, int depth, boolean isMaximizingPlayer){
		
		if ( depth == 0 || isTerminal(board) )
			return getHeuristicValue(board);
		
		if ( isMaximizingPlayer ){
			int value = -999;
			
			for(int row = 0; row < 3; row++)
				for(int column = 0; column < 3; column++)
				{	
					//make next available move
					if(board.isEmpty(row, column))
					{
						char[][] b = board.getBoard();
						b[row][column] = maximizingPlayer;
						GameEngine g = new GameEngine(b, board.getTurn());
						value = Math.max(value, minimax(g, depth - 1, false) );
					}
				}
			return value;
		}
		else {
			int value = 999;
			
			for(int row = 0; row < 3; row++)
				for(int column = 0; column < 3; column++)
				{
					//make next available move
					if(board.isEmpty(row, column))
					{
						char[][] b = board.getBoard();
						b[row][column] = minimizingPlayer;
						GameEngine g = new GameEngine(b, board.getTurn());
						value = Math.min(value, minimax(g, depth - 1, true) );
					}
				}
			return value;
		}
	}
	
	/*
	 * Determines placement at a random cell on the board.
	 * 
	 * @param	board	current game state.
	 * 
	 * @return	move coordinate for a random row and column.
	 */
	public int[] randomMove(GameEngine board){

		Random rand = new Random(System.currentTimeMillis());
		int[] coordinate = new int[2];
		
		while(true)
		{
			int x = rand.nextInt(3);
			int y = rand.nextInt(3);
			
			if ( board.isEmpty(x, y) ){
				coordinate[0] = x;
				coordinate[1] = y;
				break;
			}
		}
		
		return coordinate;
	}	
}
