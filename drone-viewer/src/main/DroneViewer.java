package main;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import gui.GuiLogic;
import gui.centerPanels.PropertiesPanel;
import gui.elements.View3D;
import gui.elements.plotter.GraphViewer;
import gui.footer.DroneConsole;
import popups.Sensor3DPanel;
import serial.FCCommand;
import serial.PortInfo;
import serial.SerialInterface;
import xGui.PresetStep;
import xGui.SplitGetter;
import xGui.XButton;
import xGui.XMenuBar;
import xGui.XPanel;
import xPresets.XIdePreset;

public class DroneViewer extends XIdePreset {

	private static final long serialVersionUID = 1L;
	
	GuiLogic logic = GuiLogic.getInstance();
	SerialInterface serial = logic.getSerialInterface();
	
	JComboBox<String> selectCombo;
	private Map<String, String> comboMap = new HashMap<>();
	
	File logFile = null;
	
	public DroneViewer() {
		super("Drone viewer", "drone-logo.png", "Alpha 1.0");
		refrechSerial();
		serial.addLineListener(line -> {
			if(logFile == null) return;
			System.out.println(line);
			try {
				FileWriter myWriter = new FileWriter(logFile, true);
			    myWriter.write(line);
			    myWriter.write("\n");
			    myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public List<SplitGetter> getSplitGetter() {
		return Arrays.asList(new SplitGetter("3D Viewer", 0, null, () -> new View3D()),
				new SplitGetter("Graphs", 0, null, () -> new GraphViewer()),
				new SplitGetter("3D Plotter", 0, null, () -> new Sensor3DPanel()),
				new SplitGetter("Console", 0, null, () -> new DroneConsole()),
				new SplitGetter("Properties", 0, null, () -> new PropertiesPanel()));
	}

	@Override
	public void initHeaderLeftMenuBar(XMenuBar bar) {
		//file
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		quit.setToolTipText("Just don't");
		quit.addActionListener(e -> System.exit(0));
		file.add(quit);
		//settings
		JMenu settings = new JMenu("Settings");
		JCheckBoxMenuItem lightMode = new JCheckBoxMenuItem("Lite mode");
		lightMode.setToolTipText("Only request settings if Reset is pressed. Usefull when dealing with limited bandwidth");
		lightMode.addActionListener(e -> logic.setLiteMode(lightMode.getState()));
		settings.add(lightMode);
		//drone
		JMenu tools = new JMenu("Tools");
		JMenuItem reboot = new JMenuItem("Reboot");
		reboot.setToolTipText("Reboot drone if connected");
		reboot.addActionListener(e -> serial.sendDo(FCCommand.FC_DO_REBOOT));
		JMenuItem writeEEPROM = new JMenuItem("Save settings");
		writeEEPROM.addActionListener(e -> serial.sendDo(FCCommand.FC_DO_SAVE_EEPROM));
		writeEEPROM.setToolTipText("Save all changes made to the internal EEPROM (Don't overdo as eeprom has limited lifecycle)");
		writeEEPROM.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
		JMenuItem eraseEEPROM = new JMenuItem("Factory reset");
		eraseEEPROM.setToolTipText("Write defaults into EEPROM (Settings remain until reboot)");
		eraseEEPROM.addActionListener(e -> serial.sendDo(FCCommand.FC_DO_ERASE_EEPROM));
		tools.add(reboot);
		tools.add(writeEEPROM);
		tools.add(eraseEEPROM);
		
		bar.add(file);
		bar.add(settings);
		bar.add(tools);
	}

	@Override
	public void initHeaderRightContent(XPanel panel) {
		XButton record = new XButton("Rocord");
		record.setToolTipText("Record incoming Serial data to File");
		comboMap = new HashMap<>();
		selectCombo = new JComboBox<>();
		comboMap.put("Select", "");
		XButton refreshButton = new XButton(XButton.STYLE_FOREGROUND, "refresh.png", 15, 15);
		XButton disconnectButton = new XButton(XButton.STYLE_FOREGROUND, "disconnect.png", 15, 15);
		panel.add(selectCombo);
		panel.add(refreshButton);
		panel.add(disconnectButton);
		panel.add(record);
		record.addActionListener(e -> {
			if(logFile == null) {				
				record.setText("Stop recording");
				record();
			} else {
				logFile = null;
				record.setText("Record");
			}
		});
		selectCombo.addActionListener(e -> {
			if(selectCombo.getSelectedIndex() > 0) {
				GuiLogic.getInstance().getSerialInterface().selectComPort(comboMap.get((String) selectCombo.getSelectedItem()));
			}
		});
		disconnectButton.addActionListener(e -> serial.disconnectSerial());
		refreshButton.addActionListener(e -> refrechSerial());
	}

	@Override
	public PresetStep getPresetSteps() {
		return 	new PresetStep(":)", 0.5, PresetStep.X, new PresetStep("Properties", 0.7, PresetStep.Y, null, new PresetStep("Console", 0.7, PresetStep.Y, null, new PresetStep("Console", 0.4, PresetStep.X))),
				new PresetStep("3D Viewer", 0.6, PresetStep.X, null,
				new PresetStep("Graphs", 0.4, PresetStep.Y)));
	}
	
	private void refrechSerial() {
		selectCombo.removeAllItems();
		selectCombo.addItem("Select COM port");
		comboMap = new HashMap<>();
		for (PortInfo port : logic.getComPorts()) {
			String displayName = port.getPortDescription() + " at " + port.getSystemPortName();
			comboMap.put(displayName, port.getSystemPortName());
			selectCombo.addItem(displayName);
		}
		selectCombo.setSelectedIndex(0);
	}
	
	private void record() {
		int i = 0;
		try {
			while(true) {
				logFile = new File("log" + i + ".txt");
				if (logFile.createNewFile()) {
					System.out.println("File created: " + logFile.getName());
					break;
				}
				i++;
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new DroneViewer();
//		double now = 0;
//		double val = 1;
//		double lpf = 0.005;
//		int i = 0;
//		while (val > 0.1) {
//			val = val * (1-lpf) + now * lpf;
//			i++;
//		}
//		System.out.println(i);
	}
}