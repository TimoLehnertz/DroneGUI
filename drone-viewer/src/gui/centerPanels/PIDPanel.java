package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.elements.FCPidSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;

public class PIDPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	//Rate
	FCPidSetter ratePIDr = new FCPidSetter(FCCommand.FC_GET_RATE_PID_R, FCCommand.FC_SET_RATE_PID_R, "Roll", true);
	FCPidSetter ratePIDp = new FCPidSetter(FCCommand.FC_GET_RATE_PID_P, FCCommand.FC_SET_RATE_PID_P, "Pitch", false);
	FCPidSetter ratePIDy = new FCPidSetter(FCCommand.FC_GET_RATE_PID_Y, FCCommand.FC_SET_RATE_PID_Y, "Yaw", false);
	//Level
	FCPidSetter levelPIDr = new FCPidSetter(FCCommand.FC_GET_RATE_PID_R, FCCommand.FC_SET_RATE_PID_R, "Roll", true);
	FCPidSetter levelPIDp = new FCPidSetter(FCCommand.FC_GET_RATE_PID_P, FCCommand.FC_SET_RATE_PID_P, "Pitch", false);
	FCPidSetter levelPIDy = new FCPidSetter(FCCommand.FC_GET_RATE_PID_Y, FCCommand.FC_SET_RATE_PID_Y, "Yaw", false);
	
	public PIDPanel() {
		super("PIDs");
		
		SectionPanel rateSection = new SectionPanel("Rate PIDs");
		SectionPanel levelSection = new SectionPanel("Level PIDs");
		
		/**
		 * Rate
		 */
		JPanel ratePidPanel = new JPanel(new GridLayout(1, 3));
		ratePidPanel.add(ratePIDr);
		ratePidPanel.add(ratePIDp);
		ratePidPanel.add(ratePIDy);
		
		rateSection.getBody().add(ratePidPanel);
		
		/**
		 * Level
		 */
		JPanel levelPidPanel = new JPanel(new GridLayout(1, 3));
		levelPidPanel.add(levelPIDr);
		levelPidPanel.add(levelPIDp);
		levelPidPanel.add(levelPIDy);
		
		levelSection.getBody().add(levelPidPanel);
		
		getBody().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		getBody().add(rateSection, gbc);
		gbc.gridy = 1;
		getBody().add(levelSection, gbc);
	}
}
