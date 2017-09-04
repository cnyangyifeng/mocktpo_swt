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

import java.util.ArrayList;
import java.util.List;

public class ReadingProseSummaryQuestionEditorView extends SashTestEditorView {

    /* Widgets */

    private StyledText headingTextWidget;
    private StyledText passageTextWidget;
    private StyledText questionTextWidget;
    private StyledText choiceATextWidget;
    private StyledText choiceBTextWidget;
    private StyledText choiceCTextWidget;
    private StyledText choiceDTextWidget;
    private StyledText choiceETextWidget;
    private StyledText choiceFTextWidget;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingProseSummaryQuestionEditorView(SashTestEditorLayer layer, int style, TestViewVo vo) {
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
        FormDataSet.attach(headingPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(headingPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("heading") + MT.STRING_TAB + MT.STRING_STAR);

        headingTextWidget = new StyledText(left, SWT.SINGLE);
        FormDataSet.attach(headingTextWidget).atLeft().atTopTo(headingPreLabel).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(headingTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("heading"));
        KeyBindingSet.bind(headingTextWidget).selectAll();
        headingTextWidget.addModifyListener(new HeadingTextModifyListener());
        headingTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel passagePreLabel = new CLabel(left, SWT.NONE);
        FormDataSet.attach(passagePreLabel).atLeft().atTopTo(headingTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(passagePreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("passage") + MT.STRING_TAB + MT.STRING_STAR);

        passageTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(passagePreLabel).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("passage"));
        StyleRangeUtils.decorate(passageTextWidget, vo.getStyledTextStyles("passage"));
        KeyBindingSet.bind(passageTextWidget).selectAll();
        passageTextWidget.addModifyListener(new PassageTextModifyListener());
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

        questionTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(questionTextWidget).atLeft().atTopTo(questionPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(questionTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("question"));
        StyleRangeUtils.decorate(questionTextWidget, vo.getStyledTextStyles("question"));
        KeyBindingSet.bind(questionTextWidget).selectAll();
        questionTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        questionTextWidget.addModifyListener(new QuestionTextModifyListener());

        final CLabel choiceAPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceAPreLabel).atLeft().atTopTo(questionTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceAPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_a") + MT.STRING_TAB + MT.STRING_STAR);

        choiceATextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceATextWidget).atLeft().atTopTo(choiceAPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceATextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceA"));
        KeyBindingSet.bind(choiceATextWidget).selectAll();
        choiceATextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceATextWidget.addModifyListener(new ChoiceATextModifyListener());

        final CLabel choiceBPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceBPreLabel).atLeft().atTopTo(choiceATextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceBPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_b") + MT.STRING_TAB + MT.STRING_STAR);

        choiceBTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceBTextWidget).atLeft().atTopTo(choiceBPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceBTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceB"));
        KeyBindingSet.bind(choiceBTextWidget).selectAll();
        choiceBTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceBTextWidget.addModifyListener(new ChoiceBTextModifyListener());

        final CLabel choiceCPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceCPreLabel).atLeft().atTopTo(choiceBTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceCPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_c") + MT.STRING_TAB + MT.STRING_STAR);

        choiceCTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceCTextWidget).atLeft().atTopTo(choiceCPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceCTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceC"));
        KeyBindingSet.bind(choiceCTextWidget).selectAll();
        choiceCTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceCTextWidget.addModifyListener(new ChoiceCTextModifyListener());

        final CLabel choiceDPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceDPreLabel).atLeft().atTopTo(choiceCTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceDPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_d") + MT.STRING_TAB + MT.STRING_STAR);

        choiceDTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceDTextWidget).atLeft().atTopTo(choiceDPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceDTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceD"));
        KeyBindingSet.bind(choiceDTextWidget).selectAll();
        choiceDTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceDTextWidget.addModifyListener(new ChoiceDTextModifyListener());

        final CLabel choiceEPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceEPreLabel).atLeft().atTopTo(choiceDTextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceEPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_e") + MT.STRING_TAB + MT.STRING_STAR);

        choiceETextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceETextWidget).atLeft().atTopTo(choiceEPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceETextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceE"));
        KeyBindingSet.bind(choiceETextWidget).selectAll();
        choiceETextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceETextWidget.addModifyListener(new ChoiceETextModifyListener());

        final CLabel choiceFPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(choiceFPreLabel).atLeft().atTopTo(choiceETextWidget, 10).atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(choiceFPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("choice_f") + MT.STRING_TAB + MT.STRING_STAR);

        choiceFTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(choiceFTextWidget).atLeft().atTopTo(choiceFPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(choiceFTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("choiceF"));
        KeyBindingSet.bind(choiceFTextWidget).selectAll();
        choiceFTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        choiceFTextWidget.addModifyListener(new ChoiceFTextModifyListener());

        rsc.setContent(c);
        rsc.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                int wh = rsc.getBounds().width;
                int hh = choiceFTextWidget.getBounds().y + choiceFTextWidget.getBounds().height + 100;
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

    private class PassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            updatePasasge();
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

    private class ChoiceETextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceETextVo = vo.getStyledTextVo("choiceE");
            if (choiceETextVo == null) {
                choiceETextVo = new StyledTextVo();
            }
            choiceETextVo.setText(choiceETextWidget.getText());
            vo.setStyledTextVo("choiceE", choiceETextVo);
            page.edit();
        }
    }

    private class ChoiceFTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            StyledTextVo choiceFTextVo = vo.getStyledTextVo("choiceF");
            if (choiceFTextVo == null) {
                choiceFTextVo = new StyledTextVo();
            }
            choiceFTextVo.setText(choiceFTextWidget.getText());
            vo.setStyledTextVo("choiceF", choiceFTextVo);
            page.edit();
        }
    }
}
