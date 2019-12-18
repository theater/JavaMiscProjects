package main;

public class Util {
    public static String byteArrayToString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (byte b : array) {
            sb.append(String.format("%02X", b)).append(" ");
            if (counter++ == 15) {
                sb.append("\n");
                counter = 0;
            }

        }
        return sb.toString();
    }

    public static void printByteArray(byte[] array, String description) {
        System.out.println(description + ":\n" + byteArrayToString(array));
    }

    public static void printByteArray(byte[] array) {
        System.out.println(byteArrayToString(array));
    }

    public static void main(String... args) {
        printByteArray(new byte[] { 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
                00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00 });
    }
}
