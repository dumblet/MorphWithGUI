package morph.engine.player.ai;

import morph.engine.board.Board;
import morph.engine.board.Move;
import morph.engine.player.MoveTransition;

public class MiniMax implements MoveStrategy{
	
	private final BoardEval boardEval;
	
	public MiniMax(){
		this.boardEval = null;
	}

	@Override
	public String toString(){
		return "MiniMax";
	}
	
	@Override
	public Move execute(Board board, int depth) {
		
		final long startTime = System.currentTimeMillis();
		
		Move bestMove = null;
		
		int highestSeenVal = Integer.MIN_VALUE;
		int lowestSeenVal = Integer.MAX_VALUE;
		int curVal;
		
		System.out.println(board.currentPlayer() + " Thinking with depth = " + depth);
		
		int numMoves = board.currentPlayer().getLegalMoves().size();
		
		for(final Move move : board.currentPlayer().getLegalMoves()){
			
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
			if(moveTransition.getMoveStatus().isDone()){
				curVal = board.currentPlayer().getSide().isCPU() ?
						max(moveTransition.getToBoard(), depth - 1) : 
						min(moveTransition.getToBoard(), depth - 1);
				if(board.currentPlayer().getSide().isCPU() && curVal <= lowestSeenVal){
					lowestSeenVal = curVal;
					bestMove = move;
				}if(board.currentPlayer().getSide().isHuman() && curVal >= highestSeenVal){
					highestSeenVal = curVal;
					bestMove = move;
				}
						
				
			}
		}
		
		final long executionTime = System.currentTimeMillis() - startTime;
		
		return bestMove;
	}
	
	
	//helper methods
	
	public int min(final Board board, final int depth){
		if(depth == 0 /* or game over*/){
			return this.boardEval.evaluate(board, depth);
		}
		
		int lowestSeenVal = Integer.MAX_VALUE;
		for(final Move move : board.currentPlayer().getLegalMoves()){
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
			if(moveTransition.getMoveStatus().isDone()){
				final int curVal = max(moveTransition.getToBoard(), depth-1);
				if(curVal <= lowestSeenVal) {
					lowestSeenVal = curVal;
				}
			}
		}
		return lowestSeenVal;
	}
	
	public int max(final Board board, final int depth){
		if(depth == 0 /* or game over*/){
			return this.boardEval.evaluate(board, depth);
		}
		
		int highestSeenVal = Integer.MIN_VALUE;
		for(final Move move : board.currentPlayer().getLegalMoves()){
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
			if(moveTransition.getMoveStatus().isDone()){
				final int curVal = min(moveTransition.getToBoard(), depth-1);
				if(curVal >= highestSeenVal) {
					highestSeenVal = curVal;
				}
			}
		}
		return highestSeenVal;
		
	}
	
}
