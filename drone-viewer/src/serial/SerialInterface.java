package serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerialInterface implements SerialReceiveListener{

	private static SerialInterface instance = null;
	Serial serial;
	
	List<LineListener> lineListeners = new ArrayList<>();
	List<PrintListener> printListeners = new ArrayList<>();
	
	/**
	 * listeners
	 */
	List<GyroListener> gyroListeners = new ArrayList<>();
	List<AccelerometerListener> accelerometerListeners = new ArrayList<>();
	List<MagnetometerListener> magnetometerListeners = new ArrayList<>();
	
	/**
	 * getter comunication
	 */
	long getterId = 0;
	Map<Long, Receiver> getters = new HashMap<>();
	
	List<INSListener> insListeners = new ArrayList<>();
	
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
	
	private void processGyro(String line) {
		String[] split = line.split(",");
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		for (GyroListener listener : gyroListeners) {
			listener.gyroDataReceived(x, y, z);
		}
	}
	
	private void processAccelerometer(String line) {
		String[] split = line.split(",");
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		for (AccelerometerListener listener : accelerometerListeners) {
			listener.accelerometerDataReceived(x, y, z);
		}
	}
	
	private void processMagnetometer(String line) {
		String[] split = line.split(",");
		double x = Double.parseDouble(split[1]);
		double y = Double.parseDouble(split[2]);
		double z = Double.parseDouble(split[3]);
		for (MagnetometerListener listener : magnetometerListeners) {
			listener.magnetometerDataReceived(x, y, z);
		}
	}
	
	private void processINS(String line) {
		String[] split = line.split(",");
		double w = Double.parseDouble(split[1]);
		double x = Double.parseDouble(split[2]);
		double y = Double.parseDouble(split[3]);
		double z = Double.parseDouble(split[4]);
		for (INSListener listener : insListeners) {
			listener.insDataReceived(w, x, y, z);
		}
	}
	
	public void get(FCCommand command, Receiver receiver) {
		getters.put(getterId, receiver);
		send(command.name() + " " + getterId);
		getterId++;
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
	
	private void processResponse(String line) {
		String split[] = line.split(" ");
		if(split.length < 3) {
			return;
		}
		long id = Long.parseLong(split[1]);
		
		int responseStart = line.indexOf(' ', 7);
		String response = line.substring(responseStart + 1);
		getters.get(id).receive(response);
		getters.remove(id);
	}
	
	private void processPost(String line) {
		String split[] = line.split(" ");
		if(split.length < 2 || !line.contains("FC_POST_")) {
			return;
		}
		String command = split[0].split("FC_POST_")[0];
		int valStart = line.indexOf(' ') + 1;
		String val = line.substring(valStart + 1);
		FCCommand fcCommand = FCCommand.valueOf(command);
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
	public boolean addGyroListener(GyroListener listener) {
		return gyroListeners.add(listener);
	}
	
	public boolean removeGyroListener(GyroListener listener) {
		return gyroListeners.remove(listener);
	}
	
	public boolean addAccelerometerListener(AccelerometerListener listener) {
		return accelerometerListeners.add(listener);
	}
	
	public boolean removeAccelerometerListener(AccelerometerListener listener) {
		return accelerometerListeners.remove(listener);
	}
	
	public boolean addMagnetometerListener(MagnetometerListener listener) {
		return magnetometerListeners.add(listener);
	}
	
	public boolean removeMagnetometerListener(MagnetometerListener listener) {
		return magnetometerListeners.remove(listener);
	}
	
	public boolean addINSListener(INSListener listener) {
		return insListeners.add(listener);
	}
	
	public boolean removeINSListener(INSListener listener) {
		return insListeners.remove(listener);
	}

	public void disconnectSerial() {
		serial.close();
	}
}