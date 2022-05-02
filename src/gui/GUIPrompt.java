package gui;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import core.TTTEngine;

public class GUIPrompt {
	
	private TTTEngine game;
	private JFrame frame;
	
	public GUIPrompt(TTTEngine game) {
		this.game = game;
		initializeFrame();
	}
	
	private void initializeFrame() {
		this.frame = new JFrame();
		frame.setAlwaysOnTop(true);
	}
	
	public void displayGameOver() {
		String messageWin = String.valueOf( game.getPreviousPlayer() ) + " Wins!";
		String messageDraw = "Draw!!";
		String messageTitle = "Gme Over";
		
		if ( game.isWin( game.getPreviousPlayer() ) )
			JOptionPane.showMessageDialog(frame, messageWin, messageTitle, JOptionPane.INFORMATION_MESSAGE);
		else if ( game.isDraw() )
			JOptionPane.showMessageDialog(frame, messageDraw, messageTitle, JOptionPane.INFORMATION_MESSAGE);		
	}

	public int selectAIDifficulty() {
		String message = "Select AI difficulty!";
		String title = "Artificial Intelligence";
		String[] optionsAI = {"Normal", "Hard"};
				
		return JOptionPane.showOptionDialog(frame,
				message, 
				title, 
				JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    optionsAI, 
			    optionsAI[0]);
	}
	
	public int selectMode() {
		String message = "Select a game mode!!";
		String title = "Welcome!";
		String[] options = {"Player vs Player", "Player vs AI"};
		
		return JOptionPane.showOptionDialog(frame,
				message, 
				title, 
				JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options, 
			    options[0]);
	}
	
	public int selectPlayAgain() {
		String message = "Would you like to play again?";
		String title = "Game Over";	
		
		return JOptionPane.showConfirmDialog(frame, 
				message, 
				title, 
				JOptionPane.YES_NO_OPTION);
	}
}
