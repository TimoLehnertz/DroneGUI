package gui;

import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

import gui.layout.PropertiesPanel;
import imu.IMU;
import popups.Popup;
import serial.PortInfo;
import serial.Serial;
import serial.SerialInterface;

public class GuiLogic {

	private static GuiLogic instance = new GuiLogic();
	
	List<PropertiesPanel> propertiePanels = new ArrayList<>();
	
	private Frame frame;
	private IMU imu = new IMU();
	private SerialInterface serial = SerialInterface.getInstance();
	private boolean liteMode = false;

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
	
	public void popup(Popup popup, boolean locking) {
		popup(popup, locking, null);
	}
	
//	private boolean popupOpen = false;
	
	public void popup(Popup popup, boolean locking, IntConsumer onclose) {
//		if(popupOpen) return;
//		popupOpen = true;
		popup.setVisible(true);
		popup.setLocationRelativeTo(frame);
		popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(locking) {
			frame.setEnabled(false);
		}
		popup.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				if(locking) {
					frame.setEnabled(true);
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				if(onclose != null) onclose.accept(1);
				popup.close();
//				popupOpen = false;
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
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
	
	public boolean isLiteMode() {
		return liteMode;
	}

	public void setLiteMode(boolean liteMode) {
		this.liteMode = liteMode;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}
}