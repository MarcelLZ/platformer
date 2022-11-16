package shared;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class World {
	public static int[][] getWorldData() {
		int[][] data = new int[Constants.Dimensions.TILES_IN_HEIGHT][Constants.Dimensions.TILES_IN_WIDTH];
		BufferedImage image = Sprites.getSprite(Sprites.WORLD_DEFINITION);
		
		for(int column = 0; column < image.getHeight(); column++) {
			for(int row = 0; row < image.getWidth(); row++) {
				Color color = new Color(image.getRGB(row, column));
				int value = color.getRed();
				data[column][row] = value >= 48 ? 0 : value;
			}
		}
		
		return data;
	}
	
	public static float getXPositionNextToWall(Rectangle2D.Float hitBox, float nextPosition) {
		int currentTile = (int) (hitBox.x / Constants.Dimensions.TILE_SIZE);
		if (nextPosition > 0) { // Going right.
			int tileXPosition = currentTile * Constants.Dimensions.TILE_SIZE;
			int xOffset = (int) (Constants.Dimensions.TILE_SIZE - hitBox.width);
			
			return tileXPosition + xOffset - 1;
		} else { // Going left.
			return currentTile * Constants.Dimensions.TILE_SIZE;
		}
	}
	
	public static float getYPositionUnderRoofOrAboveFloor(Rectangle2D.Float hitBox, float nextPosition) {
		int currentTile = (int) (hitBox.y / Constants.Dimensions.TILE_SIZE);
		if (nextPosition > 0) { // Falling.
			int tileYPosition = currentTile * Constants.Dimensions.TILE_SIZE;
			int yOffset = (int) (Constants.Dimensions.TILE_SIZE - hitBox.height);
			
			return tileYPosition + yOffset - 1;
		} else { // Jumping.
			return currentTile * Constants.Dimensions.TILE_SIZE;
		}
	}
	
	public static boolean isOnTheFloor(Rectangle2D.Float hitBox, int[][] worldData) {
		if (!isSolid(hitBox.x, hitBox.y + hitBox.height + 1, worldData))
			if (!isSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, worldData))
				return false;
		
		return true;
	}
	
	public static boolean canMoveHere(float x, float y, float width, float height, int[][] worldData) {
		if (!isSolid(x, y, worldData))
			if (!isSolid(x + width, y + height, worldData))
				if (!isSolid(x + width, y, worldData))
					if (!isSolid(x, y + height, worldData))
						return true;
		
		return false;
	}
	
	private static boolean isSolid(float x, float y, int[][] worldData) {
		if (x < 0 || x >= Constants.Dimensions.GAME_WIDTH) return true;
		if (y < 0 || y >= Constants.Dimensions.GAME_HEIGHT) return true;
		
		float xIndex = x / Constants.Dimensions.TILE_SIZE;
		float yIndex = y / Constants.Dimensions.TILE_SIZE;
		int value = worldData[(int) yIndex][(int) xIndex];
		
		return value >= 48 || value < 0 || value != 11;
	}
}
