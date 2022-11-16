package levels;

public class Level {
	private int[][] levelData;
	
	public Level(int[][] level) {
		this.levelData = level;
	}
	
	public int getSpriteIndex(int x, int y) {
		return levelData[y][x];
	}
	
	public int[][] getLevelData() {
		return levelData;
	}
}
