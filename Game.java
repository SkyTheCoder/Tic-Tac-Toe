import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Game implements Runnable {
	public int[][] grid = new int[3][3];
	public JFrame frame = null;
	public int turn = 1;
	public int won = 0;
	public boolean focused = true;
	public boolean exiting = false;
	
	public Game(JFrame frame) {
		this.frame = frame;
		for (int x = 0; x < 3; ++x) {
			grid[x] = new int[3];
			for (int y = 0; y < 3; ++y) {
				grid[x][y] = 0;
			}
		}
	}
	
	@Override
	public void run() {
		update();
	}
	
	public void update() {
		BufferStrategy bs = this.frame.getBufferStrategy();
		if (bs == null) {
			this.frame.createBufferStrategy(2);
			bs = this.frame.getBufferStrategy();
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		FontMetrics fm = g.getFontMetrics(g.getFont());
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, 300, 320);
		if (focused) {
			g.setColor(new Color(245, 245, 245));
			g.fillRect(0, 0, 300, 20);
		}
		if (exiting) {
			g.setColor(new Color(200, 200, 200));
			g.fillRect(280, 0, 20, 20);
		}
		g.setColor(new Color(220, 220, 220));
		g.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if (won == 0) {
			for (int x = 1; x < 3; ++x) {
				g.drawLine(x * 100, 20, x * 100, 320);
			}
			for (int y = 1; y < 3; ++y) {
				g.drawLine(0, y * 100 + 20, 300, y * 100 + 20);
			}
			for (int x = 0; x < 3; ++x) {
				for (int y = 0; y < 3; ++y) {
					int type = this.grid[x][y];
					int centerX = x * 100 + 50;
					int centerY = y * 100 + 70;
					switch (type) {
						case 1:
							g.drawLine(centerX - 20, centerY - 20, centerX + 20, centerY + 20);
							g.drawLine(centerX + 20, centerY - 20, centerX - 20, centerY + 20);
							break;
						case 2:
							g.drawOval(centerX - 20, centerY - 20, 40, 40);
							break;
					}
				}
			}
		} else if (won > 0) {
			String msg = "";
			switch (won) {
				case 1:
					msg = "X wins!";
					break;
				case 2:
					msg = "O wins!";
					break;
			}
			g.drawString(msg, 150 - fm.stringWidth(msg) / 2, 170);
		} else {
			String msg = "Nobody wins!";
			g.drawString(msg, 150 - fm.stringWidth(msg) / 2, 170);
		}
		g.drawLine(0, 20, 300, 20);
		g.drawLine(280, 0, 280, 20);
		g.drawLine(285, 5, 295, 15);
		g.drawLine(295, 5, 285, 15);
		if (focused) {
			g.setColor(new Color(180, 180, 180));
		}
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
		fm = g.getFontMetrics(g.getFont());
		g.drawString("Tic-Tac-Toe", 150 - fm.stringWidth("Tic-Tac-Toe") / 2, 17);
		bs.show();
		g.dispose();
	}
	
	public void checkWin() {
		for (int type = 1; type < 3; ++type) {
			for (int x = 0; x < 3; ++x) {
				if (this.grid[x][0] == type && this.grid[x][1] == type && this.grid[x][2] == type) {
					won = type;
				}
			}
			for (int y = 0; y < 3; ++y) {
				if (this.grid[0][y] == type && this.grid[1][y] == type && this.grid[2][y] == type) {
					won = type;
				}
			}
			if (this.grid[0][0] == type && this.grid[1][1] == type && this.grid[2][2] == type) {
				won = type;
			}
			if (this.grid[2][0] == type && this.grid[1][1] == type && this.grid[0][2] == type) {
				won = type;
			}
		}
		
		if (won != 0) {
			update();
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					for (int x = 0; x < 3; ++x) {
						for (int y = 0; y < 3; ++y) {
							Main.game.grid[x][y] = 0;
						}
					}
					Main.game.turn = 1;
					Main.game.won = 0;
					Main.game.update();
				}
			}).start();
		} else {
			int taken = 0;
			for (int x = 0; x < 3; ++x) {
				for (int y = 0; y < 3; ++y) {
					if (this.grid[x][y] != 0) {
						++taken;
					}
				}
			}
			if (taken == 9) {
				won = -1;
				update();
				new Thread(new Runnable() {
					public void run() {
						try {
							Thread.sleep(3000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						for (int x = 0; x < 3; ++x) {
							for (int y = 0; y < 3; ++y) {
								Main.game.grid[x][y] = 0;
							}
						}
						Main.game.turn = 1;
						Main.game.won = 0;
						Main.game.update();
					}
				}).start();
			}
		}
	}
}
