package gui;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

import core.Move;
import core.TTTEngine;

public class GUIBoardButton  extends JButton {
	
	private boolean isClicked;
	private Move move;
	
	public GUIBoardButton(Move move) {
		super();
		this.move = move;
		initializeButton();
	}
	
	public Move getMove() {
		return move;
	}
	
	public boolean isClicked() {
		return isClicked; 
	}
	
	private void initializeButton() {
		isClicked = false;
		this.setText( "" );
		this.setFont( new Font("Dialog", Font.PLAIN, 60) );
		this.setFocusable(false);
		this.setRolloverEnabled(false);
		this.setBackground(Color.WHITE);
		this.setHorizontalAlignment(JLabel.CENTER);
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
