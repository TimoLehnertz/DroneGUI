package gui.elements.plotter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Graph {

	private String label;
	private String description;
	private Color color;
	Map<Double, Double> graph = new HashMap<>();
	List<Double> times = new ArrayList<>();
	List<Double> vals = new ArrayList<>();
	List<Entry<Double, Double>> g = new ArrayList<>();
	private int maxEntries = 1000;
	private int entrySize = 0;
	private boolean visible = true;
	
	private static final Map<String, List<Color>> colors = new HashMap<>();
	static {
//		colors.put("X", Arrays.asList(Color.red, Color.MAGENTA, Color.ORANGE, Color.orange));
//		colors.put("Y", Arrays.asList(Color.green, new Color(100,255, 150), new Color(200,255, 50)));
//		colors.put("Z", Arrays.asList(Color.blue, Color.cyan, new Color(200, 50, 255)));
		colors.put("X", Arrays.asList(Color.red));
		colors.put("Y", Arrays.asList(Color.green));
		colors.put("Z", Arrays.asList(Color.blue));
	}
	
	public Graph(String label) {
		super();
		this.label = label;
		this.color = getColorForName(label);
	}
	
	public void put(double time, double val) {//never write "d"ouble small here
		times.add(time);
		vals.add(val);
		if(times.size() >= maxEntries) {
			times.remove(0);
			vals.remove(0);
		} else {
			entrySize++;
		}
	}
	
	private Color getColorForName(String name) {
		if(name.contains("X")) {
			return colors.get("X").get((int) (Math.random() * colors.get("X").size()));
		}
		if(name.contains("Y")) {
			return colors.get("Y").get((int) (Math.random() * colors.get("Y").size()));
		}
		if(name.contains("Z")) {
			System.out.println((int) (Math.random() * colors.get("Z").size()));
			return colors.get("Z").get((int) (Math.random() * colors.get("Z").size()));
		}
		return getRandomColor();
	}
	
	private Color getRandomColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}
	
	public double getMinTime() {
		if(times.size() == 0) return 0;
		double min = times.get(0);
		if(Double.isNaN(min)) {
			min = 0;
		}
		for (int i = 0; i < entrySize; i++) {
			min = Math.min(times.get(i), min);
		}
		return min;
	}
	
	public double getMaxTime() {
		if(times.size() == 0) return 1;
		double max = times.get(0);
		if(Double.isNaN(max)) {
			max = 0;
		}
		for (int i = 0; i < entrySize; i++) {
			max = Math.max(times.get(i), max);
		}
		return max;
	}
	
	public double getMinVal(double minTime, double maxTime) {
		if(vals.size() == 0) return 0;
		double min = Double.MAX_VALUE;
		for (int i = 0; i < entrySize; i++) {
			try {
				if(times.get(i) >= minTime && times.get(i) <= maxTime) {
					min = Math.min(vals.get(i), min);
				}
			} catch(Exception e) {
				System.err.println("exeption at i=" + i);
			}
		}
		return min;
	}
	
	public double getMaxVal(double minTime, double maxTime) {
		if(vals.size() == 0) return 1;
		double max = Double.MIN_VALUE;
		for (int i = 0; i < entrySize; i++) {
			if(times.get(i) >= minTime && times.get(i) <= maxTime) {
				max = Math.max(vals.get(i), max);
			}
		}
		return max;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void clear() {
//		graph.clear();
		times.clear();
		vals.clear();
	}

	public List<Double> getTimes() {
		return times;
	}

	public List<Double> getVals() {
		return vals;
	}

	public boolean toggle() {
		visible = !visible;
		return visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}