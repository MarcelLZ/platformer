package shared;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Sprites {
	public static final String WORLD = "outside_sprites.png";
	public static final String PLAYER = "player_sprites.png";
	public static final String WORLD_DEFINITION = "level_one_data.png";
	
	public static BufferedImage getSprite(String fileName) {
		BufferedImage image = null;
		InputStream inputStream = Sprites.class.getResourceAsStream("/" + fileName);
		
		try {
			image = ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
