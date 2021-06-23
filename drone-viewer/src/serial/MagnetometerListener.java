package serial;

public interface MagnetometerListener {

	public void magnetometerDataReceived(double x, double y, double z);
}
