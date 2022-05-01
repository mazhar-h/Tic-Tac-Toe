import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIBoard {
	
	private static final int BOARD_SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*.2);
	private JFrame frame;
	private GUIBoardButton[][] board;
	
	public GUIBoard(ActionListener listener) {
		createBoard();
		addBoardButtons(listener);
	}
		
	private void addBoardButtons(ActionListener listener) {
		JPanel boardPanel = new JPanel();
		boardPanel.setLayout( new GridLayout(TTTEngine.BOARD_SIZE,TTTEngine.BOARD_SIZE) );
		boardPanel.setBackground(Color.WHITE);
		
		board = new GUIBoardButton[TTTEngine.BOARD_SIZE][TTTEngine.BOARD_SIZE];
		
		for (int row = 0; row < TTTEngine.BOARD_SIZE; row++)
			for (int column = 0; column < TTTEngine.BOARD_SIZE; column++) {
				GUIBoardButton button = new GUIBoardButton(row, column);
				board[row][column] = button;
				button.addActionListener(listener);
				boardPanel.add(button);
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
	
	public void buttonsDisable() {
		for ( GUIBoardButton[] rowOfButtons : board )
			for ( GUIBoardButton button : rowOfButtons )
				button.setEnabled(false);
	}

	public void buttonsReset() {
		for ( GUIBoardButton[] rowOfButtons : board )
			for ( GUIBoardButton button : rowOfButtons )
				button.reset();
	}
	
	public void close() {
		frame.dispose();
	}
	
	private void createBoard() {
		frame = new JFrame();
		frame.setTitle(TTTEngine.GAME_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize( new Dimension(BOARD_SIZE, BOARD_SIZE));
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout( new BorderLayout() );
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void setButton(int row, int col, char player) {
		board[row][col].markButton(player);
	}
}
