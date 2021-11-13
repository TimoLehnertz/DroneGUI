package gui.elements;

import serial.FCCommand;

public class FCRatesSetter extends FCVec3Setter {

	private static final long serialVersionUID = 1L;

	protected static String[] VEC3_LABELS = {"RC", "Super", "RC expo"};
	
	public FCRatesSetter(FCCommand getter, FCCommand setter, String label) {
		super(getter, setter, label);
	}
	
	@Override
	protected String[] getLabels() {
		String[] labels = {"     RC", "  Super", "RC expo"};
		return labels;
	}
}