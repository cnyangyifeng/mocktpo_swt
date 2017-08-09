package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;

public class PreviewEditorLayer extends ResponsiveTestEditorLayer {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public PreviewEditorLayer(TestEditorPage page, int style) {
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
        previewButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_PREVIEW_CHECKED, MT.IMAGE_SYSTEM_STEP_PREVIEW_CHECKED);
    }

    @Override
    protected void updateFooter() {

    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_DARK_BLUE);
    }
}
