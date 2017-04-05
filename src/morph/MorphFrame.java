package morph;

import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import morph.engine.board.Move;
import morph.engine.player.MoveTransition;
import morph.engine.player.ai.AlphaBeta;
import morph.engine.player.ai.AlphaBetaNoMobilityCheck;
import morph.engine.player.ai.AlphaBetaRealMoveOrdering;
import morph.engine.player.ai.AlphaBetaWithMoveOrdering;
import morph.engine.player.ai.MiniMax;
import morph.engine.player.ai.MoveStrategy;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollBar;

public class MorphFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField moveInput;
	private JTable printBoardTable;
	private JTable tableLabel;
	private JTable table;
	private JLabel lblStatus;
	private JLabel lblTranslatedMove;
	private JLabel lblSearchDepth ;
	private DefaultListModel<String> modelListLegalMoves;
	private DefaultListModel<String> modelListMoveHistory;
	private static Game game;
	private static boolean gameStart;
	private static MorphFrame morphFrame = new MorphFrame();
	private static ArrayList<Long> executionTimeList = new ArrayList<>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MorphFrame frame = new MorphFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 */
		morphFrame.setVisible(true);
		gameStart = true;





	}

	/**
	 * Create the frame.
	 */
	private MorphFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Dumblet");

		moveInput = new JTextField();
		moveInput.setBounds(485, 498, 89, 22);
		contentPane.add(moveInput);
		moveInput.setColumns(10);


		printBoardTable = new JTable();
		printBoardTable.setRowSelectionAllowed(false);
		printBoardTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
					{null, null, null, null, null, null},
				},
				new String[] {
						"New column", "New column", "New column", "New column", "New column", "New column"
				}
				));

		printBoardTable.getColumnModel().getColumn(0).setResizable(false);
		printBoardTable.setBounds(48, 11, 152, 128);
		contentPane.add(printBoardTable);

		tableLabel = new JTable();
		tableLabel.setModel(new DefaultTableModel(
				new Object[][] {
					{new Integer(8)},
					{new Integer(7)},
					{new Integer(6)},
					{new Integer(5)},
					{new Integer(4)},
					{new Integer(3)},
					{new Integer(2)},
					{new Integer(1)},
				},
				new String[] {
						"New column"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
					Integer.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableLabel.setBounds(20, 11, 18, 128);
		contentPane.add(tableLabel);

		table = new JTable();
		table.setModel(new DefaultTableModel(
				new Object[][] {
					{"A", "B", "C", "D", "E", "F"},
				},
				new String[] {
						"New column", "New column", "New column", "New column", "New column", "New column"
				}
				) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
					String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.setBounds(48, 144, 152, 16);
		contentPane.add(table);

		JButton btnMove = new JButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doMove(moveInput.getText().toUpperCase());
				moveInput.setText("");
			}
		});
		btnMove.setBounds(485, 531, 89, 23);
		contentPane.add(btnMove);

		JLabel lblMoveList = new JLabel("Move List");
		lblMoveList.setBounds(422, 11, 71, 14);
		contentPane.add(lblMoveList);




		modelListLegalMoves = new DefaultListModel<String>();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(422, 33, 152, 454);
		contentPane.add(scrollPane);
		JList<String> listLegalMove = new JList<String>(modelListLegalMoves);
		scrollPane.setViewportView(listLegalMove);
		listLegalMove.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				moveInput.setText(listLegalMove.getSelectedValue());
			}
		});
		listLegalMove.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		modelListMoveHistory = new DefaultListModel<String>();

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(260, 33, 123, 454);
		contentPane.add(scrollPane_1);
		JList listMoveHistory = new JList(modelListMoveHistory);
		scrollPane_1.setViewportView(listMoveHistory);
		JScrollPane scrollListMoveHistory = new JScrollPane();
		//scrollListMoveHistory.setViewportView(listMoveHistory);

		JScrollPane scrollListLegalMoves = new JScrollPane();
		//scrollListLegalMoves.setViewportView(listLegalMove);

		JLabel lblMoveHistory = new JLabel("Move History");
		lblMoveHistory.setBounds(260, 11, 71, 14);
		contentPane.add(lblMoveHistory);
		
		lblStatus = new JLabel("AI status : ");
		lblStatus.setBounds(10, 199, 152, 34);
		contentPane.add(lblStatus);
		
		lblTranslatedMove = new JLabel("AI's Translated move : ");
		lblTranslatedMove.setBounds(10, 244, 152, 34);
		contentPane.add(lblTranslatedMove);

		this.getRootPane().setDefaultButton(btnMove);
		
		lblSearchDepth = new JLabel("Search Depth");
		lblSearchDepth.setBounds(10, 298, 240, 34);
		contentPane.add(lblSearchDepth);


		




		// GAME START //
		if(gameStart = true){
			game = new Game();
			update();
		}
		EventQueue.invokeLater(new Runnable() {

			   @Override
			     public void run() {
			         moveInput.grabFocus();
			         moveInput.requestFocus();//or inWindow
			     }
			});
		

	}

	private void doMove(String input){
		if(game.getBoard().printLegalMoves(game.getBoard().currentPlayer()).contains(input)){
			int translatedInput[] = translateInput(input);
			final Move move = Move.MoveGenerator.createMove(game.getBoard(), translatedInput[0], translatedInput[1]);
			final MoveTransition transition = game.getBoard().currentPlayer().makeMove(move);
			if(transition.getMoveStatus().isDone()){
				game.setBoard(transition.getToBoard());
			}
			modelListMoveHistory.add(0, input);
			update();
		} else if(!input.matches("[A-F][1-8][A-F][1-8]")){
			JOptionPane.showMessageDialog(null,
					"Wrong format, try again");
		} else {
			JOptionPane.showMessageDialog(null,
					"Not in legal moves list");
		}
	}
	private void doComputerMove(Move move){

		final MoveTransition transition = game.getBoard().currentPlayer().makeMove(move);
		if(transition.getMoveStatus().isDone()){
			game.setBoard(transition.getToBoard());
		}
		modelListMoveHistory.add(0, move.toString());
		update();
	}

	private int[] translateInput(String input){
		int columnCalcFrom = input.charAt(0) - 'A';
		int rowCalcFrom = (7 - (input.charAt(1) - '1')) *6;
		int columnCalcTo = input.charAt(2) - 'A';
		int rowCalcTo = (7 - (input.charAt(3) - '1')) *6;

		int[] x = new int[2];
		x[0] = columnCalcFrom + rowCalcFrom;
		x[1] = columnCalcTo + rowCalcTo;

		return x;
	}

	public void update(){
		lblTranslatedMove.setText("Thinking");

		//updates board
		int k = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 6; j++) {
				printBoardTable.setValueAt(game.getBoard().pieceOnTile(k), i, j);
				k++;
			}
		}

		if(!game.getBoard().currentPlayer().isKingAlive()){
			if(game.getBoard().currentPlayer().getSide().toString() == "CPU"){
				JOptionPane.showMessageDialog(null, "You won", "Game End", JOptionPane.INFORMATION_MESSAGE);
			} else if(game.getBoard().currentPlayer().getSide().toString() == "HUMAN"){
				JOptionPane.showMessageDialog(null, "Dumblet won", "Game End", JOptionPane.INFORMATION_MESSAGE);
			}
			System.out.println(calculateAverage(executionTimeList));
			return;
		}
		modelListLegalMoves.clear();

		if(game.getBoard().currentPlayer() == game.getBoard().humanPlayer()){
			for(final String move : game.getBoard().printLegalMoves(game.getBoard().currentPlayer())){
				modelListLegalMoves.addElement(move);
			}
		} else {
			AIThinkTank thinkTank = new AIThinkTank();
			thinkTank.execute();
		}

	}
	
	private double calculateAverage(ArrayList <Long> marks) {
		  Long sum = (long) 0;
		  if(!marks.isEmpty()) {
		    for (Long mark : marks) {
		        sum += mark;
		    }
		    return sum.doubleValue() / marks.size();
		  }
		  return sum;
		}

	private static class AIThinkTank extends SwingWorker<Move, String>{

		private AIThinkTank(){

		}

		@Override
		protected Move doInBackground() throws Exception {
			morphFrame.lblStatus.setText("AI is thinking");
			final MoveStrategy miniMax;
			if(game.getBoard().currentPlayer().getSide().toString() == "CPU"){

				miniMax = new AlphaBetaWithMoveOrdering(8);
			} else {
				miniMax = new AlphaBetaNoMobilityCheck(8);
			}
			final Move bestMove = miniMax.execute(game.getBoard());
			executionTimeList.add(( miniMax).getExecutionTime());
			morphFrame.lblStatus.setText("AI is waiting on you");
			morphFrame.lblSearchDepth.setText("Dumblet searched up to "+ miniMax.pliesSearched() );
			return bestMove;
		}

		@Override
		public void done(){
			try{
				
				final Move bestMove = get();
				morphFrame.doComputerMove(bestMove);
				morphFrame.lblTranslatedMove.setText("Translated move is "+bestMove.toStringReversed());
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(ExecutionException e){
				e.printStackTrace();
			}
		}

	}
}
