package morph.engine.player.ai;

import morph.engine.board.Board;
import morph.engine.board.Move;
import morph.engine.player.MoveTransition;

public class MiniMax implements MoveStrategy{
	
	private final BoardEval boardEval;
	private final int searchDepth;
	private long executionTime;
	
	public MiniMax(final int searchDepth){
		this.boardEval = new StandardBoardEvaluator();
		this.searchDepth = searchDepth;
	}

	/**
	 * @return the executionTime
	 */
	@Override
	public Long getExecutionTime() {
		return executionTime;
	}


	@Override
	public String toString(){
		return "MiniMax";
	}
	
	@Override
	public Move execute(Board board) {
		
		final long startTime = System.currentTimeMillis();
		
		Move bestMove = null;
		
		int highestSeenVal = Integer.MIN_VALUE;
		int lowestSeenVal = Integer.MAX_VALUE;
		int curVal;
		
		System.out.println(board.currentPlayer() + " Thinking with depth = " + searchDepth);
		
		//int numMoves = board.currentPlayer().getLegalMoves().size();
		
		for(final Move move : board.currentPlayer().getLegalMoves()){
			
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
			if(moveTransition.getMoveStatus().isDone()){
				curVal = board.currentPlayer().getSide().isCPU() ?
						max(moveTransition.getToBoard(), searchDepth - 1) : 
						min(moveTransition.getToBoard(), searchDepth - 1);

						System.out.println(curVal + " " + move);
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

		this.executionTime = executionTime;
		
		System.out.println(bestMove.toString());
		return bestMove;
	}
	
	
	//helper methods
	
	public int min(final Board board, final int depth){
		if(depth == 0 || isEndGameScenario(board)){
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
	
	private boolean isEndGameScenario(Board board) {
		// TODO Auto-generated method stub
		return !(board.currentPlayer().isKingAlive()) ||
				board.currentPlayer().isStalemate();
	}

	public int max(final Board board, final int depth){
		if(depth == 0 || isEndGameScenario(board)){
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
