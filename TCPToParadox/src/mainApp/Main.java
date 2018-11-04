package mainApp;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Main {
	private static final String PASSWORD = "MyPass";

	private static final int PORT = 10000;
	private static final String IP_ADDRESS = "192.168.254.231";

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		Socket socket = new Socket(IP_ADDRESS, PORT);
		DataOutputStream tx = new DataOutputStream(socket.getOutputStream());
		DataInputStream rx = new DataInputStream(socket.getInputStream());

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] passwordAsBytes = PASSWORD.getBytes("US-ASCII");
		printByteArray(passwordAsBytes, "Password:");

		byte[] sessionKey = Arrays.copyOf(passwordAsBytes, 16);
		Key key = new SecretKeySpec(sessionKey, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedPassword = cipher.doFinal(passwordAsBytes);
		log("EncryptedPassword size: " + encryptedPassword.length);

		byte[] packet = concatenateByteArrays(IpMessages.loginHeader, encryptedPassword);
		printByteArray(packet, "Tx Packet:");
		tx.write(packet);

		byte[] response = new byte[64];
		rx.read(response);
		printByteArray(response, "Response:");

		tx.close();
		rx.close();
		socket.close();
	}

	private static byte[] concatenateByteArrays(byte[] a, byte[] b) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(a);
		outputStream.write(b);

		return outputStream.toByteArray();
	}

	private static void printByteArray(byte[] array, String description) {
		if (description != null && !description.isEmpty()) {
			System.out.println(description);
		}

		int i = 0;
		for (byte b : array) {
			i++;
			String st = String.format("0x%02X,\t", b);
			System.out.print(st);
			if (i > 7) {
				i = 0;
				System.out.println();
			}
		}
		System.out.println();
	}

	private static void log(String arg) {
		System.out.println(arg);
	}
}