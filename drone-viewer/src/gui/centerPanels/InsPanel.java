package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;

import gui.GuiLogic;
import gui.elements.FCNumberSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;
import serial.SerialInterface;

public class InsPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	private JButton resetBtn = new JButton("Reset");
	SerialInterface serial = GuiLogic.getInstance().getSerialInterface();
	
	SectionPanel filterPanel = new SectionPanel("Filtering");
	SectionPanel sensorFusion = new SectionPanel("Sensor fusion");
	SectionPanel debugPanel = new SectionPanel("Debug");
	
	public InsPanel() {
		super("Inertial NavigationSystem");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		getBody().setLayout(new GridBagLayout());
		
		FCNumberSetter accLpf = new FCNumberSetter(FCCommand.FC_GET_ACC_LPF, FCCommand.FC_SET_ACC_LPF, "Accel. Lopass filter:");
		FCNumberSetter gyroLpf = new FCNumberSetter(FCCommand.FC_GET_GYRO_LPF, FCCommand.FC_SET_GYRO_LPF, "Gyro. Lopass filter:");
		
		filterPanel.add(accLpf);
		filterPanel.add(gyroLpf);
		
		FCNumberSetter accInf = new FCNumberSetter(FCCommand.FC_GET_COMPLEMENTARY_ACC_INF, FCCommand.FC_SET_COMPLEMENTARY_ACC_INF, "Acc. influence:");
		FCNumberSetter magInf = new FCNumberSetter(FCCommand.FC_GET_COMPLEMENTARY_MAG_INF, FCCommand.FC_SET_COMPLEMENTARY_MAG_INF, "Mag. influence:");
		
//		FCNumberSetter accMaxGError = new FCNumberSetter(FCCommand.FC_GET_INS_ACC_MAX_G, FCCommand.FC_SET_INS_ACC_MAX_G, "Max Acc G Error for rot");
		
		FCNumberSetter sensorFusionSelect = new FCNumberSetter(FCCommand.FC_GET_SENSOR_FUSION, FCCommand.FC_SET_SENSOR_FUSION, "Sensor fusion algorythm(0: Complementary, 1 Magdwick)");
		
		sensorFusion.add(sensorFusionSelect);
		sensorFusion.add(accInf);
		sensorFusion.add(magInf);
//		sensorFusion.add(accMaxGError);
		
		add(resetBtn);
		resetBtn.addActionListener(e -> {
			serial.sendDo(FCCommand.FC_DO_RESET_INS);
		});
		
		debugPanel.add(resetBtn);
		
		getBody().add(filterPanel, gbc);
		gbc.gridy = 1;
		getBody().add(sensorFusion, gbc);
		gbc.gridy = 2;
		getBody().add(debugPanel, gbc);
	}
}