package morph.engine.player.ai;

import morph.engine.board.Board;

public interface BoardEval {

	int evaluate(Board board, int depth);
}
