package com.mocktpo.pages;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.views.test.*;
import com.mocktpo.vo.TestSchemaVo;
import com.mocktpo.vo.TestViewVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class TestPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Stack */

    protected StackLayout stack;

    /* Properties */

    private TestSchemaVo testSchema;
    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestPage(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.userTestSession = userTestSession;
        this.testSchema = ConfigUtils.load(this.userTestSession.getFileAlias(), TestSchemaVo.class);
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        stack = new StackLayout();
        this.setLayout(stack);
    }

    /*
     * ==================================================
     *
     * Resume
     *
     * ==================================================
     */

    public void resume() {
        resume(this.userTestSession);
    }

    public void resume(UserTestSession userTestSession) {
        if (this.userTestSession.getTid() != userTestSession.getTid()) {
            this.testSchema = ConfigUtils.load(userTestSession.getFileAlias(), TestSchemaVo.class);
        }
        this.userTestSession = userTestSession;

        if (null != testSchema) {
            TestView testView = getLastTestView();
            if (null != testView) {
                stack.topControl = testView;
                this.layout();
            } else {
                MyApplication.get().getWindow().toReportPage(userTestSession);
            }
        }
    }

    private TestView getLastTestView() {
        int lastViewId = userTestSession.getLastViewId();
        if (testSchema.getTestEndViewId() < lastViewId) {
            return null;
        }
        TestViewVo viewVo = testSchema.getViewVo(lastViewId);

        if (ST.SECTION_TYPE_READING == viewVo.getSectionType() && !userTestSession.isReadingSelected()) {
            if (userTestSession.isListeningSelected()) {
                lastViewId = testSchema.getFirstViewIdByViewType(VT.VIEW_TYPE_LISTENING_HEADSET_ON);
                viewVo = testSchema.getViewVo(lastViewId);
                return getTestView(viewVo);
            } else {
                if (userTestSession.isSpeakingSelected()) {
                    lastViewId = testSchema.getFirstViewIdByViewType(VT.VIEW_TYPE_SPEAKING_HEADSET_ON);
                    viewVo = testSchema.getViewVo(lastViewId);
                    return getTestView(viewVo);
                } else {
                    if (userTestSession.isWritingSelected()) {
                        lastViewId = testSchema.getFirstViewIdByViewType(VT.VIEW_TYPE_WRITING_SECTION_DIRECTIONS);
                        viewVo = testSchema.getViewVo(lastViewId);
                        return getTestView(viewVo);
                    } else {
                        return null;
                    }
                }
            }
        }

        if (ST.SECTION_TYPE_LISTENING == viewVo.getSectionType() && !userTestSession.isListeningSelected()) {
            if (userTestSession.isSpeakingSelected()) {
                lastViewId = testSchema.getFirstViewIdByViewType(VT.VIEW_TYPE_SPEAKING_HEADSET_ON);
                viewVo = testSchema.getViewVo(lastViewId);
                return getTestView(viewVo);
            } else {
                if (userTestSession.isWritingSelected()) {
                    lastViewId = testSchema.getFirstViewIdByViewType(VT.VIEW_TYPE_WRITING_SECTION_DIRECTIONS);
                    viewVo = testSchema.getViewVo(lastViewId);
                    return getTestView(viewVo);
                } else {
                    return null;
                }
            }
        }

        if (ST.SECTION_TYPE_SPEAKING == viewVo.getSectionType() && !userTestSession.isSpeakingSelected()) {
            if (userTestSession.isWritingSelected()) {
                lastViewId = testSchema.getFirstViewIdByViewType(VT.VIEW_TYPE_WRITING_SECTION_DIRECTIONS);
                viewVo = testSchema.getViewVo(lastViewId);
                return getTestView(viewVo);
            } else {
                return null;
            }
        }

        if (ST.SECTION_TYPE_WRITING == viewVo.getSectionType() && !userTestSession.isWritingSelected()) {
            return null;
        }

        return getTestView(viewVo);
    }

    private TestView getTestView(TestViewVo viewVo) {
        PersistenceUtils.saveToView(userTestSession, viewVo.getViewId());
        int viewType = viewVo.getViewType();
        TestView tv = null;
        switch (viewType) {
            /* General View Types */
            case VT.VIEW_TYPE_TEST_INTRO:
                tv = new TestIntroView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_GENERAL_TEST_INFO:
                tv = new GeneralTestInfoView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_BREAK_POINT:
                tv = new BreakPointView(this, SWT.NONE);
                break;
            /* Reading Section View Types */
            case VT.VIEW_TYPE_READING_SECTION_DIRECTIONS:
                tv = new ReadingSectionDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_PASSAGE:
                tv = new ReadingPassageView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_QUESTION:
                tv = new ReadingQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION:
                tv = new ReadingInsertTextQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION:
                tv = new ReadingProseSummaryQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_CATEGORY_CHART_QUESTION:
                tv = new ReadingCategoryChartQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_SECTION_END:
                tv = new ReadingSectionEndView(this, SWT.NONE);
                break;
            /* Listening Section View Types */
            case VT.VIEW_TYPE_LISTENING_HEADSET_ON:
                tv = new ListeningHeadsetOnView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_CHANGING_VOLUME:
                tv = new ChangingVolumeView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_SECTION_DIRECTIONS:
                tv = new ListeningSectionDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_MATERIAL:
                tv = new ListeningMaterialView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_REPLAY:
                tv = new ListeningReplayView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_QUESTION:
                tv = new ListeningQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_MULTIPLE_ANSWERS_QUESTION:
                tv = new ListeningMultipleAnswersQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_ORDER_EVENTS_QUESTION:
                tv = new ListeningOrderEventsQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_MATCH_OBJECTS_QUESTION:
                tv = new ListeningMatchObjectsQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_SECTION_END:
                tv = new ListeningSectionEndView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_DIRECTIONS:
                tv = new ListeningDirectionsView(this, SWT.NONE);
                break;
            /* Speaking Section View Types */
            case VT.VIEW_TYPE_SPEAKING_HEADSET_ON:
                tv = new SpeakingHeadsetOnView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_ADJUSTING_MICROPHONE:
                tv = new AdjustingMicrophoneView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_SPEAKING_SECTION_DIRECTIONS:
                tv = new SpeakingSectionDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_SPEAKING_TASK_DIRECTIONS:
                tv = new SpeakingTaskDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_SPEAKING_TASK:
                tv = new SpeakingTaskView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_SPEAKING_READING_PASSAGE:
                tv = new SpeakingReadingPassageView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_SPEAKING_LISTENING_MATERIAL:
                tv = new SpeakingListeningMaterialView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_SPEAKING_SECTION_END:
                tv = new SpeakingSectionEndView(this, SWT.NONE);
                break;
            /* Writing Section View Types */
            case VT.VIEW_TYPE_WRITING_SECTION_DIRECTIONS:
                tv = new WritingSectionDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_INTEGRATED_WRITING_DIRECTIONS:
                tv = new IntegratedWritingDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_WRITING_READING_PASSAGE:
                tv = new WritingReadingPassageView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_WRITING_LISTENING_MATERIAL:
                tv = new WritingListeningMaterialView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK:
                tv = new IntegratedWritingTaskView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_INTEGRATED_WRITING_TASK_END:
                tv = new IntegratedWritingTaskEndView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_INDEPENDENT_WRITING_DIRECTIONS:
                tv = new IndependentWritingDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK:
                tv = new IndependentWritingTaskView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_INDEPENDENT_WRITING_TASK_END:
                tv = new IndependentWritingTaskEndView(this, SWT.NONE);
                break;
        }
        return tv;
    }

    /*
     * ==================================================
     *
     * To Reading Review View
     *
     * ==================================================
     */

    public void toReadingReview() {
        stack.topControl = new ReadingReviewView(this, SWT.NONE);
        this.layout();
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public TestSchemaVo getTestSchema() {
        return testSchema;
    }

    public UserTestSession getUserTestSession() {
        return userTestSession;
    }
}
