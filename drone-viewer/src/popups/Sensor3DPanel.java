package popups;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import gui.GuiLogic;
import gui.elements.FCSetter;
import maths.Vec3;
import objects.CenterArrows;
import objects.Cube;
import objects.MyObject;
import objects.PointLight;
import renderer.Canvas3D;
import scene.Scene;
import serial.SensorListener;
import serial.SerialInterface;
import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;
import xGui.XSpinner;
import xThemes.XStyle;

public class Sensor3DPanel extends XPanel implements SensorListener {

	private static final long serialVersionUID = 1L;

	private GuiLogic logic = GuiLogic.getInstance();
	private SerialInterface serial = logic.getSerialInterface();
	
	private Canvas3D canvas = new Canvas3D(false);
	private Scene scene = canvas.getActiveScene();
	Cube cursorCube = new Cube();
	
	private List<String> xyzSensors = new ArrayList<>();
	
	private XSpinner spinner = FCSetter.getSpinner();
	
	private XButton clearBtn = new XButton("Clear");
	private JComboBox<String> sensorSelect = new JComboBox<>();
	
	private XPanel header = new XPanel(XStyle.BACKGROUND);
	private XPanel body = new XPanel(XStyle.LAYER1);
	
	private String sensor = "";
	private Vec3 vec = new Vec3();
	private Vec3 lastVec = new Vec3();
	
	private XButton startStopRecBtn = new XButton("Start plotting");
	
	private boolean plot = false;
	
	public Sensor3DPanel() {
		super();
		if(plot) {
			startStopRecBtn.setText("Stop plotting");
		}
		serial.addSensorListener(this);
		setBounds(0,0,1200,800);
		setLayout(new BorderLayout());
		
		header.add(sensorSelect);
		header.add(clearBtn);
		header.add(new XLabel("Scale"));
		header.add(spinner);
		header.add(startStopRecBtn);
		spinner.setValue(1);
		
		clearBtn.addActionListener(e -> clear());
		sensorSelect.addActionListener(e -> updateSensor());
//		sensorSelect.addItem("");
		
		startStopRecBtn.addActionListener(e -> {
			if(startStopRecBtn.getText().contentEquals("Start plotting")) {
				this.plot = true;
				startStopRecBtn.setText("Stop plotting");
			} else {
				this.plot = false;
				startStopRecBtn.setText("Start plotting");
			}
		});
		
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		initCanvas();
		body.add(canvas);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				canvas.setPreferredSize(new Dimension(getWidth(), getHeight() - 50));
				canvas.revalidate();
				canvas.repaint();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				
			}
		});
	}
	
	void initCanvas() {
		canvas.setPreferredSize(new Dimension(1200, 750));
		canvas.getScenes().get(0).setUseMist(false);
		
		canvas.start();
		CenterArrows centerArrows = new CenterArrows();
		canvas.addObject(centerArrows);
		PointLight p = new PointLight(10, 10, 10);
		p.setColor(new Color(100, 100, 255));
		canvas.addObject(p);
		PointLight p2 = new PointLight(-10, 10, 10);
		canvas.addObject(p2);
		p2.setColor(new Color(255, 100, 100));
		
		PointLight p3 = new PointLight(-10, -10, 10);
		canvas.addObject(p3);
		p3.setColor(new Color(0, 0, 255));
		cursorCube.setScale(0.08);
		cursorCube.setColor(Color.red);
		canvas.addObject(cursorCube);
	}
	
	void updateSensor() {
		sensor = (String) sensorSelect.getSelectedItem();
		spinner.setValue(sensor.contentEquals("MAG") ? 0.002 : 1);
		clear();
	}
	
	void clear() {
		List<MyObject> removals = new ArrayList<>();
		for (int i = 0; i < scene.getObjects().size(); i++) {
			MyObject o = scene.getObjects().get(i);
			if(o.getScale().x == 0.05) {
				removals.add(o);
			}
		}
		canvas.getScenes().get(0).getObjects().removeAll(removals);
	}
	
	void plot(Vec3 vec) {
		vec.multiply((double) spinner.getValue());
		cursorCube.setLoc(vec.clone());
		if(!plot) return;
		if(lastVec.subtract(vec).getLength() < 0.1) return;
		Cube c = new Cube();
		c.setColor(Color.white);
		c.setScale(0.05);
		c.setLoc(vec.clone());
		canvas.addObject(c);
		lastVec = vec.clone();
	}
	
	@Override
	public void sensorReceived(String sensorName, String sensorSubType, double value) {
		if(sensorSubType.contentEquals("X")) {
			if(!xyzSensors.contains(sensorName)) {
				xyzSensors.add(sensorName);
				sensorSelect.addItem(sensorName);
			}
		}
		if(sensorName.contentEquals(sensor)) {
			if(sensorSubType.contains("X")) {
				vec.x = value;
			}
			if(sensorSubType.contains("Y")) {
				vec.y = value;
			}
			if(sensorSubType.contains("Z")) {
				vec.z = value;
				plot(vec);
			}
		}
	}
}