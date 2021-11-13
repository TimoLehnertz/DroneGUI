package popups;

import javax.swing.JFrame;

public abstract class Popup extends JFrame {

	private static final long serialVersionUID = 1L;

	public Popup() {
		super();
	}
	
	public Popup(String name) {
		super(name);
	}
	
	public abstract void close();
}