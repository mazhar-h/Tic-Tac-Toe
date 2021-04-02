/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe AI using minimax. Goes second as O.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.50
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
	
	private boolean isTerminal(GameEngine board){
		
		//check if winning or draw state
		if ( board.isGameOver() )
			return true;
		else
			return false;
	}
	
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
	
	private int minimax(GameEngine board, int depth, boolean maximizingPlayer){
		
		if ( depth == 0 || isTerminal(board) )
			return getHeuristicValue(board);
		
		if ( maximizingPlayer ){
			int value = -999;
			
			for(int row = 0; row < 3; row++)
				for(int column = 0; column < 3; column++)
				{
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
