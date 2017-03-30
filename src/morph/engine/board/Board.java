package morph.engine.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.stream.Stream;

import morph.engine.Side;
import morph.engine.pieces.Bishop;
import morph.engine.pieces.King;
import morph.engine.pieces.Knight;
import morph.engine.pieces.Pawn;
import morph.engine.pieces.Piece;
import morph.engine.pieces.Rook;
import morph.engine.player.CPUPlayer;
import morph.engine.player.HumanPlayer;
import morph.engine.player.Player;

public class Board {
	
	private final List<Tile> gameBoard;
	private final Collection<Piece> humanPieces;
	private final Collection<Piece> cpuPieces;
	
	private final HumanPlayer humanPlayer;
	private final CPUPlayer cpuPlayer;
	private final Player currentPlayer;
	
	

	private Board(final Builder builder){
		this.gameBoard = createGameBoard(builder);
		this.humanPieces = calculateActivePieces(this.gameBoard, Side.HUMAN);
		this.cpuPieces = calculateActivePieces(this.gameBoard, Side.CPU);
		
		final List<Move> humanStandardLegalMoves = calculateLegalMoves(this.humanPieces);
		final List<Move> cpuStandardLegalMoves = calculateLegalMoves(this.cpuPieces); 
		
		this.humanPlayer = new HumanPlayer(this, humanStandardLegalMoves, cpuStandardLegalMoves);
		this.cpuPlayer = new CPUPlayer(this, cpuStandardLegalMoves, humanStandardLegalMoves);
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.humanPlayer, this.cpuPlayer);
	}
	
	public String pieceOnTile(int i){
		return this.gameBoard.get(i).toString();
	}
	
	@Override
	public String toString(){
		final StringBuilder builder = new StringBuilder();
		for(int i = 0; i < BoardUtils.NUM_TILES; i++){
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%6s", tileText));
			if((i+1) % 6 == 0){
				builder.append("\n");
			}
		}
		return builder.toString();
			
	}
	
	public Player humanPlayer(){
		return this.humanPlayer;
	}
	
	public Player cpuPlayer(){
		return this.cpuPlayer;
	}
	
	public Player currentPlayer() {
		return this.currentPlayer;
	}
	
	
	public Collection<Piece> getHumanPieces() {
		return this.humanPieces;
	}
	public Collection<Piece> getCPUPieces() {
		return this.cpuPieces;
	}

	private List<Move> calculateLegalMoves(final Collection<Piece> pieces) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final Piece piece : pieces){
			
			legalMoves.addAll(piece.calculateLegalMoves(this));
			
		}
		return Collections.unmodifiableList(legalMoves);
	}
	
	public ArrayList<String> printLegalMoves(Player player){
		ArrayList<String> printableLegalMoves = new ArrayList<>();
		
		for(final Move move : player.getLegalMoves()){
			printableLegalMoves.add(move.toString());
		}
		
		return printableLegalMoves;
	}

	private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Side side) {
		
		final List<Piece> activePieces = new ArrayList<>();
		
		for(final Tile tile : gameBoard){
			if(tile.isTileOccupied()) {
				final Piece piece = tile.getPiece();
				if(piece.getPieceSide() == side){
					activePieces.add(piece);
				}
			}
		}
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(activePieces);
	}

	public Tile getTile(int tileCoord) {
		return gameBoard.get(tileCoord);
	}
	
	private static List<Tile> createGameBoard(final Builder builder){
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		return Collections.unmodifiableList(Arrays.asList(tiles));
		
	}

	public static Board createStandardBoard(boolean cpuMovesFirst){
		final Builder builder = new Builder();
		//player layout
		builder.setPiece(new King(1, Side.CPU));
		builder.setPiece(new Knight(6, Side.CPU));
		builder.setPiece(new Bishop(7, Side.CPU));
		builder.setPiece(new Rook(8, Side.CPU));
		builder.setPiece(new Rook(9, Side.CPU));
		builder.setPiece(new Bishop(10, Side.CPU));
		builder.setPiece(new Knight(11, Side.CPU));
		builder.setPiece(new Pawn(14, Side.CPU));
		builder.setPiece(new Pawn(15, Side.CPU));
		
		
		//computer layout
		builder.setPiece(new King(46, Side.HUMAN));
		builder.setPiece(new Knight(36, Side.HUMAN));
		builder.setPiece(new Bishop(37, Side.HUMAN));
		builder.setPiece(new Rook(38, Side.HUMAN));
		builder.setPiece(new Rook(39, Side.HUMAN));
		builder.setPiece(new Bishop(40, Side.HUMAN));
		builder.setPiece(new Knight(41, Side.HUMAN));
		builder.setPiece(new Pawn(32, Side.HUMAN));
		builder.setPiece(new Pawn(33, Side.HUMAN));
		
		//set movemaker to the one decided to be the move maker!
		
		if(cpuMovesFirst == true){
			builder.setMoveMaker(Side.CPU);
		} else {
			builder.setMoveMaker(Side.HUMAN);
		}
		
		return builder.build();
		
		
		
	}
	
	public static class Builder {
		
		Map<Integer, Piece> boardConfig;
		Side nextMoveMaker;
		
		public Builder(){
			this.boardConfig = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Builder setMoveMaker(final Side nextMoveMaker) {
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build(){
			return new Board(this);
		}
	}


	/*
	 * THIS IS IF WE WANT TO GET ALL MOVES ON MOVE GENERATOR
	public Iterable<Move> getAllLegalMoves() {
		Stream<Move> stream = Stream.concat(this.humanPlayer.getLegalMoves().stream(), this.cpuPlayer.getLegalMoves().stream());
		Iterable<Move> iterable = stream::iterator;
		return iterable;
	}
	*/
	
	

}
