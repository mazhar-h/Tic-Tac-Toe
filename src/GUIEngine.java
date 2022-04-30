/**
*Tic-Tac-Toe Game (GUI)
*
*Description: Playable Tic-Tac-Toe with GUI. Player vs player mode 
* and player vs AI mode. X goes first. O goes second.
*
*Date: 04/01/2021
*@author  Mazhar Hossain
*@version 0.0.60
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUIEngine implements ActionListener {
	
	private static final String GAME_NAME = "Tic-Tac-Toe";
	private static final int GAME_SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*.2);
	private boolean isAIHardDifficulty;
	private AI ai;
	private TTTEngine game;
	private JFrame frame;
	private BoardButton[][] board;
	
	public GUIEngine() {
		newGame();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int row = 0; row < TTTEngine.BOARD_SIZE; row++) {
			for (int col = 0; col < TTTEngine.BOARD_SIZE; col++) {
				BoardButton currentButton = board[row][col];
				
				if ( e.getSource() == currentButton && !currentButton.isClicked() )
				{
					currentButton.markButton(game.getCurrentPlayer());;
					game.makeMove( row, col );
					if ( game.isGameOver() ) { handleGameOver(); return; };
					moveAI();
					if ( game.isGameOver() ) { handleGameOver(); return; };
				}
			}
		}
	}	
	
	private void buttonsDisable() {
		for ( BoardButton[] rowOfButtons : board )
			for ( BoardButton button : rowOfButtons )
				button.setEnabled(false);
	}

	private void buttonsReset() {
		for ( BoardButton[] rowOfButtons : board )
			for ( BoardButton button : rowOfButtons )
				button.reset();
	}

	private void handleGameOver() {
		int responsePlayAgain;
		String messageWin = String.valueOf( game.getPreviousPlayer() ) + " Wins!";
		String messageDraw = "Draw!!";
		
		if ( game.isWin( game.getPreviousPlayer() ) )
			JOptionPane.showMessageDialog(null, messageWin);
		else if ( game.isDraw() )
			JOptionPane.showMessageDialog(null, messageDraw);

		buttonsDisable();
		responsePlayAgain = promptPlayAgain();
		frame.dispose();

		if ( responsePlayAgain != JOptionPane.YES_OPTION )
			return;		
		
		newGame();
	}
	
	private void moveAI() {
		if ( ai == null 
				|| game.getCurrentPlayer() != TTTEngine.PLAYER_2 
				|| game.getTurn() >= TTTEngine.TURN_GAMEOVER )
			return;

		int row, col;
		int[] moveCoordinate;
		double p = ( !isAIHardDifficulty ) ? .55 : 0;
		
		if ( Math.random() < p )
			moveCoordinate = ai.randomMove(game);
		else
			moveCoordinate = ai.getBestMove(game, 9-game.getTurn());	
		
		row = moveCoordinate[0];
		col = moveCoordinate[1];
		
		board[row][col].markButton(game.getCurrentPlayer());;
		game.makeMove( row, col );
	}
		
	private void newGame() {
		game = new TTTEngine();
		ai = null;
		isAIHardDifficulty = false;
		
		if ( board != null ) buttonsReset();
		
		userConfigureGame();
		setupBoard();
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
			else if ( responseSelectAIDifficulty == JOptionPane.NO_OPTION )
				isAIHardDifficulty = true;
			
			ai = new AI(TTTEngine.PLAYER_2, TTTEngine.PLAYER_1);
		}		
	}

	private int promptSelectAIDifficulty() {
		String message = "Select AI difficulty!";
		String title = "AI Mode";
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

	private void setupBoard() {
		JPanel boardPanel;
		frame = new JFrame();
		frame.setTitle(GAME_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize( new Dimension(GAME_SIZE, GAME_SIZE));
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout( new BorderLayout() );
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		boardPanel = new JPanel();
		boardPanel.setLayout( new GridLayout(TTTEngine.BOARD_SIZE,TTTEngine.BOARD_SIZE) );
		boardPanel.setBackground(Color.WHITE);
		
		board = new BoardButton[TTTEngine.BOARD_SIZE][TTTEngine.BOARD_SIZE];
		
		for (int row = 0; row < TTTEngine.BOARD_SIZE; row++)
			for (int column = 0; column < TTTEngine.BOARD_SIZE; column++) {
			board[row][column] = new BoardButton();
			board[row][column].addActionListener(this);
			boardPanel.add(board[row][column]);
		}
		
		board[0][0].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		board[0][1].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		board[0][2].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK) );
		board[1][0].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		board[1][1].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK) );
		board[1][2].setBorder( BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK) );
		board[2][0].setBorder( BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK) );
		board[2][1].setBorder( BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK) );
		board[2][2].setBorder( BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK) );
		frame.add(boardPanel);
	}
	
	class BoardButton extends JButton {
		private boolean isClicked;
		
		public BoardButton() {
			super();
			isClicked = false;
			this.setText( "" );
			this.setFont( new Font("Dialog", Font.PLAIN, 60) );
			this.setFocusable(false);
			this.setRolloverEnabled(false);
			this.setBackground(Color.WHITE);
			this.setHorizontalAlignment(JLabel.CENTER);
		}
		
		public boolean isClicked() {
			return isClicked; 
		}
		
		public void markButton(char player) {
			if ( player == TTTEngine.PLAYER_1 )
				this.setForeground(Color.RED);
			else				
				this.setForeground(Color.BLUE);

			this.setText( String.valueOf( player ) );
			setClicked(true);
		}
		
		public void reset() {
			this.setText("");
			this.setEnabled(true);
			isClicked = false;
		}
		
		public void setClicked(boolean bool) { 
			isClicked = bool; 
		}
	}
}
