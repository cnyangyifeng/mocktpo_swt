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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Scale;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Timer;

public class ListeningMultipleAnswersQuestionView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 150;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;

    private static final int VIEW_STATUS_INITIAL = 0;
    private static final int VIEW_STATUS_NEXT_BUTTON_ENABLED = 1;
    private static final int VIEW_STATUS_OK_BUTTON_ENABLED = 2;

    /* Widgets */

    private ImageButton nextOvalButton, okOvalButton;
    private VolumeControl vc;

    private Label choiceA, choiceB, choiceC, choiceD;
    private Label la, lb, lc, ld;
    private StyledText tips;

    /* Properties */

    private boolean volumeControlVisible;
    private int viewStatus;
    private int answer1, answer2;

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
        this.viewStatus = VIEW_STATUS_INITIAL;
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

        vc = new VolumeControl(header, SWT.NONE);
        FormDataSet.attach(vc).atTopTo(vob, 0, SWT.BOTTOM).atRightTo(vob, 0, SWT.RIGHT).atBottom(5).withWidth(LC.VOLUME_CONTROL_WIDTH);
        CompositeSet.decorate(vc).setVisible(volumeControlVisible);
        vc.addSelectionListener(new VolumeControlSelectionListener());
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
        return 0 != answer1 && 0 != answer2;
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
            viewStatus = VIEW_STATUS_OK_BUTTON_ENABLED;
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
                ut.setLastViewId(vo.getViewId() + 1);

                sqlSession.getMapper(UserTestMapper.class).update(ut);
                sqlSession.commit();

                page.resume(ut);

            } else {

                nextOvalButton.setEnabled(true);
                okOvalButton.setEnabled(false);
                viewStatus = VIEW_STATUS_NEXT_BUTTON_ENABLED;

                new RequiredAnswerDialog().openAndWaitForDisposal();
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

            int answer = (Integer) e.widget.getData(MT.KEY_CHOICE);

            if (VIEW_STATUS_INITIAL == viewStatus) {
                nextOvalButton.setEnabled(true);
                okOvalButton.setEnabled(false);
                viewStatus = VIEW_STATUS_NEXT_BUTTON_ENABLED;
            }

            if (2 == vo.getTotalAnswerCount()) {

                if (answer == answer1) {
                    answer1 = 0;
                } else if (answer == answer2) {
                    answer2 = 0;
                } else {
                    if (0 == answer1 && 0 == answer2) {
                        answer1 = answer;
                    } else if (0 != answer1 && 0 == answer2) {
                        answer2 = answer;
                    } else if (0 == answer1 /* && 0 != answer2 */) {
                        answer1 = answer;
                    } else /* if (0 != answer1 && 0 != answer2) */ {
                        nextOvalButton.setEnabled(true);
                        okOvalButton.setEnabled(false);
                        viewStatus = VIEW_STATUS_NEXT_BUTTON_ENABLED;
                        new RequiredAnswerDialog().openAndWaitForDisposal();
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
            }

            logger.info("[Listening Multiple-Answers Question {}] Answers: ({}, {})", vo.getQuestionNumberInSection(), answer1, answer2);
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
                            StyledTextSet.decorate(tips).setVisible(true);
                            LabelSet.decorate(choiceA).setVisible(true);
                            LabelSet.decorate(la).setVisible(true);
                            LabelSet.decorate(choiceB).setVisible(true);
                            LabelSet.decorate(lb).setVisible(true);
                            LabelSet.decorate(choiceC).setVisible(true);
                            LabelSet.decorate(lc).setVisible(true);
                            LabelSet.decorate(choiceD).setVisible(true);
                            LabelSet.decorate(ld).setVisible(true);
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
