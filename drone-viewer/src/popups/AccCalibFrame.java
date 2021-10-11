package popups;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import gui.GuiLogic;
import gui.elements.SectionPanel;
import maths.Vec3;
import objects.CenterArrows;
import objects.Cube;
import objects.MyObject;
import objects.PointLight;
import renderer.Canvas3D;
import scene.Scene;
import serial.FCCommand;
import serial.SensorListener;
import serial.SerialInterface;

public class AccCalibFrame extends JFrame implements SensorListener {

	private static final long serialVersionUID = 1L;
	AccCalibFrame me;
	SectionPanel body = new SectionPanel("Accelerometer Calibration");
	JButton startBtn = new JButton("Start telem");
	JButton stopBtn = new JButton("Stop telem");
	JButton measureBtn = new JButton("Measure");
	JButton loadBtn = new JButton("Load from file");
	GuiLogic logic = GuiLogic.getInstance();
	SerialInterface serial = logic.getSerialInterface();
	JLabel infoLabel = new JLabel("Press start to calibrate");
	Vec3 vecBuff = new Vec3();
	Vec3 vecBuffF = new Vec3();
	JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "\\Desktop");
	private String sensor = "ACC";
	JCheckBox filteredCheck = new JCheckBox("Use filtered");
	private Canvas3D canvas = new Canvas3D(false);
	private Scene scene = canvas.getActiveScene();
	private JButton clearBtn = new JButton("Clear");
//	private Vec3 lastVec = new Vec3();
	Cube c = new Cube();
	Cube cF = new Cube();

	public AccCalibFrame() {
		super("Accelerometer Calibration");
		setResizable(false);
		setBounds(0, 0, 1000, 900);
		add(body);
		stopBtn.setVisible(true);
		body.getBody().add(startBtn);
		body.getBody().add(stopBtn);
		body.getBody().add(infoLabel);
		body.getBody().add(fileChooser);
		body.getBody().add(filteredCheck);
		body.getBody().add(measureBtn);
		body.getBody().add(loadBtn);
		loadBtn.addActionListener(e -> load());
		measureBtn.addActionListener(e -> measure(true));
		filteredCheck.addChangeListener(e -> {
			boolean use = filteredCheck.isSelected();
			for (int i = 0; i < scene.getObjects().size(); i++) {
				MyObject o = scene.getObjects().get(i);
				if(o.getScale().x == 0.021) {
					o.setVisible(use);
				}
			}
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
				if(o.getScale().x == 0.02 || o.getScale().x == 0.021) {
					removals.add(o);
				}
			}
			canvas.getScenes().get(0).getObjects().removeAll(removals);
		});
		initScene();
		me = this;
		
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
				serial.removeSensorListener(me);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});	
	}
	
	private void load() {
		int axis = 0;
		try {
			Scanner sc = new Scanner(fileChooser.getSelectedFile());
			while (sc.hasNext()){
				String next = sc.next();
				vecBuff.set(axis, Double.parseDouble(next));
				if(axis == 2) {
					measure(false);
				}
				axis = (axis+1) % 3;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void measure(boolean save) {
		File file = fileChooser.getSelectedFile();
		Vec3 vec;
		if(filteredCheck.isSelected() && !save) {
			vec = vecBuffF;
		} else {
			vec = vecBuff;
		}
		if(file != null) {
			try {
				if(save) {					
					Files.write(Paths.get(file.getAbsolutePath()), (vec.x + "\t" + vec.y + "\t" + vec.z + "\n").getBytes(), StandardOpenOption.APPEND);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Cube c = new Cube();
		c.setScale(0.02);
		c.setLoc(vecBuff.clone());
		canvas.addObject(c);
		if(save) {
			Cube c1 = new Cube();
			c1.setScale(0.021);
			c1.setColor(Color.green);
			c1.setLoc(vecBuffF.clone());
			canvas.addObject(c1);
		}
		
	}
	
	private void initScene() {
		canvas.getScenes().get(0).setUseMist(false);
		canvas.start();
		PointLight p = new PointLight(10, 10, 10);
		p.setColor(new Color(255, 255, 255));
		canvas.addObject(p);
		PointLight p2 = new PointLight(-10, 10, 10);
		canvas.addObject(p2);
		p2.setColor(new Color(100, 100, 100));
		
		PointLight p3 = new PointLight(-10, -10, 10);
		canvas.addObject(p3);
		p3.setColor(new Color(250, 250, 250));
		
		CenterArrows arrows = new CenterArrows(1);
		canvas.addObject(arrows);
		
		c.setColor(Color.magenta);
		c.setScale(0.01);
		canvas.addObject(c);
		cF.setColor(Color.CYAN);
		cF.setScale(0.021);
//		canvas.addObject(cF);
	}
	
	private void start() {
		serial.set(FCCommand.FC_SET_ACC_TELEM, true);
		infoLabel.setText("Rotate sensor in all directions. press stop to finish");
		startBtn.setVisible(false);
		stopBtn.setVisible(true);
	}
	
	private void stop() {
		serial.set(FCCommand.FC_SET_ACC_TELEM, false);
		infoLabel.setText("Choose a file to save the generated sensor data to");
		startBtn.setVisible(true);
		stopBtn.setVisible(false);
	}

	@Override
	public void sensorReceived(String sensorName, String sensorSubType, double value) {
		Vec3 v;
		if(sensorName.contentEquals("ACC")) {
			v = vecBuff;
		} else if(sensorName.contentEquals("ACC(f)")) {
			v = vecBuffF;
		} else return;
		if(sensorSubType.contentEquals("X")) {
			v.x = value;
		}
		if(sensorSubType.contentEquals("Y")) {
			v.y = value;
		}
		if(sensorSubType.contentEquals("Z")) {
			v.z = value;
			if(sensorName.contentEquals("ACC") && !filteredCheck.isSelected()) {				
				c.setLoc(v.clone());
			} else if(sensorName.contentEquals("ACC(f)") && filteredCheck.isSelected()){
				c.setLoc(v.clone());
			}
		}
	}
}
