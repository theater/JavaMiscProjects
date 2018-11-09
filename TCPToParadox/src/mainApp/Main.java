package mainApp;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {
	private static final String PASSWORD = "blablabla";

	private static final int PORT = 10000;
	private static final String IP_ADDRESS = "192.168.254.231";

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		ParadoxSystem paradoxSystem = new ParadoxSystem(IP_ADDRESS, PORT, PASSWORD);
		paradoxSystem.logonSequence();
		paradoxSystem.logoutSequence();
		paradoxSystem.close();
	}


}