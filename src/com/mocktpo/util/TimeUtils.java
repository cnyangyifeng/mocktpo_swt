package com.mocktpo.util;

import com.mocktpo.util.constants.MT;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class TimeUtils {

    private static final int TEN_SECONDS_IN_MILLISECONDS = 10000;
    private static final int ONE_MINUTE_IN_MILLISECONDS = 60000;
    private static final int TWO_MINUTES_IN_MILLISECONDS = 120000;
    private static final int ONE_HOUR_IN_MILLISECONDS = 3600000;
    private static final int ONE_DAY_IN_MILLISECONDS = 86400000;
    private static final int ONE_WEEK_IN_MILLISECONDS = 604800000;

    /* Messages */

    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    private TimeUtils() {
    }

    public static String displayTimePeriod(long time) {
        long hours = time / 3600;
        long minutes = (time - hours * 3600) / 60;
        long seconds = time - hours * 3600 - minutes * 60;
        DecimalFormat df = new DecimalFormat("00");
        return df.format(hours) + MT.STRING_COLON + df.format(minutes) + MT.STRING_COLON + df.format(seconds);
    }

    public static String displaySocialTime(long time) {
        long duration = System.currentTimeMillis() - time;
        if (duration <= TEN_SECONDS_IN_MILLISECONDS) {
            return msgs.getString("just_now");
        } else if (duration <= ONE_MINUTE_IN_MILLISECONDS) {
            return msgs.getString("a_few_seconds_ago");
        } else if (duration <= TWO_MINUTES_IN_MILLISECONDS) {
            return msgs.getString("one_minute_ago");
        } else if (duration <= ONE_HOUR_IN_MILLISECONDS) {
            return (duration / ONE_MINUTE_IN_MILLISECONDS) + MT.STRING_SPACE + msgs.getString("minutes_ago");
        } else if (duration <= ONE_DAY_IN_MILLISECONDS) {
            return (duration / ONE_HOUR_IN_MILLISECONDS) + MT.STRING_SPACE + msgs.getString("hours_ago");
        } else if (duration <= ONE_WEEK_IN_MILLISECONDS) {
            return (duration / ONE_DAY_IN_MILLISECONDS) + MT.STRING_SPACE + msgs.getString("days_ago");
        } else {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            return df.format(calendar.getTime());
        }
    }

    public static String displayClockTime(long time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return df.format(calendar.getTime());
    }
}
