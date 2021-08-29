package gui;

import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gui.layout.LayoutPanel;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	private LayoutPanel layout;
	GuiLogic logic = GuiLogic.getInstance();
	
	public Frame() {
		super("Drone viewer");
		logic.setFrame(this);
		Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 1300, 800);
		setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon("img/drone-logo.png");
		setIconImage(img.getImage());
		layout = new LayoutPanel();
		add(layout);
		setVisible(true);
	}
}
