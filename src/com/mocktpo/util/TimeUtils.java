package com.mocktpo.util;

import com.mocktpo.util.constants.MT;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    private static final int ONE_MINUTE_IN_MILLISECONDS = 60000;
    private static final int ONE_HOUR_IN_MILLISECONDS = 3600000;
    private static final int ONE_DAY_IN_MILLISECONDS = 86400000;
    private static final int ONE_WEEK_IN_MILLISECONDS = 604800000;

    private TimeUtils() {
    }

    public static String displayTimePeriod(long time) {
        long hours = time / 3600;
        long minutes = (time - hours * 3600) / 60;
        long seconds = time - hours * 3600 - minutes * 60;
        DecimalFormat df = new DecimalFormat("00");
        return df.format(hours) + MT.STRING_COLON + df.format(minutes) + MT.STRING_COLON + df.format(seconds);
    }

    public static String displayTime(long time) {
        long duration = System.currentTimeMillis() - time;
        if (duration <= 1000) {
            return "just now";
        } else if (duration <= ONE_MINUTE_IN_MILLISECONDS) {
            return "1 minutes ago";
        } else if (duration <= ONE_HOUR_IN_MILLISECONDS) {
            return (duration / ONE_MINUTE_IN_MILLISECONDS) + " minutes ago";
        } else if (duration <= ONE_DAY_IN_MILLISECONDS) {
            return (duration / ONE_HOUR_IN_MILLISECONDS) + " hours ago";
        } else if (duration <= ONE_WEEK_IN_MILLISECONDS) {
            return (duration / ONE_DAY_IN_MILLISECONDS) + " days ago";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            return df.format(calendar.getTime());
        }
    }
}
