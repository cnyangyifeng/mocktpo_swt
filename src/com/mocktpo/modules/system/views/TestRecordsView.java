package com.mocktpo.modules.system.views;

import com.mocktpo.modules.system.widgets.TestRecordCard;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.PersistenceUtils;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import java.util.List;
import java.util.ResourceBundle;

public class TestRecordsView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /* Widgets */

    private ScrolledComposite sc;
    private Composite body;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestRecordsView(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    private void initBody() {
        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTop().atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(body).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        sc.setContent(body);
    }

    /*
     * ==================================================
     *
     * Test Record Card Operations
     *
     * ==================================================
     */

    public void refreshCards() {
        for (Control c : body.getChildren()) {
            c.dispose();
        }
        if (!d.isDisposed()) {
            d.asyncExec(() -> {
                initCards();
            });
        }
    }

    private void initCards() {
        List<UserTestSession> sessions = PersistenceUtils.findSessions();
        for (UserTestSession session : sessions) {
            TestRecordCard card = new TestRecordCard(body, SWT.NONE, session);
            GridDataSet.attach(card).fillHorizontal();
        }
        body.layout();
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
        sc.setOrigin(0, 0);
    }
}
