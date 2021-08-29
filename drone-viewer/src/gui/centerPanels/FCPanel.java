package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import gui.elements.FCNumberSetter;
import serial.FCCommand;

public class FCPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	public FCPanel() {
		super("Flight controller");
		getBody().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		body.add(new FCNumberSetter(FCCommand.FC_GET_LOOP_FREQ_RATE, FCCommand.FC_SET_LOOP_FREQ_RATE, "Loop frequency (Rate mode)"), gbc);
		body.add(new FCNumberSetter(FCCommand.FC_GET_LOOP_FREQ_LEVEL, FCCommand.FC_SET_LOOP_FREQ_LEVEL, "Loop frequency (Level mode)"), gbc);}
}