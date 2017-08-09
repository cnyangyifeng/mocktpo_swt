package com.mocktpo.modules.editor.views;

import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.ST;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

public class ReadingPassageEditorView extends SashTestEditorView {

    /* Widgets */

    private StyledText headingTextWidget;
    private StyledText passageTextWidget;
    private StyledText headingTranslationTextWidget;
    private StyledText passageTranslationTextWidget;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingPassageEditorView(SashTestEditorLayer editorView, int style, TestViewVo viewVo) {
        super(editorView, style, viewVo);
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
        viewVo.setViewType(VT.VIEW_TYPE_READING_PASSAGE);
        viewVo.setSectionType(ST.SECTION_TYPE_READING);
        viewVo.setSectionTypeName(msgs.getString("reading"));
        viewVo.setFirstPassage(true);
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
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("heading"));
        KeyBindingSet.bind(headingTextWidget).selectAll();
        headingTextWidget.addModifyListener(new HeadingTextModifyListener());
        headingTextWidget.setSelection(headingTextWidget.getText().length());
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        passageTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("passage"));
        KeyBindingSet.bind(passageTextWidget).selectAll();
        passageTextWidget.addModifyListener(new PassageTextModifyListener());
    }

    @Override
    protected void updateRight() {
        CompositeSet.decorate(right).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(right).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel headingPreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("heading") + MT.STRING_SPACE + MT.STRING_OPEN_BRACKET + MT.STRING_SPACE + msgs.getString("translation") + MT.STRING_SPACE + MT.STRING_CLOSED_BRACKET);

        headingTranslationTextWidget = new StyledText(right, SWT.SINGLE);
        FormDataSet.attach(headingTranslationTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(headingTranslationTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("localizedHeading"));
        KeyBindingSet.bind(headingTranslationTextWidget).selectAll();
        headingTranslationTextWidget.addModifyListener(new HeadingTranslationTextModifyListener());
        headingTranslationTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTranslationTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("passage") + MT.STRING_SPACE + MT.STRING_OPEN_BRACKET + MT.STRING_SPACE + msgs.getString("translation") + MT.STRING_SPACE + MT.STRING_CLOSED_BRACKET);

        passageTranslationTextWidget = new StyledText(right, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTranslationTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTranslationTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("localizedPassage"));
        KeyBindingSet.bind(passageTranslationTextWidget).selectAll();
        passageTranslationTextWidget.addModifyListener(new PassageTranslationTextWidgetTextModifyListener());
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
            page.enterUnsavedMode();
        }
    }

    private class PassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo passageTextVo = new StyledTextVo();
            passageTextVo.setText(passageTextWidget.getText());
            viewVo.setStyledTextVo("passage", passageTextVo);
            page.enterUnsavedMode();
        }
    }

    private class HeadingTranslationTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo localizedHeadingTextVo = new StyledTextVo();
            localizedHeadingTextVo.setText(headingTranslationTextWidget.getText());
            viewVo.setStyledTextVo("headingTranslation", localizedHeadingTextVo);
            page.enterUnsavedMode();
        }
    }

    private class PassageTranslationTextWidgetTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo localizedPassageTextVo = new StyledTextVo();
            localizedPassageTextVo.setText(passageTranslationTextWidget.getText());
            viewVo.setStyledTextVo("passageTranslation", localizedPassageTextVo);
            page.enterUnsavedMode();
        }
    }
}
