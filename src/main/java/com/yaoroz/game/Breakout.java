package com.yaoroz.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

// ブロック崩しのゲーム
public class Breakout extends JPanel implements KeyListener {
	/**
	 * シリアル
	 */
	private static final long serialVersionUID = 1L;
	// 画面の大きさ
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 600;

	// ブロックを作成
	private static final int BLOCK_WIDTH = 60;
	private static final int BLOCK_HEIGHT = 20;
	private Rectangle block = new Rectangle(100, 50, BLOCK_WIDTH, BLOCK_HEIGHT);
	private boolean blockExistence = true;

	// ボールを作成
	private static final int BALL_RADIUS = 10;
	private Point ballCenter = new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	private Rectangle ball = new Rectangle(ballCenter.x - BALL_RADIUS, ballCenter.y - BALL_RADIUS, BALL_RADIUS * 2,
			BALL_RADIUS * 2);

	// パドルを作成
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 20;
	private Rectangle paddle = new Rectangle(340, 520, PADDLE_WIDTH, PADDLE_HEIGHT);

	// 移動量
	private int dx = 5;
	private int dy = 5;

	public Breakout() {
		// キー入力を受け付けるように設定する
		addKeyListener(this);
	}

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
		ball.x += dx;
		ball.y += dy;

		// ボールが左右の壁に当たったときの反射処理
		if (ballCenter.x < BALL_RADIUS || ballCenter.x > SCREEN_WIDTH - BALL_RADIUS) {
			dx = -dx;
		}

		// ボールが上の壁に当たったときの反射処理
		if (ballCenter.y < BALL_RADIUS) {
			dy = -dy;
		}

		// ボールが下の壁に当たったときの処理
		if (ball.y > SCREEN_HEIGHT + BALL_RADIUS) {
			// ゲームを終了する
			System.exit(0);
		}
		// ボールがパドルに当たったときの反射処理
		if (ball.intersects(paddle)) {
			dy = -dy;
		}
		// ボールがブロックに当たったときの反射処理
		if (ball.intersects(block)) {
			blockExistence = false;
			dy = -dy;
		}

		// 画面を更新
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 動かすためにフォーカス
		requestFocus();

		// 画面を白で塗りつぶす
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		// ブロックを描画
		g.setColor(Color.BLACK);
		if (blockExistence) {
			g.fillRect(block.x, block.y, block.width, block.height);
		}
		// ボールを描画
		g.fillOval(ball.x, ball.y, ball.width, ball.height);

		// パドルを描画
		g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
		// デバッグ用出力
		System.out.println("----------------------------------------");
		System.out.printf("block(x,y)=(%s,%s)\n", block.x, block.y);
		System.out.printf("ball(x,y)=(%s,%s)\n", ball.x, ball.y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// 左矢印キーが押されたとき
		if (key == KeyEvent.VK_LEFT) {
			// 棒を左に移動する
			paddle.x = Math.max(0, paddle.x - 10);
		}
		// 右矢印キーが押されたとき
		if (key == KeyEvent.VK_RIGHT) {
			// 棒を右に移動する
			paddle.x = Math.min(SCREEN_WIDTH - PADDLE_WIDTH, paddle.x + 10);
		}
	}

	// キーが離されたときの処理
	@Override
	public void keyReleased(KeyEvent e) {
	}

	// キーがタイプされたときの処理
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
