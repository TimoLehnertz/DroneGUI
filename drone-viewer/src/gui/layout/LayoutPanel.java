package gui.layout;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class LayoutPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int GAP = 2;
	
	/**
	 * areas
	 */
	private Header header = new Header();
	private Footer footer = new Footer();
	private Explorer explorer = new Explorer();
	private PropertiesPanel propertiesPanel = new PropertiesPanel();
	private Viewer viewer = new Viewer();
	
	public LayoutPanel() {
		super();
		setLayout(new BorderLayout(GAP, GAP));
		add(header, BorderLayout.NORTH);
		add(footer, BorderLayout.SOUTH);
		add(explorer, BorderLayout.WEST);
		add(propertiesPanel, BorderLayout.CENTER);
		add(viewer, BorderLayout.EAST);
	}
}