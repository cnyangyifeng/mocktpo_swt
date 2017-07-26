package com.mocktpo.modules.paper.views;

import com.mocktpo.util.ScreenUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public class GeneralPaperView extends Composite {

    /* Constants */

    private static final int VIEW_PORT_PADDING_WIDTH = 50;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Widgets */

    private ScrolledComposite sc;
    private Composite body;
    private Composite viewPort;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public GeneralPaperView(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initBody() {
        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        GridLayoutSet.layout(body).marginBottom(50);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(ScreenUtils.getViewPort(d).x - VIEW_PORT_PADDING_WIDTH * 2);
        GridLayoutSet.layout(viewPort).verticalSpacing(0);

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


    private void updateBody() {
        CompositeSet.decorate(body).setBackground(MT.COLOR_BEIGE);
    }
}
