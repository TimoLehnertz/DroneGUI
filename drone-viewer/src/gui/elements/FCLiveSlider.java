package gui.elements;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;

import serial.FCCommand;

public class FCLiveSlider extends FCSetter<Double> {

	private static final long serialVersionUID = 1L;
	JSlider slider;
	JLabel valueLabel;
	
	long lastUpdate = System.currentTimeMillis();
	
	int minDelay = 10;
	long updateTmp = lastUpdate;
	
	public FCLiveSlider(FCCommand getter, FCCommand setter, String label, int min, int max, int value) {
		super(getter, setter);
		slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
		valueLabel = new JLabel("" + value);
		slider.addChangeListener(e -> update());
		fcFieldEnable(false);
		
		setLayout(new GridLayout(1, 3));
		add(new JLabel(label));
		add(slider);
		add(valueLabel);
		
//		slider.setBackground(Color.gray);
		
		disableOnSave = false;
		
		get();
	}
	
	private void update() {
		long now = System.currentTimeMillis();
		if(now - lastUpdate > minDelay) {
			System.out.println("saving");
			save(succsess -> {if(succsess) valueLabel.setText("" + slider.getValue());});
			lastUpdate = now;
		} else {
			updateTmp = lastUpdate;
			Timer t = new Timer(minDelay, e -> {
				((Timer) e.getSource()).stop();
				if(updateTmp == lastUpdate) {
					update();
				}
			});
			t.start();
		}
	}

	@Override
	protected void succsess() {
//		slider.setBackground(Color.gray);
	}

	@Override
	protected void error() {
//		slider.setBackground(Color.red);
	}

	@Override
	public void fcFieldEnable(boolean enabled) {
		slider.setEnabled(enabled);
	}

	@Override
	protected Double parseString(String strVal) {
		return Double.parseDouble(strVal);
	}

	@Override
	protected String parseValue(Double objVal) {
		return "" + objVal;
	}

	@Override
	protected void setVal(Double val) {
		slider.setValue((int) Math.round(val));
		valueLabel.setText("" + val);
	}

	@Override
	public Double getVal() {
		return (double) slider.getValue();
	}

}
