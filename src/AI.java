/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe AI using minimax with alpha beta pruning. Goes second as O.
*
*Date: 03/22/2021
*@author  Mazhar Hossain
*@version 0.0.60
*/
import java.util.Random;

public class AI {
	
	private static final double RATIONAL_RATE = .55;
	private TTTEngine game;
	private boolean isDifficultyHard;
	private char maximizingPlayer;
	private char minimizingPlayer;
	
	public AI (TTTEngine game, boolean isdDifficultyHard, char maximizingPlayer, char minimizingPlayer) {
		this.game = game;
		this.isDifficultyHard = isdDifficultyHard;
		this.maximizingPlayer = maximizingPlayer;
		this.minimizingPlayer = minimizingPlayer;
	}
	
	public int[] getBestMove() {
		int[] bestMove = new int[2];
		int value = Integer.MIN_VALUE;
		
		for(int row = 0; row < TTTEngine.BOARD_SIZE; row++)
			for(int column = 0; column < TTTEngine.BOARD_SIZE; column++)
			{
				if( game.isEmpty(row, column) )
				{
					char[][] b = game.getBoard();
					b[row][column] = maximizingPlayer;
					TTTEngine g = new TTTEngine(b, game.getTurn()+1);
					int v = minimax( g, 9-game.getTurn(), Integer.MIN_VALUE, Integer.MAX_VALUE, false );
					
					if ( value <  v )
					{
						value = v;
						bestMove[0] = row;
						bestMove[1] = column;
					}
				}
			}

		return bestMove;
	}
	
	private int getHeuristicValue(TTTEngine game, int depth) {
		/*
		 * maximizingPlayer Win	: 1 + depth
		 * DRAW			: 0
		 * minimizingPlayer Win	: -1 - depth
		 */
		
		if ( game.isWin(maximizingPlayer) )
			return 1 + depth;
		if ( game.isDraw() )
			return 0;
		else
			return -1 - depth;
	}

	public int[] getMove() {
		int[] moveCoordinate;
		double rationality = ( !isDifficultyHard ) ? RATIONAL_RATE : 0;
		
		if ( Math.random() < rationality )
			moveCoordinate = randomMove();
		else
			moveCoordinate = getBestMove();
		
		return moveCoordinate;
	}
	
	private boolean isTerminal(TTTEngine game) {
		if ( game.isGameOver() )
			return true;
		
		return false;
	}
	
	private int minimax(TTTEngine game, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		if ( depth == 0 || isTerminal(game) )
			return getHeuristicValue(game, depth);
		
		if ( isMaximizingPlayer ){
			int value = Integer.MIN_VALUE;
			for(int row = 0; row < TTTEngine.BOARD_SIZE; row++)
				for(int column = 0; column < TTTEngine.BOARD_SIZE; column++)
				{	
					if(game.isEmpty(row, column))
					{
						char[][] b = game.getBoard();
						b[row][column] = maximizingPlayer;
						TTTEngine g = new TTTEngine(b, game.getTurn()+1);
						value = Math.max(value, minimax(g, depth - 1, alpha, beta, false));
						if ( value >= beta ) break;
						alpha = Math.max(alpha, value);
					}
					if ( value >= beta ) break;
				}

			return value;
		}
		else {
			int value = Integer.MAX_VALUE;
			for(int row = 0; row < TTTEngine.BOARD_SIZE; row++)
				for(int column = 0; column < TTTEngine.BOARD_SIZE; column++)
				{
					if(game.isEmpty(row, column))
					{
						char[][] b = game.getBoard();
						b[row][column] = minimizingPlayer;
						TTTEngine g = new TTTEngine(b, game.getTurn()+1);
						value = Math.min(value, minimax(g, depth - 1, alpha, beta, true));
						if ( value <= alpha ) break;
						beta = Math.min(beta, value);
					}
					if ( value <= alpha ) break;
				}

			return value;
		}
	}
	
	public int[] randomMove() {
		Random rand = new Random(System.currentTimeMillis());
		int[] moveCoordinate = new int[2];
		
		while(true)
		{
			int x = rand.nextInt(TTTEngine.BOARD_SIZE);
			int y = rand.nextInt(TTTEngine.BOARD_SIZE);
			
			if ( game.isEmpty(x, y) ){
				moveCoordinate[0] = x;
				moveCoordinate[1] = y;
				break;
			}
		}
		
		return moveCoordinate;
	}	
}