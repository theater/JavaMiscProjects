package main;

public class KeyExpander {

    private static final int KEY_LENGTH = 240;
    private static byte[] lTable = new byte[256];
    private static byte[] aTable = new byte[256];

    public KeyExpander() {
        generateTables();
    }

    public byte[] expandKey(String key) {
        byte[] result = copyKeyBytesToArray(key);

        expandKey(result);

        return result;
    }

    private byte[] copyKeyBytesToArray(String key) {
        byte[] result = new byte[KEY_LENGTH];
        byte[] strKeyBytes = key.getBytes();
        for (int i = 0; i < strKeyBytes.length; i++) {
            result[i] = strKeyBytes[i];
        }
        return result;
    }

    public void expandKey(byte[] in) {
        byte[] t = new byte[4];
        byte c = 32;
        byte i = 1;
        byte a;
        while (c < 240) {
            /* Copy the temporary variable over */
            for (a = 0; a < 4; a++) {
                t[a] = in[a + c - 4];
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
                for (a = 0; a < 4; a++) {
                    t[a] = sbox(t[a]);
                }
            }
            for (a = 0; a < 4; a++) {
                in[c] = (byte) (in[c - 32] ^ t[a]);
                c++;
            }
        }
    }

    private void scheduleCore(byte[] in, byte i) {
        char a;
        /* Rotate the input 8 bits to the left */
        rotate(in);
        /* Apply Rijndael's s-box on all 4 bytes */
        for (a = 0; a < 4; a++) {
            in[a] = sbox(in[a]);
        }
        /* On just the first byte, add 2^i to the byte */
        in[0] ^= rcon(i);
    }

    private byte rcon(byte in) {
        byte c = 1;
        if (in == 0) {
            return 0;
        }

        byte counter = in;
        while (counter != 1) {
            c = gmul(c, (byte) 2);
            counter--;
        }
        return c;
    }

    private void rotate(byte[] in) {
        byte a, c;
        a = in[0];
        for (c = 0; c < 3; c++) {
            in[c] = in[c + 1];
        }
        in[3] = a;
        return;

    }

    private byte sbox(byte in) {
        byte c, s, x;
        s = x = gmulInverse(in);
        for (c = 0; c < 4; c++) {
            /* One bit circular rotate to the left */
            s = (byte) ((s << 1) | (s >> 7));
            /* xor with x */
            x ^= s;
        }
        x ^= 99; /* 0x63 */
        return x;
    }

    private byte gmul(byte a, byte b) {
        byte s;
        byte q;
        byte z = 0;
        s = (byte) (lTable[a] + lTable[b]);
        s %= 255;
        /* Get the antilog */
        s = aTable[s];
        /*
         * Now, we have some fancy code that returns 0 if either
         * a or b are zero; we write the code this way so that the
         * code will (hopefully) run at a constant speed in order to
         * minimize the risk of timing attacks
         */
        q = s;
        if (a == 0) {
            s = z;
        } else {
            s = q;
        }
        if (b == 0) {
            s = z;
        } else {
            q = z;
        }
        return s;
    }

    private byte gmulInverse(byte in) {
        /* 0 is self inverting */
        if (in == 0) {
            return 0;
        } else {
            return aTable[(255 - lTable[in])];
        }
    }

    private void generateTables() {
        byte c;
        byte a = 1;
        byte d;
        for (c = 0; c < 255; c++) {
            int index = c & 0xFF;
            aTable[index] = (byte) (a & 0xFF);
            /* Multiply by three */
            d = (byte) (a & 0x80);
            a <<= 1;
            if (d == 0x80) {
                a ^= 0x1b;
            }
            a ^= aTable[index];
            /* Set the log table value */
            try {
            } catch (Exception e) {
                System.out.println(e);
            }
            lTable[aTable[index]] = (byte) index;
        }
        aTable[255] = aTable[0];
        lTable[0] = 0;
    }
}
