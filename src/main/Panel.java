package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import shared.*;
import shared.Constants.Directions;

public class Panel extends JPanel {
	private Game game;
	private MouseInputs mouseInputs;
	private KeyboardInputs keyboardInputs;

	public Panel(Game game) {
		this.game = game;

		mouseInputs = new MouseInputs(this);
		keyboardInputs = new KeyboardInputs(this);

		defineGameSize();
		addKeyListener(keyboardInputs);
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	public void updateGame() {
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		game.render(graphics);
	}

	public Game getGame() {
		return game;
	}

	private void defineGameSize() {
		int width = Constants.Dimensions.GAME_WIDTH;
		int height = Constants.Dimensions.GAME_HEIGHT;
		Dimension size = new Dimension(width, height);
		
		System.out.println("Width: " + width + " | Height: " + height);
		setPreferredSize(size);
	}
}
