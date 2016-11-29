package com.mocktpo.util;

import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.vo.TestSchemaVo;
import com.mocktpo.vo.TestViewVo;

public class TestSchemaUtils {

    private TestSchemaUtils() {
    }

    public static int getTotalQuestionCountInSection(TestSchemaVo vo, int sectionType) {

        int count = 0;

        for (TestViewVo tvv : vo.getViews()) {
            if (tvv.getSectionType() == sectionType && tvv.isWithQuestion()) {
                // TODO handles sub section type
                count++;
            }
        }

        return count;
    }

    public static int getNextViewIdWhileTimeOut(TestSchemaVo vo, int viewId) {

        TestViewVo tvv = vo.getView(viewId);

        int nextViewId = viewId;

        switch (tvv.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                // TODO handles next timed view id
                break;
            case ST.SECTION_TYPE_LISTENING:
                // TODO handles next timed view id
                break;
            case ST.SECTION_TYPE_READING:
                nextViewId = getFirstViewIdByViewType(vo, VT.VIEW_TYPE_HEADSET);
                break;
            case ST.SECTION_TYPE_SPEAKING:
                // TODO handles next timed view id
                break;
            case ST.SECTION_TYPE_WRITING:
                // TODO handles next timed view id
                break;
        }

        return nextViewId;
    }

    public static int getFirstViewIdByViewType(TestSchemaVo vo, int viewType) {

        int viewId = 0;

        for (TestViewVo tvv : vo.getViews()) {
            if (viewType == tvv.getViewType()) {
                viewId = tvv.getViewId();
            }
        }

        return viewId;
    }
}
