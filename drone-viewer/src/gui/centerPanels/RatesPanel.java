package gui.centerPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import gui.elements.FCMUltiSetter;
import gui.elements.SectionPanel;
import serial.FCCommand;

public class RatesPanel extends CenterPanel {

	private static final long serialVersionUID = 1L;

	private SectionPanel rateSection = new SectionPanel("Rates");
	
	private List<FCMUltiSetter> setters = Arrays.asList(
			new FCMUltiSetter(FCCommand.FC_GET_ROLL_RATES, FCCommand.FC_SET_ROLL_RATES, "Roll", Arrays.asList("RC", "Super", "RC expo"), true),
			new FCMUltiSetter(FCCommand.FC_GET_PITCH_RATES, FCCommand.FC_SET_PITCH_RATES, "Pitch", Arrays.asList("RC", "Super", "RC expo"), false),
			new FCMUltiSetter(FCCommand.FC_GET_YAW_RATES, FCCommand.FC_SET_YAW_RATES, "Yaw", Arrays.asList("RC", "Super", "RC expo"), false));
	
	private List<Color> colors = Arrays.asList(Color.red, new Color(10,170,10), Color.blue);
	
	JPanel plotterPanel = new JPanel() {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int i = 0;
			double yLines = 10;
			for (int y = 0; y < yLines; y++) {
				g.setColor(Color.gray);
				int yPos = (int) ((y / yLines) * getHeight());
				g.drawLine(0, yPos, getWidth(), yPos);
			}
			for (FCMUltiSetter r : setters) {
				g.setColor(colors.get(i));
				Point a = new Point();
				Double[] rates = r.getVal();
				for (int x = 0; x < getWidth(); x++) {
					int y = (int) (getHeight() * 2 - (stickToRate((float) (x / (float) getWidth() * 2 - 1.0f), rates[0], rates[1], rates[2])));
					Point b = new Point(x, (int) (y * 0.25));
					g.drawLine(a.x, a.y, b.x, b.y);
					a = b;
					if(x == getWidth() - 1) {
						g.drawString("" + Math.round(stickToRate(1.0f, rates[0], rates[1], rates[2]) * 100) / 100.0, getWidth() - 40, (int) (y * 0.25));
					}
					if(x == 0) {
						g.drawString("" + Math.round(stickToRate(-1.0f, rates[0], rates[1], rates[2]) * 100) / 100.0, 10, (int) (y * 0.25));
					}
				}
				i++;
			}
		}
	};
	
	double stickToRate(float x, double rc, double s, double expo) {
        float mul = x > 0 ? 1 : -1;
        x = Math.abs(x);
        return (200.0f * ((Math.pow(x, 4.0f) * expo) + x * (1.0f - expo)) * rc) * (1.0f / (1.0f - (x * s))) * mul;
    }
	
	public RatesPanel() {
		super("Rates");
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 0;
		
		plotterPanel.setPreferredSize(new Dimension(700, 700));

		JPanel setterPanel = new JPanel(new GridLayout(1, 3));
		int i = 0;
		for (FCMUltiSetter setter : setters) {
			setter.setBackground(colors.get(i));
			i++;
		}
		setterPanel.add(setters.get(0));
		setterPanel.add(setters.get(1));
		setterPanel.add(setters.get(2));
		
		for (FCMUltiSetter setter : setters) {
			setter.addChangeListener(e -> plotterPanel.repaint());			
		}
		
		rateSection.getBody().setLayout(new GridBagLayout());
		
		gbc.gridy = 0;
		rateSection.getBody().add(setterPanel, gbc);
		gbc.gridy = 1;
		rateSection.getBody().add(plotterPanel, gbc);
		
		getBody().add(rateSection, gbc);
	}
}