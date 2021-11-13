package gui.elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import maths.Vec3;
import serial.FCCommand;

public class FCVec3Setter extends FCSetter<Vec3> {

	private static final long serialVersionUID = 1L;
	
	private double step;
	
	private List<JSpinner> spinners = new ArrayList<>();
	
	private JLabel label;
	private JButton saveBtn = new JButton("Save");
	private JButton resetBtn = new JButton("Reset");
	
	private JPanel rightPanel = new JPanel();
	protected JPanel content = new JPanel();
	
	public FCVec3Setter(FCCommand getter, FCCommand setter, String label) {
		this(getter, setter, label, 0.0001f);
	}

	public FCVec3Setter(FCCommand getter, FCCommand setter, String label, double step) {
		super(getter, setter);
		this.label = new JLabel(label);
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
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//		panel.setLayout(new GridBagLayout());
		
		SpinnerModel model = new SpinnerNumberModel(0, -10000000, 10000000, step);
		JSpinner spinner = new JSpinner(model);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "0.0000"));
		Component mySpinnerEditor = spinner.getEditor();
		JFormattedTextField jftf = ((JSpinner.DefaultEditor) mySpinnerEditor).getTextField();
		jftf.setColumns(5);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel(label), gbc);
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
		for (JSpinner spinner : spinners) {
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