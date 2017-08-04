package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.VT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.StyledTextSet;
import com.mocktpo.vo.StyledTextVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

public class ReadingPassageEditorView extends SashTestEditorView {

    /* Widgets */

    private StyledText headingTextWidget;
    private StyledText passageTextWidget;
    private StyledText localizedHeadingTextWidget;
    private StyledText localizedPassageTextWidget;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingPassageEditorView(SashTestPaperView paperView, int style, int viewId) {
        super(paperView, style, viewId);
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
        viewVo.setSectionType(1);
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
        CompositeSet.decorate(left).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(left).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel headingPreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("heading") + MT.STRING_TAB + MT.STRING_STAR);

        headingTextWidget = new StyledText(left, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("heading"));
        KeyBindingSet.bind(headingTextWidget).selectAll();
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        headingTextWidget.addModifyListener(new HeadingTextModifyListener());
        headingTextWidget.setSelection(headingTextWidget.getText().length());

        final CLabel passagePreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        passageTextWidget = new StyledText(left, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("passage"));
        KeyBindingSet.bind(passageTextWidget).selectAll();
        passageTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        passageTextWidget.addModifyListener(new PassageTextModifyListener());
    }

    @Override
    protected void updateRight() {
        CompositeSet.decorate(right).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(right).marginWidth(20).marginHeight(20).spacing(10);

        final CLabel headingPreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("heading") + MT.STRING_SPACE + MT.STRING_OPEN_BRACKET + MT.LANGUAGE_SIMPLIFIED_CHINESE + MT.STRING_CLOSED_BRACKET);

        localizedHeadingTextWidget = new StyledText(right, SWT.SINGLE);
        FormDataSet.attach(localizedHeadingTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(localizedHeadingTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("localized_heading"));
        KeyBindingSet.bind(localizedHeadingTextWidget).selectAll();
        localizedHeadingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        localizedHeadingTextWidget.addModifyListener(new LocalizedHeadingTextModifyListener());

        final CLabel passagePreLabel = new CLabel(right, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(localizedHeadingTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("passage") + MT.STRING_SPACE + MT.STRING_OPEN_BRACKET + MT.LANGUAGE_SIMPLIFIED_CHINESE + MT.STRING_CLOSED_BRACKET);

        localizedPassageTextWidget = new StyledText(right, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.WRAP);
        FormDataSet.attach(localizedPassageTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(localizedPassageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(viewVo.getStyledText("passage"));
        KeyBindingSet.bind(localizedPassageTextWidget).selectAll();
        localizedPassageTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        localizedPassageTextWidget.addModifyListener(new LocalizedPassageTextModifyListener());
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

    private class LocalizedHeadingTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo localizedHeadingTextVo = new StyledTextVo();
            localizedHeadingTextVo.setText(localizedHeadingTextWidget.getText());
            viewVo.setStyledTextVo("localizedHeading", localizedHeadingTextVo);
            page.enterUnsavedMode();
        }
    }

    private class LocalizedPassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo localizedPassageTextVo = new StyledTextVo();
            localizedPassageTextVo.setText(localizedPassageTextWidget.getText());
            viewVo.setStyledTextVo("localizedPassage", localizedPassageTextVo);
            page.enterUnsavedMode();
        }
    }
}
