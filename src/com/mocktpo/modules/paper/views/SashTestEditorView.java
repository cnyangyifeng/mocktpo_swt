package com.mocktpo.modules.paper.views;

import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public abstract class SashTestEditorView extends TestEditorView {

    /* Widgets */

    protected Composite left;
    protected Composite right;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SashTestEditorView(SashTestPaperView paperView, int style) {
        super(paperView, style);
    }

    @Override
    protected void initBody() {
        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft(0).atTop(0).atRight(0).atBottom(0);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        left = new Composite(body, SWT.NONE);
        FormDataSet.attach(left).atLeft().atTop().fromRight(50).atBottom();
        FormLayoutSet.layout(left).marginWidth(0).marginHeight(0).spacing(0);

        updateLeft();

        right = new Composite(body, SWT.NONE);
        FormDataSet.attach(right).atLeftTo(left).atTop().atRight().atBottom();
        FormLayoutSet.layout(right).marginWidth(0).marginHeight(0).spacing(0);

        updateRight();
    }

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    protected abstract void updateLeft();

    protected abstract void updateRight();
}
