package com.mocktpo.util;

import com.mocktpo.util.constants.VT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ResourceBundle;

public class TestViewTypeUtils {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    private TestViewTypeUtils() {
    }

    public static String getViewTypeName(int viewType) {

        String viewTypeName;

        switch (viewType) {

            /* General View Types */

            case VT.VIEW_TYPE_TEST_INTRO:
                viewTypeName = "Test Introduction";
                break;
            case VT.VIEW_TYPE_GENERAL_TEST_INFO:
                viewTypeName = "General Test Information";
                break;
            case VT.VIEW_TYPE_BREAK_POINT:
                viewTypeName = "Break Point";
                break;
            case VT.VIEW_TYPE_TEST_END:
                viewTypeName = "Test End";
                break;

            /* Reading Section View Types */

            case VT.VIEW_TYPE_READING_SECTION_DIRECTIONS:
                viewTypeName = "Reading Section Directions";
                break;
            case VT.VIEW_TYPE_READING_PASSAGE:
                viewTypeName = "Reading Passage";
                break;
            case VT.VIEW_TYPE_READING_QUESTION:
                viewTypeName = "Reading Question";
                break;
            case VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION:
                viewTypeName = "Reading Insert Text Question";
                break;
            case VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION:
                viewTypeName = "Reading Prose Summary Question";
                break;
            case VT.VIEW_TYPE_READING_FILL_IN_A_TABLE_QUESTION:
                viewTypeName = "Reading Fill in a Table Question";
                break;
            case VT.VIEW_TYPE_READING_SECTION_END:
                viewTypeName = "Reading Section End";
                break;

            /* Listening Section View Types */

            case VT.VIEW_TYPE_LISTENING_HEADSET_ON:
                viewTypeName = "Listening Headset On";
                break;
            case VT.VIEW_TYPE_CHANGING_VOLUME:
                viewTypeName = "Changing Volume";
                break;
            case VT.VIEW_TYPE_LISTENING_SECTION_DIRECTIONS:
                viewTypeName = "Listening Section Directions";
                break;
            case VT.VIEW_TYPE_LISTENING_MATERIAL:
                viewTypeName = "Listening Material";
                break;
            case VT.VIEW_TYPE_LISTENING_REPLAY:
                viewTypeName = "Listening Replay";
                break;
            case VT.VIEW_TYPE_LISTENING_QUESTION:
                viewTypeName = "Listening Question";
                break;
            case VT.VIEW_TYPE_LISTENING_MULTIPLE_RESPONSE_QUESTION:
                viewTypeName = "Listening Multiple Response Question";
                break;
            case VT.VIEW_TYPE_LISTENING_SORT_EVENTS_QUESTION:
                viewTypeName = "Listening Sort Events Question";
                break;
            case VT.VIEW_TYPE_LISTENING_CATEGORIZE_OBJECTS_QUESTION:
                viewTypeName = "Listening Categorize Objects Question";
                break;
            case VT.VIEW_TYPE_LISTENING_SECTION_END:
                viewTypeName = "Listening Section End";
                break;
            case VT.VIEW_TYPE_LISTENING_DIRECTIONS:
                viewTypeName = "Listening Directions";
                break;

            /* Speaking Section View Types */

            case VT.VIEW_TYPE_SPEAKING_HEADSET_ON:
                viewTypeName = "Speaking Headset On";
                break;
            case VT.VIEW_TYPE_ADJUSTING_MICROPHONE:
                viewTypeName = "Adjusting Microphone";
                break;
            case VT.VIEW_TYPE_SPEAKING_SECTION_DIRECTIONS:
                viewTypeName = "Speaking Section Directions";
                break;
            case VT.VIEW_TYPE_SPEAKING_TASK_DIRECTIONS:
                viewTypeName = "Speaking Task Directions";
                break;
            case VT.VIEW_TYPE_SPEAKING_TASK:
                viewTypeName = "Speaking Task";
                break;
            case VT.VIEW_TYPE_SPEAKING_READING_PASSAGE:
                viewTypeName = "Speaking Reading Passage";
                break;
            case VT.VIEW_TYPE_SPEAKING_LISTENING_MATERIAL:
                viewTypeName = "Speaking Listening Material";
                break;
            case VT.VIEW_TYPE_SPEAKING_SECTION_END:
                viewTypeName = "Speaking Section End";
                break;

            /* Writing Section View Types */

            case VT.VIEW_TYPE_WRITING_SECTION_DIRECTIONS:
                viewTypeName = "Writing Section Directions";
                break;
            case VT.VIEW_TYPE_INTEGRATED_WRITING_DIRECTIONS:
                viewTypeName = "Integrated Writing Directions";
                break;
            case VT.VIEW_TYPE_WRITING_READING_PASSAGE:
                viewTypeName = "Writing Reading Passage";
                break;
            case VT.VIEW_TYPE_WRITING_LISTENING_MATERIAL:
                viewTypeName = "Writing Listening Material";
                break;
            case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK:
                viewTypeName = "Integrated Writing Task";
                break;
            case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK_END:
                viewTypeName = "Integrated Writing Task End";
                break;
            case VT.VIEW_TYPE_INDEPENDENT_WRITING_DIRECTIONS:
                viewTypeName = "Independent Writing Directions";
                break;
            case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK:
                viewTypeName = "Independent Writing Task";
                break;
            case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK_END:
                viewTypeName = "Independent Writing Task End";
                break;
            default:
                viewTypeName = msgs.getString("unknown");
                break;
        }

        return viewTypeName;
    }
}
