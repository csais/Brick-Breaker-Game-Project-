import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BrickGenerator {
	public int map[][];
	public int brickHeight;
	public int brickWidth;

	public BrickGenerator(int row, int col) {
		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				// 1 means the brick has not intersected
				// with the ball
				map[i][j] = 1;
			}
		}
		brickWidth = 540 / col;
		brickHeight = 150 / row;
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] > 0) {
					Color myblue = new Color(102, 178, 255); // Color white
					g.setColor(myblue);
					g.fillRoundRect(j * brickWidth + 235, i * brickHeight + 50, brickWidth, brickHeight, 25, 25);

					// a border
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRoundRect(j * brickWidth + 235, i * brickHeight + 50, brickWidth, brickHeight, 25, 25);
				}
			}
		}

	}

	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;

	}

}
