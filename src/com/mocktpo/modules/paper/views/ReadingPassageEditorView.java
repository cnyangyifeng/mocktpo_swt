package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyledTextSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;

public class ReadingPassageEditorView extends SashTestEditorView {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingPassageEditorView(SashTestPaperView paperView, int style) {
        super(paperView, style);
    }

    @Override
    protected void updateLeft() {
        CompositeSet.decorate(left).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(left).marginWidth(0).marginHeight(0).spacing(0);

        paperView.getTestPaperPage().getTestPaperVo();
    }

    @Override
    protected void updateRight() {
        CompositeSet.decorate(right).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(right).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel headingPreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight();
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("heading") + MT.STRING_TAB + MT.STRING_STAR);

        final StyledText headingTextWidget = new StyledText(right, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTopTo(headingPreLabel).atRight();
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10);
        KeyBindingSet.bind(headingTextWidget).traverse().selectAll();
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight();
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        final StyledText passageTextWidget = new StyledText(right, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10);
        KeyBindingSet.bind(passageTextWidget).traverse().selectAll();
        passageTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
    }
}
