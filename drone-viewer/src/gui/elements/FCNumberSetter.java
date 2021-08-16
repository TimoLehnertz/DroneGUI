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
import javax.swing.Timer;

import gui.GuiLogic;
import maths.Vec3;
import serial.FCCommand;
import serial.SerialInterface;

public class FCNumberSetter extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<JSpinner> spinners = new ArrayList<>();
	
	SerialInterface serial = GuiLogic.getInstance().getSerialInterface();
	
	private FCCommand getter;
	private FCCommand setter;
	private int type;
	
	public static final int TYPE_VEC3 = 0;
	public static final int TYPE_FLOAT = 1;
	
	private JLabel label;
	private JPanel content = new JPanel();
	
	private double min;
	private double max;
	private double step;
	
	private static final String[] VEC3_LABELS = {"x", "y", "z"};
	
	private JButton saveBtn = new JButton("Save");
	private JButton resetBtn = new JButton("Reset");
	private JPanel rightPanel = new JPanel();
	private Object lastVal = null;
	
	public FCNumberSetter(FCCommand getter, FCCommand setter, String label) {
		this(getter, setter, label, TYPE_VEC3);
	}
	
	public FCNumberSetter(FCCommand getter, FCCommand setter, String label, int type) {
		this(getter, setter, label, type, -100000, 100000, 0.0001f);
	}
	
	public FCNumberSetter(FCCommand getter, FCCommand setter, String label, int type, double min, double max, double step) {
		super();
		serial.addOpenListeners(() -> get());
		this.getter = getter;
		this.setter = setter;
		this.type = type;
		this.label = new JLabel(label);
		this.min = min;
		this.max = max;
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
		
//		setBackground(Color.GRAY);
		switch(type) {
		case TYPE_VEC3: initVec3(); break;
		case TYPE_FLOAT: initFloat(); break;
		}
		setEnabled(false);
	}
	
	private void get() {
		switch(type) {
		case TYPE_VEC3: getVec3(); break;
		case TYPE_FLOAT: getFloat(); break;
		}
	}
	
	private void addSpinnerPanel(String label) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		
		SpinnerModel model = new SpinnerNumberModel(0, min, max, step);
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
	
	private void initFloat() {
		content.setLayout(new GridLayout(1, 1));
		rightPanel.setLayout(new GridLayout(1, 2));
		addSpinnerPanel("");
		getFloat();
	}
	
	private void initVec3() {
		content.setLayout(new GridLayout(3, 1));
		rightPanel.setLayout(new GridLayout(2, 1));
		for (int i = 0; i < 3; i++) {
			addSpinnerPanel(VEC3_LABELS[i]);
		}
		getVec3();
	}
	
	private void save() {
		serial.set(setter, getValue(), succsess -> {
			if(succsess) {
				saveBtn.setBackground(Color.green);
				lastVal = getValue();
			} else {
				System.out.println("coudnt save");
				reset();
				saveBtn.setBackground(Color.red);				
			}
			setEnabled(true);
		});
		setEnabled(false);
	}
	
	private Object getValue() {
		switch(type) {
		case TYPE_VEC3: return getVec3Value();
		case TYPE_FLOAT: return getFloatValue();
		}
		return null;
	}
	
	private void reset() {
		if(lastVal == null) {
			for (JSpinner spinner : spinners) {
				spinner.setValue(0);
			}
			return;
		}
		switch(type) {
		case TYPE_VEC3: setVec3Value((Vec3) lastVal); break;
		}
	}
	
	private void setFloat(double num) {
		spinners.get(0).setValue(num);
	}
	
	private void setVec3Value(Vec3 vec) {
		spinners.get(0).setValue(vec.x);
		spinners.get(1).setValue(vec.y);
		spinners.get(2).setValue(vec.z);
	}
	
	private double getFloatValue() {
		if(type != TYPE_FLOAT) throw new RuntimeException("cant get float from type " + type);
		return (double) spinners.get(0).getValue();
	}
	
	private Vec3 getVec3Value() {
		if(type != TYPE_VEC3) throw new RuntimeException("cant get vec 3 from type " + type);
		return new Vec3((double) spinners.get(0).getValue(), (double) spinners.get(1).getValue(), (double) spinners.get(2).getValue());
	}
	
	private void getFloat() {
		serial.get(getter, res -> {
			double num = Double.parseDouble(res);
			setFloat(num);
			setEnabled(true);
			lastVal = num;
		});
	}
	
	private void getVec3() {
		serial.get(getter, res -> {
			Vec3 vec = new Vec3(res);
			setVec3Value(vec);
			setEnabled(true);
			lastVal = vec;
		});
	}
	
	public void refresh() {
		get();
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		if(enabled) {
			Timer t = new Timer(10, e -> {
				for (JSpinner spinner : spinners) {
					spinner.setEnabled(enabled);
				}	
				saveBtn.setEnabled(enabled);
				resetBtn.setEnabled(enabled);
				((Timer) e.getSource()).stop();
			});
			t.start();
		} else {
			for (JSpinner spinner : spinners) {
				spinner.setEnabled(enabled);
			}	
			saveBtn.setEnabled(enabled);
			resetBtn.setEnabled(enabled);
		}
	}
}