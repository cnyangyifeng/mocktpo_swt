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
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

public class ListeningMaterialViewEditorView extends SashTestViewEditorView {

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

    public ListeningMaterialViewEditorView(SashTestEditorLayer layer, int style, TestViewVo vo) {
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
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(msgs.getString("heading") + MT.STRING_TAB + MT.STRING_STAR);

        headingTextWidget = new StyledText(left, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GREY_DARKEN_4).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("heading"));
        KeyBindingSet.bind(headingTextWidget).selectAll();
        headingTextWidget.addModifyListener(new HeadingTextModifyListener());
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        passageTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GREY_DARKEN_4).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("passage"));
        KeyBindingSet.bind(passageTextWidget).selectAll();
        passageTextWidget.addModifyListener(new PassageTextModifyListener());
    }

    @Override
    protected void updateRight() {
        CompositeSet.decorate(right).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(right).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel headingPreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(msgs.getString("heading") + MT.STRING_SPACE + MT.STRING_OPEN_BRACKET + msgs.getString("translation") + MT.STRING_CLOSED_BRACKET);

        headingTranslationTextWidget = new StyledText(right, SWT.SINGLE);
        FormDataSet.attach(headingTranslationTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(headingTranslationTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GREY_DARKEN_4).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("headingTranslation"));
        KeyBindingSet.bind(headingTranslationTextWidget).selectAll();
        headingTranslationTextWidget.addModifyListener(new HeadingTranslationTextModifyListener());
        headingTranslationTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTranslationTextWidget, 10).atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GREY_DARKEN_4).setText(msgs.getString("passage") + MT.STRING_SPACE + MT.STRING_OPEN_BRACKET + msgs.getString("translation") + MT.STRING_CLOSED_BRACKET);

        passageTranslationTextWidget = new StyledText(right, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTranslationTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTranslationTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GREY_DARKEN_4).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("passageTranslation"));
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
            layer.updateDescription(headingTextWidget.getText());

            StyledTextVo headingTextVo = vo.getStyledTextVo("heading");
            if (headingTextVo == null) {
                headingTextVo = new StyledTextVo();
            }
            headingTextVo.setText(headingTextWidget.getText());
            vo.setStyledTextVo("heading", headingTextVo);
            page.edit();
        }
    }

    private class PassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {

            StyledTextVo passageTextVo = vo.getStyledTextVo("passage");
            if (passageTextVo == null) {
                passageTextVo = new StyledTextVo();
            }
            passageTextVo.setText(passageTextWidget.getText());
            vo.setStyledTextVo("passage", passageTextVo);
            page.edit();
        }
    }

    private class HeadingTranslationTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo headingTranslatioinVo = vo.getStyledTextVo("headingTranslatioin");
            if (headingTranslatioinVo == null) {
                headingTranslatioinVo = new StyledTextVo();
            }
            headingTranslatioinVo.setText(headingTranslationTextWidget.getText());
            vo.setStyledTextVo("headingTranslation", headingTranslatioinVo);
            page.edit();
        }
    }

    private class PassageTranslationTextWidgetTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo passageTranslationVo = vo.getStyledTextVo("passageTranslation");
            if (passageTranslationVo == null) {
                passageTranslationVo = new StyledTextVo();
            }
            passageTranslationVo.setText(passageTranslationTextWidget.getText());
            vo.setStyledTextVo("passageTranslation", passageTranslationVo);
            page.edit();
        }
    }
}
