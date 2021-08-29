package gui.centerPanels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import gui.elements.FCBooleanSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;

public class ExtrasPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;

	private SectionPanel ledPanel = new SectionPanel("LEDs");
	private FCBooleanSetter useLeds = new FCBooleanSetter(FCCommand.FC_GET_USE_LEDS, FCCommand.FC_SET_USE_LEDS, "Use LEDs");
	
	public ExtrasPanel() {
		super("Extras");
		getBody().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		getBody().add(ledPanel, gbc);
		
		ledPanel.getBody().add(useLeds);
	}

}
