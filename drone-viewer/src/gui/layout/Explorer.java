package gui.layout;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import gui.GuiLogic;
import gui.centerPanels.ExtrasPanel;
import gui.centerPanels.FCPanel;
import gui.centerPanels.InsPanel;
import gui.centerPanels.MotorPanel;
import gui.centerPanels.PIDPanel;
import gui.centerPanels.RadioPanel;
import gui.centerPanels.SensorPanel;

public class Explorer extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private JLabel header = new JLabel("Explorer");
	private JPanel body = new JPanel();
	private GuiLogic logic = GuiLogic.getInstance();
	
	private final Map<JButton, JComponent> entries = new HashMap<>();
	
	public Explorer() {
		super();
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		body.setLayout(new GridLayout(30, 1));
		
		SensorPanel sensorPanel = new SensorPanel();
		
		entries.put(new JButton("Sensors"), sensorPanel);
		entries.put(new JButton("INS"), new InsPanel());
		entries.put(new JButton("Radio"), new RadioPanel());
		entries.put(new JButton("Motors"), new MotorPanel());
		entries.put(new JButton("PIDs"), new PIDPanel());
		entries.put(new JButton("FC"), new FCPanel());
		entries.put(new JButton("Extras"), new ExtrasPanel());
		logic.loadCenter(sensorPanel);
		Timer t = new Timer(100, e -> {
			
			((Timer) e.getSource()).stop();
		});
		t.start();
		
		initEntries();
	}
	
	void initEntries() {
		for (Entry<JButton, JComponent> entry : entries.entrySet()) {
			body.add(entry.getKey());
			entry.getKey().addActionListener(e -> logic.loadCenter(entry.getValue()));
		}
	}
}