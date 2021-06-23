package gui.layout;

import java.awt.Dimension;

import javax.swing.JPanel;

import gui.GuiLogic;
import imu.IMU;
import imu.IMUChangeListener;
import maths.Quaternion;
import objects.MyObject;
import renderer.Canvas3D;
import serial.INSListener;

public class View3D extends JPanel implements IMUChangeListener, INSListener{

	private static final long serialVersionUID = 1L;
	private Canvas3D canvas;
	GuiLogic logic = GuiLogic.getInstance();
	IMU imu = logic.getImu();
	
	public View3D() {
		super();
		logic.addINSListener(this);
		canvas = new Canvas3D(true);
		canvas.setPreferredSize(new Dimension(500, 500));
		add(canvas);
		canvas.start();
		
		MyObject drone = canvas.getScenes().get(0).getObjects().get(1);
		drone.setScale(0.1);
	}

	@Override
	public void imuChanged() {
		MyObject drone = canvas.getScenes().get(0).getObjects().get(1);
		drone.setRot(imu.getRotationQuaternion());
//		drone.setLoc(imu.getPos());
	}

	@Override
	public void insDataReceived(double w, double x, double y, double z) {
//		System.out.println(x);
		MyObject drone = canvas.getScenes().get(0).getObjects().get(1);
		drone.setRot(new Quaternion(w, x, y, z));
	}
}