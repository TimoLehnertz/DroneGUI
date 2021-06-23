package gui.centerPanels;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.GuiLogic;
import serial.FCCommand;
import serial.SerialInterface;

public class InsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton test = new JButton("test");
	private JButton calibAccBtn = new JButton("Calibrate Accelerometer");
	private JButton calibGyroBtn = new JButton("Calibrate Gyroscope");
	
	GuiLogic logic = GuiLogic.getInstance();
	
	SerialInterface serial = logic.getSerialInterface();
	
	public InsPanel() {
		super();
		
		add(test);
		add(calibAccBtn);
		add(calibGyroBtn);
		calibAccBtn.addActionListener(e -> {
			serial.sendDo(FCCommand.FC_DO_ACC_CALIB);
		});
		calibGyroBtn.addActionListener(e -> {
			serial.sendDo(FCCommand.FC_DO_GYRO_CALIB);
		});
		test.addActionListener(e -> {
			serial.post(FCCommand.FC_POST_TEST1, "123");
		});
	}
}