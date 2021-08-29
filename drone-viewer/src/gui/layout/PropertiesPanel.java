package gui.layout;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

import gui.GuiLogic;

public class PropertiesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	GuiLogic logic = GuiLogic.getInstance();
	
	JComponent active = null;
	
	public PropertiesPanel() {
		super();
		logic.registerPropertiePanel(this);
		
		addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
			}
			@Override
			public void componentResized(ComponentEvent e) {
				revalidate();
				if(active != null) active.setPreferredSize(new Dimension(getWidth(), getHeight()));
			}
			@Override
			public void componentMoved(ComponentEvent e) {
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	public void load(JComponent properties) {
		if(properties == active) return;
		if(active != null) {
			remove(active);
		}
		active = properties;
		add(active);
		revalidate();
		active.revalidate();
		if(active != null) active.setPreferredSize(new Dimension(getWidth(), getHeight()));
		repaint();
	}
}