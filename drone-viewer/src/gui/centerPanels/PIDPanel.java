package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import gui.elements.FCBooleanSetter;
import gui.elements.FCNumberSetter;
import gui.elements.FCPidSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;
import xGui.XPanel;

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
	
	FCNumberSetter angleModeMaxAngle = new FCNumberSetter(FCCommand.FC_GET_ANGLE_MODE_MAX_ANGLE, FCCommand.FC_SET_ANGLE_MODE_MAX_ANGLE, "Max Angle");
	
	// Altitude
	FCPidSetter altitudePID = new FCPidSetter(FCCommand.FC_GET_ALTITUDE_PID, FCCommand.FC_SET_ALTITUDE_PID, "Altitude", true);
	
	FCNumberSetter hoverThrottle		= new FCNumberSetter(FCCommand.FC_GET_HOVER_THROTTLE, FCCommand.FC_SET_HOVER_THROTTLE, "Hover throttle");
	FCNumberSetter launchIBoostSeconds 	= new FCNumberSetter(FCCommand.FC_GET_LAUNCH_I_BOOST_SECONDS, FCCommand.FC_SET_LAUNCH_I_BOOST_SECONDS, "Launch Boost seconds");
	FCNumberSetter launchIBoostLevel 	= new FCNumberSetter(FCCommand.FC_GET_LAUNCH_I_BOOST_LEVEL, FCCommand.FC_SET_LAUNCH_I_BOOST_LEVEL, "Launch boost level");
	FCNumberSetter launchIBoostAltitude = new FCNumberSetter(FCCommand.FC_GET_LAUNCH_I_BOOST_ALTITUDE, FCCommand.FC_SET_LAUNCH_I_BOOST_ALTITUDE, "Launch boost altitude");
	
	// Velocity
	FCPidSetter velPID = new FCPidSetter(FCCommand.FC_GET_VEL_PID, FCCommand.FC_SET_VEL_PID, "Velocity X/Y", true);
	FCNumberSetter gpsMaxSpeedHorizontal = new FCNumberSetter(FCCommand.FC_GET_GPS_MAX_SPEED_HORIZONTAL, FCCommand.FC_SET_GPS_MAX_SPEED_HORIZONTAL, "Max Speed Horizontal");
	FCNumberSetter gpsMaxSpeedVertical = new FCNumberSetter(FCCommand.FC_GET_GPS_MAX_SPEED_VERTICAL, FCCommand.FC_SET_GPS_MAX_SPEED_VERTICAL, "Max Speed Vertical");
	
	
	public PIDPanel() {
		super();
		
		SectionPanel rateSection = new SectionPanel("Rate PIDs");
		SectionPanel levelSection = new SectionPanel("Level PIDs");
		SectionPanel altitudeSection = new SectionPanel("Altitude PIDs");
		SectionPanel velocitySection = new SectionPanel("Velocity PIDs");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 100;
		
		/**
		 * Rate
		 */
		rateSection.getBody().setLayout(new GridBagLayout());
		XPanel ratePidPanel = new XPanel(new GridLayout(1, 3));
		XPanel additionalRatePanel = new XPanel();
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
		levelSection.getBody().setLayout(new GridBagLayout());
		XPanel levelPidPanel = new XPanel(new GridLayout(1, 3));
		levelPidPanel.add(levelPIDr);
		levelPidPanel.add(levelPIDp);
		levelPidPanel.add(levelPIDy);
		
		XPanel additionalLevelPanel = new XPanel();
		additionalLevelPanel.add(angleModeMaxAngle);
		
		gbc.gridy = 0;
		levelSection.getBody().add(levelPidPanel, gbc);
		gbc.gridy = 1;
		levelSection.getBody().add(additionalLevelPanel, gbc);
		
		/**
		 * Altitude
		 */
		altitudeSection.getBody().setLayout(new GridBagLayout());
		XPanel altitudePidPanel = new XPanel(new GridLayout(1, 1));
		altitudePidPanel.add(altitudePID);
		
		XPanel additionalAltitudePanel = new XPanel(new GridLayout(2, 2));
		additionalAltitudePanel.add(hoverThrottle);
		additionalAltitudePanel.add(launchIBoostSeconds);
		additionalAltitudePanel.add(launchIBoostLevel);
		additionalAltitudePanel.add(launchIBoostAltitude);
		
		gbc.gridy = 0;
		gbc.gridx = 0;
		altitudeSection.getBody().add(altitudePidPanel, gbc);
		gbc.gridx = 1;
		altitudeSection.getBody().add(additionalAltitudePanel, gbc);
		
		/**
		 * Velocity
		 */
		velocitySection.getBody().setLayout(new GridBagLayout());
		XPanel velocityPidPanel = new XPanel(new GridLayout(1, 1));
		velocityPidPanel.add(velPID);
		
		XPanel additionalVelPanel = new XPanel(new GridLayout(2, 1));
		additionalVelPanel.add(gpsMaxSpeedHorizontal);
		additionalVelPanel.add(gpsMaxSpeedVertical);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		velocitySection.getBody().add(velocityPidPanel, gbc);
		gbc.gridx = 1;
		velocitySection.getBody().add(additionalVelPanel, gbc);
		
		
		getBody().setLayout(new GridBagLayout());
		gbc.gridy = 0;
		getBody().add(rateSection, gbc);
		gbc.gridy = 1;
		getBody().add(levelSection, gbc);
		gbc.gridy = 2;
		getBody().add(altitudeSection, gbc);
		gbc.gridy = 3;
		getBody().add(velocitySection, gbc);
	}
}
