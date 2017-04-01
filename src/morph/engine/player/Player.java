package morph.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


import morph.engine.Side;
import morph.engine.board.Board;
import morph.engine.board.Move;
import morph.engine.pieces.King;
import morph.engine.pieces.Piece;

public abstract class Player {
	
	protected final Board board;
	protected final King playerKing;
	protected final List<Move> legalMoves;
	protected final Collection<Move> opponentMoves;
	
	Player(final Board board,
			final List<Move> legalMoves,
			final Collection<Move> opponentMoves){
		
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = legalMoves;
		this.opponentMoves = opponentMoves;
	}
	
	public King getPlayerKing(){
		return playerKing;
	}
	
	public List<Move> getLegalMoves(){
		return legalMoves;
	}
	
	private static List<Move> calculateAttacksOnTile(int piecePosition, List<Move> moves){
		final List<Move> attackMoves = new ArrayList<>();
		for(final Move move : moves) {
			if(piecePosition == move.getDestinationCoordinate()){
				attackMoves.add(move);
			}
		}
		return Collections.unmodifiableList(attackMoves);
	}
	

	private King establishKing() {
		for(final Piece piece : getActivePieces()){
			if(piece.getPieceType().isKing()){
				return (King) piece;
			}
		}
		return null;
	}
	
	public boolean isMoveLegal(final Move move){
		return this.legalMoves.contains(move);
	}
	
	//ALSO A LOSE
	public boolean isStalemate(){
		return !hasEscapeMoves();
	}
	
	protected boolean hasEscapeMoves(){
		for(final Move move : this.legalMoves){
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
			
		}
		return false;
	}
	
	public MoveTransition makeMove(final Move move){
		
		if(!isMoveLegal(move)){
			return new MoveTransition(this.board, this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		final Board transitionBoard = move.execute();
		
		return new MoveTransition(this.board, transitionBoard, move, MoveStatus.DONE);
	}


	public abstract Collection<Piece> getActivePieces();
	public abstract Side getSide();
	public abstract Player getOpponent();

	public boolean isKingAlive() {
		return !(playerKing == null);
	}

}
