package com.mocktpo.view.test;

import com.mocktpo.dialog.RequiredAnswerDialog;
import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.listener.StyledTextPaintImageListener;
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

    private StyledText tips;
    private Composite ac;
    private CLabel yl1, nl1, yl2, nl2, yl3, nl3, yl4, nl4, yl5, nl5;

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

        answer1 = answer2 = answer3 = answer4 = answer5 = MT.CHOICE_NEVER_CHECK_MARKED;
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

        final ImageButton hob = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(hob).atRightTo(okOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        hob.addMouseListener(new HelpOvalButtonMouseListener());

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRightTo(hob).atTopTo(nextOvalButton, 0, SWT.TOP);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        volumeControl = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(volumeControl).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(volumeControl).setVisible(volumeControlVisible);
        volumeControl.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        volumeControl.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue button

        final ImageButton cb = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(nextOvalButton, 8, SWT.TOP);
        cb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent mouseEvent) {

                release();

                UserTest ut = page.getUserTest();
                ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
                ut.setLastViewId(vo.getViewId() + 1);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                page.resume(ut);
            }
        });
    }

    @Override
    public void updateBody() {

        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);

        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        FormLayoutSet.layout(viewPort);

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("directions").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("directions").getStyles());
        dt.addPaintObjectListener(new StyledTextPaintImageListener());

        tips = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tips).atLeft().atTopTo(dt, 20).atRight();
        StyledTextSet.decorate(tips).setAlignment(SWT.CENTER).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("tips").getText()).setVisible(false);
        StyleRangeUtils.decorate(tips, vo.getStyledText("tips").getStyles());

        ac = new Composite(viewPort, SWT.NONE);
        FormDataSet.attach(ac).atLeft().atTopTo(tips, 20).atRight();
        CompositeSet.decorate(ac).setVisible(false);
        FormLayoutSet.layout(ac).marginWidth(1).marginHeight(1);
        ac.addPaintListener(new BorderedCompositePaintListener());

        final StyledText qh = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(qh).atLeft().atTop().fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(qh).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10);

        final Label vd1 = new Label(ac, SWT.VERTICAL);
        FormDataSet.attach(vd1).atLeftTo(qh).atTop().atBottom().withWidth(1);
        LabelSet.decorate(vd1).setBackground(MT.COLOR_GRAY40);

        final CLabel yh = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(yh).atLeftTo(vd1).atTopTo(qh, 0, SWT.TOP).atBottomTo(qh, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        CLabelSet.decorate(yh).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("yes"));
        yh.addMouseListener(new ChooseAnswerListener());

        final Label vd2 = new Label(ac, SWT.VERTICAL);
        FormDataSet.attach(vd2).atLeftTo(yh).atTop().atBottom().withWidth(1);
        LabelSet.decorate(vd2).setBackground(MT.COLOR_GRAY40);

        final CLabel nh = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(nh).atLeftTo(vd2).atTopTo(qh, 0, SWT.TOP).atBottomTo(qh, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(nh).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("no"));
        nh.addMouseListener(new ChooseAnswerListener());

        final Label dl1 = new Label(ac, SWT.NONE);
        FormDataSet.attach(dl1).atLeft().atTopTo(qh).atRight().withHeight(1);
        LabelSet.decorate(dl1).setBackground(MT.COLOR_GRAY40);

        final StyledText qt1 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(qt1).atLeft().atTopTo(dl1).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(qt1).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question1").getText());

        yl1 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(yl1).atLeftTo(vd1).atTopTo(qt1, 0, SWT.TOP).atBottomTo(qt1, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        CLabelSet.decorate(yl1).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_1);
        yl1.addMouseListener(new ChooseAnswerListener());

        nl1 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(nl1).atLeftTo(vd2).atTopTo(qt1, 0, SWT.TOP).atBottomTo(qt1, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(nl1).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_1);
        nl1.addMouseListener(new ChooseAnswerListener());

        final Label dl2 = new Label(ac, SWT.NONE);
        FormDataSet.attach(dl2).atLeft().atTopTo(qt1).atRight().withHeight(1);
        LabelSet.decorate(dl2).setBackground(MT.COLOR_GRAY40);

        final StyledText qt2 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(qt2).atLeft().atTopTo(dl2).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(qt2).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question2").getText());

        yl2 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(yl2).atLeftTo(vd1).atTopTo(qt2, 0, SWT.TOP).atBottomTo(qt2, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        CLabelSet.decorate(yl2).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_2);
        yl2.addMouseListener(new ChooseAnswerListener());

        nl2 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(nl2).atLeftTo(vd2).atTopTo(qt2, 0, SWT.TOP).atBottomTo(qt2, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(nl2).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_2);
        nl2.addMouseListener(new ChooseAnswerListener());

        final Label dl3 = new Label(ac, SWT.NONE);
        FormDataSet.attach(dl3).atLeft().atTopTo(qt2).atRight().withHeight(1);
        LabelSet.decorate(dl3).setBackground(MT.COLOR_GRAY40);

        final StyledText qt3 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(qt3).atLeft().atTopTo(dl3).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(qt3).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question3").getText());

        yl3 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(yl3).atLeftTo(vd1).atTopTo(qt3, 0, SWT.TOP).atBottomTo(qt3, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        CLabelSet.decorate(yl3).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_3);
        yl3.addMouseListener(new ChooseAnswerListener());

        nl3 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(nl3).atLeftTo(vd2).atTopTo(qt3, 0, SWT.TOP).atBottomTo(qt3, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(nl3).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_3);
        nl3.addMouseListener(new ChooseAnswerListener());

        final Label dl4 = new Label(ac, SWT.NONE);
        FormDataSet.attach(dl4).atLeft().atTopTo(qt3).atRight().withHeight(1);
        LabelSet.decorate(dl4).setBackground(MT.COLOR_GRAY40);

        final StyledText qt4 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(qt4).atLeft().atTopTo(dl4).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(qt4).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question4").getText());

        yl4 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(yl4).atLeftTo(vd1).atTopTo(qt4, 0, SWT.TOP).atBottomTo(qt4, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        CLabelSet.decorate(yl4).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_4);
        yl4.addMouseListener(new ChooseAnswerListener());

        nl4 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(nl4).atLeftTo(vd2).atTopTo(qt4, 0, SWT.TOP).atBottomTo(qt4, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(nl4).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_4);
        nl4.addMouseListener(new ChooseAnswerListener());

        final Label dl5 = new Label(ac, SWT.NONE);
        FormDataSet.attach(dl5).atLeft().atTopTo(qt4).atRight().withHeight(1);
        LabelSet.decorate(dl5).setBackground(MT.COLOR_GRAY40);

        final StyledText qt5 = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(qt5).atLeft().atTopTo(dl5).fromRight(0, CHECK_MARK_LABEL_WIDTH * 2);
        StyledTextSet.decorate(qt5).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(10).setText(vo.getStyledText("question5").getText());

        yl5 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(yl5).atLeftTo(vd1).atTopTo(qt5, 0, SWT.TOP).atBottomTo(qt5, 0, SWT.BOTTOM).withWidth(CHECK_MARK_LABEL_WIDTH);
        CLabelSet.decorate(yl5).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_5);
        yl5.addMouseListener(new ChooseAnswerListener());

        nl5 = new CLabel(ac, SWT.CENTER);
        FormDataSet.attach(nl5).atLeftTo(vd2).atTopTo(qt5, 0, SWT.TOP).atBottomTo(qt5, 0, SWT.BOTTOM).atRight();
        CLabelSet.decorate(nl5).setCursor(MT.CURSOR_HAND).setData(MT.KEY_QUESTION, MT.QUESTION_5);
        nl5.addMouseListener(new ChooseAnswerListener());
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

                UserTest ut = page.getUserTest();
                ut.setCompletionRate(100 * vo.getViewId() / page.getTestSchema().getViews().size());
                ut.setLastViewId(vo.getViewId() + 1);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                page.resume(ut);

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
            double selection = s.getSelection(), maximum = s.getMaximum();
            setAudioVolume(selection / maximum);
        }
    }

    private class ChooseAnswerListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            CLabel c = (CLabel) e.widget;
            int qid = (Integer) c.getData(MT.KEY_QUESTION);

            switch (qid) {
                case MT.QUESTION_1:
                    if (c == yl1) {
                        answer1 = MT.CHOICE_YES;
                        CLabelSet.decorate(yl1).setText(CHECK_MARK);
                        CLabelSet.decorate(nl1).setText("");
                    } else {
                        answer1 = MT.CHOICE_NO;
                        CLabelSet.decorate(yl1).setText("");
                        CLabelSet.decorate(nl1).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_2:
                    if (c == yl2) {
                        answer2 = MT.CHOICE_YES;
                        CLabelSet.decorate(yl2).setText(CHECK_MARK);
                        CLabelSet.decorate(nl2).setText("");
                    } else {
                        answer2 = MT.CHOICE_NO;
                        CLabelSet.decorate(yl2).setText("");
                        CLabelSet.decorate(nl2).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_3:
                    if (c == yl3) {
                        answer3 = MT.CHOICE_YES;
                        CLabelSet.decorate(yl3).setText(CHECK_MARK);
                        CLabelSet.decorate(nl3).setText("");
                    } else {
                        answer3 = MT.CHOICE_NO;
                        CLabelSet.decorate(yl3).setText("");
                        CLabelSet.decorate(nl3).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_4:
                    if (c == yl4) {
                        answer4 = MT.CHOICE_YES;
                        CLabelSet.decorate(yl4).setText(CHECK_MARK);
                        CLabelSet.decorate(nl4).setText("");
                    } else {
                        answer4 = MT.CHOICE_NO;
                        CLabelSet.decorate(yl4).setText("");
                        CLabelSet.decorate(nl4).setText(CHECK_MARK);
                    }
                    break;
                case MT.QUESTION_5:
                    if (c == yl5) {
                        answer5 = MT.CHOICE_YES;
                        CLabelSet.decorate(yl5).setText(CHECK_MARK);
                        CLabelSet.decorate(nl5).setText("");
                    } else {
                        answer5 = MT.CHOICE_NO;
                        CLabelSet.decorate(yl5).setText("");
                        CLabelSet.decorate(nl5).setText(CHECK_MARK);
                    }
                    break;
            }

            logger.info("[Listening Match Objects Question {}] Answers: ({}, {}, {}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3, answer4, answer5);
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

                            StyledTextSet.decorate(tips).setVisible(true);
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

                    countDown = page.getUserTest().getRemainingViewTime(vo.getSectionType(), vo.getGroupId());
                    timer = new Timer();
                    timerTask = new TestTimerTask();
                    timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }
            }
        }
    }
}
