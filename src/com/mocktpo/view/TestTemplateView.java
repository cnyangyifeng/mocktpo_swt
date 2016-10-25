package com.mocktpo.view;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.page.TestPage;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.TestFooter;
import com.mocktpo.widget.TestHeader;

public abstract class TestTemplateView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Page */

    protected TestPage page;

    /* Widgets */

    protected TestHeader header;
    protected Composite body;
    protected TestFooter footer;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public TestTemplateView(TestPage page, int style) {
        super(page, style);
        this.d = page.getDisplay();
        this.page = page;
        init();
    }

    private void init() {
        golbal();
        initHeader();
        initFooter();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initHeader() {
        header = new TestHeader(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight().withHeight(LC.TEST_HEADER_HEIGHT);
        FormLayoutSet.layout(header);

        final Label tl = new Label(header, SWT.NONE);
        FormDataSet.attach(tl).atLeft(10).atTop(10);
        tl.setText(page.getUserTest().getTitle());
        tl.setFont(ResourceManager.getFont(MT.FONT_SUBTITLE));
        tl.setForeground(ResourceManager.getColor(MT.COLOR_LIGHTER_GRAY));

        updateHeader();
    }

    private void initFooter() {
        footer = new TestFooter(this, SWT.NONE);
        FormDataSet.attach(footer).atLeft().atRight().atBottom().withHeight(LC.TEST_FOOTER_HEIGHT);
    }

    private void initBody() {
        final ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(header).atRight().atBottomTo(footer);
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        sc.setContent(body);

        updateBody();
    }

    /**************************************************
     * 
     * Update Widgets
     * 
     **************************************************/

    public abstract void updateHeader();

    public abstract void updateBody();
}
