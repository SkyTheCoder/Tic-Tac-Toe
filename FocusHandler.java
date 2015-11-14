import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FocusHandler implements FocusListener {
	public Game game = null;
	
	public FocusHandler(Game game) {
		this.game = game;
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		game.focused = true;
		game.update();
	}

	@Override
	public void focusLost(FocusEvent e) {
		game.focused = false;
		game.update();
	}
}
