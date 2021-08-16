package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import gui.GuiLogic;
import gui.elements.FCNumberSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;
import serial.SerialInterface;

public class SensorPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	SerialInterface serial = GuiLogic.getInstance().getSerialInterface();

	SectionPanel gyro = new SectionPanel("Gyroscope");
	SectionPanel acc = new SectionPanel("Accelerometer");
	SectionPanel mag = new SectionPanel("Magnetometer");
	SectionPanel baro = new SectionPanel("Barometer");
	SectionPanel gps = new SectionPanel("GPS");
	
	JButton calibrateAccBtn = new JButton("Calibrate");
	JButton calibrateGyroBtn = new JButton("Calibrate");
	JButton calibrateMagBtn = new JButton("Calibrate");
	
	FCNumberSetter accOffset;
	FCNumberSetter accMul;
	FCNumberSetter gyroOffset;
	FCNumberSetter gyroMul;
	FCNumberSetter magOffset;
	FCNumberSetter magMul;
	
	public SensorPanel() {
		super("Sensors");
		
		serial.addOpenListeners(() -> setEnabled(true));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		getBody().setLayout(new GridBagLayout());
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		getBody().add(acc, gbc);
		gbc.gridy = 1;
		getBody().add(gyro, gbc);
		gbc.gridy = 2;
		getBody().add(mag, gbc);
		gbc.gridy = 3;
		getBody().add(baro, gbc);
		gbc.gridy = 4;
		getBody().add(gps, gbc);
		
		accOffset = new FCNumberSetter(FCCommand.FC_GET_ACC_OFFSET, FCCommand.FC_SET_ACC_OFFSET, "Offset");
		accMul = new FCNumberSetter(FCCommand.FC_GET_ACC_MUL, FCCommand.FC_SET_ACC_MUL, "Multiplicator");
		JPanel calibAccPanel = new JPanel();
		calibAccPanel.add(calibrateAccBtn);
		calibrateAccBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_ACC_CALIB); refreshAccOffset();});
		calibAccPanel.add(new JLabel("<html>For calibration place the drone<br><b>level</b> on the ground and<br><b>dont have it moving</b> a second<br>before the calibration</html>"));
		
		gyroOffset = new FCNumberSetter(FCCommand.FC_GET_GYRO_OFFSET, FCCommand.FC_SET_GYRO_OFFSET, "Offset");
		gyroMul = new FCNumberSetter(FCCommand.FC_GET_GYRO_MUL, FCCommand.FC_SET_GYRO_MUL, "Multiplicator");
		JPanel calibGyroPanel = new JPanel();
		calibGyroPanel.add(calibrateGyroBtn);
		calibrateGyroBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_GYRO_CALIB); refreshGyroOffset();});
		calibGyroPanel.add(new JLabel("<html>The drone has to experience<br>no vibrations or<br>changes in rotation one<br>second before calibration</html>"));
		
		magOffset = new FCNumberSetter(FCCommand.FC_GET_MAG_OFFSET, FCCommand.FC_SET_MAG_OFFSET, "Offset");
		magMul = new FCNumberSetter(FCCommand.FC_GET_MAG_MUL, FCCommand.FC_SET_MAG_MUL, "Multiplicator");
		JPanel calibMagPanel = new JPanel();
		calibMagPanel.add(calibrateMagBtn);
		calibrateMagBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_MAG_CALIB); refreshMagOffset(); });
		calibMagPanel.add(new JLabel("<html>Moin</html>"));
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.ipadx = 5;
		gbc.ipady = 15;
		acc.getBody().setLayout(new GridBagLayout());
		gyro.getBody().setLayout(new GridBagLayout());
		mag.getBody().setLayout(new GridBagLayout());
		
		acc.getBody().add(accOffset, gbc);
		gbc.gridx = 1;
		acc.getBody().add(accMul, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		acc.getBody().add(calibAccPanel, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy = 0;
		gyro.getBody().add(gyroOffset, gbc);
		gbc.gridx = 1;
		gyro.getBody().add(gyroMul, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gyro.getBody().add(calibGyroPanel, gbc);
		gbc.gridwidth = 1;
		
		gbc.gridx = 0;
		mag.getBody().add(magOffset, gbc);
		gbc.gridx = 1;
		mag.getBody().add(magMul, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		mag.getBody().add(calibMagPanel, gbc);
		
		setEnabled(false);
	}
	
	private void refreshAccOffset() {
		Timer t = new Timer(100, e -> {
			accOffset.refresh();
			((Timer) e.getSource()).stop();
		});
		t.start();
	}
	
	private void refreshGyroOffset() {
		Timer t = new Timer(100, e -> {
			gyroOffset.refresh();
			((Timer) e.getSource()).stop();
		});
		t.start();
	}
	
	private void refreshMagOffset() {
		Timer t = new Timer(100, e -> {
			magOffset.refresh();
			((Timer) e.getSource()).stop();
		});
		t.start();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		calibrateAccBtn.setEnabled(enabled);
		calibrateGyroBtn.setEnabled(enabled);
		calibrateMagBtn.setEnabled(enabled);
		super.setEnabled(enabled);
	}
}