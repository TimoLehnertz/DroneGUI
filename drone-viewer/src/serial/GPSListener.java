package serial;

public interface GPSListener {

	public void gpsDataReceived(double lat, double lng);
}