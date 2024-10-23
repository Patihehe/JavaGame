package utilz;

public class Constants {

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final String RUNNING = "walk";
		public static final String IDLE = "idle", ATTACK = "chem";

		public static int GetSpriteAmount(String player_action) {
			switch (player_action){
				case RUNNING:
					return 3;
				case IDLE:
					return 5;
				case ATTACK:
					return 4;
				default:
					return 1;
			}
		}
	}

}
