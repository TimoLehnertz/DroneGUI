package gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import gui.layout.LayoutPanel;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	private LayoutPanel layout;;
	
	public Frame() {
		super("Drone viewer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 1000);
		setLocationRelativeTo(null);
		ImageIcon img = new ImageIcon("img/drone-logo.png");
		setIconImage(img.getImage());
		layout = new LayoutPanel();
		add(layout);
		setVisible(true);
	}
}
