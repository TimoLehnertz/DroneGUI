package gui.elements.plotter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import gui.elements.ImageButton;

public class Plotter extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private List<Graph> graphs = new ArrayList<>();
	private GraphRenderer renderer = new GraphRenderer(graphs);
	private boolean changed = true;
	private Timer t = new Timer(1000 / 30, this);
	private JPanel legend = new JPanel();
	private JButton toggleButton = new ImageButton("right.png", 15, 20);
	private boolean legendOpened = true;
	private JPanel header = new JPanel();
	private JLabel name = new JLabel("Plotter");
	private String requiredString = null;
	
	public Plotter() {
		super();
		setLayout(new BorderLayout());
		
		legend.setLayout(new GridLayout(4, 1));
		legend.add(toggleButton);
		toggleButton.addActionListener(e -> toggleLegend());
		setBackground(Color.gray);
		legend.setOpaque(false);
		renderer.setOpaque(false);
		openLegend();
		header.add(name);
		add(header, BorderLayout.NORTH);
		add(legend, BorderLayout.WEST);
		add(renderer, BorderLayout.CENTER);
		t.start();
	}
	
	public void addRemoveButton(JButton b) {
		header.add(b);
	}
	
	private void toggleLegend() {
		if(legendOpened) closeLegend(); else openLegend();
	}
	
	private void openLegend() {
		legend.setPreferredSize(new Dimension(200, 0));
		revalidate();
		repaint();
		legendOpened = true;
	}
	
	private void closeLegend() {
		legend.setPreferredSize(new Dimension(50, 0));
		revalidate();
		repaint();
		legendOpened = false;
	}
	
	public void plott(String label, double value) {
		plott(label, value, System.currentTimeMillis());
	}
	
	private void addGraph(Graph graph) {
		graphs.add(graph);
		JPanel p = new JPanel();
		JLabel l = new JLabel(graph.getLabel());
		JPanel color = new JPanel();
		color.setBackground(graph.getColor());
		JButton close = new JButton("visible");
		close.addActionListener(e -> {close.setText(graph.toggle() ? "Visible" : "Hidden");});
		
		
		p.add(l);
		p.add(color);
		p.add(close);
		p.setOpaque(false);
		legend.add(p);
		legend.setLayout(new GridLayout(legend.getComponentCount(), 1));
		revalidate();
		repaint();
	}
	
	public void plott(String label, double value, double time) {
		if(requiredString != null) {
			if(!label.contains(requiredString)) {
				return;
			}
		}
		Graph graph = getGraph(label);
		if(graph == null) {
			graph = new Graph(label);
			addGraph(graph);
		}
		graph.put(time, value);
		changed = true;
	}
	
	private Graph getGraph(String label) {
		for (Graph graph : graphs) {
			if(graph.getLabel().contentEquals(label)) {
				return graph;
			}
		}
		return null;
	}
	
	public void setName(String name) {
		this.name.setText(name);
	}

	public String getRequiredString() {
		return requiredString;
	}

	public void setRequiredString(String requiredString) {
		this.requiredString = requiredString;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(changed) {
			renderer.repaint();
			changed = false;
		}
	}
}