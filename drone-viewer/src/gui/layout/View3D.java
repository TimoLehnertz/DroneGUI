package gui.layout;

import java.awt.Dimension;

import javax.swing.JPanel;

import gui.GuiLogic;
import imu.IMU;
import maths.Quaternion;
import maths.Vec3;
import objects.MyObject;
import renderer.Canvas3D;
import serial.SensorListener;

public class View3D extends JPanel implements SensorListener{

	private static final long serialVersionUID = 1L;
	private Canvas3D canvas;
	GuiLogic logic = GuiLogic.getInstance();
	IMU imu = logic.getImu();
	Quaternion rot = new Quaternion();
	Vec3 loc = new Vec3();
	
	public View3D() {
		super();
		logic.getSerialInterface().addSensorListener(this);
		canvas = new Canvas3D(true);
		canvas.setPreferredSize(new Dimension(430, 400));
		add(canvas);
		canvas.start();
		
		MyObject drone = canvas.getScenes().get(0).getObjects().get(1);
		drone.setRot(rot);
		drone.setLoc(loc);
		drone.setScale(0.1);
	}

	@Override
	public void sensorReceived(String sensorName, String sensorSubType, double value) {
		if(sensorName.contentEquals("QUAT")) {
			if(sensorSubType.contentEquals("W")) {
				rot.setW(value);
			}
			if(sensorSubType.contentEquals("X")) {
				rot.setX(value);
			}
			if(sensorSubType.contentEquals("Y")) {
				rot.setY(value);
			}
			if(sensorSubType.contentEquals("Z")) {
				rot.setZ(value);
			}
		}
//		if(sensorName.contentEquals("LOC")) {
//			if(sensorSubType.contentEquals("X")) {
//				loc.setX(value);
//			}
//			if(sensorSubType.contentEquals("Y")) {
//				loc.setY(value);
//			}
//			if(sensorSubType.contentEquals("Z")) {
//				loc.setZ(value);
//			}
//		}
	}
}