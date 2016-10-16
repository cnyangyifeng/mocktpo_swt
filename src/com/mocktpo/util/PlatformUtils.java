package com.mocktpo.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class PlatformUtils {

    private PlatformUtils() {
    }

    public static boolean isMac() {
        String val = os();
        return "macosx".equals(val);
    }

    public static boolean isWindows() {
        String val = os();
        if ("windows8".equals(val) || "windows7".equals(val) || "windowsvista".equals(val) || "windowsxp".equals(val)) {
            return true;
        } else {
            return false;
        }
    }

    private static String os() {
        String os = System.getProperty("os.name");
        return StringUtils.deleteWhitespace(os).toLowerCase(Locale.US);
    }
}
