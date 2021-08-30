package gui.elements;

import java.util.Arrays;

import serial.FCCommand;

public class FCPidSetter extends FCMUltiSetter {

	private static final long serialVersionUID = 1L;

	public FCPidSetter(FCCommand getter, FCCommand setter, String label) {
		this(getter, setter, label, true);
	}
	
	public FCPidSetter(FCCommand getter, FCCommand setter, String label, boolean useLabels) {
		super(getter, setter, label, Arrays.asList("P", "I", "D", "D Lpf", "iMul", "I max Buildup", "I max out", "d Max"), useLabels);
	}

}
