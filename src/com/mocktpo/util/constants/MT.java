package com.mocktpo.util.constants;

/**
 * MockTPO Constants
 */
public interface MT {

    /*
     * ==================================================
     *
     * Characters
     *
     * ==================================================
     */

    char CHAR_SEMICOLON = ';';

    /*
     * ==================================================
     *
     * Choices
     *
     * ==================================================
     */

    int CHOICE_NONE = 0;
    int CHOICE_A = 1;
    int CHOICE_B = 2;
    int CHOICE_C = 3;
    int CHOICE_D = 4;
    int CHOICE_E = 5;
    int CHOICE_F = 6;
    int CHOICE_G = 7;

    int CHOICE_NEVER_CHECK_MARKED = -1;
    int CHOICE_NO = 0;
    int CHOICE_YES = 1;

    /*
     * ==================================================
     *
     * Colors
     *
     * ==================================================
     */

    int COLOR_BEIGE = 1;
    int COLOR_BLACK = 2;
    int COLOR_BURGUNDY = 3;
    int COLOR_DARK_ORANGE = 4;
    int COLOR_DARK_BLUE = 5;
    int COLOR_GRAY20 = 6;
    int COLOR_GRAY40 = 7;
    int COLOR_GRAY60 = 8;
    int COLOR_GREEN = 9;
    int COLOR_INDIGO = 10;
    int COLOR_ORANGE = 11;
    int COLOR_ORANGE_RED = 12;
    int COLOR_OXFORD_BLUE = 13;
    int COLOR_ROSY_BROWN = 14;
    int COLOR_TOUPE = 15;
    int COLOR_WHITE = 16;
    int COLOR_WHITE_SMOKE = 17;

    /* Others */

    int COLOR_HIGHLIGHTED = 91;
    int COLOR_WINDOW_BACKGROUND = 92;

    /*
     * ==================================================
     *
     * Cursors
     *
     * ==================================================
     */

    int CURSOR_ARROW = 101;
    int CURSOR_HAND = 102;

    /*
     * ==================================================
     *
     * Windows
     *
     * ==================================================
     */

    int REQUIRED_ANSWER_WINDOW_TYPE_NO_ANSWER_FOR_ONE = 1;
    int REQUIRED_ANSWER_WINDOW_TYPE_NO_ANSWER_FOR_MANY = 2;
    int REQUIRED_ANSWER_WINDOW_TYPE_INCORRECT_ANSWER_COUNT = 3;

    /*
     * ==================================================
     *
     * Fonts
     *
     * ==================================================
     */

    int FONT_XX_SMALL = 201;
    int FONT_X_SMALL = 202;
    int FONT_SMALL = 203;
    int FONT_SMALL_BOLD = 204;
    int FONT_SMALL_ITALIC = 205;
    int FONT_MEDIUM = 206;
    int FONT_MEDIUM_BOLD = 207;
    int FONT_MEDIUM_ITALIC = 208;
    int FONT_LARGE = 209;
    int FONT_LARGE_BOLD = 210;
    int FONT_X_LARGE = 211;
    int FONT_X_LARGE_BOLD = 212;
    int FONT_XX_LARGE = 213;

    int FONT_ACTIVATION_CODE = 214;
    int FONT_SERIF_HEADING = 215;
    int FONT_SERIF_ITALIC_TEXT = 216;

    /*
     * ==================================================
     *
     * Images
     *
     * ==================================================
     */

    int IMAGE_BACK_OVAL = 301;
    int IMAGE_BACK_OVAL_DISABLED = 302;
    int IMAGE_BACK_OVAL_HOVER = 303;
    int IMAGE_BOXED = 304;
    int IMAGE_BULLET = 305;
    int IMAGE_CHECKED = 306;
    int IMAGE_CONFIRM_RESPONSE = 307;
    int IMAGE_CONFIRM_RESPONSE_DISABLED = 308;
    int IMAGE_CONFIRM_RESPONSE_HOVER = 309;
    int IMAGE_CONTINUE = 310;
    int IMAGE_CONTINUE_DISABLED = 311;
    int IMAGE_CONTINUE_DEBUG = 312;
    int IMAGE_CONTINUE_DEBUG_HOVER = 313;
    int IMAGE_CONTINUE_HOVER = 314;
    int IMAGE_CONTINUE_OVAL = 315;
    int IMAGE_CONTINUE_OVAL_DISABLED = 316;
    int IMAGE_CONTINUE_OVAL_HOVER = 317;
    int IMAGE_ETS_TOEFL = 318;
    int IMAGE_GO_TO_QUESTION = 319;
    int IMAGE_GO_TO_QUESTION_HOVER = 320;
    int IMAGE_HEADSET = 321;
    int IMAGE_HELP_OVAL = 322;
    int IMAGE_HELP_OVAL_DISABLED = 323;
    int IMAGE_HELP_OVAL_HOVER = 324;
    int IMAGE_HIDE_TIME = 325;
    int IMAGE_HIDE_TIME_DISABLED = 326;
    int IMAGE_HIDE_TIME_HOVER = 327;
    int IMAGE_NEXT_OVAL = 328;
    int IMAGE_NEXT_OVAL_DISABLED = 329;
    int IMAGE_NEXT_OVAL_HOVER = 330;
    int IMAGE_OK_OVAL = 331;
    int IMAGE_OK_OVAL_DISABLED = 332;
    int IMAGE_OK_OVAL_HOVER = 333;
    int IMAGE_PAUSE_TEST = 334;
    int IMAGE_PAUSE_TEST_HOVER = 335;
    int IMAGE_PLAYBACK_RESPONSE = 336;
    int IMAGE_PLAYBACK_RESPONSE_DISABLED = 337;
    int IMAGE_PLAYBACK_RESPONSE_HOVER = 338;
    int IMAGE_READY_TO_ANSWER = 339;
    int IMAGE_READY_TO_ANSWER_2 = 340;
    int IMAGE_RECORD_AGAIN = 341;
    int IMAGE_RECORD_AGAIN_DISABLED = 342;
    int IMAGE_RECORD_AGAIN_HOVER = 343;
    int IMAGE_RETURN = 344;
    int IMAGE_RETURN_HOVER = 345;
    int IMAGE_RETURN_TO_QUESTION = 346;
    int IMAGE_RETURN_TO_QUESTION_HOVER = 347;
    int IMAGE_REVIEW = 348;
    int IMAGE_REVIEW_HOVER = 349;
    int IMAGE_REVIEW_OVAL = 350;
    int IMAGE_REVIEW_OVAL_DISABLED = 351;
    int IMAGE_REVIEW_OVAL_HOVER = 352;
    int IMAGE_SHOW_TIME = 353;
    int IMAGE_SHOW_TIME_DISABLED = 354;
    int IMAGE_SHOW_TIME_HOVER = 355;
    int IMAGE_STOP_RECORDING = 356;
    int IMAGE_STOP_RECORDING_HOVER = 357;
    int IMAGE_UNBOXED = 358;
    int IMAGE_UNCHECKED = 359;
    int IMAGE_VIEW_QUESTION = 360;
    int IMAGE_VIEW_QUESTION_HOVER = 361;
    int IMAGE_VIEW_TEXT = 362;
    int IMAGE_VIEW_TEXT_HOVER = 363;
    int IMAGE_VOLUME_OVAL = 364;
    int IMAGE_VOLUME_OVAL_HOVER = 365;

    int IMAGE_APP_ICON = 3001;
    int IMAGE_LOGO = 3002;
    int IMAGE_SPLASH = 3003;
    int IMAGE_SYSTEM_BACK = 3004;
    int IMAGE_SYSTEM_BACK_HOVER = 3005;
    int IMAGE_SYSTEM_BOXED = 3006;
    int IMAGE_SYSTEM_CONTINUE = 3007;
    int IMAGE_SYSTEM_CONTINUE_HOVER = 3008;
    int IMAGE_SYSTEM_DELETE = 3009;
    int IMAGE_SYSTEM_DELETE_HOVER = 3010;
    int IMAGE_SYSTEM_EXPORT = 3011;
    int IMAGE_SYSTEM_EXPORT_HOVER = 3012;
    int IMAGE_SYSTEM_IMPORT = 3013;
    int IMAGE_SYSTEM_IMPORT_HOVER = 3014;
    int IMAGE_SYSTEM_NEW_TEST = 3015;
    int IMAGE_SYSTEM_NEW_TEST_HOVER = 3016;
    int IMAGE_SYSTEM_REPORT = 3017;
    int IMAGE_SYSTEM_REPORT_HOVER = 3018;
    int IMAGE_SYSTEM_STAR = 3019;
    int IMAGE_SYSTEM_STAR_OUTLINE = 3020;
    int IMAGE_SYSTEM_UNBOXED = 3021;

    /*
     * ==================================================
     *
     * Key-Value Pairs
     *
     * ==================================================
     */

    String KEY_CHOICE = "choice";
    String KEY_QUESTION = "question";

    /*
     * ==================================================
     *
     * Strings
     *
     * ==================================================
     */

    String STRING_COLON = ":";
    String STRING_COMMA = ",";
    String STRING_QUESTION = "Question";
    String STRING_OF = "of";
    String STRING_SPACE = " ";
    String STRING_SEMICOLON = ";";
    String STRING_SLASH = "/";

    /*
     * ==================================================
     *
     * Time
     *
     * ==================================================
     */

    int TIME_READING_SECTION = 3600;
    int TIME_LISTENING_PER_SUB_SECTION = 600;
    int TIME_SPEAKING_READING_PER_TASK = 60;
    int TIME_WRITING_READING_PER_TASK = 180;
    int TIME_INTEGRATED_WRITING_TASK = 1200;
    int TIME_INDEPENDENT_WRITING_TASK = 1800;

    /*
     * ==================================================
     *
     * Questions
     *
     * ==================================================
     */

    int QUESTION_1 = 1;
    int QUESTION_2 = 2;
    int QUESTION_3 = 3;
    int QUESTION_4 = 4;
    int QUESTION_5 = 5;
}
