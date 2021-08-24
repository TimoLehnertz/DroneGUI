package gui.elements.plotter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.GuiLogic;
import gui.elements.ImageButton;
import maths.Vec3;
import serial.SensorListener;

public class GraphViewer extends JPanel implements SensorListener {

	private static final long serialVersionUID = 1L;

	private GuiLogic logic = GuiLogic.getInstance();
	private JComboBox<String> combo = new JComboBox<>();
	private JPanel header = new JPanel();
	private JLabel headerLabel = new JLabel("Plotters");
	private List<Plotter> plotters = new ArrayList<>();
	private JPanel plotterPanel = new JPanel();
	private JButton addButton = new JButton("Add");
	
	public GraphViewer() {
		super();
		logic.getSerialInterface().addSensorListener(this);
		
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(plotterPanel, BorderLayout.CENTER);
		plotterPanel.setBackground(Color.DARK_GRAY);
		plotterPanel.setLayout(new GridLayout(3, 1, 0, 2));
		
		initHeader();
	}
	
	private void initHeader() {
		/**
		 * combo
		 */
		addButton.addActionListener(e -> {
			if(combo.getSelectedItem() != null) {				
				addPlotter(getPlotterOfType((String) combo.getSelectedItem()));
			}
		});
		/**
		 * gui
		 */
		header.add(headerLabel);
		header.add(combo);
		header.add(addButton);
	}
	
	private void addPlotter(Plotter p) {
		p.setPreferredSize(new Dimension(500, 0));
		JButton remove = new ImageButton("x.png", 18, 18);
		remove.addActionListener(e -> removePlotter(p));
		p.addRemoveButton(remove);
		plotters.add(p);
		plotterPanel.add(p);
		
		plotterPanel.setLayout(new GridLayout(plotters.size(), 1, 0, 2));
		revalidate();
		repaint();
	}
	
	private void removePlotter(Plotter p) {
		plotters.remove(p);
		plotterPanel.remove(p);
		
		plotterPanel.setLayout(new GridLayout(plotters.size(), 1, 0, 2));
		revalidate();
		repaint();
	}
	
	private boolean sensorListed(String label) {
		for (int i = 0; i < combo.getItemCount(); i++) {
			if(combo.getItemAt(i).contentEquals(label)) {
				return true;
			}
		}
		return false;
	}
	
	private static Plotter getPlotterOfType(String type) {
		Plotter p = new Plotter();
		p.setName(type);
		return p;
	}

	@Override
	public void sensorReceived(String sensorName, String sensorSubType, double value) {
		if(!sensorListed(sensorName)) {
			combo.addItem(sensorName);
		}
		
		for (Plotter plotter : plotters) {
			if(plotter.getName().contentEquals(sensorName)) {
				plotter.plott(sensorSubType, value);
			}
		}
	}
}