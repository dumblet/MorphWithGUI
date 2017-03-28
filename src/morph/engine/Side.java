package morph.engine;

import morph.engine.player.CPUPlayer;
import morph.engine.player.HumanPlayer;
import morph.engine.player.Player;

public enum Side {
	HUMAN {
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isHuman() {
			return true;
		}

		@Override
		public boolean isCPU() {
			return false;
		}

		@Override
		public Player choosePlayer(HumanPlayer humanPlayer, CPUPlayer cpuPlayer) {
			return humanPlayer;
		}
	},
	CPU {
		public int getDirection(){
			return -1;
		}

		@Override
		public boolean isHuman() {
			return false;
		}

		@Override
		public boolean isCPU() {
			return true;
		}

		@Override
		public Player choosePlayer(HumanPlayer humanPlayer, CPUPlayer cpuPlayer) {
			return cpuPlayer;
		}
	};
	// CPU vs PLAYER
	// CPU is "BLACK", PLAYER is "WHITE"
	
	public abstract int getDirection();
	public abstract boolean isHuman();
	public abstract boolean isCPU();
	public abstract Player choosePlayer(HumanPlayer humanPlayer, CPUPlayer cpuPlayer) ;
	
}
