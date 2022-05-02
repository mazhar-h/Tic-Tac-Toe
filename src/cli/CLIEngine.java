package cli;

import core.AI;
import core.TTTEngine;

public class CLIEngine {
	
	private AI ai;
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
				ai = new AI(game, false, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);		
			else if ( arg.toLowerCase().equals("hard") )
				ai = new AI(game, true, TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
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
		
		game = new TTTEngine();
		prompt = new CLIPrompt(game);
		
		if ( ai != null)
			ai = new AI(game, ai.isDifficultyHard(), TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
		
		return true;
	}
	
	private boolean isGameOver() {
		if ( !game.isGameOver() )
			return false;
		
		prompt.displayCurrentBoard();
		prompt.displayGameOver();
		return true;
	}
	
	private boolean makeMove(int[] moveCoordinate) {
		int row = moveCoordinate[0];
		int col = moveCoordinate[1];
		
		return game.makeMove( row, col );
	}

	private void moveAI() {
		if ( ai != null 
			&& game.getCurrentPlayer() == TTTEngine.PLAYER_2 
			&& game.getTurn() < TTTEngine.TURN_GAMEOVER)
			makeMove(ai.getMove());
	}
	
	private void movePlayer() {
		prompt.displayCurrentBoard();

		while ( !makeMove(prompt.enterPlayerMove()) )
			prompt.displayOccupiedBoard();
	}
}
