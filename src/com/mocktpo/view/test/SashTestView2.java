package com.mocktpo.view.test;

import com.mocktpo.page.TestPage;
import com.mocktpo.util.CompositeSet;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
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
        FormLayoutSet.layout(body);

        top = new Composite(body, SWT.BORDER);
        FormDataSet.attach(top).atLeft().atTop().atRight().withHeight(TOP_HEIGHT);
        FormLayoutSet.layout(top).marginWidth(10).marginHeight(10);

        updateTop();

        left = new Composite(body, SWT.BORDER);
        FormDataSet.attach(left).atLeft().atTopTo(top, 5).fromRight(50).atBottom();
        FormLayoutSet.layout(left);

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
