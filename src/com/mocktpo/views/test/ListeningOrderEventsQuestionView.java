package com.mocktpo.views.test;

import com.mocktpo.windows.RequiredAnswerWindow;
import com.mocktpo.events.BorderedCompositePaintListener;
import com.mocktpo.events.StyledTextPaintImageListener;
import com.mocktpo.pages.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.UserTestPersistenceUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.widgets.DroppableAnswerComposite;
import com.mocktpo.widgets.ImageButton;
import com.mocktpo.widgets.VolumeControl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

public class ListeningOrderEventsQuestionView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 50;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;

    private static final int ANSWER_1 = 1;
    private static final int ANSWER_2 = 2;
    private static final int ANSWER_3 = 3;

    /* Widgets */

    private ImageButton nextOvalButton, okOvalButton;
    private VolumeControl volumeControl;

    private Label checkLabelA, checkLabelB, checkLabelC;
    private StyledText tipsTextWidget;
    private Composite ac;

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

    public ListeningOrderEventsQuestionView(TestPage page, int style) {
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
                UserTestPersistenceUtils.saveToNextView(ListeningOrderEventsQuestionView.this);
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

        ac = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(ac).atLeft().atTopTo(tipsTextWidget, 20).atRight();
        CompositeSet.decorate(ac).setVisible(false);
        FormLayoutSet.layout(ac).marginWidth(1).marginHeight(1);

        final DroppableAnswerComposite blank1 = new DroppableAnswerComposite(ac, SWT.WRAP | SWT.TOP, ANSWER_1);
        FormDataSet.attach(blank1).atLeft().atTop().atRight().withHeight(LC.LISTENING_DND_QUESTION_HEIGHT);
        AnswerCompositeDropTargetSet.drop(blank1);
        blank1.addPaintListener(new BorderedCompositePaintListener());
        blank1.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
        blank1.addMouseListener(new AnswerCompositeMouseAdapter());

        final DroppableAnswerComposite blank2 = new DroppableAnswerComposite(ac, SWT.WRAP, ANSWER_2);
        FormDataSet.attach(blank2).atLeft().atTopTo(blank1, 10).atRight().withHeight(LC.LISTENING_DND_QUESTION_HEIGHT);
        AnswerCompositeDropTargetSet.drop(blank2);
        blank2.addPaintListener(new BorderedCompositePaintListener());
        blank2.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
        blank2.addMouseListener(new AnswerCompositeMouseAdapter());

        final DroppableAnswerComposite blank3 = new DroppableAnswerComposite(ac, SWT.WRAP, ANSWER_3);
        FormDataSet.attach(blank3).atLeft().atTopTo(blank2, 10).atRight().withHeight(LC.LISTENING_DND_QUESTION_HEIGHT);
        AnswerCompositeDropTargetSet.drop(blank3);
        blank3.addPaintListener(new BorderedCompositePaintListener());
        blank3.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
        blank3.addMouseListener(new AnswerCompositeMouseAdapter());

        final CLabel l = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(l).atLeft().atTopTo(blank3, 20).atRight();
        CLabelSet.decorate(l).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("answer_choices"));

        checkLabelA = new Label(ac, SWT.WRAP);
        FormDataSet.attach(checkLabelA).atLeft().atTopTo(l, 20).atRight();
        LabelSet.decorate(checkLabelA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceA").getText());
        ChoiceLabelDragSourceSet.drag(checkLabelA);

        checkLabelB = new Label(ac, SWT.WRAP);
        FormDataSet.attach(checkLabelB).atLeft().atTopTo(checkLabelA, 10).atRight();
        LabelSet.decorate(checkLabelB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceB").getText());
        ChoiceLabelDragSourceSet.drag(checkLabelB);

        checkLabelC = new Label(ac, SWT.WRAP);
        FormDataSet.attach(checkLabelC).atLeft().atTopTo(checkLabelB, 10).atRight();
        LabelSet.decorate(checkLabelC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceC").getText());
        ChoiceLabelDragSourceSet.drag(checkLabelC);
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
        return MT.CHOICE_NONE != answer1 && MT.CHOICE_NONE != answer2 && MT.CHOICE_NONE != answer3;
    }

    public boolean isNull() {
        return MT.CHOICE_NONE == answer1 && MT.CHOICE_NONE == answer2 && MT.CHOICE_NONE == answer3;
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
                UserTestPersistenceUtils.saveToNextView(ListeningOrderEventsQuestionView.this);
                page.resume();
            } else {
                nextOvalButton.setEnabled(true);
                okOvalButton.setEnabled(false);
                if (isNull()) {
                    RequiredAnswerWindow d = new RequiredAnswerWindow(MT.REQUIRED_ANSWER_DIALOG_TYPE_NO_ANSWER_FOR_MANY);
                    d.openAndWaitForDisposal();
                } else {
                    RequiredAnswerWindow d = new RequiredAnswerWindow(MT.REQUIRED_ANSWER_DIALOG_TYPE_INCORRECT_ANSWER_COUNT);
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
            UserTestPersistenceUtils.saveVolumeControlVisibility(ListeningOrderEventsQuestionView.this);
        }
    }

    private class VolumeControlSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            Scale s = (Scale) e.widget;
            double selection = s.getSelection(), maximum = s.getMaximum();
            double volume = selection / maximum;
            UserTestPersistenceUtils.saveVolume(ListeningOrderEventsQuestionView.this, volume);
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
                            nextOvalButton.setEnabled(true);
                            okOvalButton.setEnabled(false);
                            StyledTextSet.decorate(tipsTextWidget).setVisible(true);
                            ac.setVisible(true);
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

    private class AnswerCompositePropertyChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            int oldAnswer = (Integer) e.getOldValue();
            switch (oldAnswer) {
                case MT.CHOICE_A:
                    LabelSet.decorate(checkLabelA).setText(vo.getStyledText("checkLabelA").getText());
                    break;
                case MT.CHOICE_B:
                    LabelSet.decorate(checkLabelB).setText(vo.getStyledText("checkLabelB").getText());
                    break;
                case MT.CHOICE_C:
                    LabelSet.decorate(checkLabelC).setText(vo.getStyledText("checkLabelC").getText());
                    break;
            }
            int newAnswer = (Integer) e.getNewValue();
            DroppableAnswerComposite blank = (DroppableAnswerComposite) e.getSource();
            switch (blank.getId()) {
                case ANSWER_1:
                    answer1 = newAnswer;
                    break;
                case ANSWER_2:
                    answer2 = newAnswer;
                    break;
                case ANSWER_3:
                    answer3 = newAnswer;
                    break;
            }
            logger.info("[Listening Order Events Question {}] Answers: ({}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3);
            answerText = answer1 + MT.STRING_COMMA + answer2 + MT.STRING_COMMA + answer3;
            UserTestPersistenceUtils.saveAnswers(ListeningOrderEventsQuestionView.this, answerText);
        }
    }

    private class AnswerCompositeMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            Label answerLabel = (Label) e.widget;
            int answer = (Integer) answerLabel.getData(MT.KEY_CHOICE);
            switch (answer) {
                case MT.CHOICE_A:
                    LabelSet.decorate(checkLabelA).setText(vo.getStyledText("checkLabelA").getText());
                    break;
                case MT.CHOICE_B:
                    LabelSet.decorate(checkLabelB).setText(vo.getStyledText("checkLabelB").getText());
                    break;
                case MT.CHOICE_C:
                    LabelSet.decorate(checkLabelC).setText(vo.getStyledText("checkLabelC").getText());
                    break;
            }
            answerLabel.setText("");
            DroppableAnswerComposite blank = (DroppableAnswerComposite) answerLabel.getParent();
            blank.setAnswer(0);
        }
    }
}
