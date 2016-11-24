package com.mocktpo.page;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.CLabelSet;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.view.home.ReportsHomeView;
import com.mocktpo.view.home.SettingsHomeView;
import com.mocktpo.view.home.TestEditorHomeView;
import com.mocktpo.view.home.TestsHomeView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
    private CLabel tl;
    private CLabel rl;
    private CLabel sl;

    private Composite body;
    private TestsHomeView thv;
    private ReportsHomeView rhv;
    private SettingsHomeView shv;

    /* Advanced Features: Test Editor */

    private CLabel el;
    private TestEditorHomeView chv;

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
        sidebar.setBackground(ResourceManager.getColor(MT.COLOR_OXFORD_BLUE));
        FormLayoutSet.layout(sidebar);

        final CLabel bl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(bl).atLeft().atTop().atRight().withHeight(80);
        CLabelSet.decorate(bl).setBackground(MT.COLOR_OXFORD_BLUE).setFont(MT.FONT_X_LARGE_BOLD).setForeground(MT.COLOR_WHITE).setImage(MT.IMAGE_LOGO).setLeftMargin(10).setRightMargin(20).setText(msgs.getString("app_name_alt"));

        tl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(tl).atLeft().atTopTo(bl).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(tl).setBackground(MT.COLOR_DARK_BLUE).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("tests"));
        tl.addMouseListener(new SidebarItemListener());

        rl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(rl).atLeft().atTopTo(tl).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(rl).setBackground(MT.COLOR_OXFORD_BLUE).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("reports"));
        rl.addMouseListener(new SidebarItemListener());

        sl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(sl).atLeft().atTopTo(rl).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        CLabelSet.decorate(sl).setBackground(MT.COLOR_OXFORD_BLUE).setCursor(MT.CURSOR_HAND).setFont(MT.FONT_MEDIUM_BOLD).setForeground(MT.COLOR_WHITE).setLeftMargin(20).setText(msgs.getString("settings"));
        sl.addMouseListener(new SidebarItemListener());

        /* Advanced Features: Test Editor */

        el = new CLabel(sidebar, SWT.CENTER);
        FormDataSet.attach(el).atLeft(10).atRight(10).atBottom(10).withHeight(LC.SIDEBAR_TEST_EDITOR_HEIGHT);
        CLabelSet.decorate(el).setBackground(MT.COLOR_DARK_ORANGE).setCursor(MT.CURSOR_HAND).setForeground(MT.COLOR_WHITE).setLeftMargin(30).setText(msgs.getString("test_editor"));
        el.addMouseListener(new SidebarItemListener());
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

        if (null == thv) {
            thv = new TestsHomeView(body, SWT.NONE);
        }

        stack.topControl = thv;
        body.layout();
    }

    public void toTestsHomeView(UserTest ut) {

        if (null == thv) {
            thv = new TestsHomeView(body, SWT.NONE);
        }
        thv.reset(ut);

        stack.topControl = thv;
        body.layout();
    }

    public void toReportsHomeView() {

        if (null == rhv) {
            rhv = new ReportsHomeView(body, SWT.NONE);
        }

        stack.topControl = rhv;
        body.layout();
    }

    public void toSettingsHomeView() {

        if (null == shv) {
            shv = new SettingsHomeView(body, SWT.NONE);
        }

        stack.topControl = shv;
        body.layout();
    }

    public void toTestEditorHomeView() {

        if (null == chv) {
            chv = new TestEditorHomeView(body, SWT.NONE);
        }

        stack.topControl = chv;
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

    private class SidebarItemListener implements MouseListener {

        @Override
        public void mouseDoubleClick(MouseEvent e) {
        }

        @Override
        public void mouseDown(MouseEvent e) {

            String text = ((CLabel) e.widget).getText();
            
            if (msgs.getString("tests").equals(text)) {
                CLabelSet.decorate(tl).setBackground(MT.COLOR_DARK_BLUE);
                CLabelSet.decorate(rl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(sl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(el).setBackground(MT.COLOR_DARK_ORANGE).setForeground(MT.COLOR_WHITE);
                toTestsHomeView();
            } else if (msgs.getString("reports").equals(text)) {
                CLabelSet.decorate(tl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(rl).setBackground(MT.COLOR_DARK_BLUE);
                CLabelSet.decorate(sl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(el).setBackground(MT.COLOR_DARK_ORANGE).setForeground(MT.COLOR_WHITE);
                toReportsHomeView();
            } else if (msgs.getString("settings").equals(text)) {
                CLabelSet.decorate(tl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(rl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(sl).setBackground(MT.COLOR_DARK_BLUE);
                CLabelSet.decorate(el).setBackground(MT.COLOR_DARK_ORANGE).setForeground(MT.COLOR_WHITE);
                toSettingsHomeView();
            } else if (msgs.getString("test_editor").equals(text)) {
                CLabelSet.decorate(tl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(rl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(sl).setBackground(MT.COLOR_OXFORD_BLUE);
                CLabelSet.decorate(el).setBackground(MT.COLOR_BURGUNDY).setForeground(MT.COLOR_WHITE);
                toTestEditorHomeView();
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
