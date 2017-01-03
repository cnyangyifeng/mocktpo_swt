package com.mocktpo.util;

import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.vo.TestSchemaVo;
import com.mocktpo.vo.TestViewVo;

public class TestSchemaUtils {

    private TestSchemaUtils() {
    }

    public static int getTotalQuestionCountInSectionAndGroup(TestSchemaVo testSchema, int sectionType, int groupId) {

        int count = 0;

        switch (sectionType) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                for (TestViewVo vo : testSchema.getViews()) {
                    if (vo.getSectionType() == sectionType && (vo.getViewType() == VT.VIEW_TYPE_READING_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_CATEGORY_CHART_QUESTION)) {
                        count++;
                    }
                }
                break;
            case ST.SECTION_TYPE_LISTENING:
                for (TestViewVo vo : testSchema.getViews()) {
                    if (vo.getSectionType() == sectionType && vo.getGroupId() == groupId && (vo.getViewType() == VT.VIEW_TYPE_LISTENING_QUESTION || vo.getViewType() == VT.VIEW_TYPE_LISTENING_MULTIPLE_ANSWERS_QUESTION || vo.getViewType() == VT.VIEW_TYPE_LISTENING_ORDER_EVENTS_QUESTION || vo.getViewType() == VT.VIEW_TYPE_LISTENING_MATCH_OBJECTS_QUESTION)) {
                        count++;
                    }
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                for (TestViewVo vo : testSchema.getViews()) {
                    if (vo.getSectionType() == sectionType && vo.getViewType() == VT.VIEW_TYPE_SPEAKING_TASK) {
                        count++;
                    }
                }
                break;
            case ST.SECTION_TYPE_WRITING:
                for (TestViewVo vo : testSchema.getViews()) {
                    if (vo.getSectionType() == sectionType && (vo.getViewType() == VT.VIEW_TYPE_INTEGRATED_WRITING_TASK || vo.getViewType() == VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK)) {
                        count++;
                    }
                }
                break;
        }

        return count;
    }

    public static int getNextViewIdWhileTimeOut(TestSchemaVo testSchema, int viewId) {

        TestViewVo vo = testSchema.getView(viewId);

        int nextViewId = viewId;

        switch (vo.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                nextViewId = getFirstViewIdByViewType(testSchema, VT.VIEW_TYPE_LISTENING_HEADSET_ON);
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (vo.getGroupId()) {
                    case 1:
                        nextViewId = getFirstViewIdByViewType(testSchema, VT.VIEW_TYPE_LISTENING_DIRECTIONS);
                        break;
                    case 2:
                        nextViewId = getFirstViewIdByViewType(testSchema, VT.VIEW_TYPE_SPEAKING_HEADSET_ON);
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                nextViewId = viewId + 1;
                break;
            case ST.SECTION_TYPE_WRITING:
                nextViewId = viewId + 1;
                break;
        }

        return nextViewId;
    }

    public static int getFirstViewIdByViewType(TestSchemaVo testSchema, int viewType) {

        int viewId = 0;

        for (TestViewVo tvv : testSchema.getViews()) {
            if (viewType == tvv.getViewType()) {
                viewId = tvv.getViewId();
            }
        }

        return viewId;
    }
}
