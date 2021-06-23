package serial;

public interface AccelerometerListener {

	public void accelerometerDataReceived(double x, double y, double z);
}