package com.mocktpo.view.test;

import com.mocktpo.dialog.RequiredAnswerDialog;
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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
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

    private ImageButton nob, oob;
    private VolumeControl vc;

    private Label choiceA, choiceB, choiceC, choiceD, choiceE;
    private Label la, lb, lc, ld, le;
    private StyledText tips;

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

        nob = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.setEnabled(false);
        nob.addMouseListener(new NextOvalButtonMouseListener());

        oob = new ImageButton(header, SWT.NONE, MT.IMAGE_OK_OVAL, MT.IMAGE_OK_OVAL_HOVER, MT.IMAGE_OK_OVAL_DISABLED);
        FormDataSet.attach(oob).atRightTo(nob).atTopTo(nob, 0, SWT.TOP);
        oob.setEnabled(false);
        oob.addMouseListener(new OkOvalButtonMouseListener());

        final ImageButton hob = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(hob).atRightTo(oob).atTopTo(nob, 0, SWT.TOP);
        hob.addMouseListener(new HelpOvalButtonMouseListener());

        final ImageButton vob = new ImageButton(header, SWT.NONE, MT.IMAGE_VOLUME_OVAL, MT.IMAGE_VOLUME_OVAL_HOVER);
        FormDataSet.attach(vob).atRightTo(hob).atTopTo(nob, 0, SWT.TOP);
        vob.addMouseListener(new VolumeOvalButtonMouseListener());

        vc = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(vc).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(vc).setVisible(volumeControlVisible);
        vc.setSelection(((Double) (page.getUserTest().getVolume() * 10)).intValue());
        vc.addSelectionListener(new VolumeControlSelectionListener());

        // TODO Removes the continue button

        final ImageButton cb = new ImageButton(header, SWT.NONE, MT.IMAGE_CONTINUE, MT.IMAGE_CONTINUE_HOVER);
        FormDataSet.attach(cb).atRightTo(vob, 16).atTopTo(nob, 8, SWT.TOP);
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

        final StyledText qt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(qt).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(qt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(qt, vo.getStyledText("question").getStyles());
        qt.addPaintObjectListener(new StyledTextPaintImageListener());

        tips = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tips).atLeft().atTopTo(qt, 20).atRight();
        StyledTextSet.decorate(tips).setAlignment(SWT.CENTER).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setMargins(5).setText(vo.getStyledText("tips").getText()).setVisible(false);
        StyleRangeUtils.decorate(tips, vo.getStyledText("tips").getStyles());

        choiceA = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(choiceA).atLeft(5).atTopTo(tips, 25);
        LabelSet.decorate(choiceA).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        choiceA.addMouseListener(new ChooseAnswerListener());

        la = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(la).atLeftTo(choiceA, 5).atTopTo(tips, 20).atRight();
        LabelSet.decorate(la).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceA").getText()).setVisible(false);
        la.addMouseListener(new ChooseAnswerListener());

        choiceB = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(choiceB).atLeft(5).atTopTo(la, 25);
        LabelSet.decorate(choiceB).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        choiceB.addMouseListener(new ChooseAnswerListener());

        lb = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(lb).atLeftTo(choiceB, 5).atTopTo(la, 20).atRight();
        LabelSet.decorate(lb).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceB").getText()).setVisible(false);
        lb.addMouseListener(new ChooseAnswerListener());

        choiceC = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(choiceC).atLeft(5).atTopTo(lb, 25);
        LabelSet.decorate(choiceC).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        choiceC.addMouseListener(new ChooseAnswerListener());

        lc = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(lc).atLeftTo(choiceC, 5).atTopTo(lb, 20).atRight();
        LabelSet.decorate(lc).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceC").getText()).setVisible(false);
        lc.addMouseListener(new ChooseAnswerListener());

        choiceD = new Label(viewPort, SWT.NONE);
        FormDataSet.attach(choiceD).atLeft(5).atTopTo(lc, 25);
        LabelSet.decorate(choiceD).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setImage(MT.IMAGE_UNBOXED).setVisible(false);
        choiceD.addMouseListener(new ChooseAnswerListener());

        ld = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(ld).atLeftTo(choiceD, 5).atTopTo(lc, 20).atRight();
        LabelSet.decorate(ld).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceD").getText()).setVisible(false);
        ld.addMouseListener(new ChooseAnswerListener());

        if (3 == vo.getTotalAnswerCount()) {
            choiceE = new Label(viewPort, SWT.NONE);
            FormDataSet.attach(choiceE).atLeft(5).atTopTo(ld, 25);
            LabelSet.decorate(choiceE).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_E).setImage(MT.IMAGE_UNBOXED).setVisible(false);
            choiceE.addMouseListener(new ChooseAnswerListener());

            le = new Label(viewPort, SWT.WRAP);
            FormDataSet.attach(le).atLeftTo(choiceE, 5).atTopTo(ld, 20).atRight();
            LabelSet.decorate(le).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_E).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceE").getText()).setVisible(false);
            le.addMouseListener(new ChooseAnswerListener());
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

    private class NextOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {
            nob.setEnabled(false);
            oob.setEnabled(true);
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

                nob.setEnabled(true);
                oob.setEnabled(false);

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
            CompositeSet.decorate(vc).setVisible(volumeControlVisible);

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

    private class ChooseAnswerListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

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
                            nob.setEnabled(true);
                            oob.setEnabled(false);
                            new RequiredAnswerDialog(MT.REQUIRED_ANSWER_DIALOG_TYPE_INCORRECT_ANSWER_COUNT).openAndWaitForDisposal();
                        }
                    }
                }

                LabelSet.decorate(choiceA).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(choiceB).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(choiceC).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(choiceD).setImage(MT.IMAGE_UNBOXED);

                switch (answer1) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(choiceA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(choiceB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(choiceC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(choiceD).setImage(MT.IMAGE_BOXED);
                        break;
                }

                switch (answer2) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(choiceA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(choiceB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(choiceC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(choiceD).setImage(MT.IMAGE_BOXED);
                        break;
                }

                logger.info("[Listening Multiple-Answers Question {}] Answers: ({}, {})", vo.getQuestionNumberInSection(), answer1, answer2);

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
                                nob.setEnabled(true);
                                oob.setEnabled(false);
                                new RequiredAnswerDialog(MT.REQUIRED_ANSWER_DIALOG_TYPE_INCORRECT_ANSWER_COUNT).openAndWaitForDisposal();
                            }
                        }
                    }
                }

                LabelSet.decorate(choiceA).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(choiceB).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(choiceC).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(choiceD).setImage(MT.IMAGE_UNBOXED);
                LabelSet.decorate(choiceE).setImage(MT.IMAGE_UNBOXED);

                switch (answer1) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(choiceA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(choiceB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(choiceC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(choiceD).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_E:
                        LabelSet.decorate(choiceE).setImage(MT.IMAGE_BOXED);
                        break;
                }

                switch (answer2) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(choiceA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(choiceB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(choiceC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(choiceD).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_E:
                        LabelSet.decorate(choiceE).setImage(MT.IMAGE_BOXED);
                        break;
                }

                switch (answer3) {
                    case MT.CHOICE_A:
                        LabelSet.decorate(choiceA).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_B:
                        LabelSet.decorate(choiceB).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_C:
                        LabelSet.decorate(choiceC).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_D:
                        LabelSet.decorate(choiceD).setImage(MT.IMAGE_BOXED);
                        break;
                    case MT.CHOICE_E:
                        LabelSet.decorate(choiceE).setImage(MT.IMAGE_BOXED);
                        break;
                }

                logger.info("[Listening Multiple-Answers Question {}] Answers: ({}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3);
            }
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

                            nob.setEnabled(true);
                            oob.setEnabled(false);

                            StyledTextSet.decorate(tips).setVisible(true);
                            LabelSet.decorate(choiceA).setVisible(true);
                            LabelSet.decorate(la).setVisible(true);
                            LabelSet.decorate(choiceB).setVisible(true);
                            LabelSet.decorate(lb).setVisible(true);
                            LabelSet.decorate(choiceC).setVisible(true);
                            LabelSet.decorate(lc).setVisible(true);
                            LabelSet.decorate(choiceD).setVisible(true);
                            LabelSet.decorate(ld).setVisible(true);
                            if (3 == vo.getTotalAnswerCount()) {
                                LabelSet.decorate(choiceE).setVisible(true);
                                LabelSet.decorate(le).setVisible(true);
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

                    countDown = page.getUserTest().getRemainingViewTime(vo.getSectionType(), vo.getGroupId());
                    timer = new Timer();
                    timerTask = new TestTimerTask();
                    timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }
            }
        }
    }
}
