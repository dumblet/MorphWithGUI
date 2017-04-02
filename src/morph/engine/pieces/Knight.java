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

public class Knight extends Piece {

	private final static int[] CANDIDATE_MOVE_COORD = { -13, -11, -8, -4 , 4, 8, 11, 13};

	public Knight(int piecePosition, Side pieceSide) {
		super(PieceType.KNIGHT, piecePosition, pieceSide);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		int candidateDestinationCoord;
		final List<Move> legalMoves = new ArrayList<>();

		for(final int currentCandidateOffset : CANDIDATE_MOVE_COORD){
			candidateDestinationCoord = this.piecePosition + (currentCandidateOffset * this.getPieceSide().getDirection());

			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoord) && BoardUtils.noMoveError(this.piecePosition, (currentCandidateOffset * this.getPieceSide().getDirection()))){		

				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
				if(candidateDestinationTile.isTileOccupied()){

					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Side pieceSide = pieceAtDestination.getPieceSide();

					if(this.pieceSide != pieceSide){
						legalMoves.add(new Move.EatMove(board, this, candidateDestinationCoord, pieceAtDestination));
						//capture
					}
				}else if(!candidateDestinationTile.isTileOccupied() && currentCandidateOffset < 0){
					legalMoves.add(new Move.PeacefulMove(board, this, candidateDestinationCoord));
				} 
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}

	
	@Override
	public String toString(){
		return Piece.PieceType.KNIGHT.toString();
	}
	@Override
	public Rook movePiece(final Move move) {
		return new Rook(move.getDestinationCoordinate(), move.getMovedPiece().getPieceSide());
	}

}
