package com.mocktpo.view.test;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.ST;
import com.mocktpo.vo.TestViewVo;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.TestFooter;
import com.mocktpo.widget.TestHeader;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ReadingReviewView extends Composite {

    private static final String STRING_IS_TABLE_HEADER = "isTableHeader";
    private static final int TABLE_COLUMN_NUMBER_WIDTH = 100;
    private static final int TABLE_COLUMN_STATUS_WIDTH = 200;
    private static final int TABLE_ITEM_HEIGHT = 32;

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

    protected Composite body;
    protected Composite viewPort;
    protected Table table;

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

    public ReadingReviewView(TestPage page, int style, boolean timed) {
        super(page, style);
        this.d = page.getDisplay();
        this.page = page;
        this.selectedViewId = page.getUserTest().getLastViewId();
        this.sqlSession = MyApplication.get().getSqlSession();
        this.timed = timed;
        init();
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
        pauseTestButton.addMouseListener(new ReadingReviewPauseTestButtonMouseListener());

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

        caption = new StyledText(header, SWT.SINGLE);
        FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
        StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(MT.STRING_QUESTION + MT.STRING_SPACE + page.getTestSchema().getView(page.getUserTest().getLastViewId()).getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + TestSchemaUtils.getTotalQuestionCountInSection(page.getTestSchema(), ST.SECTION_TYPE_READING));

        final ImageButton gb = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_GO_TO_QUESTION), ResourceManager.getImage(MT.IMAGE_GO_TO_QUESTION_HOVER));
        FormDataSet.attach(gb).atRight(10).atTop(10);
        gb.addMouseListener(new ReadingReviewGoToQuestionButtonMouseListener());

        final ImageButton rb = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_RETURN), ResourceManager.getImage(MT.IMAGE_RETURN_HOVER));
        FormDataSet.attach(rb).atRightTo(gb, 10).atTop(10);
        rb.addMouseListener(new ReadingReviewReturnButtonMouseListener());
    }

    private void initFooter() {
        footer = new TestFooter(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
    }

    private void initBody() {

        final ScrolledComposite sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(body).marginBottom(50);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x);
        FormLayoutSet.layout(viewPort);

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

        body.setBackground(ResourceManager.getColor(MT.COLOR_KHAKI));

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTop(20).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(msgs.getString("reading_review_directions"));
        dt.setStyleRanges(new StyleRange[]{new StyleRange(461, 14, null, null, SWT.BOLD), new StyleRange(676, 6, null, null, SWT.BOLD)});



//        table = new Table(viewPort, SWT.BORDER | SWT.FULL_SELECTION | SWT.NO_SCROLL);
//        FormDataSet.attach(table).atLeft().atTopTo(dt, 20).atRight();
//        TableSet.decorate(table).setBackground(MT.COLOR_KHAKI).setFont(MT.FONT_MEDIUM).setLineVisible(true);
//        table.addControlListener(new ReadingReviewTableControlListener());
//        table.addSelectionListener(new ReadingReviewTableSelectionListener());
//
//        final TableColumn numberColumn = new TableColumn(table, SWT.CENTER);
//        numberColumn.setText(msgs.getString("number"));
//        numberColumn.setWidth(TABLE_COLUMN_NUMBER_WIDTH);
//
//        final TableColumn descriptionColumn = new TableColumn(table, SWT.NONE);
//        descriptionColumn.setText(msgs.getString("description"));
//
//        final TableColumn statusColumn = new TableColumn(table, SWT.CENTER);
//        statusColumn.setText(msgs.getString("status"));
//        statusColumn.setWidth(TABLE_COLUMN_STATUS_WIDTH);
//
//        final TableItem tableHeader = new TableItem(table, SWT.NONE);
//        TableItemSet.decorate(tableHeader).setBackground(MT.COLOR_SADDLE_BROWN).setData(STRING_IS_TABLE_HEADER, true).setForeground(MT.COLOR_WHITE).setImage(new Image(d, 1, TABLE_ITEM_HEIGHT)).setText(new String[]{msgs.getString("number"), msgs.getString("description"), msgs.getString("status")});
//
//        for (TestViewVo tvv : page.getTestSchema().getViews()) {
//            if (ST.SECTION_TYPE_READING == tvv.getSectionType() && tvv.isWithQuestion()) {
//
//                final TableItem item = new TableItem(table, SWT.NONE);
//                String number = Integer.toString(tvv.getQuestionNumberInSection());
//                String question;
//                if (tvv.getViewType() == VT.VIEW_TYPE_READING_PROSE_SUMMARY_QUESTION) {
//                    question = tvv.getStyledText("directions").getText();
//                } else {
//                    question = tvv.getStyledText("question").getText();
//                }
//                String status = "not answered";
//                TableItemSet.decorate(item).setText(new String[]{number, question, status});
//
//                if (selectedViewId == tvv.getViewId()) {
//                    table.setSelection(item);
//                }
//            }
//        }
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

            /*
             * ==================================================
             * 
             * Timer Label and Timer Button
             * 
             * ==================================================
             */

            boolean hidden = page.getUserTest().isTimerHidden();

            timerLabel = new Label(header, SWT.NONE);
            FormDataSet.attach(timerLabel).atRight(10).atBottomTo(pauseTestButton, 0, SWT.BOTTOM);
            LabelSet.decorate(timerLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE).setText(TimeUtils.displayTime(page.getUserTest().getRemainingViewTime(ST.SECTION_TYPE_READING))).setVisible(!hidden);

            if (hidden) {
                timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SHOW_TIME, MT.IMAGE_SHOW_TIME_HOVER, MT.IMAGE_SHOW_TIME_DISABLED);
            } else {
                timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HIDE_TIME, MT.IMAGE_HIDE_TIME_HOVER, MT.IMAGE_HIDE_TIME_DISABLED);
            }
            FormDataSet.attach(timerButton).atRightTo(timerLabel, 6).atBottomTo(timerLabel, -3, SWT.BOTTOM);
            timerButton.addMouseListener(new ReadingReviewTimerButtonMouseListener());

            /*
             * ==================================================
             * 
             * Timer and Timer Task
             * 
             * ==================================================
             */

            countDown = page.getUserTest().getRemainingViewTime(ST.SECTION_TYPE_READING);
            timer = new Timer();
            timerTask = new TestTimerTask();
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
    }

    public void stopTimer() {

        if (null != timerTask) {
            timerTask.cancel();
        }

        if (null != timer) {
            timer.purge();
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

                final UserTest ut = page.getUserTest();
                ut.setRemainingViewTime(ST.SECTION_TYPE_READING, countDown);
                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

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

                    ut.setLastViewId(TestSchemaUtils.getNextViewIdWhileTimeOut(page.getTestSchema(), page.getUserTest().getLastViewId()));
                    sqlSession.getMapper(UserTestMapper.class).update(ut);
                    sqlSession.commit();

                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication.get().getWindow().toTestPage(ut);
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

    private class ReadingReviewPauseTestButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (timed) {
                stopTimer();
            }

            UserTest ut = page.getUserTest();

            MyApplication.get().getWindow().toMainPage(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReadingReviewTimerButtonMouseListener implements MouseListener {

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

            UserTest ut = page.getUserTest();
            ut.setTimerHidden(v);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReadingReviewGoToQuestionButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (timed) {
                stopTimer();
            }

            UserTest ut = page.getUserTest();
            ut.setLastViewId(selectedViewId);

            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReadingReviewReturnButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (timed) {
                stopTimer();
            }

            UserTest ut = page.getUserTest();

            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReadingReviewTableControlListener implements ControlListener {

        @Override
        public void controlMoved(ControlEvent e) {
        }

        @Override
        public void controlResized(ControlEvent e) {
            int tableWidth = table.getBounds().width, numberColumnWidth = table.getColumn(0).getWidth(), statusColumnWidth = table.getColumn(2).getWidth();
            table.getColumn(1).setWidth(tableWidth - numberColumnWidth - statusColumnWidth);
        }
    }

    private class ReadingReviewTableSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {

            TableItem item = (TableItem) e.item;

            Object b = item.getData(STRING_IS_TABLE_HEADER);
            if (null != b && (Boolean) b) {
                return;
            }

            int questionId = Integer.valueOf(item.getText(0));

            for (TestViewVo tvv : page.getTestSchema().getViews()) {
                if (ST.SECTION_TYPE_READING == tvv.getSectionType() && tvv.isWithQuestion()) {
                    if (questionId == tvv.getQuestionNumberInSection()) {
                        selectedViewId = tvv.getViewId();
                        return;
                    }
                }
            }
        }
    }
}
