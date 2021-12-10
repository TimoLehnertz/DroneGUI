package gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import gui.GuiLogic;
import imu.IMU;
import maths.Quaternion;
import maths.Vec3;
import objects.MyObject;
import renderer.Canvas3D;
import serial.FCCommand;
import serial.SensorListener;
import serial.SerialInterface;
import xGui.XButton;
import xGui.XCheckBox;
import xGui.XLabel;
import xGui.XPanel;

public class View3D extends XPanel implements SensorListener{

	private static final long serialVersionUID = 1L;
	private Canvas3D canvas;
	GuiLogic logic = GuiLogic.getInstance();
	SerialInterface serial = logic.getSerialInterface();
	IMU imu = logic.getImu();
	Quaternion rot = new Quaternion();
	Vec3 loc = new Vec3();
	
	XButton resetGuiBtn = new XButton("GUI");
	XButton resetINSBtn = new XButton("INS");
	
	XCheckBox xCheck = new XCheckBox("X", true);
	XCheckBox yCheck = new XCheckBox("Y", true);
	XCheckBox zCheck = new XCheckBox("Z", true);
	
	FCBooleanSetter quatTelem = new FCBooleanSetter(FCCommand.FC_GET_USE_QUAT_TELEM, FCCommand.FC_SET_QUAT_TELEM, "Rot");
	FCBooleanSetter locTelem = new FCBooleanSetter(FCCommand.FC_GET_USE_LOC_TELEM, FCCommand.FC_SET_LOC_TELEM, "Loc");
	
	public View3D() {
		super();
		XPanel header = new XPanel();
		header.setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout());
		logic.getSerialInterface().addSensorListener(this);
		canvas = new Canvas3D(true);
		canvas.setPreferredSize(new Dimension(430, 380));
		XLabel a = new XLabel("Reset:");
		a.setForeground(Color.white);
		header.add(a);
		header.add(resetGuiBtn);
		resetGuiBtn.addActionListener(e -> loc.setFrom(new Vec3()));
		resetINSBtn.addActionListener(e -> serial.sendDo(FCCommand.FC_DO_RESET_INS));
		header.add(resetINSBtn);
		
		XLabel b = new XLabel("Use: ");
		b.setForeground(Color.white);
		header.add(b);
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
		if(sensorName.contentEquals("Q")) {
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