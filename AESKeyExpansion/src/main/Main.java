package main;

public class Main {
    public static void main(String... args) {
        int[] array = new int[240];
        for (int i = 0; i < 32; i++) {
            array[i] = i;
        }
        System.out.println("Initial array:\n" + Util.intArrayToString(array));

        new KeyExpander().expandKey(array);

        String byteArrayToString = Util.intArrayToString(array);
        System.out.println("Final array:\n" + byteArrayToString);
    }
}
