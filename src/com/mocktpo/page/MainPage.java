package com.mocktpo.page;

import com.mocktpo.util.CLabelSet;
import com.mocktpo.util.CompositeSet;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.view.home.SettingsHomeView;
import com.mocktpo.view.home.TestsHomeView;
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
    private CLabel testsLabel;
    private CLabel settingsLabel;

    private Composite body;
    private TestsHomeView testsHomeView;
    private SettingsHomeView settingsHomeView;

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

        testsLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(testsLabel).atLeft().atTopTo(brandLabel).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(testsLabel).setBackground(MT.COLOR_DARK_BLUE).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("tests"));
        testsLabel.addMouseListener(new SidebarItemAdapter());

        settingsLabel = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(settingsLabel).atLeft().atTopTo(testsLabel).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_OXFORD_BLUE).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("settings"));
        settingsLabel.addMouseListener(new SidebarItemAdapter());
    }

    private void initPages() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeftTo(sidebar).atTop().atRight().atBottom();
        stack = new StackLayout();
        body.setLayout(stack);
        toTestsHomeView();
    }

    /*
     * ==================================================
     *
     * Page Controls
     *
     * ==================================================
     */

    public void toTestsHomeView() {
        if (null == testsHomeView) {
            testsHomeView = new TestsHomeView(body, SWT.NONE);
        }
        stack.topControl = testsHomeView;
        body.layout();
    }

    public void toSettingsHomeView() {
        if (null == settingsHomeView) {
            settingsHomeView = new SettingsHomeView(body, SWT.NONE);
        }
        stack.topControl = settingsHomeView;
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
            if (msgs.getString("tests").equals(text)) {
                CLabelSet.decorate(testsLabel).setBackground(MT.COLOR_DARK_BLUE);
                CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                toTestsHomeView();
            } else if (msgs.getString("settings").equals(text)) {
                CLabelSet.decorate(testsLabel).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(settingsLabel).setBackground(MT.COLOR_DARK_BLUE);
                toSettingsHomeView();
            }
        }
    }
}
