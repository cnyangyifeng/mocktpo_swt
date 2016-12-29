package com.mocktpo.view.test;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.vo.TestViewVo;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.TestFooter;
import com.mocktpo.widget.TestHeader;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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

    /* Persistence */

    protected SqlSession sqlSession;

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
        this.vo = page.getTestSchema().getView(page.getUserTest().getLastViewId());
        this.sqlSession = MyApplication.get().getSqlSession();
        this.volumeControlVisible = !page.getUserTest().isVolumeControlHidden();
        init();
        realloc();
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
        LabelSet.decorate(titleLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(page.getTestSchema().getTitle() + MT.STRING_SPACE + vo.getSectionTypeName());

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
         * Caption
         *
         * ==================================================
         */

        if (vo.isWithQuestion()) {
            final StyledText caption = new StyledText(header, SWT.SINGLE);
            FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
            StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(MT.STRING_QUESTION + MT.STRING_SPACE + vo.getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + TestSchemaUtils.getTotalQuestionCountInSectionAndGroup(page.getTestSchema(), vo.getSectionType(), vo.getGroupId()));
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

    private void initFooter() {
        footer = new TestFooter(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom();
    }

    protected abstract void initBody();

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

    protected void realloc() {
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

            boolean hidden = page.getUserTest().isTimerHidden();

            timerLabel = new Label(header, SWT.NONE);
            FormDataSet.attach(timerLabel).atRight(10).atBottomTo(pauseTestButton, 0, SWT.BOTTOM);
            LabelSet.decorate(timerLabel).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE).setText(TimeUtils.displayTime(page.getUserTest().getRemainingViewTime(vo.getSectionType(), vo.getGroupId()))).setVisible(!hidden);

            if (!vo.isTimerButtonUnavailable()) {
                if (hidden) {
                    timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SHOW_TIME, MT.IMAGE_SHOW_TIME_HOVER, MT.IMAGE_SHOW_TIME_DISABLED);
                } else {
                    timerButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HIDE_TIME, MT.IMAGE_HIDE_TIME_HOVER, MT.IMAGE_HIDE_TIME_DISABLED);
                }
                FormDataSet.attach(timerButton).atRightTo(timerLabel, 6).atBottomTo(timerLabel, -3, SWT.BOTTOM);
                timerButton.addMouseListener(new TimerButtonMouseListener());
            }

            /*
             * ==================================================
             * 
             * Timer and Timer Task
             * 
             * ==================================================
             */

            if (!vo.isTimerTaskDelayed()) {
                countDown = page.getUserTest().getRemainingViewTime(vo.getSectionType(), vo.getGroupId());
                timer = new Timer();
                timerTask = new TestTimerTask();
                timer.scheduleAtFixedRate(timerTask, 0, 1000);
            }
        }
    }

    public void stopTimer() {
        if (vo.isTimed()) {
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
     * Timer Task
     *
     * ==================================================
     */

    public class TestTimerTask extends TimerTask {

        @Override
        public void run() {

            if (!d.isDisposed()) {

                final UserTest ut = page.getUserTest();
                ut.setRemainingViewTime(vo.getSectionType(), vo.getGroupId(), countDown);
                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        LabelSet.decorate(timerLabel).setText(TimeUtils.displayTime(countDown--));
                    }
                });

                if (0 >= countDown) {

                    release();

                    int lastViewId = TestSchemaUtils.getNextViewIdWhileTimeOut(page.getTestSchema(), vo.getViewId());
                    ut.setCompletionRate(100 * (lastViewId - 1) / page.getTestSchema().getViews().size());
                    ut.setLastViewId(lastViewId);

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
     * Audio Player
     *
     * ==================================================
     */

    public void startAudio() {

        if (vo.isWithAudio()) {

            audioPlayer = new TestAudioPlayer(page.getUserTest(), vo.getAudio(), false);
            audioPlayer.setVolume(page.getUserTest().getVolume());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    audioPlayer.play();
                }
            }).start();
        }
    }

    public void stopAudio() {
        if (vo.isWithAudio() && null != audioPlayer) {
            audioPlayer.stop();
        }
    }

    public void setAudioVolume(double value) {
        if (vo.isWithAudio() && null != audioPlayer) {
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

    private class PauseTestButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            release();

            UserTest ut = page.getUserTest();
            ut.setCompletionRate(100 * (vo.getViewId() - 1) / page.getTestSchema().getViews().size());
            ut.setLastViewId(vo.getViewId());

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            MyApplication.get().getWindow().toMainPage(ut);
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

            UserTest ut = page.getUserTest();
            ut.setTimerHidden(v);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
