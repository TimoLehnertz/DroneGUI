package gui.elements.plotter;

import gui.GuiLogic;
import serial.SensorListener;
import xGraph.XGraphViewer;

public class GraphViewer extends XGraphViewer implements SensorListener {

	private static final long serialVersionUID = 1L;

	private GuiLogic logic = GuiLogic.getInstance();
	
	public GraphViewer() {
		super();
		logic.getSerialInterface().addSensorListener(this);
	}

	@Override
	public void sensorReceived(String sensorName, String sensorSubType, double val) {
		plot(sensorName, sensorSubType, val);
	}
}