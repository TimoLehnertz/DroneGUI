package gui.layout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import gui.GuiLogic;

public class PropertiesPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	GuiLogic logic = GuiLogic.getInstance();
	
	JComponent active = null;
	
	public PropertiesPanel() {
		super();
		logic.registerPropertiePanel(this);
	}

	public void load(JComponent properties) {
		if(properties == active) return;
		if(active != null) {
			remove(active);
		}
		active = properties;
		add(active);
		revalidate();
		repaint();
	}
}