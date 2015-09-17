import java.util.*;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;

/*
 * @author: Twilio Auto code service, Yujun Huang
 */

public class Texter {
	// Find your Account Sid and Token at twilio.com/user/account
	private static final String ACCOUNT_SID = "ACfbc403a20db2db2dabe94927071a12c8";
	private static final String AUTH_TOKEN = "bd4606c770ff522f9a179003fb845557";
	private TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
	private String number = "+13198559324";
	private List<NameValuePair> params = new ArrayList<NameValuePair>();

	public Texter(String phoneNumber) throws TwilioRestException {

		number = "+1" + phoneNumber;
		// Build the parameters
		params.add(new BasicNameValuePair("To", number));
		params.add(new BasicNameValuePair("From", "+13197746240"));

	}

	public void changeNumber(String newNumber) {
		number = "+1" + newNumber;
	}

	public void HighTempAlert() {

		params.add(new BasicNameValuePair("Body", "The temperature is now over the max temperature limit !"));
		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		try {
			Message message = messageFactory.create(params);
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void LowTempAlert() {

		params.add(new BasicNameValuePair("Body", "The temperature is now below the min temperature limit !"));
		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		try {
			Message message = messageFactory.create(params);
		} catch (TwilioRestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}