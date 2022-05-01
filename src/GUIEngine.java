import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class GUIEngine  implements ActionListener {

	private AI ai;
	private TTTEngine game;
	private GUIBoard board;
	private GUIPrompt prompt;
	
	public GUIEngine() {
		newGame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		GUIBoardButton button = (GUIBoardButton) e.getSource();
		
		if ( !button.isClicked() ) 
		{
			movePlayer(button.getRow(), button.getColumn());
			if ( game.isGameOver() ) { handleGameOver(); return; };
			moveAI();
			if ( game.isGameOver() ) { handleGameOver(); return; };
		}
	}
	
	private void handleGameOver() {
		prompt.displayGameOver();
		board.buttonsDisable();
		
		if ( prompt.selectPlayAgain() != JOptionPane.YES_OPTION )
			System.exit(0);
		else {
			board.close();
			newGame();
		}
	}
	
	private void moveAI() {
		if ( ai == null 
				|| game.getCurrentPlayer() != TTTEngine.PLAYER_2 
				|| game.getTurn() >= TTTEngine.TURN_GAMEOVER )
			return;

		int row, col;
		int[] moveCoordinate = ai.getMove();	
		
		row = moveCoordinate[0];
		col = moveCoordinate[1];

		movePlayer(row, col);
	}
	
	private void movePlayer(int row, int col) {
		board.setButton(row, col, game.getCurrentPlayer());
		game.makeMove( row, col );
	}

	private void newGame() {
		ai = null;
		game = new TTTEngine();
		prompt = new GUIPrompt(game);
		userConfigureGame();
		board = new GUIBoard(this);
	}
	
	private void userConfigureGame() {
		switch ( prompt.selectMode() ) {
			case JOptionPane.CLOSED_OPTION:
				System.exit(0);
			case JOptionPane.YES_OPTION:
				break;
			case JOptionPane.NO_OPTION:
				switch ( prompt.selectAIDifficulty() ) {
					case JOptionPane.CLOSED_OPTION:
						System.exit(0);
					case JOptionPane.YES_OPTION:
						ai = new AI(game, false, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
						break;
					case JOptionPane.NO_OPTION:
						ai = new AI(game, true, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
						break;
				}
				break;
		} 		
	}
}
