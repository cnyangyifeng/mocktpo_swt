package com.mocktpo.views.test;

import com.mocktpo.events.BorderedCompositePaintListener;
import com.mocktpo.pages.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.UserTestPersistenceUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.widgets.ImageButton;
import com.mocktpo.widgets.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class SpeakingTaskView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 100;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;
    private static final int FOOTNOTE_WIDTH = 360;
    private static final int TIMER_PANEL_WIDTH = 180;

    /* Widgets */

    private VolumeControl volumeControl;

    private Composite timerContainer;
    private CLabel timerHeader;
    private CLabel timerLabel;

    /* Properties */

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

    public SpeakingTaskView(TestPage page, int style) {
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
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseAdapter());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTestSession().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionAdapter());

        // TODO Removes the continue debug button

        final ImageButton continueDebugButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(continueDebugButton).atRightTo(volumeOvalButton, 16).atTopTo(volumeOvalButton, 8, SWT.TOP);
        continueDebugButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                UserTestPersistenceUtils.saveToNextView(SpeakingTaskView.this);
                page.resume();
            }
        });
    }

    @Override
    public void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText questionTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeft(20).atTop(VIEW_PORT_PADDING_TOP).atRight(20);
        StyledTextSet.decorate(questionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(questionTextWidget, vo.getStyledText("question").getStyles());

        timerContainer = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(timerContainer).atLeft().atTopTo(questionTextWidget, 20).atRight();
        CompositeSet.decorate(timerContainer).setVisible(false);
        FormLayoutSet.layout(timerContainer).marginWidth(1).marginHeight(1);

        final Label divider = new Label(timerContainer, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTop().atRight().withHeight(2);
        LabelSet.decorate(divider).setBackground(MT.COLOR_BLACK);

        final CLabel preparationTimeLabel = new CLabel(timerContainer, SWT.NONE);
        FormDataSet.attach(preparationTimeLabel).fromLeft(50, -FOOTNOTE_WIDTH / 2).atTopTo(divider, 20).withWidth(FOOTNOTE_WIDTH);
        CLabelSet.decorate(preparationTimeLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM).setText("Preparation Time:\t\t" + vo.getPreparationTime() + " Seconds");

        final CLabel responseTimeLabel = new CLabel(timerContainer, SWT.NONE);
        FormDataSet.attach(responseTimeLabel).fromLeft(50, -FOOTNOTE_WIDTH / 2).atTopTo(preparationTimeLabel, 10).withWidth(FOOTNOTE_WIDTH);
        CLabelSet.decorate(responseTimeLabel).setAlignment(SWT.CENTER).setFont(MT.FONT_MEDIUM).setText("Response Time:\t\t" + vo.getResponseTime() + " Seconds");

        Composite timerPanel = new Composite(timerContainer, SWT.NONE);
        FormDataSet.attach(timerPanel).fromLeft(50, -TIMER_PANEL_WIDTH / 2).atTopTo(responseTimeLabel, 30).withWidth(TIMER_PANEL_WIDTH);
        FormLayoutSet.layout(timerPanel).marginWidth(1).marginHeight(1);
        timerPanel.addPaintListener(new BorderedCompositePaintListener());

        timerHeader = new CLabel(timerPanel, SWT.CENTER);
        FormDataSet.attach(timerHeader).atLeft().atTop().atRight();
        CLabelSet.decorate(timerHeader).setBackground(MT.COLOR_BLACK).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_WHITE).setMargins(5).setText("");

        timerLabel = new CLabel(timerPanel, SWT.CENTER);
        FormDataSet.attach(timerLabel).atLeft().atTopTo(timerHeader).atRight();
        preparationCountDown = vo.getPreparationTime();
        CLabelSet.decorate(timerLabel).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(5).setText("");
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
        stopTimer();
        stopAudio();
        stopAudioVisualization();
        stopAudioAsyncExecution();
        stopPreparation();
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

    private void startPreparation() {

        /* Preparation Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTestSession(), vo.getPreparationAudio(), false);
        audioPlayer.setVolume(page.getUserTestSession().getVolume());
        audioPlayer.play();

        /* Beep Audio */

        audioPlayer = new TestAudioPlayer(page.getUserTestSession(), vo.getBeepAudio(), false);
        audioPlayer.setVolume(page.getUserTestSession().getVolume());
        audioPlayer.play();

        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    CLabelSet.decorate(timerHeader).setText("PREPARATION TIME");
                    CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTimePeriod(preparationCountDown));
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

        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    CLabelSet.decorate(timerHeader).setText("RESPONSE TIME");
                    CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTimePeriod(recorderCountDown));
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
    }

    private void goToNextTestView() {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    release();
                    UserTestPersistenceUtils.saveToNextView(SpeakingTaskView.this);
                    page.resume();
                }
            });
        }
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class VolumeOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
            UserTestPersistenceUtils.saveVolumeControlVisibility(SpeakingTaskView.this);
        }
    }

    private class VolumeControlSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;
            UserTestPersistenceUtils.saveVolume(SpeakingTaskView.this, volume);
            setAudioVolume(volume);
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
                        CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTimePeriod(preparationCountDown--));
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
                        CLabelSet.decorate(timerLabel).setText(TimeUtils.displayTimePeriod(recorderCountDown--));
                    }
                });
            }
            if (0 >= recorderCountDown) {
                stopAudioRecording();
                goToNextTestView();
            }
        }
    }
}
