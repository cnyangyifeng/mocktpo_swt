package com.mocktpo.view.test;

import com.mocktpo.listener.BorderedCompositePaintListener;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.DroppableAnswerComposite;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ReadingFillTableQuestionView extends StackTestView {

    /* Constants */

    private static final int SUB_VIEW_QUESTION = 1;
    private static final int SUB_VIEW_TEXT = 2;

    private static final int ANSWER_1 = 1;
    private static final int ANSWER_2 = 2;
    private static final int ANSWER_3 = 3;
    private static final int ANSWER_4 = 4;
    private static final int ANSWER_5 = 5;
    private static final int ANSWER_6 = 6;
    private static final int ANSWER_7 = 7;

    /* Widgets */

    private ImageButton viewTextOrQuestionButton;

    private Composite questionView;
    private Label choiceA, choiceB, choiceC, choiceD, choiceE, choiceF, choiceG;

    private Composite textView;
    private Composite rightTextView;

    /* Properties */

    private int subViewId;
    private int answer1, answer2, answer3, answer4, answer5, answer6, answer7;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingFillTableQuestionView(TestPage page, int style) {
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

        final StyledText caption = new StyledText(header, SWT.SINGLE);
        FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
        StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(MT.STRING_QUESTION + MT.STRING_SPACE + vo.getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + TestSchemaUtils.getTotalQuestionCountInSection(page.getTestSchema(), vo.getSectionType()));

        final ImageButton nob = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.addMouseListener(new NextOvalButtonMouseListener());

        final ImageButton bob = new ImageButton(header, SWT.NONE, MT.IMAGE_BACK_OVAL, MT.IMAGE_BACK_OVAL_HOVER, MT.IMAGE_BACK_OVAL_DISABLED);
        FormDataSet.attach(bob).atRightTo(nob).atTopTo(nob, 0, SWT.TOP);
        bob.addMouseListener(new BackOvalButtonMouseListener());

        final ImageButton rob = new ImageButton(header, SWT.NONE, MT.IMAGE_REVIEW_OVAL, MT.IMAGE_REVIEW_OVAL_HOVER, MT.IMAGE_REVIEW_OVAL_DISABLED);
        FormDataSet.attach(rob).atRightTo(bob).atTopTo(nob, 0, SWT.TOP);
        rob.addMouseListener(new ReviewOvalButtonMouseListener());

        final ImageButton hob = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(hob).atRightTo(rob).atTopTo(nob, 0, SWT.TOP);
        hob.addMouseListener(new HelpOvalButtonMouseListener());

        viewTextOrQuestionButton = new ImageButton(header, SWT.NONE, MT.IMAGE_VIEW_TEXT, MT.IMAGE_VIEW_TEXT_HOVER);
        FormDataSet.attach(viewTextOrQuestionButton).atRightTo(hob, 6).atTopTo(nob, 6, SWT.TOP);
        viewTextOrQuestionButton.addMouseListener(new ViewTextOrQuestionButtonMouseListener());
    }

    @Override
    public void updateBody() {
        subViewId = SUB_VIEW_QUESTION;
        stack.topControl = getSubView(subViewId);
        body.layout();
    }

    private Composite getSubView(int subViewId) {

        switch (subViewId) {
            case SUB_VIEW_QUESTION:
                if (null == questionView) {
                    questionView = initQuestionSubView();
                }
                return questionView;
            case SUB_VIEW_TEXT:
                if (null == textView) {
                    textView = initTextSubView();
                }
                return textView;
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

        /*
         * ==================================================
         *
         * Directions
         *
         * ==================================================
         */

        final StyledText dt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(dt).atLeft().atTop(5).atRight();
        StyledTextSet.decorate(dt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("directions").getText());
        StyleRangeUtils.decorate(dt, vo.getStyledText("directions").getStyles());

        /*
         * ==================================================
         *
         * Tips
         *
         * ==================================================
         */

        final StyledText tt = new StyledText(viewPort, SWT.WRAP);
        FormDataSet.attach(tt).atLeft().atTopTo(dt, 5).atRight();
        StyledTextSet.decorate(tt).setAlignment(SWT.CENTER).setBackground(MT.COLOR_HIGHLIGHTED).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setMargins(5).setText(vo.getStyledText("tips").getText());
        StyleRangeUtils.decorate(tt, vo.getStyledText("tips").getStyles());

        /*
         * ==================================================
         *
         * Answer Composite
         *
         * ==================================================
         */

        final Composite ac = new Composite(viewPort, SWT.CENTER);
        FormDataSet.attach(ac).fromLeft(50, -ScreenUtils.getClientWidth(d) / 4).atTopTo(tt, 10).withWidth(ScreenUtils.getClientWidth(d) / 2);
        FormLayoutSet.layout(ac).marginHeight(10).spacing(10);
        ac.addPaintListener(new BorderedCompositePaintListener());

        String question = vo.getStyledText("question").getText();
        String[] questions = question.split(";");

        /* Category 1 */

        final String categoryName1 = questions[0].split(":")[0];
        final int totalAnswerCountInCategory1 = Integer.parseInt(questions[0].split(":")[1]);
        final CLabel cl1 = new CLabel(ac, SWT.NONE);
        FormDataSet.attach(cl1).atLeft(10).atTop().withWidth(120);
        CLabelSet.decorate(cl1).setFont(MT.FONT_MEDIUM).setText(categoryName1);
        for (int i = 0; i < totalAnswerCountInCategory1; i++) {
            /* Bullet */
            final Label bullet = new Label(ac, SWT.NONE);
            FormDataSet.attach(bullet).atLeftTo(cl1).atTop(8 + i * LC.READING_DND_QUESTION_HEIGHT);
            LabelSet.decorate(bullet).setImage(MT.IMAGE_BULLET);
            /* Blank */
            final DroppableAnswerComposite blank = new DroppableAnswerComposite(ac, SWT.WRAP | SWT.TOP, i + 1);
            FormDataSet.attach(blank).atLeftTo(bullet).atTop(i * LC.READING_DND_QUESTION_HEIGHT).atRight(10).withHeight(LC.READING_DND_QUESTION_HEIGHT);
            AnswerCompositeDropTargetSet.drop(blank);
            blank.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
            blank.addMouseListener(new AnswerCompositeMouseListener());
        }
        final Label divider1 = new Label(ac, SWT.NONE);
        FormDataSet.attach(divider1).atLeft(0).atTopTo(cl1, totalAnswerCountInCategory1 * LC.READING_DND_QUESTION_HEIGHT, SWT.TOP).atRight().withHeight(1);
        LabelSet.decorate(divider1).setBackground(MT.COLOR_GRAY40);

        /* Category 2 */

        final String categoryName2 = questions[1].split(":")[0];
        final int totalAnswerCountInCategory2 = Integer.parseInt(questions[1].split(":")[1]);
        final CLabel cl2 = new CLabel(ac, SWT.NONE);
        FormDataSet.attach(cl2).atLeft(10).atTopTo(divider1).withWidth(120);
        CLabelSet.decorate(cl2).setFont(MT.FONT_MEDIUM).setText(categoryName2);
        for (int j = 0; j < totalAnswerCountInCategory2; j++) {
            /* Bullet */
            final Label bullet = new Label(ac, SWT.NONE);
            FormDataSet.attach(bullet).atLeftTo(cl2).atTopTo(divider1, 8 + j * LC.READING_DND_QUESTION_HEIGHT);
            LabelSet.decorate(bullet).setImage(MT.IMAGE_BULLET);
            /* Blank */
            final DroppableAnswerComposite blank = new DroppableAnswerComposite(ac, SWT.WRAP | SWT.TOP, totalAnswerCountInCategory1 + j + 1);
            FormDataSet.attach(blank).atLeftTo(bullet).atTopTo(divider1, j * LC.READING_DND_QUESTION_HEIGHT).atRight(10).withHeight(LC.READING_DND_QUESTION_HEIGHT);
            AnswerCompositeDropTargetSet.drop(blank);
            blank.addPropertyChangeListener(new AnswerCompositePropertyChangeListener());
            blank.addMouseListener(new AnswerCompositeMouseListener());
        }

        /*
         * ==================================================
         *
         * Answer Choices
         *
         * ==================================================
         */

        final CLabel l = new CLabel(viewPort, SWT.CENTER);
        FormDataSet.attach(l).atLeft().atTopTo(ac, 10).atRight();
        CLabelSet.decorate(l).setFont(MT.FONT_MEDIUM_BOLD).setText(msgs.getString("answer_choices"));

        choiceA = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceA).fromLeft(50, -LC.READING_DND_QUESTION_WIDTH - 10).atTopTo(l, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceA).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceA").getText());
        ChoiceLabelDragSourceSet.drag(choiceA);

        choiceB = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceB).fromLeft(50, 10).atTopTo(l, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceB).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceB").getText());
        ChoiceLabelDragSourceSet.drag(choiceB);

        choiceC = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceC).fromLeft(50, -LC.READING_DND_QUESTION_WIDTH - 10).atTopTo(choiceA, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceC).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceC").getText());
        ChoiceLabelDragSourceSet.drag(choiceC);

        choiceD = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceD).fromLeft(50, 10).atTopTo(choiceB, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceD).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceD").getText());
        ChoiceLabelDragSourceSet.drag(choiceD);

        choiceE = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceE).fromLeft(50, -LC.READING_DND_QUESTION_WIDTH - 10).atTopTo(choiceC, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceE).setData(MT.KEY_CHOICE, MT.CHOICE_E).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceE").getText());
        ChoiceLabelDragSourceSet.drag(choiceE);

        choiceF = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceF).fromLeft(50, 10).atTopTo(choiceD, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceF).setData(MT.KEY_CHOICE, MT.CHOICE_F).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceF").getText());
        ChoiceLabelDragSourceSet.drag(choiceF);

        choiceG = new Label(viewPort, SWT.WRAP);
        FormDataSet.attach(choiceG).fromLeft(50, -LC.READING_DND_QUESTION_WIDTH - 10).atTopTo(choiceE, 10).withWidth(LC.READING_DND_QUESTION_WIDTH).withHeight(LC.READING_DND_QUESTION_HEIGHT);
        LabelSet.decorate(choiceG).setData(MT.KEY_CHOICE, MT.CHOICE_G).setFont(MT.FONT_MEDIUM).setText(vo.getStyledText("choiceG").getText());
        ChoiceLabelDragSourceSet.drag(choiceG);

        sc.setContent(inner);
        sc.setMinSize(inner.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        return c;
    }

    private Composite initTextSubView() {

        final Composite c = new Composite(body, SWT.NONE);
        FormLayoutSet.layout(c);

        final Composite left = new Composite(c, SWT.NONE);
        FormDataSet.attach(left).atLeft().atTop().fromRight(50).atBottom();
        FormLayoutSet.layout(left);

        final Label divider = new Label(left, SWT.VERTICAL);
        FormDataSet.attach(divider).atTop().atRight().atBottom().withWidth(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        rightTextView = new Composite(c, SWT.NONE);
        FormDataSet.attach(rightTextView).atLeftTo(left).atTop().atRight().atBottom();
        FormLayoutSet.layout(rightTextView);

        initRightBody();

        return c;
    }

    private void initRightBody() {

        final ScrolledComposite sc = new ScrolledComposite(rightTextView, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        final Composite c = new Composite(sc, SWT.NONE);
        FormLayoutSet.layout(c).marginWidth(10).marginTop(20).marginBottom(100).spacing(20);

        final StyledText ht = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop().atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledText("heading").getText());

        final StyledText pt = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(pt).atLeft().atTopTo(ht).atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(pt).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("passage").getText());
        StyleRangeUtils.decorate(pt, vo.getStyledText("passage").getStyles());

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

    private class NextOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (vo.isTimed()) {
                stopTimer();
            }

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

    private class BackOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (vo.isTimed()) {
                stopTimer();
            }

            UserTest ut = page.getUserTest();
            ut.setLastViewId(vo.getViewId() - 1);

            sqlSession.getMapper(UserTestMapper.class).update(ut);
            sqlSession.commit();

            page.resume(ut);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class ReviewOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (vo.isTimed()) {
                stopTimer();
            }

            page.toReadingReview();
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

    private class ViewTextOrQuestionButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            switch (subViewId) {
                case SUB_VIEW_QUESTION:
                    viewTextOrQuestionButton.setBackgroundImages(MT.IMAGE_VIEW_QUESTION, MT.IMAGE_VIEW_QUESTION_HOVER);
                    subViewId = SUB_VIEW_TEXT;
                    break;
                case SUB_VIEW_TEXT:
                    viewTextOrQuestionButton.setBackgroundImages(MT.IMAGE_VIEW_TEXT, MT.IMAGE_VIEW_TEXT_HOVER);
                    subViewId = SUB_VIEW_QUESTION;
                    break;
            }

            stack.topControl = getSubView(subViewId);
            body.layout();
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    private class AnswerCompositePropertyChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent e) {

            int oldAnswer = (Integer) e.getOldValue();

            switch (oldAnswer) {
                case MT.CHOICE_A:
                    LabelSet.decorate(choiceA).setText(vo.getStyledText("choiceA").getText());
                    break;
                case MT.CHOICE_B:
                    LabelSet.decorate(choiceB).setText(vo.getStyledText("choiceB").getText());
                    break;
                case MT.CHOICE_C:
                    LabelSet.decorate(choiceC).setText(vo.getStyledText("choiceC").getText());
                    break;
                case MT.CHOICE_D:
                    LabelSet.decorate(choiceD).setText(vo.getStyledText("choiceD").getText());
                    break;
                case MT.CHOICE_E:
                    LabelSet.decorate(choiceE).setText(vo.getStyledText("choiceE").getText());
                    break;
                case MT.CHOICE_F:
                    LabelSet.decorate(choiceF).setText(vo.getStyledText("choiceF").getText());
                    break;
                case MT.CHOICE_G:
                    LabelSet.decorate(choiceG).setText(vo.getStyledText("choiceG").getText());
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
                case ANSWER_4:
                    answer4 = newAnswer;
                    break;
                case ANSWER_5:
                    answer5 = newAnswer;
                    break;
                case ANSWER_6:
                    answer6 = newAnswer;
                    break;
                case ANSWER_7:
                    answer7 = newAnswer;
                    break;
            }

            logger.info("Reading Fill in a Table Question Answers: ({}, {}, {}, {}, {}, {}, {}).", answer1, answer2, answer3, answer4, answer5, answer6, answer7);
        }
    }

    private class AnswerCompositeMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            Label answerLabel = (Label) e.widget;
            int answer = (Integer) answerLabel.getData(MT.KEY_CHOICE);

            switch (answer) {
                case MT.CHOICE_A:
                    LabelSet.decorate(choiceA).setText(vo.getStyledText("choiceA").getText());
                    break;
                case MT.CHOICE_B:
                    LabelSet.decorate(choiceB).setText(vo.getStyledText("choiceB").getText());
                    break;
                case MT.CHOICE_C:
                    LabelSet.decorate(choiceC).setText(vo.getStyledText("choiceC").getText());
                    break;
                case MT.CHOICE_D:
                    LabelSet.decorate(choiceD).setText(vo.getStyledText("choiceD").getText());
                    break;
                case MT.CHOICE_E:
                    LabelSet.decorate(choiceE).setText(vo.getStyledText("choiceE").getText());
                    break;
                case MT.CHOICE_F:
                    LabelSet.decorate(choiceF).setText(vo.getStyledText("choiceF").getText());
                    break;
                case MT.CHOICE_G:
                    LabelSet.decorate(choiceG).setText(vo.getStyledText("choiceG").getText());
                    break;
            }

            answerLabel.setText("");
            DroppableAnswerComposite blank = (DroppableAnswerComposite) answerLabel.getParent();
            blank.setAnswer(0);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
