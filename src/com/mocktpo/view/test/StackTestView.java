package com.mocktpo.view.test;

import com.mocktpo.page.TestPage;
import com.mocktpo.util.CompositeSet;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.constants.MT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;

public abstract class StackTestView extends TestView {

    /* Stack */

    protected StackLayout stack;

    /* Widgets */

    protected Composite body;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public StackTestView(TestPage page, int style) {
        super(page, style);
    }

    @Override
    protected void initBody() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);

        stack = new StackLayout();
        body.setLayout(stack);

        updateBody();
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    protected abstract void updateBody();

    /*
     * ==================================================
     *
     * Audio Visualization
     *
     * ==================================================
     */

    @Override
    public void startAudioVisualization() {
    }

    @Override
    public void stopAudioVisualization() {
    }
}
