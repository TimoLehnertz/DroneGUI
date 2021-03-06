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

public class FCMat3Setter extends FCSetter<Double[]> {

	private static final long serialVersionUID = 1L;
	
	private List<XSpinner> spinners = new ArrayList<>();
	private XPanel spinnerPanel = new XPanel();
	private XPanel rightPanel = new XPanel();
	private XButton saveBtn = new XButton("Save");
	private XButton resetBtn = new XButton("Reset");
	private XLabel label;

	public FCMat3Setter(FCCommand getter, FCCommand setter, String label) {
		super(getter, setter);
		this.label = new XLabel(label);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		saveBtn.addActionListener(e -> save());
		resetBtn.addActionListener(e -> reset());
		
		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(saveBtn);
		rightPanel.add(resetBtn);
		
		gbc.gridwidth = 2;
		add(this.label, gbc);
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		add(spinnerPanel, gbc);
		gbc.gridx = 1;
		add(rightPanel, gbc);
		spinnerPanel.setLayout(new GridLayout(3, 3));
		initSpinners();
		fcFieldEnable(false);
	}

	private void initSpinners() {
		for (int i = 0; i < 9; i++) {
			XSpinner spinner = getSpinner();
			spinners.add(spinner);
			spinnerPanel.add(spinner);
		}
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
		for (XSpinner jSpinner : spinners) {
			jSpinner.setEnabled(enabled);
		}
		saveBtn.setEnabled(enabled);
		resetBtn.setEnabled(enabled);
	}

	@Override
	protected Double[] parseString(String strVal) {
		Double[] arr = new Double[9];
		String[] split = strVal.split(",");
		if(split.length != 9) return arr;
		for (int i = 0; i < split.length; i++) {
			arr[i] = Double.parseDouble(split[i]);
		}
		return arr;
	}

	@Override
	protected String parseValue(Double[] objVal) {
		String str = "";
		for (Double d : objVal) {
			System.out.println(Math.round(d * 1000000d) / 1000000d);
			str += Math.round(d * 100000f) / 100000f + ",";
		}
		return str;
	}

	@Override
	protected void setVal(Double[] val) {
		for (int i = 0; i < val.length; i++) {
			spinners.get(i).setValue(val[i]);
		}
	}

	@Override
	public Double[] getVal() {
		Double[] arr = new Double[9];
		for (int i = 0; i < spinners.size(); i++) {
//			arr[i] = Double.valueOf(Math.round((Double) spinners.get(i).getValue() * 1000000) / 1000000);
			arr[i] = (Double) spinners.get(i).getValue();
//			System.out.println(arr[i]);
		}
		return arr;
	}
}