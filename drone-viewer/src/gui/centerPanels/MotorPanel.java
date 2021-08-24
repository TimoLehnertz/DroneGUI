package gui.centerPanels;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import gui.elements.FCBooleanSetter;
import gui.elements.FCLiveSlider;
import gui.elements.SectionPanel;
import serial.FCCommand;

public class MotorPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;

	SectionPanel sliderSection = new SectionPanel("Sliders");
	
	FCLiveSlider m1Slider = new FCLiveSlider(FCCommand.FC_GET_M1_OVERWRITE, FCCommand.FC_SET_M1_OVERWRITE, "Motor front left", 	0, 100, 0);
	FCLiveSlider m2Slider = new FCLiveSlider(FCCommand.FC_GET_M2_OVERWRITE, FCCommand.FC_SET_M2_OVERWRITE, "Motor front right", 0, 100, 0);
	FCLiveSlider m3Slider = new FCLiveSlider(FCCommand.FC_GET_M3_OVERWRITE, FCCommand.FC_SET_M3_OVERWRITE, "Motor back left", 	0, 100, 0);
	FCLiveSlider m4Slider = new FCLiveSlider(FCCommand.FC_GET_M4_OVERWRITE, FCCommand.FC_SET_M4_OVERWRITE, "Motor back right", 	0, 100, 0);
	
	public MotorPanel() {
		super("Motor Panel");
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new GridLayout(4, 1));
		
		JPanel checkPanel = new JPanel();
		checkPanel.setLayout(new GridLayout(2, 1));
		FCBooleanSetter motorSetter = new FCBooleanSetter(FCCommand.FC_GET_OVERWRITE_MOTORS, FCCommand.FC_SET_OVERWRITE_MOTORS, "Overwrite motors");
		checkPanel.add(motorSetter);
		checkPanel.add(new FCBooleanSetter(FCCommand.FC_GET_PROPS_IN, FCCommand.FC_SET_PROPS_IN, "Props in"));
		motorSetter.saveConsition = () -> {
			if (m1Slider.getVal() == 0 && m2Slider.getVal() == 0 && m3Slider.getVal() == 0 && m4Slider.getVal() == 0) {
				return true;
			} else {
				System.out.println("Please move all motor Sliders to 0 to enable motor overwrite!");
				return false;
			}
		};
		sliderSection.getBody().add(checkPanel);
		sliderSection.getBody().add(sliderPanel);
		
		sliderPanel.add(m1Slider);
		sliderPanel.add(m2Slider);
		sliderPanel.add(m3Slider);
		sliderPanel.add(m4Slider);
		
		getBody().add(sliderSection);
	}
}