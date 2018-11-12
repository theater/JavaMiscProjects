package mainApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParadoxSystem {

	private static Logger logger = LoggerFactory.getLogger(ParadoxSystem.class);

	private Socket socket;
	private DataOutputStream tx;
	private DataInputStream rx;

	private String password;

	public ParadoxSystem(String ipAddress, int tcpPort, String ip150Password) throws UnknownHostException, IOException {
		socket = new Socket(ipAddress, tcpPort);
		socket.setSoTimeout(3000);
		tx = new DataOutputStream(socket.getOutputStream());
		rx = new DataInputStream(socket.getInputStream());
		password = ip150Password;
	}

	public void close() throws IOException {
		tx.close();
		rx.close();
		socket.close();
	}

	public void logoutSequence() throws IOException {
		logger.debug("Logout sequence started");
		byte[] logoutMessage = new byte[] { 0x00, 0x07, 0x05, 0x00, 0x00, 0x00, 0x00 };
		ParadoxIPPacket logoutPacket = new ParadoxIPPacket(logoutMessage, true).setMessageType((byte) 0x04)
				.setUnknown0((byte) 0x14);
		sendPacket(logoutPacket);
	}

	public void logonSequence() throws IOException {
		logger.debug("Step1");
		// 1: Login to module request (IP150 only)
		ParadoxIPPacket ipPacket = new ParadoxIPPacket(password, false).setCommand((byte) 0xF0);
		byte[] sendPacket = sendPacket(ipPacket);
		if (sendPacket[4] == 0x38) {
			logger.debug("Login OK");
		} else {
			logger.debug("Login failed");
		}

		logger.debug("Step2");
		// 2: Unknown request (IP150 only)
		ParadoxIPPacket step2 = new ParadoxIPPacket(ParadoxIPPacket.EMPTY_PAYLOAD, false).setCommand((byte) 0xF2);
		sendPacket(step2);

		logger.debug("Step3");
		// 3: Unknown request (IP150 only)
		ParadoxIPPacket step3 = new ParadoxIPPacket(ParadoxIPPacket.EMPTY_PAYLOAD, false).setCommand((byte) 0xF3);
		sendPacket(step3);

		logger.debug("Step4");
		// 4: Init communication over UIP softawre request (IP150 and direct serial)
		byte[] message4 = new byte[37];
		message4[0] = 0x72;
		ParadoxIPPacket step4 = new ParadoxIPPacket(message4, true).setMessageType((byte) 0x04);
		sendPacket(step4);

		logger.debug("Step5");
		// 5: Unknown request (IP150 only)
		ParadoxIPPacket step5 = new ParadoxIPPacket(IpMessages.unknownIP150Message01, false).setCommand((byte) 0xF8);
		sendPacket(step5);

		logger.debug("\nStep6");
		// 6: Initialize serial communication request (IP150 and direct serial)
		byte[] message6 = new byte[37];
		message6[0] = 0x5F;
		message6[1] = 0x20;
		ParadoxIPPacket step6 = new ParadoxIPPacket(message6, true).setMessageType((byte) 0x04);
		byte[] response6 = sendPacket(step6);
		byte[] initializationMessage = Arrays.copyOfRange(response6, 16, response6.length);
		ParadoxUtil.printByteArray("Init communication sub array: ", initializationMessage);

		logger.debug("\nStep7");
		// 7: Initialization request (in response to the initialization from the panel)
		// (IP150 and direct serial)
		byte[] message7 = new byte[] {
				// Initialization command
				0x00,

				// Module address
				initializationMessage[1],

				// Not used
				0x00, 0x00,

				// Product ID
				initializationMessage[4],

				// Software version
				initializationMessage[5],

				// Software revision
				initializationMessage[6],

				// Software ID
				initializationMessage[7],

				// Module ID
				initializationMessage[8], initializationMessage[9],

				// PC Password
				0x09, (byte) 0x87,

				// Modem speed
				0x0A,

				// Winload type ID
				0x30,

				// User code (for some reason Winload sends user code 021000)
				0x02, 0x10, 0x00,

				// Module serial number
				initializationMessage[17], initializationMessage[18], initializationMessage[19],
				initializationMessage[20],

				// EVO section 3030-3038 data
				initializationMessage[21], initializationMessage[22], initializationMessage[23],
				initializationMessage[24], initializationMessage[25], initializationMessage[26],
				initializationMessage[27], initializationMessage[28], initializationMessage[29],

				// Not used
				0x00, 0x00, 0x00, 0x00,

				// Source ID (0x02 = Winload through IP)
				0x02,

				// Carrier length
				0x00,

				// Checksum
				0x00 };
		ParadoxIPPacket step7 = new ParadoxIPPacket(message7, true).setMessageType((byte) 0x04)
				.setUnknown0((byte) 0x14);
		byte[] finalResponse = sendPacket(step7);
		if ((finalResponse[16] & 0xF0) == 0x10) {
			logger.debug("SUCCESSFUL LOGON");
		} else {
			logger.debug("LOGON FAILURE");
		}
	}

	public List<String> readPartitions() {
		List<String> result = new ArrayList<>();

		try {
			for (int i = 1; i <= 4; i++) {
				result.add(readPartitionLabel(i));
			}
		} catch (Exception e) {
			logger.debug("Unable to retrieve partition labels.\nException: " + e.getMessage());
		}
		return result;
	}

	public String readPartitionLabel(int partitionNo) throws Exception {
		logger.debug("Reading partition label: " + partitionNo);
		if (partitionNo < 1 || partitionNo > 8)
			throw new Exception("Invalid partition number. Valid values are 1-8.");

		int address = (int) (0x3A6B + (partitionNo - 1) * 107);
		byte labelLength = 16;

		byte[] partitionLabelBytes = readEepromMemory(address, labelLength);
		byte[] payloadResult = Arrays.copyOfRange(partitionLabelBytes, 22, partitionLabelBytes.length - 1);

		String result = new String(payloadResult, "US-ASCII");
		logger.debug("Partition label: {}", result);
		return result;
	}

	public List<String> readZones() {
		List<String> result = new ArrayList<>();

		try {
			for (int i = 1; i <= 192; i++) {
				result.add(readZoneLabel(i));
			}
		} catch (Exception e) {
			logger.debug("Unable to retrieve zone labels.\nException: " + e.getMessage());
		}
		return result;
	}

	public String readZoneLabel(int zoneNumber) throws Exception {
		logger.debug("Reading zone label: " + zoneNumber);
		if (zoneNumber < 1 || zoneNumber > 192)
			throw new Exception("Invalid zone number. Valid values are 1-192.");

		byte labelLength = 16;

		int address;
		if (zoneNumber <= 96) {
			address = (int) (0x430 + (zoneNumber - 1) * 16);
		} else {
			address = (int) (0x62F7 + (zoneNumber - 97) * 16);
		}

		byte[] zoneLabelBytes = readEepromMemory(address, labelLength);
		byte[] payloadResult = Arrays.copyOfRange(zoneLabelBytes, 22, zoneLabelBytes.length - 1);

		String result = new String(payloadResult, "US-ASCII");
		logger.debug("Zone label: " + result);
		return result;
	}

	private byte[] readEepromMemory(int address, byte bytesToRead) throws Exception {
		if (bytesToRead < 1 || bytesToRead > 64)
			throw new Exception("Invalid bytes to read. Valid values are 1 to 64.");

		EpromRequestMessage message = new EpromRequestMessage(address, bytesToRead);
		ParadoxIPPacket readEpromMemoryPacket = new ParadoxIPPacket(message.getBytes(), false)
				.setMessageType((byte) 0x04).setUnknown0((byte) 0x14);
		byte[] response = sendPacket(readEpromMemoryPacket);

		return response;
	}

	private byte[] sendPacket(ParadoxIPPacket packet) throws IOException {
		return sendPacket(packet.getBytes());
	}

	private byte[] sendPacket(byte[] packet) throws IOException {
		ParadoxUtil.printByteArray("Tx Packet:", packet);
		logger.debug("Packet size = " + packet.length);
		tx.write(packet);

		byte[] response = new byte[64];
		int length = rx.read(response);

		if (length > 0) {
			byte[] realResponse = new byte[length];
			for (int i = 0; i < length; i++) {
				realResponse[i] = response[i];
			}
			ParadoxUtil.printByteArray("Response:", realResponse);
			return realResponse;
		}
		return new byte[0];
	}
}
