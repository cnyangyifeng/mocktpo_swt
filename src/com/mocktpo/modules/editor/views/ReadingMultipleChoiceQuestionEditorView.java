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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

        final Composite rightBody = new Composite(rsc, SWT.NONE);
        CompositeSet.decorate(rightBody).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(rightBody).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel questionPreLabel = new CLabel(rightBody, SWT.NONE);
        FormDataSet.attach(questionPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(questionPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("question") + MT.STRING_TAB + MT.STRING_STAR);

        questionTextWidget = new StyledText(rightBody, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(questionTextWidget).atLeft().atTopTo(questionPreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT * 3);
        StyledTextSet.decorate(questionTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("question"));
        KeyBindingSet.bind(questionTextWidget).selectAll();
        questionTextWidget.addModifyListener(new QuestionTextModifyListener());

        final CLabel choiceAPreLabel = new CLabel(rightBody, SWT.NONE);
        FormDataSet.attach(choiceAPreLabel).atLeft().atTopTo(questionTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceAPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_a") + MT.STRING_TAB + MT.STRING_STAR);

        choiceATextWidget = new StyledText(rightBody, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(choiceATextWidget).atLeft().atTopTo(choiceAPreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT * 3);
        StyledTextSet.decorate(choiceATextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledTextContent("passage"));
        KeyBindingSet.bind(choiceATextWidget).selectAll();
        choiceATextWidget.addModifyListener(new ChoiceATextModifyListener());
        choiceATextWidget.addSelectionListener(new PassageTextSelectionListener());

        rsc.setContent(rightBody);
        rsc.setMinSize(rightBody.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
            StyledTextVo passageTextVo = new StyledTextVo();
            passageTextVo.setText(choiceATextWidget.getText());
            viewVo.setStyledTextVo("choiceA", passageTextVo);
            page.edit();
        }
    }
}
