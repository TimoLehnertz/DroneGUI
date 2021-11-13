package gui.layout;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.GuiLogic;
import gui.elements.FCBooleanSetter;
import imu.IMU;
import maths.Quaternion;
import maths.Vec3;
import objects.MyObject;
import renderer.Canvas3D;
import serial.FCCommand;
import serial.SensorListener;
import serial.SerialInterface;

public class View3D extends JPanel implements SensorListener{

	private static final long serialVersionUID = 1L;
	private Canvas3D canvas;
	GuiLogic logic = GuiLogic.getInstance();
	SerialInterface serial = logic.getSerialInterface();
	IMU imu = logic.getImu();
	Quaternion rot = new Quaternion();
	Vec3 loc = new Vec3();
	
	JButton resetGuiBtn = new JButton("GUI");
	JButton resetINSBtn = new JButton("INS");
	
	JCheckBox xCheck = new JCheckBox("X", true);
	JCheckBox yCheck = new JCheckBox("Y", true);
	JCheckBox zCheck = new JCheckBox("Z", true);
	
	FCBooleanSetter quatTelem = new FCBooleanSetter(FCCommand.FC_GET_USE_QUAT_TELEM, FCCommand.FC_SET_QUAT_TELEM, "Rot");
	FCBooleanSetter locTelem = new FCBooleanSetter(FCCommand.FC_GET_USE_LOC_TELEM, FCCommand.FC_SET_LOC_TELEM, "Loc");
	
	public View3D() {
		super();
		JPanel header = new JPanel();
		setLayout(new BorderLayout());
		logic.getSerialInterface().addSensorListener(this);
		canvas = new Canvas3D(true);
		canvas.setPreferredSize(new Dimension(430, 380));
		header.add(new JLabel("Reset:"));
		header.add(resetGuiBtn);
		resetGuiBtn.addActionListener(e -> loc.setFrom(new Vec3()));
		resetINSBtn.addActionListener(e -> serial.sendDo(FCCommand.FC_DO_RESET_INS));
		header.add(resetINSBtn);
		
		header.add(new JLabel("Use:"));
		header.add(xCheck);
		header.add(yCheck);
		header.add(zCheck);
		header.add(quatTelem);
		header.add(locTelem);
		add(header, BorderLayout.NORTH);
		add(canvas, BorderLayout.CENTER);
		canvas.start();
		
		MyObject drone = canvas.getScenes().get(0).getObjects().get(1);
		drone.setRot(rot);
		drone.setLoc(loc);
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
		if(sensorName.contentEquals("LOC")) {
			if(sensorSubType.contentEquals("X") && xCheck.isSelected()) {
				loc.setX(value);
			}
			if(sensorSubType.contentEquals("Y") && yCheck.isSelected()) {
				loc.setY(value);
			}
			if(sensorSubType.contentEquals("Z") && zCheck.isSelected()) {
				loc.setZ(value);
			}
		}
	}
}