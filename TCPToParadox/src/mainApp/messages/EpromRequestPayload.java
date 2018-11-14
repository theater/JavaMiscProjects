package mainApp.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mainApp.ParadoxUtil;
import mainApp.Exceptions.ParadoxBindingException;

public class EpromRequestPayload extends MemoryRequestPayload implements IPPacketPayload {

    private static Logger logger = LoggerFactory.getLogger(EpromRequestPayload.class);

    public EpromRequestPayload(int address, byte bytesToRead) throws ParadoxBindingException {
        super(address, bytesToRead);
        setControlByte(calculateControlByte(address));
    }

    private byte calculateControlByte(int address) {
        logger.debug("Address: {}", String.format("0x%02X,\t", address));
        byte controlByte = 0x00;
        // probably the bellow line is not needed if we initially set to 0
        // controlByte |= 7 << 0;
        byte[] shortToByteArray = ParadoxUtil.intToByteArray(address);
        if (shortToByteArray.length > 2) {
            byte bit16 = ParadoxUtil.getBit(address, 16);
            controlByte |= bit16 << 0;
            byte bit17 = ParadoxUtil.getBit(address, 17);
            controlByte |= bit17 << 1;
        }
        logger.debug("ControlByte value: " + controlByte);
        return controlByte;
    }
}
