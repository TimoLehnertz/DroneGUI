package gui.properties;

import javax.swing.JButton;
import javax.swing.JPanel;

import imu.IMU;

public class IMUPropertiesPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private IMU imu;
	
	/**
	 * GUI
	 */
	private JButton calibrateButton = new JButton("Calibrate");
	
	public IMUPropertiesPanel(IMU imu) {
		super();
		this.imu = imu;
		calibrateButton.addActionListener(e -> calibrate());
		add(calibrateButton);
	}
	
	private void calibrate() {
		imu.calibrate();
	}
}