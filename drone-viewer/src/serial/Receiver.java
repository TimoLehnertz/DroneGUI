package serial;

@FunctionalInterface
public interface Receiver {

	public void receive(boolean succsess, String value);
}