package com.mocktpo.modules.test.views;

import com.mocktpo.modules.test.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.modules.test.widgets.DroppableAnswerComposite;
import com.mocktpo.modules.system.widgets.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReadingProseSummaryQuestionView extends StackTestView {

    /* Constants */

    private static final int SUB_VIEW_QUESTION = 1;
    private static final int SUB_VIEW_PASSAGE = 2;

    private static final int ANSWER_1 = 1;
    private static final int ANSWER_2 = 2;
    private static final int ANSWER_3 = 3;

    /* Widgets */

    private ImageButton viewTextOrQuestionButton;

    private Composite questionView;
    private DroppableAnswerComposite blank1, blank2, blank3;
    private Label choiceALabel, choiceBLabel, choiceCLabel, choiceDLabel, choiceELabel, choiceFLabel;

    private Composite passageView;
    private Composite rightPassageView;

    /* Properties */

    private int subViewId;

    private int answer1, answer2, answer3;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingProseSummaryQuestionView(TestPage page, int style) {
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
        final ImageButton nextOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nextOvalButton).atRight(10).atTop(10);
        nextOvalButton.addMouseListener(new NextOvalButtonMouseAdapter());

        final ImageButton backOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_BACK_OVAL, MT.IMAGE_BACK_OVAL_HOVER, MT.IMAGE_BACK_OVAL_DISABLED);
        FormDataSet.attach(backOvalButton).atRightTo(nextOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        backOvalButton.addMouseListener(new BackOvalButtonMouseAdapter());

        final ImageButton reviewOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_REVIEW_OVAL, MT.IMAGE_REVIEW_OVAL_HOVER, MT.IMAGE_REVIEW_OVAL_DISABLED);
        FormDataSet.attach(reviewOvalButton).atRightTo(backOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        reviewOvalButton.addMouseListener(new ReviewOvalButtonMouseAdapter());

        final ImageButton helpOvalButton = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(helpOvalButton).atRightTo(reviewOvalButton).atTopTo(nextOvalButton, 0, SWT.TOP);
        helpOvalButton.addMouseListener(new HelpOvalButtonMouseAdapter());

        viewTextOrQuestionButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VIEW_TEXT, MT.IMAGE_VIEW_TEXT_HOVER);
        FormDataSet.attach(viewTextOrQuestionButton).atRightTo(helpOvalButton, 6).atTopTo(nextOvalButton, 6, SWT.TOP);
        viewTextOrQuestionButton.addMouseListener(new ViewTextOrQuestionButtonMouseAdapter());
    }

    @Override
    public void updateBody() {
        subViewId = SUB_VIEW_QUESTION;
        stack.topControl = getSubView(subViewId);
        body.layout();
    }

    protected Composite getSubView(int subViewId) {
        switch (subViewId) {
            case SUB_VIEW_QUESTION:
                if (questionView == null) {
                    questionView = initQuestionSubView();
                }
                return questionView;
            case SUB_VIEW_PASSAGE:
                if (passageView == null) {
                    passageView = initPassageSubView();
                }
                return passageView;
        }
        return null;
    }

    /*
     * ==================================================
     *
     * Sub Views
     *
     * ==================================================
     */

    private Composite initQuestionSubView() {
        final Composite c = new Composite(body, SWT.NONE);
        FormLayoutSet.layout(c);

        final ScrolledComposite sc = new ScrolledComposite(c, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite inner = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(inner).marginBottom(50);

        final Composite viewPort = new Composite(inner, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x);
        FormLayoutSet.layout(viewPort);

        final StyledText directionsTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(directionsTextWidget).atLeft().atTop(5).atRight();
        StyledTextSet.decorate(directionsTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("directions").getText());
        StyleRangeUtils.decorate(directionsTextWidget, vo.getStyledText("directions").getStyles());

        final StyledText tipsTextWidget = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tipsTextWidget).atLeft().atTopTo(directionsTextWidget, 5).atRight();
        StyledTextSet.decorate(tipsTextWidget).setAlignment(SWT.CENTER).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setMargins(5).setText(vo.getStyledText("tips").getText());
        StyleRangeUtils.decorate(tipsTextWidget, vo.getStyledText("tips").getStyles());

        final Composite ac = new Composite(viewPort, SWT.CENTER);
        FormDataSet.attach(ac).fromLeft(50, -ScreenUtils.getClientWidth(d) / 4).atTopTo(tipsTextWidget, 10).withWidth(ScreenUtils.getClientWidth(d) / 2);
        FormLayoutSet.layout(ac).marginWidth(10).marginHeight(10);
        ac.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_GRAY40));

        final StyledText questionTextWidget = new StyledText(ac, SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeft(10).atTop().atRight(10);
        StyledTextSet.decorate(questionTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setLineSpacing(5).setText(vo.getStyledText("question").getText());

        final Label bullet1 = new Label(ac, SWT.NONE);
        FormDataSet.attach(bullet1).atLeft().atTopTo(questionTextWidget, 20);
        LabelSet.decorate(bullet1).setImage(MT.IMAGE_BULLET);

        blank1 = new DroppableAnswerComposite(ac, SWT.WRAP | SWT.TOP, ANSWER_1);
        FormDataSet.attach(blank1).atLeftTo(bullet1, 10).atTopTo(questionTextWidget, 10).atRight().withHeight(LC.READING_DND_QUESTION_HEIGHT);
        AnswerCompositeDropTargetSet.drop(blank1);
        blank1.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
        blank1.addMouseListener(new AnswerCompositeMouseAdapter());

        final Label bullet2 = new Label(ac, SWT.NONE);
        FormDataSet.attach(bullet2).atLeft().atTopTo(blank1, 20);
        LabelSet.decorate(bullet2).setImage(MT.IMAGE_BULLET);

        blank2 = new DroppableAnswerComposite(ac, SWT.WRAP, ANSWER_2);
        FormDataSet.attach(blank2).atLeftTo(bullet2, 10).atTopTo(blank1, 10).atRight().withHeight(LC.READING_DND_QUESTION_HEIGHT);
        AnswerCompositeDropTargetSet.drop(blank2);
        blank2.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
        blank2.addMouseListener(new AnswerCompositeMouseAdapter());

        final Label bullet3 = new Label(ac, SWT.NONE);
        FormDataSet.attach(bullet3).atLeft().atTopTo(blank2, 20);
        LabelSet.decorate(bullet3).setImage(MT.IMAGE_BULLET);

        blank3 = new DroppableAnswerComposite(ac, SWT.WRAP, ANSWER_3);
        FormDataSet.attach(blank3).atLeftTo(bullet3, 10).atTopTo(blank2, 10).atRight().withHeight(LC.READING_DND_QUESTION_HEIGHT);
        AnswerCompositeDropTargetSet.drop(blank3);
        blank3.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
        blank3.addMouseListener(new AnswerCompositeMouseAdapter());

        final CLabel l = new CLabel(viewPort, SWT.CENTER);
        FormDataSet.attach(l).atLeft().atTopTo(ac, 10).atRight();
        CLabelSet.decorate(l).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("answer_choices"));

        choiceALabel = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceALabel).fromLeft(50, -LC.READING_DND_QUESTION_WIDTH - 10).atTopTo(l, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceALabel).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceA").getText());
        ChoiceLabelDragSourceSet.drag(choiceALabel);

        choiceBLabel = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceBLabel).fromLeft(50, 10).atTopTo(l, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceBLabel).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceB").getText());
        ChoiceLabelDragSourceSet.drag(choiceBLabel);

        choiceCLabel = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceCLabel).fromLeft(50, -LC.READING_DND_QUESTION_WIDTH - 10).atTopTo(choiceALabel, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceCLabel).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceC").getText());
        ChoiceLabelDragSourceSet.drag(choiceCLabel);

        choiceDLabel = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceDLabel).fromLeft(50, 10).atTopTo(choiceBLabel, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceDLabel).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceD").getText());
        ChoiceLabelDragSourceSet.drag(choiceDLabel);

        choiceELabel = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceELabel).fromLeft(50, -LC.READING_DND_QUESTION_WIDTH - 10).atTopTo(choiceCLabel, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceELabel).setData(MT.KEY_CHOICE, MT.CHOICE_E).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceE").getText());
        ChoiceLabelDragSourceSet.drag(choiceELabel);

        choiceFLabel = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceFLabel).fromLeft(50, 10).atTopTo(choiceDLabel, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceFLabel).setData(MT.KEY_CHOICE, MT.CHOICE_F).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceF").getText());
        ChoiceLabelDragSourceSet.drag(choiceFLabel);

        updateWidgetsForAnswers();

        sc.setContent(inner);
        sc.setMinSize(inner.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return c;
    }

    /*
     * ==================================================
     *
     * Update Widgets for Answers
     *
     * ==================================================
     */

    private void updateWidgetsForAnswers() {
        String[] arr = answerText.split(MT.STRING_COMMA);
        if (arr.length == 3) {
            answer1 = Integer.parseInt(arr[0]);
            if (answer1 != 0) {
                blank1.setAnswer(answer1);
                markWidgetsForAnswers(blank1, answer1);
            }
            answer2 = Integer.parseInt(arr[1]);
            if (answer2 != 0) {
                blank2.setAnswer(answer2);
                markWidgetsForAnswers(blank2, answer2);
            }
            answer3 = Integer.parseInt(arr[2]);
            if (answer3 != 0) {
                blank3.setAnswer(answer3);
                markWidgetsForAnswers(blank3, answer3);
            }
        }
    }

    private void markWidgetsForAnswers(DroppableAnswerComposite blank, int answer) {
        switch (answer) {
            case MT.CHOICE_A:
                blank.setText(choiceALabel.getText());
                LabelSet.decorate(choiceALabel).setText("");
                break;
            case MT.CHOICE_B:
                blank.setText(choiceBLabel.getText());
                LabelSet.decorate(choiceBLabel).setText("");
                break;
            case MT.CHOICE_C:
                blank.setText(choiceCLabel.getText());
                LabelSet.decorate(choiceCLabel).setText("");
                break;
            case MT.CHOICE_D:
                blank.setText(choiceDLabel.getText());
                LabelSet.decorate(choiceDLabel).setText("");
                break;
            case MT.CHOICE_E:
                blank.setText(choiceELabel.getText());
                LabelSet.decorate(choiceELabel).setText("");
                break;
            case MT.CHOICE_F:
                blank.setText(choiceFLabel.getText());
                LabelSet.decorate(choiceFLabel).setText("");
                break;
        }
    }

    private Composite initPassageSubView() {
        final Composite c = new Composite(body, SWT.NONE);
        FormLayoutSet.layout(c);

        final Composite left = new Composite(c, SWT.NONE);
        FormDataSet.attach(left).atLeft().atTop().fromRight(50).atBottom();
        FormLayoutSet.layout(left);

        final Label divider = new Label(left, SWT.VERTICAL);
        FormDataSet.attach(divider).atTop().atRight().atBottom().withWidth(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        rightPassageView = new Composite(c, SWT.NONE);
        FormDataSet.attach(rightPassageView).atLeftTo(left).atTop().atRight().atBottom();
        FormLayoutSet.layout(rightPassageView);

        initRightBody();

        return c;
    }

    private void initRightBody() {
        final ScrolledComposite sc = new ScrolledComposite(rightPassageView, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite c = new Composite(sc, SWT.NONE);
        FormLayoutSet.layout(c).marginWidth(10).marginTop(20).marginBottom(100).spacing(20);

        final StyledText headingTextWidget = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTop().atRight();
        StyledTextSet.decorate(headingTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledText("heading").getText());

        final StyledText passageTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(headingTextWidget).atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(passageTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("passage").getText());
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledText("passage").getStyles());

        sc.setContent(c);
        sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
            PersistenceUtils.saveToNextView(ReadingProseSummaryQuestionView.this);
            page.resume();
        }
    }

    private class BackOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToPreviousView(ReadingProseSummaryQuestionView.this);
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

    private class ViewTextOrQuestionButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            switch (subViewId) {
                case SUB_VIEW_QUESTION:
                    viewTextOrQuestionButton.setBackgroundImages(MT.IMAGE_VIEW_QUESTION, MT.IMAGE_VIEW_QUESTION_HOVER);
                    subViewId = SUB_VIEW_PASSAGE;
                    break;
                case SUB_VIEW_PASSAGE:
                    viewTextOrQuestionButton.setBackgroundImages(MT.IMAGE_VIEW_TEXT, MT.IMAGE_VIEW_TEXT_HOVER);
                    subViewId = SUB_VIEW_QUESTION;
                    break;
            }
            stack.topControl = getSubView(subViewId);
            body.layout();
        }
    }

    private class AnswerCompositePropertyChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {
            int oldAnswer = (Integer) e.getOldValue();
            switch (oldAnswer) {
                case MT.CHOICE_A:
                    LabelSet.decorate(choiceALabel).setText(vo.getStyledText("choiceA").getText());
                    break;
                case MT.CHOICE_B:
                    LabelSet.decorate(choiceBLabel).setText(vo.getStyledText("choiceB").getText());
                    break;
                case MT.CHOICE_C:
                    LabelSet.decorate(choiceCLabel).setText(vo.getStyledText("choiceC").getText());
                    break;
                case MT.CHOICE_D:
                    LabelSet.decorate(choiceDLabel).setText(vo.getStyledText("choiceD").getText());
                    break;
                case MT.CHOICE_E:
                    LabelSet.decorate(choiceELabel).setText(vo.getStyledText("choiceE").getText());
                    break;
                case MT.CHOICE_F:
                    LabelSet.decorate(choiceFLabel).setText(vo.getStyledText("choiceF").getText());
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
            logger.info("[Reading Prose Summary Question {}] Answers: ({}, {}, {})", vo.getQuestionNumberInSection(), answer1, answer2, answer3);
            answerText = answer1 + MT.STRING_COMMA + answer2 + MT.STRING_COMMA + answer3;
            PersistenceUtils.saveAnswer(ReadingProseSummaryQuestionView.this, answerText);
        }
    }

    private class AnswerCompositeMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            Label answerLabel = (Label) e.widget;
            int answer = (Integer) answerLabel.getData(MT.KEY_CHOICE);
            switch (answer) {
                case MT.CHOICE_A:
                    LabelSet.decorate(choiceALabel).setText(vo.getStyledText("choiceA").getText());
                    break;
                case MT.CHOICE_B:
                    LabelSet.decorate(choiceBLabel).setText(vo.getStyledText("choiceB").getText());
                    break;
                case MT.CHOICE_C:
                    LabelSet.decorate(choiceCLabel).setText(vo.getStyledText("choiceC").getText());
                    break;
                case MT.CHOICE_D:
                    LabelSet.decorate(choiceDLabel).setText(vo.getStyledText("choiceD").getText());
                    break;
                case MT.CHOICE_E:
                    LabelSet.decorate(choiceELabel).setText(vo.getStyledText("choiceE").getText());
                    break;
                case MT.CHOICE_F:
                    LabelSet.decorate(choiceFLabel).setText(vo.getStyledText("choiceF").getText());
                    break;
            }
            LabelSet.decorate(answerLabel).setText("");
            DroppableAnswerComposite blank = (DroppableAnswerComposite) answerLabel.getParent();
            blank.setAnswer(0);
        }
    }
}
