package morph.engine.player.ai;

import morph.engine.board.Board;
import morph.engine.pieces.Piece;
import morph.engine.player.Player;

public final class StandardBoardEvaluator implements BoardEval {

	private static final int I_WIN_BONUS = 10000;
	private static final int DEPTH_BONUS = 100;

	@Override
	public int evaluate(Board board, int depth) {
		return scorePlayer(board, board.humanPlayer(), depth) - scorePlayer(board, board.cpuPlayer(), depth);
	}

	private int scorePlayer(Board board, Player player, int depth) {
		// TODO Auto-generated method stub
		return pieceVal(player) + mobility(player); //+ killEnemyKing(player, depth);
	}

	private static int killEnemyKing(Player player, int depth) {
		return !(player.getOpponent().isKingAlive()) ? I_WIN_BONUS * depthBonus(depth) : 0;
	}

	private static int depthBonus(int depth) {
		// TODO Auto-generated method stub
		return depth == 0 ? 1 : DEPTH_BONUS * depth;
	}

	private static int mobility(Player player) {
		return player.getLegalMoves().size();
	}

	private static int pieceVal(Player player) {
		int pieceValueScore = 0;
		for(final Piece piece : player.getActivePieces()){
			pieceValueScore += piece.getPieceValue();
		}
		return pieceValueScore;
	}

}
