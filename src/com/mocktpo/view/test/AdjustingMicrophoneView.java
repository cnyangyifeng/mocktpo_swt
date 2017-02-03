package com.mocktpo.view.test;

import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.UserTestPersistenceUtils;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class AdjustingMicrophoneView extends StackTestView {

    /* Constants */

    private static final int SUB_VIEW_RECORDING = 1;
    private static final int SUB_VIEW_RESPONSE = 2;

    private static final int VIEW_PORT_PADDING_TOP = 50;
    private static final int VIEW_PORT_PADDING_WIDTH = 240;
    private static final int FOOTNOTE_TEXT_WIDTH = 360;
    private static final int TIMER_PANEL_WIDTH = 180;
    private static final int STOP_RECORDING_BUTTON_WIDTH = 74;

    private static final int VIEW_PORT_PADDING_TOP_2 = 100;

    /* Widgets */

    private ImageButton continueButton, recordAgainButton, playbackResponseButton;
    private VolumeControl volumeControl;

    private Composite recordingView;
    private Composite timerPanel;
    private CLabel timerLabel;
    private ImageButton stopRecordingButton;

    private Composite responseView;

    /* Properties */

    private int subViewId;

    private PropertyChangeListener listener;

    /* Audio Recorder Timer */

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

    public AdjustingMicrophoneView(TestPage page, int style) {
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

        final ImageButton volumeOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(volumeOvalButton).atRight(10).atTop(10);
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseListener());

        continueButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER, MT.IMAGE_CONTINUE_DISABLED);
        FormDataSet.attach(continueButton).atRightTo(volumeOvalButton, 16).atTopTo(volumeOvalButton, 8, SWT.TOP);
        continueButton.setEnabled(false);
        continueButton.addMouseListener(new ContinueButtonMouseListener());

        recordAgainButton = new ImageButton(header, SWT.NONE, MT.IMAGE_RECORD_AGAIN, MT.IMAGE_RECORD_AGAIN_HOVER, MT.IMAGE_RECORD_AGAIN_DISABLED);
        FormDataSet.attach(recordAgainButton).atRightTo(continueButton, 10).atTopTo(continueButton, 0, SWT.TOP);
        recordAgainButton.setEnabled(false);
        recordAgainButton.addMouseListener(new RecordAgainButtonMouseListener());

        playbackResponseButton = new ImageButton(header, SWT.NONE, MT.IMAGE_PLAYBACK_RESPONSE, MT.IMAGE_PLAYBACK_RESPONSE_HOVER, MT.IMAGE_PLAYBACK_RESPONSE_DISABLED);
        FormDataSet.attach(playbackResponseButton).atRightTo(recordAgainButton, 10).atTopTo(continueButton, 0, SWT.TOP);
        playbackResponseButton.setEnabled(false);
        playbackResponseButton.addMouseListener(new PlaybackResponseButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTestSession().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue button

        final ImageButton continueDebugButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(continueDebugButton).atRightTo(playbackResponseButton, 10).atTopTo(continueButton, 0, SWT.TOP);
        continueDebugButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                UserTestPersistenceUtils.saveToNextView(AdjustingMicrophoneView.this);
                page.resume();
            }
        });
    }

    @Override
    public void updateBody() {
        subViewId = SUB_VIEW_RECORDING;
        stack.topControl = getSubView(subViewId);
        body.layout();
    }

    protected Composite getSubView(int subViewId) {

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

        final StyledText ht = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_HEADING).setForeground(MT.COLOR_DARK_BLUE).setText(vo.getStyledText("heading").getText());

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTopTo(ht, 30).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("description").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("description").getStyles());

        final StyledText ft = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(ft).fromLeft(50, -FOOTNOTE_TEXT_WIDTH / 2).atTopTo(dt, 20).withWidth(FOOTNOTE_TEXT_WIDTH);
        StyledTextSet.decorate(ft).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SERIF_ITALIC_TEXT).setForeground(MT.COLOR_DARK_BLUE).setLineSpacing(5).setMarginHeight(20).setText(vo.getStyledText("footnote").getText());
        ft.addPaintListener(new BorderedCompositePaintListener());

        timerPanel = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(timerPanel).fromLeft(50, -TIMER_PANEL_WIDTH / 2).atTopTo(ft, 20).withWidth(TIMER_PANEL_WIDTH);
        CompositeSet.decorate(timerPanel).setVisible(false);
        FormLayoutSet.layout(timerPanel).marginWidth(1).marginHeight(1);
        timerPanel.addPaintListener(new BorderedCompositePaintListener());

        final CLabel timerHeader = new CLabel(timerPanel, SWT.CENTER);
        FormDataSet.attach(timerHeader).atLeft().atTop().atRight();
        CLabelSet.decorate(timerHeader).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_WHITE).setMargins(5).setText("RESPONSE TIME");

        timerLabel = new CLabel(timerPanel, SWT.CENTER);
        FormDataSet.attach(timerLabel).atLeft().atTopTo(timerHeader).atRight();
        recorderCountDown = vo.getResponseTime();
        CLabelSet.decorate(timerLabel).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(5).setText(TimeUtils.displayTime(recorderCountDown));

        stopRecordingButton = new ImageButton(viewPort, SWT.NONE, MT.IMAGE_STOP_RECORDING, MT.IMAGE_STOP_RECORDING_HOVER);
        FormDataSet.attach(stopRecordingButton).fromLeft(50, -STOP_RECORDING_BUTTON_WIDTH / 2).atTopTo(timerPanel, 20);
        stopRecordingButton.setVisible(false);
        stopRecordingButton.addMouseListener(new StopRecordingButtonMouseListener());

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

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
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
     * Native Resource Operations
     *
     * ==================================================
     */

    @Override
    protected void release() {
        super.release();
        stopAudioRecording();
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

    private void startAudioRecording() {

        /* Response Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTestSession(), vo.getResponseAudio(), false);
        audioPlayer.setVolume(page.getUserTestSession().getVolume());
        audioPlayer.play();

        /* Audio Recorder */

        new Thread(new Runnable() {
            @Override
            public void run() {
                audioRecorder = new UserAudioRecorder("am");
                audioRecorder.start();
            }
        }).start();

        /* Beep Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTestSession(), vo.getBeepAudio(), false);
        audioPlayer.setVolume(page.getUserTestSession().getVolume());
        audioPlayer.play();

        /* Audio Recorder Timer */

        audioRecorderTimer = new Timer();
        audioRecorderTimerTask = new RecorderTimerTask();
        audioRecorderTimer.scheduleAtFixedRate(audioRecorderTimerTask, 0, 1000);

        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    stopRecordingButton.setVisible(true);
                }
            });
        }
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

                        stopRecordingButton.setVisible(false);

                        continueButton.setEnabled(true);
                        recordAgainButton.setEnabled(true);
                        playbackResponseButton.setEnabled(true);

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
            UserTestPersistenceUtils.saveVolumeControlVisibility(AdjustingMicrophoneView.this);
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
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;

            UserTestPersistenceUtils.saveVolume(AdjustingMicrophoneView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class ContinueButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            release();
            UserTestPersistenceUtils.saveToNextView(AdjustingMicrophoneView.this);
            page.resume();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class RecordAgainButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (subViewId == SUB_VIEW_RESPONSE) {
                if (!d.isDisposed()) {
                    d.asyncExec(new Runnable() {
                        @Override
                        public void run() {

                            continueButton.setEnabled(false);
                            recordAgainButton.setEnabled(false);
                            playbackResponseButton.setEnabled(false);

                            recorderCountDown = vo.getResponseTime();
                            CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTime(recorderCountDown));

                            subViewId = SUB_VIEW_RECORDING;
                            stack.topControl = getSubView(subViewId);
                            body.layout();
                        }
                    });
                }
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    startAudioRecording();
                }
            }).start();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class PlaybackResponseButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            audioPlayer = new TestAudioPlayer(page.getUserTestSession(), "am", true);
            audioPlayer.setVolume(page.getUserTestSession().getVolume());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    audioPlayer.play();
                }
            }).start();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class StopRecordingButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            stopAudioRecording();
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
                            CompositeSet.decorate(timerPanel).setVisible(true);
                        }
                    });
                }

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
