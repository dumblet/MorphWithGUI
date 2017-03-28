package morph.engine.player;

import morph.engine.board.Board;
import morph.engine.board.Move;

public class MoveTransition {

	private final Board fromBoard;
	private final Board toBoard;
	private final Move move;
	private final MoveStatus moveStatus;
	
	public MoveTransition(final Board fromBoard, final Board toBoard, final Move move, final MoveStatus moveStatus){
		this.fromBoard = fromBoard;
		this.toBoard = toBoard;
		this.move = move;
		this.moveStatus = moveStatus;
	}

	
	public MoveStatus getMoveStatus() {
		// TODO Auto-generated method stub
		return moveStatus;
	}

	public Board getToBoard() {
		return toBoard;
	}


	public Board getFromBoard() {
		return fromBoard;
	}
	
}
