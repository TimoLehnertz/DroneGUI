package serial;

public interface GyroListener {

	public void gyroDataReceived(double x, double y, double z);
}