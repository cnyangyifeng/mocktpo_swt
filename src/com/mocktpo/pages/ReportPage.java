package com.mocktpo.pages;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.ConfigUtils;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.constants.RC;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.ButtonSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import com.mocktpo.vo.TestSchemaVo;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ResourceBundle;

public class ReportPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Widgets */

    private Composite toolBar;
    private Composite body;

    /* Views */

    private Browser browser;

    /* Properties */

    private TestSchemaVo testSchema;
    private UserTestSession userTestSession;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public ReportPage(Composite parent, int style, UserTestSession userTestSession) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.userTestSession = userTestSession;
        this.testSchema = ConfigUtils.load(this.userTestSession.getFileAlias(), TestSchemaVo.class);
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

        final Button backButton = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(backButton).atLeft().atTop().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(backButton).setImage(MT.IMAGE_ARROW_BACK).setText(msgs.getString("back"));
        backButton.addSelectionListener(new BackButtonSelectionAdapter());

        final Button exportButton = new Button(toolBar, SWT.PUSH);
        FormDataSet.attach(exportButton).atTop().atRight().withHeight(LC.BUTTON_HEIGHT_HINT);
        ButtonSet.decorate(exportButton).setText(msgs.getString("export"));
    }

    private void initBody() {
        final ScrolledComposite sc = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
        FormDataSet.attach(sc).atLeft().atTopTo(toolBar).atRight().atBottom();
        sc.setExpandHorizontal(true);
        sc.setExpandVertical(true);

        body = new Composite(sc, SWT.NONE);
        sc.setContent(body);

        CompositeSet.decorate(body).setBackground(MT.COLOR_WINDOW_BACKGROUND);
        FormLayoutSet.layout(body);

        browser = new Browser(body, SWT.NONE);
        try {
            URL url = this.getClass().getResource(URLDecoder.decode(RC.TEMPLATES_DIR + "index.html", "utf-8"));
            browser.setUrl(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        FormDataSet.attach(browser).atLeft().atTop().atRight().atBottom();

        sc.setMinSize(body.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class BackButtonSelectionAdapter extends SelectionAdapter {

        @Override
        public void widgetSelected(SelectionEvent e) {
            MyApplication.get().getWindow().toMainPage();
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public TestSchemaVo getTestSchema() {
        return testSchema;
    }

    public UserTestSession getUserTestSession() {
        return userTestSession;
    }
}
