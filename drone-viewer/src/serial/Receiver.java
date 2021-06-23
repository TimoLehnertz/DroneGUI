package serial;

@FunctionalInterface
public interface Receiver {

	public void receive(String value);
}
