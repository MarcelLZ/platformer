package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import shared.Constants;
import shared.Sprites;
import shared.World;

public class LevelManager {
	Game game;
	BufferedImage[] sprite;
	private Level level;
	
	int tileSize = Constants.Dimensions.TILE_SIZE;
	int tilesInWidth = Constants.Dimensions.TILES_IN_WIDTH;
	int tilesInHeight = Constants.Dimensions.TILES_IN_HEIGHT;
	int defaultTileSize = Constants.Dimensions.DEFAULT_TILE_SIZE;
	
	public LevelManager(Game game) {
		this.game = game;
		loadSprite();
		level = new Level(World.getWorldData());
	}
	
	public void update() {}
	
	public void render(Graphics graphics) {
		for (int column = 0; column < tilesInHeight; column++) {
			for (int row = 0; row < tilesInWidth; row++) {
				int index = level.getSpriteIndex(row, column);
				graphics.drawImage(sprite[index], row * tileSize, column * tileSize, tileSize, tileSize, null);
			}
		}
	}

	public Level getCurrentLevel() {
		return level;
	}
	
	private void loadSprite() {
		BufferedImage image = Sprites.getSprite(Sprites.WORLD);
		sprite = new BufferedImage[48];
		
		for (int column = 0; column < 4; column++) {
			for (int row = 0; row < 12; row++) {
				int index = column * 12 + row;
				sprite[index] = image.getSubimage(row * defaultTileSize, column * defaultTileSize, defaultTileSize, defaultTileSize);
			}
		}
	}
}
