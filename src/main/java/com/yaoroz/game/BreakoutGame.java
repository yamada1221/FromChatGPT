package com.yaoroz.game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BreakoutGame extends JPanel {
	// �{�[���̍��W
	int ballX = 250;
	int ballY = 250;

	// �{�[���̑��x
	int ballVelocityX = 3;
	int ballVelocityY = 3;

	// �p�h���̍��W
	int paddleX = 0;

	// �u���b�N�̐�
	final int NUM_BLOCKS = 5;

	// �u���b�N�̍��W
	int[][] blocks = new int[NUM_BLOCKS][2];

	public BreakoutGame() {
		// �u���b�N��z�u����
		for (int i = 0; i < NUM_BLOCKS; i++) {
			blocks[i][0] = i * 50 + 25;
			blocks[i][1] = 25;
		}

		// �L�[���͂��󂯕t����
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// �������Ȃ�
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// �������Ȃ�
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// ���L�[�������ꂽ�ꍇ
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					// �p�h�������Ɉړ�����
					paddleX -= 10;
				}
				// �E�L�[�������ꂽ�ꍇ
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					// �p�h�����E�Ɉړ�����
					paddleX += 10;
				}
			}
		});
		setFocusable(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// �w�i��h��Ԃ�
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		// �{�[����`�悷��
		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, 10, 10);

		// �p�h����`�悷��
		g.setColor(Color.WHITE);
		g.fillRect(paddleX, 480, 60, 10);

		// �u���b�N��`�悷��
		for (int i = 0; i < NUM_BLOCKS; i++) {
			g.setColor(Color.WHITE);
			g.fillRect(blocks[i][0], blocks[i][1], 40, 10);
		}
	}

	// ���C������
	public static void main(String[] args) throws InterruptedException {
		JFrame frame = new JFrame();
		BreakoutGame game = new BreakoutGame();
		frame.add(game);
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �Q�[�����[�v
		while (true) {
			// �{�[�����ړ�������
			game.ballX += game.ballVelocityX;
			game.ballY += game.ballVelocityY;

			// �{�[�������܂��͉E�̕ǂɓ��������ꍇ�́A���x�𔽓]������
			if (game.ballX < 0 || game.ballX > 480) {
				game.ballVelocityX *= -1;
			}

			// �{�[������܂��͉��̕ǂɓ��������ꍇ�́A���x�𔽓]������
			if (game.ballY < 0 || game.ballY > 480) {
				game.ballVelocityY *= -1;
			}

			// �{�[�����p�h���ɓ��������ꍇ�́A���x�𔽓]������
			if (game.ballY > 460 && game.ballX > game.paddleX && game.ballX < game.paddleX + 60) {
				game.ballVelocityY *= -1;
			}

			// �{�[�����u���b�N�ɓ��������ꍇ�́A�u���b�N�������đ��x�𔽓]������
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
