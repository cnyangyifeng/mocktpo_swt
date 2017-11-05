package com.mocktpo.modules.editor.views;

import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.modules.system.widgets.ImageButton;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.*;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ReadingInsertTextQuestionViewEditorView extends SashTestViewEditorView {

    /* Widgets */

    private StyledText headingTextWidget;
    private ImageButton addAnInsertionPointButton;
    private StyledText passageTextWidget;
    private StyledText insertTextTextWidget;

    /* Properties */

    private int insertionPointCount;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingInsertTextQuestionViewEditorView(SashTestEditorLayer layer, int style, TestViewVo vo) {
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
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(msgs.getString("heading") + MT.STRING_TAB + MT.STRING_STAR);

        headingTextWidget = new StyledText(left, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLUE_GREY_DARKEN_4).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("heading"));
        KeyBindingSet.bind(headingTextWidget).selectAll();
        headingTextWidget.addModifyListener(new HeadingTextModifyListener());
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        addAnInsertionPointButton = new ImageButton(left, SWT.NONE, MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT, MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_HOVER, MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_DISABLED);
        FormDataSet.attach(addAnInsertionPointButton).atLeft().atTopTo(passagePreLabel);
        addAnInsertionPointButton.addMouseListener(new AddAnInsertionPointButtonMouseListener());
        addAnInsertionPointButton.setEnabled(false);

        passageTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(addAnInsertionPointButton).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLUE_GREY_DARKEN_4).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("passage"));
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledTextStyles("passage"));
        KeyBindingSet.bind(passageTextWidget).selectAll();
        passageTextWidget.addModifyListener(new PassageTextModifyListener());
        passageTextWidget.addFocusListener(new PassageTextFocusListener());
        insertionPointCount = StringUtils.countMatches(vo.getStyledTextContent("passage"), MT.STRING_FULL_BLOCK);
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
        FormDataSet.attach(questionPreLabel).atLeft().atTop().atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(questionPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY_DARKEN_2).setText(msgs.getString("question") + MT.STRING_TAB + MT.STRING_STAR);

        final Label questionLabel = new Label(c, SWT.WRAP);
        FormDataSet.attach(questionLabel).atLeft().atTopTo(questionPreLabel).atRight();
        LabelSet.decorate(questionLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLUE_GREY_DARKEN_4).setText(MT.STRING_READING_INSERT_TEXT_QUESTION_VIEW_QUESTION);

        insertTextTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(insertTextTextWidget).atLeft().atTopTo(questionLabel, 10).atRight().withHeight(LC.SYSTEM_TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(insertTextTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLUE_GREY_DARKEN_4).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("insertText"));
        StyleRangeUtils.decorate(insertTextTextWidget, vo.getStyledTextStyles("insertText"));
        KeyBindingSet.bind(insertTextTextWidget).selectAll();
        insertTextTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        insertTextTextWidget.addModifyListener(new InsertTextTextModifyListener());

        final Label footnoteLabel = new Label(c, SWT.WRAP);
        FormDataSet.attach(footnoteLabel).atLeft().atTopTo(insertTextTextWidget, 10).atRight();
        LabelSet.decorate(footnoteLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLUE_GREY_DARKEN_4).setText(MT.STRING_READING_INSERT_TEXT_QUESTION_VIEW_FOOTNOTE);

        rsc.setContent(c);
        rsc.addPaintListener((e) -> {
            int wh = rsc.getBounds().width;
            int hh = footnoteLabel.getBounds().y + footnoteLabel.getBounds().height + 100;
            rsc.setMinSize(c.computeSize(wh, hh));
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

    private class AddAnInsertionPointButtonMouseListener extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            if (insertionPointCount >= 4) {
                return;
            }
            insertionPointCount++;
            passageTextWidget.insert(MT.STRING_FULL_BLOCK);
            int caretOffset = passageTextWidget.getCaretOffset();
            passageTextWidget.setCaretOffset(caretOffset + 1);
        }
    }

    private class PassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            updatePasasge();
            insertionPointCount = StringUtils.countMatches(vo.getStyledTextContent("passage"), MT.STRING_FULL_BLOCK);
        }
    }

    private class PassageTextFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            addAnInsertionPointButton.setEnabled(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            addAnInsertionPointButton.setEnabled(false);
        }
    }

    private class InsertTextTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            layer.updateDescription(insertTextTextWidget.getText());
            StyledTextVo insertTextTextVo = vo.getStyledTextVo("insertText");
            if (insertTextTextVo == null) {
                insertTextTextVo = new StyledTextVo();
            }
            insertTextTextVo.setText(insertTextTextWidget.getText());
            vo.setStyledTextVo("insertText", insertTextTextVo);
            page.edit();
        }
    }
}
