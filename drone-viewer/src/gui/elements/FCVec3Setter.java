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

import maths.Vec3;
import serial.FCCommand;
import xGui.XButton;
import xGui.XLabel;
import xGui.XPanel;
import xGui.XSpinner;

public class FCVec3Setter extends FCSetter<Vec3> {

	private static final long serialVersionUID = 1L;
	
	private double step;
	
	private List<XSpinner> spinners = new ArrayList<>();
	
	private XLabel label;
	private XButton saveBtn = new XButton("Save");
	private XButton resetBtn = new XButton("Reset");
	
	private XPanel rightPanel = new XPanel();
	protected XPanel content = new XPanel();
	
	public FCVec3Setter(FCCommand getter, FCCommand setter, String label) {
		this(getter, setter, label, 0.0001f);
	}

	public FCVec3Setter(FCCommand getter, FCCommand setter, String label, double step) {
		super(getter, setter);
		this.label = new XLabel(label);
		this.step = step;
		
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
		
		content.setLayout(new GridLayout(3, 1));
		rightPanel.setLayout(new GridLayout(2, 1));
		for (int i = 0; i < 3; i++) {
			addSpinnerPanel(getLabels()[i]);
		}
		fcFieldEnable(false);
	}
	
	private void addSpinnerPanel(String label) {
		XPanel panel = new XPanel(new GridBagLayout());
//		panel.setLayout(new GridBagLayout());
		
		SpinnerModel model = new SpinnerNumberModel(0, -10000000, 10000000, step);
		XSpinner spinner = new XSpinner(model);
		spinner.setEditor(new XSpinner.NumberEditor(spinner, "0.0000"));
		Component mySpinnerEditor = spinner.getEditor();
		JFormattedTextField jftf = ((XSpinner.DefaultEditor) mySpinnerEditor).getTextField();
		jftf.setColumns(5);
		spinner.updateTheme();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new XLabel(label, XLabel.RIGHT), gbc);
		gbc.gridx = 1;
		panel.add(spinner, gbc);
		
		content.add(panel);
		spinners.add(spinner);
	}
	
	protected String[] getLabels() {
		String[] labels = {"x", "y", "z"};
		return labels;
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
	protected Vec3 parseString(String strVal) {
		return new Vec3(strVal);
	}

	@Override
	protected String parseValue(Vec3 objVal) {
		return objVal.toString();
	}

	@Override
	protected void setVal(Vec3 val) {
		if(val == null) val = new Vec3();
		spinners.get(0).setValue(val.x);
		spinners.get(1).setValue(val.y);
		spinners.get(2).setValue(val.z);
	}

	@Override
	public Vec3 getVal() {
		return new Vec3(
				(double) spinners.get(0).getValue(),
				(double) spinners.get(1).getValue(),
				(double) spinners.get(2).getValue());
	}
}