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
        descriptionVo.setText("EDUCATIONAL TESTING SERVICE, ETS, the ETS logo, TOEFL and TOEFL iBT are registered trademarks of Educational Testing Service (ETS) in the United States and other countries.\n\nClick on Continue to go on.");
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
        headingVo.setText("General Test Information");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText("This test measures your ability to use English in an academic context. There are 4 sections.\n\nIn the Reading section, you will read several passages and answer questions about them.\n\nIn the Listening section, you will hear several conversations and lectures and answer questions about them.\n\nIn the Speaking section, you will answer 6 questions. Some of the questions ask you to speak about your own experience. Other questions ask you to speak about lectures and reading passages.\n\nIn the Writing section, you will answer 2 questions. The first question asks you to write about the relationship between a lecture you will hear and a passage you will read. The second question asks you to write an essay about a topic of general interest based on your experience.\n\nDuring this practice test, you may click Pause icon at any time. This will stop the test until you decide to continue. You may continue the test in a few minutes, or at any time during the period that your test is activated.\n\nThere will be directions for each section which explain how to answer the questions in that section.\n\nYou should work quickly but carefully on the Reading and Listening questions. Some questions are more difficult than others, but try to answer every one to the best of your ability. If you are not sure of the answer to a question, make the best guess that you can. The questions that you answer by speaking and by writing are each separately timed. Try to answer every one of these questions as completely as possible in the time allowed.\n\nClick on Continue to go on.");
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
        descriptionVo.setText("You may now take a break. In an actual test there is a ten-minute break at this point.\n\nClick on Continue when you are ready to go on to the next section.\n\nIf you do not wish to take a break, click on Continue now.");
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
        descriptionVo.setText("The testing session is complete.\n\nClick on Continue to see your Score Report.");

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
        headingVo.setText("Reading Section Directions");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText("In this part of the Reading section, you will read 3 passages. In the test you will have 60 minutes to read the passage and answer the questions.\n\nMost questions are worth 1 point but the last question in each set is worth more than 1 point. The directions indicate how many points you may receive.\n\nSome passages include a word or phrase that is underlined in blue. Click on the word or phrase to see a definition or an explanation.\n\nWhen you want to move to the next question. click on Next. You may skip questions and go back to them later if you want to return to previous questions. click on Back.\n\nYou can click on Review at any time and the review screen will show you which questions you have answered and which you have not answered. From this review screen, you may go directly to any question you have already seen in the Reading section.\n\nClick on Continue to go on.");
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
        descriptionVo.setText("You have seen all of the questions in the Reading section. You may go back and review. As long as there is time remaining. You can check your work.\n\nClick on Return to go back to the last question.\n\nClick on Review to go to the Review screen.\n\nClick on Continue to go on to the next section of the test.\n\nOnce you leave the Reading section, you WILL NOT be able to return to it.");

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

    public static TestViewVo initRawListeningMaterialViewVo(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_SECTION_DIRECTIONS);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText("Reading Section Directions");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText("In this part of the Reading section, you will read 3 passages. In the test you will have 60 minutes to read the passage and answer the questions.\n\nMost questions are worth 1 point but the last question in each set is worth more than 1 point. The directions indicate how many points you may receive.\n\nSome passages include a word or phrase that is underlined in blue. Click on the word or phrase to see a definition or an explanation.\n\nWhen you want to move to the next question. click on Next. You may skip questions and go back to them later if you want to return to previous questions. click on Back.\n\nYou can click on Review at any time and the review screen will show you which questions you have answered and which you have not answered. From this review screen, you may go directly to any question you have already seen in the Reading section.\n\nClick on Continue to go on.");
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
}
