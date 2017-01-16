package com.mocktpo.view.test;

import com.mocktpo.dialog.RequiredAnswerDialog;
import com.mocktpo.listener.StyledTextPaintImageListener;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.UserTestPersistenceUtils;
import com.mocktpo.widget.ImageButton;
import com.mocktpo.widget.VolumeControl;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
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

    private Label checkWidgetA, checkWidgetB, checkWidgetC, checkWidgetD;
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
        nextOvalButton.addMouseListener(new NextOvalButtonMouseListener());

        okOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_OK_OVAL, MT.IMAGE_OK_OVAL_HOVER, MT.IMAGE_OK_OVAL_DISABLED);
        FormDataSet.attach(okOvalButton).atRightTo(nextOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        okOvalButton.setEnabled(false);
        okOvalButton.addMouseListener(new OkOvalButtonMouseListener());

        final ImageButton helpOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(helpOvalButton).atRightTo(okOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        helpOvalButton.addMouseListener(new HelpOvalButtonMouseListener());

        final ImageButton volumeOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(volumeOvalButton).atRightTo(helpOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        volumeOvalButton.addMouseListener(new VolumeOvalButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(volumeOvalButton, 0, SWT.BOTTOM).atRightTo(volumeOvalButton, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue debug button

        final ImageButton continueDebugButton = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE_DEBUG, MT.IMAGE_CONTINUE_DEBUG_HOVER);
        FormDataSet.attach(continueDebugButton).atRightTo(volumeOvalButton, 16).atTopTo(nextOvalButton, 8, SWT.TOP);
        continueDebugButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                release();
                UserTestPersistenceUtils.saveToNextView(ListeningQuestionView.this);
                page.resume(page.getUserTest());
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

        checkWidgetA = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkWidgetA).atLeft(5).atTopTo(questionTextWidget, 25);
        LabelSet.decorate(checkWidgetA).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkWidgetA.addMouseListener(new ChooseAnswerListener());

        choiceLabelA = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelA).atLeftTo(checkWidgetA, 5).atTopTo(questionTextWidget, 20).atRight();
        LabelSet.decorate(choiceLabelA).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceA").getText()).setVisible(false);
        choiceLabelA.addMouseListener(new ChooseAnswerListener());

        checkWidgetB = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkWidgetB).atLeft(5).atTopTo(choiceLabelA, 25);
        LabelSet.decorate(checkWidgetB).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkWidgetB.addMouseListener(new ChooseAnswerListener());

        choiceLabelB = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelB).atLeftTo(checkWidgetB, 5).atTopTo(choiceLabelA, 20).atRight();
        LabelSet.decorate(choiceLabelB).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceB").getText()).setVisible(false);
        choiceLabelB.addMouseListener(new ChooseAnswerListener());

        checkWidgetC = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkWidgetC).atLeft(5).atTopTo(choiceLabelB, 25);
        LabelSet.decorate(checkWidgetC).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkWidgetC.addMouseListener(new ChooseAnswerListener());

        choiceLabelC = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelC).atLeftTo(checkWidgetC, 5).atTopTo(choiceLabelB, 20).atRight();
        LabelSet.decorate(choiceLabelC).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceC").getText()).setVisible(false);
        choiceLabelC.addMouseListener(new ChooseAnswerListener());

        checkWidgetD = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(checkWidgetD).atLeft(5).atTopTo(choiceLabelC, 25);
        LabelSet.decorate(checkWidgetD).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
        checkWidgetD.addMouseListener(new ChooseAnswerListener());

        choiceLabelD = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceLabelD).atLeftTo(checkWidgetD, 5).atTopTo(choiceLabelC, 20).atRight();
        LabelSet.decorate(choiceLabelD).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceD").getText()).setVisible(false);
        choiceLabelD.addMouseListener(new ChooseAnswerListener());

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
                LabelSet.decorate(checkWidgetA).setImage(MT.IMAGE_CHECKED);
                break;
            case MT.CHOICE_B:
                LabelSet.decorate(checkWidgetB).setImage(MT.IMAGE_CHECKED);
                break;
            case MT.CHOICE_C:
                LabelSet.decorate(checkWidgetC).setImage(MT.IMAGE_CHECKED);
                break;
            case MT.CHOICE_D:
                LabelSet.decorate(checkWidgetD).setImage(MT.IMAGE_CHECKED);
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

    private class NextOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            nextOvalButton.setEnabled(false);
            okOvalButton.setEnabled(true);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class OkOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (isOk()) {
                release();
                UserTestPersistenceUtils.saveToNextView(ListeningQuestionView.this);
                page.resume(page.getUserTest());
            } else {
                nextOvalButton.setEnabled(true);
                okOvalButton.setEnabled(false);
                RequiredAnswerDialog d = new RequiredAnswerDialog(MT.REQUIRED_ANSWER_DIALOG_TYPE_NO_ANSWER_FOR_ONE);
                d.openAndWaitForDisposal();
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class HelpOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class VolumeOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            volumeControlVisible = !volumeControlVisible;
            CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
            UserTestPersistenceUtils.saveVolumeControlVisibility(ListeningQuestionView.this);
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

            UserTestPersistenceUtils.saveVolume(ListeningQuestionView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class ChooseAnswerListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            int a = (Integer) e.widget.getData(MT.KEY_CHOICE);

            if (a == answer) {
                answer = MT.CHOICE_NONE;
                LabelSet.decorate(checkWidgetA).setImage(MT.IMAGE_UNCHECKED);
                LabelSet.decorate(checkWidgetB).setImage(MT.IMAGE_UNCHECKED);
                LabelSet.decorate(checkWidgetC).setImage(MT.IMAGE_UNCHECKED);
                LabelSet.decorate(checkWidgetD).setImage(MT.IMAGE_UNCHECKED);
            } else {
                answer = a;
                switch (answer) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(checkWidgetA).setImage(MT.IMAGE_CHECKED);
                        LabelSet.decorate(checkWidgetB).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetC).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetD).setImage(MT.IMAGE_UNCHECKED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(checkWidgetA).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetB).setImage(MT.IMAGE_CHECKED);
                        LabelSet.decorate(checkWidgetC).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetD).setImage(MT.IMAGE_UNCHECKED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(checkWidgetA).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetB).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetC).setImage(MT.IMAGE_CHECKED);
                        LabelSet.decorate(checkWidgetD).setImage(MT.IMAGE_UNCHECKED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(checkWidgetA).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetB).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetC).setImage(MT.IMAGE_UNCHECKED);
                        LabelSet.decorate(checkWidgetD).setImage(MT.IMAGE_CHECKED);
                        break;
                }
            }

            logger.info("[Listening Question {}] Answer: {}", vo.getQuestionNumberInSection(), answer);

            answerText = Integer.toString(answer);
            UserTestPersistenceUtils.saveAnswers(ListeningQuestionView.this, answerText);
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

                            nextOvalButton.setEnabled(true);
                            okOvalButton.setEnabled(false);

                            LabelSet.decorate(checkWidgetA).setVisible(true);
                            LabelSet.decorate(choiceLabelA).setVisible(true);
                            LabelSet.decorate(checkWidgetB).setVisible(true);
                            LabelSet.decorate(choiceLabelB).setVisible(true);
                            LabelSet.decorate(checkWidgetC).setVisible(true);
                            LabelSet.decorate(choiceLabelC).setVisible(true);
                            LabelSet.decorate(checkWidgetD).setVisible(true);
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

                    countDown = page.getUserTest().getRemainingViewTime(vo);
                    timer = new Timer();
                    timerTask = new TestTimerTask();
                    timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }
            }
        }
    }
}
