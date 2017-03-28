package morph.engine.player;

public enum MoveStatus {
	DONE {
		@Override
		public boolean isDone(){
			return true;
		}
	},
	ILLEGAL_MOVE {
		@Override
		public boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}
	}, 
	EXPOSING_KING_TO_ATTACK {
		@Override
		public boolean isDone() {
			// TODO Auto-generated method stub
			return false;
		}
	};

	public abstract boolean isDone();
}
