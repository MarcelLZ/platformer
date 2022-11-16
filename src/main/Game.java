package main;

import java.awt.Graphics;

import entities.Player;
import levels.LevelManager;
import shared.Constants;
import shared.Constants.Dimensions;

public class Game implements Runnable {
	private Panel panel;
	private Window window;
	private Thread thread;

	private Player player;
	private LevelManager levelManager;

	public Game() {
		initWorld();
		
		panel = new Panel(this);
		window = new Window(panel);
		panel.requestFocus();

		startGameLoop();
	}

	public void update() {
		player.update();
		levelManager.update();
	}

	public void render(Graphics graphics) {
		levelManager.render(graphics);
		player.render(graphics);
	}

	public Player getPlayer() {
		return player;
	}

	public void windowFocusLost() {
		player.stopMoving();
	}

	@Override
	public void run() {
		long previousTime = System.nanoTime();
		double timePerFrame = 1000000000.0 / Constants.FPS_SET; // 1 second.
		double timePerUpdate = 1000000000.0 / Constants.UPS_SET;

		double deltaU = 0;
		double deltaF = 0;

		int frames = 0;
		int updates = 0;
		int oneSecond = 1000;
		long lastCheck = System.currentTimeMillis();

		while (true) {
			long currentTime = System.nanoTime();

			deltaF += (currentTime - previousTime) / timePerFrame;
			deltaU += (currentTime - previousTime) / timePerUpdate;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				panel.repaint();
				frames++;
				deltaF--;
			}

			if ((System.currentTimeMillis() - lastCheck) >= oneSecond) {
				System.out.println("FPS: " + frames + " | UPS: " + updates);

				frames = 0;
				updates = 0;
				lastCheck = System.currentTimeMillis();
			}
		}
	}

	private void startGameLoop() {
		thread = new Thread(this);
		thread.start();
	}

	private void initWorld() {
		levelManager = new LevelManager(this);
		player = new Player(200, 198, (int) (64 * Dimensions.SCALE), (int) (40 * Dimensions.SCALE));
		player.setWorldData(levelManager.getCurrentLevel().getLevelData());
	}
}
