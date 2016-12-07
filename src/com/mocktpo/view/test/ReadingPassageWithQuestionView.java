package com.mocktpo.view.test;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.orm.mapper.UserTestMapper;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.ImageButton;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ReadingPassageWithQuestionView extends SashTestView {

    /* Constants */

    private static final String SQUARE = "\u2588";

    /* Widgets */

    private CLabel indicator;
    private ScrolledComposite rightScrolled;
    private Label choiceA, choiceB, choiceC, choiceD;

    /* Properties */

    private int answer;
    private String insertText;
    private int insertPointA, insertPointB, insertPointC, insertPointD;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingPassageWithQuestionView(TestPage page, int style) {
        super(page, style);
        this.answer = MT.CHOICE_NONE;
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    @Override
    public void updateHeader() {

        final ImageButton nob = new ImageButton(header, SWT.NONE, MT.IMAGE_NEXT_OVAL, MT.IMAGE_NEXT_OVAL_HOVER, MT.IMAGE_NEXT_OVAL_DISABLED);
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.addMouseListener(new NextOvalButtonMouseListener());

        final ImageButton bob = new ImageButton(header, SWT.NONE, MT.IMAGE_BACK_OVAL, MT.IMAGE_BACK_OVAL_HOVER, MT.IMAGE_BACK_OVAL_DISABLED);
        FormDataSet.attach(bob).atRightTo(nob).atTop(10);
        bob.addMouseListener(new BackOvalButtonMouseListener());

        final ImageButton rob = new ImageButton(header, SWT.NONE, MT.IMAGE_REVIEW_OVAL, MT.IMAGE_REVIEW_OVAL_HOVER, MT.IMAGE_REVIEW_OVAL_DISABLED);
        FormDataSet.attach(rob).atRightTo(bob).atTop(10);
        rob.addMouseListener(new ReviewOvalButtonMouseListener());

        final ImageButton hob = new ImageButton(header, SWT.NONE, MT.IMAGE_HELP_OVAL, MT.IMAGE_HELP_OVAL_HOVER, MT.IMAGE_HELP_OVAL_DISABLED);
        FormDataSet.attach(hob).atRightTo(rob).atTop(10);
        hob.addMouseListener(new HelpOvalButtonMouseListener());
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

        final StyledText ht = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(ht).atLeft().atTop().atRight();
        StyledTextSet.decorate(ht).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledText("heading").getText());

        final StyledText pt = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(pt).atLeft().atTopTo(ht).atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(pt).setNoCaret().setCursor(MT.CURSOR_ARROW).setEditable(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("passage").getText());
        StyleRangeUtils.decorate(pt, vo.getStyledText("passage").getStyles());
        pt.addControlListener(new ParagraphTextControlListener());
        pt.addMouseListener(new ParagraphTextMouseListener());

        rightScrolled.setContent(c);
        rightScrolled.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    @Override
    public void updateLeft() {

        final Composite c = new Composite(left, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTop().atRight().atBottom();
        FormLayoutSet.layout(c).marginWidth(20).marginHeight(20);

        final StyledText question = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(question).atLeft().atTop().atRight();
        StyledTextSet.decorate(question).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(question, vo.getStyledText("question").getStyles());

        if (null != vo.getStyledText("choiceA") && null != vo.getStyledText("choiceB") && null != vo.getStyledText("choiceC") && null != vo.getStyledText("choiceD")) {

            choiceA = new Label(c, SWT.NONE);
            FormDataSet.attach(choiceA).atLeft(10).atTopTo(question, 25);
            LabelSet.decorate(choiceA).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setImage(MT.IMAGE_UNCHECKED);
            choiceA.addMouseListener(new ChooseAnswerListener());

            final Label la = new Label(c, SWT.WRAP);
            FormDataSet.attach(la).atLeftTo(choiceA, 5).atTopTo(question, 20).atRight();
            LabelSet.decorate(la).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_A).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceA").getText());
            la.addMouseListener(new ChooseAnswerListener());

            choiceB = new Label(c, SWT.NONE);
            FormDataSet.attach(choiceB).atLeft(10).atTopTo(la, 25);
            LabelSet.decorate(choiceB).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setImage(MT.IMAGE_UNCHECKED);
            choiceB.addMouseListener(new ChooseAnswerListener());

            final Label lb = new Label(c, SWT.WRAP);
            FormDataSet.attach(lb).atLeftTo(choiceB, 5).atTopTo(la, 20).atRight();
            LabelSet.decorate(lb).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_B).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceB").getText());
            lb.addMouseListener(new ChooseAnswerListener());

            choiceC = new Label(c, SWT.NONE);
            FormDataSet.attach(choiceC).atLeft(10).atTopTo(lb, 25);
            LabelSet.decorate(choiceC).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setImage(MT.IMAGE_UNCHECKED);
            choiceC.addMouseListener(new ChooseAnswerListener());

            final Label lc = new Label(c, SWT.WRAP);
            FormDataSet.attach(lc).atLeftTo(choiceC, 5).atTopTo(lb, 20).atRight();
            LabelSet.decorate(lc).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_C).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceC").getText());
            lc.addMouseListener(new ChooseAnswerListener());

            choiceD = new Label(c, SWT.NONE);
            FormDataSet.attach(choiceD).atLeft(10).atTopTo(lc, 25);
            LabelSet.decorate(choiceD).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setImage(MT.IMAGE_UNCHECKED);
            choiceD.addMouseListener(new ChooseAnswerListener());

            final Label ld = new Label(c, SWT.WRAP);
            FormDataSet.attach(ld).atLeftTo(choiceD, 5).atTopTo(lc, 20).atRight();
            LabelSet.decorate(ld).setCursor(MT.CURSOR_HAND).setData(MT.KEY_CHOICE, MT.CHOICE_D).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceD").getText());
            ld.addMouseListener(new ChooseAnswerListener());

            if (null != vo.getStyledText("footnote")) {
                final StyledText fn = new StyledText(c, SWT.WRAP);
                FormDataSet.attach(fn).atLeft().atTopTo(ld, 40).atRight();
                StyledTextSet.decorate(fn).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("footnote").getText());
            }
        }

        if (null != vo.getStyledText("insertText") && null != vo.getStyledText("insertPointA") && null != vo.getStyledText("insertPointB") && null != vo.getStyledText("insertPointC") && null != vo.getStyledText("insertPointD")) {

            final StyledText it = new StyledText(c, SWT.WRAP);
            FormDataSet.attach(it).atLeft().atTopTo(question, 20).atRight();
            StyledTextSet.decorate(it).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setLineSpacing(5).setText(vo.getStyledText("insertText").getText());

            insertText = vo.getStyledText("insertText").getText();
            insertPointA = vo.getStyledText("insertPointA").getStyles().get(0).getStart();
            insertPointB = vo.getStyledText("insertPointB").getStyles().get(0).getStart();
            insertPointC = vo.getStyledText("insertPointC").getStyles().get(0).getStart();
            insertPointD = vo.getStyledText("insertPointD").getStyles().get(0).getStart();

            if (null != vo.getStyledText("footnote")) {
                final StyledText fn = new StyledText(c, SWT.WRAP);
                FormDataSet.attach(fn).atLeft().atTopTo(it, 20).atRight();
                StyledTextSet.decorate(fn).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("footnote").getText());
            }
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

    private class BackOvalButtonMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            release();

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

            release();

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

    private class ChooseAnswerListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            answer = (Integer) e.widget.getData(MT.KEY_CHOICE);

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

    private class ParagraphTextControlListener implements ControlListener {

        @Override
        public void controlMoved(ControlEvent e) {
        }

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

    private class ParagraphTextMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            if (null == vo.getStyledText("insertText") || null == vo.getStyledText("insertPointA") || null == vo.getStyledText("insertPointB") || null == vo.getStyledText("insertPointC") || null == vo.getStyledText("insertPointD")) {
                return;
            }

            int insertTextLength = insertText.length();
            int change = insertTextLength - 1;

            StyledText st = (StyledText) e.widget;
            int offset = st.getCaretOffset();

            if (answer == MT.CHOICE_NONE) {
                if (offset == insertPointA || offset == insertPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
                    insertPointB += change;
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset == insertPointB || offset == insertPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset == insertPointC || offset == insertPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset == insertPointD || offset == insertPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Set choice to D
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_D;

                }
            } else if (answer == MT.CHOICE_A) {
                if (offset >= insertPointA && offset <= insertPointA + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, insertTextLength, SQUARE);
                    st.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
                    insertPointB -= change;
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to NONE
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_NONE;

                } else if (offset == insertPointB || offset == insertPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                    st.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
                    insertPointB -= change;
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset == insertPointC || offset == insertPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                    st.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
                    insertPointB -= change;
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset == insertPointD || offset == insertPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                    st.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
                    insertPointB -= change;
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to D
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_D;

                }
            } else if (answer == MT.CHOICE_B) {
                if (offset == insertPointA || offset == insertPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
                    insertPointB += change;
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                    st.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset >= insertPointB && offset <= insertPointB + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, insertTextLength, SQUARE);
                    st.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to NONE
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_NONE;

                } else if (offset == insertPointC || offset == insertPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                    st.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset == insertPointD || offset == insertPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                    st.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
                    insertPointC -= change;
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to D
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_D;

                }
            } else if (answer == MT.CHOICE_C) {
                if (offset == insertPointA || offset == insertPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
                    insertPointB += change;
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                    st.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset == insertPointB || offset == insertPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                    st.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset >= insertPointC && offset <= insertPointC + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, insertTextLength, SQUARE);
                    st.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to NONE
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_NONE;

                } else if (offset == insertPointD || offset == insertPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                    st.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
                    insertPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to D
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_D;

                }
            } else if (answer == MT.CHOICE_D) {
                if (offset == insertPointA || offset == insertPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointA, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
                    insertPointB += change;
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset == insertPointB || offset == insertPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointB, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset == insertPointC || offset == insertPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointC, 1, insertText);
                    st.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset >= insertPointD && offset <= insertPointD + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    st.replaceTextRange(insertPointD, insertTextLength, SQUARE);
                    st.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));

                    /*
                     * ==================================================
                     *
                     * Set choice to NONE
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_NONE;

                }
            }

            logger.info("Reading Insert Text Question Answer: {}.", answer);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
