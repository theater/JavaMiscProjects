package mainApp.messages;

import mainApp.ParadoxUtil;
import mainApp.Exceptions.ParadoxBindingException;

public class RamRequestPayload extends MemoryRequestPayload implements IPPacketPayload {

    public RamRequestPayload(int address, byte bytesToRead) throws ParadoxBindingException {
        super(address, bytesToRead);

        byte controlByte = ParadoxUtil.setBit((byte) 0, 7, 1);
        setControlByte(controlByte);
    }

}
