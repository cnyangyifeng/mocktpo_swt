package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;

public class SpeakingPaperView extends ResponsiveTestPaperView {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SpeakingPaperView(TestPaperPage page, int style) {
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
        speakingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_SPEAKING_CHECKED, MT.IMAGE_SYSTEM_STEP_SPEAKING_CHECKED);
    }

    @Override
    protected void updateFooter() {

    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_OXFORD_BLUE);
    }
}
