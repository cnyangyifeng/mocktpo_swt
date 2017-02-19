package com.mocktpo.util.widgets;

import com.mocktpo.util.constants.MT;
import org.apache.commons.lang3.StringUtils;

public class AnswerUtils {

    private AnswerUtils() {
    }

    public static String toAlphabeticAnswer(String answer) {
        if (StringUtils.isEmpty(answer)) {
            return "NIL";
        }
        int val = Integer.parseInt(answer);
        String result;
        switch (val) {
            case MT.CHOICE_NONE:
                result = "--";
                break;
            case MT.CHOICE_A:
                result = "A";
                break;
            case MT.CHOICE_B:
                result = "B";
                break;
            case MT.CHOICE_C:
                result = "C";
                break;
            case MT.CHOICE_D:
                result = "D";
                break;
            case MT.CHOICE_E:
                result = "E";
                break;
            case MT.CHOICE_F:
                result = "F";
                break;
            case MT.CHOICE_G:
                result = "G";
                break;
            default:
                result = "N/A";
        }
        return result;
    }
}
