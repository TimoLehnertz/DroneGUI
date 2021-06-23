package serial;

@FunctionalInterface
public interface LineListener {

	public void lineReceived(String line);
}
