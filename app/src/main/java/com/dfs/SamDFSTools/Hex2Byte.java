package com.dfs.SamDFSTools;

import android.support.v4.media.TransportMediator;

public class Hex2Byte {
    private static final char[] kDigits;

    static {
        kDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    public static byte[] hexToBytes(char[] hex) {
        int length = hex.length / 2;
        byte[] raw = new byte[length];
        for (int i = 0; i < length; i++) {
            int high = Character.digit(hex[i * 2], 16);
            int value = (high << 4) | Character.digit(hex[(i * 2) + 1], 16);
            if (value > TransportMediator.KEYCODE_MEDIA_PAUSE) {
                value -= 256;
            }
            raw[i] = (byte) value;
        }
        return raw;
    }

    public static byte[] hexToBytes(String hex) {
        return hexToBytes(hex.toCharArray());
    }
}