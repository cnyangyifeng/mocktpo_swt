package com.mocktpo.view.test;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.UserTestAnswerMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.*;
import com.mocktpo.vo.TestViewVo;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.ReadingReviewTableRow;
import com.mocktpo.widget.TestFooter;
import com.mocktpo.widget.TestHeader;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ReadingReviewView extends Composite {

    /* Constants */

    public static final String STATUS_TEXT_NOT_SEEN = "Not Seen";
    public static final String STATUS_TEXT_NOT_ANSWERED = "Not Answered";
    public static final String STATUS_TEXT_ANSWERED = "Answered";

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Page */

    protected TestPage page;

    /* Widgets */

    protected TestHeader header;
    protected ImageButton pauseTestButton;
    protected Label timerLabel;
    protected ImageButton timerButton;

    protected StyledText caption;

    protected TestFooter footer;

    protected ScrolledComposite sc;
    protected Composite body;
    protected Composite viewPort;

    protected ReadingReviewTableRow selectedTableRow;

    /* Properties */

    protected int selectedViewId;

    /* Persistence */

    protected SqlSession sqlSession;

    /* Timer */

    protected int countDown;
    protected Timer timer;
    protected TestTimerTask timerTask;

    protected boolean timed;

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
        this.sqlSession = MyApplication.get().getSqlSession();
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
        FormLayoutSet.layout(this);
    }

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
        FormLayoutSet.layout(header);

        /*
         * ==================================================
         * 
         * Title Label
         * 
         * ==================================================
         */

        final Label titleLabel = new Label(header, SWT.WRAP);
        FormDataSet.attach(titleLabel).atLeft(10).atTop(10);
        LabelSet.decorate(titleLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(page.getTestSchema().getTitle() + MT.STRING_SPACE + "Reading");

        /*
         * ==================================================
         * 
         * Pause Test Button
         * 
         * ==================================================
         */

        pauseTestButton = new ImageButton(header, SWT.NONE, MT.IMAGE_PAUSE_TEST, MT.IMAGE_PAUSE_TEST_HOVER);
        FormDataSet.attach(pauseTestButton).atLeft(10).atBottom(10);
        pauseTestButton.addMouseListener(new PauseTestButtonMouseListener());

        /*
         * ==================================================
         * 
         * Header Updates
         * 
         * ==================================================
         */

        updateHeader();
    }

    private void updateHeader() {

        TestViewVo vo = page.getTestSchema().getView(page.getUserTestSession().getLastViewId());
        if (vo.isQuestionCaptionVisible()) {
            caption = new StyledText(header, SWT.SINGLE);
            FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
            StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(MT.STRING_QUESTION + MT.STRING_SPACE + vo.getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + page.getTestSchema().getTotalQuestionCountInSectionAndGroup(ST.SECTION_TYPE_READING, 0));
        }

        final ImageButton goToQuestionButton = new ImageButton(header, SWT.NONE, MT.IMAGE_GO_TO_QUESTION, MT.IMAGE_GO_TO_QUESTION_HOVER);
        FormDataSet.attach(goToQuestionButton).atRight(10).atTop(10);
        goToQuestionButton.addMouseListener(new GoToQuestionButtonMouseListener());

        final ImageButton returnButton = new ImageButton(header, SWT.NONE, MT.IMAGE_RETURN, MT.IMAGE_RETURN_HOVER);
        FormDataSet.attach(returnButton).atRightTo(goToQuestionButton, 10).atTopTo(goToQuestionButton, 0, SWT.TOP);
        returnButton.addMouseListener(new ReturnButtonMouseListener());
    }

    private void initFooter() {
        footer = new TestFooter(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
    }

    private void initBody() {

        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(body).marginBottom(50);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x);
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

    private void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        GridDataSet.attach(dt).fillBoth();
        StyledTextSet.decorate(dt).setBottomMargin(50).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(msgs.getString("reading_review_directions")).setTopMargin(20);
        dt.setStyleRanges(new StyleRange[]{new StyleRange(461, 14, null, null, SWT.BOLD), new StyleRange(676, 6, null, null, SWT.BOLD)});

        final ReadingReviewTableRow tableHeader = new ReadingReviewTableRow(viewPort, SWT.NONE, msgs.getString("number"), msgs.getString("description"), msgs.getString("status"), 0, true);
        GridDataSet.attach(tableHeader).fillBoth();

        for (TestViewVo vo : page.getTestSchema().getViews()) {
            if (vo.getSectionType() == ST.SECTION_TYPE_READING && (vo.getViewType() == VT.VIEW_TYPE_READING_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_INSERT_TEXT_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_CATEGORY_CHART_QUESTION)) {
                String statusText = getStatusText(vo.getViewId());
                final ReadingReviewTableRow row = new ReadingReviewTableRow(viewPort, SWT.NONE, Integer.toString(vo.getQuestionNumberInSection()), getDescriptionText(vo), statusText, vo.getViewId());
                GridDataSet.attach(row).fillBoth();
                row.addMouseListener(new ReadingReviewTableCellMouseListener());
                if (statusText.equals(STATUS_TEXT_NOT_SEEN)) {
                    row.setEnabled(false);
                }
                if (selectedViewId == vo.getViewId()) {
                    selectedTableRow = row;
                    selectedTableRow.setSelectionBackground();
                    selectedTableRow.addControlListener(new ReadingReviewControlListener());
                }
            }
        }

        final Label bottomTableBorder = new Label(viewPort, SWT.NONE);
        GridDataSet.attach(bottomTableBorder).fillBoth().withHeight(1);
        LabelSet.decorate(bottomTableBorder).setBackground(MT.COLOR_GRAY40);
    }

    private String getDescriptionText(TestViewVo vo) {
        String text;
        if (vo.getViewType() == VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION || vo.getViewType() == VT.VIEW_TYPE_READING_CATEGORY_CHART_QUESTION) {
            text = vo.getStyledText("directions").getText();
        } else {
            text = vo.getStyledText("question").getText();
        }
        return text;
    }

    private String getStatusText(int viewId) {
        String text;
        UserTestAnswerMapper mapper = sqlSession.getMapper(UserTestAnswerMapper.class);
        UserTestSession userTestSession = page.getUserTestSession();
        String readingAnswer = mapper.findByViewId(userTestSession, viewId);
        if (!StringUtils.isEmpty(readingAnswer)) {
            text = STATUS_TEXT_ANSWERED;
        } else if (viewId > userTestSession.getMaxViewId()) {
            text = STATUS_TEXT_NOT_SEEN;
        } else {
            text = STATUS_TEXT_NOT_ANSWERED;
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

            TestViewVo vo = page.getTestSchema().getView(page.getUserTestSession().getLastViewId());

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
            LabelSet.decorate(timerLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE).setText(TimeUtils.displayTime(page.getUserTestSession().getRemainingViewTime(vo))).setVisible(!hidden);

            if (hidden) {
                timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SHOW_TIME, MT.IMAGE_SHOW_TIME_HOVER, MT.IMAGE_SHOW_TIME_DISABLED);
            } else {
                timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HIDE_TIME, MT.IMAGE_HIDE_TIME_HOVER, MT.IMAGE_HIDE_TIME_DISABLED);
            }
            FormDataSet.attach(timerButton).atRightTo(timerLabel, 6).atBottomTo(timerLabel, -3, SWT.BOTTOM);
            timerButton.addMouseListener(new TimerButtonMouseListener());

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
            if (null != timerTask) {
                timerTask.cancel();
            }
            if (null != timer) {
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
                TestViewVo vo = page.getTestSchema().getView(page.getUserTestSession().getLastViewId());
                UserTestPersistenceUtils.saveRemainingViewTime(userTestSession, vo, countDown);
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        LabelSet.decorate(timerLabel).setText(TimeUtils.displayTime(countDown--));
                    }
                });
                if (0 >= countDown) {
                    if (timed) {
                        stopTimer();
                    }
                    int lastViewId = page.getTestSchema().getNextViewIdWhileTimeOut(page.getUserTestSession().getLastViewId());
                    UserTestPersistenceUtils.saveToCurrentView(userTestSession, lastViewId);
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication.get().getWindow().toTestPage(userTestSession);
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

    private class PauseTestButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            MyApplication.get().getWindow().toMainPage(page.getUserTestSession());
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class TimerButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            boolean v = timerLabel.isVisible();
            if (v) {
                timerButton.setBackgroundImages(MT.IMAGE_SHOW_TIME, MT.IMAGE_SHOW_TIME_HOVER, MT.IMAGE_SHOW_TIME_DISABLED);
            } else {
                timerButton.setBackgroundImages(MT.IMAGE_HIDE_TIME, MT.IMAGE_HIDE_TIME_HOVER, MT.IMAGE_HIDE_TIME_DISABLED);
            }
            timerLabel.setVisible(!v);
            UserTestPersistenceUtils.saveTimerHidden(page.getUserTestSession(), v);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class GoToQuestionButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            if (selectedViewId == page.getTestSchema().getFirstViewIdByViewType(VT.VIEW_TYPE_READING_SECTION_END)) {
                return;
            }
            if (selectedViewId > page.getUserTestSession().getMaxViewId()) {
                return;
            }
            release();
            UserTestPersistenceUtils.saveToCurrentView(page.getUserTestSession(), selectedViewId);
            page.resume(page.getUserTestSession());
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReturnButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            page.resume(page.getUserTestSession());
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReadingReviewTableCellMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            ReadingReviewTableRow c = (ReadingReviewTableRow) ((Label) e.widget).getParent();
            if (selectedTableRow != c) {
                c.setSelectionBackground();
                if (null != selectedTableRow) {
                    selectedTableRow.resetBackground();
                }
                selectedTableRow = c;
                selectedViewId = c.getViewId();
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReadingReviewControlListener implements ControlListener {

        @Override
        public void controlMoved(ControlEvent e) {
        }

        @Override
        public void controlResized(ControlEvent e) {
            ReadingReviewTableRow row = (ReadingReviewTableRow) e.widget;
            int quarter = sc.getBounds().height / 4;
            sc.setOrigin(0, row.getBounds().y - quarter);
        }
    }
}
