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

import gui.GuiLogic;
import gui.centerPanels.InsPanel;

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
		
		entries.put(new JButton("INS"), new InsPanel());
		entries.put(new JButton("Moin"), new JLabel("Moin"));
		
		initEntries();
	}
	
	void initEntries() {
		for (Entry<JButton, JComponent> entry : entries.entrySet()) {
			body.add(entry.getKey());
			entry.getKey().addActionListener(e -> logic.loadCenter(entry.getValue()));
		}
	}
}