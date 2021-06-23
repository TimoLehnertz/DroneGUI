package serial;

public interface INSListener {

	public void insDataReceived(double w, double x, double y, double z);
}