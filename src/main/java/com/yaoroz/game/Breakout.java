package com.yaoroz.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

// �u���b�N�����̃Q�[��
public class Breakout extends JPanel {
	// ��ʂ̑傫��
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 600;

	// �u���b�N���쐬
	private static final int BLOCK_WIDTH = 60;
	private static final int BLOCK_HEIGHT = 20;
	private Rectangle block = new Rectangle(100, 50, BLOCK_WIDTH, BLOCK_HEIGHT);

	// �{�[�����쐬
	private static final int BALL_RADIUS = 10;
	private Point ballCenter = new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	private Rectangle ball = new Rectangle(ballCenter.x - BALL_RADIUS, ballCenter.y - BALL_RADIUS, BALL_RADIUS * 2,
			BALL_RADIUS * 2);

	// �p�h�����쐬
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 20;
	private Rectangle paddle = new Rectangle(SCREEN_WIDTH / 2 - PADDLE_WIDTH / 2, SCREEN_HEIGHT - PADDLE_HEIGHT - 10,
			PADDLE_WIDTH, PADDLE_HEIGHT);

	// �ړ���
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

		// �Q�[���̃��C�����[�v
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
		// �{�[�����ړ�
		ballCenter.x += dx;
		ballCenter.y += dy;

		// �{�[�������E�̕ǂɓ��������Ƃ��̔��ˏ���
		if (ballCenter.x < BALL_RADIUS || ballCenter.x > SCREEN_WIDTH - BALL_RADIUS) {
			dx = -dx;
		}
		// �{�[������̕ǂɓ��������Ƃ��̔��ˏ���
		if (ballCenter.y < BALL_RADIUS) {
			dy = -dy;
		}
		// �{�[�����p�h���ɓ��������Ƃ��̔��ˏ���
		if (ball.intersects(paddle)) {
			dy = -dy;
		}

		// ��ʂ��X�V
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// ��ʂ𔒂œh��Ԃ�
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		// �u���b�N��`��
		g.setColor(Color.BLACK);
		g.fillRect(block.x, block.y, block.width, block.height);

		// �{�[����`��
		g.fillOval(ball.x, ball.y, ball.width, ball.height);

		// �p�h����`��
		g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
	}
}
