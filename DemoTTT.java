/**
*Tic-Tac-Toe Game
*
*Description: Playable Tic-Tac-Toe on CLI
*
*Date: 03/20/2021
*@author  Mazhar Hossain
*@version 1.0.0
*/
import java.util.Scanner;

public class DemoTTT {
	public static void main(String[] args){
		
		Scanner keyboard = new Scanner(System.in);
		TicTacToe ttt = new TicTacToe();
		int turn = 1;
		
		System.out.println("Welcome to Tic-Tac-Toe!!\n");
		System.out.printf("Turn %d\n", turn);
		ttt.printBoard();
		
		while (turn < 10){
			
			int player = 0; 		//current player
			char icon = '_';		//current player's icon
			int[] moveCoordinate;	//current move
			boolean validMove;		//valid move
			boolean win;			//if move wins
			
			//calculate current player state
			if (turn % 2 == 0){
				player = 2;		//even turn is player 2
				icon = 'O';
			}
			else{
				player = 1;		//odd turn is player 1
				icon = 'X';
			}
			
			//get user input, make move, and check if valid move
			moveCoordinate = moveCoordinateInput(player, keyboard);
			validMove = ttt.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
			
			//re-do move if invalid
			while ( !validMove ){
				System.out.printf("Turn %d\n", turn);
				ttt.printBoard();
				System.out.printf("That position is occupied!\n\n");
				moveCoordinate = moveCoordinateInput(player, keyboard);
				validMove = ttt.makeMove(icon, moveCoordinate[0], moveCoordinate[1]);
			}
			
			//check win condition
			win = ttt.checkWinCondition(icon);
			
			//win condition
			if (win){
				System.out.printf("Player %d Wins!!!\n", player);
				ttt.printBoard();
				
				//create new game
				if (newGameInput(keyboard)){
					turn = 0;
					ttt = new TicTacToe();
				}
				else{
					System.out.println("GG and Goodbye!!");
					break;	//exit
				}
			}//draw condition
			else if (win == false && turn == 9){
				
				System.out.printf("Draw!!!\n");
				ttt.printBoard();
				
				//create new game
				if (newGameInput(keyboard)){
					turn = 0;
					ttt = new TicTacToe();
				}
				else{
					System.out.println("GG and Goodbye!!");
					break; //exit
				}
			}
			
			//next game state
			turn++;
			System.out.printf("Turn %d\n", turn);
			ttt.printBoard();

		}
		keyboard.close();
	}
	
	public static int[] moveCoordinateInput(int player, Scanner keyboard){
		
		int[] input = new int[2];
		
		//display current player
		System.out.printf("Player %d\n", player);
		
		//get row
		System.out.print("Enter row: ");
		input[0] = keyboard.nextInt();
		
		//input checking
		while(input[0] < 1 || input[0] > 3){
			System.out.print("Invalid value! Re-Enter row: ");
			input[0] = keyboard.nextInt();
		}
		
		//get column
		System.out.print("Enter column: ");
		input[1] = keyboard.nextInt();
		
		//input checking
		while(input[1] < 1 || input[1] > 3){
			System.out.print("Invalid value! Re-Enter column: ");
			input[1] = keyboard.nextInt();
		}
		
		//adjust input so the game can understand it
		input[0]--;
		input[1]--;
		System.out.println();
		
		return input;
	}
	
	public static boolean newGameInput(Scanner keyboard){
				
		System.out.print("Play again? (y/n): ");
		String input = keyboard.next().toLowerCase();
		System.out.println();
		
		if ( input.equals("y") )
			return true;
		else
			return false;
	}
}
