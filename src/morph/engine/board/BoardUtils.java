package morph.engine.board;

public class BoardUtils {

	public static final int NUM_TILES = 48;


	private BoardUtils(){
		throw new RuntimeException("Cannot be instantiated");
	}


	public static boolean isValidTileCoordinate(int coord) {
		return coord >=0 && coord < 48;
	}


	public static boolean noMoveError(final int currentPosition, final int candidateOffset){


		int sum = candidateOffset + currentPosition;

		if(Math.abs(candidateOffset) < 10 && Math.abs(candidateOffset) > 1 ){
			int minRange, maxRange;
			if(candidateOffset > 0){
				minRange = ((currentPosition / 6) + 1) * 6;
				maxRange = ((currentPosition / 6) + 2) * 6;
				if(sum >= minRange && sum < maxRange){
					return true;
				} else {
					return false;
				}
			}
			if(candidateOffset < 0){
				minRange = ((currentPosition / 6)) * 6;
				maxRange = ((currentPosition / 6) - 1) * 6;

				if(sum < minRange && sum >= maxRange){
					return true;
				} else {
					return false;
				}
			}
		}
		if(Math.abs(candidateOffset) <= 1 ){
			int minRange, maxRange;
			minRange = ((currentPosition / 6)) * 6;
			maxRange = ((currentPosition / 6) + 1) * 6;
			if(sum >= minRange && sum < maxRange){
				return true;
			} else {
				return false;
			}

		}

		if(Math.abs(candidateOffset) > 10){
			int minRange, maxRange;
			if(candidateOffset > 0){
				minRange = ((currentPosition / 6) + 2) * 6;
				maxRange = ((currentPosition / 6) + 3) * 6;
				if(sum >= minRange && sum < maxRange){
					return true;
				} else {
					return false;
				}
			}
			if(candidateOffset < 0){
				minRange = ((currentPosition / 6) - 1) * 6;
				maxRange = ((currentPosition / 6) - 2) * 6;

				if(sum < minRange && sum >= maxRange){
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

}
