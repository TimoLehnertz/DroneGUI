package gui.centerPanels;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Timer;

import gui.GuiLogic;
import gui.elements.FCBooleanSetter;
import gui.elements.FCNumberSetter;
import gui.elements.FCSetter;
import gui.elements.FCVec3Setter;
import gui.elements.SectionPanel;
import serial.FCCommand;
import serial.SerialInterface;
import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;
import xGui.XSpinner;

public class SensorPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	GuiLogic logic = GuiLogic.getInstance();
	
	SerialInterface serial = logic.getSerialInterface();

	SectionPanel gyro = new SectionPanel("Gyroscope");
	SectionPanel acc  = new SectionPanel("Accelerometer");
	SectionPanel mag  = new SectionPanel("Magnetometer");
	SectionPanel baro = new SectionPanel("Barometer");
	SectionPanel gps  = new SectionPanel("GPS");
	SectionPanel bat  = new SectionPanel("Battery");
	
	SectionPanel telemSection = new SectionPanel("Telemetry");
	
	FCBooleanSetter accTelem 	= new FCBooleanSetter(FCCommand.FC_GET_USE_ACC_TELEM, FCCommand.FC_SET_ACC_TELEM, "Accelerometer");
	FCBooleanSetter gyroTelem	= new FCBooleanSetter(FCCommand.FC_GET_USE_GYRO_TELEM, FCCommand.FC_SET_GYRO_TELEM, "Gyroscope");
	FCBooleanSetter magTelem 	= new FCBooleanSetter(FCCommand.FC_GET_USE_MAG_TELEM, FCCommand.FC_SET_MAG_TELEM, "Magnetometer");
	FCBooleanSetter baroTelem	= new FCBooleanSetter(FCCommand.FC_GET_USE_BARO_TELEM, FCCommand.FC_SET_BARO_TELEM, "Barometer");
	FCBooleanSetter gpsTelem 	= new FCBooleanSetter(FCCommand.FC_GET_USE_GPS_TELEM, FCCommand.FC_SET_GPS_TELEM, "GPS");
	FCBooleanSetter attiTelem 	= new FCBooleanSetter(FCCommand.FC_GET_USE_ATTI_TELEM, FCCommand.FC_SET_ATTI_TELEM, "Attitude");
	FCBooleanSetter quatTelem 	= new FCBooleanSetter(FCCommand.FC_GET_USE_QUAT_TELEM, FCCommand.FC_SET_QUAT_TELEM, "Quaternion Rot");
	FCBooleanSetter velTelem 	= new FCBooleanSetter(FCCommand.FC_GET_USE_VEL_TELEM, FCCommand.FC_SET_VEL_TELEM, "Velocity");
	FCBooleanSetter locTelem 	= new FCBooleanSetter(FCCommand.FC_GET_USE_LOC_TELEM, FCCommand.FC_SET_LOC_TELEM, "Location");
	FCBooleanSetter timingTelem	= new FCBooleanSetter(FCCommand.FC_GET_USE_TIMING, FCCommand.FC_SET_USE_TIMING, "Timing");
	FCBooleanSetter rcTelem	   	= new FCBooleanSetter(FCCommand.FC_GET_USE_RC_TELEM, FCCommand.FC_SET_USE_RC_TELEM, "RC chanels");
	FCBooleanSetter fcTelem	   	= new FCBooleanSetter(FCCommand.FC_GET_USE_FC_TELEM, FCCommand.FC_SET_USE_FC_TELEM, "FC");
	FCBooleanSetter batTelem   	= new FCBooleanSetter(FCCommand.FC_GET_USE_BAT_TELEM, FCCommand.FC_SET_BAT_TELEM, "Bat");
	FCBooleanSetter ultrasonicTelem= new FCBooleanSetter(FCCommand.FC_GET_USE_ULTRASONIC_TELEM, FCCommand.FC_SET_USE_ULTRASONIC_TELEM, "Ultrasonic");
	
	FCNumberSetter batLpf   	= new FCNumberSetter(FCCommand.FC_GET_BAT_LPF, FCCommand.FC_SET_BAT_LPF, "Bat low pass Filter");
	FCBooleanSetter useBatCell 	= new FCBooleanSetter(FCCommand.FC_GET_USE_VCELL, FCCommand.FC_SET_USE_VCELL, "Report cell voltage");
	
	FCNumberSetter magZOffset   = new FCNumberSetter(FCCommand.FC_GET_MAG_Z_OFFSET, FCCommand.FC_SET_MAG_Z_OFFSET, "Z offset(deg)");
		
	XButton calibrateAccBtn = new XButton("Calibrate");
	XButton calibrateGyroOffsetBtn = new XButton("Calibrate Offset");
	XButton calibrateGyroScaleBtn = new XButton("Calibrate Scale");
	XButton calibrateMagBtn = new XButton("Calibrate");
	
	XLabel batInfoLabel = new XLabel("Enter actual battery voltage and hit Calibrate");
	XSpinner batCalibSpinner = FCSetter.getSpinner();
	XButton batCalibBtn = new XButton("Calibrate");
	
	FCVec3Setter accOffset;
	FCVec3Setter gyroOffset;
	FCVec3Setter gyroScale;
	FCVec3Setter gyroMul;
	FCVec3Setter magHardIron;
	FCVec3Setter magSoftIron;
	
	
	public SensorPanel() {
		super();
		
		serial.addOpenListeners(() -> setEnabled(true));
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		getBody().setLayout(new GridBagLayout());
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 100;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		getBody().add(acc, gbc);
		gbc.gridy = 1;
		getBody().add(gyro, gbc);
		gbc.gridy = 2;
		getBody().add(mag, gbc);
		gbc.gridy = 3;
//		getBody().add(baro, gbc);
//		gbc.gridy = 4;
//		getBody().add(gps, gbc);
		gbc.gridy = 5;
		getBody().add(bat, gbc);
		gbc.gridy = 6;
		getBody().add(telemSection, gbc);
		
		accOffset = new FCVec3Setter(FCCommand.FC_GET_ACC_OFFSET, FCCommand.FC_SET_ACC_OFFSET, "Offset");
		XPanel calibAccPanel = new XPanel(new FlowLayout(FlowLayout.LEFT));
		
		calibAccPanel.add(accOffset);
		calibAccPanel.add(calibrateAccBtn);
		calibrateAccBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_ACC_CALIB_QUICK); accOffset.get();});
		
		gyroOffset = new FCVec3Setter(FCCommand.FC_GET_GYRO_OFFSET, FCCommand.FC_SET_GYRO_OFFSET, "Offset");
		gyroScale = new FCVec3Setter(FCCommand.FC_GET_GYRO_SCALE, FCCommand.FC_SET_GYRO_SCALE, "Scale");
		XPanel calibGyroPanel = new XPanel();
		calibGyroPanel.add(calibrateGyroOffsetBtn);
		calibrateGyroOffsetBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_GYRO_CALIB_OFFSET); refreshGyroOffset();});
//		calibGyroPanel.add(new XLabel("<html>Let drone rest on<br>the ground for 2 seconds</html>"));
		calibGyroPanel.add(calibrateGyroScaleBtn);
		calibrateGyroScaleBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_GYRO_CALIB_SCALE); refreshGyroScale();});
		
		magHardIron = new FCVec3Setter(FCCommand.FC_GET_MAG_OFFSET, FCCommand.FC_SET_MAG_OFFSET, "Hard iron offset");
		magSoftIron = new FCVec3Setter(FCCommand.FC_GET_MAG_SCALE, FCCommand.FC_SET_MAG_SCALE, "Soft Iron scale factor");
		XPanel calibMagPanel = new XPanel();
		calibMagPanel.add(calibrateMagBtn);
		calibMagPanel.add(magZOffset);
//		calibrateMagBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_MAG_CALIB); logic.popup(new Sensor3DFrame("MAG", true), false);});

		
		/**
		 * Bat
		 */
		gbc.gridy = 0;
		gbc.gridx = 0;
		
		bat.getBody().setLayout(new GridBagLayout());
		bat.getBody().add(batInfoLabel, gbc);
		gbc.gridx = 1;
		bat.getBody().add(batCalibSpinner, gbc);
		gbc.gridx = 2;
		bat.getBody().add(batCalibBtn, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		bat.getBody().add(batLpf, gbc);
		gbc.gridx = 1;
		bat.getBody().add(useBatCell, gbc);
		
		batCalibSpinner.setValue(15);
		batCalibBtn.addActionListener(e -> {
			double voltage = (double) batCalibSpinner.getValue();
			System.out.println(voltage);
			serial.set(FCCommand.FC_SET_VOLTAGE_CALIB, voltage, s -> {
				if(s) {
					System.out.println("Succsessfully calibrated vBat");
				}
			});
		});
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.ipadx = 5;
		gbc.ipady = 15;
		acc.getBody().setLayout(new GridBagLayout());
		gyro.getBody().setLayout(new GridBagLayout());
		mag.getBody().setLayout(new GridBagLayout());
		
		acc.getBody().add(accOffset, gbc);
		gbc.gridx = 1;
		acc.getBody().add(calibAccPanel, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gyro.getBody().add(gyroOffset, gbc);
		gbc.gridx = 1;
		gyro.getBody().add(gyroScale, gbc);
		gbc.gridx = 2;
		gyro.getBody().add(calibGyroPanel, gbc);
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		mag.getBody().add(magHardIron, gbc);
		gbc.gridx = 1;
		mag.getBody().add(magSoftIron, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		mag.getBody().add(calibMagPanel, gbc);
		gbc.gridwidth = 1;
		
		telemSection.getBody().setLayout(new GridLayout(5, 3));
		gbc.gridx = 0;
		gbc.gridy = 0;
		telemSection.getBody().add(accTelem);
		gbc.gridy = 1;
		telemSection.getBody().add(gyroTelem);
		gbc.gridy = 2;
		telemSection.getBody().add(magTelem);
		gbc.gridy = 0;
		gbc.gridx = 1;
		telemSection.getBody().add(baroTelem);
		gbc.gridy = 1;
		telemSection.getBody().add(gpsTelem);
		gbc.gridy = 2;
		telemSection.getBody().add(attiTelem);
		gbc.gridx = 2;
		gbc.gridy = 0;
		telemSection.getBody().add(quatTelem);
		gbc.gridy = 1;
		telemSection.getBody().add(velTelem);
		gbc.gridy = 2;
		telemSection.getBody().add(locTelem);
		gbc.gridx = 3;
		gbc.gridy = 0;
		telemSection.getBody().add(timingTelem);
		gbc.gridy = 1;
		telemSection.getBody().add(rcTelem);
		gbc.gridy = 2;
		telemSection.getBody().add(fcTelem);
		gbc.gridx = 0;
		gbc.gridy = 3;
		telemSection.getBody().add(batTelem);
		gbc.gridx = 1;
		gbc.gridy = 3;
		telemSection.getBody().add(ultrasonicTelem);
		
		
		setEnabled(false);
	}
	
	private void refreshGyroOffset() {
		Timer t = new Timer(100, e -> {
			gyroOffset.refresh();
			((Timer) e.getSource()).stop();
		});
		t.start();
	}
	
	private void refreshGyroScale() {
		Timer t = new Timer(100, e -> {
			gyroScale.refresh();
			((Timer) e.getSource()).stop();
		});
		t.start();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		calibrateAccBtn.setEnabled(enabled);
		calibrateGyroOffsetBtn.setEnabled(enabled);
		calibrateGyroScaleBtn.setEnabled(enabled);
		calibrateMagBtn.setEnabled(enabled);
		batCalibBtn.setEnabled(enabled);
		batCalibSpinner.setEnabled(enabled);
	}
}