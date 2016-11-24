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

public class ReadingTextWithQuestionView extends SashTestView {

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

    public ReadingTextWithQuestionView(TestPage page, int style) {
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

        final StyledText caption = new StyledText(header, SWT.SINGLE);
        FormDataSet.attach(caption).fromLeft(50, -LC.CAPTION_WIDTH / 2).atBottomTo(pauseTestButton, 0, SWT.BOTTOM).withWidth(LC.CAPTION_WIDTH);
        StyledTextSet.decorate(caption).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_SMALL_BOLD).setForeground(MT.COLOR_WHITE_SMOKE).setText(MT.STRING_QUESTION + MT.STRING_SPACE + vo.getQuestionNumberInSection() + MT.STRING_SPACE + MT.STRING_OF + MT.STRING_SPACE + TestSchemaUtils.getTotalQuestionCountInSection(page.getTestSchema(), vo.getSectionType()));

        final ImageButton nob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_NEXT_OVAL), ResourceManager.getImage(MT.IMAGE_NEXT_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_NEXT_OVAL_DISABLED));
        FormDataSet.attach(nob).atRight(10).atTop(10);
        nob.addMouseListener(new NextOvalButtonMouseListener());

        final ImageButton bob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_BACK_OVAL), ResourceManager.getImage(MT.IMAGE_BACK_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_BACK_OVAL_DISABLED));
        FormDataSet.attach(bob).atRightTo(nob).atTop(10);
        bob.addMouseListener(new BackOvalButtonMouseListener());

        final ImageButton rob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_REVIEW_OVAL), ResourceManager.getImage(MT.IMAGE_REVIEW_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_REVIEW_OVAL_DISABLED));
        FormDataSet.attach(rob).atRightTo(bob).atTop(10);
        rob.addMouseListener(new ReviewOvalButtonMouseListener());

        final ImageButton hob = new ImageButton(header, SWT.NONE, ResourceManager.getImage(MT.IMAGE_HELP_OVAL), ResourceManager.getImage(MT.IMAGE_HELP_OVAL_HOVER), ResourceManager.getImage(MT.IMAGE_HELP_OVAL_DISABLED));
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
            LabelSet.decorate(choiceA).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_UNCHECKED);
            choiceA.setData(MT.KEY_CHOICE, MT.CHOICE_A);
            choiceA.addMouseListener(new ChooseAnswerListener());

            final Label la = new Label(c, SWT.WRAP);
            FormDataSet.attach(la).atLeftTo(choiceA, 5).atTopTo(question, 20).atRight();
            LabelSet.decorate(la).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceA").getText());
            la.setData(MT.KEY_CHOICE, MT.CHOICE_A);
            la.addMouseListener(new ChooseAnswerListener());

            choiceB = new Label(c, SWT.NONE);
            FormDataSet.attach(choiceB).atLeft(10).atTopTo(la, 25);
            LabelSet.decorate(choiceB).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_UNCHECKED);
            choiceB.setData(MT.KEY_CHOICE, MT.CHOICE_B);
            choiceB.addMouseListener(new ChooseAnswerListener());

            final Label lb = new Label(c, SWT.WRAP);
            FormDataSet.attach(lb).atLeftTo(choiceB, 5).atTopTo(la, 20).atRight();
            LabelSet.decorate(lb).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceB").getText());
            lb.setData(MT.KEY_CHOICE, MT.CHOICE_B);
            lb.addMouseListener(new ChooseAnswerListener());

            choiceC = new Label(c, SWT.NONE);
            FormDataSet.attach(choiceC).atLeft(10).atTopTo(lb, 25);
            LabelSet.decorate(choiceC).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_UNCHECKED);
            choiceC.setData(MT.KEY_CHOICE, MT.CHOICE_C);
            choiceC.addMouseListener(new ChooseAnswerListener());

            final Label lc = new Label(c, SWT.WRAP);
            FormDataSet.attach(lc).atLeftTo(choiceC, 5).atTopTo(lb, 20).atRight();
            LabelSet.decorate(lc).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceC").getText());
            lc.setData(MT.KEY_CHOICE, MT.CHOICE_C);
            lc.addMouseListener(new ChooseAnswerListener());

            choiceD = new Label(c, SWT.NONE);
            FormDataSet.attach(choiceD).atLeft(10).atTopTo(lc, 25);
            LabelSet.decorate(choiceD).setCursor(MT.CURSOR_HAND).setImage(MT.IMAGE_UNCHECKED);
            choiceD.setData(MT.KEY_CHOICE, MT.CHOICE_D);
            choiceD.addMouseListener(new ChooseAnswerListener());

            final Label ld = new Label(c, SWT.WRAP);
            FormDataSet.attach(ld).atLeftTo(choiceD, 5).atTopTo(lc, 20).atRight();
            LabelSet.decorate(ld).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM).setImage(MT.IMAGE_UNCHECKED).setText(vo.getStyledText("choiceD").getText());
            ld.setData(MT.KEY_CHOICE, MT.CHOICE_D);
            ld.addMouseListener(new ChooseAnswerListener());

            if (null != vo.getStyledText("footnote")) {
                final StyledText fn = new StyledText(c, SWT.WRAP);
                FormDataSet.attach(fn).atLeft().atTopTo(ld, 40).atRight();
                StyledTextSet.decorate(fn).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("footnote").getText());
            }
        } else {
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

            if (offset == insertPointA || offset == insertPointA + 1) {
                if (answer != MT.CHOICE_A) {

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
                     * Remove old text from the other point
                     * 
                     * ==================================================
                     */

                    switch (answer) {
                    case MT.CHOICE_B:
                        st.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                        insertPointC -= change;
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_C:
                        st.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_D:
                        st.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());
                    default:
                        break;
                    }

                    /*
                     * ==================================================
                     * 
                     * Set choice to A
                     * 
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;
                }
            } else if (offset == insertPointB || offset == insertPointB + 1) {
                if (answer != MT.CHOICE_B) {

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
                     * Remove old text from the other point
                     * 
                     * ==================================================
                     */

                    switch (answer) {
                    case MT.CHOICE_A:
                        st.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                        insertPointB -= change;
                        insertPointC -= change;
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_C:
                        st.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_D:
                        st.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());
                    default:
                        break;
                    }

                    /*
                     * ==================================================
                     * 
                     * Set choice to B
                     * 
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;
                }
            } else if (offset == insertPointC || offset == insertPointC + 1) {
                if (answer != MT.CHOICE_C) {

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
                     * Remove old text from the other point
                     * 
                     * ==================================================
                     */

                    switch (answer) {
                    case MT.CHOICE_A:
                        st.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                        insertPointB -= change;
                        insertPointC -= change;
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_B:
                        st.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                        insertPointC -= change;
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_D:
                        st.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());
                    default:
                        break;
                    }

                    /*
                     * ==================================================
                     * 
                     * Set choice to C
                     * 
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;
                }
            } else if (offset == insertPointD || offset == insertPointD + 1) {
                if (answer != MT.CHOICE_D) {

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
                     * Remove old text from the other point
                     * 
                     * ==================================================
                     */

                    switch (answer) {
                    case MT.CHOICE_A:
                        st.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                        insertPointB -= change;
                        insertPointC -= change;
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_B:
                        st.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                        insertPointC -= change;
                        insertPointD -= change;
                        break;
                    case MT.CHOICE_C:
                        st.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                        insertPointD -= change;
                    default:
                        break;
                    }

                    /*
                     * ==================================================
                     * 
                     * Set choice to D
                     * 
                     * ==================================================
                     */

                    answer = MT.CHOICE_D;
                }
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
