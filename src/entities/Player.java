package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import shared.Constants;
import shared.PlayerConstants;
import shared.Sprites;
import shared.World;

public class Player extends Entity {
	private float scale = Constants.Dimensions.SCALE;
	private int[][] worldData;

	// Movement and animations.
	private BufferedImage[][] animations;
	private int animationTick, animationIndex = 0;
	private int playerAction = PlayerConstants.Actions.IDDLE;
	private boolean left, up, right, down;
	private boolean moving, attacking, jumping;
	
	// Hitbox.
	private float xDrawOffset = 21 * Constants.Dimensions.SCALE;
	private float yDrawOffset = 4 * Constants.Dimensions.SCALE;

	// Gravity.
	private float airSpeed = 0f;
	private float gravity = 0.04f * scale;
	private float jumpSpeed = -2.25f * scale;
	private float fallSpeedAfterColision = 0.5f * scale;
	private boolean inAir = false;
	

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		defineHitBox(x, y, 20 * scale, 27 * scale);
	}

	public void update() {
		tickAnimations();
		setAnimations();
		updatePlayerPosition();
	}

	public void render(Graphics graphics) {
		float x = hitBox.x - xDrawOffset;
		float y = hitBox.y - yDrawOffset;
		
		BufferedImage image = animations[playerAction][animationIndex];
		graphics.drawImage(image, (int) x, (int) y, width, height, null);
	}

	public void setWorldData(int[][] worldData) {
		this.worldData = worldData;
	}

	public void stopMoving() {
		left = false;
		up = false;
		right = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public boolean isJumping() {
		return jumping;
	}
	
	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	private void tickAnimations() {
		animationTick++;
		if (animationTick >= PlayerConstants.ANIMATION_SPEED) {
			animationTick = 0;
			animationIndex++;

			if (animationIndex >= PlayerConstants.Actions.getSpriteAmount(playerAction)) {
				animationIndex = 0;
				attacking = false;
			}
		}
	}

	private void setAnimations() {
		int actualAnimation = playerAction;

		if (moving) {
			playerAction = PlayerConstants.Actions.RUNNING;
		} else {
			playerAction = PlayerConstants.Actions.IDDLE;
		}

		if (inAir) {
			if (airSpeed < 0) {
				playerAction = PlayerConstants.Actions.JUMP;
			} 
			
//			else {
//				playerAction = PlayerConstants.Actions.FALLING;
//			}
		} 

		if (attacking) {
			playerAction = PlayerConstants.Actions.ATTACK_1;
		}

		if (actualAnimation != playerAction) {
			resetAnimation();
		}
	}

	private void resetAnimation() {
		animationTick = 0;
		animationIndex = 0;
	}

	private void updatePlayerPosition() {
		moving = false;
		
		if (jumping) jump(); 
		if (!left && !right && !inAir)
			return;

		float xSpeed = 0;

		if (left) {
			xSpeed -= PlayerConstants.MOVEMENT_SPEED;
		} 
		
		if (right) {
			xSpeed += PlayerConstants.MOVEMENT_SPEED;
		}
		
		if (!inAir) {
			if (!World.isOnTheFloor(hitBox, worldData)) {
				inAir = true;
			}
		}
		
		if (inAir) {
			if (World.canMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, worldData)) {
				hitBox.y += airSpeed;
				airSpeed += gravity;
				updateXPosition(xSpeed);
			} else {
				hitBox.y = World.getYPositionUnderRoofOrAboveFloor(hitBox, airSpeed);
				if (airSpeed > 0) {
					inAir = false;
					airSpeed = 0;
				} else {
					airSpeed = fallSpeedAfterColision;
				}
				
				updateXPosition(xSpeed);
			}
		} else {
			updateXPosition(xSpeed);
		}
		
		moving = true;
	}
	
	private void jump() {
		if (inAir) return;
		
		inAir = true;
		airSpeed = jumpSpeed;
	}
	
	private void updateXPosition(float xSpeed) {
		float x = hitBox.x + xSpeed;
		float y = hitBox.y;
		if (World.canMoveHere(x, y, hitBox.width, hitBox.height, worldData)) {
			hitBox.x += xSpeed;
		} else {
			hitBox.x = World.getXPositionNextToWall(hitBox, xSpeed);
		}
	}

	private void loadAnimations() {
		BufferedImage image = Sprites.getSprite(Sprites.PLAYER);

		animations = new BufferedImage[9][6];
		for (int row = 0; row < animations.length; row++) {
			for (int column = 0; column < animations[row].length; column++) {
				animations[row][column] = image.getSubimage(column * 64, row * 40, 64, 40);
			}
		}
	}
}
