package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ai.AI;
import core.Move;
import core.TTTEngine;

public class GUIEngine implements ActionListener {

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
			movePlayer(button.getMove());
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
		if ( game.getMaximizingPlayer() == null 
				|| game.getCurrentPlayer() != TTTEngine.PLAYER_2 
				|| game.getTurn() >= TTTEngine.TURN_GAMEOVER )
			return;

		if ( game.isAIDifficultyHard() )
			movePlayer(AI.getOptimalMove(game, 
					TTTEngine.TURN_GAMEOVER-game.getTurn()-1));
		else
			movePlayer(AI.getMaybeOptimalMove(game, 
					TTTEngine.RATIONALITY_RATE, 
					TTTEngine.TURN_GAMEOVER-game.getTurn()-1));
	}
	
	private void movePlayer(Move move) {
		board.setButton(move, game.getCurrentPlayer());
		game.makeMove( move );
	}

	private void newGame() {
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
						game.setAIDifficultyHard(false);
						game.setMaximizingPlayer(TTTEngine.PLAYER_2);
						break;
					case JOptionPane.NO_OPTION:
						game.setAIDifficultyHard(true);
						game.setMaximizingPlayer(TTTEngine.PLAYER_2);
						break;
				}
				break;
		} 		
	}
}
