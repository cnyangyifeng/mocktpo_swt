package com.mocktpo.views.nav;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.orm.mapper.UserTestSessionMapper;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.ButtonSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.widgets.TestRow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.*;

import java.util.List;
import java.util.ResourceBundle;

public class LoadTestView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Widgets */

    private Composite toolBar;
    private ScrolledComposite sc;
    private Composite body;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public LoadTestView(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initToolBar();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initToolBar() {
        toolBar = new Composite(this, SWT.NONE);
        FormDataSet.attach(toolBar).atLeft().atTop().atRight();
        CompositeSet.decorate(toolBar).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(toolBar).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(toolBar).atRight().withHeight(1);
        LabelSet.decorate(divider).setBackground(MT.COLOR_HIGHLIGHTED);

        final Button sortByNameButton = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(sortByNameButton).atLeft().atTop().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(sortByNameButton).setText(msgs.getString("sort_by_name"));

        final Button importButton = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(importButton).atTop().atRight().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(importButton).setText(msgs.getString("import"));
    }

    private void initBody() {
        sc = new ScrolledComposite(this, SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        sc.setContent(body);

        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        GridLayoutSet.layout(body).marginWidth(20).marginHeight(20).horizontalSpacing(20).verticalSpacing(20);

        initRows();
    }

    public void initRows() {
        UserTestSessionMapper userTestSessionMapper = MyApplication.get().getSqlSession().getMapper(UserTestSessionMapper.class);
        List<UserTestSession> list = userTestSessionMapper.find();
        for (UserTestSession userTestSession : list) {
            TestRow row = new TestRow(body, SWT.NONE, userTestSession);
            GridDataSet.attach(row).fillHorizontal();
        }
        body.layout();
        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    public void refreshRows() {
        for (Control c : body.getChildren()) {
            c.dispose();
        }
        initRows();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */
}
