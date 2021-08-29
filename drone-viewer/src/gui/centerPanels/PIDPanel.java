package gui.centerPanels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.Cons;
import gui.elements.FCNumberSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;

public class PIDPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;

	SectionPanel rateSection = new SectionPanel("Rate PIDs");
	SectionPanel levelSection = new SectionPanel("Level PIDs");
	
	//Rate
	FCNumberSetter ratePP = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_PP, FCCommand.FC_SET_RATE_PID_PP, "P");
	FCNumberSetter ratePI = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_PI, FCCommand.FC_SET_RATE_PID_PI, "I");
	FCNumberSetter ratePD = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_PD1, FCCommand.FC_SET_RATE_PID_PD1, "D");
	FCNumberSetter ratePDlpf = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_PD_LPF, FCCommand.FC_SET_RATE_PID_PD_LPF, "D lpf");
	
	FCNumberSetter rateRP = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_RP, FCCommand.FC_SET_RATE_PID_RP, "P");
	FCNumberSetter rateRI = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_RI, FCCommand.FC_SET_RATE_PID_RI, "I");
	FCNumberSetter rateRD = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_RD1, FCCommand.FC_SET_RATE_PID_RD1, "D");
	FCNumberSetter rateRDlpf = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_RD_LPF, FCCommand.FC_SET_RATE_PID_RD_LPF, "D lpf");
	
	FCNumberSetter rateYP = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_YP, FCCommand.FC_SET_RATE_PID_YP, "P");
	FCNumberSetter rateYI = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_YI, FCCommand.FC_SET_RATE_PID_YI, "I");
	FCNumberSetter rateYD = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_YD1, FCCommand.FC_SET_RATE_PID_YD1, "D");
	FCNumberSetter rateYDlpf = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_YD_LPF, FCCommand.FC_SET_RATE_PID_YD_LPF, "D lpf");
	
	//Level
	FCNumberSetter levelPP = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_PP, FCCommand.FC_SET_LEVEL_PID_PP, "P");
	FCNumberSetter levelPI = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_PI, FCCommand.FC_SET_LEVEL_PID_PI, "I");
	FCNumberSetter levelPD = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_PD1, FCCommand.FC_SET_LEVEL_PID_PD1, "D");
	FCNumberSetter levelPDlpf = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_PD_LPF, FCCommand.FC_SET_LEVEL_PID_PD_LPF, "D lpf");
	
	FCNumberSetter levelRP = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_RP, FCCommand.FC_SET_LEVEL_PID_RP, "P");
	FCNumberSetter levelRI = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_RI, FCCommand.FC_SET_LEVEL_PID_RI, "I");
	FCNumberSetter levelRD = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_RD1, FCCommand.FC_SET_LEVEL_PID_RD1, "D");
	FCNumberSetter levelRDlpf = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_RD_LPF, FCCommand.FC_SET_LEVEL_PID_RD_LPF, "D lpf");
	
	FCNumberSetter levelYP = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_YP, FCCommand.FC_SET_LEVEL_PID_YP, "P");
	FCNumberSetter levelYI = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_YI, FCCommand.FC_SET_LEVEL_PID_YI, "I");
	FCNumberSetter levelYD = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_YD1, FCCommand.FC_SET_LEVEL_PID_YD1, "D");
	FCNumberSetter levelYDlpf = new FCNumberSetter(FCCommand.FC_GET_LEVEL_PID_YD_LPF, FCCommand.FC_SET_LEVEL_PID_YD_LPF, "D lpf");
	
	public PIDPanel() {
		super("PIDs");
		
		/**
		 * Rate
		 */
		JPanel ratePidPanel = new JPanel();
		ratePidPanel.setLayout(new GridLayout(1, 3));
		
		JPanel rateRollPanel = new JPanel();
		rateRollPanel.setLayout(new BorderLayout());
		rateRollPanel.add(new JLabel("Roll"), BorderLayout.NORTH);
		JPanel rateRollContent = new JPanel();
		rateRollPanel.add(rateRollContent, BorderLayout.CENTER);
		rateRollContent.setLayout(new GridLayout(4, 1));
		rateRollContent.add(rateRP);
		rateRollContent.add(rateRI);
		rateRollContent.add(rateRD);
		rateRollContent.add(rateRDlpf);
		
		JPanel ratePitchPanel = new JPanel();
		ratePitchPanel.setLayout(new BorderLayout());
		ratePitchPanel.add(new JLabel("Pitch"), BorderLayout.NORTH);
		JPanel ratePitchContent = new JPanel();
		ratePitchPanel.add(ratePitchContent, BorderLayout.CENTER);
		ratePitchContent.setLayout(new GridLayout(4, 1));
		ratePitchContent.add(ratePP);
		ratePitchContent.add(ratePI);
		ratePitchContent.add(ratePD);
		ratePitchContent.add(ratePDlpf);
		
		JPanel rateYawPanel = new JPanel();
		rateYawPanel.setLayout(new BorderLayout());
		rateYawPanel.add(new JLabel("Yaw"), BorderLayout.NORTH);
		JPanel rateYawContent = new JPanel();
		rateYawPanel.add(rateYawContent, BorderLayout.CENTER);
		rateYawContent.setLayout(new GridLayout(4, 1));
		rateYawContent.add(rateYP);
		rateYawContent.add(rateYI);
		rateYawContent.add(rateYD);
		rateYawContent.add(rateYDlpf);
		
		ratePidPanel.add(rateRollPanel);
		ratePidPanel.add(ratePitchPanel);
		ratePidPanel.add(rateYawPanel);
		
		rateSection.getBody().setLayout(new BorderLayout());
		JLabel rateHeader = new JLabel("Rate PIDs");
		rateHeader.setFont(Cons.FONT_H2);
		rateSection.getBody().add(rateHeader, BorderLayout.NORTH);
		rateSection.getBody().add(ratePidPanel, BorderLayout.CENTER);
		
		/**
		 * Level
		 */
		JPanel levelPidPanel = new JPanel();
		levelPidPanel.setLayout(new GridLayout(1, 3));
		
		JPanel levelRollPanel = new JPanel();
		levelRollPanel.setLayout(new BorderLayout());
		levelRollPanel.add(new JLabel("Roll"), BorderLayout.NORTH);
		JPanel levelRollContent = new JPanel();
		levelRollPanel.add(levelRollContent, BorderLayout.CENTER);
		levelRollContent.setLayout(new GridLayout(4, 1));
		levelRollContent.add(levelRP);
		levelRollContent.add(levelRI);
		levelRollContent.add(levelRD);
		levelRollContent.add(levelRDlpf);
		
		JPanel levelPitchPanel = new JPanel();
		levelPitchPanel.setLayout(new BorderLayout());
		levelPitchPanel.add(new JLabel("Pitch"), BorderLayout.NORTH);
		JPanel levelPitchContent = new JPanel();
		levelPitchPanel.add(levelPitchContent, BorderLayout.CENTER);
		levelPitchContent.setLayout(new GridLayout(4, 1));
		levelPitchContent.add(levelPP);
		levelPitchContent.add(levelPI);
		levelPitchContent.add(levelPD);
		levelPitchContent.add(levelPDlpf);
		
		JPanel levelYawPanel = new JPanel();
		levelYawPanel.setLayout(new BorderLayout());
		levelYawPanel.add(new JLabel("Yaw"), BorderLayout.NORTH);
		JPanel levelYawContent = new JPanel();
		levelYawPanel.add(levelYawContent, BorderLayout.CENTER);
		levelYawContent.setLayout(new GridLayout(4, 1));
		levelYawContent.add(levelYP);
		levelYawContent.add(levelYI);
		levelYawContent.add(levelYD);
		levelYawContent.add(levelYDlpf);
		
		levelPidPanel.add(levelRollPanel);
		levelPidPanel.add(levelPitchPanel);
		levelPidPanel.add(levelYawPanel);
		
		levelSection.getBody().setLayout(new BorderLayout());
		JLabel levelHeader = new JLabel("Level PIDs");
		levelHeader.setFont(Cons.FONT_H2);
		levelSection.getBody().add(levelHeader, BorderLayout.NORTH);
		levelSection.getBody().add(levelPidPanel, BorderLayout.CENTER);
		
		getBody().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		getBody().add(rateSection, gbc);
		gbc.gridy = 1;
		getBody().add(levelSection, gbc);
	}
}
