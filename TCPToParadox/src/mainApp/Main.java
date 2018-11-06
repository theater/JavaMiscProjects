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
	private static final String PASSWORD = "password";

	private static final int PORT = 10000;
	private static final String IP_ADDRESS = "192.168.254.231";

	private static Socket socket;
	private static DataOutputStream tx;
	private static DataInputStream rx;

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		socket = new Socket(IP_ADDRESS, PORT);
		tx = new DataOutputStream(socket.getOutputStream());
		rx = new DataInputStream(socket.getInputStream());

		IpPacket ipPacket = new IpPacket(PASSWORD).setCommand((byte) 0xF0);
		sendPacket(ipPacket.getBytes());

		IpPacket step2 = new IpPacket(IpPacket.EMPTY_PAYLOAD).setCommand((byte) 0xF2);
		sendPacket(step2.getBytes());

		IpPacket step3 = new IpPacket(IpPacket.EMPTY_PAYLOAD).setCommand((byte) 0xF3);
		sendPacket(step3.getBytes());

		tx.close();
		rx.close();
		socket.close();
	}

	private static byte[] sendPacket(byte[] packet) throws IOException {
		printByteArray("Tx Packet:", packet);
		log("Packet size = " + packet.length);
		tx.write(packet);

		byte[] response = new byte[64];
		int length = rx.read(response);

		if (length > 0) {
			byte[] realResponse = new byte[length];
			for (int i = 0; i < length; i++) {
				realResponse[i] = response[i];
			}
			printByteArray("Response:", realResponse);
			return realResponse;
		}
		return new byte[0];
	}

	private static byte[] concatenateByteArrays(byte[]... arr) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (byte[] byteArr : arr) {
			outputStream.write(byteArr);
		}
		return outputStream.toByteArray();
	}

	private static void printByteArray(String description, byte[] array) {
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

	private static byte[] encryptMessage(byte[] passwordAsBytes) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] sessionKey = Arrays.copyOf(passwordAsBytes, 16);
		Key key = new SecretKeySpec(sessionKey, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedPassword = cipher.doFinal(passwordAsBytes);
		log("EncryptedPassword size: " + encryptedPassword.length);
		return encryptedPassword;
	}
}