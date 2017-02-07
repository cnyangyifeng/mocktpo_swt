package com.mocktpo.pages;

import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.views.nav.LoadTestView;
import com.mocktpo.views.nav.NewTestView;
import com.mocktpo.views.nav.ReportsView;
import com.mocktpo.views.nav.SettingsView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import java.util.ResourceBundle;

public class MainPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Stack */

    protected StackLayout stack;

    /* Widgets */

    private Composite sidebar;
    private CLabel newTestLabel;
    private CLabel loadTestLabel;
    private CLabel reportsLabel;
    private CLabel settingsLabel;

    private Composite body;
    private NewTestView newTestView;
    private LoadTestView loadTestView;
    private ReportsView reportsView;
    private SettingsView settingsView;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public MainPage(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initSidebar();
        initPages();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
        addListener(SWT.Resize, new AppWindowResizeListener());
    }

    private void initSidebar() {
        sidebar = new Composite(this, SWT.NONE);
        FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(this.getBounds().width / 6);
        CompositeSet.decorate(sidebar).setBackground(MT.COLOR_OXFORD_BLUE);
        FormLayoutSet.layout(sidebar);

        final CLabel brandLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(brandLabel).atLeft().atTop().atRight().withHeight(80);
        CLabelSet.decorate(brandLabel).setBackground(MT.COLOR_OXFORD_BLUE).setFont(MT.FONT_X_LARGE_BOLD).setForeground(MT.COLOR_WHITE).setImage(MT.IMAGE_LOGO).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("app_name_alt"));

        newTestLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(newTestLabel).atLeft().atTopTo(brandLabel).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_DARK_BLUE).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("new_test"));
        newTestLabel.addMouseListener(new SidebarItemAdapter());

        loadTestLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(loadTestLabel).atLeft().atTopTo(newTestLabel).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(loadTestLabel).setBackground(MT.COLOR_OXFORD_BLUE).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("load_test"));
        loadTestLabel.addMouseListener(new SidebarItemAdapter());

        reportsLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(reportsLabel).atLeft().atTopTo(loadTestLabel).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(reportsLabel).setBackground(MT.COLOR_OXFORD_BLUE).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("reports"));
        reportsLabel.addMouseListener(new SidebarItemAdapter());

        settingsLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(settingsLabel).atLeft().atTopTo(reportsLabel).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_OXFORD_BLUE).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("settings"));
        settingsLabel.addMouseListener(new SidebarItemAdapter());
    }

    private void initPages() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeftTo(sidebar).atTop().atRight().atBottom();
        stack = new StackLayout();
        body.setLayout(stack);
        toNewTestView();
    }

    /*
     * ==================================================
     *
     * Page Controls
     *
     * ==================================================
     */

    public void toNewTestView() {
        if (null == newTestView) {
            newTestView = new NewTestView(body, SWT.NONE);
        }
        stack.topControl = newTestView;
        body.layout();
    }

    public void toLoadTestView() {
        if (null == loadTestView) {
            loadTestView = new LoadTestView(body, SWT.NONE);
        }
        stack.topControl = loadTestView;
        body.layout();
    }

    public void toReportsView() {
        if (null == reportsView) {
            reportsView = new ReportsView(body, SWT.NONE);
        }
        stack.topControl = reportsView;
        body.layout();
    }

    public void toSettingsView() {
        if (null == settingsView) {
            settingsView = new SettingsView(body, SWT.NONE);
        }
        stack.topControl = settingsView;
        body.layout();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class AppWindowResizeListener implements Listener {

        @Override
        public void handleEvent(Event e) {
            FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(((Composite) e.widget).getBounds().width / 6);
        }
    }

    private class SidebarItemAdapter extends MouseAdapter {

        @Override
        public void mouseDown(MouseEvent e) {
            String text = ((CLabel) e.widget).getText();
            if (msgs.getString("new_test").equals(text)) {
                CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_DARK_BLUE);
                CLabelSet.decorate(loadTestLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(reportsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                toNewTestView();
            } else if (msgs.getString("load_test").equals(text)) {
                CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(loadTestLabel).setBackground(MT.COLOR_DARK_BLUE);
                CLabelSet.decorate(reportsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                toLoadTestView();
            } else if (msgs.getString("reports").equals(text)) {
                CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(loadTestLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(reportsLabel).setBackground(MT.COLOR_DARK_BLUE);
                CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                toReportsView();
            } else if (msgs.getString("settings").equals(text)) {
                CLabelSet.decorate(newTestLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(loadTestLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(reportsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_DARK_BLUE);
                toSettingsView();
            }
        }
    }
}
