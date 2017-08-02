package com.mocktpo.modules.paper.views;

import com.mocktpo.modules.paper.TestPaperPage;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;

public abstract class ResponsiveTestPaperView extends TestPaperView {

    /* Constants */

    private static final int VIEW_PORT_WIDTH = 720;

    /* Widgets */

    protected Composite body;
    protected Composite viewPort;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ResponsiveTestPaperView(TestPaperPage page, int style) {
        super(page, style);
    }

    @Override
    protected void initBody() {
        final ScrolledComposite sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(body).marginWidth(0).marginHeight(0).spacing(0);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(VIEW_PORT_WIDTH);
        FormLayoutSet.layout(viewPort).marginWidth(0).marginHeight(50).spacing(10);

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

    /*
     * ==================================================
     *
     * Widget Updates
     *
     * ==================================================
     */

    protected abstract void updateBody();
}
