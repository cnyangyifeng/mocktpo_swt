package com.mocktpo.page;

import java.util.ResourceBundle;

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

import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;
import com.mocktpo.view.ReportsHomeView;
import com.mocktpo.view.SettingsHomeView;
import com.mocktpo.view.TestsHomeView;

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
    private TestsHomeView tv;
    private ReportsHomeView rv;
    private SettingsHomeView sv;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

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
        sidebar.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        FormLayoutSet.layout(sidebar);

        final CLabel bl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(bl).atLeft().atTop().atRight().withHeight(80);
        bl.setText(msgs.getString("app_name_alt"));
        bl.setFont(ResourceManager.getFont(MT.FONT_TITLE));
        bl.setImage(ResourceManager.getImage(MT.IMAGE_LOGO));
        bl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        bl.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        bl.setLeftMargin(10);
        bl.setRightMargin(20);

        tl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(tl).atLeft().atTopTo(bl).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        tl.setText(msgs.getString("tests"));
        tl.setBackground(ResourceManager.getColor(MT.COLOR_BLUE));
        tl.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        tl.setLeftMargin(30);
        tl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        tl.addMouseListener(new SidebarItemListener());

        rl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(rl).atLeft().atTopTo(tl).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        rl.setText(msgs.getString("reports"));
        rl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        rl.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        rl.setLeftMargin(30);
        rl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        rl.addMouseListener(new SidebarItemListener());

        sl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(sl).atLeft().atTopTo(rl).atRight().withHeight(LC.SIDEBAR_ITEM_HEIGHT);
        sl.setText(msgs.getString("settings"));
        sl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        sl.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        sl.setLeftMargin(30);
        sl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        sl.addMouseListener(new SidebarItemListener());
    }

    private void initPages() {
        body = new Composite(this, SWT.NONE);
        FormDataSet.attach(body).atLeftTo(sidebar).atTop().atRight().atBottom();
        stack = new StackLayout();
        body.setLayout(stack);

        tv = new TestsHomeView(body, SWT.NONE);
        rv = new ReportsHomeView(body, SWT.NONE);
        sv = new SettingsHomeView(body, SWT.NONE);

        stack.topControl = tv;
        body.layout();
    }

    /**************************************************
     * 
     * Page Controls
     * 
     **************************************************/

    public void toTestsView() {
        stack.topControl = tv;
        body.layout();
    }

    public void toReportsView() {
        stack.topControl = rv;
        body.layout();
    }

    public void toSettingsView() {
        stack.topControl = sv;
        body.layout();
    }

    /**************************************************
     * 
     * Listeners
     * 
     **************************************************/

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
                tl.setBackground(ResourceManager.getColor(MT.COLOR_BLUE));
                rl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                sl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                toTestsView();
            } else if (msgs.getString("reports").equals(text)) {
                rl.setBackground(ResourceManager.getColor(MT.COLOR_BLUE));
                sl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                tl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                toReportsView();
            } else if (msgs.getString("settings").equals(text)) {
                sl.setBackground(ResourceManager.getColor(MT.COLOR_BLUE));
                tl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                rl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                toSettingsView();
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }
}
