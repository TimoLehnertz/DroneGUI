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
import imu.IMU;
import imu.IMUChangeListener;
import maths.Vec3;

public class GraphViewer extends JPanel implements IMUChangeListener {

	private static final long serialVersionUID = 1L;

	private GuiLogic logic = GuiLogic.getInstance();
	private IMU imu = logic.getImu();
	private JComboBox<String> combo = new JComboBox<>(PLOTTE_TYPES);
	private JPanel header = new JPanel();
	private JLabel headerLabel = new JLabel("Plotters");
	private List<Plotter> plotters = new ArrayList<>();
	private JPanel plotterPanel = new JPanel();
	private JButton addButton = new JButton("Add");
	
	private static final String ACCELEROMETER = "Accelerometer (unfiltered)";
	private static final String GYROSCOPE = "Gyroscope (unfiltered)";
	private static final String MAGNETOMETER = "Mangetometer (unfiltered)";
	private static final String ACCELEROMETER_FILTERED = "Accelerometer (filtered)";
	private static final String GYROSCOPE_FILTERED = "Gyroscope (filtered)";
	private static final String MAGNETOMETER_FILTERED = "Mangetometer (filtered)";
	private static final String[] PLOTTE_TYPES = {ACCELEROMETER, GYROSCOPE, MAGNETOMETER, ACCELEROMETER_FILTERED, GYROSCOPE_FILTERED, MAGNETOMETER_FILTERED};
	
	public GraphViewer() {
		super();
		imu.addIMUChangeListener(this);
		
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(plotterPanel, BorderLayout.CENTER);
		plotterPanel.setBackground(Color.black);
		plotterPanel.setLayout(new GridLayout(3, 1, 0, 2));
		
		initHeader();
		
//		addPlotter(getPlotterOfType(GYROSCOPE));
//		addPlotter(getPlotterOfType(GYROSCOPE_FILTERED));
//		addPlotter(getPlotterOfType(ACCELEROMETER));
//		addPlotter(getPlotterOfType(ACCELEROMETER_FILTERED));
		addPlotter(getPlotterOfType(MAGNETOMETER));
		addPlotter(getPlotterOfType(MAGNETOMETER_FILTERED));
	}
	
	private void initHeader() {
		/**
		 * combo
		 */
		addButton.addActionListener(e -> {addPlotter(getPlotterOfType((String) combo.getSelectedItem()));});
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
	
	private static Plotter getPlotterOfType(String type) {
		Plotter p = new Plotter();
		p.setName(type);
		p.setRequiredString(type);
		return p;
	}
	
	private void plott(String name, Vec3 vec) {
		plott(name, vec.x, vec.y, vec.z);
	}

	private void plott(String name, double x, double y, double z) {
		for (Plotter plotter : plotters) {
			plotter.plott(name + " X", x);
			plotter.plott(name + " Y", y);
			plotter.plott(name + " Z", z);
		}
	}

	@Override
	public void imuChanged() {
		plott(ACCELEROMETER, imu.getLastRawAccelerometer());
		plott(ACCELEROMETER_FILTERED, imu.getLastFilteredAccelerometer());
		
		plott(GYROSCOPE, imu.getLastRawGyro());
		plott(GYROSCOPE_FILTERED, imu.getLastFilteredGyro());
		
		plott(MAGNETOMETER, imu.getLastRawMagnetometer());
		plott(MAGNETOMETER_FILTERED, imu.getLastFilteredMagnetometer());
	}
}