package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.elements.FCBooleanSetter;
import gui.elements.FCNumberSetter;
import gui.elements.FCPidSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;

public class PIDPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;
	
	//Rate
	FCPidSetter ratePIDr = new FCPidSetter(FCCommand.FC_GET_RATE_PID_R, FCCommand.FC_SET_RATE_PID_R, "Roll", true);
	FCPidSetter ratePIDp = new FCPidSetter(FCCommand.FC_GET_RATE_PID_P, FCCommand.FC_SET_RATE_PID_P, "Pitch", false);
	FCPidSetter ratePIDy = new FCPidSetter(FCCommand.FC_GET_RATE_PID_Y, FCCommand.FC_SET_RATE_PID_Y, "Yaw", false);
	FCNumberSetter iRelax = new FCNumberSetter(FCCommand.FC_GET_I_RELAX_MIN_RATE, FCCommand.FC_SET_I_RELAX_MIN_RATE, "I term relax min rate");
	
	FCBooleanSetter useAntiGravity = new FCBooleanSetter(FCCommand.FC_GET_USE_ANTI_GRAVITY, FCCommand.FC_SET_USE_ANTI_GRAVITY, "Use anti gravity");
	FCNumberSetter antiGravityMul 	= new FCNumberSetter(FCCommand.FC_GET_ANTI_GRAVITY_MULTIPLICATOR, FCCommand.FC_SET_ANTI_GRAVITY_MULTIPLICATOR, "Anti gravity multiplicator");
	FCNumberSetter antiGravitySpeed = new FCNumberSetter(FCCommand.FC_GET_ANTI_GRAVITY_SPEED, FCCommand.FC_SET_ANTI_GRAVITY_SPEED, "Anti gravity speed");
	FCNumberSetter antiGravityLpf 	= new FCNumberSetter(FCCommand.FC_GET_ANTI_GRAVITY_LPF, FCCommand.FC_SET_ANTI_GRAVITY_LPF, "Anti gravity lpf");
	
	//Level
	FCPidSetter levelPIDr = new FCPidSetter(FCCommand.FC_GET_LEVEL_PID_R, FCCommand.FC_SET_LEVEL_PID_R, "Roll", true);
	FCPidSetter levelPIDp = new FCPidSetter(FCCommand.FC_GET_LEVEL_PID_P, FCCommand.FC_SET_LEVEL_PID_P, "Pitch", false);
	FCPidSetter levelPIDy = new FCPidSetter(FCCommand.FC_GET_LEVEL_PID_Y, FCCommand.FC_SET_LEVEL_PID_Y, "Yaw", false);
	FCNumberSetter levelFactor = new FCNumberSetter(FCCommand.FC_GET_LEVEL_FACTOR, FCCommand.FC_SET_LEVEL_FACTOR, "Level fector");
	
	public PIDPanel() {
		super("PIDs");
		
		SectionPanel rateSection = new SectionPanel("Rate PIDs");
		SectionPanel levelSection = new SectionPanel("Level PIDs");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		/**
		 * Rate
		 */
		rateSection.getBody().setLayout(new GridBagLayout());
		JPanel ratePidPanel = new JPanel(new GridLayout(1, 3));
		JPanel additionalRatePanel = new JPanel();
		ratePidPanel.add(ratePIDr);
		ratePidPanel.add(ratePIDp);
		ratePidPanel.add(ratePIDy);
		
		additionalRatePanel.add(iRelax);
		additionalRatePanel.add(useAntiGravity);
		additionalRatePanel.add(antiGravityMul);
		additionalRatePanel.add(antiGravitySpeed);
		additionalRatePanel.add(antiGravityLpf);
		
		rateSection.getBody().add(ratePidPanel, gbc);
		gbc.gridy = 1;
		rateSection.getBody().add(additionalRatePanel, gbc);
		
		
		/**
		 * Level
		 */
		JPanel levelPidPanel = new JPanel(new GridLayout(1, 3));
		levelPidPanel.add(levelPIDr);
		levelPidPanel.add(levelPIDp);
		levelPidPanel.add(levelPIDy);
		
		levelSection.getBody().add(levelPidPanel);
		levelSection.getBody().add(levelFactor);
		
		getBody().setLayout(new GridBagLayout());
		gbc.gridy = 0;
		getBody().add(rateSection, gbc);
		gbc.gridy = 1;
		getBody().add(levelSection, gbc);
	}
}
