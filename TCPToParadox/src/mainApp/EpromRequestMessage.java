package mainApp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EpromRequestMessage {

	private static Logger logger = LoggerFactory.getLogger(EpromRequestMessage.class);

	private short messageStart = (short) ((0x50 << 8) | 0x08);
	private byte controlByte;
	private int address;
	private byte bytesToRead;

	public EpromRequestMessage(int address, byte bytesToRead) {
		this.address = address;
		this.bytesToRead = bytesToRead;
		this.controlByte = calculateControlByte(address);
		logger.debug("MessageStart: {}", String.format("0x%02X,\t", messageStart));
	}

	private byte calculateControlByte(int address) {
		logger.debug("Address: {}", String.format("0x%02X,\t", address));
		byte controlByte = 0x00;
		controlByte |= 7 << 1;
		byte[] shortToByteArray = intToByteArray(address);
		if (shortToByteArray.length > 2) {
			byte bit16 = ParadoxUtil.getBit(address, 16);
			controlByte |= bit16 << 0;
			byte bit17 = ParadoxUtil.getBit(address, 17);
			controlByte |= bit17 << 1;
		}
		logger.debug("ControlByte value: " + controlByte);
		return controlByte;
	}

	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		outputStream.write(shortToByteArray(messageStart));
		outputStream.write(controlByte);
		outputStream.write((byte)0x00);

		outputStream.write(shortToByteArray((short) address));

		outputStream.write(bytesToRead);

		// The bellow 0x00 is dummy which will be overwritten by the checksum
		outputStream.write(0x00);
		byte[] byteArray = outputStream.toByteArray();
		byteArray[byteArray.length - 1] = ParadoxUtil.calculateChecksum(byteArray);
		;

		return byteArray;
	}

	private byte[] intToByteArray(int value) {
		return ByteBuffer.allocate(Integer.SIZE / Byte.SIZE).order(ByteOrder.BIG_ENDIAN).putInt(value).array();
	}

	private byte[] shortToByteArray(short value) {
		return ByteBuffer.allocate(Short.SIZE / Byte.SIZE).order(ByteOrder.BIG_ENDIAN).putShort(value).array();
	}
}
