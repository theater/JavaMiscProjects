package mainApp.messages;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.ParadoxUtil;
import mainApp.Exceptions.ParadoxBindingException;

public class MemoryRequestPayload implements IPPacketPayload {

    private static Logger logger = LoggerFactory.getLogger(MemoryRequestPayload.class);

    private short messageStart = (short) ((0x50 << 8) | 0x08);
    private byte controlByte;

    private int address;
    private byte bytesToRead;

    public MemoryRequestPayload(int address, byte bytesToRead) throws ParadoxBindingException {
        if (bytesToRead < 1 || bytesToRead > 64) {
            throw new ParadoxBindingException("Invalid bytes to read. Valid values are 1 to 64.");
        }

        this.address = address;
        this.bytesToRead = bytesToRead;
        logger.trace("MessageStart: {}", String.format("0x%02X,\t", messageStart));
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write(ParadoxUtil.shortToByteArray(messageStart));
        outputStream.write(controlByte);
        outputStream.write((byte) 0x00);

        outputStream.write(ParadoxUtil.shortToByteArray((short) address));

        outputStream.write(bytesToRead);

        // The bellow 0x00 is dummy which will be overwritten by the checksum
        outputStream.write(0x00);
        byte[] byteArray = outputStream.toByteArray();

        return byteArray;
    }

    protected byte getControlByte() {
        return controlByte;
    }

    protected void setControlByte(byte controlByte) {
        this.controlByte = controlByte;
    }

}
