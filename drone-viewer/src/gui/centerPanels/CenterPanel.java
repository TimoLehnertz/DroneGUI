package gui.centerPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.Cons;

public class CenterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	protected JPanel body = new JPanel();
	
	public CenterPanel(String name) {
		super();
		
		setLayout(new BorderLayout(2, 2));

		JPanel top = new JPanel();
		JLabel nameLabel = new JLabel(name);
		nameLabel.setFont(Cons.FONT_H2);
		top.add(nameLabel);
		setBackground(Color.black);
		body.setBackground(Color.DARK_GRAY);
		
//		body = new JPanel();
		
		/**
		 * Scroll bar
		 */
		
		JScrollPane scroll = new JScrollPane(body);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		setPreferredSize(new Dimension(500, 700));
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		
//		body.add(scroll);
		
		add(top, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
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
