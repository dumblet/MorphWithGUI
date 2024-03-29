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

public class Bishop extends Piece{

	private final static int[] CANDIDATE_MOVE_VECTOR_COORD = {-7, -5, 5, 7};
	
	public Bishop(int piecePosition, Side pieceSide) {
		super(PieceType.BISHOP, piecePosition, pieceSide);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();

		for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORD){
			
			int candidateDestinationCoordinate = this.piecePosition;
			
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate) && BoardUtils.noMoveError(candidateDestinationCoordinate, (candidateCoordinateOffset * this.getPieceSide().getDirection()))){
				
				candidateDestinationCoordinate += (candidateCoordinateOffset * this.getPieceSide().getDirection());
				
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){		

					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

					if(candidateDestinationTile.isTileOccupied()){

						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Side pieceSide = pieceAtDestination.getPieceSide();

						if(this.pieceSide != pieceSide){
							legalMoves.add(new Move.EatMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
							//capture
						}

						break;
					} else if(!candidateDestinationTile.isTileOccupied() && candidateCoordinateOffset < 0){
						legalMoves.add(new Move.PeacefulMove(board, this, candidateDestinationCoordinate));
					} else if(!candidateDestinationTile.isTileOccupied() && candidateCoordinateOffset > 0){
						continue;
					}
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString(){
		return Piece.PieceType.BISHOP.toString();
	}

	@Override
	public Knight movePiece(final Move move) {
		return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceSide());
	}

}
