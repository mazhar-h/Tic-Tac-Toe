package ai;

import core.Move;

public interface AIObject {

	public AIObject clone();
	public boolean isDraw();
	public boolean isGameOver();
	public boolean isWin(Object maximizingPlayer);
	public Move[] getAvailableMoves();
	public Object getMaximizingPlayer();
	public void makeMove(Move m);
	
}
