package com.mocktpo.modules.editor.views;

import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyleRangeUtils;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Composite;

public class ReadingMultipleChoiceQuestionEditorView extends SashTestEditorView {

    /* Widgets */

    private StyledText headingTextWidget;
    private StyledText passageTextWidget;
    private StyledText questionTextWidget;
    private StyledText choiceATextWidget;
    private StyledText choiceBTextWidget;
    private StyledText choiceCTextWidget;
    private StyledText choiceDTextWidget;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingMultipleChoiceQuestionEditorView(SashTestEditorLayer layer, int style, TestViewVo viewVo) {
        super(layer, style, viewVo);
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
        viewVo.setFirstPassage(false);
        viewVo.setTimed(true);
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
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("heading"));
        KeyBindingSet.bind(headingTextWidget).selectAll();
        headingTextWidget.addModifyListener(new HeadingTextModifyListener());
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        passageTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("passage"));
        KeyBindingSet.bind(passageTextWidget).selectAll();
        passageTextWidget.addModifyListener(new PassageTextModifyListener());
        passageTextWidget.addSelectionListener(new PassageTextSelectionListener());
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

        questionTextWidget = new StyledText(c, SWT.BORDER | SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeft().atTopTo(questionPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(questionTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("question"));
        KeyBindingSet.bind(questionTextWidget).selectAll();
        questionTextWidget.addModifyListener(new QuestionTextModifyListener());

        final CLabel choiceAPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceAPreLabel).atLeft().atTopTo(questionTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceAPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_a") + MT.STRING_TAB + MT.STRING_STAR);

        choiceATextWidget = new StyledText(c, SWT.BORDER | SWT.WRAP);
        FormDataSet.attach(choiceATextWidget).atLeft().atTopTo(choiceAPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceATextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("choiceA"));
        KeyBindingSet.bind(choiceATextWidget).selectAll();
        choiceATextWidget.addModifyListener(new ChoiceATextModifyListener());

        final CLabel choiceBPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceBPreLabel).atLeft().atTopTo(choiceATextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceBPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_b") + MT.STRING_TAB + MT.STRING_STAR);

        choiceBTextWidget = new StyledText(c, SWT.BORDER | SWT.WRAP);
        FormDataSet.attach(choiceBTextWidget).atLeft().atTopTo(choiceBPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceBTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("choiceB"));
        KeyBindingSet.bind(choiceBTextWidget).selectAll();
        choiceBTextWidget.addModifyListener(new ChoiceBTextModifyListener());

        final CLabel choiceCPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceCPreLabel).atLeft().atTopTo(choiceBTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceCPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_c") + MT.STRING_TAB + MT.STRING_STAR);

        choiceCTextWidget = new StyledText(c, SWT.BORDER | SWT.WRAP);
        FormDataSet.attach(choiceCTextWidget).atLeft().atTopTo(choiceCPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceCTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("choiceC"));
        KeyBindingSet.bind(choiceCTextWidget).selectAll();
        choiceCTextWidget.addModifyListener(new ChoiceCTextModifyListener());

        final CLabel choiceDPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceDPreLabel).atLeft().atTopTo(choiceCTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceDPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_d") + MT.STRING_TAB + MT.STRING_STAR);

        choiceDTextWidget = new StyledText(c, SWT.BORDER | SWT.WRAP);
        FormDataSet.attach(choiceDTextWidget).atLeft().atTopTo(choiceDPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceDTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("choiceD"));
        KeyBindingSet.bind(choiceDTextWidget).selectAll();
        choiceDTextWidget.addModifyListener(new ChoiceDTextModifyListener());

        rsc.setContent(c);
        rsc.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                int wh = rsc.getBounds().width;
                int hh = choiceDTextWidget.getBounds().y + choiceDTextWidget.getBounds().height + 100;
                rsc.setMinSize(c.computeSize(wh, hh));
            }
        });
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
            StyledTextVo headingTextVo = new StyledTextVo();
            headingTextVo.setText(headingTextWidget.getText());
            viewVo.setStyledTextVo("heading", headingTextVo);
            page.edit();
        }
    }

    private class PassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo passageTextVo = new StyledTextVo();
            passageTextVo.setText(passageTextWidget.getText());
            viewVo.setStyledTextVo("passage", passageTextVo);
            page.edit();
        }
    }

    private class PassageTextSelectionListener extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            logger.info("selection: {}, selection text: {}, selection background: {}, selection count: {}", passageTextWidget.getSelection(), passageTextWidget.getSelectionText(), passageTextWidget.getSelectionBackground(), passageTextWidget.getSelectionCount());
            StyledTextVo passageTextVo = viewVo.getStyledTextVo("passage");
            StyleRangeUtils.decorate(passageTextWidget, passageTextVo.getStyles());
        }
    }

    private class QuestionTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            layer.updateDescription(questionTextWidget.getText());

            StyledTextVo questionTextVo = new StyledTextVo();
            questionTextVo.setText(questionTextWidget.getText());
            viewVo.setStyledTextVo("question", questionTextVo);
            page.edit();
        }
    }

    private class ChoiceATextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceATextVo = new StyledTextVo();
            choiceATextVo.setText(choiceATextWidget.getText());
            viewVo.setStyledTextVo("choiceA", choiceATextVo);
            page.edit();
        }
    }

    private class ChoiceBTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceBTextVo = new StyledTextVo();
            choiceBTextVo.setText(choiceBTextWidget.getText());
            viewVo.setStyledTextVo("choiceB", choiceBTextVo);
            page.edit();
        }
    }

    private class ChoiceCTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceCTextVo = new StyledTextVo();
            choiceCTextVo.setText(choiceCTextWidget.getText());
            viewVo.setStyledTextVo("choiceC", choiceCTextVo);
            page.edit();
        }
    }

    private class ChoiceDTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceDTextVo = new StyledTextVo();
            choiceDTextVo.setText(choiceDTextWidget.getText());
            viewVo.setStyledTextVo("choiceD", choiceDTextVo);
            page.edit();
        }
    }
}
