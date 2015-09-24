import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * @author Yujun Huang
 */
public class SerialComm implements SerialPortEventListener {
	SerialPort serialPort = null;
	private static final String PORT_NAME = "COM4";

	private String appName;
	private BufferedReader input;
	private OutputStream output;
	private String inputLine = "";
	private boolean dataSent;

	private static final int TIME_OUT = 1000; // Port open timeout
	private static final int DATA_RATE = 9600; // Arduino serial port

	public boolean initialize() {
		try {
			CommPortIdentifier portId = null;
			CommPortIdentifier currPortId = CommPortIdentifier.getPortIdentifier(PORT_NAME);
			serialPort = (SerialPort) currPortId.open(appName, TIME_OUT);
			portId = currPortId;
			if (portId == null || serialPort == null) {
				return false;
			}

			dataSent = false;
			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

			// Give the Arduino some time
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ie) {
			}
			this.sendData('T');
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	void sendData(char data) {
		try {
			output = serialPort.getOutputStream();
			output.write(data);
		} catch (Exception e) {
			System.exit(0);
		}
	}

	// This should be called when you stop using the port
	//
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	//
	// Handle serial port event
	//
	public synchronized void serialEvent(SerialPortEvent oEvent) {

		try {
			switch (oEvent.getEventType()) {
			case SerialPortEvent.DATA_AVAILABLE:
				if (input == null) {
					input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

				}
				for (int i = 0; i < 6; i++) {
					inputLine += (char) input.read();
				}
				dataSent = true;
				break;
			default:
				break;
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public void dataReset() {
		inputLine = "";
		dataSent = false;
	}

	public String getTemperature() {
		if (dataSent) {
			return inputLine;
		} else {
			return "wait";
		}

	}

	public SerialComm() {
		appName = getClass().getName();
	}

	public static void main(String[] args) {
		SerialComm communication = new SerialComm();
		boolean connection; // display graph of old data
		Scanner s = new Scanner(System.in);
		try {
			connection = communication.initialize();
			if (!connection) {
				JOptionPane.showMessageDialog(null, "Serial Communication offline! re-connecting...");
			}

			while (!connection) {
				connection = communication.initialize();
			}
			JOptionPane.showMessageDialog(null, "Serial Communication online!");
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		char command = 'a';
		while (command != '1') {
			System.out.println("Input ongoing command:");
			command = s.next().charAt(0);
			System.out.println("Input operating command:");
			char temp = s.next().charAt(0);
			if (temp == 'T') {
				communication.sendData(temp);
				System.out.println(communication.getTemperature());
				System.out.println(Double.parseDouble(communication.getTemperature()));
				communication.dataReset();
			}

		}
	}
}
