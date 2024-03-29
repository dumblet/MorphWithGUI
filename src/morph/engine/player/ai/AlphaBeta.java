package morph.engine.player.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import morph.engine.Side;
import morph.engine.board.Board;
import morph.engine.board.Move;
import morph.engine.player.MoveTransition;
import morph.engine.player.Player;

public class AlphaBeta implements MoveStrategy {



	@Override
	public Long getExecutionTime() {
		return executionTime;
	}

	private final BoardEval evaluator;
	private long executionTime;
	private int cutOffsProduced;
	private int boardsEvaluated;
	private long startTime;
	private long timer;
	private int depth;



	public AlphaBeta(final int depth) {
		this.evaluator = new StandardBoardEvaluator();
		this.cutOffsProduced = 0;
	}

	@Override
	public String toString() {
		return "AB+MO";
	}

	public int pliesSearched(){
		return depth;
	}

	private boolean isEndGameScenario(Board board) {
		// TODO Auto-generated method stub
		return !(board.currentPlayer().isKingAlive()) ||
				board.currentPlayer().isStalemate();
	}

	@Override
	public Move execute(final Board board) {
		startTime = System.currentTimeMillis();
		final Player currentPlayer = board.currentPlayer();
		final Side side = currentPlayer.getSide();

		Move previousBestMove = Move.NULL_MOVE;
		int currentDepth = 1;
		timer = System.currentTimeMillis() - startTime;
		while(timer < 5000){
			Move bestMove = Move.NULL_MOVE;
			int highestSeenValue = Integer.MIN_VALUE;
			int lowestSeenValue = Integer.MAX_VALUE;
			int currentValue = 0;
			System.out.println(board.currentPlayer() + " THINKING with depth = " + currentDepth);
			for (final Move move : moveSort(board, currentDepth)){/*this.moveSorter.sort(board.currentPlayer().getLegalMoves())*/ 
				final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
				if (moveTransition.getMoveStatus().isDone()) {
					currentValue = side.isHuman() ?
							min(moveTransition.getToBoard(), currentDepth - 1, highestSeenValue, lowestSeenValue) :
								max(moveTransition.getToBoard(), currentDepth - 1, highestSeenValue, lowestSeenValue);
							if (side.isHuman() && currentValue > highestSeenValue) {

								highestSeenValue = currentValue;
								bestMove = move;

								//setChanged();
								//notifyObservers(bestMove);
							}
							else if (side.isCPU() && currentValue < lowestSeenValue) {
								lowestSeenValue = currentValue;
								bestMove = move;

								System.out.println(lowestSeenValue);

								System.out.println(currentValue);
								//System.out.println(bestMove);
								//setChanged();
								//notifyObservers(bestMove);
							}
				} 
				
			}
			currentDepth++;
			timer = System.currentTimeMillis() - startTime;
			if(timer < 5000){
				previousBestMove = bestMove;
				
			}
			System.out.println(currentValue);
		}
		this.executionTime = System.currentTimeMillis() - startTime;
		System.out.println(cutOffsProduced + "  cut offs");
		System.out.println(boardsEvaluated + " boards evaluated");
		System.out.println(executionTime + " execution time");
		System.out.println("CHOSEN MOVE IS " + previousBestMove);
		this.depth = currentDepth-2;
		return previousBestMove;
	}

	public int max(final Board board,
			final int depth,
			final int highest,
			final int lowest) {
		if (depth == 0) {
			this.boardsEvaluated++;
			return this.evaluator.evaluate(board, depth);
		}
		if(isEndGameScenario(board)){
			this.boardsEvaluated++;
			return this.evaluator.evaluate(board, depth) - depth*100;
		}

		timer = System.currentTimeMillis() - startTime;
		if(timer > 5000){
			return Integer.MIN_VALUE;
		}
		int currentHighest = highest;
		for (final Move move : moveSort(board, depth)) {
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
			if (moveTransition.getMoveStatus().isDone()) {
				currentHighest = Math.max(currentHighest, min(moveTransition.getToBoard(),depth - 1, currentHighest, lowest));
				if (lowest <= currentHighest) {
					this.cutOffsProduced++;
					break;
				}
			}
		}
		return currentHighest;
	}

	public int min(final Board board,
			final int depth,
			final int highest,
			final int lowest) {
		if (depth == 0) {
			this.boardsEvaluated++;
			return this.evaluator.evaluate(board, depth);
		}
		if(isEndGameScenario(board)){
			this.boardsEvaluated++;
			return this.evaluator.evaluate(board, depth) + depth*100;
		}

		timer = System.currentTimeMillis() - startTime;
		if(timer > 5000){
			return Integer.MAX_VALUE;
		}
		int currentLowest = lowest;
		for (final Move move : moveSort(board, depth)) {
			final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
			if (moveTransition.getMoveStatus().isDone()) {
				currentLowest = Math.min(currentLowest, max(moveTransition.getToBoard(),depth-1, highest, currentLowest));
				if (currentLowest <= highest) {
					this.cutOffsProduced++;
					break;
				}
			}
		}
		return currentLowest;
	}

	public List<Move> moveSort(Board board, int depth){

		List<Move> sortedList = new ArrayList<Move>(board.currentPlayer().getLegalMoves());

		List<Move> newList = new ArrayList<Move>();
		if(depth > 1){
			List<Integer> score = new ArrayList<Integer>();
			
			for(int i = 0; i < sortedList.size(); i++){
				if(sortedList.get(i).isAttack()){
					score.add(i, sortedList.get(i).pieceValueDifference());
				}else{
					score.add(i, Integer.MIN_VALUE);
				}
			}

			
			for(int i = 0; i<(Math.min(6, sortedList.size())); i++){
				int max= Integer.MIN_VALUE, maxLoc = 0;
				for(int j=0; j>sortedList.size();j++){
					if(score.get(j)>max){
						max = score.get(j);
						maxLoc = j;
					}
				}
				score.remove(maxLoc);
				newList.add(sortedList.get(maxLoc));
				sortedList.remove(maxLoc);
			}
//			sortedList.sort((m1, m2) -> {
//				if(m1.isAttack() == m2.isAttack() && m1.isAttack() == true){
//					return m2.getMovedPiece().getPieceValue() - m1.getMovedPiece().getPieceValue();
//				} else {
//					return Boolean.compare(m1.isAttack(), m2.isAttack());
//				}
//			});
		}
		newList.addAll(sortedList);
		return newList;
	}



}