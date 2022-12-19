package com.yaoroz.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

// �u���b�N�����̃Q�[��
public class Breakout extends JPanel implements KeyListener {
	/**
	 * �V���A��
	 */
	private static final long serialVersionUID = 1L;
	// ��ʂ̑傫��
	private static final int SCREEN_WIDTH = 400;
	private static final int SCREEN_HEIGHT = 600;

	// �u���b�N���쐬
	private static final int BLOCK_WIDTH = 60;
	private static final int BLOCK_HEIGHT = 20;
	private Rectangle block = new Rectangle(100, 50, BLOCK_WIDTH, BLOCK_HEIGHT);
	private boolean blockExistence = true;

	// �{�[�����쐬
	private static final int BALL_RADIUS = 10;
	private Point ballCenter = new Point(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	private Rectangle ball = new Rectangle(ballCenter.x - BALL_RADIUS, ballCenter.y - BALL_RADIUS, BALL_RADIUS * 2,
			BALL_RADIUS * 2);

	// �p�h�����쐬
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 20;
	private Rectangle paddle = new Rectangle(340, 520, PADDLE_WIDTH, PADDLE_HEIGHT);

	// �ړ���
	private int dx = 5;
	private int dy = 5;

	public Breakout() {
		// �L�[���͂��󂯕t����悤�ɐݒ肷��
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
		ball.x += dx;
		ball.y += dy;

		// �{�[�������E�̕ǂɓ��������Ƃ��̔��ˏ���
		if (ballCenter.x < BALL_RADIUS || ballCenter.x > SCREEN_WIDTH - BALL_RADIUS) {
			dx = -dx;
		}

		// �{�[������̕ǂɓ��������Ƃ��̔��ˏ���
		if (ballCenter.y < BALL_RADIUS) {
			dy = -dy;
		}

		// �{�[�������̕ǂɓ��������Ƃ��̏���
		if (ball.y > SCREEN_HEIGHT + BALL_RADIUS) {
			// �Q�[�����I������
			System.exit(0);
		}
		// �{�[�����p�h���ɓ��������Ƃ��̔��ˏ���
		if (ball.intersects(paddle)) {
			dy = -dy;
		}
		// �{�[�����u���b�N�ɓ��������Ƃ��̔��ˏ���
		if (ball.intersects(block)) {
			blockExistence = false;
			dy = -dy;
		}

		// ��ʂ��X�V
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// ���������߂Ƀt�H�[�J�X
		requestFocus();

		// ��ʂ𔒂œh��Ԃ�
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		// �u���b�N��`��
		g.setColor(Color.BLACK);
		if (blockExistence) {
			g.fillRect(block.x, block.y, block.width, block.height);
		}
		// �{�[����`��
		g.fillOval(ball.x, ball.y, ball.width, ball.height);

		// �p�h����`��
		g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
		// �f�o�b�O�p�o��
		System.out.println("----------------------------------------");
		System.out.printf("block(x,y)=(%s,%s)\n", block.x, block.y);
		System.out.printf("ball(x,y)=(%s,%s)\n", ball.x, ball.y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		// �����L�[�������ꂽ�Ƃ�
		if (key == KeyEvent.VK_LEFT) {
			// �_�����Ɉړ�����
			paddle.x = Math.max(0, paddle.x - 10);
		}
		// �E���L�[�������ꂽ�Ƃ�
		if (key == KeyEvent.VK_RIGHT) {
			// �_���E�Ɉړ�����
			paddle.x = Math.min(SCREEN_WIDTH - PADDLE_WIDTH, paddle.x + 10);
		}
	}

	// �L�[�������ꂽ�Ƃ��̏���
	@Override
	public void keyReleased(KeyEvent e) {
	}

	// �L�[���^�C�v���ꂽ�Ƃ��̏���
	@Override
	public void keyTyped(KeyEvent e) {
	}
}
