package mainApp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EpromRequestMessage {

	private short messageStart = (short) ((0x08 << 8) | 0x50);
	private byte controlByte;
	private short address;
	private byte bytesToRead;

	public EpromRequestMessage(short address, byte bytesToRead) {
		this.address = address;
		this.bytesToRead = bytesToRead;
		this.controlByte = calculateControlByte(address);
	}

	//TODO see how to do the proper calculation as per Jean's logic
	private byte calculateControlByte(short address) {
		byte controlByte = 0x00;
		controlByte = (byte) (controlByte | 1 << 7);
		return controlByte;
	}

	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		outputStream.write(ByteBuffer.allocate(Short.SIZE / Byte.SIZE).order(ByteOrder.LITTLE_ENDIAN)
				.putShort(messageStart).array());
		outputStream.write(controlByte);
		outputStream.write(0x00);

		outputStream.write(
				ByteBuffer.allocate(Short.SIZE / Byte.SIZE).order(ByteOrder.LITTLE_ENDIAN).putShort(address).array());
		outputStream.write(bytesToRead);

		// The bellow 0x00 is dummy which will be overwritten by the checksum
		outputStream.write(0x00);
		byte[] byteArray = outputStream.toByteArray();
		byteArray[byteArray.length - 1] = ParadoxUtil.calculateChecksum(outputStream.toByteArray());
		;

		return byteArray;
	}
}
