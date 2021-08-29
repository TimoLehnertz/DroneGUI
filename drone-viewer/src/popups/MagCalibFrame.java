package popups;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.GuiLogic;
import gui.elements.SectionPanel;
import maths.Vec3;
import objects.Cube;
import objects.MyObject;
import objects.PointLight;
import renderer.Canvas3D;
import scene.Scene;
import serial.FCCommand;
import serial.SensorListener;
import serial.SerialInterface;

public class MagCalibFrame extends JFrame implements SensorListener{

	private static final long serialVersionUID = 1L;
	SectionPanel body = new SectionPanel("Magnetometer Calibration");
	JButton startBtn = new JButton("Start calibration");
	JButton stopBtn = new JButton("Stop calibration");
	GuiLogic logic = GuiLogic.getInstance();
	SerialInterface serial = logic.getSerialInterface();
	JLabel infoLabel = new JLabel("Press start to calibrate");
	Vec3 vecBuff = new Vec3();
	JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "\\Desktop");
	private String sensor = "MAG";
	JCheckBox filteredCheck = new JCheckBox("Use filtered");
	private Canvas3D canvas = new Canvas3D(false);
	private Scene scene = canvas.getActiveScene();
	private JButton clearBtn = new JButton("Clear");
	private Vec3 lastVec = new Vec3();
	private boolean running = true;

	public MagCalibFrame() {
		super("Magnetometer Calibration");
		setResizable(false);
		setBounds(0, 0, 1000, 900);
		add(body);
		stopBtn.setVisible(true);
		body.getBody().add(startBtn);
		body.getBody().add(stopBtn);
		body.getBody().add(infoLabel);
		body.getBody().add(fileChooser);
		body.getBody().add(filteredCheck);
		filteredCheck.addChangeListener(e -> {
			sensor = filteredCheck.isSelected() ? "MAG(f)" : "MAG";
		});
		startBtn.addActionListener(e -> start());
		stopBtn.addActionListener(e -> stop());
		serial.addSensorListener(this);
		canvas.setPreferredSize(new Dimension(400, 400));
		body.getBody().add(canvas);
		body.getBody().add(clearBtn);
		clearBtn.addActionListener(e -> {
			List<MyObject> removals = new ArrayList<>();
			for (int i = 0; i < scene.getObjects().size(); i++) {
				MyObject o = scene.getObjects().get(i);
				if(o.getScale().x == 0.05) {
					removals.add(o);
				}
			}
			canvas.getScenes().get(0).getObjects().removeAll(removals);
		});
		initScene();
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				running = false;
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
	}
	
	private void initScene() {
		canvas.getScenes().get(0).setUseMist(false);
		canvas.start();
		Cube defaultCube = new Cube();
		defaultCube.setScale(0.3);
		canvas.addObject(defaultCube);
		PointLight p = new PointLight(10, 10, 10);
		p.setColor(new Color(100, 100, 255));
		canvas.addObject(p);
		PointLight p2 = new PointLight(-10, 10, 10);
		canvas.addObject(p2);
		p2.setColor(new Color(255, 100, 100));
		
		PointLight p3 = new PointLight(-10, -10, 10);
		canvas.addObject(p3);
		p3.setColor(new Color(0, 0, 255));
		
	}
	
	private void start() {
		serial.sendDo(FCCommand.FC_DO_MAG_CALIB);
		infoLabel.setText("Rotate sensor in all directions. press stop to finish");
		startBtn.setVisible(false);
		stopBtn.setVisible(true);
	}
	
	private void stop() {
		serial.sendDo(FCCommand.FC_DO_STOP_MAG_CALIB);
		infoLabel.setText("Choose a file to save the generated sensor data to");
		startBtn.setVisible(true);
		stopBtn.setVisible(false);
	}
	
	private void magReceived(Vec3 vec) {
		if(!running) return;
		File file = fileChooser.getSelectedFile();
		if(file != null) {
			try {
				Files.write(Paths.get(file.getAbsolutePath()), (vec.x + "\t" + vec.y + "\t" + vec.z + "\n").getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		lastVec = vec.clone();
		vec.divide(30);
		Cube c = new Cube();
		c.setScale(0.05);
		c.setLoc(vec.clone());
		canvas.addObject(c);
		lastVec = vec;
	}

	@Override
	public void sensorReceived(String sensorName, String sensorSubType, double value) {
		if(!sensorName.contentEquals(sensor)) return;
		if(sensorSubType.contentEquals("X")) {
			vecBuff.x = value;
		}
		if(sensorSubType.contentEquals("Y")) {
			vecBuff.y = value;
		}
		if(sensorSubType.contentEquals("Z")) {
			vecBuff.z = value;
			magReceived(vecBuff);
		}
	}
}