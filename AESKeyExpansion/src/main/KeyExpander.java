package main;

public class KeyExpander {

    private static final int KEY_LENGTH = 240;
    private static int[] lTable = new int[256];
    private static int[] aTable = new int[256];

    public KeyExpander() {
        generateTables();
    }

    public int[] expandKey(String key) {
        int[] result = copyKeyBytesToArray(key);

        expandKey(result);

        return result;
    }

    private int[] copyKeyBytesToArray(String key) {
        int[] result = new int[KEY_LENGTH];
        byte[] strKeyBytes = key.getBytes();
        for (int i = 0; i < strKeyBytes.length; i++) {
            result[i] = strKeyBytes[i] & 0xFF;
        }
        return result;
    }

    public void expandKey(int[] in) {
        int[] t = new int[4];
        int c = 32;
        int i = 1;
        while (c < 240) {
            /* Copy the temporary variable over */
            for (int a = 0; a < 4; a++) {
                t[a] = in[a + c - 4] & 0xFF;
            }
            /* Every eight sets, do a complex calculation */
            if (c % 32 == 0) {
                scheduleCore(t, i);
                i++;
            }
            /*
             * For 256-bit keys, we add an extra sbox to the
             * calculation
             */
            if (c % 32 == 16) {
                for (int a = 0; a < 4; a++) {
                    t[a] = sbox(t[a]);
                }
            }
            for (int a = 0; a < 4; a++) {
                in[c] = (in[c - 32] ^ t[a]) & 0xFF;
                c++;
            }
        }
    }

    private void scheduleCore(int[] t, int i) {
        char a;
        /* Rotate the input 8 bits to the left */
        rotate(t);
        /* Apply Rijndael's s-box on all 4 bytes */
        for (a = 0; a < 4; a++) {
            t[a] = sbox(t[a]);
        }
        /* On just the first byte, add 2^i to the byte */
        t[0] ^= rcon(i);
    }

    private int rcon(int i) {
        int c = 1;
        if (i == 0) {
            return 0;
        }

        int counter = i;
        while (counter != 1) {
            c = gmul(c, 2);
            counter--;
        }
        return c;
    }

    private void rotate(int[] t) {
        int a, c;
        a = t[0];
        for (c = 0; c < 3; c++) {
            t[c] = t[c + 1];
        }
        t[3] = a;
        return;

    }

    private int sbox(int t) {
        int c, s, x;
        s = x = gmulInverse(t);
        for (c = 0; c < 4; c++) {
            /* One bit circular rotate to the left */
            s = ((s << 1) | (s >> 7)) & 0xFF;
            /* xor with x */
            x ^= s;
        }
        x ^= 99; /* 0x63 */
        return x & 0xFF;
    }

    private int gmul(int c, int b) {
        int s;
        int q;
        int z = 0;
        s = (lTable[c] + lTable[b]) & 0xFF;
        s %= 255;
        /* Get the antilog */
        s = aTable[s & 0xFF];
        /*
         * Now, we have some fancy code that returns 0 if either
         * a or b are zero; we write the code this way so that the
         * code will (hopefully) run at a constant speed in order to
         * minimize the risk of timing attacks
         */
        q = s;
        if (c == 0) {
            s = z;
        } else {
            s = q;
        }
        if (b == 0) {
            s = z;
        } else {
            q = z;
        }
        return s & 0xFF;
    }

    private int gmulInverse(int in) {
        /* 0 is self inverting */
        if (in == 0) {
            return 0;
        } else {
        	int index = lTable[in];
            return aTable[(255 - index)];
        }
    }

    private void generateTables() {
        int a = 1;
        int d;
        for (int index = 0; index < 255; index++) {
//        	System.out.println(index);
            aTable[index] = a & 0xFF;
            /* Multiply by three */
            d = (a & 0x80) & 0xFF;
            a <<= 1;
            if (d == 0x80) {
                a ^= 0x1b;
                a &= 0xFF;
            }
            a ^= aTable[index];
            a &= 0xFF;
            /* Set the log table value */
            try {
            	lTable[aTable[index]] = index & 0xFF;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        aTable[255] = aTable[0];
        lTable[0] = 0;

        Util.printIntArray(aTable, "ATABLE");
        Util.printIntArray(lTable, "LTABLE");
    }
}
