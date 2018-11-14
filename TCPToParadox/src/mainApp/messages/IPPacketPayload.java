package mainApp.messages;

import java.io.IOException;

public interface IPPacketPayload {

    public byte[] getBytes() throws IOException;
}
