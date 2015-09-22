import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;

/*
 * @author: Twilio Auto code service, Yujun Huang
 */

/**
 * The Class Texter to send text message.
 */
public class Texter {
	
	/** The Constant ACCOUNT_SID. */
	// Find your Account Sid and Token at twilio.com/user/account
	private static final String ACCOUNT_SID = "ACfbc403a20db2db2dabe94927071a12c8";
	
	/** The Constant AUTH_TOKEN. */
	private static final String AUTH_TOKEN = "bd4606c770ff522f9a179003fb845557";
	
	/** The client to connect with twilio server*/
	private TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	
	/** The params. */
	private List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	/** The boolean value for Text sent signal*/
	private boolean TextSent;

	/**
	 * Instantiates a new texter.
	 *
	 * @param phoneNumber the phone number
	 * @throws TwilioRestException the twilio rest exception
	 */
	public Texter(String phoneNumber) throws TwilioRestException {

		// Build the parameters
		params.add(new BasicNameValuePair("To", phoneNumber));
		params.add(new BasicNameValuePair("From", "+13197746240"));
		TextSent = false;

	}
	
	/**
	 * Reset the Texter messager. 
	 */
	public void Reset() {
		TextSent = false;
	}
	
	/**
	 * Checks if is alerted.
	 *
	 * @return true, if is alerted
	 */
	public boolean isAlerted() {
		return TextSent;
	}

	/**
	 * Send message for High temperature alert.
	 */
	public void HighTempAlert() {
		while (!TextSent) {
			params.add(new BasicNameValuePair("Body", "The temperature is now over the max temperature limit !"));
			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			try {
				@SuppressWarnings("unused")
				Message message = messageFactory.create(params);
			} catch (TwilioRestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TextSent = true;
		}

	}

	/**
	 * Send message for Low temperature alert.
	 */
	public void LowTempAlert() {
		while (!TextSent) {
			params.add(new BasicNameValuePair("Body", "The temperature is now below the min temperature limit !"));
			MessageFactory messageFactory = client.getAccount().getMessageFactory();
			try {
				@SuppressWarnings("unused")
				Message message = messageFactory.create(params);
			} catch (TwilioRestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TextSent = true;
		}

	}
}