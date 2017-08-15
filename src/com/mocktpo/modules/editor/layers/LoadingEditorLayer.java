package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;

public class LoadingEditorLayer extends ResponsiveTestEditorLayer {

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public LoadingEditorLayer(TestEditorPage page, int style) {
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
        backButton.setEnabled(false);
        generalButton.setEnabled(false);
        readingButton.setEnabled(false);
        listeningButton.setEnabled(false);
        speakingButton.setEnabled(false);
        writingButton.setEnabled(false);
        previewButton.setEnabled(false);
    }

    @Override
    protected void updateFooter() {
        exportAsZipButton.setEnabled(false);
        saveButton.setEnabled(false);
    }

    @Override
    protected void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);

        FormLayoutSet.layout(viewPort).marginWidth(0).marginHeight(50).spacing(10);

    }
}
