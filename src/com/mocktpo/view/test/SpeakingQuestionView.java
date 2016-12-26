package com.mocktpo.view.test;

import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class SpeakingQuestionView extends StackTestView {

    /* Constants */

    private static final int SUB_VIEW_RECORDING = 1;
    private static final int SUB_VIEW_RESPONSE = 2;

    private static final int VIEW_PORT_PADDING_TOP = 100;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;
    private static final int FOOTNOTE_WIDTH = 360;
    private static final int TIMER_PANEL_WIDTH = 180;

    private static final int VIEW_PORT_PADDING_TOP_2 = 200;
    private static final int VIEW_PORT_PADDING_WIDTH_2 = 240;

    /* Widgets */

    private ImageButton confirmResponseButton;
    private VolumeControl volumeControl;

    private Composite recordingView;
    private Composite timerContainer;
    private CLabel timerHeader;
    private CLabel timerLabel;

    private Composite responseView;

    /* Properties */

    private int subViewId;

    private PropertyChangeListener listener;

    /* Audio Recorder Timer */

    private int preparationCountDown;
    private Timer preparationTimer;
    private TimerTask preparationTimerTask;
    private int recorderCountDown;
    private Timer audioRecorderTimer;
    private TimerTask audioRecorderTimerTask;

    /* Audio Recorder */

    private UserAudioRecorder audioRecorder;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SpeakingQuestionView(TestPage page, int style) {
        super(page, style);
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    @Override
    public void updateHeader() {

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRight(10).atTop(10);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        confirmResponseButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONFIRM_RESPONSE, MT.IMAGE_CONFIRM_RESPONSE_HOVER, MT.IMAGE_CONFIRM_RESPONSE_DISABLED);
        FormDataSet.attach(confirmResponseButton).atRightTo(vob, 16).atTopTo(vob, 8, SWT.TOP);
        confirmResponseButton.setEnabled(false);
        confirmResponseButton.addMouseListener(new ConfirmResponseButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());
    }


    @Override
    public void updateBody() {
        subViewId = SUB_VIEW_RECORDING;
        stack.topControl = getSubView(subViewId);
        body.layout();
    }

    private Composite getSubView(int subViewId) {

        switch (subViewId) {
            case SUB_VIEW_RECORDING:
                if (null == recordingView) {
                    recordingView = initRecordingSubView();
                }
                return recordingView;
            case SUB_VIEW_RESPONSE:
                if (null == responseView) {
                    responseView = initResponseSubView();
                }
                return responseView;
        }

        return null;
    }

    /*
     * ==================================================
     *
     * Sub Views
     *
     * ==================================================
     */

    private Composite initRecordingSubView() {

        final Composite c = new Composite(body, SWT.NONE);
        CompositeSet.decorate(c).setBackground(MT.COLOR_BEIGE);
        FormLayoutSet.layout(c);

        final ScrolledComposite sc = new ScrolledComposite(c, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite inner = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(inner).marginBottom(50);

        final Composite viewPort = new Composite(inner, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText qt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(qt).atLeft(20).atTop(VIEW_PORT_PADDING_TOP).atRight(20);
        StyledTextSet.decorate(qt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(qt, vo.getStyledText("question").getStyles());

        timerContainer = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(timerContainer).atLeft().atTopTo(qt, 20).atRight();
        CompositeSet.decorate(timerContainer).setVisible(false);
        FormLayoutSet.layout(timerContainer).marginWidth(1).marginHeight(1);

        final Label dl1 = new Label(timerContainer, SWT.NONE);
        FormDataSet.attach(dl1).atLeft().atTop().atRight().withHeight(2);
        LabelSet.decorate(dl1).setBackground(MT.COLOR_BLACK);

        final CLabel ptl = new CLabel(timerContainer, SWT.NONE);
        FormDataSet.attach(ptl).fromLeft(50, -FOOTNOTE_WIDTH / 2).atTopTo(dl1, 20).withWidth(FOOTNOTE_WIDTH);
        CLabelSet.decorate(ptl).setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM).setText("Preparation Time:\t\t" + vo.getPreparationTime() + " Seconds");

        final CLabel rtl = new CLabel(timerContainer, SWT.NONE);
        FormDataSet.attach(rtl).fromLeft(50, -FOOTNOTE_WIDTH / 2).atTopTo(ptl, 10).withWidth(FOOTNOTE_WIDTH);
        CLabelSet.decorate(rtl).setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM).setText("Response Time:\t\t" + vo.getResponseTime() + " Seconds");

        Composite timerPanel = new Composite(timerContainer, SWT.NONE);
        FormDataSet.attach(timerPanel).fromLeft(50, -TIMER_PANEL_WIDTH / 2).atTopTo(rtl, 30).withWidth(TIMER_PANEL_WIDTH);
        FormLayoutSet.layout(timerPanel).marginWidth(1).marginHeight(1);
        timerPanel.addPaintListener(new BorderedCompositePaintListener());

        timerHeader = new CLabel(timerPanel, SWT.CENTER);
        FormDataSet.attach(timerHeader).atLeft().atTop().atRight();
        CLabelSet.decorate(timerHeader).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_WHITE).setMargins(5).setText("");

        timerLabel = new CLabel(timerPanel, SWT.CENTER);
        FormDataSet.attach(timerLabel).atLeft().atTopTo(timerHeader).atRight();
        preparationCountDown = vo.getPreparationTime();
        CLabelSet.decorate(timerLabel).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(5).setText("");

        sc.setContent(inner);
        sc.setMinSize(inner.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return c;
    }

    private Composite initResponseSubView() {

        final Composite c = new Composite(body, SWT.NONE);
        CompositeSet.decorate(c).setBackground(MT.COLOR_BEIGE);
        FormLayoutSet.layout(c);

        final ScrolledComposite sc = new ScrolledComposite(c, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite inner = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(inner).marginBottom(50);

        final Composite viewPort = new Composite(inner, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x);
        FormLayoutSet.layout(viewPort);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH_2 * 2);
        FormLayoutSet.layout(viewPort);

        StyledText rt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(rt).atLeft().atTop(VIEW_PORT_PADDING_TOP_2).atRight();
        StyledTextSet.decorate(rt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("response").getText());
        StyleRangeUtils.decorate(rt, vo.getStyledText("response").getStyles());

        sc.setContent(inner);
        sc.setMinSize(inner.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return c;
    }

    /*
     * ==================================================
     *
     * Audio Async Execution
     *
     * ==================================================
     */

    @Override
    public void startAudioAsyncExecution() {
        listener = new AudioAsyncExecutionListener();
        audioPlayer.addPropertyChangeListener(listener);
    }

    @Override
    public void stopAudioAsyncExecution() {
        audioPlayer.removePropertyChangeListener(listener);
    }


    /*
     * ==================================================
     *
     * Audio Recording
     *
     * ==================================================
     */

    private void startPreparation() {

        /* Preparation Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTest(), vo.getPreparationAudio(), false);
        audioPlayer.setVolume(page.getUserTest().getVolume());
        audioPlayer.play();

        /* Beep Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTest(), vo.getBeepAudio(), false);
        audioPlayer.setVolume(page.getUserTest().getVolume());
        audioPlayer.play();

        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    CLabelSet.decorate(timerHeader).setText("PREPARATION TIME");
                    CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTime(preparationCountDown));
                }
            });
        }

        /* Preparation Timer */

        preparationTimer = new Timer();
        preparationTimerTask = new PreparationTimerTask();
        preparationTimer.scheduleAtFixedRate(preparationTimerTask, 0, 1000);
    }

    private void stopPreparation() {

        if (null != audioPlayer) {
            audioPlayer.stop();
        }
        if (null != preparationTimerTask) {
            preparationTimerTask.cancel();
        }
        if (null != preparationTimer) {
            preparationTimer.purge();
        }

        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    CLabelSet.decorate(timerHeader).setText("");
                    CLabelSet.decorate(timerLabel).setText("");
                }
            });
        }
    }

    private void startAudioRecording() {

        /* Response Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTest(), vo.getResponseAudio(), false);
        audioPlayer.setVolume(page.getUserTest().getVolume());
        audioPlayer.play();

        /* Audio Recorder */

        new Thread(new Runnable() {
            @Override
            public void run() {
                audioRecorder = new UserAudioRecorder(page.getUserTest(), "am");
                audioRecorder.start();
            }
        }).start();

        /* Beep Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTest(), vo.getBeepAudio(), false);
        audioPlayer.setVolume(page.getUserTest().getVolume());
        audioPlayer.play();

        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    CLabelSet.decorate(timerHeader).setText("RESPONSE TIME");
                    CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTime(recorderCountDown));
                }
            });
        }

        /* Audio Recorder Timer */

        recorderCountDown = vo.getResponseTime();
        audioRecorderTimer = new Timer();
        audioRecorderTimerTask = new RecorderTimerTask();
        audioRecorderTimer.scheduleAtFixedRate(audioRecorderTimerTask, 0, 1000);
    }

    private void stopAudioRecording() {

        if (null != audioRecorder) {
            audioRecorder.stop();
        }
        if (null != audioRecorderTimerTask) {
            audioRecorderTimerTask.cancel();
        }
        if (null != audioRecorderTimer) {
            audioRecorderTimer.purge();
        }

        if (subViewId == SUB_VIEW_RECORDING) {
            if (!d.isDisposed()) {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {

                        confirmResponseButton.setEnabled(true);

                        subViewId = SUB_VIEW_RESPONSE;
                        stack.topControl = getSubView(subViewId);
                        body.layout();
                    }
                });
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

    private class VolumeOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);

            UserTest ut = page.getUserTest();
            ut.setVolumeControlHidden(!volumeControlVisible);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class VolumeControlSelectionListener implements SelectionListener {

        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
        }

        @Override
        public void widgetSelected(SelectionEvent e) {

            Scale s = (Scale) e.widget;

            if (null != audioPlayer) {

                double selection = s.getSelection(), maximum = s.getMaximum();
                double volume = selection / maximum;

                UserTest ut = page.getUserTest();
                ut.setVolume(volume);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                audioPlayer.setVolume(volume);
            }
        }
    }

    private class ConfirmResponseButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            release();

            UserTest ut = page.getUserTest();
            ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
            ut.setLastViewId(vo.getViewId() + 1);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class AudioAsyncExecutionListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {

            if (audioPlayer.isStopped()) {

                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            CompositeSet.decorate(timerContainer).setVisible(true);
                        }
                    });
                }

                startPreparation();
            }
        }
    }

    /*
     * ==================================================
     *
     * Preparation Timer Task
     *
     * ==================================================
     */

    public class PreparationTimerTask extends TimerTask {

        @Override
        public void run() {

            if (!d.isDisposed()) {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTime(preparationCountDown--));
                    }
                });
            }

            if (0 >= preparationCountDown) {
                stopPreparation();
                startAudioRecording();
            }
        }
    }

    /*
     * ==================================================
     *
     * Recorder Timer Task
     *
     * ==================================================
     */

    public class RecorderTimerTask extends TimerTask {

        @Override
        public void run() {

            if (!d.isDisposed()) {
                d.asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTime(recorderCountDown--));
                    }
                });
            }

            if (0 >= recorderCountDown) {
                stopAudioRecording();
            }
        }
    }
}
