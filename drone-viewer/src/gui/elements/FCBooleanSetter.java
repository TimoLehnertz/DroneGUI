package gui.elements;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import serial.FCCommand;

public class FCBooleanSetter extends FCSetter<Boolean> {

private static final long serialVersionUID = 1L;
	
	private JPanel rightPanel = new JPanel();
	private JCheckBox checkbox = new JCheckBox();

	public FCBooleanSetter(FCCommand getter, FCCommand setter, String label) {
		super(getter, setter);
		
		rightPanel.setLayout(new GridLayout(2, 1));
		setLayout(new GridLayout(1, 2));
		checkbox.setText(label);
		add(checkbox);
		
		checkbox.addActionListener(e -> save());
		
		fcFieldEnable(false);
		get();
	}

	@Override
	protected void succsess() {
		checkbox.setBackground(Color.green);
	}

	@Override
	protected void error() {
		checkbox.setBackground(Color.red);
	}

	@Override
	public void fcFieldEnable(boolean enabled) {
		checkbox.setEnabled(enabled);
	}

	@Override
	protected Boolean parseString(String strVal) {
		return Boolean.parseBoolean(strVal);
	}

	@Override
	protected String parseValue(Boolean objVal) {
		return objVal.toString();
	}

	@Override
	protected void setVal(Boolean val) {
		if(val == null) val = false;
		checkbox.setSelected(val);
	}

	@Override
	public Boolean getVal() {
		return checkbox.isSelected();
	}
}