/**
*Tic-Tac-Toe Game
*
*Description: Tic-Tac-Toe Game Engine
*
*Date: 03/20/2021
*@author  Mazhar Hossain
*@version 1.0.0
*/

public class TicTacToe {
	
	private char[][] board = new char[3][3];
	private char placeholder = '_';
	
	public TicTacToe(){
		for(int i = 0; i < board.length; i++)
			for(int j = 0; j < board[0].length; j++)
				board[i][j] = placeholder;
	}
	
	public boolean isEmpty(int x, int y){
		
		if (board[x][y] == placeholder)
			return true;
		else
			return false;
	}
	
	public boolean makeMove(char icon, int x, int y){
		
		if (!isEmpty(x, y))
			return false;
		else
			board[x][y] = icon;
		
		return true;
	}
	
	private boolean checkHorizontals(char icon){
		
		int count = 0;
		
		//check every possible horizontal win condition
		for (int row = 0; row < board.length; row++){
			for (int column = 0; column < board[0].length; column++)
				count += (board[row][column] == icon) ? 1 : 0;
		
			if (count == 3)
				return true;
			else
				count = 0;
		}
		
		return false;
	}
	
	private boolean checkVerticals(char icon){
		
		int count = 0;
		
		//check every possible vertical win condition
		for (int column = 0; column < board.length; column++){
			for (int row = 0; row < board[0].length; row++)
				count += (board[row][column] == icon) ? 1 : 0;
		
			if (count == 3)
				return true;
			else
				count = 0;
		}
		
		return false;
	}
	
	private boolean checkDiagonals(char icon){
		
		int count = 0;

		//check
		// #
		//  #
		//   #
		count += (board[0][0] == icon) ? 1 : 0;
		count += (board[1][1] == icon) ? 1 : 0;
		count += (board[2][2] == icon) ? 1 : 0;
		
		if (count == 3)
			return true;
		else
			count = 0;
		
		//check
		//   #
		//  #
		// #
		count += (board[0][2] == icon) ? 1 : 0;
		count += (board[1][1] == icon) ? 1 : 0;
		count += (board[2][0] == icon) ? 1 : 0;

		if (count == 3)
			return true;
		else
			return false;
	}
	
	public boolean checkWinCondition(char icon){
		if ( checkHorizontals(icon) || checkVerticals(icon) || checkDiagonals(icon) )
			return true;
		else
			return false;
	}
	
	public void printBoard(){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				System.out.printf("%c ", board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
