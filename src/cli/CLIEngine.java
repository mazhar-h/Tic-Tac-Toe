package cli;

import ai.AI;
import core.Move;
import core.TTTEngine;

public class CLIEngine {
	
	private TTTEngine game;
	private CLIPrompt prompt;
	
	public CLIEngine(String[] args) {
		game = new TTTEngine();
		prompt = new CLIPrompt(game);
		configureGame(args);
		startGame();
	}
	
	private void configureGame(String[] args) {
		for (String arg : args) {
			if ( arg.toLowerCase().equals("normal") )
			{
				game.setAIDifficultyHard(false);
				game.setMaximizingPlayer(TTTEngine.PLAYER_2);
			}
			else if ( arg.toLowerCase().equals("hard") )
			{
				game.setAIDifficultyHard(true);
				game.setMaximizingPlayer(TTTEngine.PLAYER_2);
			}
		}
	}
	
	private void startGame() {
		boolean gameLoop = true;

		prompt.displayWelcomeMessage();
		
		while ( gameLoop ) 
		{
			while ( !isGameOver() )
			{		
				movePlayer();
				moveAI();
			}
			gameLoop = handleGameOver();
		}
		
		prompt.displayExitMessage();
	}

	private boolean handleGameOver() {
		if ( !prompt.enterPlayAgain() )
			return false;
		
		boolean aiDiff = game.isAIDifficultyHard();
		Object maxiPlay = game.getMaximizingPlayer();
		
		game = new TTTEngine();
		prompt = new CLIPrompt(game);
		game.setAIDifficultyHard(aiDiff);
		game.setMaximizingPlayer(maxiPlay);
		return true;
	}
	
	private boolean isGameOver() {
		if ( !game.isGameOver() )
			return false;
		
		prompt.displayCurrentBoard();
		prompt.displayGameOver();
		return true;
	}
		
	private void moveAI() {
		if ( game.getMaximizingPlayer() == null 
				|| game.getCurrentPlayer() != TTTEngine.PLAYER_2
				|| game.getTurn() >= TTTEngine.TURN_GAMEOVER )
			return;
		
		if ( game.isAIDifficultyHard() )
			game.makeMove(AI.getOptimalMove(game, 
					TTTEngine.TURN_GAMEOVER-game.getTurn()-1));
		else
			game.makeMove(AI.getMaybeOptimalMove(game, 
					TTTEngine.RATIONALITY_RATE,
					TTTEngine.TURN_GAMEOVER-game.getTurn()-1));
	}
	
	private void movePlayer() {
		prompt.displayCurrentBoard();
		
		Move m = prompt.enterPlayerMove();
		
		while ( !game.isEmpty(m) ) 
		{
			prompt.displayOccupiedBoard();
			m = prompt.enterPlayerMove();
		}
		
		game.makeMove(m);
	}
}
