package gui.footer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Console extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final String DATE_FORMAT_NOW = "HH:mm:ss.SSS";
	
	List<LogLine> lines = new ArrayList<>();
	
	int maxLines = 10;
	
	JPanel head = new JPanel();
	JPanel body = new JPanel();
	JLabel nameLabel;
	
	public Console(String name) {
		super();
		setLayout(new BorderLayout());
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		body.setLayout(null);
		body.setBackground(Color.black);
		JLabel nameLabel = new JLabel(name);
		head.add(nameLabel);
		nameLabel.setBackground(Color.DARK_GRAY);
		nameLabel.setForeground(Color.white);
		head.setBackground(Color.DARK_GRAY);
	}
	
	public void log(String msg, boolean in) {
		LogLine log = new LogLine(now() + "  |  " + (in ? "<<  " : ">>  ") + msg, false);
		lines.add(log);
		body.add(log);
		if(lines.size() >= maxLines) {
			body.remove(lines.get(0));
			lines.remove(0);
		}
		int used = 0;
		int padding = 2;
		int height = 16;
		boolean zebra = false;
		for (int i = Math.min(maxLines - 1, lines.size() - 1); i >= 0; i--) {
			lines.get(i).setSize(body.getWidth() + 500, height);
			lines.get(i).setLocation(0, body.getHeight() - (used + height));
			lines.get(i).setZebra(zebra);
			used += height + padding;
			zebra = !zebra;
		}
	}
	
	public void setName(String name) {
		nameLabel.setText(name);
	}
	
	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
}
