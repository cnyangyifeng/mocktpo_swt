package com.mocktpo.util.constants;

/**
 * Layout Constants
 */
public interface LC {

    /**************************************************
     * Screens
     *
     *
     * 2560 : 1920 : 1280 = 2x : 1.5x : 1x
     *
     * Use Large (2x) images, if screen width approximately equals with 2560px;
     *
     * e.g. 2560x1600 (8:5), or above
     *
     * Use Medium (1.5x) images, if screen width approximately equals with
     * 1920px;
     *
     * e.g. 1920x1200 (8:5), 1920x1080 (16:9), 1680x1050 (8:5), 1600x900 (16:9)
     *
     * Use Small (1x) images, if screen width approximately equals with 1280px;
     *
     * e.g. 1440x900 (8:5), 1366x768 (16:9), 1280x1024 (5:4), 1280x800 (8:5)
     **************************************************/

    int SCREEN_SMALL = 1;
    int SCREEN_MEDIUM = 2;
    int SCREEN_LARGE = 3;

    int REF_SMALL_SCREEN_WIDTH = 1440;
    int REF_MEDIUM_SCREEN_WIDTH = 1920;
    // int REF_LARGE_SCREEN_WIDTH = 2560;

    /*
     * ==================================================
     *
     * Shell
     *
     * ==================================================
     */

    /* Test Module */
    int CAPTION_WIDTH = 180;
    int CONTINUE_BUTTON_WIDTH = 74;
    int LISTENING_DND_QUESTION_HEIGHT = 50;
    int READING_DND_QUESTION_WIDTH = 480;
    int READING_DND_QUESTION_HEIGHT = 60;
    int RETURN_TO_QUESTION_BUTTON_WIDTH = 74;
    int TEST_HEADER_HEIGHT = 90;
    int VOLUME_CONTROL_WIDTH = 100;

    /* Test Report Module */
    int REPORT_TITLE_WIDTH = 300;
    int REPORT_SECTION_LABEL_WIDTH = 90;
    int REPORT_SECTION_LABEL_HEIGHT = 30;

    /* Test Editor Module */
    int REPORT_STEP_BUTTON_WIDTH = 120;

    /*
     * ==================================================
     *
     * Window
     *
     * ==================================================
     */

    int MODAL_WINDOW_WIDTH_HINT = 480;
    int MODAL_WINDOW_HEIGHT_HINT = 480;
}
