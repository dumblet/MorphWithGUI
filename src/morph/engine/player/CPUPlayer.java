package morph.engine.player;

import java.util.Collection;

import morph.engine.Side;
import morph.engine.board.Board;
import morph.engine.board.Move;
import morph.engine.pieces.Piece;

public class CPUPlayer extends Player {

	public CPUPlayer(Board board, Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
		super(board, legalMoves, opponentMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getCPUPieces();
	}

	@Override
	public Side getSide() {
		return Side.CPU;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.humanPlayer();
	}

}
