package main;

public class Main {
    public static void main(String... args) {
        byte[] array = new byte[240];
        for (int i = 0; i < 16; i++) {
            array[i] = 0;
        }
        new KeyExpander().expandKey(array);
        String byteArrayToString = Util.byteArrayToString(array);
        System.out.println("Final array:\n" + byteArrayToString);
    }
}
