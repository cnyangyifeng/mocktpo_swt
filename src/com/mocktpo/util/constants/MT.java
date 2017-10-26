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

    String COLOR_BEIGE = "color_beige";
    String COLOR_BLACK = "color_black";
    String COLOR_BURGUNDY = "color_burgundy";
    String COLOR_DARK_BLUE = "color_dark_blue";
    String COLOR_GRAY20 = "color_gray20";
    String COLOR_GRAY40 = "color_gray40";
    String COLOR_GRAY60 = "color_gray60";
    String COLOR_INDIGO = "color_indigo";
    String COLOR_PINK = "color_pink";
    String COLOR_ROSY_BROWN = "color_rosy_brown";
    String COLOR_TOUPE = "color_toupe";
    String COLOR_WHITE = "color_white";
    String COLOR_WHITE_SMOKE = "color_white_smoke";

    /* Others */

    String COLOR_HIGHLIGHTED = "color_highlighted";
    String COLOR_WINDOW_BACKGROUND = "color_window_background";

    /*
     * ==================================================
     *
     * Cursors
     *
     * ==================================================
     */

    String CURSOR_ARROW = "cursor_arrow";
    String CURSOR_HAND = "cursor_hand";

    /*
     * ==================================================
     *
     * Fonts
     *
     * ==================================================
     */

    String FONT_XX_SMALL = "font_xx_small";
    String FONT_X_SMALL = "font_x_small";
    String FONT_SMALL = "font_small";
    String FONT_SMALL_BOLD = "font_small_bold";
    String FONT_SMALL_ITALIC = "font_small_italic";
    String FONT_MEDIUM = "font_medium";
    String FONT_MEDIUM_BOLD = "font_medium_bold";
    String FONT_MEDIUM_ITALIC = "font_medium_italic";
    String FONT_LARGE = "font_large";
    String FONT_LARGE_BOLD = "font_large_bold";
    String FONT_X_LARGE = "font_x_large";
    String FONT_X_LARGE_BOLD = "font_x_large_bold";
    String FONT_XX_LARGE = "font_xx_large";

    String FONT_SERIF_HEADING = "font_serif_heading";
    String FONT_SERIF_ITALIC_TEXT = "font_serif_italic_text";

    /*
     * ==================================================
     *
     * Images
     *
     * ==================================================
     */

    String IMAGE_BACK_OVAL = "image_back_oval";
    String IMAGE_BACK_OVAL_DISABLED = "image_back_oval_disabled";
    String IMAGE_BACK_OVAL_HOVER = "image_back_oval_hover";
    String IMAGE_BOXED = "image_boxed";
    String IMAGE_BULLET = "image_bullet";
    String IMAGE_CHECKED = "image_checked";
    String IMAGE_CONFIRM_RESPONSE = "image_confirm_response";
    String IMAGE_CONFIRM_RESPONSE_DISABLED = "image_confirm_response_disabled";
    String IMAGE_CONFIRM_RESPONSE_HOVER = "image_confirm_response_hover";
    String IMAGE_CONTINUE = "image_continue";
    String IMAGE_CONTINUE_DISABLED = "image_continue_disabled";
    String IMAGE_CONTINUE_HOVER = "image_continue_hover";
    String IMAGE_CONTINUE_OVAL = "image_continue_oval";
    String IMAGE_CONTINUE_OVAL_DISABLED = "image_continue_oval_disabled";
    String IMAGE_CONTINUE_OVAL_HOVER = "image_continue_oval_hover";
    String IMAGE_ETS_TOEFL = "image_ets_toefl";
    String IMAGE_GO_TO_QUESTION = "image_go_to_question";
    String IMAGE_GO_TO_QUESTION_HOVER = "image_go_to_question_hover";
    String IMAGE_HEADSET = "image_headset";
    String IMAGE_HELP_OVAL = "image_help_oval";
    String IMAGE_HELP_OVAL_DISABLED = "image_help_oval_disabled";
    String IMAGE_HELP_OVAL_HOVER = "image_help_oval_hover";
    String IMAGE_HIDE_TIME = "image_hide_time";
    String IMAGE_HIDE_TIME_DISABLED = "image_hide_time_disabled";
    String IMAGE_HIDE_TIME_HOVER = "image_hide_time_hover";
    String IMAGE_NEXT_OVAL = "image_next_oval";
    String IMAGE_NEXT_OVAL_DISABLED = "image_next_oval_disabled";
    String IMAGE_NEXT_OVAL_HOVER = "image_next_oval_hover";
    String IMAGE_OK_OVAL = "image_ok_oval";
    String IMAGE_OK_OVAL_DISABLED = "image_ok_oval_disabled";
    String IMAGE_OK_OVAL_HOVER = "image_ok_oval_hover";
    String IMAGE_PAUSE_TEST = "image_pause_text";
    String IMAGE_PAUSE_TEST_HOVER = "image_pause_test_hover";
    String IMAGE_PLAYBACK_RESPONSE = "image_playback_response";
    String IMAGE_PLAYBACK_RESPONSE_DISABLED = "image_playback_response_disabled";
    String IMAGE_PLAYBACK_RESPONSE_HOVER = "image_playback_response_hover";
    String IMAGE_READY_TO_ANSWER = "image_ready_to_answer";
    String IMAGE_READY_TO_ANSWER_2 = "image_ready_to_answer_2";
    String IMAGE_RECORD_AGAIN = "image_record_again";
    String IMAGE_RECORD_AGAIN_DISABLED = "image_record_again_disabled";
    String IMAGE_RECORD_AGAIN_HOVER = "image_record_again_hover";
    String IMAGE_RETURN = "image_return";
    String IMAGE_RETURN_HOVER = "image_return_hover";
    String IMAGE_RETURN_TO_QUESTION = "image_return_to_question";
    String IMAGE_RETURN_TO_QUESTION_HOVER = "image_return_to_question_hover";
    String IMAGE_REVIEW = "image_review";
    String IMAGE_REVIEW_HOVER = "image_review_hover";
    String IMAGE_REVIEW_OVAL = "image_review_oval";
    String IMAGE_REVIEW_OVAL_DISABLED = "image_review_oval_disabled";
    String IMAGE_REVIEW_OVAL_HOVER = "image_review_oval_hover";
    String IMAGE_SHOW_TIME = "image_show_time";
    String IMAGE_SHOW_TIME_DISABLED = "image_show_time_disabled";
    String IMAGE_SHOW_TIME_HOVER = "image_show_time_hover";
    String IMAGE_SKIP = "image_skip";
    String IMAGE_SKIP_HOVER = "image_skip_hover";
    String IMAGE_STOP_RECORDING = "image_stop_recording";
    String IMAGE_STOP_RECORDING_HOVER = "image_stop_recording_hover";
    String IMAGE_UNBOXED = "image_unboxed";
    String IMAGE_UNCHECKED = "image_unchecked";
    String IMAGE_VIEW_QUESTION = "image_view_question";
    String IMAGE_VIEW_QUESTION_HOVER = "image_view_question_hover";
    String IMAGE_VIEW_TEXT = "image_view_text";
    String IMAGE_VIEW_TEXT_HOVER = "image_view_text_hover";
    String IMAGE_VOLUME_OVAL = "image_volume_oval";
    String IMAGE_VOLUME_OVAL_HOVER = "image_volume_oval_hover";

    String IMAGE_APP_ICON = "image_app_icon";
    String IMAGE_LOGO = "image_logo";
    String IMAGE_SPLASH = "image_splash";
    String IMAGE_SYSTEM_ACTIVATE = "image_system_activate";
    String IMAGE_SYSTEM_ACTIVATE_DISABLED = "image_system_activate_disabled";
    String IMAGE_SYSTEM_ACTIVATE_HOVER = "image_system_activate_hover";
    String IMAGE_SYSTEM_ADD_AN_INSERTION_POINT = "image_system_add_an_insertion_point";
    String IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_DISABLED = "image_system_add_an_insertion_point_disabled";
    String IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_HOVER = "image_system_add_an_insertion_point_hover";
    String IMAGE_SYSTEM_BACK = "image_system_back";
    String IMAGE_SYSTEM_BACK_DISABLED = "image_system_back_disabled";
    String IMAGE_SYSTEM_BACK_HOVER = "image_system_back_hover";
    String IMAGE_SYSTEM_BOXED = "image_system_boxed";
    String IMAGE_SYSTEM_BOXED_DISABLED = "image_system_boxed_disabled";
    String IMAGE_SYSTEM_BRING_FORWARD = "image_system_bring_forward";
    String IMAGE_SYSTEM_BRING_FORWARD_HOVER = "image_system_bring_forward_hover";
    String IMAGE_SYSTEM_CANCEL = "image_system_cancel";
    String IMAGE_SYSTEM_CANCEL_DISABLED = "image_system_cancel_disabled";
    String IMAGE_SYSTEM_CANCEL_HOVER = "image_system_cancel_hover";
    String IMAGE_SYSTEM_CANCEL_WIDE = "image_system_cancel_wide";
    String IMAGE_SYSTEM_CANCEL_WIDE_HOVER = "image_system_cancel_wide_hover";
    String IMAGE_SYSTEM_CONTINUE = "image_system_continue";
    String IMAGE_SYSTEM_CONTINUE_HOVER = "image_system_continue_hover";
    String IMAGE_SYSTEM_COPY = "image_system_copy";
    String IMAGE_SYSTEM_COPY_HOVER = "image_system_copy_hover";
    String IMAGE_SYSTEM_CUT = "image_system_cut";
    String IMAGE_SYSTEM_CUT_HOVER = "image_system_cut_hover";
    String IMAGE_SYSTEM_DELETE = "image_system_delete";
    String IMAGE_SYSTEM_DELETE_HOVER = "image_system_delete_hover";
    String IMAGE_SYSTEM_DOWNLOAD = "image_system_download";
    String IMAGE_SYSTEM_DOWNLOAD_HOVER = "image_system_download_hover";
    String IMAGE_SYSTEM_EDIT = "image_system_edit";
    String IMAGE_SYSTEM_EDIT_HOVER = "image_system_edit_hover";
    String IMAGE_SYSTEM_EXPORT = "image_system_export";
    String IMAGE_SYSTEM_EXPORT_HOVER = "image_system_export_hover";
    String IMAGE_SYSTEM_EXPORT_AS_PDF = "image_system_export_as_pdf";
    String IMAGE_SYSTEM_EXPORT_AS_PDF_HOVER = "image_system_export_as_pdf_hover";
    String IMAGE_SYSTEM_EXPORT_AS_ZIP = "image_system_export_as_zip";
    String IMAGE_SYSTEM_EXPORT_AS_ZIP_DISABLED = "image_system_export_as_zip_disabled";
    String IMAGE_SYSTEM_EXPORT_AS_ZIP_HOVER = "image_system_export_as_zip_hover";
    String IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT = "image_system_highlight_selected_text";
    String IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_DISABLED = "image_system_highlight_selected_text_disabled";
    String IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_HOVER = "image_system_highlight_selected_text_hover";
    String IMAGE_SYSTEM_IMPORT = "image_system_import";
    String IMAGE_SYSTEM_IMPORT_HOVER = "image_system_import_hover";
    String IMAGE_SYSTEM_INSTALLED = "image_system_installed";
    String IMAGE_SYSTEM_MARK_PARAGRAPHS = "image_system_mark_paragraphs";
    String IMAGE_SYSTEM_MARK_PARAGRAPHS_DISABLED = "image_system_mark_paragraphs_disabled";
    String IMAGE_SYSTEM_MARK_PARAGRAPHS_HOVER = "image_system_mark_paragraphs_hover";
    String IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION = "image_system_new_categorize_objects_question";
    String IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION_HOVER = "image_system_new_categorize_objects_question_hover";
    String IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION = "image_system_new_fill_in_a_table_question";
    String IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION_HOVER = "image_system_new_fill_in_a_table_question_hover";
    String IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S = "image_system_new_independent_task_s";
    String IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S_HOVER = "image_system_new_independent_task_s_hover";
    String IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W = "image_system_new_independent_task_w";
    String IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W_HOVER = "image_system_new_independent_task_w_hover";
    String IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION = "image_system_new_insert_text_question";
    String IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION_HOVER = "image_system_new_insert_text_question_hover";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS = "image_system_new_integrated_task_ls";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS_HOVER = "image_system_new_integrated_task_ls_hover";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS = "image_system_new_integrated_task_rls";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS_HOVER = "image_system_new_integrated_task_rls_hover";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW = "image_system_new_integrated_task_rlw";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW_HOVER = "image_system_new_integrated_task_rlw_hover";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S = "image_system_new_integrated_task_s";
    String IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S_HOVER = "image_system_new_integrated_task_s_hover";
    String IMAGE_SYSTEM_NEW_LISTENING_MATERIAL = "image_system_new_listening_material";
    String IMAGE_SYSTEM_NEW_LISTENING_MATERIAL_HOVER = "image_system_new_listening_material_hover";
    String IMAGE_SYSTEM_NEW_LISTENING_QUESTION = "image_system_new_listening_question";
    String IMAGE_SYSTEM_NEW_LISTENING_QUESTION_HOVER = "image_system_new_listening_question_hover";
    String IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION = "image_system_new_listening_replay_question";
    String IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION_HOVER = "image_system_new_listening_replay_question_hover";
    String IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION = "image_system_new_multiple_choice_question";
    String IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION_HOVER = "image_system_new_multiple_choice_question_hover";
    String IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION = "image_system_new_multiple_response_question";
    String IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION_HOVER = "image_system_new_multiple_response_question_hover";
    String IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION = "image_system_new_prose_summary_question";
    String IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION_HOVER = "image_system_new_prose_summary_question_hover";
    String IMAGE_SYSTEM_NEW_READING_PASSAGE = "image_system_new_reading_passage";
    String IMAGE_SYSTEM_NEW_READING_PASSAGE_HOVER = "image_system_new_reading_passage_hover";
    String IMAGE_SYSTEM_NEW_READING_QUESTION = "image_system_new_reading_question";
    String IMAGE_SYSTEM_NEW_READING_QUESTION_HOVER = "image_system_new_reading_question_hover";
    String IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION = "image_system_new_sort_events_question";
    String IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION_HOVER = "image_system_new_sort_events_question_hover";
    String IMAGE_SYSTEM_NEW_TEST = "image_system_new_test";
    String IMAGE_SYSTEM_NEW_TEST_HOVER = "image_system_new_test_hover";
    String IMAGE_SYSTEM_NEW_TEST_PAPER = "image_system_new_test_paper";
    String IMAGE_SYSTEM_NEW_TEST_PAPER_HOVER = "image_system_new_test_paper_hover";
    String IMAGE_SYSTEM_PASTE = "image_system_paste";
    String IMAGE_SYSTEM_PASTE_HOVER = "image_system_paste_hover";
    String IMAGE_SYSTEM_PLEASE_WAIT = "image_system_please_wait";
    String IMAGE_SYSTEM_REPORT = "image_system_report";
    String IMAGE_SYSTEM_REPORT_HOVER = "image_system_report_hover";
    String IMAGE_SYSTEM_SAVE = "image_system_save";
    String IMAGE_SYSTEM_SAVE_DISABLED = "image_system_save_disabled";
    String IMAGE_SYSTEM_SAVE_HOVER = "image_system_save_hover";
    String IMAGE_SYSTEM_SEND_BACKWARD = "image_system_send_backward";
    String IMAGE_SYSTEM_SEND_BACKWARD_HOVER = "image_system_send_backward_hover";
    String IMAGE_SYSTEM_STAR = "image_system_star";
    String IMAGE_SYSTEM_STAR_OUTLINE = "image_system_star_outline";
    String IMAGE_SYSTEM_STEP_GENERAL = "image_system_step_general";
    String IMAGE_SYSTEM_STEP_GENERAL_CHECKED = "image_system_step_general_checked";
    String IMAGE_SYSTEM_STEP_GENERAL_DISABLED = "image_system_step_general_disabled";
    String IMAGE_SYSTEM_STEP_GENERAL_HOVER = "image_system_step_general_hover";
    String IMAGE_SYSTEM_STEP_LISTENING = "image_system_step_listening";
    String IMAGE_SYSTEM_STEP_LISTENING_CHECKED = "image_system_step_listening_checked";
    String IMAGE_SYSTEM_STEP_LISTENING_DISABLED = "image_system_step_listening_disabled";
    String IMAGE_SYSTEM_STEP_LISTENING_HOVER = "image_system_step_listening_hover";
    String IMAGE_SYSTEM_STEP_PREVIEW = "image_system_step_preview";
    String IMAGE_SYSTEM_STEP_PREVIEW_CHECKED = "image_system_step_preview_checked";
    String IMAGE_SYSTEM_STEP_PREVIEW_DISABLED = "image_system_step_preview_disabled";
    String IMAGE_SYSTEM_STEP_PREVIEW_HOVER = "image_system_step_preview_hover";
    String IMAGE_SYSTEM_STEP_READING = "image_system_step_reading";
    String IMAGE_SYSTEM_STEP_READING_CHECKED = "image_system_step_reading_checked";
    String IMAGE_SYSTEM_STEP_READING_DISABLED = "image_system_step_reading_disabled";
    String IMAGE_SYSTEM_STEP_READING_HOVER = "image_system_step_reading_hover";
    String IMAGE_SYSTEM_STEP_SPEAKING = "image_system_step_speaking";
    String IMAGE_SYSTEM_STEP_SPEAKING_CHECKED = "image_system_step_speaking_checked";
    String IMAGE_SYSTEM_STEP_SPEAKING_DISABLED = "image_system_step_speaking_disabled";
    String IMAGE_SYSTEM_STEP_SPEAKING_HOVER = "image_system_step_speaking_hover";
    String IMAGE_SYSTEM_STEP_WRITING = "image_system_step_writing";
    String IMAGE_SYSTEM_STEP_WRITING_CHECKED = "image_system_step_writing_checked";
    String IMAGE_SYSTEM_STEP_WRITING_DISABLED = "image_system_step_writing_disabled";
    String IMAGE_SYSTEM_STEP_WRITING_HOVER = "image_system_step_writing_hover";
    String IMAGE_SYSTEM_TRASH = "image_system_trash";
    String IMAGE_SYSTEM_TRASH_HOVER = "image_system_trash_hover";
    String IMAGE_SYSTEM_UNBOXED = "image_system_unboxed";
    String IMAGE_SYSTEM_UNBOXED_DISABLED = "image_system_unboxed_disabled";
    String IMAGE_SYSTEM_UPDATE = "image_system_update";
    String IMAGE_SYSTEM_UPDATE_HOVER = "image_system_update_hover";

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
