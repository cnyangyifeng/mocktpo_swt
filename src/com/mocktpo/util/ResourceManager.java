package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ResourceManager {

    private static final int INITIAL_CACHE_SIZE = 64;

    private static final ConcurrentMap<Integer, Resource> caches = new ConcurrentHashMap<Integer, Resource>(INITIAL_CACHE_SIZE);

    public static void alloc(Display d) {

        /*
         * ==================================================
         * 
         * Colors
         * 
         * ==================================================
         */

        caches.putIfAbsent(MT.COLOR_BEIGE, new Color(d, 242, 232, 200)); // #f2e8c8
        caches.putIfAbsent(MT.COLOR_BLACK, new Color(d, 38, 50, 56)); // #000000(rgb 0,0,0), #263238(rgb 38,50,56)
        caches.putIfAbsent(MT.COLOR_BURGUNDY, new Color(d, 132, 0, 50)); // #840032
        // caches.putIfAbsent(MT.COLOR_DARK_ORANGE, new Color(d, 255, 140, 0)); // #ff8c00
        caches.putIfAbsent(MT.COLOR_DARK_BLUE, new Color(d, 13, 71, 161)); // #2f528c(47,82,140), #0d47a1(rgb 13,71,161)
        caches.putIfAbsent(MT.COLOR_GRAY20, new Color(d, 55, 71, 79)); // #333333, #37474f(rgb 55,71,79)
        caches.putIfAbsent(MT.COLOR_GRAY40, new Color(d, 102, 102, 102)); // #666666
        caches.putIfAbsent(MT.COLOR_GRAY60, new Color(d, 153, 153, 153)); // #999999
        // caches.putIfAbsent(MT.COLOR_GREEN, new Color(d, 0, 128, 0)); // #008000
        caches.putIfAbsent(MT.COLOR_INDIGO, new Color(d, 55, 50, 125)); // #37327d
        // caches.putIfAbsent(MT.COLOR_ORANGE, new Color(d, 255, 165, 0)); // #ffa500
        // caches.putIfAbsent(MT.COLOR_ORANGE_RED, new Color(d, 255, 69, 0)); // #ff4500
        // caches.putIfAbsent(MT.COLOR_OXFORD_BLUE, new Color(d, 0, 38, 66)); // #002642
        // caches.putIfAbsent(MT.COLOR_PINK, new Color(d, 233, 30, 99)); // #e91e63
        caches.putIfAbsent(MT.COLOR_ROSY_BROWN, new Color(d, 218, 192, 200)); // #dac0c8
        caches.putIfAbsent(MT.COLOR_TOUPE, new Color(d, 179, 139, 109)); // #b38b6d
        caches.putIfAbsent(MT.COLOR_WHITE, new Color(d, 255, 255, 255)); // #ffffff
        caches.putIfAbsent(MT.COLOR_WHITE_SMOKE, new Color(d, 245, 245, 245)); // #f5f5f5
        /* Others */
        caches.putIfAbsent(MT.COLOR_HIGHLIGHTED, new Color(d, 220, 220, 220)); // #dcdcdc
        caches.putIfAbsent(MT.COLOR_WINDOW_BACKGROUND, new Color(d, 239, 239, 239)); // #efefef

        /*
         * ==================================================
         * 
         * Cursors
         * 
         * ==================================================
         */

        caches.putIfAbsent(MT.CURSOR_ARROW, new Cursor(d, SWT.CURSOR_ARROW));
        caches.putIfAbsent(MT.CURSOR_HAND, new Cursor(d, SWT.CURSOR_HAND));

        /*
         * ==================================================
         * 
         * Fonts
         * 
         * ==================================================
         */

        FontUtils.loadExternalFonts(d);

        /* 8px, 6pt, 50% */
        caches.putIfAbsent(MT.FONT_XX_SMALL, FontUtils.getFont(d, 8));
        /* 10px, 7.5pt(8pt), 62.5% */
        caches.putIfAbsent(MT.FONT_X_SMALL, FontUtils.getFont(d, 10));
        /* 12px, 9pt, 75% */
        caches.putIfAbsent(MT.FONT_SMALL, FontUtils.getFont(d, 12));
        caches.putIfAbsent(MT.FONT_SMALL_BOLD, FontUtils.getFont(d, 12, SWT.BOLD));
        caches.putIfAbsent(MT.FONT_SMALL_ITALIC, FontUtils.getFont(d, 12, SWT.ITALIC));
        /* 15px, 11pt, 95% */
        caches.putIfAbsent(MT.FONT_MEDIUM, FontUtils.getFont(d, 15));
        caches.putIfAbsent(MT.FONT_MEDIUM_BOLD, FontUtils.getFont(d, 15, SWT.BOLD));
        caches.putIfAbsent(MT.FONT_MEDIUM_ITALIC, FontUtils.getFont(d, 15, SWT.ITALIC));
        /* 20px, 15pt, 125% */
        caches.putIfAbsent(MT.FONT_LARGE, FontUtils.getFont(d, 20));
        caches.putIfAbsent(MT.FONT_LARGE_BOLD, FontUtils.getFont(d, 20, SWT.BOLD));
        /* 24px, 18pt, 150% */
        caches.putIfAbsent(MT.FONT_X_LARGE, FontUtils.getFont(d, 24));
        caches.putIfAbsent(MT.FONT_X_LARGE_BOLD, FontUtils.getFont(d, 24, SWT.BOLD));
        /* 32px, 24pt, 200% */
        caches.putIfAbsent(MT.FONT_XX_LARGE, FontUtils.getFont(d, 32));
        /* Others */
        caches.putIfAbsent(MT.FONT_ACTIVATION_CODE, FontUtils.getFont(d, "Courier New", 12, SWT.NORMAL));
        caches.putIfAbsent(MT.FONT_SERIF_HEADING, FontUtils.getFont(d, "Georgia", 20, SWT.BOLD));
        caches.putIfAbsent(MT.FONT_SERIF_ITALIC_TEXT, FontUtils.getFont(d, "Georgia", 16, SWT.ITALIC));

        /*
         * ==================================================
         * 
         * Images
         * 
         * ==================================================
         */

        caches.putIfAbsent(MT.IMAGE_BACK_OVAL, ImageUtils.load(d, "back_o"));
        caches.putIfAbsent(MT.IMAGE_BACK_OVAL_DISABLED, ImageUtils.load(d, "back_o_d"));
        caches.putIfAbsent(MT.IMAGE_BACK_OVAL_HOVER, ImageUtils.load(d, "back_o_h"));
        caches.putIfAbsent(MT.IMAGE_BOXED, ImageUtils.load(d, "boxed"));
        caches.putIfAbsent(MT.IMAGE_BULLET, ImageUtils.load(d, "bullet"));
        caches.putIfAbsent(MT.IMAGE_CHECKED, ImageUtils.load(d, "checked"));
        caches.putIfAbsent(MT.IMAGE_CONFIRM_RESPONSE, ImageUtils.load(d, "confirm_response"));
        caches.putIfAbsent(MT.IMAGE_CONFIRM_RESPONSE_DISABLED, ImageUtils.load(d, "confirm_response_d"));
        caches.putIfAbsent(MT.IMAGE_CONFIRM_RESPONSE_HOVER, ImageUtils.load(d, "confirm_response_h"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE, ImageUtils.load(d, "continue"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_DISABLED, ImageUtils.load(d, "continue_d"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_HOVER, ImageUtils.load(d, "continue_h"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_OVAL, ImageUtils.load(d, "continue_o"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_OVAL_DISABLED, ImageUtils.load(d, "continue_o_d"));
        caches.putIfAbsent(MT.IMAGE_CONTINUE_OVAL_HOVER, ImageUtils.load(d, "continue_o_h"));
        caches.putIfAbsent(MT.IMAGE_ETS_TOEFL, ImageUtils.load(d, "ets_toefl"));
        caches.putIfAbsent(MT.IMAGE_GO_TO_QUESTION, ImageUtils.load(d, "go_to_question"));
        caches.putIfAbsent(MT.IMAGE_GO_TO_QUESTION_HOVER, ImageUtils.load(d, "go_to_question_h"));
        caches.putIfAbsent(MT.IMAGE_HEADSET, ImageUtils.load(d, "headset"));
        caches.putIfAbsent(MT.IMAGE_HELP_OVAL, ImageUtils.load(d, "help_o"));
        caches.putIfAbsent(MT.IMAGE_HELP_OVAL_DISABLED, ImageUtils.load(d, "help_o_d"));
        caches.putIfAbsent(MT.IMAGE_HELP_OVAL_HOVER, ImageUtils.load(d, "help_o_h"));
        caches.putIfAbsent(MT.IMAGE_HIDE_TIME, ImageUtils.load(d, "hide_time"));
        caches.putIfAbsent(MT.IMAGE_HIDE_TIME_DISABLED, ImageUtils.load(d, "hide_time_d"));
        caches.putIfAbsent(MT.IMAGE_HIDE_TIME_HOVER, ImageUtils.load(d, "hide_time_h"));
        caches.putIfAbsent(MT.IMAGE_NEXT_OVAL, ImageUtils.load(d, "next_o"));
        caches.putIfAbsent(MT.IMAGE_NEXT_OVAL_DISABLED, ImageUtils.load(d, "next_o_d"));
        caches.putIfAbsent(MT.IMAGE_NEXT_OVAL_HOVER, ImageUtils.load(d, "next_o_h"));
        caches.putIfAbsent(MT.IMAGE_OK_OVAL, ImageUtils.load(d, "ok_o"));
        caches.putIfAbsent(MT.IMAGE_OK_OVAL_DISABLED, ImageUtils.load(d, "ok_o_d"));
        caches.putIfAbsent(MT.IMAGE_OK_OVAL_HOVER, ImageUtils.load(d, "ok_o_h"));
        caches.putIfAbsent(MT.IMAGE_PAUSE_TEST, ImageUtils.load(d, "pause_test"));
        caches.putIfAbsent(MT.IMAGE_PAUSE_TEST_HOVER, ImageUtils.load(d, "pause_test_h"));
        caches.putIfAbsent(MT.IMAGE_PLAYBACK_RESPONSE, ImageUtils.load(d, "playback_response"));
        caches.putIfAbsent(MT.IMAGE_PLAYBACK_RESPONSE_DISABLED, ImageUtils.load(d, "playback_response_d"));
        caches.putIfAbsent(MT.IMAGE_PLAYBACK_RESPONSE_HOVER, ImageUtils.load(d, "playback_response_h"));
        caches.putIfAbsent(MT.IMAGE_READY_TO_ANSWER, ImageUtils.load(d, "ready_to_answer"));
        caches.putIfAbsent(MT.IMAGE_READY_TO_ANSWER_2, ImageUtils.load(d, "ready_to_answer_2"));
        caches.putIfAbsent(MT.IMAGE_RECORD_AGAIN, ImageUtils.load(d, "record_again"));
        caches.putIfAbsent(MT.IMAGE_RECORD_AGAIN_DISABLED, ImageUtils.load(d, "record_again_d"));
        caches.putIfAbsent(MT.IMAGE_RECORD_AGAIN_HOVER, ImageUtils.load(d, "record_again_h"));
        caches.putIfAbsent(MT.IMAGE_RETURN, ImageUtils.load(d, "return"));
        caches.putIfAbsent(MT.IMAGE_RETURN_HOVER, ImageUtils.load(d, "return_h"));
        caches.putIfAbsent(MT.IMAGE_RETURN_TO_QUESTION, ImageUtils.load(d, "return_to_question"));
        caches.putIfAbsent(MT.IMAGE_RETURN_TO_QUESTION_HOVER, ImageUtils.load(d, "return_to_question_h"));
        caches.putIfAbsent(MT.IMAGE_REVIEW, ImageUtils.load(d, "review"));
        caches.putIfAbsent(MT.IMAGE_REVIEW_HOVER, ImageUtils.load(d, "review_h"));
        caches.putIfAbsent(MT.IMAGE_REVIEW_OVAL, ImageUtils.load(d, "review_o"));
        caches.putIfAbsent(MT.IMAGE_REVIEW_OVAL_DISABLED, ImageUtils.load(d, "review_o_d"));
        caches.putIfAbsent(MT.IMAGE_REVIEW_OVAL_HOVER, ImageUtils.load(d, "review_o_h"));
        caches.putIfAbsent(MT.IMAGE_SHOW_TIME, ImageUtils.load(d, "show_time"));
        caches.putIfAbsent(MT.IMAGE_SHOW_TIME_DISABLED, ImageUtils.load(d, "show_time_d"));
        caches.putIfAbsent(MT.IMAGE_SHOW_TIME_HOVER, ImageUtils.load(d, "show_time_h"));
        caches.putIfAbsent(MT.IMAGE_SKIP, ImageUtils.load(d, "skip"));
        caches.putIfAbsent(MT.IMAGE_SKIP_HOVER, ImageUtils.load(d, "skip_h"));
        caches.putIfAbsent(MT.IMAGE_STOP_RECORDING, ImageUtils.load(d, "stop_recording"));
        caches.putIfAbsent(MT.IMAGE_STOP_RECORDING_HOVER, ImageUtils.load(d, "stop_recording_h"));
        caches.putIfAbsent(MT.IMAGE_UNBOXED, ImageUtils.load(d, "unboxed"));
        caches.putIfAbsent(MT.IMAGE_UNCHECKED, ImageUtils.load(d, "unchecked"));
        caches.putIfAbsent(MT.IMAGE_VIEW_QUESTION, ImageUtils.load(d, "view_question"));
        caches.putIfAbsent(MT.IMAGE_VIEW_QUESTION_HOVER, ImageUtils.load(d, "view_question_h"));
        caches.putIfAbsent(MT.IMAGE_VIEW_TEXT, ImageUtils.load(d, "view_text"));
        caches.putIfAbsent(MT.IMAGE_VIEW_TEXT_HOVER, ImageUtils.load(d, "view_text_h"));
        caches.putIfAbsent(MT.IMAGE_VOLUME_OVAL, ImageUtils.load(d, "volume_o"));
        caches.putIfAbsent(MT.IMAGE_VOLUME_OVAL_HOVER, ImageUtils.load(d, "volume_o_h"));

        caches.putIfAbsent(MT.IMAGE_APP_ICON, ImageUtils.load(d, "app_icon"));
        caches.putIfAbsent(MT.IMAGE_LOGO, ImageUtils.load(d, "logo"));
        caches.putIfAbsent(MT.IMAGE_SPLASH, ImageUtils.load(d, "splash"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_ACTIVATE, ImageUtils.load(d, "sys_activate"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_ACTIVATE_HOVER, ImageUtils.load(d, "sys_activate_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT, ImageUtils.load(d, "sys_add_an_insertion_point"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_DISABLED, ImageUtils.load(d, "sys_add_an_insertion_point_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_HOVER, ImageUtils.load(d, "sys_add_an_insertion_point_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_BACK, ImageUtils.load(d, "sys_back"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_BACK_DISABLED, ImageUtils.load(d, "sys_back_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_BACK_HOVER, ImageUtils.load(d, "sys_back_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_BOXED, ImageUtils.load(d, "sys_boxed"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_BOXED_DISABLED, ImageUtils.load(d, "sys_boxed_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_BRING_FORWARD, ImageUtils.load(d, "sys_bring_forward"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_BRING_FORWARD_HOVER, ImageUtils.load(d, "sys_bring_forward_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CANCEL, ImageUtils.load(d, "sys_cancel"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CANCEL_HOVER, ImageUtils.load(d, "sys_cancel_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CANCEL_WIDE, ImageUtils.load(d, "sys_cancel_wide"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CANCEL_WIDE_HOVER, ImageUtils.load(d, "sys_cancel_wide_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CONTINUE, ImageUtils.load(d, "sys_continue"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CONTINUE_HOVER, ImageUtils.load(d, "sys_continue_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_COPY, ImageUtils.load(d, "sys_copy"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_COPY_HOVER, ImageUtils.load(d, "sys_copy_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CUT, ImageUtils.load(d, "sys_cut"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_CUT_HOVER, ImageUtils.load(d, "sys_cut_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_DELETE, ImageUtils.load(d, "sys_delete"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_DELETE_HOVER, ImageUtils.load(d, "sys_delete_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_EDIT, ImageUtils.load(d, "sys_edit"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_EDIT_HOVER, ImageUtils.load(d, "sys_edit_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_EXPORT_AS_PDF, ImageUtils.load(d, "sys_export_as_pdf"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_EXPORT_AS_PDF_HOVER, ImageUtils.load(d, "sys_export_as_pdf_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_EXPORT_AS_ZIP, ImageUtils.load(d, "sys_export_as_zip"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_EXPORT_AS_ZIP_DISABLED, ImageUtils.load(d, "sys_export_as_zip_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_EXPORT_AS_ZIP_HOVER, ImageUtils.load(d, "sys_export_as_zip_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT, ImageUtils.load(d, "sys_highlight_selected_text"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_DISABLED, ImageUtils.load(d, "sys_highlight_selected_text_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_HOVER, ImageUtils.load(d, "sys_highlight_selected_text_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_IMPORT, ImageUtils.load(d, "sys_import"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_IMPORT_HOVER, ImageUtils.load(d, "sys_import_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_MARK_PARAGRAPHS, ImageUtils.load(d, "sys_mark_paragraphs"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_MARK_PARAGRAPHS_DISABLED, ImageUtils.load(d, "sys_mark_paragraphs_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_MARK_PARAGRAPHS_HOVER, ImageUtils.load(d, "sys_mark_paragraphs_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION, ImageUtils.load(d, "sys_new_categorize_objects_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_CATEGORIZE_OBJECTS_QUESTION_HOVER, ImageUtils.load(d, "sys_new_categorize_objects_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION, ImageUtils.load(d, "sys_new_fill_in_a_table_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_FILL_IN_A_TABLE_QUESTION_HOVER, ImageUtils.load(d, "sys_new_fill_in_a_table_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S, ImageUtils.load(d, "sys_new_independent_task_s"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_S_HOVER, ImageUtils.load(d, "sys_new_independent_task_s_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W, ImageUtils.load(d, "sys_new_independent_task_w"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INDEPENDENT_TASK_W_HOVER, ImageUtils.load(d, "sys_new_independent_task_w_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION, ImageUtils.load(d, "sys_new_insert_text_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INSERT_TEXT_QUESTION_HOVER, ImageUtils.load(d, "sys_new_insert_text_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS, ImageUtils.load(d, "sys_new_integrated_task_ls"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_LS_HOVER, ImageUtils.load(d, "sys_new_integrated_task_ls_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS, ImageUtils.load(d, "sys_new_integrated_task_rls"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLS_HOVER, ImageUtils.load(d, "sys_new_integrated_task_rls_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW, ImageUtils.load(d, "sys_new_integrated_task_rlw"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_RLW_HOVER, ImageUtils.load(d, "sys_new_integrated_task_rlw_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S, ImageUtils.load(d, "sys_new_integrated_task_s"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_INTEGRATED_TASK_S_HOVER, ImageUtils.load(d, "sys_new_integrated_task_s_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_LISTENING_MATERIAL, ImageUtils.load(d, "sys_new_listening_material"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_LISTENING_MATERIAL_HOVER, ImageUtils.load(d, "sys_new_listening_material_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_LISTENING_QUESTION, ImageUtils.load(d, "sys_new_listening_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_LISTENING_QUESTION_HOVER, ImageUtils.load(d, "sys_new_listening_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION, ImageUtils.load(d, "sys_new_listening_replay_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_LISTENING_REPLAY_QUESTION_HOVER, ImageUtils.load(d, "sys_new_listening_replay_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION, ImageUtils.load(d, "sys_new_multiple_choice_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_MULTIPLE_CHOICE_QUESTION_HOVER, ImageUtils.load(d, "sys_new_multiple_choice_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION, ImageUtils.load(d, "sys_new_multiple_response_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_MULTIPLE_RESPONSE_QUESTION_HOVER, ImageUtils.load(d, "sys_new_multiple_response_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION, ImageUtils.load(d, "sys_new_prose_summary_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_PROSE_SUMMARY_QUESTION_HOVER, ImageUtils.load(d, "sys_new_prose_summary_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_READING_PASSAGE, ImageUtils.load(d, "sys_new_reading_passage"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_READING_PASSAGE_HOVER, ImageUtils.load(d, "sys_new_reading_passage_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_READING_QUESTION, ImageUtils.load(d, "sys_new_reading_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_READING_QUESTION_HOVER, ImageUtils.load(d, "sys_new_reading_question_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION, ImageUtils.load(d, "sys_new_sort_events_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_SORT_EVENTS_QUESTION_HOVER, ImageUtils.load(d, "sys_new_sort_events_question"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_TEST, ImageUtils.load(d, "sys_new_test"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_TEST_HOVER, ImageUtils.load(d, "sys_new_test_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_TEST_PAPER, ImageUtils.load(d, "sys_new_test_paper"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_NEW_TEST_PAPER_HOVER, ImageUtils.load(d, "sys_new_test_paper_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_PASTE, ImageUtils.load(d, "sys_paste"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_PASTE_HOVER, ImageUtils.load(d, "sys_paste_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_REPORT, ImageUtils.load(d, "sys_report"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_REPORT_HOVER, ImageUtils.load(d, "sys_report_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_SAVE, ImageUtils.load(d, "sys_save"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_SAVE_DISABLED, ImageUtils.load(d, "sys_save_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_SAVE_HOVER, ImageUtils.load(d, "sys_save_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_SEND_BACKWARD, ImageUtils.load(d, "sys_send_backward"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_SEND_BACKWARD_HOVER, ImageUtils.load(d, "sys_send_backward_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STAR, ImageUtils.load(d, "sys_star"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STAR_OUTLINE, ImageUtils.load(d, "sys_star_outline"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_GENERAL, ImageUtils.load(d, "sys_step_general"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_GENERAL_CHECKED, ImageUtils.load(d, "sys_step_general_c"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_GENERAL_DISABLED, ImageUtils.load(d, "sys_step_general_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_GENERAL_HOVER, ImageUtils.load(d, "sys_step_general_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_LISTENING, ImageUtils.load(d, "sys_step_listening"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED, ImageUtils.load(d, "sys_step_listening_c"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_LISTENING_DISABLED, ImageUtils.load(d, "sys_step_listening_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_LISTENING_HOVER, ImageUtils.load(d, "sys_step_listening_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_PREVIEW, ImageUtils.load(d, "sys_step_preview"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_PREVIEW_CHECKED, ImageUtils.load(d, "sys_step_preview_c"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_PREVIEW_DISABLED, ImageUtils.load(d, "sys_step_preview_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_PREVIEW_HOVER, ImageUtils.load(d, "sys_step_preview_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_READING, ImageUtils.load(d, "sys_step_reading"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_READING_CHECKED, ImageUtils.load(d, "sys_step_reading_c"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_READING_DISABLED, ImageUtils.load(d, "sys_step_reading_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_READING_HOVER, ImageUtils.load(d, "sys_step_reading_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_SPEAKING, ImageUtils.load(d, "sys_step_speaking"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_SPEAKING_CHECKED, ImageUtils.load(d, "sys_step_speaking_c"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_SPEAKING_DISABLED, ImageUtils.load(d, "sys_step_speaking_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_SPEAKING_HOVER, ImageUtils.load(d, "sys_step_speaking_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_WRITING, ImageUtils.load(d, "sys_step_writing"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_WRITING_CHECKED, ImageUtils.load(d, "sys_step_writing_c"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_WRITING_DISABLED, ImageUtils.load(d, "sys_step_writing_d"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_STEP_WRITING_HOVER, ImageUtils.load(d, "sys_step_writing_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_TRASH, ImageUtils.load(d, "sys_trash"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_TRASH_HOVER, ImageUtils.load(d, "sys_trash_h"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_UNBOXED, ImageUtils.load(d, "sys_unboxed"));
        caches.putIfAbsent(MT.IMAGE_SYSTEM_UNBOXED_DISABLED, ImageUtils.load(d, "sys_unboxed_d"));
    }

    public static void dispose() {
        for (int key : caches.keySet()) {
            Resource r = caches.get(key);
            caches.remove(key);
            r.dispose();
        }
    }

    public static Color getColor(int key) {
        return (Color) caches.get(key);
    }

    public static Cursor getCursor(int key) {
        return (Cursor) caches.get(key);
    }

    public static Font getFont(int key) {
        return (Font) caches.get(key);
    }

    public static Image getImage(int key) {
        return (Image) caches.get(key);
    }
}
