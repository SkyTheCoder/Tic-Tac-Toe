import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class MouseHandler implements MouseListener, MouseMotionListener {
	public JFrame frame = null;
	public Game game = null;
	public Point startMouse = new Point(0, 0);
	public Point lastMouse = new Point(0, 0);
	public boolean pressing = false;
	
	public MouseHandler(JFrame frame, Game game) {
		this.frame = frame;
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressing = true;
		if (e.getX() >= 280 && e.getY() <= 20) {
			game.exiting = true;
		} else {
			game.exiting = false;
		}
		game.update();
		startMouse = e.getPoint();
		lastMouse = e.getLocationOnScreen();
		int x = (int)Math.floor(((float) e.getX()) / 100);
		int y = (int)Math.floor((float) (e.getY() - 20) / 100);
		if (e.getY() > 20 && y >= 0 && game.grid[x][y] == 0 && game.won == 0) {
			game.grid[x][y] = game.turn;
			game.turn = 2 - (game.turn - 1);
			game.checkWin();
			game.update();
		} else if (y < 0) {
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressing = false;
		if (startMouse.x >= 280 && startMouse.y <= 20 && e.getX() >= 280 && e.getY() <= 20) {
			System.exit(0);
		} else {
			game.exiting = false;
		}
		game.update();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getX() >= 280 && e.getY() <= 20 && pressing && startMouse.x >= 280 && startMouse.y <= 20) {
			game.exiting = true;
		} else {
			game.exiting = false;
		}
		game.update();
		if (startMouse.y <= 20 && startMouse.x < 280) {
			Point p = frame.getLocation();
			frame.setLocation((int) (p.x + (e.getXOnScreen() - lastMouse.getX())), (int) (p.y + (e.getYOnScreen() - lastMouse.getY())));
			lastMouse = e.getLocationOnScreen();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= 280 && e.getY() <= 20 && pressing && startMouse.x >= 280 && startMouse.y <= 20) {
			game.exiting = true;
		} else {
			game.exiting = false;
		}
		game.update();
	}

}
