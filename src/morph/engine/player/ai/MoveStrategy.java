package morph.engine.player.ai;

import morph.engine.board.Board;
import morph.engine.board.Move;

public interface MoveStrategy {

	Move execute(Board board);
}
