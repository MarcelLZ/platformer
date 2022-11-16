package shared;

public class Constants {
	public static final int PLAYER_SPEED = 3;
	public static final int TOTAL_ENEMIES = 1;
	public static final int ANIMATION_SPEED = 15;
	
	public static final int FPS_SET = 120;
	public static final int UPS_SET = 200;
	
	public static class Dimensions {
		public static final float SCALE = 1.5f;
		public static final int DEFAULT_TILE_SIZE = 32;
		public static final int TILES_IN_WIDTH = 26;
		public static final int TILES_IN_HEIGHT = 14;
		public static final int TILE_SIZE = (int) (DEFAULT_TILE_SIZE * SCALE);
		public static final int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
		public static final int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;
	}
	
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
}

