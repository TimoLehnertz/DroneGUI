package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import gui.layout.PropertiesPanel;
import imu.IMU;
import serial.PortInfo;
import serial.Serial;
import serial.SerialInterface;

public class GuiLogic {

	private static GuiLogic instance = new GuiLogic();
	
	List<PropertiesPanel> propertiePanels = new ArrayList<>();
	
	private IMU imu = new IMU();
	private SerialInterface serial = SerialInterface.getInstance();
	
	private GuiLogic() {
		super();
	}

	public static GuiLogic getInstance() {
		return instance;
	}
	
	public List<PortInfo> getComPorts() {
		return Serial.getSerialPorts();
	}
	
	public ImageIcon getImage(String name) {
		return getImage(name, -1, -1);
	}
	
	public void selectComPort(String port) {
		SerialInterface.getInstance().selectComPort(port);
	}
	
	public ImageIcon getImage(String name, int width, int height) {
		if(!name.contains("img")) {
			name = "img/" + name;
		}
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(name));
		} catch (IOException e) {
		    e.printStackTrace();
		    return new ImageIcon();
		}
		if(width < 0) {
			width = img.getWidth();
			height = img.getHeight();
		}
		Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(dimg);;
	    return icon;
	}

	public IMU getImu() {
		return imu;
	}
	
	public boolean registerPropertiePanel(PropertiesPanel p) {
		return propertiePanels.add(p);
	}

	public void loadCenter(JComponent properties) {
		for (PropertiesPanel panel : propertiePanels) {
			panel.load(properties);
		}
	}

	public void disconnectSerial() {
		serial.disconnectSerial();
	}

	public SerialInterface getSerialInterface() {
		return serial;
	}
}