package gui.centerPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Cons;

public class CenterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JPanel body = new JPanel();
	
	public CenterPanel(String name) {
		super();
		
		setLayout(new BorderLayout(2, 2));

		JPanel top = new JPanel();
		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(Cons.FONT_H2);
		top.add(nameLabel);
		setBackground(Color.black);
		
//		body = new JPanel();
		
		add(top, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
	}
	
	public JPanel getBody() {
		return body;
	}
	
	@Override
	public Component add(Component comp) {
		return body.add(comp);
	}
	
	@Override
	public void remove(Component comp) {
		body.remove(comp);
	}
}
