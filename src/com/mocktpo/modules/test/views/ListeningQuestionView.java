package com.mocktpo.modules.test.views;

import com.mocktpo.windows.RequiredAnswerWindow;
import com.mocktpo.modules.test.listeners.StyledTextPaintImageListener;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.test.widgets.VolumeControl;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

public class ListeningQuestionView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 150;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;

    /* Widgets */

    private ImageButton nextOvalButton, okOvalButton;
    private VolumeControl volumeControl;

    private Label checkLabelA, checkLabelB, checkLabelC, checkLabelD;
    private Label choiceLabelA, choiceLabelB, choiceLabelC, choiceLabelD;

    /* Properties */

    private int answer;

    private PropertyChangeListener listener;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningQuestionView(TestPage page, int style) {
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
        nextOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nextOvalButton).atRight(10).atTop(10);
        nextOvalButton.setEnabled(false);
        nextOvalButton.addMouseListener(new NextOvalButtonMouseAdapter());

        okOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_OK_OVAL, MT.IMAGE_OK_OVAL_HOVER, MT.IMAGE_OK_OVAL_DISABLED);
        FormDataSet.attach(okOvalButton).atRightTo(nextOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        okOvalButton.setEnabled(false);
        okOvalButton.addMouseListener(new OkOvalButtonMouseAdapter());

        final ImageButton helpOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(helpOvalButton).atRightTo(okOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        helpOvalButton.addMouseListener(new HelpOvalButtonMouseAdapter());

        final ImageButton volumeOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(volumeOvalButton).atRightTo(helpOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseAdapter());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTestSession().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionAdapter());

        final ImageButton skipButton = new ImageButton(header, SWT.NONE, MT.IMAGE_SKIP, MT.IMAGE_SKIP_HOVER);
        FormDataSet.attach(skipButton).atRightTo(volumeOvalButton, 16).atTopTo(nextOvalButton, 8, SWT.TOP);
        skipButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                PersistenceUtils.saveToNextView(ListeningQuestionView.this);
                page.resume();
            }
        });
    }

    @Override
    public void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText questionTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(questionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(questionTextWidget, vo.getStyledText("question").getStyles());
        questionTextWidget.addPaintObjectListener(new StyledTextPaintImageListener());

        checkLabelA = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkLabelA).atLeft(10).atTopTo(questionTextWidget, 20);
        LabelSet.decorate(checkLabelA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkLabelA.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelA = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelA).atLeftTo(checkLabelA, 10).atTopTo(questionTextWidget, 20).atRight();
        LabelSet.decorate(choiceLabelA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceA").getText()).setVisible(false);
        choiceLabelA.addMouseListener(new ChooseAnswerAdapter());

        checkLabelB = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkLabelB).atLeft(10).atTopTo(choiceLabelA, 20);
        LabelSet.decorate(checkLabelB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkLabelB.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelB = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelB).atLeftTo(checkLabelB, 10).atTopTo(choiceLabelA, 20).atRight();
        LabelSet.decorate(choiceLabelB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceB").getText()).setVisible(false);
        choiceLabelB.addMouseListener(new ChooseAnswerAdapter());

        checkLabelC = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkLabelC).atLeft(10).atTopTo(choiceLabelB, 20);
        LabelSet.decorate(checkLabelC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkLabelC.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelC = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelC).atLeftTo(checkLabelC, 10).atTopTo(choiceLabelB, 20).atRight();
        LabelSet.decorate(choiceLabelC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceC").getText()).setVisible(false);
        choiceLabelC.addMouseListener(new ChooseAnswerAdapter());

        checkLabelD = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkLabelD).atLeft(10).atTopTo(choiceLabelC, 20);
        LabelSet.decorate(checkLabelD).setData(MT.KEY_CHOICE, MT.CHOICE_D).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkLabelD.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelD = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelD).atLeftTo(checkLabelD, 10).atTopTo(choiceLabelC, 20).atRight();
        LabelSet.decorate(choiceLabelD).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceD").getText()).setVisible(false);
        choiceLabelD.addMouseListener(new ChooseAnswerAdapter());

        updateWidgetsForAnswer();
    }

    private void updateWidgetsForAnswer() {
        if (StringUtils.isEmpty(answerText)) {
            answer = MT.CHOICE_NONE;
        } else {
            answer = Integer.parseInt(answerText);
        }
        switch (answer) {
            case MT.CHOICE_NONE:
                break;
            case MT.CHOICE_A:
                LabelSet.decorate(checkLabelA).setImage(MT.IMAGE_CHECKED);
                break;
            case MT.CHOICE_B:
                LabelSet.decorate(checkLabelB).setImage(MT.IMAGE_CHECKED);
                break;
            case MT.CHOICE_C:
                LabelSet.decorate(checkLabelC).setImage(MT.IMAGE_CHECKED);
                break;
            case MT.CHOICE_D:
                LabelSet.decorate(checkLabelD).setImage(MT.IMAGE_CHECKED);
                break;
        }
        logger.info("[Listening Question {}] Answer: {}", vo.getQuestionNumberInSection(), answer);
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
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    timerButton.setEnabled(false);
                }
            });
        }
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
     * Answer Status
     *
     * ==================================================
     */

    public boolean isOk() {
        return MT.CHOICE_NONE != answer;
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class NextOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            nextOvalButton.setEnabled(false);
            okOvalButton.setEnabled(true);
        }
    }

    private class OkOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {

            if (isOk()) {
                release();
                PersistenceUtils.saveToNextView(ListeningQuestionView.this);
                page.resume();
            } else {
                nextOvalButton.setEnabled(true);
                okOvalButton.setEnabled(false);
                RequiredAnswerWindow d = new RequiredAnswerWindow(MT.REQUIRED_ANSWER_WINDOW_TYPE_NO_ANSWER_FOR_ONE);
                d.openAndWaitForDisposal();
            }
        }
    }

    private class HelpOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
        }
    }

    private class VolumeOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
            PersistenceUtils.saveVolumeControlVisibility(ListeningQuestionView.this);
        }
    }

    private class VolumeControlSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;
            PersistenceUtils.saveVolume(ListeningQuestionView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class ChooseAnswerAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int a = (Integer) e.widget.getData(MT.KEY_CHOICE);
            if (a == answer) {
                answer = MT.CHOICE_NONE;
                LabelSet.decorate(checkLabelA).setImage(MT.IMAGE_UNCHECKED);
                LabelSet.decorate(checkLabelB).setImage(MT.IMAGE_UNCHECKED);
                LabelSet.decorate(checkLabelC).setImage(MT.IMAGE_UNCHECKED);
                LabelSet.decorate(checkLabelD).setImage(MT.IMAGE_UNCHECKED);
            } else {
                answer = a;
                switch (answer) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(checkLabelA).setImage(MT.IMAGE_CHECKED);
                        LabelSet.decorate(checkLabelB).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelC).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelD).setImage(MT.IMAGE_UNCHECKED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(checkLabelA).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelB).setImage(MT.IMAGE_CHECKED);
                        LabelSet.decorate(checkLabelC).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelD).setImage(MT.IMAGE_UNCHECKED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(checkLabelA).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelB).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelC).setImage(MT.IMAGE_CHECKED);
                        LabelSet.decorate(checkLabelD).setImage(MT.IMAGE_UNCHECKED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(checkLabelA).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelB).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelC).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkLabelD).setImage(MT.IMAGE_CHECKED);
                        break;
                }
            }
            logger.info("[Listening Question {}] Answer: {}", vo.getQuestionNumberInSection(), answer);
            answerText = Integer.toString(answer);
            PersistenceUtils.saveAnswer(ListeningQuestionView.this, answerText);
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
                            nextOvalButton.setEnabled(true);
                            okOvalButton.setEnabled(false);
                            LabelSet.decorate(checkLabelA).setVisible(true);
                            LabelSet.decorate(choiceLabelA).setVisible(true);
                            LabelSet.decorate(checkLabelB).setVisible(true);
                            LabelSet.decorate(choiceLabelB).setVisible(true);
                            LabelSet.decorate(checkLabelC).setVisible(true);
                            LabelSet.decorate(choiceLabelC).setVisible(true);
                            LabelSet.decorate(checkLabelD).setVisible(true);
                            LabelSet.decorate(choiceLabelD).setVisible(true);
                        }
                    });
                }
                /*
                 * ==================================================
                 *
                 * Schedules a new Timer and a Timer Task here if the
                 * view is marked as timerTaskDelayed, rather than
                 * TestView.startTimer().
                 *
                 * ==================================================
                 */
                if (vo.isTimerTaskDelayed()) {
                    if (!d.isDisposed()) {
                        d.asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                timerButton.setEnabled(true);
                            }
                        });
                    }
                    countDown = page.getUserTestSession().getRemainingViewTime(vo);
                    timer = new Timer();
                    timerTask = new TestTimerTask();
                    timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }
            }
        }
    }
}
