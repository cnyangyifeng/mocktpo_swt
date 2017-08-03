package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.LabelSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public abstract class SashTestPaperView extends TestPaperView {

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

    public SashTestPaperView(TestPaperPage page, int style) {
        super(page, style);
    }

    @Override
    protected void initBody() {
        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        left = new Composite(body, SWT.NONE);
        FormDataSet.attach(left).atLeft().atTop().fromRight(80).atBottom();
        FormLayoutSet.layout(left).marginWidth(0).marginHeight(0).spacing(0);

        final Label divider = new Label(left, SWT.VERTICAL);
        FormDataSet.attach(divider).atTop().atRight().atBottom().withWidth(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

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

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public Composite getLeft() {
        return left;
    }

    public Composite getRight() {
        return right;
    }
}
