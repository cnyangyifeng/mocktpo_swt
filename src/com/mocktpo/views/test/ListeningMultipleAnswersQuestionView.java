package com.mocktpo.views.test;

import com.mocktpo.events.StyledTextPaintImageListener;
import com.mocktpo.pages.TestPage;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.widgets.ImageButton;
import com.mocktpo.widgets.VolumeControl;
import com.mocktpo.windows.RequiredAnswerWindow;
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

public class ListeningMultipleAnswersQuestionView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 150;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;

    /* Widgets */

    private ImageButton nextOvalButton, okOvalButton;
    private VolumeControl volumeControl;

    private Label boxLabelA, boxLabelB, boxLabelC, boxLabelD, checkLabelE;
    private Label choiceLabelA, choiceLabelB, choiceLabelC, choiceLabelD, choiceLabelE;
    private StyledText tipsTextWidget;

    /* Properties */

    private int answer1, answer2, answer3;

    private PropertyChangeListener listener;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningMultipleAnswersQuestionView(TestPage page, int style) {
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

        // TODO Removes the continue debug button

        final ImageButton continueDebugButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(continueDebugButton).atRightTo(volumeOvalButton, 16).atTopTo(nextOvalButton, 8, SWT.TOP);
        continueDebugButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                PersistenceUtils.saveToNextView(ListeningMultipleAnswersQuestionView.this);
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

        tipsTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tipsTextWidget).atLeft().atTopTo(questionTextWidget, 20).atRight();
        StyledTextSet.decorate(tipsTextWidget).setAlignment(SWT.CENTER).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(5).setText(vo.getStyledText("tips").getText()).setVisible(false);
        StyleRangeUtils.decorate(tipsTextWidget, vo.getStyledText("tips").getStyles());

        boxLabelA = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(boxLabelA).atLeft(10).atTopTo(tipsTextWidget, 20 + LC.BOX_LABEL_MARGIN_TOP);
        LabelSet.decorate(boxLabelA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        boxLabelA.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelA = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelA).atLeftTo(boxLabelA, 10).atTopTo(tipsTextWidget, 20).atRight();
        LabelSet.decorate(choiceLabelA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceA").getText()).setVisible(false);
        choiceLabelA.addMouseListener(new ChooseAnswerAdapter());

        boxLabelB = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(boxLabelB).atLeft(10).atTopTo(choiceLabelA, 20 + LC.BOX_LABEL_MARGIN_TOP);
        LabelSet.decorate(boxLabelB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        boxLabelB.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelB = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelB).atLeftTo(boxLabelB, 10).atTopTo(choiceLabelA, 20).atRight();
        LabelSet.decorate(choiceLabelB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceB").getText()).setVisible(false);
        choiceLabelB.addMouseListener(new ChooseAnswerAdapter());

        boxLabelC = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(boxLabelC).atLeft(10).atTopTo(choiceLabelB, 20 + LC.BOX_LABEL_MARGIN_TOP);
        LabelSet.decorate(boxLabelC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        boxLabelC.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelC = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelC).atLeftTo(boxLabelC, 10).atTopTo(choiceLabelB, 20).atRight();
        LabelSet.decorate(choiceLabelC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceC").getText()).setVisible(false);
        choiceLabelC.addMouseListener(new ChooseAnswerAdapter());

        boxLabelD = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(boxLabelD).atLeft(10).atTopTo(choiceLabelC, 20 + LC.BOX_LABEL_MARGIN_TOP);
        LabelSet.decorate(boxLabelD).setData(MT.KEY_CHOICE, MT.CHOICE_D).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        boxLabelD.addMouseListener(new ChooseAnswerAdapter());

        choiceLabelD = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelD).atLeftTo(boxLabelD, 10).atTopTo(choiceLabelC, 20).atRight();
        LabelSet.decorate(choiceLabelD).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceD").getText()).setVisible(false);
        choiceLabelD.addMouseListener(new ChooseAnswerAdapter());

        if (3 == vo.getTotalAnswerCount()) {
            checkLabelE = new Label(viewPort, SWT.NONE);
            FormDataSet.attach(checkLabelE).atLeft(10).atTopTo(choiceLabelD, 20 + LC.BOX_LABEL_MARGIN_TOP);
            LabelSet.decorate(checkLabelE).setData(MT.KEY_CHOICE, MT.CHOICE_E).setImage(MT.IMAGE_UNBOXED).setVisible(false);
            checkLabelE.addMouseListener(new ChooseAnswerAdapter());

            choiceLabelE = new Label(viewPort, SWT.WRAP);
            FormDataSet.attach(choiceLabelE).atLeftTo(checkLabelE, 10).atTopTo(choiceLabelD, 20).atRight();
            LabelSet.decorate(choiceLabelE).setData(MT.KEY_CHOICE, MT.CHOICE_E).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceE").getText()).setVisible(false);
            choiceLabelE.addMouseListener(new ChooseAnswerAdapter());
        }

        updateWidgetsForAnswers();
    }

    private void updateWidgetsForAnswers() {
        if (StringUtils.isEmpty(answerText)) {
            answer1 = MT.CHOICE_NONE;
            answer2 = MT.CHOICE_NONE;
            if (3 == vo.getTotalAnswerCount()) {
                answer3 = MT.CHOICE_NONE;
            }
        } else {
            String[] arr = answerText.split(MT.STRING_COMMA);
            switch (arr.length) {
                case 2:
                    answer1 = Integer.parseInt(arr[0]);
                    markWidgetsForAnswers(answer1);
                    answer2 = Integer.parseInt(arr[1]);
                    markWidgetsForAnswers(answer2);
                    break;
                case 3:
                    answer1 = Integer.parseInt(arr[0]);
                    markWidgetsForAnswers(answer1);
                    answer2 = Integer.parseInt(arr[1]);
                    markWidgetsForAnswers(answer2);
                    answer3 = Integer.parseInt(arr[2]);
                    markWidgetsForAnswers(answer3);
                    break;
            }
        }
        logger.info("[Listening Multiple-Answers Question {}] Answers: ({}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3);
    }

    private void markWidgetsForAnswers(int answer) {
        switch (answer) {
            case MT.CHOICE_NONE:
                break;
            case MT.CHOICE_A:
                LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_BOXED);
                break;
            case MT.CHOICE_B:
                LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_BOXED);
                break;
            case MT.CHOICE_C:
                LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_BOXED);
                break;
            case MT.CHOICE_D:
                LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_BOXED);
                break;
            case MT.CHOICE_E:
                LabelSet.decorate(checkLabelE).setImage(MT.IMAGE_BOXED);
                break;
        }
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
        boolean b = false;
        switch (vo.getTotalAnswerCount()) {
            case 2:
                b = (MT.CHOICE_NONE != answer1 && MT.CHOICE_NONE != answer2);
                break;
            case 3:
                b = (MT.CHOICE_NONE != answer1 && MT.CHOICE_NONE != answer2 && MT.CHOICE_NONE != answer3);
                break;
        }
        return b;
    }

    public boolean isNull() {
        boolean b = false;
        switch (vo.getTotalAnswerCount()) {
            case 2:
                b = (MT.CHOICE_NONE == answer1 && MT.CHOICE_NONE == answer2);
                break;
            case 3:
                b = (MT.CHOICE_NONE == answer1 && MT.CHOICE_NONE == answer2 && MT.CHOICE_NONE == answer3);
                break;
        }
        return b;
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
                PersistenceUtils.saveToNextView(ListeningMultipleAnswersQuestionView.this);
                page.resume();
            } else {
                nextOvalButton.setEnabled(true);
                okOvalButton.setEnabled(false);
                if (isNull()) {
                    RequiredAnswerWindow d = new RequiredAnswerWindow(MT.REQUIRED_ANSWER_WINDOW_TYPE_NO_ANSWER_FOR_MANY);
                    d.openAndWaitForDisposal();
                } else {
                    RequiredAnswerWindow d = new RequiredAnswerWindow(MT.REQUIRED_ANSWER_WINDOW_TYPE_INCORRECT_ANSWER_COUNT);
                    d.openAndWaitForDisposal();
                }
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
            PersistenceUtils.saveVolumeControlVisibility(ListeningMultipleAnswersQuestionView.this);
        }
    }

    private class VolumeControlSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;
            PersistenceUtils.saveVolume(ListeningMultipleAnswersQuestionView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class ChooseAnswerAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int a = (Integer) e.widget.getData(MT.KEY_CHOICE);
            if (2 == vo.getTotalAnswerCount()) {
                if (a == answer1) {
                    answer1 = MT.CHOICE_NONE;
                } else if (a == answer2) {
                    answer2 = MT.CHOICE_NONE;
                } else {
                    if (MT.CHOICE_NONE == answer1) {
                        answer1 = a;
                    } else {
                        if (MT.CHOICE_NONE == answer2) {
                            answer2 = a;
                        } else {
                            nextOvalButton.setEnabled(true);
                            okOvalButton.setEnabled(false);
                            new RequiredAnswerWindow(MT.REQUIRED_ANSWER_WINDOW_TYPE_INCORRECT_ANSWER_COUNT).openAndWaitForDisposal();
                        }
                    }
                }
                LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_UNBOXED);
                switch (answer1) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_BOXED);
                        break;
                }
                switch (answer2) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_BOXED);
                        break;
                }
                logger.info("[Listening Multiple-Answers Question {}] Answers: ({}, {})", vo.getQuestionNumberInSection(), answer1, answer2);
                answerText = answer1 + MT.STRING_COMMA + answer2;
            } else if (3 == vo.getTotalAnswerCount()) {
                if (a == answer1) {
                    answer1 = MT.CHOICE_NONE;
                } else if (a == answer2) {
                    answer2 = MT.CHOICE_NONE;
                } else if (a == answer3) {
                    answer3 = MT.CHOICE_NONE;
                } else {
                    if (MT.CHOICE_NONE == answer1) {
                        answer1 = a;
                    } else {
                        if (MT.CHOICE_NONE == answer2) {
                            answer2 = a;
                        } else {
                            if (MT.CHOICE_NONE == answer3) {
                                answer3 = a;
                            } else {
                                nextOvalButton.setEnabled(true);
                                okOvalButton.setEnabled(false);
                                new RequiredAnswerWindow(MT.REQUIRED_ANSWER_WINDOW_TYPE_INCORRECT_ANSWER_COUNT).openAndWaitForDisposal();
                            }
                        }
                    }
                }
                LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(checkLabelE).setImage(MT.IMAGE_UNBOXED);
                switch (answer1) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_E:
                        LabelSet.decorate(checkLabelE).setImage(MT.IMAGE_BOXED);
                        break;
                }
                switch (answer2) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_E:
                        LabelSet.decorate(checkLabelE).setImage(MT.IMAGE_BOXED);
                        break;
                }
                switch (answer3) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(boxLabelA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(boxLabelB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(boxLabelC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(boxLabelD).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_E:
                        LabelSet.decorate(checkLabelE).setImage(MT.IMAGE_BOXED);
                        break;
                }
                logger.info("[Listening Multiple-Answers Question {}] Answers: ({}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3);
                answerText = answer1 + MT.STRING_COMMA + answer2 + MT.STRING_COMMA + answer3;
            }
            PersistenceUtils.saveAnswer(ListeningMultipleAnswersQuestionView.this, answerText);
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
                            StyledTextSet.decorate(tipsTextWidget).setVisible(true);
                            LabelSet.decorate(boxLabelA).setVisible(true);
                            LabelSet.decorate(choiceLabelA).setVisible(true);
                            LabelSet.decorate(boxLabelB).setVisible(true);
                            LabelSet.decorate(choiceLabelB).setVisible(true);
                            LabelSet.decorate(boxLabelC).setVisible(true);
                            LabelSet.decorate(choiceLabelC).setVisible(true);
                            LabelSet.decorate(boxLabelD).setVisible(true);
                            LabelSet.decorate(choiceLabelD).setVisible(true);
                            if (3 == vo.getTotalAnswerCount()) {
                                LabelSet.decorate(checkLabelE).setVisible(true);
                                LabelSet.decorate(choiceLabelE).setVisible(true);
                            }
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
