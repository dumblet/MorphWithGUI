package morph.engine.player.ai;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

import morph.engine.Side;
import morph.engine.board.Board;
import morph.engine.board.BoardUtils;
import morph.engine.board.Move;
import morph.engine.player.MoveTransition;
import morph.engine.player.Player;
//import com.google.common.collect.ComparisonChain;
//import com.google.common.collect.Ordering;

public class AlphaBetaWithMoveOrdering implements MoveStrategy {

	

	@Override
	public Long getExecutionTime() {
		return executionTime;
	}

    private final BoardEval evaluator;
    private long executionTime;
    private int cutOffsProduced;
    private int depth;
	private int boardsEvaluated;

    

    public AlphaBetaWithMoveOrdering(final int depth) {
        this.evaluator = new StandardBoardEvaluator();
        this.depth = depth;
        this.cutOffsProduced = 0;
    }

    @Override
    public String toString() {
        return "AB+MO";
    }
    
	private boolean isEndGameScenario(Board board) {
		// TODO Auto-generated method stub
		return !(board.currentPlayer().isKingAlive()) ||
				board.currentPlayer().isStalemate();
	}

    @Override
    public Move execute(final Board board) {
        final long startTime = System.currentTimeMillis();
        final Player currentPlayer = board.currentPlayer();
        final Side side = currentPlayer.getSide();
        List<Move> moveList = new ArrayList<Move>(board.currentPlayer().getLegalMoves());
        moveList.sort(Comparator.comparing(Move::isAttack));
        Move bestMove = Move.NULL_MOVE;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        System.out.println(board.currentPlayer() + " THINKING with depth = " + depth);
        System.out.println("\tOrdered moves! : " + moveList);
        for (final Move move : moveList){/*this.moveSorter.sort(board.currentPlayer().getLegalMoves())*/ 
            final MoveTransition moveTransition = board.currentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currentValue = side.isHuman() ?
                        min(moveTransition.getToBoard(), depth - 1, highestSeenValue, lowestSeenValue) :
                        max(moveTransition.getToBoard(), depth - 1, highestSeenValue, lowestSeenValue);
                if (side.isHuman() && currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                    //setChanged();
                    //notifyObservers(bestMove);
                }
                else if (side.isCPU() && currentValue < lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                    //setChanged();
                    //notifyObservers(bestMove);
                }
            } 
        }
        this.executionTime = System.currentTimeMillis() - startTime;
        System.out.println(cutOffsProduced + "  cut offs");
        System.out.println(boardsEvaluated + " boards evaluated");
        System.out.println(executionTime + " execution time");
        return bestMove;
    }

    public int max(final Board board,
                   final int depth,
                   final int highest,
                   final int lowest) {
        if (depth == 0 || isEndGameScenario(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        List<Move> moveList = new ArrayList<Move>(board.currentPlayer().getLegalMoves());
        //moveList.sort(Comparator.comparing(Move::isAttack));
        int currentHighest = highest;
        for (final Move move : moveList) {
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
        if (depth == 0 || isEndGameScenario(board)) {
            this.boardsEvaluated++;
            return this.evaluator.evaluate(board, depth);
        }
        List<Move> moveList = new ArrayList<Move>(board.currentPlayer().getLegalMoves());
        //moveList.sort(Comparator.comparing(Move::isAttack));
        int currentLowest = lowest;
        for (final Move move : moveList) {
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



}