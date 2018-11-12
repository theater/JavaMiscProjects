package mainApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParadoxUtil {

	private static Logger logger = LoggerFactory.getLogger(ParadoxUtil.class);

	public static byte calculateChecksum(byte[] payload) {
		int result = 0;
		for (byte everyByte : payload) {
			result += everyByte;
		}

		return (byte) (result % 256);
	}

	public static byte getBit(int address, int bitNumber) {
		return (byte) ((address >> bitNumber) & 1);
	}

	public static void printByteArray(String description, byte[] array) {
		if (description != null && !description.isEmpty()) {
			logger.debug(description);
		}

		int i = 0;
		String result =  new String();
		for (byte b : array) {
			i++;
			String st = String.format("0x%02X,\t", b);
			result+=st;
			if (i > 7) {
				i = 0;
				logger.debug(result);
				result = "";
			}
		}
	}

	public static byte setBit(byte byteValue, byte position, byte setValue) {
		if (setValue == 1) {
			return (byte) (byteValue | (1 << position));
		} else {
			return (byte) (byteValue & ~(1 << position));
		}
	}

}
