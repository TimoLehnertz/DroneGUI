package gui.elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import maths.Quaternion;
import serial.FCCommand;
import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;
import xGui.XSpinner;

public class FCQuaternionSetter extends FCSetter<Quaternion> {

	private static final long serialVersionUID = 1L;

private double step;
	
	protected static String[] VEC3_LABELS = {"w", "x", "y", "z"};
	
	private List<XSpinner> spinners = new ArrayList<>();
	
	private XLabel label;
	private XButton saveBtn = new XButton("Save");
	private XButton resetBtn = new XButton("Reset");
	
	private XPanel rightPanel = new XPanel();
	protected XPanel content = new XPanel();
	
	public FCQuaternionSetter(FCCommand getter, FCCommand setter, String label) {
		super(getter, setter);
		this.label = new XLabel(label);
//		this.step = step;
		
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
		
		content.setLayout(new GridLayout(4, 1));
		rightPanel.setLayout(new GridLayout(2, 1));
		for (int i = 0; i < 4; i++) {
			addSpinnerPanel(VEC3_LABELS[i]);
		}
		fcFieldEnable(false);
	}
	
	private void addSpinnerPanel(String label) {
		XPanel panel = new XPanel();
		panel.setLayout(new GridBagLayout());
		
		SpinnerModel model = new SpinnerNumberModel(0, -10000000, 10000000, step);
		XSpinner spinner = new XSpinner(model);
		spinner.setEditor(new XSpinner.NumberEditor(spinner, "0.0000"));
		Component mySpinnerEditor = spinner.getEditor();
		JFormattedTextField jftf = ((XSpinner.DefaultEditor) mySpinnerEditor).getTextField();
		jftf.setColumns(5);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new XLabel(label), gbc);
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
		for (XSpinner spinner : spinners) {
			spinner.setEnabled(enabled);
		}
	}

	@Override
	protected Quaternion parseString(String strVal) {
		return new Quaternion(strVal);
	}

	@Override
	protected String parseValue(Quaternion quat) {
		return quat.toString();
	}

	@Override
	protected void setVal(Quaternion val) {
		if(val == null) val = new Quaternion();
		spinners.get(0).setValue(val.w);
		spinners.get(1).setValue(val.x);
		spinners.get(2).setValue(val.y);
		spinners.get(3).setValue(val.z);
	}

	@Override
	public Quaternion getVal() {
		return new Quaternion(
				(double) spinners.get(0).getValue(),
				(double) spinners.get(1).getValue(),
				(double) spinners.get(2).getValue(),
				(double) spinners.get(3).getValue());
	}
}