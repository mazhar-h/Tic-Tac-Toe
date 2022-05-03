package ai;
import java.util.Random;

import core.Move;

public class AI {
	
	private static int getHeuristicValue(AIObject state, int depth) {
		/*
		 * maximizingPlayer Win	: 1 + depth
		 * DRAW			: 0
		 * maximizingPlayer Loss: -1 - depth
		 */
		
		if ( state.isWin(state.getMaximizingPlayer()) )
			return 1 + depth;
		if ( state.isDraw() )
			return 0;
		else
			return -1 - depth;
	}

	public static Move getMaybeOptimalMove(AIObject state, double rationalityRate, int depth) {
		if ( Math.random() < rationalityRate )
			return getRandomMove(state);
		else
			return getOptimalMove(state, depth);
	}
	
	public static Move getOptimalMove(AIObject state, int depth) {
		Move bestMove = null;
		Move[] availableMoves = state.getAvailableMoves();
		int value = Integer.MIN_VALUE;
		
		for ( Move m : availableMoves ) {
			AIObject s = state.clone();
			s.makeMove( m );
			int v = minimax( s, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false );
			
			if ( value <  v )
			{
				value = v;
				bestMove = m;
			}
		}
		
		return bestMove;
	}

	public static Move getRandomMove(AIObject state) {
		Random rand = new Random(System.currentTimeMillis());
		Move[] m = state.getAvailableMoves();
		
		return m[rand.nextInt(m.length)];
	}	
	
	private static boolean isTerminal(AIObject state) {
		return state.isGameOver();
	}
	
	private static int minimax(AIObject state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		if ( depth == 0 || isTerminal(state) )
			return getHeuristicValue(state, depth);
		
		if ( isMaximizingPlayer ){
			int value = Integer.MIN_VALUE;
			Move[] availableMoves = state.getAvailableMoves();
			
			for ( Move m : availableMoves ) {
				AIObject s = state.clone();
				s.makeMove( m );
				value = Math.max(value, minimax(s, depth - 1, alpha, beta, false));
				if ( value >= beta ) break;
				alpha = Math.max(alpha, value);
			}
			
			return value;
		}
		else {
			int value = Integer.MAX_VALUE;
			Move[] availableMoves = state.getAvailableMoves();
			
			for ( Move m : availableMoves ) {
				AIObject s = state.clone();
				s.makeMove( m );
				value = Math.min(value, minimax(s, depth - 1, alpha, beta, true));
				if ( value <= alpha ) break;
				beta = Math.min(beta, value);
			}
			
			return value;
		}
	}
}
