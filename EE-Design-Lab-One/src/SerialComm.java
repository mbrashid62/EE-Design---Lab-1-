import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * The Class SerialComm.
 *
 * @author Yujun Huang
 */
public class SerialComm implements SerialPortEventListener {
	
	/** The serial port variable. */
	SerialPort serialPort = null;
	
	/** The app name variable. */
	private String appName;
	
	/** The input for reading. input stream*/
	private BufferedReader input;
	
	/** The output to write output stream*/
	private OutputStream output;
	
	/** The input line. */
	private String inputLine = "";
	
	/** The boolean value for data sent signal. */
	private boolean dataSent;

	/** The Constant TIME_OUT. */
	private static final int TIME_OUT = 2000; // Port open timeout
	
	/** The Constant DATA_RATE. */
	private static final int DATA_RATE = 9600; // Arduino serial port

	/**
	 * Initialize the serial communication connection.
	 *
	 * @return true, if successful
	 */
	public boolean initialize() {
		try {
			CommPortIdentifier portId = null;

			@SuppressWarnings("unchecked")
			Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
			
			//try to find a usable serial port to connect
			while (portEnum.hasMoreElements()) {
				CommPortIdentifier portIdentifier = portEnum.nextElement();
				if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {

					portId = portIdentifier;

				}
			}
			serialPort = (SerialPort) portId.open(appName, TIME_OUT);
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
			this.sendData('T');//send T command to initialize the dataStream from arduino
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Send data.
	 *
	 * @param data the data
	 */
	void sendData(char data) {
		try {
			output = serialPort.getOutputStream();
			output.write(data);
		} catch (Exception e) {
			System.exit(0);
		}
	}

	// This should be called when you stop using the port
	/**
	 * Close the port
	 */
	//
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	// Handle serial port event
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
				dataSent = true;
				break;
			default:
				break;
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * Input Data reset to empty.
	 */
	public void dataReset() {
		inputLine = "";
		dataSent = false;
	}

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public String getTemperature() {
		if (dataSent) {
			return inputLine;
		}
		return "19.0";
	}

	/**
	 * Instantiates a new serial communication.
	 */
	public SerialComm() {
		appName = getClass().getName();
	}
}
