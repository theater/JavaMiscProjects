package mainApp.messages;

/**
 *
 * @author Konstantin Polihronov From Jean's excel: 0x03: IP Request 0x01: IP Response 0x04:
 *         Serial/pass through command request 0x02: Serial/pass through command
 *         response"
 *
 */

public enum HeaderMessageType {
	IP_REQUEST((byte) 0x03), IP_RESPONSE((byte) 0x01), SERIAL_PASSTHRU_REQUEST((byte) 0x04),
	SERIAL_PASSTHRU_RESPONSE((byte) 0x02);

	private byte value;

	HeaderMessageType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
}
