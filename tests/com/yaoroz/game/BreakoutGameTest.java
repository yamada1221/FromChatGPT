package com.yaoroz.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class BreakoutGameTest {
	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeAndWait(() -> {
			BreakoutGame game = new BreakoutGame();
			game.setSize(game.getPreferredSize());
			check(game.getWidth() == 500 && game.getHeight() == 500, "playfield must have room for its paddle");
			for (int i = 0; i < 100; i++) press(game, KeyEvent.VK_LEFT);
			check(game.paddleX == 0, "left key must stop at edge");
			for (int i = 0; i < 100; i++) press(game, KeyEvent.VK_RIGHT);
			check(game.paddleX == 440, "right key must stop at edge");

			game.paddleX = 0;
			game.ballX = 10;
			game.ballY = 468;
			game.ballVelocityX = 0;
			game.ballVelocityY = 3;
			game.step();
			check(game.ballVelocityY == -3 && game.ballY == 470, "descending ball must bounce at paddle top");
			game.step();
			check(game.ballVelocityY == -3 && game.ballY == 467, "ball must leave paddle without getting stuck");

			game.ballX = 20;
			game.ballY = 20;
			game.ballVelocityY = 3;
			game.step();
			check(game.blocks[0][1] == -1, "overlapping ball edge must remove block");
			game.ballY = 20;
			game.ballVelocityY = 3;
			game.step();
			check(game.ballVelocityY == 3, "removed block must not collide");
			BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = image.createGraphics();
			try { game.paintComponent(graphics); } finally { graphics.dispose(); }
			check(image.getRGB(30, 1) == Color.BLACK.getRGB(), "removed block must not remain as a stripe at the top");

			game.ballX = 488;
			game.ballY = 250;
			game.ballVelocityX = 3;
			game.step();
			check(game.ballX == 490 && game.ballVelocityX == -3, "wall collision must keep full ball within playfield");
		});
		System.out.println("PASS: playfield, key bounds, paddle bounce, ball-edge contact, removed-block rendering and wall bounds");
	}
	private static void press(BreakoutGame game, int key) {
		KeyEvent event = new KeyEvent(game, KeyEvent.KEY_PRESSED, 0, 0, key, KeyEvent.CHAR_UNDEFINED);
		for (KeyListener listener : game.getKeyListeners()) listener.keyPressed(event);
	}
	private static void check(boolean value, String message) {
		if (!value) throw new AssertionError(message);
	}
}
