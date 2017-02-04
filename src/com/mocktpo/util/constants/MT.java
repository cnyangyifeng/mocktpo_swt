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
    int FONT_MEDIUM_ITALIC = 207;
    int FONT_LARGE = 208;
    int FONT_LARGE_BOLD = 209;
    int FONT_X_LARGE = 210;
    int FONT_X_LARGE_BOLD = 211;
    int FONT_XX_LARGE = 212;

    int FONT_ACTIVATION_CODE = 213;
    int FONT_SERIF_HEADING = 214;
    int FONT_SERIF_ITALIC_TEXT = 215;

    /*
     * ==================================================
     *
     * Images
     *
     * ==================================================
     */

    int IMAGE_APP_ICON = 301;
    int IMAGE_ARROW_RIGHT = 302; /* TODO Remove */
    int IMAGE_BACK_OVAL = 303;
    int IMAGE_BACK_OVAL_HOVER = 304;
    int IMAGE_BACK_OVAL_DISABLED = 305;
    int IMAGE_BOXED = 306;
    int IMAGE_BULLET = 307;
    int IMAGE_CHECKED = 308;
    int IMAGE_CONFIRM_RESPONSE = 309;
    int IMAGE_CONFIRM_RESPONSE_HOVER = 310;
    int IMAGE_CONFIRM_RESPONSE_DISABLED = 311;
    int IMAGE_CONTINUE = 312;
    int IMAGE_CONTINUE_HOVER = 313;
    int IMAGE_CONTINUE_DISABLED = 314;
    int IMAGE_CONTINUE_DEBUG = 315;
    int IMAGE_CONTINUE_DEBUG_HOVER = 316;
    int IMAGE_CONTINUE_OVAL = 317;
    int IMAGE_CONTINUE_OVAL_HOVER = 318;
    int IMAGE_CONTINUE_OVAL_DISABLED = 319;
    int IMAGE_ETS_TOEFL = 320;
    int IMAGE_EXERCISES = 321; /* TODO Remove */
    int IMAGE_GO_TO_QUESTION = 322;
    int IMAGE_GO_TO_QUESTION_HOVER = 323;
    int IMAGE_HEADSET = 324;
    int IMAGE_HELP_OVAL = 325;
    int IMAGE_HELP_OVAL_HOVER = 326;
    int IMAGE_HELP_OVAL_DISABLED = 327;
    int IMAGE_HIDE_TIME = 328;
    int IMAGE_HIDE_TIME_HOVER = 329;
    int IMAGE_HIDE_TIME_DISABLED = 330;
    int IMAGE_LOGO = 331;
    int IMAGE_NEXT_OVAL = 332;
    int IMAGE_NEXT_OVAL_HOVER = 333;
    int IMAGE_NEXT_OVAL_DISABLED = 334;
    int IMAGE_OK_OVAL = 335;
    int IMAGE_OK_OVAL_HOVER = 336;
    int IMAGE_OK_OVAL_DISABLED = 337;
    int IMAGE_PAUSE_TEST = 338;
    int IMAGE_PAUSE_TEST_HOVER = 339;
    int IMAGE_PLAYBACK_RESPONSE = 340;
    int IMAGE_PLAYBACK_RESPONSE_HOVER = 341;
    int IMAGE_PLAYBACK_RESPONSE_DISABLED = 342;
    int IMAGE_READY_TO_ANSWER = 343;
    int IMAGE_READY_TO_ANSWER_2 = 344;
    int IMAGE_RECORD_AGAIN = 345;
    int IMAGE_RECORD_AGAIN_HOVER = 346;
    int IMAGE_RECORD_AGAIN_DISABLED = 347;
    int IMAGE_REPORT = 348; /* TODO Remove */
    int IMAGE_RESTART = 349; /* TODO Remove */
    int IMAGE_RETURN = 350;
    int IMAGE_RETURN_HOVER = 351;
    int IMAGE_RETURN_TO_QUESTION = 352;
    int IMAGE_RETURN_TO_QUESTION_HOVER = 353;
    int IMAGE_REVIEW = 354;
    int IMAGE_REVIEW_HOVER = 355;
    int IMAGE_REVIEW_OVAL = 356;
    int IMAGE_REVIEW_OVAL_HOVER = 357;
    int IMAGE_REVIEW_OVAL_DISABLED = 358;
    int IMAGE_SETTINGS = 359; /* TODO Remove */
    int IMAGE_SHOW_TIME = 360;
    int IMAGE_SHOW_TIME_HOVER = 361;
    int IMAGE_SHOW_TIME_DISABLED = 362;
    int IMAGE_SPLASH = 363;
    int IMAGE_STAR = 364;
    int IMAGE_STAR_EMPTY = 365;
    int IMAGE_STAR_HALF = 366; /* TODO Remove */
    int IMAGE_STOP_RECORDING = 367;
    int IMAGE_STOP_RECORDING_HOVER = 368;
    int IMAGE_STORE = 369; /* TODO Remove */
    int IMAGE_TESTS = 370; /* TODO Remove */
    int IMAGE_VIEW_QUESTION = 371;
    int IMAGE_VIEW_QUESTION_HOVER = 372;
    int IMAGE_VIEW_TEXT = 373;
    int IMAGE_VIEW_TEXT_HOVER = 374;
    int IMAGE_VOLUME_OVAL = 375;
    int IMAGE_VOLUME_OVAL_HOVER = 376;
    int IMAGE_UNBOXED = 377;
    int IMAGE_UNCHECKED = 378;

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
