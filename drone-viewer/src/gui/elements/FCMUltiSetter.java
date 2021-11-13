package gui.elements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;

import serial.FCCommand;

public class FCMUltiSetter extends FCSetter<Double[]> {

	private static final long serialVersionUID = 1L;

	private List<JSpinner> spinners = new ArrayList<>();
	private List<String> labels = new ArrayList<>();
	private JButton saveBtn = new JButton("Save");
	private JButton resetBtn = new JButton("Reset");
	
	private List<ChangeListener> changeListeners = new ArrayList<>();
	
	public FCMUltiSetter(FCCommand getter, FCCommand setter, String label, List<String> labels) {
		this(getter, setter, label, labels, true);
	}
	
	public FCMUltiSetter(FCCommand getter, FCCommand setter, String label, List<String> labels, boolean useLabels) {
		super(getter, setter);
		this.labels = labels;
		setLayout(new BorderLayout());
		add(new JLabel(label), BorderLayout.NORTH);
		if(useLabels) {			
			initLabels();
		}
		initCenter();
		initFooter();
		
		saveBtn.addActionListener(e -> save());
		resetBtn.addActionListener(e -> reset());
		
		fcFieldEnable(false);
	}
	
	private void initLabels() {
		if(labels == null) return;
		JPanel left = new JPanel(new GridLayout(labels.size(), 1));
		for (String label : labels) {
			left.add(new JLabel(label));
		}
		add(left, BorderLayout.WEST);
	}
	
	private void initCenter() {
		JPanel center = new JPanel(new GridLayout(labels.size(), 1));
		for (int i = 0; i < labels.size(); i++) {
			JSpinner spinner = getSpinner();
			if(i % 2 == 0) {
				JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor) spinner.getEditor(); jsEditor.getTextField().setBackground(Color.LIGHT_GRAY);
			}
			spinner.addChangeListener(e -> {
				for (ChangeListener changeListener : changeListeners) {
					changeListener.stateChanged(null);
				}
			});
			spinners.add(spinner);
			center.add(spinner);
		}
		add(center, BorderLayout.CENTER);
	}
	
	private void initFooter() {
		JPanel footer = new JPanel();
		add(footer, BorderLayout.SOUTH);
		footer.add(saveBtn);
		footer.add(resetBtn);
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
		for (JSpinner spinner : spinners) {
			spinner.setEnabled(enabled);
		}
		saveBtn.setEnabled(enabled);
		resetBtn.setEnabled(enabled);
	}

	@Override
	protected Double[] parseString(String strVal) {
		String[] split = strVal.substring(1).split(",");
		Double[] arr = new Double[spinners.size()];
		if(split.length != arr.length) {
			return arr;
		}
		for (int i = 0; i < split.length; i++) {
			arr[i] = Double.parseDouble(split[i]);
		}
		return arr;
	}

	@Override
	protected String parseValue(Double[] arr) {
		String str = "";
		for (Double val : arr) {
			str += "," + val;
		}
		return str + ",";
	}

	@Override
	protected void setVal(Double[] val) {
		if(val.length != spinners.size()) return;
		for (int i = 0; i < spinners.size(); i++) {
			spinners.get(i).setValue(val[i]);
		}
	}

	@Override
	public Double[] getVal() {
		Double[] arr = new Double[spinners.size()];
		for (int i = 0; i < spinners.size(); i++) {
			arr[i] = (Double) spinners.get(i).getValue();
		}
		return arr;
	}

	public void addChangeListener(ChangeListener e) {
		changeListeners.add(e);
	}
	
	public boolean removeChangeListener(ChangeListener e) {
		return changeListeners.remove(e);
	}
}
