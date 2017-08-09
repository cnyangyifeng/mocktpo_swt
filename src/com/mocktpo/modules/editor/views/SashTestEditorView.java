package com.mocktpo.modules.editor.views;

import com.mocktpo.modules.editor.layers.SashTestEditorLayer;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestViewVo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

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

    public SashTestEditorView(SashTestEditorLayer editorView, int style, TestViewVo viewVo) {
        super(editorView, style, viewVo);
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
        FormDataSet.attach(body).atLeft(0).atTop(0).atRight(0).atBottom(0);
        FormLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);

        final Label divider = new Label(body, SWT.VERTICAL);
        FormDataSet.attach(divider).atTop(0).fromRight(50).atBottom(0).withWidth(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

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
