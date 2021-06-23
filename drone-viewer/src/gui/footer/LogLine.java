package gui.footer;

import java.awt.Color;

import javax.swing.JLabel;

public class LogLine extends JLabel {

	private static final long serialVersionUID = 1L;

	public LogLine(String msg, boolean zebra) {
		super(msg);
		setZebra(zebra);
		setOpaque(true);
		setHorizontalAlignment(LEFT);
		setForeground(Color.white);
	}
	
	public void setZebra(boolean zebra) {
		if(zebra) {
			setBackground(Color.black);			
		} else {
			setBackground(Color.DARK_GRAY);
		}
	}
}
