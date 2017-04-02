package morph.engine.player.ai;

import morph.engine.board.Board;
import morph.engine.pieces.Piece;
import morph.engine.player.Player;

public final class NotStandardBoardEvaluator implements BoardEval {


	@Override
	public int evaluate(Board board, int depth) {
		return scorePlayer(board, board.humanPlayer(), depth) - scorePlayer(board, board.cpuPlayer(), depth);
	}

	private int scorePlayer(Board board, Player player, int depth) {
		// TODO Auto-generated method stub
		return pieceVal(player);
	}

	private static int mobility(Player player) {
		return player.getLegalMoves().size();
	}

	private static int pieceVal(Player player) {
		int pieceValueScore = 0;
		if(player.getSide().isHuman()){
			for(final Piece piece : player.getActivePieces()){
				pieceValueScore += piece.getPieceValue();
			}
		} else {
			for(final Piece piece : player.getActivePieces()){
				pieceValueScore += piece.getPieceValue2();
			}
		}
		return pieceValueScore;
	}
	private static int kingMove(Player player){
		if(player.getPlayerKing() == null){
			return 0;
		}
		if(player.getSide().toString() == "CPU"){
			return 1 - player.getPlayerKing().getPiecePosition();
		} else{
			return player.getPlayerKing().getPiecePosition() - 46;
		}
	}

}
