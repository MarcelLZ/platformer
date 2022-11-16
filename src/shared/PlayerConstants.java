package shared;

public class PlayerConstants {
	public static final int MOVEMENT_SPEED = 1;
	public static final int ANIMATION_SPEED = 15;
	
	public static class Actions {
		public static final int IDDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMP = 2;
		public static final int FALLING = 3;
		public static final int GROUD = 4;
		public static final int HIT = 5;
		public static final int ATTACK_1 = 6;
		public static final int ATTACK_JUMP_1 = 7;
		public static final int ATTACK_JUMP_2 = 8;
		
		public static int getSpriteAmount(int playerAction) {
			switch (playerAction) {
			case IDDLE:
				return 5;
			case RUNNING:
				return 6;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK_1:
			case ATTACK_JUMP_1:
			case ATTACK_JUMP_2:
				return 3;
			case GROUD:
				return 2;
			case FALLING:
			default:
				return 1;
			}
		}
	}
}
