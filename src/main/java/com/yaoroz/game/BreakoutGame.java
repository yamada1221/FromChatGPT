package com.yaoroz.game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BreakoutGame extends JPanel {
	// ボールの座標
	int ballX = 250;
	int ballY = 250;

	// ボールの速度
	int ballVelocityX = 3;
	int ballVelocityY = 3;

	// パドルの座標
	int paddleX = 0;

	// ブロックの数
	final int NUM_BLOCKS = 5;

	// ブロックの座標
	int[][] blocks = new int[NUM_BLOCKS][2];

	public BreakoutGame() {
		// ブロックを配置する
		for (int i = 0; i < NUM_BLOCKS; i++) {
			blocks[i][0] = i * 50 + 25;
			blocks[i][1] = 25;
		}

		// キー入力を受け付ける
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// 何もしない
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// 何もしない
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// 左キーが押された場合
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					// パドルを左に移動する
					paddleX -= 10;
				}
				// 右キーが押された場合
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					// パドルを右に移動する
					paddleX += 10;
				}
			}
		});
		setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 背景を塗りつぶす
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		// ボールを描画する
		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, 10, 10);

		// パドルを描画する
		g.setColor(Color.WHITE);
		g.fillRect(paddleX, 480, 60, 10);

		// ブロックを描画する
		for (int i = 0; i < NUM_BLOCKS; i++) {
			g.setColor(Color.WHITE);
			g.fillRect(blocks[i][0], blocks[i][1], 40, 10);
		}
	}

	// メイン処理
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame();
		BreakoutGame game = new BreakoutGame();
		frame.add(game);
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ゲームループ
		while (true) {
			// ボールを移動させる
			game.ballX += game.ballVelocityX;
			game.ballY += game.ballVelocityY;

			// ボールが左または右の壁に当たった場合は、速度を反転させる
			if (game.ballX < 0 || game.ballX > 480) {
				game.ballVelocityX *= -1;
			}

			// ボールが上または下の壁に当たった場合は、速度を反転させる
			if (game.ballY < 0 || game.ballY > 480) {
				game.ballVelocityY *= -1;
			}

			// ボールがパドルに当たった場合は、速度を反転させる
			if (game.ballY > 460 && game.ballX > game.paddleX && game.ballX < game.paddleX + 60) {
				game.ballVelocityY *= -1;
			}

			// ボールがブロックに当たった場合は、ブロックを消して速度を反転させる
			for (int i = 0; i < game.NUM_BLOCKS; i++) {
				if (game.blocks[i][1] > 0 && game.ballY < game.blocks[i][1] + 10 && game.ballY > game.blocks[i][1]
						&& game.ballX > game.blocks[i][0] && game.ballX < game.blocks[i][0] + 40) {
					game.blocks[i][1] = -1;
					game.ballVelocityY *= -1;
				}
			}

			game.repaint();
			Thread.sleep(10);
		}
	}
}
