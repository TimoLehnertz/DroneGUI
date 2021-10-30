package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.Timer;

import gui.GuiLogic;
import gui.elements.FCBooleanSetter;
import gui.elements.FCNumberSetter;
import gui.elements.FCSetter;
import gui.elements.FCVec3Setter;
import gui.elements.SectionPanel;
import serial.FCCommand;
import serial.SerialInterface;

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
	
	FCNumberSetter batLpf   	= new FCNumberSetter(FCCommand.FC_GET_BAT_LPF, FCCommand.FC_SET_BAT_LPF, "Bat low pass Filter");
	FCNumberSetter accSideCalib = new FCNumberSetter(FCCommand.FC_GET_0, FCCommand.FC_SET_ACC_CALIB_SIDE, "Side calibration", true);
	FCBooleanSetter useBatCell 	= new FCBooleanSetter(FCCommand.FC_GET_USE_VCELL, FCCommand.FC_SET_USE_VCELL, "Report cell voltage");
		
	JButton calibrateAccBtn = new JButton("Quick calibration");
//	JButton calibrateAccBtn = new JButton("Quick calibration");
	JLabel accCalibLabel = new JLabel("Side calib: 0: Bottom, 1: left, 2: right, 3 front, 4: back, 5 top");
	JButton calibrateAccBtnAdvanced = new JButton("Advanced calibration");
//	JButton accAngleBtn = new JButton("Set acc angle offset");
	JButton calibrateGyroBtn = new JButton("Calibrate");
	JButton calibrateMagBtn = new JButton("Calibrate");
	
	JLabel batInfoLabel = new JLabel("Enter actual battery voltage and hit Calibrate");
	JSpinner batCalibSpinner = FCSetter.getSpinner();
	JButton batCalibBtn = new JButton("Calibrate");
	
	FCVec3Setter accOffset;
	FCVec3Setter accMul;
//	FCMat3Setter accMul;
//	FCQuaternionSetter accAngleOffset;
	FCVec3Setter gyroOffset;
	FCVec3Setter gyroMul;
	FCVec3Setter magHardIron;
//	FCMat3Setter magSoftIron;
	FCVec3Setter magSoftIron;
	
	
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
		gbc.gridy = 5;
		getBody().add(bat, gbc);
		gbc.gridy = 6;
		getBody().add(telemSection, gbc);
		
		accOffset = new FCVec3Setter(FCCommand.FC_GET_ACC_OFFSET, FCCommand.FC_SET_ACC_OFFSET, "Offset");
//		accMul = new FCMat3Setter(FCCommand.FC_GET_ACC_MUL, FCCommand.FC_SET_ACC_MUL, "Multiplicator");
		accMul = new FCVec3Setter(FCCommand.FC_GET_ACC_SCALE, FCCommand.FC_SET_ACC_SCALE, "Multiplicator");
//		accAngleOffset = new FCQuaternionSetter(FCCommand.FC_GET_ACC_ANGLE_OFFSET, FCCommand.FC_SET_ACC_ANGLE_OFFSET, "Quaternion Angle offset");
		JPanel calibAccPanel = new JPanel();
		calibAccPanel.add(calibrateAccBtn);
		calibrateAccBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_ACC_CALIB_QUICK); });//refreshAccOffset();});
		calibAccPanel.add(calibrateAccBtnAdvanced);
//		calibAccPanel.add(accAngleBtn);
		calibAccPanel.add(accSideCalib);
//		accAngleBtn.addActionListener(e -> {serial.sendDo(FCCommand.FC_DO_SET_ACC_ANGLE_OFFSET); accAngleOffset.get(100);});
//		calibrateAccBtnAdvanced.addActionListener((e) -> logic.popup(new AccCalibFrame(), true));
		calibrateAccBtnAdvanced.addActionListener((e) -> serial.sendDo(FCCommand.FC_DO_ACC_CALIB));
		
		gyroOffset = new FCVec3Setter(FCCommand.FC_GET_GYRO_OFFSET, FCCommand.FC_SET_GYRO_OFFSET, "Offset");
//		gyroMul = new FCVec3Setter(FCCommand.FC_GET_GYRO_MUL, FCCommand.FC_SET_GYRO_MUL, "Multiplicator");
		JPanel calibGyroPanel = new JPanel();
		calibGyroPanel.add(calibrateGyroBtn);
		calibrateGyroBtn.addActionListener((e) -> {serial.sendDo(FCCommand.FC_DO_GYRO_CALIB); refreshGyroOffset();});
		calibGyroPanel.add(new JLabel("<html>The drone has to experience<br>no vibrations or<br>changes in rotation one<br>second before calibration</html>"));
		
		magHardIron = new FCVec3Setter(FCCommand.FC_GET_MAG_OFFSET, FCCommand.FC_SET_MAG_OFFSET, "Hard iron offset");
//		magSoftIron = new FCMat3Setter(FCCommand.FC_GET_MAG_SOFT_IRON, FCCommand.FC_SET_MAG_SOFT_IRON, "Soft Iron scale factor");
		magSoftIron = new FCVec3Setter(FCCommand.FC_GET_MAG_SCALE, FCCommand.FC_SET_MAG_SCALE, "Soft Iron scale factor");
		JPanel calibMagPanel = new JPanel();
		calibMagPanel.add(calibrateMagBtn);
//		calibrateMagBtn.addActionListener((e) -> logic.popup(new MagCalibFrame(), true));
		calibrateMagBtn.addActionListener((e) -> serial.sendDo(FCCommand.FC_DO_MAG_CALIB));
//		calibMagPanel.add(new JLabel("<html>Open magnetometer calibration</html>"));
		
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
		acc.getBody().add(accMul, gbc);
		gbc.gridx = 2;
//		acc.getBody().add(accAngleOffset, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		acc.getBody().add(calibAccPanel, gbc);
		gbc.gridy = 2;
		acc.getBody().add(accCalibLabel, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy = 0;
		gyro.getBody().add(gyroOffset, gbc);
		gbc.gridx = 1;
//		gyro.getBody().add(gyroMul, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gyro.getBody().add(calibGyroPanel, gbc);
		gbc.gridwidth = 1;
		
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
		
		setEnabled(false);
	}
	
//	private void refreshAccOffset() {
//		Timer t = new Timer(100, e -> {
//			accOffset.refresh();
//			((Timer) e.getSource()).stop();
//		});
//		t.start();
//	}
	
	private void refreshGyroOffset() {
		Timer t = new Timer(100, e -> {
			gyroOffset.refresh();
			((Timer) e.getSource()).stop();
		});
		t.start();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		calibrateAccBtn.setEnabled(enabled);
		calibrateAccBtnAdvanced.setEnabled(enabled);
//		accAngleBtn.setEnabled(enabled);
		calibrateGyroBtn.setEnabled(enabled);
		calibrateMagBtn.setEnabled(enabled);
		batCalibBtn.setEnabled(enabled);
		batCalibSpinner.setEnabled(enabled);
	}
}