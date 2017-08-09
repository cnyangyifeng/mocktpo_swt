package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;

public class ListeningEditorLayer extends ResponsiveTestEditorLayer {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ListeningEditorLayer(TestEditorPage page, int style) {
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
        listeningButton.setBackgroundImages(MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED, MT.IMAGE_SYSTEM_STEP_LISTENING_CHECKED);
    }

    @Override
    protected void updateFooter() {

    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_ORANGE);
    }
}
