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
import com.mocktpo.vo.StyledTextVo;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.Composite;

public class ReadingInsertTextQuestionEditorView extends SashTestEditorView {

    /* Widgets */

    private StyledText headingTextWidget;
    private ImageButton addAnInsertionPointButton;
    private StyledText passageTextWidget;
    private StyledText insertTextTextWidget;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReadingInsertTextQuestionEditorView(SashTestEditorLayer layer, int style, TestViewVo vo) {
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

        addAnInsertionPointButton = new ImageButton(left, SWT.NONE, MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT, MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_HOVER, MT.IMAGE_SYSTEM_ADD_AN_INSERTION_POINT_DISABLED);
        FormDataSet.attach(addAnInsertionPointButton).atLeft().atTopTo(passagePreLabel);
        addAnInsertionPointButton.addMouseListener(new AddAnInsertionPointButtonMouseListener());
        addAnInsertionPointButton.setEnabled(false);

        passageTextWidget = new StyledText(left, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
        FormDataSet.attach(passageTextWidget).atLeft().atTopTo(addAnInsertionPointButton).atRight().atBottom();
        StyledTextSet.decorate(passageTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("passage"));
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

        final CLabel insertTextPreLabel = new CLabel(c, SWT.NONE);
        FormDataSet.attach(insertTextPreLabel).atLeft().atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        CLabelSet.decorate(insertTextPreLabel).setFont(MT.FONT_SMALL).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("insert_text") + MT.STRING_TAB + MT.STRING_STAR);

        insertTextTextWidget = new StyledText(c, SWT.WRAP);
        FormDataSet.attach(insertTextTextWidget).atLeft().atTopTo(insertTextPreLabel).atRight().withHeight(LC.TRIPLE_LINES_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(insertTextTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(vo.getStyledTextContent("insertText"));
        StyleRangeUtils.decorate(insertTextTextWidget, vo.getStyledTextStyles("insertText"));
        KeyBindingSet.bind(insertTextTextWidget).selectAll();
        insertTextTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        insertTextTextWidget.addModifyListener(new InsertTextTextModifyListener());

        rsc.setContent(c);
        rsc.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                int wh = rsc.getBounds().width;
                int hh = insertTextTextWidget.getBounds().y + insertTextTextWidget.getBounds().height + 100;
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

    private class AddAnInsertionPointButtonMouseListener extends MouseAdapter {

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
        }
    }

    private class PassageTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            updatePasasge();
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
