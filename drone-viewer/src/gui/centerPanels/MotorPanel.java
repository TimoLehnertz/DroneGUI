package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import gui.GuiLogic;
import gui.elements.FCBooleanSetter;
import gui.elements.FCLiveSlider;
import gui.elements.FCNumberSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;
import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;

public class MotorPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;

	SectionPanel sliderSection = new SectionPanel("Sliders");
	
	SectionPanel remapSection = new SectionPanel("Pin remapping (Pins 2 - 5)");
	
	FCLiveSlider m1Slider = new FCLiveSlider(FCCommand.FC_GET_M1_OVERWRITE, FCCommand.FC_SET_M1_OVERWRITE, "Motor front left", 	0, 100, 0);
	FCLiveSlider m2Slider = new FCLiveSlider(FCCommand.FC_GET_M2_OVERWRITE, FCCommand.FC_SET_M2_OVERWRITE, "Motor front right", 0, 100, 0);
	FCLiveSlider m3Slider = new FCLiveSlider(FCCommand.FC_GET_M3_OVERWRITE, FCCommand.FC_SET_M3_OVERWRITE, "Motor back left", 	0, 100, 0);
	FCLiveSlider m4Slider = new FCLiveSlider(FCCommand.FC_GET_M4_OVERWRITE, FCCommand.FC_SET_M4_OVERWRITE, "Motor back right", 	0, 100, 0);
	
	FCNumberSetter m1Pin = new FCNumberSetter(FCCommand.FC_GET_M1_PIN, FCCommand.FC_SET_M1_PIN, "M1(front left)", true);
	FCNumberSetter m2Pin = new FCNumberSetter(FCCommand.FC_GET_M2_PIN, FCCommand.FC_SET_M2_PIN, "M2(front right)", true);
	FCNumberSetter m3Pin = new FCNumberSetter(FCCommand.FC_GET_M3_PIN, FCCommand.FC_SET_M3_PIN, "M3(back left)", true);
	FCNumberSetter m4Pin = new FCNumberSetter(FCCommand.FC_GET_M4_PIN, FCCommand.FC_SET_M4_PIN, "M4(back right)", true);
	
	XButton fullBtn = new XButton("All on 100%");
	XButton lowBtn = new XButton("All on 4%");
	XButton offBtn = new XButton("All on 0%");
	
	
	
	public MotorPanel() {
		super();
		getBody().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 100;
		XPanel sliderPanel = new XPanel();
		sliderPanel.setLayout(new GridLayout(4, 1));
		
		XPanel checkPanel = new XPanel();
		XPanel stuffPanel = new XPanel(new GridLayout(3, 1));
		checkPanel.setLayout(new GridLayout(3, 1));
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
		
		fullBtn.addActionListener(e -> {
			m1Slider.setVal(100.0D);
			m2Slider.setVal(100.0D);
			m3Slider.setVal(100.0D);
			m4Slider.setVal(100.0D);
		});
		lowBtn.addActionListener(e -> {
			m1Slider.setVal(4.0D);
			m2Slider.setVal(4.0D);
			m3Slider.setVal(4.0D);
			m4Slider.setVal(4.0D);
		});
		offBtn.addActionListener(e -> {
			m1Slider.setVal(0.0D);
			m2Slider.setVal(0.0D);
			m3Slider.setVal(0.0D);
			m4Slider.setVal(0.0D);
		});
		stuffPanel.add(fullBtn);
		stuffPanel.add(lowBtn);
		stuffPanel.add(offBtn);
		sliderSection.getBody().add(checkPanel);
		sliderSection.getBody().add(sliderPanel);
		sliderSection.getBody().add(stuffPanel);
		
		sliderPanel.add(m1Slider);
		sliderPanel.add(m2Slider);
		sliderPanel.add(m3Slider);
		sliderPanel.add(m4Slider);
		
		remapSection.getBody().add(m1Pin);
		remapSection.getBody().add(m2Pin);
		remapSection.getBody().add(m3Pin);
		remapSection.getBody().add(m4Pin);
		remapSection.getBody().add(new XLabel("Breadboard mapping: 4,3,5,2"));
		remapSection.getBody().add(new XLabel("New mapping: 5,3,4,2"));
		getBody().add(sliderSection, gbc);
		gbc.gridy = 1;
		getBody().add(remapSection, gbc);
		
		GuiLogic.getInstance().getSerialInterface().addOpenListeners(() -> setEnabled(true));
		setEnabled(false);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		fullBtn.setEnabled(enabled);
		lowBtn.setEnabled(enabled);
		offBtn.setEnabled(enabled);
	}
}