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
    private static final int CREATOR_TEXT_WIDGET_WIDTH = 240;

    /* Widgets */

    private StyledText titleTextWidget;
    private StyledText starsTextWidget;
    private StyledText creatorTextWidget;

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
        FormDataSet.attach(titleTextWidget).atLeft(PRE_LABEL_WIDTH).atTop().atRight().withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(titleTextWidget).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(page.getTestEditorVo().getTitle());
        KeyBindingSet.bind(titleTextWidget).traverse().selectAll();
        titleTextWidget.addModifyListener(new TitleTextWidgetModifyListener());
        titleTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        titleTextWidget.setSelection(titleTextWidget.getText().length());

        final CLabel titlePreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(titlePreLabel).atLeft().atTopTo(titleTextWidget, 0, SWT.TOP).atRightTo(titleTextWidget, 0, SWT.LEFT).atBottomTo(titleTextWidget, 0, SWT.BOTTOM);
        CLabelSet.decorate(titlePreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setText(msgs.getString("title"));

        starsTextWidget = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(starsTextWidget).atLeft(PRE_LABEL_WIDTH).atTopTo(titleTextWidget).withWidth(STARS_TEXT_WIDGET_WIDTH).withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(starsTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(Integer.toString(page.getTestEditorVo().getStars()));
        KeyBindingSet.bind(starsTextWidget).traverse().selectAll();
        starsTextWidget.addModifyListener(new StarsTextWidgetModifyListener());
        starsTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel starsPreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(starsPreLabel).atLeft().atTopTo(starsTextWidget, 0, SWT.TOP).atRightTo(starsTextWidget, 0, SWT.LEFT).atBottomTo(starsTextWidget, 0, SWT.BOTTOM);
        CLabelSet.decorate(starsPreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setText(msgs.getString("stars"));

        creatorTextWidget = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(creatorTextWidget).atLeft(PRE_LABEL_WIDTH).atTopTo(starsTextWidget).withWidth(CREATOR_TEXT_WIDGET_WIDTH).withHeight(LC.SYSTEM_SINGLE_LINE_TEXT_WIDGET_HEIGHT);
        StyledTextSet.decorate(creatorTextWidget).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_BLACK).setMargins(10, 10, 10, 10).setText(page.getTestEditorVo().getCreator());
        KeyBindingSet.bind(creatorTextWidget).traverse().selectAll();
        creatorTextWidget.addModifyListener(new CreatorTextWidgetModifyListener());
        creatorTextWidget.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel creatorPreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(creatorPreLabel).atLeft().atTopTo(creatorTextWidget, 0, SWT.TOP).atRightTo(creatorTextWidget, 0, SWT.LEFT).atBottomTo(creatorTextWidget, 0, SWT.BOTTOM);
        CLabelSet.decorate(creatorPreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setText(msgs.getString("creator"));
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
            page.getTestEditorVo().setTitle(titleTextWidget.getText());
            page.edit();
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
                    page.getTestEditorVo().setStars(stars);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
            page.edit();
        }
    }

    private class CreatorTextWidgetModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            page.getTestEditorVo().setCreator(creatorTextWidget.getText());
            page.edit();
        }
    }
}
