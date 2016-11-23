package com.mocktpo.view.test;

import com.mocktpo.page.TestPage;
import com.mocktpo.util.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;

public abstract class ResponsiveTestView extends TestView {

    /* Widgets */

    protected Composite body;
    protected Composite viewPort;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public ResponsiveTestView(TestPage page, int style) {
        super(page, style);
    }

    @Override
    protected void initBody() {

        final ScrolledComposite sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(body).marginBottom(50);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x);
        FormLayoutSet.layout(viewPort);

        /*
         * ==================================================
         * 
         * Body Updates
         * 
         * ==================================================
         */

        updateBody();

        sc.setContent(body);
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));

    }

    /**************************************************
     * 
     * Widget Updates
     * 
     **************************************************/

    protected abstract void updateBody();
}
