package mainApp;

public class ParadoxUtil {
	public static byte calculateChecksum(byte[] payload) {
		int result = 0;
		for (byte everyByte : payload) {
			result += everyByte;
		}

		return (byte) (result % 256);
	}
}
