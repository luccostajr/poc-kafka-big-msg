package br.cepel.pojo;

import java.io.Serializable;
import java.util.UUID;

import br.cepel.config.Constants;
import br.cepel.helper.Helper;

public class CustomObject implements Serializable {
	private static final long serialVersionUID = 202401041907L;

	private String topic;
	private long timeStamp;
	private UUID id;
	private String name;
	private byte[] payload = new byte[Constants.MAX_MESSAGE_SIZE];

	public CustomObject(String topic) {
		super();

		this.topic = topic;
		this.timeStamp = System.currentTimeMillis();
		this.id = UUID.randomUUID();
		this.name = "{topic:\"" + this.topic + "\", timestamp:\"" + this.timeStamp + "\", id:\"" + this.id + "\"}";

		fullFillPayload();
	}

	// fill payload with an ascii sequence
	protected void fullFillPayload() {
		byte b = 0;
		int i = 0;
		String idStr = id.toString();
		String timeStampStr = String.valueOf(timeStamp);

		// header with id in string format
		for (; i < idStr.length(); i++) {
			payload[i] = idStr.getBytes()[i];
		}

		// body with ascii sequence
		for (; i < payload.length - timeStampStr.length(); i++) {
			b = (byte) (b++ % 127) == 0 ? 48 : b;
			payload[i] = b;
		}

		// footer with determined 5 chars
		for (; i < payload.length; i++) {
			payload[i] = timeStampStr.getBytes()[i - payload.length + timeStampStr.length()];
		}
	}

	public String getPayloadString() {
		return new String(payload);
	}

	@Override
	public String toString() {
		String payloadString = getPayloadString();
		return "{CustomObject: {\n" +
				"\ttopic: \"" + topic + "\",\n" +
				"\tid: \"" + id + "\",\n" +
				"\tname: \"" + name + "\",\n" +
				"\ttimeStamp: \"" + timeStamp + "\",\n" +
				"\ttimeStampFormatted: \"" + Helper.getFormattedDateTime(timeStamp) + "\",\n" +
				"\tpayload: {" + "\n" +
				"\t\tsize: \"" + payload.length + "\",\n" +
				"\t\tchars: \"" + payloadString.length() + "\",\n" +
				"\t\tcontent: \"'" + payloadString.substring(0, 30) + "' ... '"
				+ payloadString.substring(payloadString.length() - 30) + "'\"\n\t}" +
				"\n}}";
	}
}