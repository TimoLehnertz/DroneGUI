package gui.centerPanels;

import java.awt.BorderLayout;
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
	
	FCNumberSetter ratePP = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_PP, FCCommand.FC_SET_RATE_PID_PP, "P");
	FCNumberSetter ratePI = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_PI, FCCommand.FC_SET_RATE_PID_PI, "I");
	FCNumberSetter ratePD = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_PD, FCCommand.FC_SET_RATE_PID_PD, "D");
	
	FCNumberSetter rateRP = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_RP, FCCommand.FC_SET_RATE_PID_RP, "P");
	FCNumberSetter rateRI = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_RI, FCCommand.FC_SET_RATE_PID_RI, "I");
	FCNumberSetter rateRD = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_RD, FCCommand.FC_SET_RATE_PID_RD, "D");
	
	FCNumberSetter rateYP = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_YP, FCCommand.FC_SET_RATE_PID_YP, "P");
	FCNumberSetter rateYI = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_YI, FCCommand.FC_SET_RATE_PID_YI, "I");
	FCNumberSetter rateYD = new FCNumberSetter(FCCommand.FC_GET_RATE_PID_YD, FCCommand.FC_SET_RATE_PID_YD, "D");
	
	public PIDPanel() {
		super("PIDs");
		
		JPanel ratePidPanel = new JPanel();
		ratePidPanel.setLayout(new GridLayout(1, 3));
		
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new BorderLayout());
		rollPanel.add(new JLabel("Roll"), BorderLayout.NORTH);
		JPanel rollContent = new JPanel();
		rollPanel.add(rollContent, BorderLayout.CENTER);
		rollContent.setLayout(new GridLayout(3, 1));
		rollContent.add(rateRP);
		rollContent.add(rateRI);
		rollContent.add(rateRD);
		
		JPanel pitchPanel = new JPanel();
		pitchPanel.setLayout(new BorderLayout());
		pitchPanel.add(new JLabel("Pitch"), BorderLayout.NORTH);
		JPanel pitchContent = new JPanel();
		pitchPanel.add(pitchContent, BorderLayout.CENTER);
		pitchContent.setLayout(new GridLayout(3, 1));
		pitchContent.add(ratePP);
		pitchContent.add(ratePI);
		pitchContent.add(ratePD);
		
		JPanel yawPanel = new JPanel();
		yawPanel.setLayout(new BorderLayout());
		yawPanel.add(new JLabel("Yaw"), BorderLayout.NORTH);
		JPanel yawContent = new JPanel();
		yawPanel.add(yawContent, BorderLayout.CENTER);
		yawContent.setLayout(new GridLayout(3, 1));
		yawContent.add(rateYP);
		yawContent.add(rateYI);
		yawContent.add(rateYD);
		
		ratePidPanel.add(rollPanel);
		ratePidPanel.add(pitchPanel);
		ratePidPanel.add(yawPanel);
		
		rateSection.getBody().setLayout(new BorderLayout());
		JLabel rateHeader = new JLabel("Rate PIDs");
		rateHeader.setFont(Cons.FONT_H2);
		rateSection.getBody().add(rateHeader, BorderLayout.NORTH);
		rateSection.getBody().add(ratePidPanel, BorderLayout.CENTER);
		
		getBody().add(rateSection);
	}
}
