package morph.engine.player;

import java.util.Collection;
import java.util.List;

import morph.engine.Side;
import morph.engine.board.Board;
import morph.engine.board.Move;
import morph.engine.pieces.Piece;

public class HumanPlayer extends Player {

	public HumanPlayer(Board board, List<Move> legalMoves, final Collection<Move> opponentMoves) {
		super(board, legalMoves, opponentMoves);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getHumanPieces();
	}

	@Override
	public Side getSide() {
		// TODO Auto-generated method stub
		return Side.HUMAN;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.cpuPlayer();
	}

}
