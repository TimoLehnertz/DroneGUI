package gui.elements;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.elements.plotter.GraphViewer;

public class Viewer extends JPanel {

	private static final long serialVersionUID = 1L;
	private GraphViewer graph = new GraphViewer();
	private View3D view3D = new View3D();

	public Viewer() {
		super();
		setLayout(new GridLayout(2, 1));
		add(view3D);
		add(graph);
		setBackground(Color.DARK_GRAY);
	}
}