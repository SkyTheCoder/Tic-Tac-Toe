import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class Main {
	public static Game game = null;
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setSize(300, 320);
		game = new Game(frame);
		MouseHandler mh = new MouseHandler(frame, game);
		frame.addMouseListener(mh);
		frame.addMouseMotionListener(mh);
		KeyHandler kh = new KeyHandler();
		frame.addKeyListener(kh);
		FocusHandler fh = new FocusHandler(game);
		frame.addFocusListener(fh);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width / 2 - frame.getWidth() / 2, d.height / 2 - frame.getHeight() / 2);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.requestFocus();
		Thread t = new Thread(game);
		t.start();
	}
}
