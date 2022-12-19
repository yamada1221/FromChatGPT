package com.yaoroz.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

// ブロック崩しのゲーム
public class Breakout extends JPanel {
	// 画面の大きさ
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 600;

	// ブロックを作成
	private static final int BLOCK_WIDTH = 60;
	private static final int BLOCK_HEIGHT = 20;
	private Rectangle block = new Rectangle(100, 50, BLOCK_WIDTH, BLOCK_HEIGHT);

	// ボールを作成
	private static final int BALL_RADIUS = 10;
	private Point ballCenter = new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	private Rectangle ball = new Rectangle(ballCenter.x - BALL_RADIUS, ballCenter.y - BALL_RADIUS, BALL_RADIUS * 2,
			BALL_RADIUS * 2);

	// パドルを作成
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 20;
	private Rectangle paddle = new Rectangle(SCREEN_WIDTH / 2 - PADDLE_WIDTH / 2, SCREEN_HEIGHT - PADDLE_HEIGHT - 10,
			PADDLE_WIDTH, PADDLE_HEIGHT);

	// 移動量
	private int dx = 5;
	private int dy = 5;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

		Breakout game = new Breakout();
		frame.add(game);

		frame.setVisible(true);

		// ゲームのメインループ
		while (true) {
			game.update();
			game.repaint();

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		// ボールを移動
		ballCenter.x += dx;
		ballCenter.y += dy;

		// ボールが左右の壁に当たったときの反射処理
		if (ballCenter.x < BALL_RADIUS || ballCenter.x > SCREEN_WIDTH - BALL_RADIUS) {
			dx = -dx;
		}
		// ボールが上の壁に当たったときの反射処理
		if (ballCenter.y < BALL_RADIUS) {
			dy = -dy;
		}
		// ボールがパドルに当たったときの反射処理
		if (ball.intersects(paddle)) {
			dy = -dy;
		}

		// 画面を更新
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 画面を白で塗りつぶす
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		// ブロックを描画
		g.setColor(Color.BLACK);
		g.fillRect(block.x, block.y, block.width, block.height);

		// ボールを描画
		g.fillOval(ball.x, ball.y, ball.width, ball.height);

		// パドルを描画
		g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
	}
}
