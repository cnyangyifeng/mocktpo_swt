package com.mocktpo.modules.editor.views;

import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.StyleRangeVo;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import java.util.*;

public class ReadingMultipleChoiceQuestionEditorView extends SashTestEditorView {

    /* Widgets */

    private StyledText headingTextWidget;
    private ImageButton markParagraphsButton;
    private ImageButton highlightPassageButton;
    private StyledText passageTextWidget;
    private ImageButton highlightQuestionButton;
    private StyledText questionTextWidget;
    private StyledText choiceATextWidget;
    private StyledText choiceBTextWidget;
    private StyledText choiceCTextWidget;
    private StyledText choiceDTextWidget;
    private StyledText footnoteTextWidget;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingMultipleChoiceQuestionEditorView(SashTestEditorLayer layer, int style, TestViewVo vo) {
        super(layer, style, vo);
    }

    /*
     * ==================================================
     *
     * TestViewVo Updates
     *
     * ==================================================
     */

    @Override
    protected void updateTestViewVo() {
        vo.setFirstPassage(false);
        vo.setTimed(true);
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    @Override
    protected void updateLeft() {
        CompositeSet.decorate(left).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(left).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel headingPreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("heading") + MT.STRING_TAB + MT.STRING_STAR);

        headingTextWidget = new StyledText(left, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("heading"));
        KeyBindingSet.bind(headingTextWidget).selectAll();
        headingTextWidget.addModifyListener(new HeadingTextModifyListener());
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        markParagraphsButton = new ImageButton(left, SWT.NONE, MT.IMAGE_SYSTEM_MARK_PARAGRAPHS, MT.IMAGE_SYSTEM_MARK_PARAGRAPHS_HOVER, MT.IMAGE_SYSTEM_MARK_PARAGRAPHS_DISABLED);
        FormDataSet.attach(markParagraphsButton).atLeft().atTopTo(passagePreLabel);
        markParagraphsButton.addMouseListener(new MarkParagraphsButtonMouseListener());
        markParagraphsButton.setEnabled(false);

        highlightPassageButton = new ImageButton(left, SWT.NONE, MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT, MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_HOVER, MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_DISABLED);
        FormDataSet.attach(highlightPassageButton).atLeftTo(markParagraphsButton).atTopTo(passagePreLabel);
        highlightPassageButton.addMouseListener(new HighlightPassageButtonMouseListener());
        highlightPassageButton.setEnabled(false);

        passageTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(markParagraphsButton).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("passage"));
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledTextStyles("passage"));
        KeyBindingSet.bind(passageTextWidget).selectAll();
        passageTextWidget.addModifyListener(new PassageTextModifyListener());
        passageTextWidget.addFocusListener(new PassageTextFocusListener());
    }

    @Override
    protected void updateRight() {
        final ScrolledComposite rsc = new ScrolledComposite(right, SWT.V_SCROLL);
        FormDataSet.attach(rsc).atLeft().atTop().atRight().atBottom();
        rsc.setExpandHorizontal(true);
        rsc.setExpandVertical(true);

        final Composite c = new Composite(rsc, SWT.NONE);
        CompositeSet.decorate(c).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(c).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel questionPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(questionPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(questionPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("question") + MT.STRING_TAB + MT.STRING_STAR);

        highlightQuestionButton = new ImageButton(c, SWT.NONE, MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT, MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_HOVER, MT.IMAGE_SYSTEM_HIGHLIGHT_SELECTED_TEXT_DISABLED);
        FormDataSet.attach(highlightQuestionButton).atLeftTo(markParagraphsButton).atTopTo(questionPreLabel);
        highlightQuestionButton.addMouseListener(new HighlightQuestionButtonMouseListener());
        highlightQuestionButton.setEnabled(false);

        questionTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeft().atTopTo(highlightQuestionButton).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(questionTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("question"));
        StyleRangeUtils.decorate(questionTextWidget, vo.getStyledTextStyles("question"));
        KeyBindingSet.bind(questionTextWidget).selectAll();
        questionTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        questionTextWidget.addModifyListener(new QuestionTextModifyListener());
        questionTextWidget.addFocusListener(new QuestionTextFocusListener());

        final CLabel choiceAPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceAPreLabel).atLeft().atTopTo(questionTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceAPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_a") + MT.STRING_TAB + MT.STRING_STAR);

        choiceATextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceATextWidget).atLeft().atTopTo(choiceAPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceATextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceA"));
        KeyBindingSet.bind(choiceATextWidget).selectAll();
        choiceATextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceATextWidget.addModifyListener(new ChoiceATextModifyListener());

        final CLabel choiceBPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceBPreLabel).atLeft().atTopTo(choiceATextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceBPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_b") + MT.STRING_TAB + MT.STRING_STAR);

        choiceBTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceBTextWidget).atLeft().atTopTo(choiceBPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceBTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceB"));
        KeyBindingSet.bind(choiceBTextWidget).selectAll();
        choiceBTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceBTextWidget.addModifyListener(new ChoiceBTextModifyListener());

        final CLabel choiceCPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceCPreLabel).atLeft().atTopTo(choiceBTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceCPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_c") + MT.STRING_TAB + MT.STRING_STAR);

        choiceCTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceCTextWidget).atLeft().atTopTo(choiceCPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceCTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceC"));
        KeyBindingSet.bind(choiceCTextWidget).selectAll();
        choiceCTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceCTextWidget.addModifyListener(new ChoiceCTextModifyListener());

        final CLabel choiceDPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceDPreLabel).atLeft().atTopTo(choiceCTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceDPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_d") + MT.STRING_TAB + MT.STRING_STAR);

        choiceDTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceDTextWidget).atLeft().atTopTo(choiceDPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceDTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceD"));
        KeyBindingSet.bind(choiceDTextWidget).selectAll();
        choiceDTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceDTextWidget.addModifyListener(new ChoiceDTextModifyListener());

        final CLabel footnotePreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(footnotePreLabel).atLeft().atTopTo(choiceDTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(footnotePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("footnote"));

        footnoteTextWidget = new StyledText(c, SWT.SINGLE);
        FormDataSet.attach(footnoteTextWidget).atLeft().atTopTo(footnotePreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(footnoteTextWidget).setBackground(MT.COLOR_WHITE_SMOKE).setEditable(false).setEnabled(false).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setNoCaret().setText(vo.getStyledTextContent("footnote"));
        footnoteTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        rsc.setContent(c);
        rsc.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                int wh = rsc.getBounds().width;
                int hh = footnoteTextWidget.getBounds().y + footnoteTextWidget.getBounds().height + 100;
                rsc.setMinSize(c.computeSize(wh, hh));
            }
        });
    }

    private void updateHeading() {
        StyledTextVo headingTextVo = vo.getStyledTextVo("heading");
        if (headingTextVo == null) {
            headingTextVo = new StyledTextVo();
        }
        headingTextVo.setText(headingTextWidget.getText());
        vo.setStyledTextVo("heading", headingTextVo);
        page.edit();
    }

    private void updatePasasge() {
        StyledTextVo passageTextVo = vo.getStyledTextVo("passage");
        if (passageTextVo == null) {
            passageTextVo = new StyledTextVo();
        }
        passageTextVo.setText(passageTextWidget.getText());
        vo.setStyledTextVo("passage", passageTextVo);
        page.edit();
    }

    private void updateFootnote() {
        if (passageTextWidget == null) {
            return;
        }
        String text = passageTextWidget.getText();
        Map<Integer, Integer> markedParagraphIndices = new HashMap<Integer, Integer>();
        int number = 1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == MT.STRING_LINEFEED.charAt(0)) {
                number++;
            }
            if (text.charAt(i) == MT.STRING_ARROW.charAt(0)) {
                markedParagraphIndices.put(number, i);
            }
        }
        logger.info("marked: {}", markedParagraphIndices);

        StringJoiner joiner = new StringJoiner(MT.STRING_COMMA + MT.STRING_SPACE);
        for (Map.Entry<Integer, Integer> entry : markedParagraphIndices.entrySet()) {
            joiner.add(entry.getKey().toString());
        }
        String footnote;
        if (joiner.length() > 0) {
            footnote = "Paragraph " + joiner + " is marked with ➤.";
        } else {
            footnote = "";
        }
        footnoteTextWidget.setText(footnote);
        StyledTextVo footnoteTextVo = vo.getStyledTextVo("footnote");
        if (footnoteTextVo == null) {
            footnoteTextVo = new StyledTextVo();
        }
        footnoteTextVo.setText(footnote);
        vo.setStyledTextVo("footnote", footnoteTextVo);
        page.edit();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class HeadingTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            updateHeading();
        }
    }

    private class MarkParagraphsButtonMouseListener extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            String text = passageTextWidget.getText();
            int start = passageTextWidget.getCaretOffset() - 1;

            if (passageTextWidget.getCaretOffset() == 0) {
                boolean arrowExists = false;
                for (int k = 0; k < text.length(); k++) {
                    if (text.charAt(k) == MT.STRING_LINEFEED.charAt(0)) {
                        break;
                    }
                    if (text.charAt(k) == MT.STRING_ARROW.charAt(0)) {
                        arrowExists = true;
                        break;
                    }
                }
                if (!arrowExists) {
                    passageTextWidget.insert(MT.STRING_ARROW + MT.STRING_SPACE);
                }
            }

            for (int i = start; i >= 0; i--) {
                if (text.charAt(i) == MT.STRING_LINEFEED.charAt(0)) {
                    boolean arrowExists = false;
                    for (int j = i + 1; j < text.length(); j++) {
                        if (text.charAt(j) == MT.STRING_LINEFEED.charAt(0)) {
                            break;
                        }
                        if (text.charAt(j) == MT.STRING_ARROW.charAt(0)) {
                            arrowExists = true;
                            break;
                        }
                    }
                    if (!arrowExists) {
                        passageTextWidget.setCaretOffset(i + 1);
                        passageTextWidget.insert(MT.STRING_ARROW + MT.STRING_SPACE);
                    }
                    break;
                }
                if (i == 0) {
                    boolean arrowExists = false;
                    for (int j = i; j < text.length(); j++) {
                        if (text.charAt(j) == MT.STRING_LINEFEED.charAt(0)) {
                            break;
                        }
                        if (text.charAt(j) == MT.STRING_ARROW.charAt(0)) {
                            arrowExists = true;
                            break;
                        }
                    }
                    if (!arrowExists) {
                        passageTextWidget.setCaretOffset(i);
                        passageTextWidget.insert(MT.STRING_ARROW + MT.STRING_SPACE);
                    }
                    break;
                }
            }

            updateFootnote();
        }
    }

    private class HighlightPassageButtonMouseListener extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            List<StyleRangeVo> styles = new ArrayList<StyleRangeVo>();
            Point p = passageTextWidget.getSelectionRange();
            styles.add(new StyleRangeVo(p.x, p.y, 0, 0, MT.COLOR_HIGHLIGHTED, false, null));
            StyleRangeUtils.decorate(passageTextWidget, styles);
            passageTextWidget.setSelection(p.x + p.y);

            StyledTextVo passageTextVo = vo.getStyledTextVo("passage");
            if (passageTextVo == null) {
                passageTextVo = new StyledTextVo();
            }
            passageTextVo.setStyles(styles);
            vo.setStyledTextVo("passage", passageTextVo);
            page.edit();
        }
    }

    private class PassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            updatePasasge();
            updateFootnote();
        }
    }

    private class PassageTextFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            markParagraphsButton.setEnabled(true);
            highlightPassageButton.setEnabled(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            markParagraphsButton.setEnabled(false);
            highlightPassageButton.setEnabled(false);
        }
    }

    private class HighlightQuestionButtonMouseListener extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            List<StyleRangeVo> styles = new ArrayList<StyleRangeVo>();
            Point p = questionTextWidget.getSelectionRange();
            styles.add(new StyleRangeVo(p.x, p.y, 0, 0, MT.COLOR_HIGHLIGHTED, false, null));
            StyleRangeUtils.decorate(questionTextWidget, styles);
            questionTextWidget.setSelection(p.x + p.y);

            StyledTextVo questionTextVo = vo.getStyledTextVo("question");
            if (questionTextVo == null) {
                questionTextVo = new StyledTextVo();
            }
            questionTextVo.setStyles(styles);
            vo.setStyledTextVo("question", questionTextVo);
            page.edit();
        }
    }

    private class QuestionTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            layer.updateDescription(questionTextWidget.getText());

            StyledTextVo questionTextVo = vo.getStyledTextVo("question");
            if (questionTextVo == null) {
                questionTextVo = new StyledTextVo();
            }
            questionTextVo.setText(questionTextWidget.getText());
            vo.setStyledTextVo("question", questionTextVo);
            page.edit();
        }
    }

    private class QuestionTextFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            highlightQuestionButton.setEnabled(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            highlightQuestionButton.setEnabled(false);
        }
    }

    private class ChoiceATextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceATextVo = vo.getStyledTextVo("choiceA");
            if (choiceATextVo == null) {
                choiceATextVo = new StyledTextVo();
            }
            choiceATextVo.setText(choiceATextWidget.getText());
            vo.setStyledTextVo("choiceA", choiceATextVo);
            page.edit();
        }
    }

    private class ChoiceBTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceBTextVo = vo.getStyledTextVo("choiceB");
            if (choiceBTextVo == null) {
                choiceBTextVo = new StyledTextVo();
            }
            choiceBTextVo.setText(choiceBTextWidget.getText());
            vo.setStyledTextVo("choiceB", choiceBTextVo);
            page.edit();
        }
    }

    private class ChoiceCTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceCTextVo = vo.getStyledTextVo("choiceC");
            if (choiceCTextVo == null) {
                choiceCTextVo = new StyledTextVo();
            }
            choiceCTextVo.setText(choiceCTextWidget.getText());
            vo.setStyledTextVo("choiceC", choiceCTextVo);
            page.edit();
        }
    }

    private class ChoiceDTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceDTextVo = vo.getStyledTextVo("choiceD");
            if (choiceDTextVo == null) {
                choiceDTextVo = new StyledTextVo();
            }
            choiceDTextVo.setText(choiceDTextWidget.getText());
            vo.setStyledTextVo("choiceD", choiceDTextVo);
            page.edit();
        }
    }
}
