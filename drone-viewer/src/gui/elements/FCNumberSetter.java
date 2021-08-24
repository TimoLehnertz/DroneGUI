package gui.elements;

import java.awt.Color;
import java.awt.Component;
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

import serial.FCCommand;

public class FCNumberSetter extends FCSetter<Double> {

	private static final long serialVersionUID = 1L;
	
	private double step;
	
	private List<JSpinner> spinners = new ArrayList<>();
	
	private JLabel label;
	private JButton saveBtn = new JButton("Save");
	private JButton resetBtn = new JButton("Reset");
	
	private JPanel rightPanel = new JPanel();
	private JPanel content = new JPanel();
	
	public FCNumberSetter(FCCommand getter, FCCommand setter, String label) {
		this(getter, setter, label, 0.00001f);
	}

	public FCNumberSetter(FCCommand getter, FCCommand setter, String label, double step) {
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
		
		rightPanel.setLayout(new GridLayout(2, 1));
		addSpinnerPanel(label);
		fcFieldEnable(false);
	}
	
	private void addSpinnerPanel(String label) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		SpinnerModel model = new SpinnerNumberModel(0, -10000000, 10000000, step);
		JSpinner spinner = new JSpinner(model);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "0.00000"));
		Component mySpinnerEditor = spinner.getEditor();
		JFormattedTextField jftf = ((JSpinner.DefaultEditor) mySpinnerEditor).getTextField();
		jftf.setColumns(5);

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