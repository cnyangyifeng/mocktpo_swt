package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;

public class PreviewPaperView extends ResponsiveTestPaperView {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public PreviewPaperView(TestPaperPage page, int style) {
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
        previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW_CHECKED, MT.IMAGE_SYSTEM_STEP_PREVIEW_CHECKED);
    }

    @Override
    public void updateFooter() {

    }

    @Override
    public void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_DARK_BLUE);
    }
}
