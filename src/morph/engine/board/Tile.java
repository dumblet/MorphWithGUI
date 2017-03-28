package morph.engine.board;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import morph.engine.pieces.Piece;

public abstract class Tile {
	
	protected final int tileCoordinate;
	
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	
	Tile(int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
		for(int i = 0; i < 48; i++){
			emptyTileMap.put(i, new EmptyTile(i));
		}
		return Collections.unmodifiableMap(emptyTileMap);
	}
	
	public static Tile createTile(final int tileCoordinate, final Piece piece){
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
	}

	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public static final class EmptyTile extends Tile{
		
		private EmptyTile(int coordinate){
			super(coordinate);
		}
		
		@Override
		public String toString() {
			return "-";
		}
		
		@Override
		public boolean isTileOccupied() {
			return false;
		}
		
		public Piece getPiece(){
			return null;
		}
	}
	
	public static final class OccupiedTile extends Tile {
		
		private final Piece pieceOnTile;
		
		private OccupiedTile(int tileCoordinate, Piece pieceOnTile){
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString() {
			return getPiece().getPieceSide().isHuman() ? getPiece().toString().toLowerCase() :
				getPiece().toString();
		}
		

		@Override
		public boolean isTileOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			return pieceOnTile;
		}
		
		
	}
}
