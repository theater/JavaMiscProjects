package mainApp.messages;

/**
 * From Jean's excel sheet: 0x00: Serial/pass through command any other: IP
 * module command 0xF0: Connect to IP module 0xF2: (unknown, part of login
 * sequence) 0xF3: (unknown, part of login sequence) 0xF4: (unknown) 0xF8:
 * (unknown, occurs after serial connection is initiated with the panel) 0xFB:
 * Multicommand 0xFF: Disconnect from IP module (byte 00 in the response payload
 * MUST be 01 to indicate a successful disconnect)"
 *
 *
 */
public enum HeaderCommand {
	SERIAL((byte) 0x00), CONNECT_TO_IP_MODULE((byte) 0xF0), LOGIN_COMMAND1((byte) 0xF2), LOGIN_COMMAND2((byte) 0xF3),
	UNKNOWN1((byte) 0xF4), SERIAL_CONNECTION_INITIATED((byte) 0xF8), MULTI_COMMAND((byte) 0xFB),
	DISCONNECT((byte) 0xFF);

	private byte value;

	HeaderCommand(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}
}
