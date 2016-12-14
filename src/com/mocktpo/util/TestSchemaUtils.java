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

        for (TestViewVo vo : testSchema.getViews()) {
            if (vo.getSectionType() == sectionType && vo.isWithQuestion() && vo.getGroupId() == groupId) {
                count++;
            }
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
                nextViewId = getFirstViewIdByViewType(testSchema, VT.VIEW_TYPE_WRITING_HEADSET_ON);
                break;
            case ST.SECTION_TYPE_WRITING:
                // TODO handles next timed view id
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
