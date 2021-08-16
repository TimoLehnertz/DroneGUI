package gui.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import gui.GuiLogic;
import serial.SerialInterface;

public class NumberSetter extends JPanel {

	private static final long serialVersionUID = 1L;

	List<NumberListener> listeners = new ArrayList<>();
	
	private JLabel label;
	private JSpinner input;
	private JButton enterBtn = new JButton("Save");
	private JButton resetBtn = new JButton("Reset");
	SerialInterface serial = GuiLogic.getInstance().getSerialInterface();
	
	private double lastVal;
	
	
	public NumberSetter(String label, double initValue, double min, double max, double step) {
		super();
		SpinnerNumberModel model = new SpinnerNumberModel(initValue, min, max, step);
		input = new JSpinner(model);

		lastVal = initValue;
		this.label = new JLabel(label);
		add(this.label);
		add(input);
		add(enterBtn);
		add(resetBtn);
		setBorder(BorderFactory.createBevelBorder(2));
		enterBtn.addActionListener(e -> save());
		resetBtn.addActionListener(e -> reset());
	}
	
	private void save() {
		double val = (double) input.getValue();
		for (NumberListener l : listeners) {
			l.numberEntered(val);
		}
		lastVal = val;
		setEnabled(false);
	}
	
	private void reset() {
		input.setValue(lastVal);
	}
	
	public void setValue(double val) {
		this.input.setValue(val);
		lastVal = val;
	}
	
	public void saved() {
		setEnabled(true);
		enterBtn.setBackground(Color.green);
	}
	
	@Override
	public void setEnabled(boolean enable) {
		input.setEnabled(enable);
		enterBtn.setEnabled(enable);
		resetBtn.setEnabled(enable);
	}
	
	public boolean addNumberListener(NumberListener l) {
		return listeners.add(l);
	}
}