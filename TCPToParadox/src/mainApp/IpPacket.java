package mainApp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class IpPacket {
	public static final byte[] EMPTY_PAYLOAD = new byte[0];

	private byte startOfHeader = (byte) 0xAA;
	private short payloadLength;
	private byte messageType = (byte) 0x03;
	private byte encryption = (byte) 0x08;
	private byte command = (byte) 0;
	private byte subCommand = (byte) 0;
	private byte unknown0 = (byte) 0x0A;
	private long theRest = 0xEEEEEEEEEEEEEEEEl;
	private byte[] payload;

	public IpPacket(String payload) throws IOException {
		this(payload.getBytes("US-ASCII"));
	}

	public IpPacket(byte[] payload) throws IOException {
		if(payload == null) {
			this.payload = new byte[0];
			this.payloadLength = 0;
		} else {
			this.payload = payload;
			this.payloadLength = (short) payload.length;
		}

		// TODO: Figure out how to fill up to 16, 32, 48, etc sizes with 0xEE
//		if (payload.length < 16) {
//			this.payload = extendPayload(16, payload);
//		} else {
//		}
	}

	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		outputStream.write(startOfHeader);
		outputStream.write(ByteBuffer.allocate(Short.SIZE / Byte.SIZE).order(ByteOrder.LITTLE_ENDIAN)
				.putShort(payloadLength).array());
		outputStream.write(messageType);
		outputStream.write(encryption);
		outputStream.write(command);
		outputStream.write(subCommand);
		outputStream.write(unknown0);
		outputStream.write(ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(theRest).array());
		outputStream.write(payload);

		return outputStream.toByteArray();
	}

	private byte[] extendPayload(int payloadLength, byte[] payload) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		stream.write(payload);
		for (int i = payload.length; i < payloadLength; i++) {
			stream.write((byte) 0xEE);
		}

		return stream.toByteArray();
	}

	public byte getStartOfHeader() {
		return startOfHeader;
	}

	public IpPacket setStartOfHeader(byte startOfHeader) {
		this.startOfHeader = startOfHeader;
		return this;
	}

	public short getPayloadLength() {
		return payloadLength;
	}

	public IpPacket setPayloadLength(short payloadLength) {
		this.payloadLength = payloadLength;
		return this;
	}

	public byte getMessageType() {
		return messageType;
	}

	public IpPacket setMessageType(byte messageType) {
		this.messageType = messageType;
		return this;
	}

	public byte getEncryption() {
		return encryption;
	}

	public IpPacket setEncryption(byte encryption) {
		this.encryption = encryption;
		return this;
	}

	public byte getCommand() {
		return command;
	}

	public IpPacket setCommand(byte command) {
		this.command = command;
		return this;
	}

	public byte getSubCommand() {
		return subCommand;
	}

	public IpPacket setSubCommand(byte subCommand) {
		this.subCommand = subCommand;
		return this;
	}

	public byte getUnknown0() {
		return unknown0;
	}

	public IpPacket setUnknown0(byte unknown0) {
		this.unknown0 = unknown0;
		return this;
	}

	public long getTheRest() {
		return theRest;
	}

	public IpPacket setTheRest(long theRest) {
		this.theRest = theRest;
		return this;
	}

}
