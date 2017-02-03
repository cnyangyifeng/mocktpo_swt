package com.mocktpo.view.test;

import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.UserTestPersistenceUtils;
import com.mocktpo.widget.ImageButton;
import org.apache.commons.lang3.StringUtils;
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

public class ReadingInsertTextQuestionView extends SashTestView {

    /* Constants */

    private static final String SQUARE = "\u2588";

    /* Widgets */

    private CLabel indicator;
    private ScrolledComposite rightScrolled;
    private StyledText passageTextWidget;

    /* Properties */

    private String insertText;
    private int insertPointA, insertPointB, insertPointC, insertPointD;

    private int answer;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingInsertTextQuestionView(TestPage page, int style) {
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
    public void updateLeft() {

        final Composite c = new Composite(left, SWT.NONE);
        FormDataSet.attach(c).atLeft().atTop().atRight().atBottom();
        FormLayoutSet.layout(c).marginWidth(20).marginHeight(20);

        final StyledText question = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(question).atLeft().atTop().atRight();
        StyledTextSet.decorate(question).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("question").getText());
        StyleRangeUtils.decorate(question, vo.getStyledText("question").getStyles());

        final StyledText it = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(it).atLeft().atTopTo(question, 20).atRight();
        StyledTextSet.decorate(it).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setLineSpacing(5).setText(vo.getStyledText("insertText").getText());

        insertText = vo.getStyledText("insertText").getText();
        insertPointA = vo.getStyledText("insertPointA").getStyles().get(0).getStart();
        insertPointB = vo.getStyledText("insertPointB").getStyles().get(0).getStart();
        insertPointC = vo.getStyledText("insertPointC").getStyles().get(0).getStart();
        insertPointD = vo.getStyledText("insertPointD").getStyles().get(0).getStart();

        final StyledText fn = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(fn).atLeft().atTopTo(it, 20).atRight();
        StyledTextSet.decorate(fn).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("footnote").getText());
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
        StyledTextSet.decorate(headingTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledText("heading").getText());

        passageTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(headingTextWidget).atBottom().withWidth(ScreenUtils.getHalfClientWidth(d));
        StyledTextSet.decorate(passageTextWidget).setNoCaret().setCursor(MT.CURSOR_ARROW).setEditable(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledText("passage").getText());
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledText("passage").getStyles());
        passageTextWidget.addControlListener(new PassageTextControlListener());
        passageTextWidget.addMouseListener(new PassageTextMouseListener());

        // updateUIForAnswer();

        rightScrolled.setContent(c);
        rightScrolled.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    /*
     * ==================================================
     *
     * UI Updates for Answer
     *
     * ==================================================
     */

    private void updateUIForAnswer() {

        int insertTextLength = insertText.length();
        int change = insertTextLength - 1;

        if (StringUtils.isEmpty(answerText)) {
            answer = MT.CHOICE_NONE;
        } else {
            answer = Integer.parseInt(answerText);
        }

        switch (answer) {
            case MT.CHOICE_NONE:
                break;
            case MT.CHOICE_A:
                /*
                 * ==================================================
                 *
                 * Insert new text at point A
                 *
                 * ==================================================
                 */
                passageTextWidget.replaceTextRange(insertPointA, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
                insertPointB += change;
                insertPointC += change;
                insertPointD += change;
                break;
            case MT.CHOICE_B:
                /*
                 * ==================================================
                 *
                 * Insert new text at point B
                 *
                 * ==================================================
                 */
                passageTextWidget.replaceTextRange(insertPointB, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                insertPointC += change;
                insertPointD += change;
                break;
            case MT.CHOICE_C:
                /*
                 * ==================================================
                 *
                 * Insert new text at point C
                 *
                 * ==================================================
                 */
                passageTextWidget.replaceTextRange(insertPointC, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                insertPointD += change;
                break;
            case MT.CHOICE_D:
                /*
                 * ==================================================
                 *
                 * Insert new text at point D
                 *
                 * ==================================================
                 */
                passageTextWidget.replaceTextRange(insertPointD, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));
                break;
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
            UserTestPersistenceUtils.saveToNextView(ReadingInsertTextQuestionView.this);
            page.resume();
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
            UserTestPersistenceUtils.saveToPreviousView(ReadingInsertTextQuestionView.this);
            page.resume();
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

    private class PassageTextControlListener implements ControlListener {

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

    private class PassageTextMouseListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            int insertTextLength = insertText.length();
            int change = insertTextLength - 1;

            int offset = passageTextWidget.getCaretOffset();

            if (answer == MT.CHOICE_NONE) {
                if (offset == insertPointA || offset == insertPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
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

                    passageTextWidget.replaceTextRange(insertPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
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

                    passageTextWidget.replaceTextRange(insertPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
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

                    passageTextWidget.replaceTextRange(insertPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

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

                    passageTextWidget.replaceTextRange(insertPointA, insertTextLength, SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointA, insertTextLength, vo.getStyledText("insertPointA").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
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

                    passageTextWidget.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointB, insertTextLength, SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointB, insertTextLength, vo.getStyledText("insertPointB").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
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

                    passageTextWidget.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointC, insertTextLength, SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointC, insertTextLength, vo.getStyledText("insertPointC").getText());
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));
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

                    passageTextWidget.replaceTextRange(insertPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointA, insertTextLength, null, null, SWT.BOLD));
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

                    passageTextWidget.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());

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

                    passageTextWidget.replaceTextRange(insertPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointB, insertTextLength, null, null, SWT.BOLD));
                    insertPointC += change;
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());

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

                    passageTextWidget.replaceTextRange(insertPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, insertTextLength, null, null, SWT.BOLD));
                    insertPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertPointD, insertTextLength, vo.getStyledText("insertPointD").getText());

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

                    passageTextWidget.replaceTextRange(insertPointD, insertTextLength, SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertPointC, 1, null, null, SWT.NORMAL));

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

            logger.info("[Reading Insert Text Question {}] Answer: {}", vo.getQuestionNumberInSection(), answer);

            answerText = Integer.toString(answer);
            UserTestPersistenceUtils.saveAnswers(ReadingInsertTextQuestionView.this, answerText);
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
