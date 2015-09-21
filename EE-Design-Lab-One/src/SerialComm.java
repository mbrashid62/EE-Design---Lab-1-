import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author Yujun Huang
 */
public class SerialComm implements SerialPortEventListener {
	SerialPort serialPort = null;
	private static final String PORT_NAME = "COM6";

	private String appName;
	private BufferedReader input;
	private OutputStream output;
	private String inputLine = "";

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
				inputLine = "";
				for (int i = 0; i < 6; i++) {
					inputLine += (char) input.read();
				}
				break;

			default:
				break;
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public String getTemperature() {
		return inputLine;
	}

	public SerialComm() {
		appName = getClass().getName();
	}
}
