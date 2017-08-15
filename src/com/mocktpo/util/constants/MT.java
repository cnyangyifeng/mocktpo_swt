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
    int IMAGE_CONTINUE_HOVER = 312;
    int IMAGE_CONTINUE_OVAL = 313;
    int IMAGE_CONTINUE_OVAL_DISABLED = 314;
    int IMAGE_CONTINUE_OVAL_HOVER = 315;
    int IMAGE_ETS_TOEFL = 316;
    int IMAGE_GO_TO_QUESTION = 317;
    int IMAGE_GO_TO_QUESTION_HOVER = 318;
    int IMAGE_HEADSET = 319;
    int IMAGE_HELP_OVAL = 320;
    int IMAGE_HELP_OVAL_DISABLED = 321;
    int IMAGE_HELP_OVAL_HOVER = 322;
    int IMAGE_HIDE_TIME = 323;
    int IMAGE_HIDE_TIME_DISABLED = 324;
    int IMAGE_HIDE_TIME_HOVER = 325;
    int IMAGE_NEXT_OVAL = 326;
    int IMAGE_NEXT_OVAL_DISABLED = 327;
    int IMAGE_NEXT_OVAL_HOVER = 328;
    int IMAGE_OK_OVAL = 329;
    int IMAGE_OK_OVAL_DISABLED = 330;
    int IMAGE_OK_OVAL_HOVER = 331;
    int IMAGE_PAUSE_TEST = 332;
    int IMAGE_PAUSE_TEST_HOVER = 333;
    int IMAGE_PLAYBACK_RESPONSE = 334;
    int IMAGE_PLAYBACK_RESPONSE_DISABLED = 335;
    int IMAGE_PLAYBACK_RESPONSE_HOVER = 336;
    int IMAGE_READY_TO_ANSWER = 337;
    int IMAGE_READY_TO_ANSWER_2 = 338;
    int IMAGE_RECORD_AGAIN = 339;
    int IMAGE_RECORD_AGAIN_DISABLED = 340;
    int IMAGE_RECORD_AGAIN_HOVER = 341;
    int IMAGE_RETURN = 342;
    int IMAGE_RETURN_HOVER = 343;
    int IMAGE_RETURN_TO_QUESTION = 344;
    int IMAGE_RETURN_TO_QUESTION_HOVER = 345;
    int IMAGE_REVIEW = 346;
    int IMAGE_REVIEW_HOVER = 347;
    int IMAGE_REVIEW_OVAL = 348;
    int IMAGE_REVIEW_OVAL_DISABLED = 349;
    int IMAGE_REVIEW_OVAL_HOVER = 350;
    int IMAGE_SHOW_TIME = 351;
    int IMAGE_SHOW_TIME_DISABLED = 352;
    int IMAGE_SHOW_TIME_HOVER = 353;
    int IMAGE_SKIP = 354;
    int IMAGE_SKIP_HOVER = 355;
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

    int IMAGE_APP_ICON = 401;
    int IMAGE_LOGO = 402;
    int IMAGE_SPLASH = 403;
    int IMAGE_SYSTEM_BACK = 404;
    int IMAGE_SYSTEM_BACK_DISABLED = 405;
    int IMAGE_SYSTEM_BACK_HOVER = 406;
    int IMAGE_SYSTEM_BOXED = 407;
    int IMAGE_SYSTEM_CONTINUE = 408;
    int IMAGE_SYSTEM_CONTINUE_HOVER = 409;
    int IMAGE_SYSTEM_COPY = 410;
    int IMAGE_SYSTEM_COPY_HOVER = 411;
    int IMAGE_SYSTEM_CUT = 412;
    int IMAGE_SYSTEM_CUT_HOVER = 413;
    int IMAGE_SYSTEM_EDIT = 414;
    int IMAGE_SYSTEM_EDIT_HOVER = 415;
    int IMAGE_SYSTEM_EXPORT_AS_PDF = 416;
    int IMAGE_SYSTEM_EXPORT_AS_PDF_HOVER = 417;
    int IMAGE_SYSTEM_EXPORT_AS_ZIP = 418;
    int IMAGE_SYSTEM_EXPORT_AS_ZIP_DISABLED = 419;
    int IMAGE_SYSTEM_EXPORT_AS_ZIP_HOVER = 420;
    int IMAGE_SYSTEM_IMPORT = 421;
    int IMAGE_SYSTEM_IMPORT_HOVER = 422;
    int IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION = 423;
    int IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION_HOVER = 424;
    int IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION = 425;
    int IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION_HOVER = 426;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S = 427;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S_HOVER = 428;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W = 429;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W_HOVER = 430;
    int IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION = 431;
    int IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION_HOVER = 432;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS = 433;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS_HOVER = 434;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS = 435;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS_HOVER = 436;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW = 437;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW_HOVER = 438;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S = 439;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S_HOVER = 440;
    int IMAGE_SYSTEM_NEW_LISTENING_MATERIAL = 441;
    int IMAGE_SYSTEM_NEW_LISTENING_MATERIAL_HOVER = 442;
    int IMAGE_SYSTEM_NEW_LISTENING_QUESTION = 443;
    int IMAGE_SYSTEM_NEW_LISTENING_QUESTION_HOVER = 444;
    int IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION = 445;
    int IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION_HOVER = 446;
    int IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION = 447;
    int IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION_HOVER = 448;
    int IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION = 449;
    int IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION_HOVER = 450;
    int IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION = 451;
    int IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION_HOVER = 452;
    int IMAGE_SYSTEM_NEW_READING_PASSAGE = 453;
    int IMAGE_SYSTEM_NEW_READING_PASSAGE_HOVER = 454;
    int IMAGE_SYSTEM_NEW_READING_QUESTION = 455;
    int IMAGE_SYSTEM_NEW_READING_QUESTION_HOVER = 456;
    int IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION = 457;
    int IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION_HOVER = 458;
    int IMAGE_SYSTEM_NEW_TEST = 459;
    int IMAGE_SYSTEM_NEW_TEST_HOVER = 460;
    int IMAGE_SYSTEM_NEW_TEST_PAPER = 461;
    int IMAGE_SYSTEM_NEW_TEST_PAPER_HOVER = 462;
    int IMAGE_SYSTEM_PASTE = 463;
    int IMAGE_SYSTEM_PASTE_HOVER = 464;
    int IMAGE_SYSTEM_REPORT = 465;
    int IMAGE_SYSTEM_REPORT_HOVER = 466;
    int IMAGE_SYSTEM_SAVE = 467;
    int IMAGE_SYSTEM_SAVE_DISABLED = 468;
    int IMAGE_SYSTEM_SAVE_HOVER = 469;
    int IMAGE_SYSTEM_STAR = 470;
    int IMAGE_SYSTEM_STAR_OUTLINE = 471;
    int IMAGE_SYSTEM_STEP_GENERAL = 472;
    int IMAGE_SYSTEM_STEP_GENERAL_CHECKED = 473;
    int IMAGE_SYSTEM_STEP_GENERAL_DISABLED = 474;
    int IMAGE_SYSTEM_STEP_GENERAL_HOVER = 475;
    int IMAGE_SYSTEM_STEP_LISTENING = 476;
    int IMAGE_SYSTEM_STEP_LISTENING_CHECKED = 477;
    int IMAGE_SYSTEM_STEP_LISTENING_DISABLED = 478;
    int IMAGE_SYSTEM_STEP_LISTENING_HOVER = 479;
    int IMAGE_SYSTEM_STEP_PREVIEW = 480;
    int IMAGE_SYSTEM_STEP_PREVIEW_CHECKED = 481;
    int IMAGE_SYSTEM_STEP_PREVIEW_DISABLED = 482;
    int IMAGE_SYSTEM_STEP_PREVIEW_HOVER = 483;
    int IMAGE_SYSTEM_STEP_READING = 484;
    int IMAGE_SYSTEM_STEP_READING_CHECKED = 485;
    int IMAGE_SYSTEM_STEP_READING_DISABLED = 486;
    int IMAGE_SYSTEM_STEP_READING_HOVER = 487;
    int IMAGE_SYSTEM_STEP_SPEAKING = 488;
    int IMAGE_SYSTEM_STEP_SPEAKING_CHECKED = 489;
    int IMAGE_SYSTEM_STEP_SPEAKING_DISABLED = 490;
    int IMAGE_SYSTEM_STEP_SPEAKING_HOVER = 491;
    int IMAGE_SYSTEM_STEP_WRITING = 492;
    int IMAGE_SYSTEM_STEP_WRITING_CHECKED = 493;
    int IMAGE_SYSTEM_STEP_WRITING_DISABLED = 494;
    int IMAGE_SYSTEM_STEP_WRITING_HOVER = 495;
    int IMAGE_SYSTEM_UNBOXED = 496;

    int ANIMATED_GIF_LOADING = 601;

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

    String STRING_CLOSED_BRACKET = ")";
    String STRING_COLON = ":";
    String STRING_COMMA = ",";
    String STRING_OF = "of";
    String STRING_OPEN_BRACKET = "(";
    String STRING_PERCENTAGE = "%";
    String STRING_QUESTION = "Question";
    String STRING_SEMICOLON = ";";
    String STRING_SLASH = "/";
    String STRING_SPACE = " ";
    String STRING_SQUARE = "\u2588";
    String STRING_STAR = "*";
    String STRING_TAB = "\t";

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
}
