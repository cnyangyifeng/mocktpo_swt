package com.mocktpo.util;

import com.mocktpo.vo.StyleRangeVo;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestSchemaVo;
import com.mocktpo.vo.TestViewVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSchemaUtils {

    private TestSchemaUtils() {
    }

    public static void generate() {
        TestSchemaVo testSchemaVo = new TestSchemaVo();
        testSchemaVo.setTid(2);
        testSchemaVo.setTitle("TPO 2");
        testSchemaVo.setStars(5);
        testSchemaVo.setViewVos(initViewVos());
        ConfigUtils.save("tpo2", testSchemaVo);
    }

    private static List<TestViewVo> initViewVos() {
        List<TestViewVo> viewVos = new ArrayList<TestViewVo>();
        viewVos.add(initTestIntroView(1));
        viewVos.add(initGeneralTestInfoView(2));
        // viewVos.add(initReadingSectionDirectionsView(3));
        viewVos.add(initBreakPointView(3));
        viewVos.add(initTestEndView(4));
        return viewVos;
    }

    /*
     * ==================================================
     *
     * General View Types
     *
     * ==================================================
     */

    private static TestViewVo initTestIntroView(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(1);
        viewVo.setViewTypeName("Test Introduction");
        viewVo.setSectionType(0);
        viewVo.setSectionTypeName("");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText("EDUCATIONAL TESTING SERVICE, ETS, the ETS logo, TOEFL and TOEFL iBT are registered trademarks of Educational Testing Service (ETS) in the United States and other countries.\n\nClick on Continue to go on.");
        List<StyleRangeVo> descriptionStylesVo = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(183, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStylesVo);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    private static TestViewVo initGeneralTestInfoView(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(2);
        viewVo.setViewTypeName("General Test Information");
        viewVo.setSectionType(0);
        viewVo.setSectionTypeName("");

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText("General Test Information");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText("This test measures your ability to use English in an academic context. There are 4 sections.\n\nIn the Reading section, you will read several passages and answer questions about them.\n\nIn the Listening section, you will hear several conversations and lectures and answer questions about them.\n\nIn the Speaking section, you will answer 6 questions. Some of the questions ask you to speak about your own experience. Other questions ask you to speak about lectures and reading passages.\n\nIn the Writing section, you will answer 2 questions. The first question asks you to write about the relationship between a lecture you will hear and a passage you will read. The second question asks you to write an essay about a topic of general interest based on your experience.\n\nDuring this practice test, you may click Pause icon at any time. This will stop the test until you decide to continue. You may continue the test in a few minutes, or at any time during the period that your test is activated.\n\nThere will be directions for each section which explain how to answer the questions in that section.\n\nYou should work quickly but carefully on the Reading and Listening questions. Some questions are more difficult than others, but try to answer every one to the best of your ability. If you are not sure of the answer to a question, make the best guess that you can. The questions that you answer by speaking and by writing are each separately timed. Try to answer every one of these questions as completely as possible in the time allowed.\n\nClick on Continue to go on.");
        List<StyleRangeVo> descriptionStylesVo = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(101, 7, 1, 0, 0, false, null));
            add(new StyleRangeVo(190, 9, 1, 0, 0, false, null));
            add(new StyleRangeVo(299, 8, 1, 0, 0, false, null));
            add(new StyleRangeVo(490, 7, 1, 0, 0, false, null));
            add(new StyleRangeVo(806, 5, 1, 0, 0, false, null));
            add(new StyleRangeVo(1542, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStylesVo);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("heading", headingVo);
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    private static TestViewVo initBreakPointView(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(3);
        viewVo.setViewTypeName("Break Point");
        viewVo.setSectionType(0);
        viewVo.setSectionTypeName("");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText("You may now take a break. In an actual test there is a ten-minute break at this point.\n\nClick on Continue when you are ready to go on to the next section.\n\nIf you do not wish to take a break, click on Continue now.");
        List<StyleRangeVo> descriptionStylesVo = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(97, 8, 1, 0, 0, false, null));
            add(new StyleRangeVo(201, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStylesVo);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    private static TestViewVo initTestEndView(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(4);
        viewVo.setViewTypeName("Test End");
        viewVo.setSectionType(0);
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

    private static TestViewVo initReadingSectionDirectionsView(int viewId) {
        TestViewVo viewVo = new TestViewVo();
        viewVo.setViewId(viewId);
        viewVo.setViewType(11);
        viewVo.setViewTypeName("Reading Section Directions");
        viewVo.setSectionType(1);
        viewVo.setSectionTypeName("Reading");

        final StyledTextVo headingVo = new StyledTextVo();
        headingVo.setText("Reading Section Directions");

        final StyledTextVo descriptionVo = new StyledTextVo();
        descriptionVo.setText("In this part of the Reading section, you will read 3 passages. In the test you will have 60 minutes to read the passage and answer the questions.\n\nMost questions are worth 1 point but the last question in each set is worth more than 1 point. The directions indicate how many points you may receive.\n\nSome passages include a word or phrase that is underlined in blue. Click on the word or phrase to see a definition or an explanation.\n\nWhen you want to move to the next question. click on Next. You may skip questions and go back to them later if you want to return to previous questions. click on Back.\n\nYou can click on Review at any time and the review screen will show you which questions you have answered and which you have not answered. From this review screen, you may go directly to any question you have already seen in the Reading section.\n\nClick on Continue to go on.");
        List<StyleRangeVo> descriptionStylesVo = new ArrayList<StyleRangeVo>() {{
            add(new StyleRangeVo(51, 10, 1, 0, 0, false, null));
            add(new StyleRangeVo(89, 10, 1, 0, 0, false, null));
            add(new StyleRangeVo(347, 10, 1, 0, 0, false, null));
            add(new StyleRangeVo(488, 4, 1, 0, 0, false, null));
            add(new StyleRangeVo(597, 4, 1, 0, 0, false, null));
            add(new StyleRangeVo(621, 6, 1, 0, 0, false, null));
            add(new StyleRangeVo(860, 8, 1, 0, 0, false, null));
        }};
        descriptionVo.setStyles(descriptionStylesVo);

        Map<String, StyledTextVo> body = new HashMap<String, StyledTextVo>() {{
            put("heading", headingVo);
            put("description", descriptionVo);
        }};
        viewVo.setBody(body);

        return viewVo;
    }

    public static void main(String[] args) {
        TestSchemaUtils.generate();
    }
}
