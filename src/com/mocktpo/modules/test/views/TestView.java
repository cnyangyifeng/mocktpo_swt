package com.mocktpo.modules.test.views;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.modules.test.widgets.TestFooter;
import com.mocktpo.modules.test.widgets.TestHeader;
import com.mocktpo.orm.domain.UserTestAnswer;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.TestAudioPlayer;
import com.mocktpo.util.TimeUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.ST;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.TestViewVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public abstract class TestView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Page */

    protected TestPage page;

    /* Widgets */

    protected TestHeader header;
    protected TestFooter footer;

    protected ImageButton pauseTestButton;

    protected Label timerLabel;
    protected ImageButton timerButton;

    /* Properties */

    protected TestViewVo vo;
    protected String answerText;

    /* Timer */

    protected int countDown;
    protected Timer timer;
    protected TestTimerTask timerTask;

    /* Audio Player */

    protected TestAudioPlayer audioPlayer;
    protected boolean volumeControlVisible;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestView(TestPage page, int style) {
        super(page, style);
        this.d = page.getDisplay();
        this.page = page;
        this.vo = page.getTestVo().getViewVo(page.getUserTestSession().getLastViewId());
        this.volumeControlVisible = !page.getUserTestSession().isVolumeControlHidden();
        init();
        alloc();
    }

    private void init() {
        initAnswer();
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
        LabelSet.decorate(titleLabel).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_GREY_LIGHTEN_4).setText(page.getTestVo().getTitle() + MT.STRING_SPACE + vo.getSectionTypeName());

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
         * Caption
         *
         * ==================================================
         */

        if (vo.isQuestionCaptionVisible()) {
            final StyledText caption = new StyledText(header, SWT.SINGLE);
            FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
            int totalQuestionCount;
            if (vo.getSectionType() == ST.SECTION_TYPE_LISTENING) {
                totalQuestionCount = page.getTestVo().findTotalQuestionCountInListeningSectionAndGroup(vo.getSectionType(), vo.getListeningGroupId());
            } else {
                totalQuestionCount = page.getTestVo().findTotalQuestionCountInSection(vo.getSectionType());
            }
            StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_GREY_LIGHTEN_4).setText(MT.STRING_QUESTION + MT.STRING_SPACE + vo.getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + totalQuestionCount);
        }

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

    protected abstract void initBody();

    /*
     * ==================================================
     *
     * Answer Initialization
     *
     * ==================================================
     */

    protected void initAnswer() {
        if (vo.isAnswerable()) {
            UserTestAnswer userTestAnswer = PersistenceUtils.findAnswer(this);
            if (userTestAnswer == null) {
                userTestAnswer = PersistenceUtils.newAnswer(this);
            }
            answerText = userTestAnswer.getAnswer();
        }
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    protected abstract void updateHeader();

    /*
     * ==================================================
     *
     * Native Resource Operations
     *
     * ==================================================
     */

    protected void alloc() {
        startTimer();
        startAudio();
        startAudioVisualization();
        startAudioAsyncExecution();
    }

    protected void release() {
        stopTimer();
        stopAudio();
        stopAudioVisualization();
        stopAudioAsyncExecution();
    }

    /*
     * ==================================================
     *
     * Timer
     *
     * ==================================================
     */

    public void startTimer() {
        if (vo.isTimed()) {

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

            if (!vo.isTimerButtonUnavailable()) {
                if (hidden) {
                    timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SHOW_TIME, MT.IMAGE_SHOW_TIME_HOVER, MT.IMAGE_SHOW_TIME_DISABLED);
                } else {
                    timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HIDE_TIME, MT.IMAGE_HIDE_TIME_HOVER, MT.IMAGE_HIDE_TIME_DISABLED);
                }
                FormDataSet.attach(timerButton).atRightTo(timerLabel, 6).atBottomTo(timerLabel, -3, SWT.BOTTOM);
                timerButton.addMouseListener(new TimerButtonMouseAdapter());
            }

            /*
             * ==================================================
             * 
             * Timer and Timer Task
             * 
             * ==================================================
             */

            if (!vo.isTimerTaskDelayed()) {
                countDown = page.getUserTestSession().getRemainingViewTime(vo);
                timer = new Timer();
                timerTask = new TestTimerTask();
                timer.scheduleAtFixedRate(timerTask, 0, 1000);
            }
        }
    }

    public void stopTimer() {
        if (vo.isTimed()) {
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
     * Timer Task
     *
     * ==================================================
     */

    public class TestTimerTask extends TimerTask {

        @Override
        public void run() {
            if (!d.isDisposed()) {
                PersistenceUtils.saveRemainingViewTime(TestView.this);
                d.asyncExec(() -> LabelSet.decorate(timerLabel).setText(TimeUtils.displayTimePeriod(countDown--)));
                if (countDown <= 0) {
                    release();
                    int lastViewId = page.getTestVo().findNextViewIdWhileTimeOut(vo.getViewId());
                    PersistenceUtils.saveToView(page.getUserTestSession(), lastViewId);
                    d.asyncExec(page::resume);
                }
            }
        }
    }

    /*
     * ==================================================
     *
     * Audio Player
     *
     * ==================================================
     */

    public void startAudio() {
        if (vo.isWithAudio()) {
            audioPlayer = new TestAudioPlayer(page.getUserTestSession(), vo.getAudio(), false);
            audioPlayer.setVolume(page.getUserTestSession().getVolume());
            new Thread(audioPlayer::play).start();
        }
    }

    public void stopAudio() {
        if (vo.isWithAudio() && audioPlayer != null) {
            audioPlayer.stop();
        }
    }

    public void setAudioVolume(double value) {
        if (vo.isWithAudio() && audioPlayer != null) {
            audioPlayer.setVolume(value);
        }
    }

    /*
     * ==================================================
     *
     * Audio Visualization
     *
     * ==================================================
     */

    public void startAudioVisualization() {
    }

    public void stopAudioVisualization() {
    }

    /*
     * ==================================================
     *
     * Audio Async Execution
     *
     * ==================================================
     */

    public void startAudioAsyncExecution() {
    }

    public void stopAudioAsyncExecution() {
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
            PersistenceUtils.saveToView(TestView.this);
            MyApplication.get().getWindow().toMainPageAndToTestReportNavContent();
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
            PersistenceUtils.saveTimerHidden(TestView.this);
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public TestPage getPage() {
        return page;
    }

    public TestViewVo getVo() {
        return vo;
    }

    public boolean isVolumeControlVisible() {
        return volumeControlVisible;
    }

    public int getCountDown() {
        return countDown;
    }

    public boolean isTimerHidden() {
        return !timerLabel.isVisible();
    }
}
