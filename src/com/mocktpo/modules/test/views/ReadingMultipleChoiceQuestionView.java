package com.mocktpo.modules.test.views;

import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.modules.system.widgets.ImageButton;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ReadingMultipleChoiceQuestionView extends SashTestView {

    /* Widgets */

    private CLabel indicator;
    private ScrolledComposite rightScrolled;
    private Label checkLabelA, checkLabelB, checkLabelC, checkLabelD;

    /* Properties */

    private int answer;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingMultipleChoiceQuestionView(TestPage page, int style) {
        super(page, style);
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    @Override
    protected void updateHeader() {
        final ImageButton nextOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nextOvalButton).atRight(10).atTop(10);
        nextOvalButton.addMouseListener(new NextOvalButtonMouseAdapter());

        final ImageButton backOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_BACK_OVAL, MT.IMAGE_BACK_OVAL_HOVER, MT.IMAGE_BACK_OVAL_DISABLED);
        FormDataSet.attach(backOvalButton).atRightTo(nextOvalButton).atTop(10);
        backOvalButton.addMouseListener(new BackOvalButtonMouseAdapter());

        final ImageButton reviewOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_REVIEW_OVAL, MT.IMAGE_REVIEW_OVAL_HOVER, MT.IMAGE_REVIEW_OVAL_DISABLED);
        FormDataSet.attach(reviewOvalButton).atRightTo(backOvalButton).atTop(10);
        reviewOvalButton.addMouseListener(new ReviewOvalButtonMouseAdapter());

        final ImageButton helpOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(helpOvalButton).atRightTo(reviewOvalButton).atTop(10);
        helpOvalButton.addMouseListener(new HelpOvalButtonMouseAdapter());
    }

    @Override
    public void updateLeft() {
        final Composite c = new Composite(left, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTop().atRight().atBottom();
        FormLayoutSet.layout(c).marginWidth(20).marginHeight(20).spacing(0);

        final StyledText questionTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeft().atTop().atRight();
        StyledTextSet.decorate(questionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("question"));
        StyleRangeUtils.decorate(questionTextWidget, vo.getStyledTextStyles("question"));

        checkLabelA = new Label(c, SWT.NONE);
        FormDataSet.attach(checkLabelA).atLeft(10).atTopTo(questionTextWidget, 20);
        LabelSet.decorate(checkLabelA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setImage(MT.IMAGE_UNCHECKED);
        checkLabelA.addMouseListener(new ChooseAnswerAdapter());

        final Label choiceLabelA = new Label(c, SWT.WRAP);
        FormDataSet.attach(choiceLabelA).atLeftTo(checkLabelA, 10).atTopTo(questionTextWidget, 20).atRight();
        LabelSet.decorate(choiceLabelA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setText(vo.getStyledTextContent("choiceA"));
        choiceLabelA.addMouseListener(new ChooseAnswerAdapter());

        checkLabelB = new Label(c, SWT.NONE);
        FormDataSet.attach(checkLabelB).atLeft(10).atTopTo(choiceLabelA, 20);
        LabelSet.decorate(checkLabelB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setImage(MT.IMAGE_UNCHECKED);
        checkLabelB.addMouseListener(new ChooseAnswerAdapter());

        final Label choiceLabelB = new Label(c, SWT.WRAP);
        FormDataSet.attach(choiceLabelB).atLeftTo(checkLabelB, 10).atTopTo(choiceLabelA, 20).atRight();
        LabelSet.decorate(choiceLabelB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setText(vo.getStyledTextContent("choiceB"));
        choiceLabelB.addMouseListener(new ChooseAnswerAdapter());

        checkLabelC = new Label(c, SWT.NONE);
        FormDataSet.attach(checkLabelC).atLeft(10).atTopTo(choiceLabelB, 20);
        LabelSet.decorate(checkLabelC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setImage(MT.IMAGE_UNCHECKED);
        checkLabelC.addMouseListener(new ChooseAnswerAdapter());

        final Label choiceLabelC = new Label(c, SWT.WRAP);
        FormDataSet.attach(choiceLabelC).atLeftTo(checkLabelC, 10).atTopTo(choiceLabelB, 20).atRight();
        LabelSet.decorate(choiceLabelC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setText(vo.getStyledTextContent("choiceC"));
        choiceLabelC.addMouseListener(new ChooseAnswerAdapter());

        checkLabelD = new Label(c, SWT.NONE);
        FormDataSet.attach(checkLabelD).atLeft(10).atTopTo(choiceLabelC, 20);
        LabelSet.decorate(checkLabelD).setData(MT.KEY_CHOICE, MT.CHOICE_D).setImage(MT.IMAGE_UNCHECKED);
        checkLabelD.addMouseListener(new ChooseAnswerAdapter());

        final Label choiceLabelD = new Label(c, SWT.WRAP);
        FormDataSet.attach(choiceLabelD).atLeftTo(checkLabelD, 10).atTopTo(choiceLabelC, 20).atRight();
        LabelSet.decorate(choiceLabelD).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setText(vo.getStyledTextContent("choiceD"));
        choiceLabelD.addMouseListener(new ChooseAnswerAdapter());

        if (vo.getStyledTextContent("footnote") != null) {
            final StyledText footnoteTextWidget = new StyledText(c, SWT.WRAP);
            FormDataSet.attach(footnoteTextWidget).atLeft().atTopTo(choiceLabelD, 40).atRight();
            StyledTextSet.decorate(footnoteTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("footnote"));
        }

        updateWidgetsForAnswers();
    }

    /*
     * ==================================================
     *
     * Update Widgets for Answers
     *
     * ==================================================
     */

    private void updateWidgetsForAnswers() {
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
        logger.info("[Reading Question {}] Answer: {}", vo.getQuestionNumberInSection(), answer);
    }

    @Override
    public void updateRight() {
        initIndicator();
        initRightBody();
    }

    private void initIndicator() {
        indicator = new CLabel(right, SWT.RIGHT);
        FormDataSet.attach(indicator).atLeft().atTop().atRight();
        CLabelSet.decorate(indicator).setBackground(MT.COLOR_INDIGO).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_WHITE);
    }

    private void initRightBody() {
        rightScrolled = new ScrolledComposite(right, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(rightScrolled).atLeft().atTopTo(indicator).atRight().atBottom();
        rightScrolled.setExpandHorizontal(true);
        rightScrolled.setExpandVertical(true);

        final Composite c = new Composite(rightScrolled, SWT.NONE);
        FormLayoutSet.layout(c).marginWidth(10).marginTop(20).marginBottom(100).spacing(20);

        final StyledText headingTextWidget = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTop().atRight();
        StyledTextSet.decorate(headingTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledTextContent("heading"));

        final StyledText passageTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(headingTextWidget).atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(passageTextWidget).setNoCaret().setCursor(MT.CURSOR_ARROW).setEditable(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("passage"));
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledTextStyles("passage"));
        passageTextWidget.addControlListener(new ParagraphTextControlAdapter());

        rightScrolled.setContent(c);
        rightScrolled.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
            release();
            PersistenceUtils.saveToNextView(ReadingMultipleChoiceQuestionView.this);
            page.resume();
        }
    }

    private class BackOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToPreviousView(ReadingMultipleChoiceQuestionView.this);
            page.resume();
        }
    }

    private class ReviewOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            page.toReadingReview();
        }
    }

    private class HelpOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
        }
    }

    private class ChooseAnswerAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            answer = (Integer) e.widget.getData(MT.KEY_CHOICE);
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
            logger.info("[Reading Question {}] Answer: {}", vo.getQuestionNumberInSection(), answer);
            answerText = Integer.toString(answer);
            PersistenceUtils.saveAnswer(ReadingMultipleChoiceQuestionView.this, answerText);
        }
    }

    private class ParagraphTextControlAdapter extends ControlAdapter {

        @Override
        public void controlResized(ControlEvent e) {
            StyledText st = (StyledText) e.widget;
            Rectangle bounds = st.getBounds();
            int quarter = rightScrolled.getBounds().height / 4;
            int offsetY = bounds.y + st.getLocationAtOffset(vo.getPassageOffset()).y;
            if (offsetY > quarter) {
                rightScrolled.setOrigin(0, offsetY - quarter);
            }
        }
    }
}
