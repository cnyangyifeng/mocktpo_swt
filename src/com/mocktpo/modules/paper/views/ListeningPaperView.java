package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;

public class ListeningPaperView extends ResponsiveTestPaperView {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningPaperView(TestPaperPage page, int style) {
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
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED, MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED);
    }

    @Override
    public void updateFooter() {

    }

    @Override
    public void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_ORANGE);
    }
}
