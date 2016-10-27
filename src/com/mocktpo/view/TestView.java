package com.mocktpo.view;

import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.MyApplication;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.GridDataSet;
import com.mocktpo.util.GridLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.TestFooter;
import com.mocktpo.widget.TestHeader;

public abstract class TestView extends Composite {

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
    protected Composite viewPort;
    protected TestFooter footer;

    private Label tl;

    /* Properties */

    protected int nextViewId;

    /* Persistence */

    protected SqlSession sqlSession;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public TestView(TestPage page, int style) {
        super(page, style);
        this.d = page.getDisplay();
        this.page = page;
        this.sqlSession = MyApplication.get().getSqlSession();
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

        tl = new Label(header, SWT.NONE);
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
        body.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        GridLayoutSet.layout(body);

        viewPort = new Composite(body, SWT.NONE);
        GridDataSet.attach(viewPort).topCenter().withWidth(LC.VIEW_PORT_WIDTH);
        FormLayoutSet.layout(viewPort);

        updateBody();

        sc.setContent(body);
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    /**************************************************
     * 
     * Update Widgets
     * 
     **************************************************/

    public abstract void updateHeader();

    public abstract void updateBody();

    /**************************************************
     * 
     * Reset
     * 
     **************************************************/

    public void reset() {
        d.asyncExec(new Runnable() {
            @Override
            public void run() {
                tl.setText(page.getUserTest().getTitle());
            }
        });
    }
}
