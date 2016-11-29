package com.mocktpo.page;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.constants.VT;
import com.mocktpo.view.test.*;
import com.mocktpo.vo.TestSchemaVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class TestPage extends Composite {

    public static final int TOTAL_VIEW_COUNT = 99;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Stack */

    protected StackLayout stack;

    /* Properties */

    private TestSchemaVo testSchema;
    private UserTest userTest;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestPage(Composite parent, int style, UserTest userTest) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.userTest = userTest;
        this.testSchema = ConfigUtils.load(this.userTest.getAlias(), TestSchemaVo.class);
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

    public void resume(UserTest ut) {

        if (this.userTest.getTid() != ut.getTid()) {
            this.setVo(ConfigUtils.load(ut.getAlias(), TestSchemaVo.class));
        }
        this.userTest = ut;

        TestView tv = getLastTestView();
        tv.startTimer();
        tv.startAudio();

        stack.topControl = tv;
        this.layout();
    }

    private TestView getLastTestView() {

        int lastViewId = userTest.getLastViewId();
        int lastViewType = testSchema.getView(lastViewId).getViewType();

        TestView tv = null;
        switch (lastViewType) {
            case VT.VIEW_TYPE_CHANGING_VOLUME:
                tv = new ChangingVolumeView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_GENERAL_TEST_INFO:
                tv = new GeneralTestInfoView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_HEADSET:
                tv = new HeadsetView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_LISTENING_DIRECTIONS:
                tv = new ListeningDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_DIRECTIONS:
                tv = new ReadingDirectionsView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_FILL_TABLE_QUESTION:
                tv = new ReadingFillTableQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION:
                tv = new ReadingProseSummaryQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_TEXT:
                tv = new ReadingTextView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_READING_TEXT_WITH_QUESTION:
                tv = new ReadingTextWithQuestionView(this, SWT.NONE);
                break;
            case VT.VIEW_TYPE_TEST_INTRO:
                tv = new TestIntroView(this, SWT.NONE);
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

        final ReadingReviewView readingReview = new ReadingReviewView(this, SWT.NONE, true);
        readingReview.startTimer();

        stack.topControl = readingReview;
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

    public void setVo(TestSchemaVo testSchema) {
        this.testSchema = testSchema;
    }

    public UserTest getUserTest() {
        return userTest;
    }
}
