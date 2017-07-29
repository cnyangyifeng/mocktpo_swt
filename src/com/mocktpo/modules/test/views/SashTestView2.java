package com.mocktpo.modules.test.views;

import com.mocktpo.modules.test.TestPage;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.constants.MT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class SashTestView2 extends TestView {

    /* Constants */

    protected static final int TOP_HEIGHT = 120;

    /* Widgets */

    protected Composite top;
    protected Composite left;
    protected Composite right;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SashTestView2(TestPage page, int style) {
        super(page, style);
    }

    @Override
    protected void initBody() {
        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WHITE);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        top = new Composite(body, SWT.BORDER);
        FormDataSet.attach(top).atLeft().atTop().atRight().withHeight(TOP_HEIGHT);
        FormLayoutSet.layout(top).marginWidth(10).marginHeight(10).spacing(0);

        updateTop();

        left = new Composite(body, SWT.BORDER);
        FormDataSet.attach(left).atLeft().atTopTo(top, 5).fromRight(50).atBottom();
        FormLayoutSet.layout(left).marginWidth(0).marginHeight(0).spacing(0);

        updateLeft();

        right = new Composite(body, SWT.BORDER);
        FormDataSet.attach(right).atLeftTo(left, 5).atTopTo(top, 5).atRight().atBottom();
        FormLayoutSet.layout(right).marginWidth(10).marginHeight(10).spacing(10);

        updateRight();
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    protected abstract void updateTop();

    protected abstract void updateLeft();

    protected abstract void updateRight();

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
