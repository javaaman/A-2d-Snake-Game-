package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
	private boolean inGame = true;

	private int dots;
	private Timer timer;

	private Image apple;
	private Image dot;
	private Image head;

	private final int Random_poss = 29;
	private int apple_x;
	private int apple_y;

	private final int All_dots = 900;
	private final int Dot_size = 10;
	private final int x[] = new int[All_dots];
	private final int y[] = new int[All_dots];

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;

	Board() {

		addKeyListener(new TAdapter());

		setBackground(Color.black);
		setPreferredSize(new Dimension(300, 300));
		setFocusable(true);
		loadImages();
		initGame();
	}

	public void loadImages() {
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.jpg"));
		apple = i1.getImage();

		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.jpg"));
		dot = i2.getImage();

		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.jpg"));
		head = i3.getImage();
	}

	public void initGame() {

		dots = 3;

		for (int i = 0; i < dots; i++) {
			y[i] = 50;
			x[i] = 50 - i * Dot_size;
		}
		locate_Apple();
		timer = new Timer(140, this);
		timer.start();

	}

	public void locate_Apple() {
		int r = (int) (Math.random() * Random_poss);
		apple_x = r * Dot_size;
		r = (int) (Math.random() * Random_poss);
		apple_y = r * Dot_size;
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		draw(g);
	}

	public void draw(Graphics g) {
		if (inGame) {
			g.drawImage(apple, apple_x, apple_y, this);

			for (int i = 0; i < dots; i++) {
				if (i == 0) {
					g.drawImage(head, x[i], y[i], this);
				} else {
					g.drawImage(dot, x[i], y[i], this);
				}
			}

			Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);

		}
	}

	public void gameOver(Graphics g) {
		String msg = "Game Over!";
		Font font = new Font("SAN_SERIF", Font.BOLD, 14);
		FontMetrics metrices = getFontMetrics(font);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg, (300 - metrices.stringWidth(msg) )/ 2, 300 / 2);
	}

	public void move() {
		for (int i = dots; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		if (leftDirection) {
			x[0] = x[0] - Dot_size;
		}
		if (rightDirection) {
			x[0] = x[0] + Dot_size;
		}
		if (upDirection) {
			y[0] = y[0] - Dot_size;
		}
		if (downDirection) {
			y[0] = y[0] + Dot_size;
		}

	}

	public void checkApple() {
		if (x[0] == apple_x && (y[0] == apple_y)) {
			dots++;
			locate_Apple();
		}
	}

	public void checkCollision() {
		for (int i = dots; i > 0; i--) {
			if (i > 4 && (x[0] == x[i]) && y[0] == y[i]) {
				inGame = false;
			}
		}

		if (y[0] >= 300) {
			inGame = false;
		}

		if (x[0] >= 300) {
			inGame = false;
		}

		if (y[0] < 0) {
			inGame = false;
		}

		if (x[0] < 0) {
			inGame = false;
		}
		if (!inGame) {
			timer.stop();
		}

	}

	public void actionPerformed(ActionEvent ae) {
		if (inGame) {
			checkApple();
			checkCollision();
			move();
		}

		repaint();
	}

	public class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if (key == KeyEvent.VK_RIGHT && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if (key == KeyEvent.VK_UP && (!downDirection)) {
				upDirection = true;
				leftDirection = false;
				rightDirection = false;
			}
			if (key == KeyEvent.VK_DOWN && (!upDirection)) {
				downDirection = true;
				leftDirection = false;
				rightDirection = false;
			}

		}
	}
}
