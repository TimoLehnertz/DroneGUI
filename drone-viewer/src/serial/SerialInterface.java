package serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

public class SerialInterface implements SerialReceiveListener {

	private static SerialInterface instance = null;
	private Serial serial;
	private static final double SETTER_TIMEOUT = 1;//seconds

	long uid = 0; //uid used for getters and setters
	
	List<SetterHelper> setters = new ArrayList<>();
	
	/**
	 * listeners
	 */
	List<LineListener> lineListeners = new ArrayList<>();
	List<PrintListener> printListeners = new ArrayList<>();
	List<SensorListener> sensorListeners = new ArrayList<>();
	
	/**
	 * getter comunication
	 */
	Map<Long, Receiver> getters = new HashMap<>();
	
	private SerialInterface() {
		super();
		serial = new Serial("Nano 33 BLE");
		serial.addReceiveListener(this);
	}
	
	public static SerialInterface getInstance() {
		if(instance == null) instance = new SerialInterface();
		return instance;
	}
	
	public void selectComPort(String name) {
		serial.selectPort(name);
	}
	
	public void get(FCCommand command, Receiver receiver) {
		getters.put(uid, receiver);
		send(command.name() + " " + uid);
		uid++;
	}
	
	public void set(FCCommand setter, Object value, SetListener listener) {
		String msg = value.toString();
		SetterHelper s = new SetterHelper(msg, listener, uid);
		setters.add(s);
		Timer t = new Timer((int) SETTER_TIMEOUT * 1000, e -> {
			if(setters.contains(s)) {
				System.out.println("timout");
				s.listener.setFinished(false);
				setters.remove(s);
			}
			((Timer) e.getSource()).stop();
		});
		//send
		send(setter.name() + " " + uid + " " + msg);
		t.start();
		uid++;
	}
	
	public void post(FCCommand command, String value) {
		send(command.name() + " " + value);
	}
	
	public void sendDo(FCCommand command) {
		send(command.name());
	}
	
	public boolean addLineListener(LineListener l) {
		return lineListeners.add(l);
	}
	
	public boolean removeLineListener(LineListener l) {
		return lineListeners.remove(l);
	}
	
	public boolean addPrintListener(PrintListener l) {
		return printListeners.add(l);
	}
	
	public boolean removePrintListener(PrintListener l) {
		return printListeners.remove(l);
	}
	
	public boolean addSensorListener(SensorListener l) {
		return sensorListeners.add(l);
	}
	
	public boolean removeSensorListener(SensorListener l) {
		return sensorListeners.remove(l);
	}
	
	private void processResponse(String line) {
		String split[] = line.split(" ");
		if(split.length < 3) {
			return;
		}
		long id = Long.parseLong(split[1]);
		
		int responseStart = line.indexOf(' ', 7);
		String response = line.substring(responseStart + 1);
		/**
		 * Setters
		 */
		for (SetterHelper setter : setters) {
			if(setter.uid == id) {
				setter.listener.setFinished(setter.checkMsg.contentEquals(response));
				setters.remove(setter);
				return;
			}
		}
		/**
		 * Getters
		 */
		if(getters.containsKey(id)) {			
			getters.get(id).receive(response);
			getters.remove(id);
		}
	}
	
	/**
	 * POST COmmands
	 * 	FC_POST_SENSOR
	 * 		<SensorName>;<SensorSubType>;<float>
	 * @param line
	 */
	private void processPost(String line) {
		String split[] = line.split(" ");
		if(split.length < 2 || !line.contains("FC_POST_")) {
			return;
		}
		String command = split[0];
		int valStart = command.length() + 1;
		String val = line.substring(valStart);
		FCCommand fcCommand = FCCommand.valueOf(command);
		if(fcCommand == FCCommand.FC_POST_SENSOR) {
			processSensor(val);
		}
	}
	
	private void processSensor(String body) {
		String[] split =  body.split(";");
		if(split.length < 3) {
			System.err.println("invalid sensordata");
			return;
		}
		String sensorName = split[0];
		String sensorSubType = split[1];
		int valueStart = sensorName.length() + 1 + sensorSubType.length() + 1;
		String value = body.substring(valueStart, body.length());
		if(isNumeric(value)) {
			finishSensor(sensorName, sensorSubType, Float.parseFloat(value));
		}
	}
	
	private void finishSensor(String sensorName, String sensorSubType, double value) {
		for (SensorListener listener : sensorListeners) {
			listener.sensorReceived(sensorName, sensorSubType, value);
		}
	}
	
	@Override
	public void receive(String line) {
		/**
		 * comunication protocol
		 */
//		FC_RES
		if(line.startsWith("FC_RES")) {
			processResponse(line);
		}
//		FC_POST
		if(line.startsWith("FC_POST_")) {
			processPost(line);
		}
		for (LineListener listener : lineListeners) {
			listener.lineReceived(line);
		}
	}
	
	public void startTelem() {
		send(FCCommand.FC_DO_START_TELEM);
	}
	
	public void stopTelem() {
		send(FCCommand.FC_DO_STOP_TELEM);
	}
	
	public void calibrateAcc() {
		send(FCCommand.FC_DO_ACC_CALIB);
	}

	public void calibrateGyro() {
		send(FCCommand.FC_DO_GYRO_CALIB);
	}
	
	private void send(FCCommand command) {
		send(command.name());
	}
	
	private void send(String line) {
		serial.println(line);
		for (PrintListener listener : printListeners) {
			listener.linePrinted(line);
		}
	}
	
	/**
	 * listeners
	 */
	public void disconnectSerial() {
		serial.close();
	}
	
	public boolean addOpenListeners(SerialOpenListener l) {
		return serial.openListeners.add(l);
	}
	
	public boolean removeOpenListeners(SerialOpenListener l) {
		return serial.openListeners.remove(l);
	}
	
	public static boolean isNumeric(String str) { 
		try {  
			Double.parseDouble(str);  
			return true;
		} catch(NumberFormatException e){  
			return false;  
		}
	}
}