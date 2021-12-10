package gui.centerPanels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Timer;

import xGui.XPanel;
import xGui.XScrollPane;
import xThemes.XStyle;
import xThemes.XStyle.BackgroundType;

public class CenterPanel extends XPanel implements ComponentListener {
	
	private static final long serialVersionUID = 1L;

	protected XPanel body = new XPanel(new XStyle(BackgroundType.none));
	XScrollPane scroll;
	
	public CenterPanel() {
		super();
		
		scroll = new XScrollPane(body);
		scroll.setHorizontalScrollBarPolicy(XScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(XScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.getVerticalScrollBar().setUnitIncrement(16);

		add(scroll);
		addComponentListener(this);
//		Timer t = new Timer(300, e -> {
////			scroll.setPreferredSize(new Dimension(getWidth(), getHeight()));
//			revalidate();
//			repaint();
//		});
//		t.start();
	}
	
	public XPanel getBody() {
		return body;
	}
	
	@Override
	public void remove(Component comp) {
		body.remove(comp);
	}

	@Override
	public void componentResized(ComponentEvent e) {
		scroll.setPreferredSize(new Dimension(getWidth(), getHeight()));
		revalidate();
		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}
