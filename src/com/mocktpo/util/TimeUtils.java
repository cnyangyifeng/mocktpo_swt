package com.mocktpo.util;

import java.text.DecimalFormat;

public class TimeUtils {

    public static String displayTime(long time) {
        long hours = time / 3600;
        long minutes = (time - hours * 3600) / 60;
        long seconds = time - hours * 3600 - minutes * 60;
        DecimalFormat df = new DecimalFormat("00");
        return df.format(hours) + ":" + df.format(minutes) + ":" + df.format(seconds);
    }
}
