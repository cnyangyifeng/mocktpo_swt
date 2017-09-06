package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.vo.StyleRangeVo;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class TestViewUtils {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    private TestViewUtils() {
    }

//    private static List<TestViewVo> initViewVos() {
//        List<TestViewVo> viewVos = new ArrayList<TestViewVo>();
//        int viewId = 0;
//        viewVos.add(initTestIntroView(++viewId));
//        viewVos.add(initGeneralTestInfoView(++viewId));
//        int questionNumber = 0;
//        viewVos.add(initReadingSectionDirectionsView(++viewId));
//        viewVos.add(initReadingPassageView(++viewId, schema.getString("RP1H"), schema.getString("RP1P"), true));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q1P"), Integer.parseInt(schema.getString("RP1Q1PO")), highlight(schema.getString("RP1Q1PS")), schema.getString("RP1Q1"), highlight(schema.getString("RP1Q1S")), schema.getString("RP1Q1CA"), schema.getString("RP1Q1CB"), schema.getString("RP1Q1CC"), schema.getString("RP1Q1CD"), schema.getString("RP1Q1F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q2P"), Integer.parseInt(schema.getString("RP1Q2PO")), highlight(schema.getString("RP1Q2PS")), schema.getString("RP1Q2"), highlight(schema.getString("RP1Q2S")), schema.getString("RP1Q2CA"), schema.getString("RP1Q2CB"), schema.getString("RP1Q2CC"), schema.getString("RP1Q2CD"), schema.getString("RP1Q2F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q3P"), Integer.parseInt(schema.getString("RP1Q3PO")), highlight(schema.getString("RP1Q3PS")), schema.getString("RP1Q3"), highlight(schema.getString("RP1Q3S")), schema.getString("RP1Q3CA"), schema.getString("RP1Q3CB"), schema.getString("RP1Q3CC"), schema.getString("RP1Q3CD"), schema.getString("RP1Q3F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q4P"), Integer.parseInt(schema.getString("RP1Q4PO")), highlight(schema.getString("RP1Q4PS")), schema.getString("RP1Q4"), highlight(schema.getString("RP1Q4S")), schema.getString("RP1Q4CA"), schema.getString("RP1Q4CB"), schema.getString("RP1Q4CC"), schema.getString("RP1Q4CD"), schema.getString("RP1Q4F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q5P"), Integer.parseInt(schema.getString("RP1Q5PO")), highlight(schema.getString("RP1Q5PS")), schema.getString("RP1Q5"), highlight(schema.getString("RP1Q5S")), schema.getString("RP1Q5CA"), schema.getString("RP1Q5CB"), schema.getString("RP1Q5CC"), schema.getString("RP1Q5CD"), schema.getString("RP1Q5F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q6P"), Integer.parseInt(schema.getString("RP1Q6PO")), highlight(schema.getString("RP1Q6PS")), schema.getString("RP1Q6"), highlight(schema.getString("RP1Q6S")), schema.getString("RP1Q6CA"), schema.getString("RP1Q6CB"), schema.getString("RP1Q6CC"), schema.getString("RP1Q6CD"), schema.getString("RP1Q6F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q7P"), Integer.parseInt(schema.getString("RP1Q7PO")), highlight(schema.getString("RP1Q7PS")), schema.getString("RP1Q7"), highlight(schema.getString("RP1Q7S")), schema.getString("RP1Q7CA"), schema.getString("RP1Q7CB"), schema.getString("RP1Q7CC"), schema.getString("RP1Q7CD"), schema.getString("RP1Q7F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q8P"), Integer.parseInt(schema.getString("RP1Q8PO")), highlight(schema.getString("RP1Q8PS")), schema.getString("RP1Q8"), highlight(schema.getString("RP1Q8S")), schema.getString("RP1Q8CA"), schema.getString("RP1Q8CB"), schema.getString("RP1Q8CC"), schema.getString("RP1Q8CD"), schema.getString("RP1Q8F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q9P"), Integer.parseInt(schema.getString("RP1Q9PO")), highlight(schema.getString("RP1Q9PS")), schema.getString("RP1Q9"), highlight(schema.getString("RP1Q9S")), schema.getString("RP1Q9CA"), schema.getString("RP1Q9CB"), schema.getString("RP1Q9CC"), schema.getString("RP1Q9CD"), schema.getString("RP1Q9F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q10P"), Integer.parseInt(schema.getString("RP1Q10PO")), highlight(schema.getString("RP1Q10PS")), schema.getString("RP1Q10"), highlight(schema.getString("RP1Q10S")), schema.getString("RP1Q10CA"), schema.getString("RP1Q10CB"), schema.getString("RP1Q10CC"), schema.getString("RP1Q10CD"), schema.getString("RP1Q10F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q11P"), Integer.parseInt(schema.getString("RP1Q11PO")), highlight(schema.getString("RP1Q11PS")), schema.getString("RP1Q11"), highlight(schema.getString("RP1Q11S")), schema.getString("RP1Q11CA"), schema.getString("RP1Q11CB"), schema.getString("RP1Q11CC"), schema.getString("RP1Q11CD"), schema.getString("RP1Q11F")));
//        viewVos.add(initReadingInsertTextQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q12P"), Integer.parseInt(schema.getString("RP1Q12PO")), schema.getString("RP1Q12IT"), schema.getString("RP1Q12IPA"), schema.getString("RP1Q12IPB"), schema.getString("RP1Q12IPC"), schema.getString("RP1Q12IPD")));
//        viewVos.add(initReadingProseSummaryQuestionView(++viewId, ++questionNumber, schema.getString("RP1H"), schema.getString("RP1Q13P"), schema.getString("RP1Q13"), schema.getString("RP1Q13CA"), schema.getString("RP1Q13CB"), schema.getString("RP1Q13CC"), schema.getString("RP1Q13CD"), schema.getString("RP1Q13CE"), schema.getString("RP1Q13CF")));
//        viewVos.add(initReadingPassageView(++viewId, schema.getString("RP2H"), schema.getString("RP2P"), false));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q1P"), Integer.parseInt(schema.getString("RP2Q1PO")), highlight(schema.getString("RP2Q1PS")), schema.getString("RP2Q1"), highlight(schema.getString("RP2Q1S")), schema.getString("RP2Q1CA"), schema.getString("RP2Q1CB"), schema.getString("RP2Q1CC"), schema.getString("RP2Q1CD"), schema.getString("RP2Q1F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q2P"), Integer.parseInt(schema.getString("RP2Q2PO")), highlight(schema.getString("RP2Q2PS")), schema.getString("RP2Q2"), highlight(schema.getString("RP2Q2S")), schema.getString("RP2Q2CA"), schema.getString("RP2Q2CB"), schema.getString("RP2Q2CC"), schema.getString("RP2Q2CD"), schema.getString("RP2Q2F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q3P"), Integer.parseInt(schema.getString("RP2Q3PO")), highlight(schema.getString("RP2Q3PS")), schema.getString("RP2Q3"), highlight(schema.getString("RP2Q3S")), schema.getString("RP2Q3CA"), schema.getString("RP2Q3CB"), schema.getString("RP2Q3CC"), schema.getString("RP2Q3CD"), schema.getString("RP2Q3F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q4P"), Integer.parseInt(schema.getString("RP2Q4PO")), highlight(schema.getString("RP2Q4PS")), schema.getString("RP2Q4"), highlight(schema.getString("RP2Q4S")), schema.getString("RP2Q4CA"), schema.getString("RP2Q4CB"), schema.getString("RP2Q4CC"), schema.getString("RP2Q4CD"), schema.getString("RP2Q4F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q5P"), Integer.parseInt(schema.getString("RP2Q5PO")), highlight(schema.getString("RP2Q5PS")), schema.getString("RP2Q5"), highlight(schema.getString("RP2Q5S")), schema.getString("RP2Q5CA"), schema.getString("RP2Q5CB"), schema.getString("RP2Q5CC"), schema.getString("RP2Q5CD"), schema.getString("RP2Q5F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q6P"), Integer.parseInt(schema.getString("RP2Q6PO")), highlight(schema.getString("RP2Q6PS")), schema.getString("RP2Q6"), highlight(schema.getString("RP2Q6S")), schema.getString("RP2Q6CA"), schema.getString("RP2Q6CB"), schema.getString("RP2Q6CC"), schema.getString("RP2Q6CD"), schema.getString("RP2Q6F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q7P"), Integer.parseInt(schema.getString("RP2Q7PO")), highlight(schema.getString("RP2Q7PS")), schema.getString("RP2Q7"), highlight(schema.getString("RP2Q7S")), schema.getString("RP2Q7CA"), schema.getString("RP2Q7CB"), schema.getString("RP2Q7CC"), schema.getString("RP2Q7CD"), schema.getString("RP2Q7F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q8P"), Integer.parseInt(schema.getString("RP2Q8PO")), highlight(schema.getString("RP2Q8PS")), schema.getString("RP2Q8"), highlight(schema.getString("RP2Q8S")), schema.getString("RP2Q8CA"), schema.getString("RP2Q8CB"), schema.getString("RP2Q8CC"), schema.getString("RP2Q8CD"), schema.getString("RP2Q8F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q9P"), Integer.parseInt(schema.getString("RP2Q9PO")), highlight(schema.getString("RP2Q9PS")), schema.getString("RP2Q9"), highlight(schema.getString("RP2Q9S")), schema.getString("RP2Q9CA"), schema.getString("RP2Q9CB"), schema.getString("RP2Q9CC"), schema.getString("RP2Q9CD"), schema.getString("RP2Q9F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q10P"), Integer.parseInt(schema.getString("RP2Q10PO")), highlight(schema.getString("RP2Q10PS")), schema.getString("RP2Q10"), highlight(schema.getString("RP2Q10S")), schema.getString("RP2Q10CA"), schema.getString("RP2Q10CB"), schema.getString("RP2Q10CC"), schema.getString("RP2Q10CD"), schema.getString("RP2Q10F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q11P"), Integer.parseInt(schema.getString("RP2Q11PO")), highlight(schema.getString("RP2Q11PS")), schema.getString("RP2Q11"), highlight(schema.getString("RP2Q11S")), schema.getString("RP2Q11CA"), schema.getString("RP2Q11CB"), schema.getString("RP2Q11CC"), schema.getString("RP2Q11CD"), schema.getString("RP2Q11F")));
//        viewVos.add(initReadingInsertTextQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q12P"), Integer.parseInt(schema.getString("RP2Q12PO")), schema.getString("RP2Q12IT"), schema.getString("RP2Q12IPA"), schema.getString("RP2Q12IPB"), schema.getString("RP2Q12IPC"), schema.getString("RP2Q12IPD")));
//        viewVos.add(initReadingProseSummaryQuestionView(++viewId, ++questionNumber, schema.getString("RP2H"), schema.getString("RP2Q13P"), schema.getString("RP2Q13"), schema.getString("RP2Q13CA"), schema.getString("RP2Q13CB"), schema.getString("RP2Q13CC"), schema.getString("RP2Q13CD"), schema.getString("RP2Q13CE"), schema.getString("RP2Q13CF")));
//        viewVos.add(initReadingPassageView(++viewId, schema.getString("RP3H"), schema.getString("RP3P"), true));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q1P"), Integer.parseInt(schema.getString("RP3Q1PO")), highlight(schema.getString("RP3Q1PS")), schema.getString("RP3Q1"), highlight(schema.getString("RP3Q1S")), schema.getString("RP3Q1CA"), schema.getString("RP3Q1CB"), schema.getString("RP3Q1CC"), schema.getString("RP3Q1CD"), schema.getString("RP3Q1F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q2P"), Integer.parseInt(schema.getString("RP3Q2PO")), highlight(schema.getString("RP3Q2PS")), schema.getString("RP3Q2"), highlight(schema.getString("RP3Q2S")), schema.getString("RP3Q2CA"), schema.getString("RP3Q2CB"), schema.getString("RP3Q2CC"), schema.getString("RP3Q2CD"), schema.getString("RP3Q2F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q3P"), Integer.parseInt(schema.getString("RP3Q3PO")), highlight(schema.getString("RP3Q3PS")), schema.getString("RP3Q3"), highlight(schema.getString("RP3Q3S")), schema.getString("RP3Q3CA"), schema.getString("RP3Q3CB"), schema.getString("RP3Q3CC"), schema.getString("RP3Q3CD"), schema.getString("RP3Q3F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q4P"), Integer.parseInt(schema.getString("RP3Q4PO")), highlight(schema.getString("RP3Q4PS")), schema.getString("RP3Q4"), highlight(schema.getString("RP3Q4S")), schema.getString("RP3Q4CA"), schema.getString("RP3Q4CB"), schema.getString("RP3Q4CC"), schema.getString("RP3Q4CD"), schema.getString("RP3Q4F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q5P"), Integer.parseInt(schema.getString("RP3Q5PO")), highlight(schema.getString("RP3Q5PS")), schema.getString("RP3Q5"), highlight(schema.getString("RP3Q5S")), schema.getString("RP3Q5CA"), schema.getString("RP3Q5CB"), schema.getString("RP3Q5CC"), schema.getString("RP3Q5CD"), schema.getString("RP3Q5F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q6P"), Integer.parseInt(schema.getString("RP3Q6PO")), highlight(schema.getString("RP3Q6PS")), schema.getString("RP3Q6"), highlight(schema.getString("RP3Q6S")), schema.getString("RP3Q6CA"), schema.getString("RP3Q6CB"), schema.getString("RP3Q6CC"), schema.getString("RP3Q6CD"), schema.getString("RP3Q6F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q7P"), Integer.parseInt(schema.getString("RP3Q7PO")), highlight(schema.getString("RP3Q7PS")), schema.getString("RP3Q7"), highlight(schema.getString("RP3Q7S")), schema.getString("RP3Q7CA"), schema.getString("RP3Q7CB"), schema.getString("RP3Q7CC"), schema.getString("RP3Q7CD"), schema.getString("RP3Q7F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q8P"), Integer.parseInt(schema.getString("RP3Q8PO")), highlight(schema.getString("RP3Q8PS")), schema.getString("RP3Q8"), highlight(schema.getString("RP3Q8S")), schema.getString("RP3Q8CA"), schema.getString("RP3Q8CB"), schema.getString("RP3Q8CC"), schema.getString("RP3Q8CD"), schema.getString("RP3Q8F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q9P"), Integer.parseInt(schema.getString("RP3Q9PO")), highlight(schema.getString("RP3Q9PS")), schema.getString("RP3Q9"), highlight(schema.getString("RP3Q9S")), schema.getString("RP3Q9CA"), schema.getString("RP3Q9CB"), schema.getString("RP3Q9CC"), schema.getString("RP3Q9CD"), schema.getString("RP3Q9F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q10P"), Integer.parseInt(schema.getString("RP3Q10PO")), highlight(schema.getString("RP3Q10PS")), schema.getString("RP3Q10"), highlight(schema.getString("RP3Q10S")), schema.getString("RP3Q10CA"), schema.getString("RP3Q10CB"), schema.getString("RP3Q10CC"), schema.getString("RP3Q10CD"), schema.getString("RP3Q10F")));
//        viewVos.add(initReadingQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q11P"), Integer.parseInt(schema.getString("RP3Q11PO")), highlight(schema.getString("RP3Q11PS")), schema.getString("RP3Q11"), highlight(schema.getString("RP3Q11S")), schema.getString("RP3Q11CA"), schema.getString("RP3Q11CB"), schema.getString("RP3Q11CC"), schema.getString("RP3Q11CD"), schema.getString("RP3Q11F")));
//        viewVos.add(initReadingInsertTextQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q12P"), Integer.parseInt(schema.getString("RP3Q12PO")), schema.getString("RP3Q12IT"), schema.getString("RP3Q12IPA"), schema.getString("RP3Q12IPB"), schema.getString("RP3Q12IPC"), schema.getString("RP3Q12IPD")));
//        viewVos.add(initReadingProseSummaryQuestionView(++viewId, ++questionNumber, schema.getString("RP3H"), schema.getString("RP3Q13P"), schema.getString("RP3Q13"), schema.getString("RP3Q13CA"), schema.getString("RP3Q13CB"), schema.getString("RP3Q13CC"), schema.getString("RP3Q13CD"), schema.getString("RP3Q13CE"), schema.getString("RP3Q13CF")));
//        viewVos.add(initReadingSectionEndView(++viewId));
//        viewVos.add(initBreakPointView(++viewId));
//        viewVos.add(initTestEndView(++viewId));
//        return viewVos;
//    }

    /*
     * ==================================================
     *
     * General View Types
     *
     * ==================================================
     */

    public static TestViewVo initTestIntroView(int viewId) {
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

    public static TestViewVo initGeneralTestInfoView(int viewId) {
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

    public static TestViewVo initBreakPointView(int viewId) {
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

    public static TestViewVo initTestEndView(int viewId) {
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

    public static TestViewVo initReadingSectionDirectionsView(int viewId) {
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
        viewVo.setBody(new HashMap<String, StyledTextVo>());

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
        viewVo.setBody(new HashMap<String, StyledTextVo>());

        return viewVo;
    }

    public static TestViewVo initReadingMultipleChoiceQuestionViewVo(int viewId, int questionNumberInSection, String heading, String passage, int passageOffset, final StyleRangeVo passageStyleVo, String question, final StyleRangeVo questionStyleVo, String choiceA, String choiceB, String choiceC, String choiceD, String footnote) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_MULTIPLE_CHOICE_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setPassageOffset(passageOffset);
        viewVo.setTimed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setQuestionNumberInSection(questionNumberInSection);
        viewVo.setAnswerable(true);

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText(heading);

        final StyledTextVo passageVo = new StyledTextVo();
        passageVo.setText(passage);
        if (passageStyleVo != null) {
            List<StyleRangeVo> passageStyleVos = new ArrayList<StyleRangeVo>() {{
                add(passageStyleVo);
            }};
            passageVo.setStyles(passageStyleVos);
        }

        final StyledTextVo questionVo = new StyledTextVo();
        questionVo.setText(question);
        if (questionStyleVo != null) {
            List<StyleRangeVo> questionStyleVos = new ArrayList<StyleRangeVo>() {{
                add(questionStyleVo);
            }};
            questionVo.setStyles(questionStyleVos);
        }

        final StyledTextVo choiceAVo = new StyledTextVo();
        choiceAVo.setText(choiceA);

        final StyledTextVo choiceBVo = new StyledTextVo();
        choiceBVo.setText(choiceB);

        final StyledTextVo choiceCVo = new StyledTextVo();
        choiceCVo.setText(choiceC);

        final StyledTextVo choiceDVo = new StyledTextVo();
        choiceDVo.setText(choiceD);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("heading", headingVo);
            put("passage", passageVo);
            put("question", questionVo);
            put("choiceA", choiceAVo);
            put("choiceB", choiceBVo);
            put("choiceC", choiceCVo);
            put("choiceD", choiceDVo);
        }};
        if (!StringUtils.isEmpty(footnote)) {
            final StyledTextVo footnoteVo = new StyledTextVo();
            footnoteVo.setText(footnote);
            body.put("footnote", footnoteVo);
        }
        viewVo.setBody(body);

        return viewVo;
    }

    public static void updatePassageOffset(TestViewVo viewVo) {
        int passageOffset = 0;
        String text = viewVo.getStyledTextContent("passage");
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
        viewVo.setPassageOffset(passageOffset);
    }

    public static TestViewVo initRawReadingInsertTextQuestionView(int viewId) {
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

    public static TestViewVo initReadingInsertTextQuestionView(int viewId, int questionNumberInSection, String heading, String passage, int passageOffset, String insertText, final String insertionPointA, final String insertionPointB, final String insertionPointC, final String insertionPointD) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setPassageOffset(passageOffset);
        viewVo.setTimed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setQuestionNumberInSection(questionNumberInSection);
        viewVo.setAnswerable(true);

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText(heading);

        final StyledTextVo passageVo = new StyledTextVo();
        passageVo.setText(passage);

        final StyledTextVo questionVo = new StyledTextVo();
        questionVo.setText("Look at the four squares [ \u2588 ] that indicate where the following sentence could be added to the passage.");

        final StyledTextVo insertTextVo = new StyledTextVo();
        insertTextVo.setText(insertText);

        final StyledTextVo insertionPointAVo = new StyledTextVo();
        insertionPointAVo.setText("\u2588");
        List<StyleRangeVo> insertionPointAStyleVos = new ArrayList<StyleRangeVo>() {{
            add(insertionPoint(insertionPointA));
        }};
        insertionPointAVo.setStyles(insertionPointAStyleVos);

        final StyledTextVo insertionPointBVo = new StyledTextVo();
        insertionPointBVo.setText("\u2588");
        List<StyleRangeVo> insertionPointBStyleVos = new ArrayList<StyleRangeVo>() {{
            add(insertionPoint(insertionPointB));
        }};
        insertionPointBVo.setStyles(insertionPointBStyleVos);

        final StyledTextVo insertionPointCVo = new StyledTextVo();
        insertionPointCVo.setText("\u2588");
        List<StyleRangeVo> insertionPointCStyleVos = new ArrayList<StyleRangeVo>() {{
            add(insertionPoint(insertionPointC));
        }};
        insertionPointCVo.setStyles(insertionPointCStyleVos);

        final StyledTextVo insertionPointDVo = new StyledTextVo();
        insertionPointDVo.setText("\u2588");
        List<StyleRangeVo> insertionPointDStyleVos = new ArrayList<StyleRangeVo>() {{
            add(insertionPoint(insertionPointD));
        }};
        insertionPointDVo.setStyles(insertionPointDStyleVos);

        final StyledTextVo footnoteVo = new StyledTextVo();
        footnoteVo.setText("Where would the sentence best fit? Click on a square to add the sentence to the passage.");

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("heading", headingVo);
            put("passage", passageVo);
            put("question", questionVo);
            put("insertText", insertTextVo);
            put("insertionPointA", insertionPointAVo);
            put("insertionPointB", insertionPointBVo);
            put("insertionPointC", insertionPointCVo);
            put("insertionPointD", insertionPointDVo);
            put("footnote", footnoteVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static TestViewVo initRawReadingProseSummaryQuestionView(int viewId) {
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

    public static TestViewVo initReadingProseSummaryQuestionView(int viewId, int questionNumberInSection, String heading, String passage, String question, String choiceA, String choiceB, String choiceC, String choiceD, String choiceE, String choiceF) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setTimed(true);
        viewVo.setQuestionCaptionVisible(true);
        viewVo.setQuestionNumberInSection(questionNumberInSection);
        viewVo.setAnswerable(true);

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText(heading);

        final StyledTextVo passageVo = new StyledTextVo();
        passageVo.setText(passage);

        final StyledTextVo directionsVo = new StyledTextVo();
        directionsVo.setText("Directions: An introductory sentence for a brief summary of the passage is provided below. Complete the summary by selecting the THREE answer choices that express the most important ideas in the passage. Some sentences do not belong in the summary because they express ideas that are not presented in the passage or are minor ideas in the passage. This question is worth 2 points.");
        List<StyleRangeVo> directionsStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(0, 11, 1, 0, 0, false, null));
            add(new StyleRangeVo(348, 32, 1, 0, 0, false, null));
        }};
        directionsVo.setStyles(directionsStyleVos);

        final StyledTextVo tipsVo = new StyledTextVo();
        tipsVo.setText("Drag your answer choices to the spaces where they belong. To remove an answer choice, click on it.\nTo review the passage, click VIEW TEXT.");
        List<StyleRangeVo> tipsStyleVos = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(128, 9, 1, 0, 0, false, null));
        }};
        tipsVo.setStyles(tipsStyleVos);

        final StyledTextVo questionVo = new StyledTextVo();
        questionVo.setText(question);

        final StyledTextVo choiceAVo = new StyledTextVo();
        choiceAVo.setText(choiceA);

        final StyledTextVo choiceBVo = new StyledTextVo();
        choiceBVo.setText(choiceB);

        final StyledTextVo choiceCVo = new StyledTextVo();
        choiceCVo.setText(choiceC);

        final StyledTextVo choiceDVo = new StyledTextVo();
        choiceDVo.setText(choiceD);

        final StyledTextVo choiceEVo = new StyledTextVo();
        choiceEVo.setText(choiceE);

        final StyledTextVo choiceFVo = new StyledTextVo();
        choiceFVo.setText(choiceF);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("heading", headingVo);
            put("passage", passageVo);
            put("directions", directionsVo);
            put("tips", tipsVo);
            put("question", questionVo);
            put("choiceA", choiceAVo);
            put("choiceB", choiceBVo);
            put("choiceC", choiceCVo);
            put("choiceD", choiceDVo);
            put("choiceE", choiceEVo);
            put("choiceF", choiceFVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static TestViewVo initReadingSectionEndView(int viewId) {
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

    private static StyleRangeVo insertionPoint(String text) {
        StyleRangeVo styleRangeVo = new StyleRangeVo();
        if (!StringUtils.isEmpty(text)) {
            styleRangeVo.setStart(Integer.parseInt(text));
            styleRangeVo.setLength(0);
            styleRangeVo.setFontStyle(0);
            styleRangeVo.setForeground(0);
            styleRangeVo.setBackground(0);
            styleRangeVo.setUnderline(false);
            styleRangeVo.setImage(null);
        }
        return styleRangeVo;
    }
}
