/*
 * Copyright (C) 2009-2010 Aubort Jean-Baptiste (Rorist)
 * Licensed under GNU's GPL 2, see README
 */

// http://standards.ieee.org/regauth/oui/oui.txt

package com.restaurant.project.mikuyapp.scan;

import static jcifs.util.Hexdump.HEX_DIGITS;

final class HardwareAddress {

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[17];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_DIGITS[v >>> 4];
            hexChars[j * 3 + 1] = HEX_DIGITS[v & 0x0F];
            if (!(j == bytes.length - 1)) hexChars[j * 3 + 2] = ':';
        }
        return new String(hexChars).trim();
    }
}
