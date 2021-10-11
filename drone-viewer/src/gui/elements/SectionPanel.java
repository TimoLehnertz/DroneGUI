package gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Cons;

public class SectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	JLabel nameLabel;
	JPanel head;
	JPanel body;
	
	public SectionPanel(String name) {
		super();
		head = new JPanel();
		body = new JPanel();
		nameLabel = new JLabel(name);
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		nameLabel.setFont(Cons.FONT_H3);

		head.setBackground(Color.gray);
		
		setLayout(new BorderLayout(2, 2));
		
		head.add(nameLabel);
		
		
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		
		setBackground(Color.DARK_GRAY);
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
	
	public String getName() {
		return nameLabel.getText();
	}
}