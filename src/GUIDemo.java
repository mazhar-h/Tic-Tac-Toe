/**
*Tic-Tac-Toe Game (GUI)
*
*Description: Playable Tic-Tac-Toe with GUI. Player vs player mode 
* and player vs AI mode. X goes first. O goes second.
*
*Date: 04/01/2021
*@author  Mazhar Hossain
*@version 0.0.32
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUIDemo implements ActionListener{
		
	public static void main(String[] args) {
		GUIDemo gui = new GUIDemo();
	}
	
	private GameEngine game;
	private final char PLAYER_X = 'X';
	private final char PLAYER_O = 'O';
	private int turn = 1; 				//current turn
	private AI ai;						//AI input
	private boolean normalMode;			//AI difficulty
	private boolean enableAI = false;	//AI mode

	private JFrame frame = new JFrame();
	private JPanel boardPanel = new JPanel();
	private CellButton[][] boardCells = new CellButton[3][3];
	
	public GUIDemo(){
		game = new GameEngine();
		selectModePrompt();
		
		//setup main frame
		frame.setTitle("Tic-Tac-Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int dimensionX = (int) ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()*.2);
		frame.setMinimumSize( new Dimension(dimensionX, dimensionX) );
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout( new BorderLayout() );
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		boardPanel.setLayout( new GridLayout(3,3) );
		boardPanel.setBackground(Color.WHITE);
		
		for (int row = 0; row < 3; row++)
			for (int column = 0; column < 3; column++){
			boardCells[row][column] = new CellButton();
			boardCells[row][column].addActionListener(this);
			boardPanel.add(boardCells[row][column]);
		}
		
		//create tic-tac-toe border
		boardCells[0][0].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		boardCells[0][1].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		boardCells[0][2].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK) );
		boardCells[1][0].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		boardCells[1][1].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		boardCells[1][2].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK) );
		boardCells[2][0].setBorder( BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK) );
		boardCells[2][1].setBorder( BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK) );
		boardCells[2][2].setBorder( BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK) );

		
		frame.add(boardPanel);
		
	}
	
	/*
	 * @return Boolean value if the current turn state is odd or even.
	 */
	private boolean isOddTurn(){
		return (turn % 2 == 0) ? false : true;
	}
	
	/*
	 * @return The current player icon. X or O.
	 */
	private char getCurrentIcon(){
		return ( isOddTurn() ) ? PLAYER_X : PLAYER_O;
	}
	
	/*
	 * AI determines its best move.
	 * 
	 * @return Integer array of size 2 containing the AI's move coordinate.
	 * Index 0 is row and index 1 is column;
	 */
	private int[] aiMove(){
		
		//.32 normal mode
		//0 hard mode
		double difficultyProbability = ( normalMode ) ? .32 : 0;
		int[] moveCoordinate;
		
		if ( Math.random() < difficultyProbability )
		{
			moveCoordinate = ai.randomMove(game);
		}
		else
		{
			moveCoordinate = ai.getBestMove(game, 9-turn);	
		}
		
		game.makeMove( getCurrentIcon(), moveCoordinate[0], moveCoordinate[1] );
		
		return moveCoordinate;
	}
	
	/*
	 * Asks player to chose game mode
	 * Player vs Player or Player vs AI
	 */
	private void selectModePrompt(){
		String message = "Select a game mode!!";
		String title = "Welcome!";
		String[] options = {"Player vs Player", "Player vs AI"};
		
		int newGamePrompt = JOptionPane.showOptionDialog(frame,
				message, 
				title, 
				JOptionPane.YES_NO_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options, 
			    options[0]);
		
		if ( newGamePrompt != 0 )
		{
			message = "Select AI difficulty!";
			title = "AI Mode";
			String[] optionsAI = {"Normal", "Hard"};
			
			ai = new AI(PLAYER_O, PLAYER_X);
			enableAI = true;
			
			int aiPrompt = JOptionPane.showOptionDialog(frame,
					message, 
					title, 
					JOptionPane.YES_NO_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    optionsAI, 
				    optionsAI[0]);
			
			if ( aiPrompt == 0 )
				normalMode = true;
			else
				normalMode = false;
		}
		else
			enableAI = false;
	}
	
	private void disableCells(){
		for(int row = 0; row < 3; row++)
			for(int column = 0; column < 3; column++)
				boardCells[row][column].setEnabled(false);
	}
	
	/*
	 * Resets the board
	 */
	private void newGame(){
		for(int row = 0; row < 3; row++)
			for(int column = 0; column < 3; column++)
			{
				CellButton c = boardCells[row][column];
				c.setText("");
				c.setEnabled(true);
				c.clicked = false;
				turn = 0;
				game = new GameEngine();
			}
		selectModePrompt();
	}
	
	/*
	 * Asks the player whether they want to play again.
	 */
	private void playAgainPrompt(){
		String message = "Would you like to play again?";
		String title = "Game Over";		
		int n = JOptionPane.showConfirmDialog(frame, 
				message, 
				title, 
				JOptionPane.YES_NO_OPTION);
		
		if ( n == 0 )
			newGame();
		else
			frame.dispatchEvent( new WindowEvent(frame, WindowEvent.WINDOW_CLOSING) );
	}
	
	/*
	 * Checks for a win or draw and resets the game state accordingly.
	 */
	private void checkGameOver(){
		String messageWin = String.valueOf( getCurrentIcon() ) + " Wins!";
		String messageDraw = "Draw!!";
		
		if ( game.isWin( getCurrentIcon() ) )
		{
			JOptionPane.showMessageDialog(frame, messageWin);
			disableCells();
			playAgainPrompt();
		}
		else if ( game.isDraw() )
		{
			JOptionPane.showMessageDialog(frame, messageDraw);
			disableCells();
			playAgainPrompt();
		}
	}
	
	/*
	 * Marks a cell X or O
	 */
	private void markButton(CellButton c){
		if ( isOddTurn() )
		{
			c.setText( String.valueOf(getCurrentIcon()) );
			c.setForeground(Color.RED);
			c.setClicked(true);
		}
		else
		{
			c.setText(String.valueOf( getCurrentIcon() ));					
			c.setForeground(Color.BLUE);
			c.setClicked(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int row = 0; row < 3; row++)
			for (int column = 0; column < 3; column++){
				
				CellButton currentCell = boardCells[row][column];
				
				if ( e.getSource() == currentCell && !currentCell.isClicked() )
				{
					markButton(currentCell);
					game.makeMove(getCurrentIcon(), row, column);
					checkGameOver();
					turn++;
					
					//AI makes move after
					if ( enableAI && turn % 2 == 0 && turn < 10 )
					{
						int[] moveCoordinate = aiMove();
						int x = moveCoordinate[0];
						int y = moveCoordinate[1];
						
						currentCell = boardCells[x][y];
						markButton(currentCell);
						checkGameOver();
						turn++;
					}
				}
			}//end for
	}
	
	class CellButton extends JButton {
		private boolean clicked;
		
		public CellButton(){ 
			clicked = false;
			this.setText( "" );
			this.setFont( new Font("Dialog", Font.PLAIN, 60) );
			this.setFocusable(false);
			this.setRolloverEnabled(false);
			this.setBackground(Color.WHITE);
			this.setHorizontalAlignment(JLabel.CENTER);
			}
		
		public boolean isClicked(){ return clicked; }
		
		public void setClicked(boolean clickMode){ clicked = clickMode; }
	}
}
