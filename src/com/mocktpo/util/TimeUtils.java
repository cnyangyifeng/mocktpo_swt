package com.mocktpo.util;

import com.mocktpo.util.constants.MT;

import java.text.DecimalFormat;

public class TimeUtils {

    private TimeUtils() {
    }

    public static String displayTime(long time) {
        long hours = time / 3600;
        long minutes = (time - hours * 3600) / 60;
        long seconds = time - hours * 3600 - minutes * 60;
        DecimalFormat df = new DecimalFormat("00");
        return df.format(hours) + MT.STRING_COLON + df.format(minutes) + MT.STRING_COLON + df.format(seconds);
    }
}
