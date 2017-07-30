package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.modules.system.listeners.BorderedCompositePaintListener;
import com.mocktpo.util.KeyBindingSet;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.StyledTextSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.h2.util.StringUtils;

public class GeneralPaperView extends ResponsiveTestPaperView {

    /* Constants */

    private static final int INPUT_TEXT_HEIGHT = 22;
    private static final int PRE_LABEL_WIDTH = 120;

    /* Widgets */

    private StyledText titleText;
    private StyledText starsText;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public GeneralPaperView(TestPaperPage page, int style) {
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
    public void updateHeader() {
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
    public void updateFooter() {
        if (page.isFirstRun()) {
            exportAsZipButton.setEnabled(false);
        }
    }

    @Override
    public void updateBody() {
        titleText = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(titleText).atLeft(PRE_LABEL_WIDTH).atTop().atRight().withHeight(INPUT_TEXT_HEIGHT);
        StyledTextSet.decorate(titleText).setBackground(MT.COLOR_WHITE).setFocus().setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(page.getTestPaperVo().getTitle());
        KeyBindingSet.bind(titleText).traverse().selectAll();
        titleText.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        titleText.addModifyListener(new TitleTextModifyListener());
        titleText.setSelection(titleText.getText().length());

        final CLabel titlePreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(titlePreLabel).atLeft().atTopTo(titleText, 0, SWT.TOP).atRightTo(titleText, 0, SWT.LEFT).atBottomTo(titleText, 0, SWT.BOTTOM);
        CLabelSet.decorate(titlePreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("title") + MT.STRING_TAB + MT.STRING_STAR);

        starsText = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(starsText).atLeft(PRE_LABEL_WIDTH).atTopTo(titleText).withWidth(40).withHeight(INPUT_TEXT_HEIGHT);
        StyledTextSet.decorate(starsText).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10).setText(Integer.toString(page.getTestPaperVo().getStars()));
        KeyBindingSet.bind(starsText).traverse().selectAll();
        starsText.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));
        starsText.addModifyListener(new StarsTextModifyListener());

        final CLabel starsPreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(starsPreLabel).atLeft().atTopTo(starsText, 0, SWT.TOP).atRightTo(starsText, 0, SWT.LEFT).atBottomTo(starsText, 0, SWT.BOTTOM);
        CLabelSet.decorate(starsPreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("stars") + MT.STRING_TAB + MT.STRING_STAR);

        final StyledText authorText = new StyledText(viewPort, SWT.SINGLE);
        FormDataSet.attach(authorText).atLeft(PRE_LABEL_WIDTH).atTopTo(starsText).withWidth(240).withHeight(INPUT_TEXT_HEIGHT);
        StyledTextSet.decorate(authorText).setBackground(MT.COLOR_WHITE).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY20).setMargins(10, 10, 10, 10);
        KeyBindingSet.bind(authorText).traverse().selectAll();
        authorText.addPaintListener(new BorderedCompositePaintListener(MT.COLOR_HIGHLIGHTED));

        final CLabel authorPreLabel = new CLabel(viewPort, SWT.NONE);
        FormDataSet.attach(authorPreLabel).atLeft().atTopTo(authorText, 0, SWT.TOP).atRightTo(authorText, 0, SWT.LEFT).atBottomTo(authorText, 0, SWT.BOTTOM);
        CLabelSet.decorate(authorPreLabel).setFont(MT.FONT_MEDIUM).setForeground(MT.COLOR_GRAY60).setText(msgs.getString("author") + MT.STRING_TAB + MT.STRING_STAR);
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class TitleTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            page.getTestPaperVo().setTitle(titleText.getText());
            page.enterUnsavedMode();
        }
    }

    private class StarsTextModifyListener implements ModifyListener {

        @Override
        public void modifyText(ModifyEvent e) {
            String st = starsText.getText();
            if (StringUtils.isNumber(st)) {
                try {
                    Integer stars = Integer.parseInt(st);
                    if (stars < 0) {
                        stars = 0;
                    } else if (stars > 5) {
                        stars = 5;
                    }
                    page.getTestPaperVo().setStars(stars);
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
            page.enterUnsavedMode();
        }
    }
}
