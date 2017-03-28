package morph;

import java.util.Scanner;

import morph.GUI.Table;
import morph.engine.board.Board;

public class Morph {
	
	public static void main(String[] args) {
		
		
		
		boolean cpuMovesFirst;

		Scanner scanner = new Scanner(System.in);
		System.out.println("CPU moves first? (y/n) \n(note: any other input other than y will be defaulted to n)");
		String input = scanner.nextLine();
		if(input == "y"){
			cpuMovesFirst = true;
		} else {
			cpuMovesFirst = false;
		}
		Board board = Board.createStandardBoard(cpuMovesFirst);
		
		System.out.println(board);
		System.out.println(board.printLegalMoves(board.humanPlayer()));
		System.out.println(board.printLegalMoves(board.cpuPlayer()));
		
		Table table = new Table();
		
		
	}
	
	
	private int[] translateInput(String input){
	    int columnCalcFrom = input.charAt(0) - 'a';
	    int rowCalcFrom = (7 - (input.charAt(1) - '1')) *6;
	    int columnCalcTo = input.charAt(2) - 'a';
	    int rowCalcTo = (7 - (input.charAt(3) - '1')) *6;
	    
	    int[] x = new int[2];
	    x[0] = columnCalcFrom + rowCalcFrom;
	    x[1] = columnCalcTo + rowCalcTo;
	    
	    return x;
	}

}
