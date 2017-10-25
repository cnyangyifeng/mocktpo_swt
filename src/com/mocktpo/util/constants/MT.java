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
    // int COLOR_DARK_ORANGE = 4;
    int COLOR_DARK_BLUE = 5;
    int COLOR_GRAY20 = 6;
    int COLOR_GRAY40 = 7;
    int COLOR_GRAY60 = 8;
    // int COLOR_GREEN = 9;
    int COLOR_INDIGO = 10;
    // int COLOR_ORANGE = 11;
    // int COLOR_ORANGE_RED = 12;
    // int COLOR_OXFORD_BLUE = 13;
    int COLOR_PINK = 12;
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
    int IMAGE_SYSTEM_ACTIVATE = 404;
    int IMAGE_SYSTEM_ACTIVATE_HOVER = 405;
    int IMAGE_SYSTEM_ADD_AN_INSERTION_POINT = 406;
    int IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_DISABLED = 407;
    int IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_HOVER = 408;
    int IMAGE_SYSTEM_BACK = 409;
    int IMAGE_SYSTEM_BACK_DISABLED = 410;
    int IMAGE_SYSTEM_BACK_HOVER = 411;
    int IMAGE_SYSTEM_BOXED = 412;
    int IMAGE_SYSTEM_BOXED_DISABLED = 413;
    int IMAGE_SYSTEM_BRING_FORWARD = 414;
    int IMAGE_SYSTEM_BRING_FORWARD_HOVER = 415;
    int IMAGE_SYSTEM_CANCEL = 416;
    int IMAGE_SYSTEM_CANCEL_HOVER = 417;
    int IMAGE_SYSTEM_CANCEL_WIDE = 418;
    int IMAGE_SYSTEM_CANCEL_WIDE_HOVER = 419;
    int IMAGE_SYSTEM_CONTINUE = 420;
    int IMAGE_SYSTEM_CONTINUE_HOVER = 421;
    int IMAGE_SYSTEM_COPY = 422;
    int IMAGE_SYSTEM_COPY_HOVER = 423;
    int IMAGE_SYSTEM_CUT = 424;
    int IMAGE_SYSTEM_CUT_HOVER = 425;
    int IMAGE_SYSTEM_DELETE = 426;
    int IMAGE_SYSTEM_DELETE_HOVER = 427;
    int IMAGE_SYSTEM_EDIT = 428;
    int IMAGE_SYSTEM_EDIT_HOVER = 429;
    int IMAGE_SYSTEM_EXPORT_AS_PDF = 430;
    int IMAGE_SYSTEM_EXPORT_AS_PDF_HOVER = 431;
    int IMAGE_SYSTEM_EXPORT_AS_ZIP = 432;
    int IMAGE_SYSTEM_EXPORT_AS_ZIP_DISABLED = 433;
    int IMAGE_SYSTEM_EXPORT_AS_ZIP_HOVER = 434;
    int IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT = 435;
    int IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_DISABLED = 436;
    int IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_HOVER = 437;
    int IMAGE_SYSTEM_IMPORT = 438;
    int IMAGE_SYSTEM_IMPORT_HOVER = 439;
    int IMAGE_SYSTEM_MARK_PARAGRAPHS = 440;
    int IMAGE_SYSTEM_MARK_PARAGRAPHS_DISABLED = 441;
    int IMAGE_SYSTEM_MARK_PARAGRAPHS_HOVER = 442;
    int IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION = 443;
    int IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION_HOVER = 444;
    int IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION = 445;
    int IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION_HOVER = 446;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S = 447;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S_HOVER = 448;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W = 449;
    int IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W_HOVER = 450;
    int IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION = 451;
    int IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION_HOVER = 452;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS = 453;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS_HOVER = 454;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS = 455;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS_HOVER = 456;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW = 457;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW_HOVER = 458;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S = 459;
    int IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S_HOVER = 460;
    int IMAGE_SYSTEM_NEW_LISTENING_MATERIAL = 461;
    int IMAGE_SYSTEM_NEW_LISTENING_MATERIAL_HOVER = 462;
    int IMAGE_SYSTEM_NEW_LISTENING_QUESTION = 463;
    int IMAGE_SYSTEM_NEW_LISTENING_QUESTION_HOVER = 464;
    int IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION = 465;
    int IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION_HOVER = 466;
    int IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION = 467;
    int IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION_HOVER = 468;
    int IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION = 469;
    int IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION_HOVER = 470;
    int IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION = 471;
    int IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION_HOVER = 472;
    int IMAGE_SYSTEM_NEW_READING_PASSAGE = 473;
    int IMAGE_SYSTEM_NEW_READING_PASSAGE_HOVER = 474;
    int IMAGE_SYSTEM_NEW_READING_QUESTION = 475;
    int IMAGE_SYSTEM_NEW_READING_QUESTION_HOVER = 476;
    int IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION = 477;
    int IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION_HOVER = 478;
    int IMAGE_SYSTEM_NEW_TEST = 479;
    int IMAGE_SYSTEM_NEW_TEST_HOVER = 480;
    int IMAGE_SYSTEM_NEW_TEST_PAPER = 481;
    int IMAGE_SYSTEM_NEW_TEST_PAPER_HOVER = 482;
    int IMAGE_SYSTEM_PASTE = 483;
    int IMAGE_SYSTEM_PASTE_HOVER = 484;
    int IMAGE_SYSTEM_REPORT = 485;
    int IMAGE_SYSTEM_REPORT_HOVER = 486;
    int IMAGE_SYSTEM_SAVE = 487;
    int IMAGE_SYSTEM_SAVE_DISABLED = 488;
    int IMAGE_SYSTEM_SAVE_HOVER = 489;
    int IMAGE_SYSTEM_SEND_BACKWARD = 490;
    int IMAGE_SYSTEM_SEND_BACKWARD_HOVER = 491;
    int IMAGE_SYSTEM_STAR = 492;
    int IMAGE_SYSTEM_STAR_OUTLINE = 493;
    int IMAGE_SYSTEM_STEP_GENERAL = 494;
    int IMAGE_SYSTEM_STEP_GENERAL_CHECKED = 495;
    int IMAGE_SYSTEM_STEP_GENERAL_DISABLED = 496;
    int IMAGE_SYSTEM_STEP_GENERAL_HOVER = 497;
    int IMAGE_SYSTEM_STEP_LISTENING = 498;
    int IMAGE_SYSTEM_STEP_LISTENING_CHECKED = 499;
    int IMAGE_SYSTEM_STEP_LISTENING_DISABLED = 500;
    int IMAGE_SYSTEM_STEP_LISTENING_HOVER = 501;
    int IMAGE_SYSTEM_STEP_PREVIEW = 502;
    int IMAGE_SYSTEM_STEP_PREVIEW_CHECKED = 503;
    int IMAGE_SYSTEM_STEP_PREVIEW_DISABLED = 504;
    int IMAGE_SYSTEM_STEP_PREVIEW_HOVER = 505;
    int IMAGE_SYSTEM_STEP_READING = 506;
    int IMAGE_SYSTEM_STEP_READING_CHECKED = 507;
    int IMAGE_SYSTEM_STEP_READING_DISABLED = 508;
    int IMAGE_SYSTEM_STEP_READING_HOVER = 509;
    int IMAGE_SYSTEM_STEP_SPEAKING = 510;
    int IMAGE_SYSTEM_STEP_SPEAKING_CHECKED = 511;
    int IMAGE_SYSTEM_STEP_SPEAKING_DISABLED = 512;
    int IMAGE_SYSTEM_STEP_SPEAKING_HOVER = 513;
    int IMAGE_SYSTEM_STEP_WRITING = 514;
    int IMAGE_SYSTEM_STEP_WRITING_CHECKED = 515;
    int IMAGE_SYSTEM_STEP_WRITING_DISABLED = 516;
    int IMAGE_SYSTEM_STEP_WRITING_HOVER = 517;
    int IMAGE_SYSTEM_TRASH = 518;
    int IMAGE_SYSTEM_TRASH_HOVER = 519;
    int IMAGE_SYSTEM_UNBOXED = 520;
    int IMAGE_SYSTEM_UNBOXED_DISABLED = 521;

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

    String STRING_AUDIO_INDICATOR = "[Audio]";
    String STRING_ARROW = "➤"; // \ \u27a4
    String STRING_CLOSED_BRACKET = ")";
    String STRING_COLON = ":";
    String STRING_COMMA = ",";
    String STRING_FULL_BLOCK = "█"; // \u2588
    String STRING_LINEFEED = "\n"; // LF, \\u00a
    String STRING_OF = "of";
    String STRING_OPEN_BRACKET = "(";
    String STRING_PERCENTAGE = "%";
    String STRING_QUESTION = "Question";
    String STRING_SEMICOLON = ";";
    String STRING_SLASH = "/";
    String STRING_SPACE = " ";
    String STRING_SQUARE = "\u2588";
    String STRING_STAR = "*";
    String STRING_TAB = "\t"; // HT, Horizontal Tab, \\u009

    String STRING_TEST_INTRO_VIEW_DESCRIPTION = "EDUCATIONAL TESTING SERVICE, ETS, the ETS logo, TOEFL and TOEFL iBT are registered trademarks of Educational Testing Service (ETS) in the United States and other countries.\n\nClick on Continue to go on.";
    String STRING_GENERAL_TEST_INFO_VIEW_HEADING = "General Test Information";
    String STRING_GENERAL_TEST_INFO_VIEW_DESCRIPTION = "This test measures your ability to use English in an academic context. There are 4 sections.\n\nIn the Reading section, you will read several passages and answer questions about them.\n\nIn the Listening section, you will hear several conversations and lectures and answer questions about them.\n\nIn the Speaking section, you will answer 6 questions. Some of the questions ask you to speak about your own experience. Other questions ask you to speak about lectures and reading passages.\n\nIn the Writing section, you will answer 2 questions. The first question asks you to write about the relationship between a lecture you will hear and a passage you will read. The second question asks you to write an essay about a topic of general interest based on your experience.\n\nDuring this practice test, you may click Pause icon at any time. This will stop the test until you decide to continue. You may continue the test in a few minutes, or at any time during the period that your test is activated.\n\nThere will be directions for each section which explain how to answer the questions in that section.\n\nYou should work quickly but carefully on the Reading and Listening questions. Some questions are more difficult than others, but try to answer every one to the best of your ability. If you are not sure of the answer to a question, make the best guess that you can. The questions that you answer by speaking and by writing are each separately timed. Try to answer every one of these questions as completely as possible in the time allowed.\n\nClick on Continue to go on.";
    String STRING_BREAK_POINT_VIEW_DESCRIPTION = "You may now take a break. In an actual test there is a ten-minute break at this point.\n\nClick on Continue when you are ready to go on to the next section.\n\nIf you do not wish to take a break, click on Continue now.";
    String STRING_TEST_END_VIEW_DESCRIPTION = "The testing session is complete.\n\nClick on Continue to see your Score Report.";
    String STRING_READING_SECTION_DIRECTIONS_VIEW_HEADING = "Reading Section Directions";
    String STRING_READING_SECTION_DIRECTIONS_VIEW_DESCRIPTION = "In this part of the Reading section, you will read 3 passages. In the test you will have 60 minutes to read the passage and answer the questions.\n\nMost questions are worth 1 point but the last question in each set is worth more than 1 point. The directions indicate how many points you may receive.\n\nSome passages include a word or phrase that is underlined in blue. Click on the word or phrase to see a definition or an explanation.\n\nWhen you want to move to the next question. click on Next. You may skip questions and go back to them later if you want to return to previous questions. click on Back.\n\nYou can click on Review at any time and the review screen will show you which questions you have answered and which you have not answered. From this review screen, you may go directly to any question you have already seen in the Reading section.\n\nClick on Continue to go on.";
    String STRING_READING_INSERT_TEXT_QUESTION_VIEW_QUESTION = "Look at the four squares [ █ ] that indicate where the following sentence could be added to the passage.";
    String STRING_READING_INSERT_TEXT_QUESTION_VIEW_FOOTNOTE = "Where would the sentence best fit? Click on a square to add the sentence to the passage.";
    String STRING_READING_PROSE_SUMMARY_QUESTION_VIEW_DIRECTIONS = "Directions: An introductory sentence for a brief summary of the passage is provided below. Complete the summary by selecting the THREE answer choices that express the most important ideas in the passage. Some sentences do not belong in the summary because they express ideas that are not presented in the passage or are minor ideas in the passage. This question is worth 2 points.";
    String STRING_READING_PROSE_SUMMARY_QUESTION_VIEW_TIPS = "Drag your answer choices to the spaces where they belong. To remove an answer choice, click on it.\nTo review the passage, click VIEW TEXT.";
    String STRING_READING_SECTION_END_VIEW_DESCRIPTION = "You have seen all of the questions in the Reading section. You may go back and review. As long as there is time remaining. You can check your work.\n\nClick on Return to go back to the last question.\n\nClick on Review to go to the Review screen.\n\nClick on Continue to go on to the next section of the test.\n\nOnce you leave the Reading section, you WILL NOT be able to return to it.";
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
