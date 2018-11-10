package mainApp;

public class ParadoxUtil {
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

	public static byte setBit(byte byteValue, byte position, byte setValue) {
		if (setValue == 1) {
			return (byte) (byteValue | (1 << position));
		} else {
			return (byte) (byteValue & ~(1 << position));
		}
	}

}
