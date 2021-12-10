package gui.elements;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import serial.FCCommand;
import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;
import xGui.XSpinner;

public class FCNumberSetter extends FCSetter<Double> {

	private static final long serialVersionUID = 1L;
	
	private List<XSpinner> spinners = new ArrayList<>();
	
	private XLabel label;
	private XButton saveBtn = new XButton("Save");
	private XButton resetBtn = new XButton("Reset");
	
	private XPanel rightPanel = new XPanel();
	private XPanel content = new XPanel();
	
	private boolean integer;
	
	public FCNumberSetter(FCCommand getter, FCCommand setter, String label) {
		this(getter, setter, label, false);
	}

	public FCNumberSetter(FCCommand getter, FCCommand setter, String label, boolean integer) {
		super(getter, setter);
		this.label = new XLabel(label);
		this.integer = integer;
		
		saveBtn.addActionListener(e -> save());
		resetBtn.addActionListener(e -> reset());
		
		rightPanel.add(saveBtn);
		rightPanel.add(resetBtn);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.ipadx = 5;
		add(this.label, gbc);
		
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		add(content, gbc);
		
		gbc.gridx = 1;
		add(rightPanel, gbc);
		
		rightPanel.setLayout(new GridLayout(2, 1));
		addSpinnerPanel(label);
		fcFieldEnable(false);
	}
	
	private void addSpinnerPanel(String label) {
		XPanel panel = new XPanel();
		panel.setLayout(new GridBagLayout());

		XSpinner spinner = getSpinner(integer);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
//		panel.add(new JLabel(label), gbc);
		gbc.gridx = 1;
		panel.add(spinner, gbc);
		
		content.add(panel);
		spinners.add(spinner);
	}

	@Override
	protected void succsess() {
		saveBtn.setBackground(Color.green);
	}

	@Override
	protected void error() {
		saveBtn.setBackground(Color.red);
	}

	@Override
	public void fcFieldEnable(boolean enabled) {
		if(spinners == null) spinners = new ArrayList<>();
		saveBtn.setEnabled(enabled);
		resetBtn.setEnabled(enabled);
		spinners.get(0).setEnabled(enabled);
	}

	@Override
	protected Double parseString(String strVal) {
		return Double.parseDouble(strVal);
	}

	@Override
	protected String parseValue(Double objVal) {
		return objVal.toString();
	}

	@Override
	protected void setVal(Double val) {
		if(val == null) val = 0D;
		spinners.get(0).setValue(val);
	}

	@Override
	public Double getVal() {
		return (Double) spinners.get(0).getValue();
	}
}