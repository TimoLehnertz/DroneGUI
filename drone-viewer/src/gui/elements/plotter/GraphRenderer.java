package gui.elements.plotter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import input.Controlls;

public class GraphRenderer extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<Graph> graphs;
	
	private static Color COLOR_LINE = Color.darkGray;
	private double minSpan = 3000.0;
	private double maxSpan = 3000.0;
	private boolean keepCenter = true;
	private int lineMargin = 25;

	private double padding = 0.3;//	multiplicator
	
	private double minTime, maxTime, minVal, maxVal;
	private int width, height;
	
	private Controlls mouse = new Controlls(this);
	
	public GraphRenderer(List<Graph> graphs) {
		super();
		this.graphs = graphs;
//		setPreferredSize(new Dimension(800, 700));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		/**
		 * setup
		 */
		maxTime = getMaxTime();
		minTime = getMinTime(maxTime);
		minVal = getMinVal(minTime, maxTime);
		maxVal = getMaxVal(minTime, maxTime);
		
		if(keepCenter) {
			double max = Math.max(Math.abs(minVal), Math.abs(maxVal));
			minVal = -max;
			maxVal = max;
		}
		double valRange = maxVal- minVal;
		minVal -= ((valRange * padding) / 2);
		maxVal += ((valRange * padding) / 2);
		width = getWidth();
		height = getHeight();
		
		/**
		 * draw Lines
		 */
		g.setColor(COLOR_LINE);
		//x lines
		double lines = (width / lineMargin);
		double timeMargin = (maxTime - minTime) / lines;
		double startTime = ((long) (minTime / timeMargin)) * timeMargin;
		if(!Double.isNaN(startTime)) {
			for (int line = 0; line < lines + 2; line++) {
				Point p = getPoint(startTime + line * timeMargin, 0);
				g.drawLine(p.x, 0, p.x, height);
			}
		}
		//y lines
		lines = (height / lineMargin);
		double valMargin = (maxVal - minVal) / lines;
		double startVal = 0 - (lines / 2) * valMargin;
		if(!Double.isNaN(startVal)) {
			for (int line = 0; line < lines + 2; line++) {
				double val = startVal + line * valMargin;
				Point p = getPoint(0, val);
				g.setColor(Color.black);
				g.drawString(Math.round(val * 100.0) / 100.0 + "", 0, p.y);
				g.setColor(COLOR_LINE);
				g.drawLine(0, p.y, width, p.y);
			}
		}
		/**
		 * draw graphs
		 */
		g.setColor(COLOR_LINE);
		for (Graph graph : graphs) {
			if(!graph.isVisible()) continue;
			Point last = new Point(0, height);
			g.setColor(graph.getColor());
			for (int j = 0; j < graph.getTimes().size() && j < graph.getVals().size(); j++) {
				Double time = graph.getTimes().get(j);
				double val = graph.getVals().get(j);
				if(time < minTime) {
					continue;
				}
				Point p = getPoint(time, val);
				g.drawLine(last.x, last.y, p.x, p.y);
				last = p;
			}
		}
		/**
		 * mouse
		 */
		if(mouse.isMouseInside()) {
			g.setColor(Color.black);
			double[] vals = getValuesAtScreen(mouse.getX(), mouse.getY());
			g.drawString("" + Math.round(vals[1] * 100) / 100.0, mouse.getX() + 5, mouse.getY() - 0);
		}
	}
	
	private double[] getValuesAtScreen(int x, int y) {
		double xProgress = (double) x / width;
		double yProgress = 1 - (double) y / height;
		double timeSpan = maxTime - minTime;
		double valSpan = maxVal - minVal;
		double val = minVal + valSpan * yProgress;
		double time = minTime + timeSpan * xProgress;
		double[] res = {time, val};
		return res;
	}
	
	private Point getPoint(double time, double val) {
		double x = ((time - minTime) / (maxTime - minTime)) * width;
		double y = height - ((val - minVal) / (maxVal - minVal) * height);
		return new Point((int) x, (int) y);
	}
	
	private double getMinTime(double maxTime) {
		if(graphs.size() == 0) return 0;
		double min = graphs.get(0).getMinTime();
		for (Graph graph : graphs) {
			min = Math.min(min, graph.getMinTime());
		}
		/**
		 * clampening
		 */
		double absMax = maxTime - minSpan;
		double absMin = maxTime - maxSpan;
		return Math.max(absMin, Math.min(absMax, min));
//		return min;
	}
	
	private double getMaxTime() {
		if(graphs.size() == 0) return 0;
		double max = graphs.get(0).getMaxTime();
		for (Graph graph : graphs) {
			max = Math.max(max, graph.getMaxTime());
		}
		return max;
	}
	
	private double getMinVal(double minTime, double maxTime) {
		if(graphs.size() == 0) return 0;
		double min = graphs.get(0).getMinVal(minTime, maxTime);
		for (Graph graph : graphs) {
			if(!graph.isVisible()) continue;
			min = Math.min(min, graph.getMinVal(minTime, maxTime));
		}
		return min;
	}
	
	private double getMaxVal(double minTime, double maxTime) {
		if(graphs.size() == 0) return 1;
		double max = graphs.get(0).getMaxVal(minTime, maxTime);
		for (Graph graph : graphs) {
			if(!graph.isVisible()) continue;
			max = Math.max(max, graph.getMaxVal(minTime, maxTime));
		}
		return max;
	}
}