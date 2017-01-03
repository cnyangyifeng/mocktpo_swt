package com.mocktpo.util;

import java.util.StringTokenizer;

public class WordCountUtils {

    public static int count(String text) {
        StringTokenizer st = new StringTokenizer(text, "\n\r\t ");
        int count = 0;
        while (st.hasMoreTokens()) {
            st.nextToken();
            count++;
        }
        return count;
    }
}
