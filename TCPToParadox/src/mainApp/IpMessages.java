package mainApp;

public class IpMessages {
//	enum
	public static byte[] loginHeader = {
			(byte) 0xAA,
			(byte) 0x0A,
			(byte) 0x00,
			(byte) 0x03,
			(byte) 0x08,
			(byte) 0xF0,
			(byte) 0x00,
			(byte) 0x0A,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE
	};

	public static byte[] emptyMessage1 = {
			(byte) 0xAA,
			(byte) 0x00,
			(byte) 0x00,
			(byte) 0x03,
			(byte) 0x08,
			(byte) 0xF2,
			(byte) 0x00,
			(byte) 0x0A,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE
	};

	public static byte[] emptyMessage2 = {
			(byte) 0xAA,
			(byte) 0x00,
			(byte) 0x00,
			(byte) 0x03,
			(byte) 0x08,
			(byte) 0xF3,
			(byte) 0x00,
			(byte) 0x0A,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE
	};

	public static byte[] filler = {
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE,
			(byte) 0xEE
	};
}
