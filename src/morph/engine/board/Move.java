package morph.engine.board;

import morph.engine.board.Board.Builder;
import morph.engine.pieces.Piece;

public abstract class Move {
	
	
	final Board board;
	final Piece movedPiece;
	final int destinationCoord;
	
	public static final Move NULL_MOVE = new NullMove();
	
	private Move(final Board board,
			final Piece movedPiece,
			final int destinationCoord){
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoord = destinationCoord;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = prime * result + this.destinationCoord;
		result = prime * result + this.movedPiece.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(final Object other){
		if(this==other){
			return true;
		}
		if(!(other instanceof Move)){
			return false;
		}
		final Move otherMove = (Move) other;
		
		return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
			   getMovedPiece().equals(otherMove.getMovedPiece());
		
	}
	
	public String toString(){
		StringBuilder legalMove = new StringBuilder("    ");
		legalMove.setCharAt(0, (char) ((this.getCurrentCoord() % 6) + 'A'));
		legalMove.setCharAt(1, (char) (7 - ((this.getCurrentCoord() / 6)) + '1'));
		legalMove.setCharAt(2, (char) ((this.getDestinationCoordinate() % 6) + 'A'));
		legalMove.setCharAt(3, (char) (7 - ((this.getDestinationCoordinate() / 6)) + '1'));
		return legalMove.toString();
	}
	
	public String toStringReversed(){
		StringBuilder legalMove = new StringBuilder("    ");
		legalMove.setCharAt(0, (char) ('F' - (this.getCurrentCoord() % 6)));
		legalMove.setCharAt(1, (char) (((this.getCurrentCoord() / 6)) + '1'));
		legalMove.setCharAt(2, (char) ('F' - (this.getDestinationCoordinate() % 6)));
		legalMove.setCharAt(3, (char) (((this.getDestinationCoordinate() / 6)) + '1'));
		return legalMove.toString();
	}
	
	public boolean isAttack(){
		return false;
	}
	
	public Piece getAttackedPiece(){
		return null;
	}
	
	public int getCurrentCoord(){
		return this.getMovedPiece().getPiecePosition();
	}
	
	public int getDestinationCoordinate() {
		return this.destinationCoord;
	}
	
	public Piece getMovedPiece(){
		return movedPiece;
	}

	public Board execute() {
		final Board.Builder builder = new Builder();
		
		for(final Piece piece : this.board.currentPlayer().getActivePieces()){
			
			//TODO hashcode and equals for pieces
			if(!this.movedPiece.equals(piece)){
				builder.setPiece(piece);
			}
		}
		
		for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
			builder.setPiece(piece);	
		}
		//move the moved piece using setPiece 
		//CAN USE THIS FOR SWITCHING THE KNIGHT -> ROOK -> BISHOP **DONE**
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getSide());
		return builder.build();
	}




	public static final class PeacefulMove extends Move {

		public PeacefulMove(final Board board, final Piece movedPiece, final int destinationCoord) {
			super(board, movedPiece, destinationCoord);
		}
	}
	
	public static final class EatMove extends Move{
		
		final Piece enemyPiece;

		public EatMove(final Board board, final Piece movedPiece, final int destinationCoord, final Piece enemyPiece) {
			super(board, movedPiece, destinationCoord);
			this.enemyPiece = enemyPiece;
		}		
		
		@Override
		public int hashCode(){
			return this.getAttackedPiece().hashCode() + super.hashCode();
		}
		
		@Override
		public boolean equals(final Object other){
			if(this==other){
				return true;
			}
			if(!(other instanceof EatMove)){
				return false;
			}
			final EatMove otherEatMove = (EatMove) other;
			
			return super.equals(otherEatMove) && getAttackedPiece().equals(otherEatMove.getAttackedPiece());
			
		}
		
		@Override
		public boolean isAttack(){
			return true;
		}
		
		@Override
		public Piece getAttackedPiece(){
			return this.getAttackedPiece();
		}
	}
	
	public static final class NullMove extends Move{

		public NullMove() {
			super(null, null, -1);
		}		
		
		@Override
		public Board execute(){
			throw new RuntimeException("null move is being executed");
		}
	}
	
	public static class MoveGenerator {
		private MoveGenerator(){
			throw new RuntimeException("Not instantiable");
		}

		public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate){
			for(final Move move : board.currentPlayer().getLegalMoves()) {
				if(move.getCurrentCoord() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate){
					return move;
				}
			}
			return NULL_MOVE;
		}
	
	}


	
}
