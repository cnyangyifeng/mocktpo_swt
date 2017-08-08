package com.mocktpo.modules.test.views;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.modules.test.widgets.ReadingReviewTableRow;
import com.mocktpo.modules.test.widgets.TestFooter;
import com.mocktpo.modules.test.widgets.TestHeader;
import com.mocktpo.orm.domain.UserTestAnswer;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.TimeUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.TestViewVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ReadingReviewView extends Composite {

    /* Constants */

    private static final int VIEW_PORT_PADDING_WIDTH = 50;

    private static final String STATUS_TEXT_NOT_SEEN = "Not Seen";
    private static final String STATUS_TEXT_NOT_ANSWERED = "Not Answered";
    private static final String STATUS_TEXT_ANSWERED = "Answered";

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Page */

    private TestPage page;

    /* Widgets */

    private TestHeader header;
    private ImageButton pauseTestButton;
    private Label timerLabel;
    private ImageButton timerButton;

    private StyledText caption;

    private TestFooter footer;

    private ScrolledComposite sc;
    private Composite body;
    private Composite viewPort;

    private ReadingReviewTableRow selectedTableRow;

    /* Properties */

    private int selectedViewId;

    /* Timer */

    private int countDown;
    private Timer timer;
    private TestTimerTask timerTask;

    private boolean timed;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingReviewView(TestPage page, int style) {
        super(page, style);
        this.d = page.getDisplay();
        this.page = page;
        this.selectedViewId = page.getUserTestSession().getLastViewId();
        this.timed = true;
        init();
        alloc();
    }

    private void init() {
        golbal();
        initHeader();
        initFooter();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    /*
     * ==================================================
     *
     * Header Initialization
     *
     * ==================================================
     */

    private void initHeader() {

        /*
         * ==================================================
         * 
         * Header Composite
         * 
         * ==================================================
         */

        header = new TestHeader(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight().withHeight(LC.TEST_HEADER_HEIGHT);
        FormLayoutSet.layout(header).marginWidth(0).marginHeight(0).spacing(0);

        /*
         * ==================================================
         * 
         * Title Label
         * 
         * ==================================================
         */

        final Label titleLabel = new Label(header, SWT.WRAP);
        FormDataSet.attach(titleLabel).atLeft(10).atTop(10);
        LabelSet.decorate(titleLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(page.getTestPaperVo().getTitle() + MT.STRING_SPACE + msgs.getString("reading"));

        /*
         * ==================================================
         * 
         * Pause Test Button
         * 
         * ==================================================
         */

        pauseTestButton = new ImageButton(header, SWT.NONE, MT.IMAGE_PAUSE_TEST, MT.IMAGE_PAUSE_TEST_HOVER);
        FormDataSet.attach(pauseTestButton).atLeft(10).atBottom(10);
        pauseTestButton.addMouseListener(new PauseTestButtonMouseAdapter());

        /*
         * ==================================================
         * 
         * Header Updates
         * 
         * ==================================================
         */

        updateHeader();
    }

    /*
     * ==================================================
     *
     * Footer Initialization
     *
     * ==================================================
     */

    private void initFooter() {
        footer = new TestFooter(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    private void initBody() {
        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(body).marginBottom(50);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        GridLayoutSet.layout(viewPort).verticalSpacing(0);

        /*
         * ==================================================
         * 
         * Body Updates
         * 
         * ==================================================
         */

        updateBody();

        sc.setContent(body);
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    private void updateHeader() {
        TestViewVo vo = page.getTestPaperVo().getViewVo(page.getUserTestSession().getLastViewId());
        if (vo.isQuestionCaptionVisible()) {
            caption = new StyledText(header, SWT.SINGLE);
            FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
            StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(MT.STRING_QUESTION + MT.STRING_SPACE + vo.getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + page.getTestPaperVo().findTotalQuestionCountInSection(ST.SECTION_TYPE_READING));
        }

        final ImageButton goToQuestionButton = new ImageButton(header, SWT.NONE, MT.IMAGE_GO_TO_QUESTION, MT.IMAGE_GO_TO_QUESTION_HOVER);
        FormDataSet.attach(goToQuestionButton).atRight(10).atTop(10);
        goToQuestionButton.addMouseListener(new GoToQuestionButtonMouseAdapter());

        final ImageButton returnButton = new ImageButton(header, SWT.NONE, MT.IMAGE_RETURN, MT.IMAGE_RETURN_HOVER);
        FormDataSet.attach(returnButton).atRightTo(goToQuestionButton, 10).atTopTo(goToQuestionButton, 0, SWT.TOP);
        returnButton.addMouseListener(new ReturnButtonMouseAdapter());
    }

    private void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        final StyledText directionsTextWidget = new StyledText(viewPort, SWT.WRAP);
        GridDataSet.attach(directionsTextWidget).fillBoth();
        StyledTextSet.decorate(directionsTextWidget).setBottomMargin(50).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(msgs.getString("reading_review_directions")).setTopMargin(20);
        directionsTextWidget.setStyleRanges(new StyleRange[]{new StyleRange(461, 14, null, null, SWT.BOLD), new StyleRange(676, 6, null, null, SWT.BOLD)});

        final ReadingReviewTableRow tableHeader = new ReadingReviewTableRow(viewPort, SWT.NONE, msgs.getString("number"), msgs.getString("description"), msgs.getString("status"), 0, true);
        GridDataSet.attach(tableHeader).fillBoth();

        for (TestViewVo vo : page.getTestPaperVo().getViewVos()) {
            if (vo.getSectionType() == ST.SECTION_TYPE_READING && vo.isAnswerable()) {
                String statusText = getStatusText(vo.getViewId());
                final ReadingReviewTableRow row = new ReadingReviewTableRow(viewPort, SWT.NONE, Integer.toString(vo.getQuestionNumberInSection()), getDescriptionText(vo), statusText, vo.getViewId());
                GridDataSet.attach(row).fillBoth();
                row.addMouseListener(new ReadingReviewTableCellMouseAdapter());
                if (statusText.equals(STATUS_TEXT_NOT_SEEN)) {
                    row.setEnabled(false);
                }
                if (selectedViewId == vo.getViewId()) {
                    selectedTableRow = row;
                    selectedTableRow.setSelectionBackground();
                    selectedTableRow.addControlListener(new ReadingReviewControlAdapter());
                }
            }
        }

        final Label bottomTableBorder = new Label(viewPort, SWT.NONE);
        GridDataSet.attach(bottomTableBorder).fillBoth().withHeight(1);
        LabelSet.decorate(bottomTableBorder).setBackground(MT.COLOR_GRAY60);
    }

    private String getDescriptionText(TestViewVo vo) {
        String text;
        if (vo.getViewType() == VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_FILL_IN_A_TABLE_QUESTION) {
            text = vo.getStyledText("directions") + MT.STRING_SPACE + vo.getStyledText("question");
        } else {
            text = vo.getStyledText("question");
        }
        return text;
    }

    private String getStatusText(int viewId) {
        String text;
        UserTestAnswer userTestAnswer = PersistenceUtils.findAnswer(page.getUserTestSession(), viewId);
        if (userTestAnswer != null) {
            String readingAnswer = userTestAnswer.getAnswer();
            if (!StringUtils.isEmpty(readingAnswer)) {
                text = STATUS_TEXT_ANSWERED;
            } else {
                text = STATUS_TEXT_NOT_ANSWERED;
            }
        } else {
            text = STATUS_TEXT_NOT_ANSWERED;
        }
        if (viewId > page.getUserTestSession().getMaxViewId()) {
            text = STATUS_TEXT_NOT_SEEN;
        }
        return text;
    }

    /*
     * ==================================================
     *
     * Native Resource Operations
     *
     * ==================================================
     */

    protected void alloc() {
        startTimer();
    }

    protected void release() {
        stopTimer();
    }

    /*
     * ==================================================
     *
     * Timer
     *
     * ==================================================
     */

    public void startTimer() {
        if (timed) {
            TestViewVo vo = page.getTestPaperVo().getViewVo(page.getUserTestSession().getLastViewId());

            /*
             * ==================================================
             * 
             * Timer Label and Timer Button
             * 
             * ==================================================
             */

            boolean hidden = page.getUserTestSession().isTimerHidden();

            timerLabel = new Label(header, SWT.NONE);
            FormDataSet.attach(timerLabel).atRight(10).atBottomTo(pauseTestButton, 0, SWT.BOTTOM);
            LabelSet.decorate(timerLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE).setText(TimeUtils.displayTimePeriod(page.getUserTestSession().getRemainingViewTime(vo))).setVisible(!hidden);

            if (hidden) {
                timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SHOW_TIME, MT.IMAGE_SHOW_TIME_HOVER, MT.IMAGE_SHOW_TIME_DISABLED);
            } else {
                timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HIDE_TIME, MT.IMAGE_HIDE_TIME_HOVER, MT.IMAGE_HIDE_TIME_DISABLED);
            }
            FormDataSet.attach(timerButton).atRightTo(timerLabel, 6).atBottomTo(timerLabel, -3, SWT.BOTTOM);
            timerButton.addMouseListener(new TimerButtonMouseAdapter());

            /*
             * ==================================================
             * 
             * Timer and Timer Task
             * 
             * ==================================================
             */

            countDown = page.getUserTestSession().getRemainingViewTime(vo);
            timer = new Timer();
            timerTask = new TestTimerTask();
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }

    public void stopTimer() {
        if (timed) {
            if (timerTask != null) {
                timerTask.cancel();
            }
            if (timer != null) {
                timer.purge();
            }
        }
    }

    /*
     * ==================================================
     *
     * Timer Tasks
     *
     * ==================================================
     */

    private class TestTimerTask extends TimerTask {

        @Override
        public void run() {
            if (!d.isDisposed()) {
                final UserTestSession userTestSession = page.getUserTestSession();
                TestViewVo vo = page.getTestPaperVo().getViewVo(page.getUserTestSession().getLastViewId());
                PersistenceUtils.saveRemainingViewTime(userTestSession, vo, countDown);
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        LabelSet.decorate(timerLabel).setText(TimeUtils.displayTimePeriod(countDown--));
                    }
                });
                if (countDown <= 0) {
                    if (timed) {
                        stopTimer();
                    }
                    int lastViewId = page.getTestPaperVo().findNextViewIdWhileTimeOut(page.getUserTestSession().getLastViewId());
                    PersistenceUtils.saveToView(userTestSession, lastViewId);
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            page.resume();
                        }
                    });
                }
            }
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class PauseTestButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            MyApplication.get().getWindow().toMainPageAndToTestRecordsView();
        }
    }

    private class TimerButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            boolean v = timerLabel.isVisible();
            if (v) {
                timerButton.setBackgroundImages(MT.IMAGE_SHOW_TIME, MT.IMAGE_SHOW_TIME_HOVER, MT.IMAGE_SHOW_TIME_DISABLED);
            } else {
                timerButton.setBackgroundImages(MT.IMAGE_HIDE_TIME, MT.IMAGE_HIDE_TIME_HOVER, MT.IMAGE_HIDE_TIME_DISABLED);
            }
            timerLabel.setVisible(!v);
            PersistenceUtils.saveTimerHidden(page.getUserTestSession(), v);
        }
    }

    private class GoToQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            if (selectedViewId == page.getTestPaperVo().findFirstViewIdByViewType(VT.VIEW_TYPE_READING_SECTION_END)) {
                return;
            }
            if (selectedViewId > page.getUserTestSession().getMaxViewId()) {
                return;
            }
            release();
            PersistenceUtils.saveToView(page.getUserTestSession(), selectedViewId);
            page.resume();
        }
    }

    private class ReturnButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            page.resume();
        }
    }

    private class ReadingReviewTableCellMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            ReadingReviewTableRow c = (ReadingReviewTableRow) ((Label) e.widget).getParent();
            if (selectedTableRow != c) {
                c.setSelectionBackground();
                if (selectedTableRow != null) {
                    selectedTableRow.resetBackground();
                }
                selectedTableRow = c;
                selectedViewId = c.getViewId();
            }
        }
    }

    private class ReadingReviewControlAdapter extends ControlAdapter {

        @Override
        public void controlResized(ControlEvent e) {
            ReadingReviewTableRow row = (ReadingReviewTableRow) e.widget;
            int quarter = sc.getBounds().height / 4;
            sc.setOrigin(0, row.getBounds().y - quarter);
        }
    }
}
