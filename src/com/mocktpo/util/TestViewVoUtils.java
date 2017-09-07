package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.vo.StyleRangeVo;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class TestViewVoUtils {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    private TestViewVoUtils() {
    }

    /*
     * ==================================================
     *
     * General View Types
     *
     * ==================================================
     */

    public static TestViewVo initTestIntroViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_TEST_INTRO);
        viewVo.setSectionType(ST.SECTION_TYPE_NONE);
        viewVo.setSectionTypeName("");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText(MT.STRING_TEST_INTRO_VIEW_DESCRIPTION);
        List<StyleRangeVo> descriptionStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(183, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStyleVos);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static TestViewVo initGeneralTestInfoViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_GENERAL_TEST_INFO);
        viewVo.setSectionType(ST.SECTION_TYPE_NONE);
        viewVo.setSectionTypeName("");

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText(MT.STRING_GENERAL_TEST_INFO_VIEW_HEADING);

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText(MT.STRING_GENERAL_TEST_INFO_VIEW_DESCRIPTION);
        List<StyleRangeVo> descriptionStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(101, 7, 1, 0, 0, false, null));
            add(new StyleRangeVo(190, 9, 1, 0, 0, false, null));
            add(new StyleRangeVo(299, 8, 1, 0, 0, false, null));
            add(new StyleRangeVo(490, 7, 1, 0, 0, false, null));
            add(new StyleRangeVo(806, 5, 1, 0, 0, false, null));
            add(new StyleRangeVo(1542, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStyleVos);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("heading", headingVo);
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static TestViewVo initBreakPointViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_BREAK_POINT);
        viewVo.setSectionType(ST.SECTION_TYPE_NONE);
        viewVo.setSectionTypeName("");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText(MT.STRING_BREAK_POINT_VIEW_DESCRIPTION);
        List<StyleRangeVo> descriptionStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(97, 8, 1, 0, 0, false, null));
            add(new StyleRangeVo(201, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStyleVos);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static TestViewVo initTestEndViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_TEST_END);
        viewVo.setSectionType(ST.SECTION_TYPE_NONE);
        viewVo.setSectionTypeName("");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText(MT.STRING_TEST_END_VIEW_DESCRIPTION);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    /*
     * ==================================================
     *
     * Reading Section View Types
     *
     * ==================================================
     */

    public static TestViewVo initReadingSectionDirectionsViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_SECTION_DIRECTIONS);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText(MT.STRING_READING_SECTION_DIRECTIONS_VIEW_HEADING);

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText(MT.STRING_READING_SECTION_DIRECTIONS_VIEW_DESCRIPTION);
        List<StyleRangeVo> descriptionStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(51, 10, 1, 0, 0, false, null));
            add(new StyleRangeVo(89, 10, 1, 0, 0, false, null));
            add(new StyleRangeVo(347, 10, 1, 0, 0, false, null));
            add(new StyleRangeVo(488, 4, 1, 0, 0, false, null));
            add(new StyleRangeVo(597, 4, 1, 0, 0, false, null));
            add(new StyleRangeVo(621, 6, 1, 0, 0, false, null));
            add(new StyleRangeVo(860, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStyleVos);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("heading", headingVo);
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static TestViewVo initRawReadingPassageViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_PASSAGE);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setTimed(true);
        viewVo.setBody(new HashMap<>());

        return viewVo;
    }

    public static TestViewVo initRawReadingMultipleChoiceQuestionViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setTimed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setAnswerable(true);
        viewVo.setBody(new HashMap<>());

        return viewVo;
    }

    public static void updatePassageOffset(TestViewVo viewVo) {
        int passageOffset = 0;
        String text = viewVo.getStyledTextContent("passage");
        if (viewVo.getViewType() == VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION) {
            List<StyleRangeVo> styles = viewVo.getStyledTextStyles("passage");
            if (styles != null && styles.size() > 0) {
                StyleRangeVo styleRangeVo = styles.get(0);
                passageOffset = styleRangeVo.getStart();
            } else {
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == MT.STRING_ARROW.charAt(0)) {
                        passageOffset = i;
                        break;
                    }
                }
            }
        } else if (viewVo.getViewType() == VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION) {
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == MT.STRING_FULL_BLOCK.charAt(0)) {
                    passageOffset = i;
                    break;
                }
            }
        }
        viewVo.setPassageOffset(passageOffset);
    }

    public static TestViewVo initRawReadingInsertTextQuestionViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setTimed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setAnswerable(true);

        final StyledTextVo questionVo = new StyledTextVo();
        questionVo.setText(MT.STRING_READING_INSERT_TEXT_QUESTION_VIEW_QUESTION);

        final StyledTextVo footnoteVo = new StyledTextVo();
        footnoteVo.setText(MT.STRING_READING_INSERT_TEXT_QUESTION_VIEW_FOOTNOTE);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("question", questionVo);
            put("footnote", footnoteVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static void updateInsertionPoints(TestViewVo viewVo) {
        /* Full Block Locations */
        int[] arr = new int[]{0, 0, 0, 0};
        String text = viewVo.getStyledTextContent("passage");
        int p = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == MT.STRING_FULL_BLOCK.charAt(0)) {
                arr[p++] = i;
            }
            if (p == 4) {
                break;
            }
        }
        /* Insertion Point A */
        final StyledTextVo insertionPointAVo = new StyledTextVo();
        insertionPointAVo.setText(MT.STRING_FULL_BLOCK);
        List<StyleRangeVo> insertionPointAStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(arr[0], 0, 0, 0, 0, false, null));
        }};
        insertionPointAVo.setStyles(insertionPointAStyleVos);
        /* Insertion Point B */
        final StyledTextVo insertionPointBVo = new StyledTextVo();
        insertionPointBVo.setText(MT.STRING_FULL_BLOCK);
        List<StyleRangeVo> insertionPointBStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(arr[1], 0, 0, 0, 0, false, null));
        }};
        insertionPointBVo.setStyles(insertionPointBStyleVos);
        /* Insertion Point C */
        final StyledTextVo insertionPointCVo = new StyledTextVo();
        insertionPointCVo.setText(MT.STRING_FULL_BLOCK);
        List<StyleRangeVo> insertionPointCStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(arr[2], 0, 0, 0, 0, false, null));
        }};
        insertionPointCVo.setStyles(insertionPointCStyleVos);
        /* Insertion Point D */
        final StyledTextVo insertionPointDVo = new StyledTextVo();
        insertionPointDVo.setText(MT.STRING_FULL_BLOCK);
        List<StyleRangeVo> insertionPointDStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(arr[3], 0, 0, 0, 0, false, null));
        }};
        insertionPointDVo.setStyles(insertionPointDStyleVos);
        /* Body */
        Map<String, StyledTextVo> body = viewVo.getBody();
        body.put("insertionPointA", insertionPointAVo);
        body.put("insertionPointB", insertionPointBVo);
        body.put("insertionPointC", insertionPointCVo);
        body.put("insertionPointD", insertionPointDVo);
    }

    public static TestViewVo initRawReadingProseSummaryQuestionViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setTimed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setAnswerable(true);

        final StyledTextVo directionsVo = new StyledTextVo();
        directionsVo.setText(MT.STRING_READING_PROSE_SUMMARY_QUESTION_VIEW_DIRECTIONS);
        List<StyleRangeVo> directionsStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(0, 11, 1, 0, 0, false, null));
            add(new StyleRangeVo(348, 32, 1, 0, 0, false, null));
        }};
        directionsVo.setStyles(directionsStyleVos);

        final StyledTextVo tipsVo = new StyledTextVo();
        tipsVo.setText(MT.STRING_READING_PROSE_SUMMARY_QUESTION_VIEW_TIPS);
        List<StyleRangeVo> tipsStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(128, 9, 1, 0, 0, false, null));
        }};
        tipsVo.setStyles(tipsStyleVos);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("directions", directionsVo);
            put("tips", tipsVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static TestViewVo initReadingSectionEndViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_SECTION_END);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setTimed(true);

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText(MT.STRING_READING_SECTION_END_VIEW_DESCRIPTION);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    /*
     * ==================================================
     *
     * Listening Section View Types
     *
     * ==================================================
     */

    public static TestViewVo initListeningHeadsetOnViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initChangingVolumeViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initListeningSectionDirectionsViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initRawListeningMaterialViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_LISTENING_MATERIAL);
        viewVo.setSectionType(ST.SECTION_TYPE_LISTENING);
        viewVo.setSectionTypeName(msgs.getString("listening"));
        viewVo.setWithAudio(true);

        return viewVo;
    }

    public static TestViewVo initRawListeningMultipleChoiceQuestionViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_LISTENING_MULTIPLE_CHOICE_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_LISTENING);
        viewVo.setSectionTypeName(msgs.getString("listening"));
        viewVo.setWithAudio(true);
        viewVo.setTimed(true);
        viewVo.setTimerTaskDelayed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setAnswerable(true);
        viewVo.setBody(new HashMap<>());

        return viewVo;
    }

    public static TestViewVo initRawListeningMultipleResponseQuestionViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_LISTENING_MULTIPLE_RESPONSE_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_LISTENING);
        viewVo.setSectionTypeName(msgs.getString("listening"));
        viewVo.setWithAudio(true);
        viewVo.setTimed(true);
        viewVo.setTimerTaskDelayed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setAnswerable(true);
        viewVo.setBody(new HashMap<>());

        return viewVo;
    }

    public static TestViewVo initRawListeningReplayViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_LISTENING_REPLAY);
        viewVo.setSectionType(ST.SECTION_TYPE_LISTENING);
        viewVo.setSectionTypeName(msgs.getString("listening"));
        viewVo.setWithAudio(true);
        viewVo.setQuestionCaptionVisible(true);

        return viewVo;
    }

    public static TestViewVo initRawListeningSortEventsQuestionViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_LISTENING_SORT_EVENTS_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_LISTENING);
        viewVo.setSectionTypeName(msgs.getString("listening"));
        viewVo.setWithAudio(true);
        viewVo.setTimed(true);
        viewVo.setTimerTaskDelayed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setAnswerable(true);
        viewVo.setBody(new HashMap<>());

        return viewVo;
    }

    public static TestViewVo initRawListeningCategorizeObjectsQuestionViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_LISTENING_CATEGORIZE_OBJECTS_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_LISTENING);
        viewVo.setSectionTypeName(msgs.getString("listening"));
        viewVo.setWithAudio(true);
        viewVo.setTimed(true);
        viewVo.setTimerTaskDelayed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setAnswerable(true);
        viewVo.setBody(new HashMap<>());

        return viewVo;
    }

    public static TestViewVo initListeningSectionEndViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initListeningDirectionsViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    /*
     * ==================================================
     *
     * Speaking Section View Types
     *
     * ==================================================
     */

    public static TestViewVo initSpeakingHeadsetOnViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initAdjustingMicrophoneViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initSpeakingSectionDirectionsViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initSpeakingSectionEndViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    /*
     * ==================================================
     *
     * Writing Section View Types
     *
     * ==================================================
     */

    public static TestViewVo initWritingSectionDirectionsViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initIntegratedWritingDirectionsViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initIntegratedWritingTaskEndViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }


    public static TestViewVo initIndependentWritingDirectionsViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }

    public static TestViewVo initIndependentWritingTaskEndViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();

        return viewVo;
    }
}
