package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import gui.elements.FCNumberSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;
import xGui.XLabel;

public class FCPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	private SectionPanel freqSection = new SectionPanel("Loop frequencies");
	private SectionPanel modeSection = new SectionPanel("FLight mode(Debug)");
	private SectionPanel throttleSection = new SectionPanel("Throttle");
	
	public FCPanel() {
		super();
		getBody().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 100;
		body.add(freqSection, gbc);
		gbc.gridy = 1;
		body.add(modeSection, gbc);
		gbc.gridy = 2;
		body.add(throttleSection, gbc);
		
		throttleSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_THROTTLE_MUL_4S, FCCommand.FC_SET_THROTTLE_MUL_4S, "4S Throttle multiplicator"));
		throttleSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_THROTTLE_MUL_6S, FCCommand.FC_SET_THROTTLE_MUL_6S, "6S Throttle multiplicator"));
		freqSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_LOOP_FREQ_RATE, FCCommand.FC_SET_LOOP_FREQ_RATE, "Loop frequency (Rate mode)"));
		freqSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_LOOP_FREQ_LEVEL, FCCommand.FC_SET_LOOP_FREQ_LEVEL, "Loop frequency (Level mode)"), gbc);
		modeSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_OVERWRITE_FM, FCCommand.FC_SET_OVERWRITE_FM, "Overwrite flight mode", true), gbc);
		modeSection.getBody().add(new XLabel("Mode 0: None(disable overwrite), Mode 1: Rate, Mode 2: Level"));
	}
}