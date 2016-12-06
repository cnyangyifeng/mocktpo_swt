package com.mocktpo.view.test;

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

public class ListeningQuestionView extends ResponsiveTestView {

    /* Constants */

    private static final int VIEW_PORT_PADDING_TOP = 150;
    private static final int VIEW_PORT_PADDING_WIDTH = 200;

    /* Widgets */

    private ImageButton nextOvalButton, okOvalButton;
    private VolumeControl vc;

    private Label choiceA, choiceB, choiceC, choiceD;
    private Label la, lb, lc, ld;
    private StyledText footnote;

    /* Properties */

    private boolean volumeControlVisible;
    private int answer;

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

        final StyledText question = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(question).atLeft().atTop(VIEW_PORT_PADDING_TOP).atRight();
        StyledTextSet.decorate(question).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(question, vo.getStyledText("question").getStyles());
        question.addPaintObjectListener(new StyledTextPaintImageListener());

        if (null != vo.getStyledText("choiceA") && null != vo.getStyledText("choiceB") && null != vo.getStyledText("choiceC") && null != vo.getStyledText("choiceD")) {

            choiceA = new Label(viewPort, SWT.NONE);
            FormDataSet.attach(choiceA).atLeft().atTopTo(question, 25);
            LabelSet.decorate(choiceA).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
            choiceA.addMouseListener(new ChooseAnswerListener());

            la = new Label(viewPort, SWT.WRAP);
            FormDataSet.attach(la).atLeftTo(choiceA, 5).atTopTo(question, 20).atRight();
            LabelSet.decorate(la).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceA").getText()).setVisible(false);
            la.addMouseListener(new ChooseAnswerListener());

            choiceB = new Label(viewPort, SWT.NONE);
            FormDataSet.attach(choiceB).atLeft().atTopTo(la, 25);
            LabelSet.decorate(choiceB).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
            choiceB.addMouseListener(new ChooseAnswerListener());

            lb = new Label(viewPort, SWT.WRAP);
            FormDataSet.attach(lb).atLeftTo(choiceB, 5).atTopTo(la, 20).atRight();
            LabelSet.decorate(lb).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceB").getText()).setVisible(false);
            lb.addMouseListener(new ChooseAnswerListener());

            choiceC = new Label(viewPort, SWT.NONE);
            FormDataSet.attach(choiceC).atLeft().atTopTo(lb, 25);
            LabelSet.decorate(choiceC).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
            choiceC.addMouseListener(new ChooseAnswerListener());

            lc = new Label(viewPort, SWT.WRAP);
            FormDataSet.attach(lc).atLeftTo(choiceC, 5).atTopTo(lb, 20).atRight();
            LabelSet.decorate(lc).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceC").getText()).setVisible(false);
            lc.addMouseListener(new ChooseAnswerListener());

            choiceD = new Label(viewPort, SWT.NONE);
            FormDataSet.attach(choiceD).atLeft().atTopTo(lc, 25);
            LabelSet.decorate(choiceD).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setImage(MT.IMAGE_UNCHECKED).setVisible(false);
            choiceD.addMouseListener(new ChooseAnswerListener());

            ld = new Label(viewPort, SWT.WRAP);
            FormDataSet.attach(ld).atLeftTo(choiceD, 5).atTopTo(lc, 20).atRight();
            LabelSet.decorate(ld).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceD").getText()).setVisible(false);
            ld.addMouseListener(new ChooseAnswerListener());

            if (null != vo.getStyledText("footnote")) {
                footnote = new StyledText(viewPort, SWT.WRAP);
                FormDataSet.attach(footnote).atLeft().atTopTo(ld, 40).atRight();
                StyledTextSet.decorate(footnote).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("footnote").getText()).setVisible(false);
            }
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
        if (vo.isAudioAsyncExecutable()) {
            audioPlayer.addPropertyChangeListener(new AudioAsyncExecutionListener());
        }
    }

    @Override
    public void stopAudioAsyncExecution() {
        if (vo.isAudioAsyncExecutable()) {
        }
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

            release();

            UserTest ut = page.getUserTest();
            ut.setLastViewId(vo.getViewId() + 1);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            page.resume(ut);
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

            answer = (Integer) e.widget.getData(MT.KEY_CHOICE);

            nextOvalButton.setEnabled(true);
            okOvalButton.setEnabled(false);

            switch (answer) {
                case MT.CHOICE_A:
                    LabelSet.decorate(choiceA).setImage(MT.IMAGE_CHECKED);
                    LabelSet.decorate(choiceB).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceC).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceD).setImage(MT.IMAGE_UNCHECKED);
                    break;
                case MT.CHOICE_B:
                    LabelSet.decorate(choiceA).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceB).setImage(MT.IMAGE_CHECKED);
                    LabelSet.decorate(choiceC).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceD).setImage(MT.IMAGE_UNCHECKED);
                    break;
                case MT.CHOICE_C:
                    LabelSet.decorate(choiceA).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceB).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceC).setImage(MT.IMAGE_CHECKED);
                    LabelSet.decorate(choiceD).setImage(MT.IMAGE_UNCHECKED);
                    break;
                case MT.CHOICE_D:
                    LabelSet.decorate(choiceA).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceB).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceC).setImage(MT.IMAGE_UNCHECKED);
                    LabelSet.decorate(choiceD).setImage(MT.IMAGE_CHECKED);
                    break;
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
                            if (null != vo.getStyledText("choiceA") && null != vo.getStyledText("choiceB") && null != vo.getStyledText("choiceC") && null != vo.getStyledText("choiceD")) {
                                LabelSet.decorate(choiceA).setVisible(true);
                                LabelSet.decorate(la).setVisible(true);
                                LabelSet.decorate(choiceB).setVisible(true);
                                LabelSet.decorate(lb).setVisible(true);
                                LabelSet.decorate(choiceC).setVisible(true);
                                LabelSet.decorate(lc).setVisible(true);
                                LabelSet.decorate(choiceD).setVisible(true);
                                LabelSet.decorate(ld).setVisible(true);
                                if (null != vo.getStyledText("footnote")) {
                                    StyledTextSet.decorate(footnote).setVisible(true);
                                }
                            }
                        }
                    });
                }
            }
        }
    }
}
