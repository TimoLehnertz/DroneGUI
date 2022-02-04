package gui.centerPanels;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import xGui.XButton;
import xGui.XPanel;
import xLayouts.XBorderLayout;
import xThemes.XStyle;

public class PropertiesPanel extends XPanel implements ComponentListener {

	private static final long serialVersionUID = 1L;

	private XPanel head = new XPanel(XStyle.BACKGROUND);
	private JComponent body = null;
	
	private SensorPanel sensors = new SensorPanel();
	private PIDPanel pids = new PIDPanel();
	private InsPanel ins = new InsPanel();
	private FCPanel fc = new FCPanel();
	private RatesPanel rates = new RatesPanel();
	private ExtrasPanel extras= new ExtrasPanel();
	private MotorPanel motor = new MotorPanel();
	
	private Map<String, JComponent> properties = new HashMap<>();

	private final Map<String, JComponent> loadedComponents = new HashMap<>();
	
	public PropertiesPanel() {
		super(XStyle.BACKGROUND);
		properties.put("Sensors", 	sensors);
		properties.put("PIDs", 		pids);
		properties.put("INS",	 	ins);
		properties.put("FC", 		fc);
		properties.put("Rates", 	rates);
		properties.put("Extras", 	extras);
		properties.put("Motors", 	motor);
		setLayout(new XBorderLayout());
//		body.setLayout(new XBorderLayout());
		add(head, BorderLayout.NORTH);
//		add(body, BorderLayout.CENTER);
		initHead();
		
		//init first
		if(properties.size() > 0) {
//			Timer t = new Timer(1000, e -> {
//				((Timer) e.getSource()).stop();
				loadedComponents.put("Sensors", properties.get("Sensors"));
				load(loadedComponents.get("Sensors"));
//			});
//			t.start();
		}
		addComponentListener(this);
	}
	
	private void load(JComponent c) {
		if(body != null) {
			remove(body);
		}
		body = c;
		add(body, BorderLayout.CENTER);
		revalidate();
		repaint();
//		body.removeAll();
//		body.add(c, BorderLayout.CENTER);
//		c.setSize(getWidth(), body.getHeight());
//		revalidate();
//		repaint();
	}
	
	private void initHead() {
		for (String name : properties.keySet()) {
			XButton btn = new XButton(name, XButton.STYLE_HIGHLIGHT2);
			btn.addActionListener(e -> {
				if(loadedComponents.containsKey(name)) {
					load(loadedComponents.get(name));
				} else {
					loadedComponents.put(name, properties.get(name));
					load(loadedComponents.get(name));
				}
			});
			head.add(btn);
		}
		head.revalidate();
		head.repaint();
	}

	@Override
	public void componentResized(ComponentEvent e) {
//		body.revalidate();
//		body.repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}