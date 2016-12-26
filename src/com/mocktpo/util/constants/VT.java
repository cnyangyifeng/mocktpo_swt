package com.mocktpo.util.constants;

/**
 * View Type Constants
 */
public interface VT {

    /* General View Types */

    int VIEW_TYPE_TEST_INTRO = 1;
    int VIEW_TYPE_GENERAL_TEST_INFO = 2;
    int VIEW_TYPE_BREAK_POINT = 3;

    /* Reading Section View Types */

    int VIEW_TYPE_READING_SECTION_DIRECTIONS = 11;
    int VIEW_TYPE_READING_PASSAGE = 12;
    int VIEW_TYPE_READING_QUESTION = 13;
    int VIEW_TYPE_READING_INSERT_TEXT_QUESTION = 14;
    int VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION = 15;
    int VIEW_TYPE_READING_CATEGORY_CHART_QUESTION = 16;
    int VIEW_TYPE_READING_SECTION_END = 17;

    /* Listening Section View Types */

    int VIEW_TYPE_LISTENING_HEADSET_ON = 21;
    int VIEW_TYPE_CHANGING_VOLUME = 22;
    int VIEW_TYPE_LISTENING_SECTION_DIRECTIONS = 23;
    int VIEW_TYPE_LISTENING_MATERIAL = 24;
    int VIEW_TYPE_LISTENING_REPLAY = 25;
    int VIEW_TYPE_LISTENING_QUESTION = 26;
    int VIEW_TYPE_LISTENING_MULTIPLE_ANSWERS_QUESTION = 27;
    int VIEW_TYPE_LISTENING_ORDER_EVENTS_QUESTION = 28;
    int VIEW_TYPE_LISTENING_MATCH_OBJECTS_QUESTION = 29;
    int VIEW_TYPE_LISTENING_SECTION_END = 210;
    int VIEW_TYPE_LISTENING_DIRECTIONS = 211;

    /* Speaking Section View Types */

    int VIEW_TYPE_SPEAKING_HEADSET_ON = 31;
    int VIEW_TYPE_ADJUSTING_MICROPHONE = 32;
    int VIEW_TYPE_SPEAKING_SECTION_DIRECTIONS = 33;
    int VIEW_TYPE_SPEAKING_QUESTION_DIRECTIONS = 34;
    int VIEW_TYPE_SPEAKING_QUESTION = 35;

    /* Writing Section View Types */

    int VIEW_TYPE_WRITING_HEADSET_ON = 41;
    int VIEW_TYPE_INTEGRATED_WRITING_DIRECTIONS = 42;
    int VIEW_TYPE_INDEPENDENT_WRITING_DIRECTIONS = 43;
}
