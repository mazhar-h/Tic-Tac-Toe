package cli;
import java.util.Scanner;

import core.TTTEngine;

public class CLIPrompt {
	
	private TTTEngine game;
	private Scanner keyboard;
	
	public CLIPrompt(TTTEngine game) {
		this.game = game;
		keyboard = new Scanner(System.in);
	}
	
	private int enterInteger(String valueName) {
		String valueStr;
		int valueInt;
		
		System.out.printf("Enter %s[1-%d]: ", valueName, TTTEngine.BOARD_SIZE);
		valueStr = keyboard.next();
		
		while( !valueStr.matches("\\d") )
		{
			System.out.printf("Invalid! Re-Enter %s[1-%d]: ", valueName, TTTEngine.BOARD_SIZE);
			valueStr = keyboard.next();
		}
		
		valueInt = Integer.parseInt(valueStr);
		
		while( valueInt < 1 || valueInt > TTTEngine.BOARD_SIZE )
		{
			System.out.printf("Invalid! Re-Enter %s[1-%d]: ", valueName, TTTEngine.BOARD_SIZE);
			valueInt = keyboard.nextInt();
		}
		
		return valueInt;
	}
	
	public boolean enterPlayAgain() {		
		System.out.print("Play again? (y/n): ");
		String input = keyboard.next();
		System.out.println();
		
		if ( input.toLowerCase().equals("y") ) 
			return true;
		
		return false;
	}
	
	public int[] enterPlayerMove() {
		int[] input = new int[2];
		
		System.out.printf("Player %c\n", game.getCurrentPlayer());
		
		input[0] = enterInteger("row") - 1;
		input[1] = enterInteger("column") - 1;
		System.out.println();
		
		return input;
	}
	
	public void displayCurrentBoard() {
		System.out.printf("Turn %d\n", game.getTurn());
		printBoard();
	}
	
	public void displayExitMessage() {
		System.out.println("GG and Goodbye!!");
	}
	
	public void displayGameOver() {
		if ( game.isWin(game.getPreviousPlayer()) )
			System.out.printf("Player %c Wins!!!\n\n", game.getPreviousPlayer());
		else if ( game.isDraw() )
			System.out.printf("Draw!!!\n\n");
	}
	
	public void displayOccupiedBoard() {
		displayCurrentBoard();
		System.out.println("Occupied! Please try another position.");
	}
	
	public void displayWelcomeMessage() {
		System.out.printf("Welcome to %s!!\n\n", TTTEngine.GAME_NAME);
	}
	
	private void printBoard() {	
		char[][] board = game.getBoard();
        
		for ( char[] rowOfPositions : board ) {
			for ( char position : rowOfPositions ) 
				System.out.printf("%c ", position);
				System.out.println();
		}
		System.out.println();
	}
	
}
