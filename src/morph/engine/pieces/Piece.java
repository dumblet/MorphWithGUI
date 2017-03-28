package morph.engine.pieces;

import java.util.Collection;

import morph.engine.Side;
import morph.engine.board.Board;
import morph.engine.board.Move;

public abstract class Piece {
	
	final int piecePosition;
	final Side pieceSide;
	protected final PieceType pieceType;
	private final int cachedHashCode;
	
	public Piece(final PieceType pieceType, final int piecePosition, final Side pieceSide) {
		this.pieceSide = pieceSide;
		this.piecePosition = piecePosition;
		this.pieceType = pieceType;
		this.cachedHashCode = computeHashCode();
	}
	
	private int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31 * result + pieceSide.hashCode();
		result = 31 * result + piecePosition;
		return result;
	}

	public Side getPieceSide(){
		return this.pieceSide;
	}
	
	public int getPiecePosition(){
		return this.piecePosition;
	}
	
	public PieceType getPieceType(){
		return this.pieceType;
	}
	
	@Override
	public boolean equals(final Object other){
		if(this == other){
			return true;
		}
		if(!(other instanceof Piece)){
			return false;
		}
		final Piece otherPiece = (Piece) other;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() && pieceSide == otherPiece.getPieceSide();
	}
	
	@Override
	public int hashCode() {
		return this.cachedHashCode;
	}
	
	public abstract Piece movePiece(Move move);
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	
	public enum PieceType {
		
		PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		
		
		private String pieceName;
			
		PieceType(final String pieceName) {
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString(){
			return this.pieceName;
		}
		
		public abstract boolean isKing();
	}
	
}
