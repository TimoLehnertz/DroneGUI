package gui.elements;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;

import gui.GuiLogic;
import serial.FCCommand;
import serial.SerialInterface;

public abstract class FCSetter<T> extends JPanel {

	public static final long serialVersionUID = 1L;

	private FCCommand getter;
	private FCCommand setter;
	protected boolean disableOnSave = true;
	GuiLogic logic = GuiLogic.getInstance();
	
	public BooleanSupplier saveConsition = null;
	
	private SerialInterface serial = GuiLogic.getInstance().getSerialInterface();
	
	public FCSetter(FCCommand getter, FCCommand setter) {
		super();
		serial.addOpenListeners(() -> {
			if(logic.isLiteMode()) {
				fcFieldEnable(true);
			} else {				
				get();	
			}
		});
		this.getter = getter;
		this.setter = setter;
		serial.addFcSetterListener(this);
		get();
	}
	
	/**
	 * displays succsess
	 */
	protected abstract void succsess();
	
	/**
	 * Displays an error
	 */
	protected abstract void error();
	
	/**
	 * Resets the FCSetter to the value of lastVal
	 */
	protected void reset() {
		get(true);
//		setVal(lastVal);
//		setEnabledTrueDelay(100);
	}
	
	/**
	 * Sets the enabled status for this Setter
	 * @param enabled
	 */
	public abstract void fcFieldEnable(boolean enabled);
	
	/**
	 * initiates the get Call and sets the setters value to the received value
	 */
	public void get() {
		get(false);
	}
	public void get(boolean force) {
		if(!force && logic.isLiteMode()) return;
		if(!serial.isConnected()) return;
		serial.get(getter, (succsess, res) -> {
			if(!succsess) {
				System.out.println("retrying to get " + getter.name());
				get();
				return;
			}
			setVal(parseString(res));
			setEnabledTrueDelay(100);
		});
	}
	
	private void setEnabledTrueDelay(int delay) {
		Timer t = new Timer(100, e -> {
			((Timer) e.getSource()).stop();				
			fcFieldEnable(true);
		});
		t.start();
	}
	
	/**
	 * parses a string received from the fc into a useful format
	 * has to return a new instance every time called
	 * @param val
	 */
	protected abstract T parseString(String strVal);
	
	/**
	 * parses an Object value into a string to be send to the fc
	 * @param val
	 */
	protected abstract String parseValue(T objVal);
	
	/**
	 * Sets this Field to the Objects value
	 * @param val
	 */
	protected abstract void setVal(T val);
	
	/**
	 * @return this fields Value
	 */
	public abstract T getVal();

	/**
	 * Sends this fields value to the fc
	 */
	protected void save() {
		save(null);
	}
	
	/**
	 * retreive a standart JSpinner
	 * @return
	 */
	protected JSpinner getSpinner() {
		SpinnerModel model = new SpinnerNumberModel(0, -10000000, 10000000, 0.00001f);
		JSpinner spinner = new JSpinner(model);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "0.00000"));
		JFormattedTextField jftf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		jftf.setColumns(5);
		return spinner;
	}
	
	protected void save(Consumer<Boolean> consumer) {
		if(saveConsition != null && !saveConsition.getAsBoolean()) {
			reset();
			System.out.println("Condition for " + getter.name() + " is false!");
			if(consumer != null) consumer.accept(false);
			return;
		}
		if(disableOnSave) {
			fcFieldEnable(false);
		}
		serial.set(setter, parseValue(getVal()), succsess -> {
			if(succsess) {
				succsess();
				if(consumer != null) consumer.accept(true);
			} else {
				System.out.println("couldnt save");
				error();
				reset();
				if(consumer != null) consumer.accept(false);
			}
			setEnabledTrueDelay(100);
		});
	}
	
	public void refresh() {
		get();
	}
}