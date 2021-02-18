import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.Action;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, Action {

	private boolean play = false;
	private int score = 0;
	private int totalBricks = 50;
	private Timer time; // how fast ball should move
	private int delay = 8;

	private int playerX = 465; // starting position of slider
	private int ballposX = 502;
	private int ballposY = 530;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private BrickGenerator map;

	public Game() {
		map = new BrickGenerator(5, 10);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		time = new Timer(delay, this);
		time.start();

	}

	// to draw the shapes, ball, bricks
	public void paint(Graphics g) {

		// for background
		Image img = Toolkit.getDefaultToolkit().getImage("imgs/background3.jpg");
		g.drawImage(img, 155, 0, 692, 650, null);

		// scoreboard
		g.setColor(Color.black);
		g.fillRect(0, 0, 155, 650);
		// draw map
		map.draw((Graphics2D) g);

		// for borders
		g.setColor(Color.black);
		// g.fillRect(x, y, width, height);
		g.fillRect(155, 0, 5, 640);
		g.fillRect(155, 0, 692, 5);
		g.fillRect(845, 0, 5, 650);
		// for paddle
		g.setColor(Color.white);
		g.fillRect(playerX, 550, 100, 8);
		// for ball
		g.setColor(Color.white);
		g.fillOval(ballposX, ballposY, 20, 20);

		if (totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won!", 415, 300);

			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 370, 350);

		}
		if (ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.white);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over!", 410, 300);
			g.drawString("Your score: " + score, 395, 350);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 380, 400);
		}

		// scores
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.drawString("Score:", 20, 50);
		g.drawString("" + score, 20, 100);

		g.dispose();
	}

	// automatically called method
	@Override
	public void actionPerformed(ActionEvent e) {
		time.start();
		// true if u click right or left key
		if (play) {
			// intersection of ball and paddle
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
				ballYdir = -ballYdir;

			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (this.map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 235;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;

						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;

						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;

							if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}

							break A;
						}

					}
				}
			}

			ballposX += ballXdir;
			ballposY += ballYdir;
			// for left border
			if (ballposX < 160) {
				ballXdir = -ballXdir;
			}
			// for top border
			if (ballposY < 5) {
				ballYdir = -ballYdir;
			}

			// for right
			if (ballposX > 825) {
				ballXdir = -ballXdir;
			}

		}
		repaint();
	}

	@Override
	public Object getValue(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putValue(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 745)
				playerX = 745;
			else
				moveRight();
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX <= 165)
				playerX = 165;
			else
				moveLeft();
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballposX = 502;
				ballposY = 530;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 465;
				score = 0;
				totalBricks = 50;
				map = new BrickGenerator(5, 10);

				repaint();
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

}
