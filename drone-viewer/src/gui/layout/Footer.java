package gui.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.PrintStream;

import javax.swing.JPanel;

import gui.GuiLogic;
import gui.footer.Console;
import serial.LineListener;
import serial.PrintListener;

public class Footer extends JPanel implements LineListener, PrintListener {

	private static final long serialVersionUID = 1L;

	Console in = new Console("Serial RX");
	Console out = new Console("Serial TX");
	Console sys = new Console("Local console");
	
	JPanel head = new JPanel();
	JPanel body = new JPanel();
	
	GuiLogic logic = GuiLogic.getInstance();
	
	public Footer() {
		super();
		setLayout(new BorderLayout());
		add(head, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		
		body.setBackground(Color.darkGray);
		
		body.setLayout(new GridLayout(1, 3, 3, 0));
		
		in.setPreferredSize(new Dimension(0, 120));
		
		body.add(sys);
		body.add(in);
		body.add(out);
		
		logic.getSerialInterface().addLineListener(this);
		logic.getSerialInterface().addPrintListener(this);
		
		System.setOut(new PrintStream(System.out) {
			public void println(String s) {
			    sys.log(s, true);
			    super.println(s);
			}
		});
	}

	@Override
	public void lineReceived(String line) {
		in.log(line, true);
	}

	@Override
	public void linePrinted(String line) {
		out.log(line, false);
	}
}