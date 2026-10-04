package com.yaoroz.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class BreakoutGame extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final int BALL_SIZE = 10;
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_Y = 480;
	int ballX = 250;
	int ballY = 250;
	int ballVelocityX = 3;
	int ballVelocityY = 3;
	int paddleX = 0;
	final int NUM_BLOCKS = 5;
	int[][] blocks = new int[NUM_BLOCKS][2];

	public BreakoutGame() {
		for (int i = 0; i < NUM_BLOCKS; i++) {
			blocks[i][0] = i * 50 + 25;
			blocks[i][1] = 25;
		}
		addKeyListener(new KeyAdapter() {
			@Override public void keyPressed(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_LEFT) {
					paddleX = Math.max(0, paddleX - 10);
				} else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
					paddleX = Math.min(WIDTH - PADDLE_WIDTH, paddleX + 10);
				}
			}
		});
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	/** キー入力・描画と同じSwingイベントスレッドで1フレーム進める。 */
	void step() {
		int previousY = ballY;
		ballX += ballVelocityX;
		ballY += ballVelocityY;
		if (ballX < 0 || ballX > WIDTH - BALL_SIZE) {
			ballX = Math.max(0, Math.min(WIDTH - BALL_SIZE, ballX));
			ballVelocityX = -ballVelocityX;
		}
		if (ballY < 0 || ballY > HEIGHT - BALL_SIZE) {
			ballY = Math.max(0, Math.min(HEIGHT - BALL_SIZE, ballY));
			ballVelocityY = -ballVelocityY;
		}
		// 下降中にパドル上端を通過したときだけ跳ね返す。
		if (ballVelocityY > 0 && previousY + BALL_SIZE <= PADDLE_Y && ballY + BALL_SIZE >= PADDLE_Y
				&& ballX + BALL_SIZE > paddleX && ballX < paddleX + PADDLE_WIDTH) {
			ballY = PADDLE_Y - BALL_SIZE;
			ballVelocityY = -ballVelocityY;
		}
		Rectangle ball = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
		for (int[] block : blocks) {
			if (block[1] >= 0 && ball.intersects(new Rectangle(block[0], block[1], 40, 10))) {
				block[1] = -1;
				ballVelocityY = -ballVelocityY;
				break;
			}
		}
		repaint();
	}

	@Override public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(Color.WHITE);
		graphics.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
		graphics.fillRect(paddleX, PADDLE_Y, PADDLE_WIDTH, 10);
		for (int[] block : blocks) {
			if (block[1] >= 0) graphics.fillRect(block[0], block[1], 40, 10);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame();
			BreakoutGame game = new BreakoutGame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.add(game);
			frame.pack();
			frame.setVisible(true);
			game.requestFocusInWindow();
			new Timer(10, event -> game.step()).start();
		});
	}
}
