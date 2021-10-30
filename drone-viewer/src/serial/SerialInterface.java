package serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import gui.elements.FCSetter;

public class SerialInterface implements SerialReceiveListener {

	private static SerialInterface instance = null;
	private Serial serial;
	private static final double SETTER_TIMEOUT = 2;//seconds

	long uid = 0; //uid used for getters and setters
	
	List<SetterHelper> setters = new ArrayList<>();
	
	/**
	 * listeners
	 */
	List<LineListener> lineListeners = new ArrayList<>();
	List<PrintListener> printListeners = new ArrayList<>();
	List<SensorListener> sensorListeners = new ArrayList<>();
	List<FCSetter<?>> fcSetters = new ArrayList<>();
	
	/**
	 * getter comunication
	 */
	Map<Long, Receiver> getters = new HashMap<>();
	
	private long lastSend;
	private int minSendDelay = 1;
	
	public boolean isConnected() {
		return serial.isConnected();
	}
	
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
		long uidCpy = uid;
		Timer t = new Timer((int) (SETTER_TIMEOUT * 10000), e -> {
			((Timer) e.getSource()).stop();
			if(getters.containsKey(uidCpy)) {
				getters.get(uidCpy).receive(false, null);
				getters.remove(uidCpy);
			}
		});
		t.start();
		uid++;
	}
	
	public void set(FCCommand setter, Object value) {
//		set(setter, value, null);
		set(setter, value, (e) -> {});
	}
	
	public void set(FCCommand setter, Object value, SetListener listener) {
		String msg = value.toString();
		SetterHelper s = new SetterHelper(msg, listener, uid);
		setters.add(s);
		send(setter.name() + " " + uid++ + " " + msg); //send
		
		if(listener == null) {
			setters.remove(s);
			return;
		}
		
		Timer t = new Timer((int) SETTER_TIMEOUT * 1000, e -> {
			if(setters.contains(s)) {
				System.out.println("timout");
				s.listener.setFinished(false);
				setters.remove(s);
			}
			((Timer) e.getSource()).stop();
		});
		t.start();
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
	
	public boolean addFcSetterListener(FCSetter<?> l) {
		return fcSetters.add(l);
	}
	
	public boolean removeFcSetterListener(FCSetter<?> l) {
		return fcSetters.remove(l);
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
			getters.get(id).receive(true, response);
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
		set(FCCommand.FC_SET_QUAT_TELEM, true, e -> {});
//		refreshSetters();
	}
	
	public void stopTelem() {
		set(FCCommand.FC_SET_QUAT_TELEM, false, e -> {});
//		refreshSetters();
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
		if(System.currentTimeMillis() - lastSend < minSendDelay) {
			Timer t = new Timer((int) (minSendDelay - (System.currentTimeMillis() - lastSend)), e -> {
				((Timer) e.getSource()).stop();
				send(line);
			});
			t.start();
			return;
		}
		serial.println(line);
		for (PrintListener listener : printListeners) {
			listener.linePrinted(line);
		}
		lastSend = System.currentTimeMillis();
	}
	
	/**
	 * listeners
	 */
	public void disconnectSerial() {
		serial.close();
		for (FCSetter<?> fcSetter : fcSetters) {
			fcSetter.fcFieldEnable(false);
		}
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
	
	public void refreshSetters() {
		Timer t = new Timer(100, e -> {
			((Timer) e.getSource()).stop();
			for (FCSetter<?> fcSetter : fcSetters) {
				fcSetter.get();
			}
		});
		t.start();
	}

	public int getMinSendDelay() {
		return minSendDelay;
	}

	public void setMinSendDelay(int minSendDelay) {
		this.minSendDelay = minSendDelay;
	}
}