package morph;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import morph.engine.board.Move;
import morph.engine.player.MoveTransition;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

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
	private JTextArea txtrMovelist;
	private DefaultListModel<String> model;
	private static Game game;
	private static boolean gameStart;
	private ArrayList<String> legalMoves;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MorphFrame frame = new MorphFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		gameStart = true;
		
		
		
		

	}

	/**
	 * Create the frame.
	 */
	public MorphFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
			}
		});
		btnMove.setBounds(485, 531, 89, 23);
		contentPane.add(btnMove);
		
		txtrMovelist = new JTextArea();
		txtrMovelist.setLineWrap(true);
		txtrMovelist.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtrMovelist.setText("MoveList");
		txtrMovelist.setBounds(10, 384, 399, 170);
		contentPane.add(txtrMovelist);
		
		JLabel lblMoveList = new JLabel("Move List");
		lblMoveList.setBounds(10, 359, 71, 14);
		contentPane.add(lblMoveList);
		
		
		
		
		model = new DefaultListModel<String>();
		JList<String> list = new JList<String>(model);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				moveInput.setText(list.getSelectedValue());
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(422, 10, 152, 477);
		contentPane.add(list);
		
		
		
		
		
		
		
		
		
		// GAME START //
		if(gameStart = true){
			game = new Game();
			update();
		}
		
	}
	
	private void doMove(String input){
		if(legalMoves.contains(input)){
			int translatedInput[] = translateInput(input);
			final Move move = Move.MoveGenerator.createMove(game.getBoard(), translatedInput[0], translatedInput[1]);
			final MoveTransition transition = game.getBoard().currentPlayer().makeMove(move);
			if(transition.getMoveStatus().isDone()){
				game.setBoard(transition.getToBoard());
			}
			update();
		} else if(!input.matches("[A-F][1-8][A-F][1-8]")){
			JOptionPane.showMessageDialog(null,
				    "Wrong format, try again");
		} else {
			JOptionPane.showMessageDialog(null,
				    "Not in legal moves list");
		}
		
		
		
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
		legalMoves = game.getBoard().printLegalMoves(game.getBoard().currentPlayer());
		
		//updates board
		int k = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 6; j++) {
				printBoardTable.setValueAt(game.getBoard().pieceOnTile(k), i, j);
				k++;
			}
		}
		
		//updates movelist
		txtrMovelist.setText(legalMoves.toString());
		model.clear();
		for(final String move : legalMoves){
			model.addElement(move);
		}
		
	}
}
