/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe AI using minimax. Goes second as O.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.52
*/
import java.util.Random;

public class AI {
	
	private Random rand = new Random(System.currentTimeMillis());
	private char maxIcon;	//maximizing player's icon
	private char minIcon;	//minimizing player's icon
	
	public AI (char maximizingPlayer, char minimizingPlayer){
		this.maxIcon = maximizingPlayer;
		this.minIcon = minimizingPlayer;
	}
	
	/*
	 * Determines placement at a random cell on the board.
	 * 
	 * @return Integer array where index 0 is the row value and
	 *  index 1 is the column value.
	 */
	public int[] randomMove(GameEngine board){
		
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
	
	/*
	 * Checks if current game state is a terminal state (game over)
	 * 
	 * @return Boolean value is true if terminal else false if not.
	 */
	private boolean isTerminal(GameEngine board){
		
		//check if winning or draw state
		if ( board.isGameOver() )
			return true;
		else
			return false;
	}
	
	/* 
	 * @return Integer value for the heuristic score.
	 */
	private int getHeuristicValue(GameEngine board){
		
		/*
		 *  O 	: 1
		 * 	DRAW: 0
		 *  X	: -1
		 */
		
		if ( board.isWin(maxIcon) )
			return 1;
		else if ( board.isDraw() )
			return 0;
		else
			return -1;
	}
	
	/*
	 * Determines whether a successor game state is an optimal choice dictated
	 * by the heuristic score.
	 * 
	 * @return Integer value of the heuristic score.
	 */
	private int minimax(GameEngine board, int depth, boolean maximizingPlayer){
		
		if ( depth == 0 || isTerminal(board) )
			return getHeuristicValue(board);
		
		if ( maximizingPlayer ){
			int value = -999;
			
			for(int row = 0; row < 3; row++)
				for(int column = 0; column < 3; column++)
				{	
					//make next available move
					if(board.isEmpty(row, column))
					{
						char[][] b = board.getBoard();
						b[row][column] = maxIcon;
						GameEngine newBoard = new GameEngine(b);
						value = Math.max(value, minimax(newBoard, depth - 1, false) );
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
						b[row][column] = minIcon;
						GameEngine newBoard = new GameEngine(b);
						value = Math.min(value, minimax(newBoard, depth - 1, true) );
					}
				}
			return value;
		}
	}
	
	/*
	 * Determines an optimal move given a current game state.
	 * 
	 * @return Integer array containing the move coordinate where
	 * index 0 is the row value and index 1 is the column value.
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
				if( board.isEmpty(row, column) )
				{
					char[][] b = board.getBoard();
					b[row][column] = maxIcon;
					GameEngine newBoard = new GameEngine(b);
					int v = minimax(newBoard, turnsLeft, false );
					
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
	
}
