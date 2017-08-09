package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.StyledTextSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.h2.util.StringUtils;

public class GeneralEditorLayer extends ResponsiveTestEditorLayer {

    /* Constants */

    private static final int PRE_LABEL_WIDTH = 120;
    private static final int STARS_TEXT_WIDGET_WIDTH = 40;
    private static final int AUTHOR_TEXT_WIDGET_WIDTH = 240;

    /* Widgets */

    private StyledText titleTextWidget;
    private StyledText starsTextWidget;
    private StyledText authorTextWidget;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public GeneralEditorLayer(TestEditorPage page, int style) {
        super(page, style);
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    @Override
    protected void updateHeader() {
        generalButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_GENERAL_CHECKED, MT.IMAGE_SYSTEM_STEP_GENERAL_CHECKED);
        if (page.isFirstRun()) {
            readingButton.setEnabled(false);
            listeningButton.setEnabled(false);
            speakingButton.setEnabled(false);
            writingButton.setEnabled(false);
            previewButton.setEnabled(false);
        }
    }

    @Override
    protected void updateFooter() {
        if (page.isFirstRun()) {
            exportAsZipButton.setEnabled(false);
        }
    }

    @Override
    protected void updateBody() {
        FormLayoutSet.layout(viewPort).marginWidth(0).marginHeight(50).spacing(10);

        titleTextWidget = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(titleTextWidget).atLeft(PRE_LABEL_WIDTH).atTop().atRight().withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(titleTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(page.getTestVo().getTitle());
        KeyBindingSet.bind(titleTextWidget).traverse().selectAll();
        titleTextWidget.addModifyListener(new TitleTextWidgetModifyListener());
        titleTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        titleTextWidget.setSelection(titleTextWidget.getText().length());

        final CLabel titlePreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(titlePreLabel).atLeft().atTopTo(titleTextWidget, 0, SWT.TOP).atRightTo(titleTextWidget, 0, SWT.LEFT).atBottomTo(titleTextWidget, 0, SWT.BOTTOM);
        CLabelSet.decorate(titlePreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("title"));

        starsTextWidget = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(starsTextWidget).atLeft(PRE_LABEL_WIDTH).atTopTo(titleTextWidget).withWidth(STARS_TEXT_WIDGET_WIDTH).withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(starsTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(Integer.toString(page.getTestVo().getStars()));
        KeyBindingSet.bind(starsTextWidget).traverse().selectAll();
        starsTextWidget.addModifyListener(new StarsTextWidgetModifyListener());
        starsTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel starsPreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(starsPreLabel).atLeft().atTopTo(starsTextWidget, 0, SWT.TOP).atRightTo(starsTextWidget, 0, SWT.LEFT).atBottomTo(starsTextWidget, 0, SWT.BOTTOM);
        CLabelSet.decorate(starsPreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("stars"));

        authorTextWidget = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(authorTextWidget).atLeft(PRE_LABEL_WIDTH).atTopTo(starsTextWidget).withWidth(AUTHOR_TEXT_WIDGET_WIDTH).withHeight(LC.SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(authorTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(page.getTestVo().getAuthor());
        KeyBindingSet.bind(authorTextWidget).traverse().selectAll();
        authorTextWidget.addModifyListener(new AuthorTextWidgetModifyListener());
        authorTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel authorPreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(authorPreLabel).atLeft().atTopTo(authorTextWidget, 0, SWT.TOP).atRightTo(authorTextWidget, 0, SWT.LEFT).atBottomTo(authorTextWidget, 0, SWT.BOTTOM);
        CLabelSet.decorate(authorPreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY40).setText(msgs.getString("author"));
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class TitleTextWidgetModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            page.getTestVo().setTitle(titleTextWidget.getText());
            page.enterUnsavedMode();
        }
    }

    private class StarsTextWidgetModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            String st = starsTextWidget.getText();
            if (StringUtils.isNumber(st)) {
                try {
                    Integer stars = Integer.parseInt(st);
                    if (stars < 0) {
                        stars = 0;
                    } else if (stars > 5) {
                        stars = 5;
                    }
                    page.getTestVo().setStars(stars);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
            page.enterUnsavedMode();
        }
    }

    private class AuthorTextWidgetModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            page.getTestVo().setAuthor(authorTextWidget.getText());
            page.enterUnsavedMode();
        }
    }
}
