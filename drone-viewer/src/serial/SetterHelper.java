package serial;

public class SetterHelper {

	String checkMsg;
	SetListener listener;
	long uid;
	
	public SetterHelper(String checkMsg, SetListener listener, long uid) {
		super();
		this.checkMsg = checkMsg;
		this.listener = listener;
		this.uid = uid;
	}
}