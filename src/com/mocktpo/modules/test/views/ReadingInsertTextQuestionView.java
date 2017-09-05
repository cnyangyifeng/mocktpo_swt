package com.mocktpo.modules.test.views;

import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.PersistenceUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public class ReadingInsertTextQuestionView extends SashTestView {

    /* Widgets */

    private CLabel indicator;
    private ScrolledComposite rsc;
    private StyledText passageTextWidget;

    /* Properties */

    private String insertText;
    private int insertionPointA, insertionPointB, insertionPointC, insertionPointD;

    private int answer;
    private boolean autoScrollRequired;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingInsertTextQuestionView(TestPage page, int style) {
        super(page, style);
        this.autoScrollRequired = true;
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

        final StyledText insertTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(insertTextWidget).atLeft().atTopTo(questionTextWidget, 20).atRight();
        StyledTextSet.decorate(insertTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setLineSpacing(5).setText(vo.getStyledTextContent("insertText"));

        insertText = vo.getStyledTextContent("insertText");
        insertionPointA = vo.getStyledTextStyles("insertionPointA").get(0).getStart();
        insertionPointB = vo.getStyledTextStyles("insertionPointB").get(0).getStart();
        insertionPointC = vo.getStyledTextStyles("insertionPointC").get(0).getStart();
        insertionPointD = vo.getStyledTextStyles("insertionPointD").get(0).getStart();

        final StyledText footnoteTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(footnoteTextWidget).atLeft().atTopTo(insertTextWidget, 20).atRight();
        StyledTextSet.decorate(footnoteTextWidget).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("footnote"));
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
        rsc = new ScrolledComposite(right, SWT.V_SCROLL);
        FormDataSet.attach(rsc).atLeft().atTopTo(indicator).atRight().atBottom();
        rsc.setExpandHorizontal(true);
        rsc.setExpandVertical(true);

        final Composite c = new Composite(rsc, SWT.NONE);
        FormLayoutSet.layout(c).marginWidth(20).marginTop(20).marginBottom(0).spacing(20);

        final StyledText headingTextWidget = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTop().atRight();
        StyledTextSet.decorate(headingTextWidget).setAlignment(SWT.CENTER).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM_BOLD).setText(vo.getStyledTextContent("heading"));

        passageTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(headingTextWidget).atRight();
        StyledTextSet.decorate(passageTextWidget).setNoCaret().setCursor(MT.CURSOR_ARROW).setEditable(false).setFont(MT.FONT_MEDIUM).setLineSpacing(5).setText(vo.getStyledTextContent("passage"));
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledTextStyles("passage"));
        passageTextWidget.addMouseListener(new PassageTextMouseAdapter());

        updateWidgetsForAnswers();

        rsc.setContent(c);
        rsc.addPaintListener((e) -> {
            int wh = rsc.getBounds().width;
            int hh = passageTextWidget.getBounds().y + passageTextWidget.getBounds().height + 100;
            rsc.setMinSize(c.computeSize(wh, hh));
            if (autoScrollRequired) {
                Rectangle bounds = passageTextWidget.getBounds();
                int quarter = rsc.getBounds().height / 4;
                int offsetY = bounds.y + passageTextWidget.getLocationAtOffset(vo.getPassageOffset()).y;
                if (offsetY > quarter) {
                    rsc.setOrigin(0, offsetY - quarter);
                }
                autoScrollRequired = false;
            }
        });
    }

    /*
     * ==================================================
     *
     * Update Widgets for Answers
     *
     * ==================================================
     */

    private void updateWidgetsForAnswers() {

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
                passageTextWidget.replaceTextRange(insertionPointA, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertionPointA, insertTextLength, null, null, SWT.BOLD));
                insertionPointB += change;
                insertionPointC += change;
                insertionPointD += change;
                break;
            case MT.CHOICE_B:
                /*
                 * ==================================================
                 *
                 * Insert new text at point B
                 *
                 * ==================================================
                 */
                passageTextWidget.replaceTextRange(insertionPointB, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertionPointB, insertTextLength, null, null, SWT.BOLD));
                insertionPointC += change;
                insertionPointD += change;
                break;
            case MT.CHOICE_C:
                /*
                 * ==================================================
                 *
                 * Insert new text at point C
                 *
                 * ==================================================
                 */
                passageTextWidget.replaceTextRange(insertionPointC, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertionPointC, insertTextLength, null, null, SWT.BOLD));
                insertionPointD += change;
                break;
            case MT.CHOICE_D:
                /*
                 * ==================================================
                 *
                 * Insert new text at point D
                 *
                 * ==================================================
                 */
                passageTextWidget.replaceTextRange(insertionPointD, 1, insertText);
                passageTextWidget.setStyleRange(new StyleRange(insertionPointD, insertTextLength, null, null, SWT.BOLD));
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

    private class NextOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToNextView(ReadingInsertTextQuestionView.this);
            page.resume();
        }
    }

    private class BackOvalButtonMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            release();
            PersistenceUtils.saveToPreviousView(ReadingInsertTextQuestionView.this);
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

    private class PassageTextMouseAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            int insertTextLength = insertText.length();
            int change = insertTextLength - 1;
            int offset = passageTextWidget.getCaretOffset();
            if (answer == MT.CHOICE_NONE) {
                if (offset == insertionPointA || offset == insertionPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, insertTextLength, null, null, SWT.BOLD));
                    insertionPointB += change;
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset == insertionPointB || offset == insertionPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, insertTextLength, null, null, SWT.BOLD));
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset == insertionPointC || offset == insertionPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, insertTextLength, null, null, SWT.BOLD));
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset == insertionPointD || offset == insertionPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointD, insertTextLength, null, null, SWT.BOLD));

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
                if (offset >= insertionPointA && offset <= insertionPointA + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, insertTextLength, MT.STRING_SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, 1, null, null, SWT.NORMAL));
                    insertionPointB -= change;
                    insertionPointC -= change;
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to NONE
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_NONE;

                } else if (offset == insertionPointB || offset == insertionPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, insertTextLength, null, null, SWT.BOLD));
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, insertTextLength, vo.getStyledTextContent("insertionPointA"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, 1, null, null, SWT.NORMAL));
                    insertionPointB -= change;
                    insertionPointC -= change;
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset == insertionPointC || offset == insertionPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, insertTextLength, null, null, SWT.BOLD));
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, insertTextLength, vo.getStyledTextContent("insertionPointA"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, 1, null, null, SWT.NORMAL));
                    insertionPointB -= change;
                    insertionPointC -= change;
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset == insertionPointD || offset == insertionPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, insertTextLength, vo.getStyledTextContent("insertionPointA"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, 1, null, null, SWT.NORMAL));
                    insertionPointB -= change;
                    insertionPointC -= change;
                    insertionPointD -= change;

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
                if (offset == insertionPointA || offset == insertionPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, insertTextLength, null, null, SWT.BOLD));
                    insertionPointB += change;
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, insertTextLength, vo.getStyledTextContent("insertionPointB"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, 1, null, null, SWT.NORMAL));
                    insertionPointC -= change;
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset >= insertionPointB && offset <= insertionPointB + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, insertTextLength, MT.STRING_SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, 1, null, null, SWT.NORMAL));
                    insertionPointC -= change;
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to NONE
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_NONE;

                } else if (offset == insertionPointC || offset == insertionPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, insertTextLength, null, null, SWT.BOLD));
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, insertTextLength, vo.getStyledTextContent("insertionPointB"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, 1, null, null, SWT.NORMAL));
                    insertionPointC -= change;
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset == insertionPointD || offset == insertionPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, insertTextLength, vo.getStyledTextContent("insertionPointB"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, 1, null, null, SWT.NORMAL));
                    insertionPointC -= change;
                    insertionPointD -= change;

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
                if (offset == insertionPointA || offset == insertionPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, insertTextLength, null, null, SWT.BOLD));
                    insertionPointB += change;
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, insertTextLength, vo.getStyledTextContent("insertionPointC"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, 1, null, null, SWT.NORMAL));
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset == insertionPointB || offset == insertionPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, insertTextLength, null, null, SWT.BOLD));
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, insertTextLength, vo.getStyledTextContent("insertionPointC"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, 1, null, null, SWT.NORMAL));
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset >= insertionPointC && offset <= insertionPointC + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, insertTextLength, MT.STRING_SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, 1, null, null, SWT.NORMAL));
                    insertionPointD -= change;

                    /*
                     * ==================================================
                     *
                     * Set choice to NONE
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_NONE;

                } else if (offset == insertionPointD || offset == insertionPointD + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointD, insertTextLength, null, null, SWT.BOLD));

                    /*
                     * ==================================================
                     *
                     * Remove old text from point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, insertTextLength, vo.getStyledTextContent("insertionPointC"));
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, 1, null, null, SWT.NORMAL));
                    insertionPointD -= change;

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
                if (offset == insertionPointA || offset == insertionPointA + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point A
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointA, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointA, insertTextLength, null, null, SWT.BOLD));
                    insertionPointB += change;
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, insertTextLength, vo.getStyledTextContent("insertionPointD"));

                    /*
                     * ==================================================
                     *
                     * Set choice to A
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_A;

                } else if (offset == insertionPointB || offset == insertionPointB + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point B
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointB, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointB, insertTextLength, null, null, SWT.BOLD));
                    insertionPointC += change;
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, insertTextLength, vo.getStyledTextContent("insertionPointD"));

                    /*
                     * ==================================================
                     *
                     * Set choice to B
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_B;

                } else if (offset == insertionPointC || offset == insertionPointC + 1) {

                    /*
                     * ==================================================
                     *
                     * Insert new text at point C
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointC, 1, insertText);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, insertTextLength, null, null, SWT.BOLD));
                    insertionPointD += change;

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, insertTextLength, vo.getStyledTextContent("insertionPointD"));

                    /*
                     * ==================================================
                     *
                     * Set choice to C
                     *
                     * ==================================================
                     */

                    answer = MT.CHOICE_C;

                } else if (offset >= insertionPointD && offset <= insertionPointD + insertTextLength) {

                    /*
                     * ==================================================
                     *
                     * Remove old text from point D
                     *
                     * ==================================================
                     */

                    passageTextWidget.replaceTextRange(insertionPointD, insertTextLength, MT.STRING_SQUARE);
                    passageTextWidget.setStyleRange(new StyleRange(insertionPointC, 1, null, null, SWT.NORMAL));

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
            PersistenceUtils.saveAnswer(ReadingInsertTextQuestionView.this, answerText);
        }
    }
}
