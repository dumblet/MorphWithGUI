package morph;

import javax.swing.JOptionPane;

import morph.engine.board.Board;

public class Game {
	
	private Board board;

	public Game(){
		
		boolean cpuFirst;
		
		Object[] options = {"CPU", "Human"};
		int input = JOptionPane.showOptionDialog(null, "Who moves first?", "First Move", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if(input == JOptionPane.OK_OPTION)
		{
			cpuFirst = true;
		} else {
			cpuFirst = false;
		}
		setBoard(Board.createStandardBoard(cpuFirst));
	
	
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	
	
}
