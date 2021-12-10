package gui.elements;

import java.awt.BorderLayout;
import java.awt.Component;

import xGui.XLabel;
import xGui.XPanel;
import xThemes.XStyle;

public class SectionPanel extends XPanel {

	private static final long serialVersionUID = 1L;

	XLabel nameLabel;
	XPanel head;
	XPanel body;
	
	public SectionPanel(String name) {
		super(XStyle.BACKGROUND);
		head = new XPanel(XStyle.LAYER2);
		body = new XPanel(XStyle.LAYER1);
		nameLabel = new XLabel(name);
		nameLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		setLayout(new BorderLayout(2, 2));
		
		head.add(nameLabel);
		
		
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
	}
	
	public XPanel getBody() {
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