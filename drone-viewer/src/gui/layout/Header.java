package gui.layout;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import gui.Cons;
import gui.GuiLogic;
import gui.elements.ImageButton;
import serial.FCCommand;
import serial.PortInfo;

public class Header extends JPanel {

	private static final long serialVersionUID = 1L;

	GuiLogic logic = GuiLogic.getInstance();
	
	private JLabel title = new JLabel("Drone viewer");
	
	private JPanel selectPanel = new JPanel();
	private JLabel selectLabel = new JLabel("Select drone:");
	private JComboBox<String> selectCombo = new JComboBox<>();
	private JButton refreshButton = new ImageButton("refresh.png", 30, 30);
	private JButton disconnectButton = new JButton("Disconnect");
	private JButton startButton = new JButton("Start Atti Telem");
	private JButton stopButton = new JButton("Stop Atti Telemetry");
	private JButton saveButton = new JButton("Write to EEPROM");
	private JButton ereaseButton = new JButton("Erease to EEPROM");
	private JCheckBox liteCheckBox = new JCheckBox("Lite mode");
	private Map<String, String> comboMap = new HashMap<>();
	
	public Header() {
		super();
		/**
		 * title
		 */
		title.setFont(Cons.FONT_H1);
		add(title);
		
		setBackground(Color.LIGHT_GRAY);
		
		/**
		 * select
		 */
		refrechSerial();
		refreshButton.addActionListener(e -> refrechSerial());
		
		disconnectButton.addActionListener(e -> disconnect());
		
		selectCombo.addActionListener(e -> serialChanged());
		
		startButton.addActionListener(e -> logic.getSerialInterface().startTelem());
		stopButton.addActionListener(e -> logic.getSerialInterface().stopTelem());
		saveButton.addActionListener(e -> logic.getSerialInterface().sendDo(FCCommand.FC_DO_SAVE_EEPROM));
		ereaseButton.addActionListener(e -> logic.getSerialInterface().sendDo(FCCommand.FC_DO_ERASE_EEPROM));
		
		selectPanel.add(selectLabel);
		selectPanel.add(selectCombo);
		selectPanel.add(refreshButton);
		selectPanel.add(disconnectButton);
		selectCombo.setSelectedIndex(-1);
		
		add(selectPanel);
		add(startButton);
		add(stopButton);
		add(saveButton);
		add(ereaseButton);
		add(liteCheckBox);
		liteCheckBox.addChangeListener(e -> logic.setLiteMode(liteCheckBox.isSelected()));
//		selectCom("Nano 33 BLE");
		new Timer(300, e -> {selectCom("Nano 33 BLE"); ((Timer) e.getSource()).stop();}).start();
	}
	
	private void disconnect() {
		logic.disconnectSerial();
		selectCombo.setSelectedIndex(-1);
	}
	
	private void selectCom(String name) {
		for (int i = 0; i < selectCombo.getItemCount(); i++) {
			String item = selectCombo.getItemAt(i);
			if(item.contains(name)) {
				selectCombo.setSelectedIndex(i);
				logic.selectComPort(name);
				break;
			}
		}
	}

	private void serialChanged() {
		String description = (String) selectCombo.getSelectedItem();
		if(description == null) {
			return;
		}
		String port = comboMap.get(description);
		logic.selectComPort(port);
	}
		
	private void refrechSerial() {
		selectCombo.removeAllItems();
		comboMap = new HashMap<>();
		for (PortInfo port : logic.getComPorts()) {
			String displayName = port.getPortDescription() + " at " + port.getSystemPortName();
			comboMap.put(displayName, port.getSystemPortName());
			selectCombo.addItem(displayName);
		}
	}
}
