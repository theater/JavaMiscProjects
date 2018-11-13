package mainApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
		socket.setSoTimeout(2000);
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

	public void logonSequence() throws IOException, InterruptedException {
		logger.debug("Step1");
		// 1: Login to module request (IP150 only)
		ParadoxIPPacket ipPacket = new ParadoxIPPacket(password, false).setCommand((byte) 0xF0);
		sendPacket(ipPacket);
		byte[] sendPacket = receivePacket();
		if (sendPacket[4] == 0x38) {
			logger.debug("Login OK");
		} else {
			logger.debug("Login failed");
		}

		logger.debug("Step2");
		// 2: Unknown request (IP150 only)
		ParadoxIPPacket step2 = new ParadoxIPPacket(ParadoxIPPacket.EMPTY_PAYLOAD, false).setCommand((byte) 0xF2);
		sendPacket(step2);
		receivePacket();

		logger.debug("Step3");
		// 3: Unknown request (IP150 only)
		ParadoxIPPacket step3 = new ParadoxIPPacket(ParadoxIPPacket.EMPTY_PAYLOAD, false).setCommand((byte) 0xF3);
		sendPacket(step3);
		receivePacket();

		logger.debug("Step4");
		// 4: Init communication over UIP softawre request (IP150 and direct serial)
		byte[] message4 = new byte[37];
		message4[0] = 0x72;
		ParadoxIPPacket step4 = new ParadoxIPPacket(message4, true).setMessageType((byte) 0x04);
		sendPacket(step4);
		receivePacket();

		logger.debug("Step5");
		// 5: Unknown request (IP150 only)
		ParadoxIPPacket step5 = new ParadoxIPPacket(IpMessages.unknownIP150Message01, false).setCommand((byte) 0xF8);
		sendPacket(step5);
		receivePacket();

		logger.debug("Step6");
		// 6: Initialize serial communication request (IP150 and direct serial)
		byte[] message6 = new byte[37];
		message6[0] = 0x5F;
		message6[1] = 0x20;
		ParadoxIPPacket step6 = new ParadoxIPPacket(message6, true).setMessageType((byte) 0x04);
		sendPacket(step6);
		byte[] response6 = receivePacket();
		byte[] initializationMessage = Arrays.copyOfRange(response6, 16, response6.length);
		ParadoxUtil.printByteArray("Init communication sub array: ", initializationMessage);

		logger.debug("Step7");
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
		sendPacket(step7);
		byte[] finalResponse = receivePacket();
		if ((finalResponse[16] & 0xF0) == 0x10) {
			logger.debug("SUCCESSFUL LOGON");
		} else {
			logger.debug("LOGON FAILURE");
		}
		Thread.sleep(300);
		// TODO check why after a short sleep a 37 bytes packet is received after logon ! ! !
		receivePacket();
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

		byte[] payloadResult = readEepromMemory(address, labelLength);

		String result = createString(payloadResult);
		logger.debug("Partition label: {}", result);
		return result;
	}

	private String createString(byte[] payloadResult) throws UnsupportedEncodingException {
		return new String(payloadResult, "US-ASCII");
	}

	public List<String> readZones() {
		List<String> result = new ArrayList<>();

		try {
			for (int i = 1; i <= 60; i++) {
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

		byte[] payloadResult = readEepromMemory(address, labelLength);

		String result = createString(payloadResult);
		logger.debug("Zone label: " + result);
		return result;
	}

	private byte[] readEepromMemory(int address, byte bytesToRead) throws Exception {
		if (bytesToRead < 1 || bytesToRead > 64)
			throw new Exception("Invalid bytes to read. Valid values are 1 to 64.");

		EpromRequestMessage message = new EpromRequestMessage(address, bytesToRead);
		ParadoxIPPacket readEpromMemoryPacket = new ParadoxIPPacket(message.getBytes(), false)
				.setMessageType((byte) 0x04).setUnknown0((byte) 0x14);
		sendPacket(readEpromMemoryPacket);
		byte[] response = receivePacket((byte) 0x5);
		return response;
	}

	private void sendPacket(ParadoxIPPacket packet) throws IOException {
		sendPacket(packet.getBytes());
	}

	private void sendPacket(byte[] packet) throws IOException {
		ParadoxUtil.printByteArray("Tx Packet:", packet);
		tx.write(packet);
	}

	private byte[] receivePacket() throws InterruptedException {
		for(int retryCounter = 0 ; retryCounter < 3 ; retryCounter++) {
			try {
				byte[] result = new byte[256];
				rx.read(result);
				ParadoxUtil.printByteArray("RX:", result);
				return Arrays.copyOfRange(result, 0, result[1] + 16);
			} catch (IOException e) {
				logger.debug("Unable to retrieve data from RX. {}", e.getMessage());
				Thread.sleep(100);
				if(retryCounter < 2) {
					logger.debug("Attempting one more time");
				}
			}
		}
		return new byte[0];
	}

	/// <summary>
	/// This method reads data from the IP150 module. It can return multiple
	/// responses
	/// e.g. a live event is combined with another response.
	/// </summary>
	/// <param name="networkStream">The open active TCP/IP stream.</param>
	/// <param name="command">A panel command, e.g. 0x5 (read memory)</param>
	/// <returns>An array of an array of the raw bytes received from the TCP/IP
	/// stream.</returns>
	private byte[] receivePacket(byte command) throws IOException, InterruptedException {
		if (command > 0xF)
			command = ParadoxUtil.getHighNibble(command);

		byte retryCounter = 0;

		// We might enter this too early, meaning the panel has not yet had time to
		// respond
		// to our command. We add a retry counter that will wait and retry.
		while (retryCounter < 3) {
			byte[] response = receivePacket();
			List<byte[]> responses = splitResponsePackets(response);
			for (byte[] bs : responses) {
				// Message too short
				if (response.length < 17)
					continue;

				// Response command (after header) is not related to reading memory
				if (ParadoxUtil.getHighNibble(response[16]) != command)
					continue;

				return Arrays.copyOfRange(response, 22, response.length-1);
			}

			// Give the panel time to send us a response
			Thread.sleep(100);

			retryCounter++;
		}

		logger.error("Failed to receive data for command 0x{0:X}", command);
		return null;
	}

	private List<byte[]> splitResponsePackets(byte[] response) {
		List<byte[]> packets = new ArrayList<byte[]>();
		try {
			int totalLength = response.length;
			while (response.length > 0) {
				if (response.length < 16 || (byte)response[0] != (byte)0xAA) {
//					throw new Exception("No 16 byte header found");
					logger.debug("No 16 byte header found");
				}

				byte[] header = Arrays.copyOfRange(response, 0, 16);
				byte messageLength = header[1];

				// Remove the header
				response = Arrays.copyOfRange(response, 16, totalLength);

				if (response.length < messageLength) {
					throw new Exception("Unexpected end of data");
				}

				// Check if there's padding bytes (0xEE)
				if (response.length > messageLength) {
					for (int i = messageLength; i < response.length; i++) {
						if (response[i] == 0xEE)
							messageLength++;
						else
							break;
					}
				}

				byte[] message = Arrays.copyOfRange(response, 0, messageLength);

				response = Arrays.copyOfRange(response, messageLength, response.length);

				packets.add(ParadoxUtil.mergeByteArrays(header, message));
			}
		} catch (Exception ex) {
			logger.error("Exception occurred: {}", ex.getMessage());
		}

		return packets;

	}
}
