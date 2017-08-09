package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;

public class WritingEditorLayer extends ResponsiveTestEditorLayer {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public WritingEditorLayer(TestEditorPage page, int style) {
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
        writingButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_WRITING_CHECKED, MT.IMAGE_SYSTEM_STEP_WRITING_CHECKED);
    }

    @Override
    protected void updateFooter() {

    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_ORANGE_RED);
    }
}
