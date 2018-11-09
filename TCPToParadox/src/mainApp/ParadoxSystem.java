package mainApp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ParadoxSystem {

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
		log("Logout sequence started");
        byte[] logoutMessage = new byte[] { 0x00, 0x07, 0x05, 0x00, 0x00, 0x00, 0x00 };
  		ParadoxIPPacket logoutPacket = new ParadoxIPPacket(logoutMessage, true).setMessageType((byte) 0x04).setUnknown0((byte) 0x14);
  		sendPacket(logoutPacket);
	}

	public void logonSequence() throws IOException {
		log("Step1");
		// 1: Login to module request (IP150 only)
		ParadoxIPPacket ipPacket = new ParadoxIPPacket(password, false).setCommand((byte) 0xF0);
		byte[] sendPacket = sendPacket(ipPacket);
		if (sendPacket[4] == 0x38) {
			log("Login OK");
		} else {
			log("Login failed");
		}

		log("Step2");
		// 2: Unknown request (IP150 only)
		ParadoxIPPacket step2 = new ParadoxIPPacket(ParadoxIPPacket.EMPTY_PAYLOAD, false).setCommand((byte) 0xF2);
		sendPacket(step2);

		log("Step3");
		// 3: Unknown request (IP150 only)
		ParadoxIPPacket step3 = new ParadoxIPPacket(ParadoxIPPacket.EMPTY_PAYLOAD, false).setCommand((byte) 0xF3);
		sendPacket(step3);

		log("Step4");
		// 4: Init communication over UIP softawre request (IP150 and direct serial)
		byte[] message4 = new byte[37];
		message4[0] = 0x72;
		ParadoxIPPacket step4 = new ParadoxIPPacket(message4, true).setMessageType((byte) 0x04);
		sendPacket(step4);
		// TODO probably this two lines are not needed
//		byte[] initCommOverSoftwareMessage = Arrays.copyOfRange(response4, 16, response4.length - 1);
//		printByteArray("Init communication sub array: ", initCommOverSoftwareMessage);

		log("Step5");
		// 5: Unknown request (IP150 only)
		ParadoxIPPacket step5 = new ParadoxIPPacket(IpMessages.unknownIP150Message01, false).setCommand((byte) 0xF8);
		sendPacket(step5);

		log("\nStep6");
		// 6: Initialize serial communication request (IP150 and direct serial)
		byte[] message6 = new byte[37];
		message6[0] = 0x5F;
		message6[1] = 0x20;
		ParadoxIPPacket step6 = new ParadoxIPPacket(message6, true).setMessageType((byte) 0x04);
		byte[] response6 = sendPacket(step6);
		byte[] initializationMessage = Arrays.copyOfRange(response6, 16, response6.length);
		printByteArray("Init communication sub array: ", initializationMessage);

		log("\nStep7");
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
		ParadoxIPPacket step7 = new ParadoxIPPacket(message7, true).setMessageType((byte) 0x04).setUnknown0((byte) 0x14);
		byte[] finalResponse = sendPacket(step7);
		if((finalResponse[16] & 0xF0) == 0x10) {
			log("SUCCESSFUL LOGON");
		} else {
			log("LOGON FAILURE");
		}
	}

	public String readPartitionLabel(byte partitionNo) throws Exception
    {
        if (partitionNo < 1 || partitionNo > 8)
            throw new Exception("Invalid partition number. Valid values are 1-8.");

        //region EVO192 specific
        int address = (int)(0x3A6B + (partitionNo - 1) * 107);
        byte labelLength = 16;

        byte[] zoneLabelBytes = readEepromMemory((short) address, labelLength);

        return new String(zoneLabelBytes, "US-ASCII");
    }

	  private byte[] readEepromMemory(short address, byte bytesToRead) throws Exception
      {
          if (bytesToRead < 1 || bytesToRead > 64)
              throw new Exception("Invalid bytes to read. Valid values are 1 to 64.");

          EpromRequestMessage message = new EpromRequestMessage(address, bytesToRead);
          ParadoxIPPacket readEpromMemoryPacket = new ParadoxIPPacket(message.getBytes(), false).setMessageType((byte) 0x04).setUnknown0((byte) 0x14);
          byte[] sendPacket = sendPacket(readEpromMemoryPacket);

          return sendPacket;
      }

	private byte[] sendPacket(ParadoxIPPacket packet) throws IOException {
		return sendPacket(packet.getBytes());
	}

	private byte[] sendPacket(byte[] packet) throws IOException {
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

	private void printByteArray(String description, byte[] array) {
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

	private void log(String arg) {
		System.out.println(arg);
	}
}
