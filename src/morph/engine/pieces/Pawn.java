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

public class Pawn extends Piece{

	private final static int[] CANDIDATE_MOVE_VECTOR_COORD = {5, 6, 7};


	public Pawn(int piecePosition, Side pieceSide) {
		super(PieceType.PAWN, piecePosition, pieceSide);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORD){

			int candidateDestinationCoordinate = this.piecePosition + (candidateCoordinateOffset * this.getPieceSide().getDirection() *-1);

			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate) && BoardUtils.noMoveError(candidateDestinationCoordinate, candidateCoordinateOffset)){		

				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

				if(!candidateDestinationTile.isTileOccupied() && candidateCoordinateOffset == 6){
					legalMoves.add(new Move.PeacefulMove(board, this, candidateDestinationCoordinate));
				} else if(candidateDestinationTile.isTileOccupied() && candidateCoordinateOffset != 6){

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
		return Piece.PieceType.PAWN.toString();
	}
	@Override
	public Pawn movePiece(final Move move) {
		return new Pawn(move.getDestinationCoordinate(), move.getMovedPiece().getPieceSide());
	}
}
