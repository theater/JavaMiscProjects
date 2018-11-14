package mainApp.messages;

import mainApp.Exceptions.ParadoxBindingException;

public class RamRequestPayload extends MemoryRequestPayload implements IPPacketPayload {

    public RamRequestPayload(int address, byte bytesToRead) throws ParadoxBindingException {
        super(address, bytesToRead);

        byte controlByte = 0x00;
        controlByte |= 7 << 1;
        setControlByte(controlByte);
    }

}
