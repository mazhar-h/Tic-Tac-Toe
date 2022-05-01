import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class GUIEngine  implements ActionListener {

	private AI ai;
	private TTTEngine game;
	private GUIBoard board;
	
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
		promptGameOver();
		board.buttonsDisable();
		
		int responsePlayAgain = promptPlayAgain();
		
		board.close();
		
		if ( responsePlayAgain == JOptionPane.YES_OPTION )
			newGame();
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
		userConfigureGame();
		game = new TTTEngine();
		board = new GUIBoard(this);
	}
	
	private void promptGameOver() {
		String messageWin = String.valueOf( game.getPreviousPlayer() ) + " Wins!";
		String messageDraw = "Draw!!";
		String title = "Game Over";
		
		if ( game.isWin( game.getPreviousPlayer() ) )
			JOptionPane.showMessageDialog(null, messageWin, title, JOptionPane.INFORMATION_MESSAGE);
		else if ( game.isDraw() )
			JOptionPane.showMessageDialog(null, messageDraw, title, JOptionPane.INFORMATION_MESSAGE);
	}

	private int promptSelectAIDifficulty() {
		String message = "Select AI difficulty!";
		String title = "Artificial Intelligence";
		String[] optionsAI = {"Normal", "Hard"};
				
		return JOptionPane.showOptionDialog(null,
				message, 
				title, 
				JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    optionsAI, 
			    optionsAI[0]);
	}
	
	private int promptSelectMode() {
		String message = "Select a game mode!!";
		String title = "Welcome!";
		String[] options = {"Player vs Player", "Player vs AI"};
		
		return JOptionPane.showOptionDialog(null,
				message, 
				title, 
				JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options, 
			    options[0]);
	}
	
	private int promptPlayAgain() {
		String message = "Would you like to play again?";
		String title = "Game Over";	
		
		return JOptionPane.showConfirmDialog(null, 
				message, 
				title, 
				JOptionPane.YES_NO_OPTION);
	}
	
	private void userConfigureGame() {
		int responseSelectAIDifficulty;
		int responseSelectMode = promptSelectMode();

		if ( responseSelectMode == JOptionPane.CLOSED_OPTION )
			System.exit(0);
		else if ( responseSelectMode == JOptionPane.NO_OPTION ) 
		{
			responseSelectAIDifficulty = promptSelectAIDifficulty();
			
			if ( responseSelectAIDifficulty == JOptionPane.CLOSED_OPTION )
				System.exit(0);
			if ( responseSelectAIDifficulty == JOptionPane.YES_OPTION )
				ai = new AI(game, false, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1); 
			else if ( responseSelectAIDifficulty == JOptionPane.NO_OPTION )
				ai = new AI(game, true, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
		}		
	}
}
