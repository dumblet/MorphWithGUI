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
		
		PAWN(1,"P", 1) {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KNIGHT(3,"N" , 3) {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		BISHOP(3,"B" , 3) {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		ROOK(3,"R", 3) {
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KING(10000,"K",10000) {
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		
		
		private String pieceName;
		private int pieceValue;
		private int pieceValue2;
			
		PieceType(final int pieceValue, final String pieceName, final int pieceValue2) {
			this.pieceValue = pieceValue;
			this.pieceValue2 = pieceValue2;
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString(){
			return this.pieceName;
		}
		
		public abstract boolean isKing();

		public int getPieceValue() {
			return pieceValue;
		}

		public int getPieceValue2() {
			// TODO Auto-generated method stub
			return pieceValue2;
		}
	}

	public int getPieceValue() {
		return this.pieceType.getPieceValue();
	}
	
	public int getPieceValue2(){
		return this.pieceType.getPieceValue2();
	}
	
}
