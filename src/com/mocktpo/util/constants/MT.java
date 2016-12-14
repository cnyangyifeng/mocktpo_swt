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
    int COLOR_GRAY40 = 6;
    int COLOR_GREEN = 7;
    int COLOR_INDIGO = 8;
    int COLOR_ORANGE = 9;
    int COLOR_ORANGE_RED = 10;
    int COLOR_OXFORD_BLUE = 11;
    int COLOR_ROSY_BROWN = 12;
    int COLOR_TOUPE = 13;
    int COLOR_WHITE = 14;
    int COLOR_WHITE_SMOKE = 15;

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
     * Dialogs
     *
     * ==================================================
     */

    int REQUIRED_ANSWER_DIALOG_TYPE_NO_ANSWER_FOR_ONE = 1;
    int REQUIRED_ANSWER_DIALOG_TYPE_NO_ANSWER_FOR_MANY = 2;
    int REQUIRED_ANSWER_DIALOG_TYPE_INCORRECT_ANSWER_COUNT = 3;

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
    int FONT_MEDIUM = 205;
    int FONT_MEDIUM_BOLD = 206;
    int FONT_LARGE = 207;
    int FONT_LARGE_BOLD = 208;
    int FONT_X_LARGE = 209;
    int FONT_X_LARGE_BOLD = 210;
    int FONT_XX_LARGE = 211;

    int FONT_ACTIVATION_CODE = 212;
    int FONT_SERIF_HEADING = 213;
    int FONT_SERIF_ITALIC_TEXT = 214;

    /*
     * ==================================================
     *
     * Images
     *
     * ==================================================
     */

    int IMAGE_APP_ICON = 301;
    int IMAGE_ARROW_RIGHT = 302;
    int IMAGE_BACK_OVAL = 303;
    int IMAGE_BACK_OVAL_HOVER = 304;
    int IMAGE_BACK_OVAL_DISABLED = 305;
    int IMAGE_BOXED = 306;
    int IMAGE_BULLET = 307;
    int IMAGE_CHECKED = 308;
    int IMAGE_CONTINUE = 309;
    int IMAGE_CONTINUE_HOVER = 310;
    int IMAGE_CONTINUE_DISABLED = 311;
    int IMAGE_CONTINUE_OVAL = 312;
    int IMAGE_CONTINUE_OVAL_HOVER = 313;
    int IMAGE_CONTINUE_OVAL_DISABLED = 314;
    int IMAGE_ETS_TOEFL = 315;
    int IMAGE_GO_TO_QUESTION = 316;
    int IMAGE_GO_TO_QUESTION_HOVER = 317;
    int IMAGE_HEADSET = 318;
    int IMAGE_HELP_OVAL = 319;
    int IMAGE_HELP_OVAL_HOVER = 320;
    int IMAGE_HELP_OVAL_DISABLED = 321;
    int IMAGE_HIDE_TIME = 322;
    int IMAGE_HIDE_TIME_HOVER = 323;
    int IMAGE_HIDE_TIME_DISABLED = 324;
    int IMAGE_LOGO = 325;
    int IMAGE_NEXT_OVAL = 326;
    int IMAGE_NEXT_OVAL_HOVER = 327;
    int IMAGE_NEXT_OVAL_DISABLED = 328;
    int IMAGE_OK_OVAL = 329;
    int IMAGE_OK_OVAL_HOVER = 330;
    int IMAGE_OK_OVAL_DISABLED = 331;
    int IMAGE_PAUSE_TEST = 332;
    int IMAGE_PAUSE_TEST_HOVER = 333;
    int IMAGE_PLAYBACK_RESPONSE = 334;
    int IMAGE_PLAYBACK_RESPONSE_HOVER = 335;
    int IMAGE_PLAYBACK_RESPONSE_DISABLED = 336;
    int IMAGE_READY_TO_ANSWER = 337;
    int IMAGE_RECORD_AGAIN = 338;
    int IMAGE_RECORD_AGAIN_HOVER = 339;
    int IMAGE_RECORD_AGAIN_DISABLED = 340;
    int IMAGE_RETURN = 341;
    int IMAGE_RETURN_HOVER = 342;
    int IMAGE_RETURN_TO_QUESTION = 343;
    int IMAGE_RETURN_TO_QUESTION_HOVER = 344;
    int IMAGE_REVIEW = 345;
    int IMAGE_REVIEW_HOVER = 346;
    int IMAGE_REVIEW_OVAL = 347;
    int IMAGE_REVIEW_OVAL_HOVER = 348;
    int IMAGE_REVIEW_OVAL_DISABLED = 349;
    int IMAGE_SHOW_TIME = 350;
    int IMAGE_SHOW_TIME_HOVER = 351;
    int IMAGE_SHOW_TIME_DISABLED = 352;
    int IMAGE_SPLASH = 353;
    int IMAGE_STOP_RECORDING = 354;
    int IMAGE_STOP_RECORDING_HOVER = 355;
    int IMAGE_VIEW_QUESTION = 356;
    int IMAGE_VIEW_QUESTION_HOVER = 357;
    int IMAGE_VIEW_TEXT = 358;
    int IMAGE_VIEW_TEXT_HOVER = 359;
    int IMAGE_VOLUME_OVAL = 360;
    int IMAGE_VOLUME_OVAL_HOVER = 361;
    int IMAGE_UNBOXED = 362;
    int IMAGE_UNCHECKED = 363;

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

    String STRING_QUESTION = "Question";
    String STRING_OF = "of";
    String STRING_SPACE = " ";

    /*
     * ==================================================
     *
     * Time
     *
     * ==================================================
     */

    int TIME_LISTENING_GROUP = 600;
    int TIME_READING_SECTION = 3600;

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
