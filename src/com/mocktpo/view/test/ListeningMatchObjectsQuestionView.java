package com.mocktpo.view.test;

import com.mocktpo.dialog.RequiredAnswerDialog;
import com.mocktpo.listener.BorderedCompositePaintListener;
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
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

public class ListeningMatchObjectsQuestionView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 100;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;
    private static final int CHECK_MARK_LABEL_WIDTH = 50;

    private static final String CHECK_MARK = "\u2713";

    /* Widgets */

    private ImageButton nextOvalButton, okOvalButton;
    private VolumeControl volumeControl;

    private StyledText tipsTextWidget;
    private Composite ac;
    private Label yesLabel1, noLabel1, yesLabel2, noLabel2, yesLabel3, noLabel3, yesLabel4, noLabel4, yesLabel5, noLabel5;

    /* Properties */

    private int answer1, answer2, answer3, answer4, answer5;

    private PropertyChangeListener listener;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningMatchObjectsQuestionView(TestPage page, int style) {
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
                UserTestPersistenceUtils.saveToNextView(ListeningMatchObjectsQuestionView.this);
                page.resume();
            }
        });
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText directionsTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(directionsTextWidget).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(directionsTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("directions").getText());
        StyleRangeUtils.decorate(directionsTextWidget, vo.getStyledText("directions").getStyles());
        directionsTextWidget.addPaintObjectListener(new StyledTextPaintImageListener());

        tipsTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tipsTextWidget).atLeft().atTopTo(directionsTextWidget, 20).atRight();
        StyledTextSet.decorate(tipsTextWidget).setAlignment(SWT.CENTER).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("tips").getText()).setVisible(false);
        StyleRangeUtils.decorate(tipsTextWidget, vo.getStyledText("tips").getStyles());

        ac = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(ac).atLeft().atTopTo(tipsTextWidget, 20).atRight();
        CompositeSet.decorate(ac).setVisible(false);
        FormLayoutSet.layout(ac).marginWidth(1).marginHeight(1);
        ac.addPaintListener(new BorderedCompositePaintListener());

        final StyledText questionTextHeader = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(questionTextHeader).atLeft().atTop().fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(questionTextHeader).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10);

        final Label verticalDivider1 = new Label(ac, SWT.VERTICAL);
        FormDataSet.attach(verticalDivider1).atLeftTo(questionTextHeader).atTop().atBottom().withWidth(1);
        LabelSet.decorate(verticalDivider1).setBackground(MT.COLOR_GRAY40);

        final CLabel yesTextHeader = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(yesTextHeader).atLeftTo(verticalDivider1).atTopTo(questionTextHeader, 0, SWT.TOP).atBottomTo(questionTextHeader, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        CLabelSet.decorate(yesTextHeader).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("yes"));
        yesTextHeader.addMouseListener(new ChooseAnswerListener());

        final Label verticalDivider2 = new Label(ac, SWT.VERTICAL);
        FormDataSet.attach(verticalDivider2).atLeftTo(yesTextHeader).atTop().atBottom().withWidth(1);
        LabelSet.decorate(verticalDivider2).setBackground(MT.COLOR_GRAY40);

        final CLabel noTextHeader = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(noTextHeader).atLeftTo(verticalDivider2).atTopTo(questionTextHeader, 0, SWT.TOP).atBottomTo(questionTextHeader, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(noTextHeader).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("no"));
        noTextHeader.addMouseListener(new ChooseAnswerListener());

        final Label divider1 = new Label(ac, SWT.NONE);
        FormDataSet.attach(divider1).atLeft().atTopTo(questionTextHeader).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_GRAY40);

        final StyledText questionTextWidget1 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(questionTextWidget1).atLeft().atTopTo(divider1).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(questionTextWidget1).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question1").getText());

        yesLabel1 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(yesLabel1).atLeftTo(verticalDivider1).atTopTo(questionTextWidget1, 0, SWT.TOP).atBottomTo(questionTextWidget1, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        LabelSet.decorate(yesLabel1).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_1);
        yesLabel1.addMouseListener(new ChooseAnswerListener());

        noLabel1 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(noLabel1).atLeftTo(verticalDivider2).atTopTo(questionTextWidget1, 0, SWT.TOP).atBottomTo(questionTextWidget1, 0, SWT.BOTTOM).atRight();
        LabelSet.decorate(noLabel1).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_1);
        noLabel1.addMouseListener(new ChooseAnswerListener());

        final Label divider2 = new Label(ac, SWT.NONE);
        FormDataSet.attach(divider2).atLeft().atTopTo(questionTextWidget1).atRight().withHeight(1);
        LabelSet.decorate(divider2).setBackground(MT.COLOR_GRAY40);

        final StyledText questionTextWidget2 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(questionTextWidget2).atLeft().atTopTo(divider2).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(questionTextWidget2).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question2").getText());

        yesLabel2 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(yesLabel2).atLeftTo(verticalDivider1).atTopTo(questionTextWidget2, 0, SWT.TOP).atBottomTo(questionTextWidget2, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        LabelSet.decorate(yesLabel2).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_2);
        yesLabel2.addMouseListener(new ChooseAnswerListener());

        noLabel2 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(noLabel2).atLeftTo(verticalDivider2).atTopTo(questionTextWidget2, 0, SWT.TOP).atBottomTo(questionTextWidget2, 0, SWT.BOTTOM).atRight();
        LabelSet.decorate(noLabel2).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_2);
        noLabel2.addMouseListener(new ChooseAnswerListener());

        final Label divider3 = new Label(ac, SWT.NONE);
        FormDataSet.attach(divider3).atLeft().atTopTo(questionTextWidget2).atRight().withHeight(1);
        LabelSet.decorate(divider3).setBackground(MT.COLOR_GRAY40);

        final StyledText questionTextWidget3 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(questionTextWidget3).atLeft().atTopTo(divider3).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(questionTextWidget3).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question3").getText());

        yesLabel3 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(yesLabel3).atLeftTo(verticalDivider1).atTopTo(questionTextWidget3, 0, SWT.TOP).atBottomTo(questionTextWidget3, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        LabelSet.decorate(yesLabel3).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_3);
        yesLabel3.addMouseListener(new ChooseAnswerListener());

        noLabel3 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(noLabel3).atLeftTo(verticalDivider2).atTopTo(questionTextWidget3, 0, SWT.TOP).atBottomTo(questionTextWidget3, 0, SWT.BOTTOM).atRight();
        LabelSet.decorate(noLabel3).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_3);
        noLabel3.addMouseListener(new ChooseAnswerListener());

        final Label divider4 = new Label(ac, SWT.NONE);
        FormDataSet.attach(divider4).atLeft().atTopTo(questionTextWidget3).atRight().withHeight(1);
        LabelSet.decorate(divider4).setBackground(MT.COLOR_GRAY40);

        final StyledText questionTextWidget4 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(questionTextWidget4).atLeft().atTopTo(divider4).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(questionTextWidget4).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question4").getText());

        yesLabel4 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(yesLabel4).atLeftTo(verticalDivider1).atTopTo(questionTextWidget4, 0, SWT.TOP).atBottomTo(questionTextWidget4, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        LabelSet.decorate(yesLabel4).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_4);
        yesLabel4.addMouseListener(new ChooseAnswerListener());

        noLabel4 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(noLabel4).atLeftTo(verticalDivider2).atTopTo(questionTextWidget4, 0, SWT.TOP).atBottomTo(questionTextWidget4, 0, SWT.BOTTOM).atRight();
        LabelSet.decorate(noLabel4).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_4);
        noLabel4.addMouseListener(new ChooseAnswerListener());

        final Label divider5 = new Label(ac, SWT.NONE);
        FormDataSet.attach(divider5).atLeft().atTopTo(questionTextWidget4).atRight().withHeight(1);
        LabelSet.decorate(divider5).setBackground(MT.COLOR_GRAY40);

        final StyledText questionTextWidget5 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(questionTextWidget5).atLeft().atTopTo(divider5).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(questionTextWidget5).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question5").getText());

        yesLabel5 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(yesLabel5).atLeftTo(verticalDivider1).atTopTo(questionTextWidget5, 0, SWT.TOP).atBottomTo(questionTextWidget5, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        LabelSet.decorate(yesLabel5).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_5);
        yesLabel5.addMouseListener(new ChooseAnswerListener());

        noLabel5 = new Label(ac, SWT.CENTER);
        FormDataSet.attach(noLabel5).atLeftTo(verticalDivider2).atTopTo(questionTextWidget5, 0, SWT.TOP).atBottomTo(questionTextWidget5, 0, SWT.BOTTOM).atRight();
        LabelSet.decorate(noLabel5).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_5);
        noLabel5.addMouseListener(new ChooseAnswerListener());

        updateWidgetsForAnswers();
    }

    private void updateWidgetsForAnswers() {

        if (StringUtils.isEmpty(answerText)) {
            answer1 = answer2 = answer3 = answer4 = answer5 = MT.CHOICE_NEVER_CHECK_MARKED;
        } else {
            String[] arr = answerText.split(MT.STRING_COMMA);
            if (5 == arr.length) {
                answer1 = Integer.parseInt(arr[0]);
                markWidgetsForAnswers(answer1, yesLabel1, noLabel1);
                answer2 = Integer.parseInt(arr[1]);
                markWidgetsForAnswers(answer2, yesLabel2, noLabel2);
                answer3 = Integer.parseInt(arr[2]);
                markWidgetsForAnswers(answer3, yesLabel3, noLabel3);
                answer4 = Integer.parseInt(arr[3]);
                markWidgetsForAnswers(answer4, yesLabel4, noLabel4);
                answer5 = Integer.parseInt(arr[4]);
                markWidgetsForAnswers(answer5, yesLabel5, noLabel5);
            }
        }

        logger.info("[Listening Match Objects Question {}] Answers: ({}, {}, {}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3, answer4, answer5);
    }

    private void markWidgetsForAnswers(int answer, Label yesLabel, Label noLabel) {
        switch (answer) {
            case MT.CHOICE_NEVER_CHECK_MARKED:
                break;
            case MT.CHOICE_YES:
                LabelSet.decorate(yesLabel).setImage(MT.IMAGE_CHECKED);
                break;
            case MT.CHOICE_NO:
                LabelSet.decorate(noLabel).setImage(MT.IMAGE_CHECKED);
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
        return MT.CHOICE_NEVER_CHECK_MARKED != answer1 && MT.CHOICE_NEVER_CHECK_MARKED != answer2 && MT.CHOICE_NEVER_CHECK_MARKED != answer3 && MT.CHOICE_NEVER_CHECK_MARKED != answer4 && MT.CHOICE_NEVER_CHECK_MARKED != answer5;
    }

    public boolean isNull() {
        return MT.CHOICE_NEVER_CHECK_MARKED == answer1 && MT.CHOICE_NEVER_CHECK_MARKED == answer2 && MT.CHOICE_NEVER_CHECK_MARKED == answer3 && MT.CHOICE_NEVER_CHECK_MARKED == answer4 && MT.CHOICE_NEVER_CHECK_MARKED == answer5;
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
                UserTestPersistenceUtils.saveToNextView(ListeningMatchObjectsQuestionView.this);
                page.resume();

            } else {

                nextOvalButton.setEnabled(true);
                okOvalButton.setEnabled(false);

                if (isNull()) {
                    RequiredAnswerDialog d = new RequiredAnswerDialog(MT.REQUIRED_ANSWER_DIALOG_TYPE_NO_ANSWER_FOR_MANY);
                    d.openAndWaitForDisposal();
                } else {
                    RequiredAnswerDialog d = new RequiredAnswerDialog(MT.REQUIRED_ANSWER_DIALOG_TYPE_INCORRECT_ANSWER_COUNT);
                    d.openAndWaitForDisposal();
                }
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
            UserTestPersistenceUtils.saveVolumeControlVisibility(ListeningMatchObjectsQuestionView.this);
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

            UserTestPersistenceUtils.saveVolume(ListeningMatchObjectsQuestionView.this, volume);
            setAudioVolume(volume);
        }
    }

    private class ChooseAnswerListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            Label c = (Label) e.widget;
            int qid = (Integer) c.getData(MT.KEY_QUESTION);

            switch (qid) {
                case MT.QUESTION_1:
                    if (c == yesLabel1) {
                        answer1 = MT.CHOICE_YES;
                        LabelSet.decorate(yesLabel1).setText(CHECK_MARK);
                        LabelSet.decorate(noLabel1).setText("");
                    } else {
                        answer1 = MT.CHOICE_NO;
                        LabelSet.decorate(yesLabel1).setText("");
                        LabelSet.decorate(noLabel1).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_2:
                    if (c == yesLabel2) {
                        answer2 = MT.CHOICE_YES;
                        LabelSet.decorate(yesLabel2).setText(CHECK_MARK);
                        LabelSet.decorate(noLabel2).setText("");
                    } else {
                        answer2 = MT.CHOICE_NO;
                        LabelSet.decorate(yesLabel2).setText("");
                        LabelSet.decorate(noLabel2).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_3:
                    if (c == yesLabel3) {
                        answer3 = MT.CHOICE_YES;
                        LabelSet.decorate(yesLabel3).setText(CHECK_MARK);
                        LabelSet.decorate(noLabel3).setText("");
                    } else {
                        answer3 = MT.CHOICE_NO;
                        LabelSet.decorate(yesLabel3).setText("");
                        LabelSet.decorate(noLabel3).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_4:
                    if (c == yesLabel4) {
                        answer4 = MT.CHOICE_YES;
                        LabelSet.decorate(yesLabel4).setText(CHECK_MARK);
                        LabelSet.decorate(noLabel4).setText("");
                    } else {
                        answer4 = MT.CHOICE_NO;
                        LabelSet.decorate(yesLabel4).setText("");
                        LabelSet.decorate(noLabel4).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_5:
                    if (c == yesLabel5) {
                        answer5 = MT.CHOICE_YES;
                        LabelSet.decorate(yesLabel5).setText(CHECK_MARK);
                        LabelSet.decorate(noLabel5).setText("");
                    } else {
                        answer5 = MT.CHOICE_NO;
                        LabelSet.decorate(yesLabel5).setText("");
                        LabelSet.decorate(noLabel5).setText(CHECK_MARK);
                    }
                    break;
            }

            logger.info("[Listening Match Objects Question {}] Answers: ({}, {}, {}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3, answer4, answer5);

            answerText = answer1 + MT.STRING_COMMA + answer2 + MT.STRING_COMMA + answer3 + MT.STRING_COMMA + answer4 + MT.STRING_COMMA + answer5;
            UserTestPersistenceUtils.saveAnswers(ListeningMatchObjectsQuestionView.this, answerText);
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

                    countDown = page.getUserTest().getRemainingViewTime(vo);
                    timer = new Timer();
                    timerTask = new TestTimerTask();
                    timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }
            }
        }
    }
}
