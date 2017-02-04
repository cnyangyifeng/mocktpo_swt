package com.mocktpo.util;

import java.util.Locale;

public class PlatformUtils {

    private static String OS = System.getProperty("os.name").toLowerCase(Locale.US);

    private PlatformUtils() {
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isWindows() {
        return OS.contains("win");
    }
}
