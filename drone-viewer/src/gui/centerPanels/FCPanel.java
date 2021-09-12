package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import gui.elements.FCNumberSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;

public class FCPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	private SectionPanel freqSection = new SectionPanel("Loop frequencies");
	private SectionPanel modeSection = new SectionPanel("FLight mode(Debug)");
	
	public FCPanel() {
		super("Flight controller");
		getBody().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		body.add(freqSection, gbc);
		gbc.gridy = 1;
		body.add(modeSection, gbc);
		freqSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_LOOP_FREQ_RATE, FCCommand.FC_SET_LOOP_FREQ_RATE, "Loop frequency (Rate mode)"));
		freqSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_LOOP_FREQ_LEVEL, FCCommand.FC_SET_LOOP_FREQ_LEVEL, "Loop frequency (Level mode)"), gbc);
		modeSection.getBody().add(new FCNumberSetter(FCCommand.FC_GET_OVERWRITE_FM, FCCommand.FC_SET_OVERWRITE_FM, "Overwrite flight mode", true), gbc);
		modeSection.getBody().add(new JLabel("Mode 0: None(disable overwrite), Mode 1: Rate, Mode 2: Level"));
	}
}