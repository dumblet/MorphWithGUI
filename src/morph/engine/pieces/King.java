package morph.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import morph.engine.Side;
import morph.engine.board.Board;
import morph.engine.board.BoardUtils;
import morph.engine.board.Move;
import morph.engine.board.Tile;

public class King extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORD = {-1,1};


	public King(int piecePosition, Side pieceSide) {
		super(PieceType.KING, piecePosition, pieceSide);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORD){

			int candidateDestinationCoordinate = this.piecePosition + (candidateCoordinateOffset * this.getPieceSide().getDirection());

			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate) && BoardUtils.noMoveError(candidateDestinationCoordinate, (candidateCoordinateOffset * this.getPieceSide().getDirection() * -1))){		

				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

				if(candidateCoordinateOffset == -1){
					if(!candidateDestinationTile.isTileOccupied()){
					legalMoves.add(new Move.PeacefulMove(board, this, candidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Side pieceSide = pieceAtDestination.getPieceSide();

						if(this.pieceSide != pieceSide){
							legalMoves.add(new Move.EatMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
							//capture

						}
					}
				}
				
				if(candidateDestinationTile.isTileOccupied() && candidateCoordinateOffset == 1){

					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Side pieceSide = pieceAtDestination.getPieceSide();

					if(this.pieceSide != pieceSide){
						legalMoves.add(new Move.EatMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						//capture

					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString(){
		return Piece.PieceType.KING.toString();
	}

	@Override
	public Piece movePiece(final Move move) {
		return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceSide());
	}

}
