package com.mocktpo.modules.editor.layers;

import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public abstract class SashTestEditorLayer extends TestEditorLayer {

    /* Constants */

    private static final int LEFT_COMPOSITE_WIDTH = 232;

    /* Widgets */

    protected Composite left;
    protected ScrolledComposite lsc;
    protected Composite leftBody;

    protected Composite right;
    protected StackLayout rightViewStack;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SashTestEditorLayer(TestEditorPage page, int style) {
        super(page, style);
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    @Override
    protected void initBody() {
        final Composite body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        left = new Composite(body, SWT.NONE);
        FormDataSet.attach(left).atLeft().atTop().atBottom().withWidth(LEFT_COMPOSITE_WIDTH);
        FormLayoutSet.layout(left).marginWidth(0).marginHeight(0).spacing(0);

        final Label divider = new Label(left, SWT.VERTICAL);
        FormDataSet.attach(divider).atTop().atRight().atBottom().withWidth(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        lsc = new ScrolledComposite(left, SWT.V_SCROLL);
        FormDataSet.attach(lsc).atLeft().atTop().atRight().atBottom();
        lsc.setExpandHorizontal(true);
        lsc.setExpandVertical(true);

        leftBody = new Composite(lsc, SWT.NONE);
        CompositeSet.decorate(leftBody).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(leftBody).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        lsc.setContent(leftBody);

        updateLeft();

        right = new Composite(body, SWT.NONE);
        FormDataSet.attach(right).atLeftTo(left).atTop().atRight().atBottom();
        FormLayoutSet.layout(right).marginWidth(0).marginHeight(0).spacing(0);

        FormLayoutSet.layout(right).marginWidth(0).marginHeight(0).spacing(0);
        rightViewStack = new StackLayout();
        right.setLayout(rightViewStack);

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

    public Composite getLeftBody() {
        return leftBody;
    }

    public Composite getRight() {
        return right;
    }
}
