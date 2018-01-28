package com.mocktpo.vo;

import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Logger */

    protected static final Logger logger = LogManager.getLogger();

    /* Properties */

    private List<TestViewVo> viewVos = new ArrayList<>();

    private String tid;
    private int tagId;
    private String title;
    private int stars;
    private String creator;
    private long createdTime;
    private long updatedTime;
    private double version;
    private int status;

    public TestViewVo getViewVo(int viewId) {
        for (TestViewVo v : viewVos) {
            if (v.getViewId() == viewId) {
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

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /*
     * ==================================================
     *
     * Customized Methods
     *
     * ==================================================
     */

    public int findTotalQuestionCountInSection(int sectionType) {
        int count = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (vo.getSectionType() == sectionType && vo.isAnswerable()) {
                count++;
            }
        }
        return count;
    }

    public int findTotalQuestionCountInListeningSectionAndGroup(int sectionType, int listeningGroupId) {
        int count = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (vo.getSectionType() == sectionType && vo.getListeningGroupId() == listeningGroupId && vo.isAnswerable()) {
                count++;
            }
        }
        return count;
    }

    public int findNextViewIdWhileTimeOut(int viewId) {
        TestViewVo vo = this.getViewVo(viewId);
        int nextViewId = viewId;
        switch (vo.getSectionType()) {
            case ST.SECTION_TYPE_NONE:
                break;
            case ST.SECTION_TYPE_READING:
                nextViewId = findFirstViewIdByViewType(VT.VIEW_TYPE_LISTENING_HEADSET_ON);
                break;
            case ST.SECTION_TYPE_LISTENING:
                switch (vo.getListeningGroupId()) {
                    case 1:
                        nextViewId = findFirstViewIdByViewType(VT.VIEW_TYPE_LISTENING_DIRECTIONS);
                        break;
                    case 2:
                        nextViewId = findFirstViewIdByViewType(VT.VIEW_TYPE_SPEAKING_HEADSET_ON);
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

    public int findFirstViewIdByViewType(int viewType) {
        int viewId = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (vo.getViewType() == viewType) {
                viewId = vo.getViewId();
            }
        }
        return viewId;
    }

    public int findTestEndViewId() {
        int endViewId = 0;
        for (TestViewVo vo : this.getViewVos()) {
            if (vo.getViewType() == VT.VIEW_TYPE_TEST_END) {
                endViewId = vo.getViewId();
            }
        }
        return endViewId;
    }

    public int findTotalViewCount(boolean readingSelected, boolean listeningSelected, boolean speakingSelected, boolean writingSelected) {
        List<TestViewVo> viewVos = this.getViewVos();
        int count = 0;
        for (TestViewVo viewVo : viewVos) {
            int sectionType = viewVo.getSectionType();
            if ((readingSelected && sectionType == ST.SECTION_TYPE_READING) ||
                    (listeningSelected && sectionType == ST.SECTION_TYPE_LISTENING) ||
                    (speakingSelected && sectionType == ST.SECTION_TYPE_SPEAKING) ||
                    (writingSelected && sectionType == ST.SECTION_TYPE_WRITING) ||
                    (sectionType == ST.SECTION_TYPE_NONE)) {
                count++;
            }
        }
        logger.info("Total view count: {}", count);
        return count;
    }
}
