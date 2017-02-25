package com.mocktpo.vo;

import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestSchemaVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int INITIAL_CACHE_SIZE = 64;

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    /* Properties */

    private List<TestViewVo> viewVos = new ArrayList<TestViewVo>(INITIAL_CACHE_SIZE);

    private int tid;
    private String title;
    private int stars;

    public TestViewVo getViewVo(int viewId) {
        for (TestViewVo v : viewVos) {
            if (viewId == v.getViewId()) {
                return v;
            }
        }
        return null;
    }

    public List<TestViewVo> getViewVos() {
        return viewVos;
    }

    public void setViewVos(List<TestViewVo> viewVos) {
        this.viewVos = viewVos;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    /*
     * ==================================================
     *
     * Customized Methods
     *
     * ==================================================
     */

    public int getTotalQuestionCountInSection(int sectionType) {
        int count = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (vo.getSectionType() == sectionType && vo.isAnswerable()) {
                count++;
            }
        }
        return count;
    }

    public int getTotalQuestionCountInListeningSection(int sectionType, int listeningGroupId) {
        int count = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (vo.getSectionType() == sectionType && vo.getListeningGroupId() == listeningGroupId && vo.isAnswerable()) {
                count++;
            }
        }
        return count;
    }

    public int getNextViewIdWhileTimeOut(int viewId) {
        TestViewVo vo = this.getViewVo(viewId);
        int nextViewId = viewId;
        switch (vo.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                nextViewId = getFirstViewIdByViewType(VT.VIEW_TYPE_LISTENING_HEADSET_ON);
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (vo.getListeningGroupId()) {
                    case 1:
                        nextViewId = getFirstViewIdByViewType(VT.VIEW_TYPE_LISTENING_DIRECTIONS);
                        break;
                    case 2:
                        nextViewId = getFirstViewIdByViewType(VT.VIEW_TYPE_SPEAKING_HEADSET_ON);
                        break;
                }
                break;
            case ST.SECTION_TYPE_SPEAKING:
                nextViewId = viewId + 1;
                break;
            case ST.SECTION_TYPE_WRITING:
                switch (vo.getViewType()) {
                    case VT.VIEW_TYPE_WRITING_READING_PASSAGE:
                        nextViewId = viewId + 1;
                        break;
                    case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK:
                        nextViewId = viewId + 2;
                        break;
                    case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK_END:
                        nextViewId = viewId + 1;
                        break;
                    case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK:
                        nextViewId = viewId + 2;
                        break;
                    case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK_END:
                        nextViewId = viewId + 1;
                        break;
                }
                break;
        }
        return nextViewId;
    }

    public int getFirstViewIdByViewType(int viewType) {
        int viewId = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (viewType == vo.getViewType()) {
                viewId = vo.getViewId();
            }
        }
        return viewId;
    }

    public int getTestEndViewId() {
        int endViewId = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK_END == vo.getViewType()) {
                endViewId = vo.getViewId();
            }
        }
        return endViewId;
    }

    public int getTotalViewCount(boolean readingSelected, boolean listeningSelected, boolean speakingSelected, boolean writingSelected) {
        List<TestViewVo> viewVos = this.getViewVos();
        int count = 0;
        for (TestViewVo viewVo : viewVos) {
            int sectionType = viewVo.getSectionType();
            if ((readingSelected && ST.SECTION_TYPE_READING == sectionType) ||
                    (listeningSelected && ST.SECTION_TYPE_LISTENING == sectionType) ||
                    (speakingSelected && ST.SECTION_TYPE_SPEAKING == sectionType) ||
                    (writingSelected && ST.SECTION_TYPE_WRITING == sectionType) ||
                    (ST.SECTION_TYPE_NONE == sectionType)) {
                count++;
            }
        }
        logger.info("Total view count: {}", count);
        return count;
    }

    @Override
    public String toString() {
        return "{\ntid:" + this.getTid() + ",\ntitle:" + this.getTitle() + "\n,stars:" + this.getStars() + "\n}";
    }
}
