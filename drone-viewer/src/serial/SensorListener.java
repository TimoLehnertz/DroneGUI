package serial;

@FunctionalInterface
public interface SensorListener {

	public void sensorReceived(String sensorName, String sensorSubType, double value);
}
