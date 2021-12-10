package gui.footer;

import java.awt.BorderLayout;
import java.io.PrintStream;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import gui.GuiLogic;
import serial.LineListener;
import serial.PrintListener;
import xGui.XButton;
import xGui.XConsole;
import xGui.XLabel;
import xGui.XMenuBar;
import xGui.XPanel;
import xGui.XTriplePanel;
import xThemes.XStyle;

public class DroneConsole extends XPanel implements LineListener, PrintListener {

	private static final long serialVersionUID = 1L;

	private XConsole console = new XConsole();
	private XTriplePanel head = new XTriplePanel();
	private XLabel label = new XLabel("Serial");
	
	private static final int SOURCE_SERIAL = 0;
	private static final int SOURCE_JAVA = 1;
	
	private int source = SOURCE_SERIAL;
	
	private static boolean odd = true;
	
	public DroneConsole() {
		super(XStyle.BACKGROUND);
		setLayout(new BorderLayout());
		add(head, BorderLayout.NORTH);
		add(console, BorderLayout.CENTER);
		
		XMenuBar menu = new XMenuBar();
		JMenu soureMenu = new JMenu("Source");
		JMenuItem serialItem = new JMenuItem("Serial");
		serialItem.addActionListener(e -> {
			console.clear();
			source = SOURCE_SERIAL;
			label.setText("Serial");
		});
		soureMenu.add(serialItem);
		JMenuItem javaItem = new JMenuItem("Terminal");
		javaItem.addActionListener(e -> {
			console.clear();
			source = SOURCE_JAVA;
			label.setText("Terminal");
		});
		soureMenu.add(javaItem);
		menu.add(soureMenu);
		
		head.getCenter().add(label);
		head.getLeft().add(menu);
		XButton clearBtn = new XButton("Clear");
		clearBtn.addActionListener(e -> console.clear());
		head.getRight().add(clearBtn);
		
		GuiLogic.getInstance().getSerialInterface().addLineListener(this);
		GuiLogic.getInstance().getSerialInterface().addPrintListener(this);
		System.setOut(new PrintStream(System.out) {
			public void println(String s) {
				if(source == SOURCE_JAVA) {
					console.log(s, true);
				}
				super.println(s);
			}
		});
		if(odd) {
			source = SOURCE_SERIAL;
			label.setText("Serial");
		} else {
			source = SOURCE_JAVA;
			label.setText("Terminal");
		}
		odd = !odd;
	}

	@Override
	public void linePrinted(String line) {
		if(source == SOURCE_SERIAL) {
			console.log(line, false);
		}
	}

	@Override
	public void lineReceived(String line) {
		if(source == SOURCE_SERIAL) {
			console.log(line, true);
		}
	}
}