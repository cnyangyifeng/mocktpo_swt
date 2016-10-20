package com.mocktpo.window;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.mocktpo.MyApplication;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;
import com.mocktpo.widgets.TestsHomeView;

public class MainWindow {

    /* Constants */

    private static final int SIDEBAR_ITEM_HEIGHT = 50;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application and Display */

    protected MyApplication app;
    protected Display d;

    /* Shell */

    private Shell s;

    /* Widgets */

    private Composite sidebar;
    private CLabel tl;
    private CLabel rl;
    private CLabel sl;

    private Composite body;
    private TestsHomeView tc;
    private Composite rc;
    private Composite sc;

    /* Layouts */

    private StackLayout layout;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public MainWindow(MyApplication app) {
        this.app = app;
        this.d = app.getDisplay();
        init();
    }

    private void init() {
        s = new Shell(d);
        golbal();
        initSidebar();
        initBody();
    }

    private void golbal() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        s.setMaximized(true);
        s.setMinimumSize(900, 600);
        FormLayoutSet.layout(s);
        s.addShellListener(new AppWindowListener());
        s.addListener(SWT.Resize, new AppWindowResizeListener());
    }

    private void initSidebar() {
        sidebar = new Composite(s, SWT.NONE);
        FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(s.getBounds().width / 6);
        sidebar.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        FormLayoutSet.layout(sidebar);

        final CLabel title = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(title).atLeft().atTop().atRight().withHeight(80);
        title.setText(msgs.getString("app_name_alt"));
        title.setFont(ResourceManager.getFont(MT.FONT_TITLE));
        title.setImage(ResourceManager.getImage(MT.IMAGE_LOGO));
        title.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        title.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        title.setLeftMargin(10);
        title.setRightMargin(20);

        tl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(tl).atLeft().atTopTo(title).atRight().withHeight(SIDEBAR_ITEM_HEIGHT);
        tl.setText(msgs.getString("tests"));
        tl.setBackground(ResourceManager.getColor(MT.COLOR_BLUE));
        tl.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        tl.setLeftMargin(30);
        // tl.setImage(ResourceManager.getImage(MT.IMAGE_ARROW_RIGHT));
        tl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        tl.addMouseListener(new SidebarItemListener());

        rl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(rl).atLeft().atTopTo(tl).atRight().withHeight(SIDEBAR_ITEM_HEIGHT);
        rl.setText(msgs.getString("reports"));
        rl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        rl.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        rl.setLeftMargin(30);
        // rl.setImage(ResourceManager.getImage(MT.IMAGE_ARROW_RIGHT));
        rl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        rl.addMouseListener(new SidebarItemListener());

        sl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(sl).atLeft().atTopTo(rl).atRight().withHeight(SIDEBAR_ITEM_HEIGHT);
        sl.setText(msgs.getString("settings"));
        sl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
        sl.setForeground(ResourceManager.getColor(MT.COLOR_WHITE));
        sl.setLeftMargin(30);
        // sl.setImage(ResourceManager.getImage(MT.IMAGE_ARROW_RIGHT));
        sl.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        sl.addMouseListener(new SidebarItemListener());
    }

    private void initBody() {
        body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeftTo(sidebar).atTop().atRight().atBottom();
        layout = new StackLayout();
        body.setLayout(layout);

        tc = new TestsHomeView(body, SWT.NONE);

        rc = new Composite(body, SWT.NONE);
        rc.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));

        sc = new Composite(body, SWT.NONE);
        sc.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));

        layout.topControl = tc;
        body.layout();
        // TODO Remove focus from body.
        body.setFocus();
    }

    public void openAndWaitForDisposal() {
        s.open();
        while (!s.isDisposed()) {
            if (!d.readAndDispatch()) {
                d.sleep();
            }
        }
    }

    public void dispose() {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    s.dispose();
                    d.dispose();
                }
            });
        }
    }

    public void close() {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    s.dispose();
                }
            });
        }
    }

    /**************************************************
     * 
     * Listeners
     * 
     **************************************************/

    private class AppWindowListener implements ShellListener {

        @Override
        public void shellActivated(ShellEvent e) {
        }

        @Override
        public void shellClosed(ShellEvent e) {
        }

        @Override
        public void shellDeactivated(ShellEvent e) {
        }

        @Override
        public void shellDeiconified(ShellEvent e) {
        }

        @Override
        public void shellIconified(ShellEvent e) {
        }
    }

    private class AppWindowResizeListener implements Listener {

        @Override
        public void handleEvent(Event e) {
            FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(((Shell) e.widget).getBounds().width / 6);
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
                layout.topControl = tc;
                body.layout();
            } else if (msgs.getString("reports").equals(text)) {
                rl.setBackground(ResourceManager.getColor(MT.COLOR_BLUE));
                sl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                tl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                layout.topControl = rc;
                body.layout();
            } else if (msgs.getString("settings").equals(text)) {
                sl.setBackground(ResourceManager.getColor(MT.COLOR_BLUE));
                tl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                rl.setBackground(ResourceManager.getColor(MT.COLOR_DARK_GRAY));
                layout.topControl = sc;
                body.layout();
            }
        }

        @Override
        public void mouseUp(MouseEvent e) {
        }
    }

    /**************************************************
     * 
     * Getters and Setters
     * 
     **************************************************/

    public MyApplication getApp() {
        return app;
    }

    public void setApp(MyApplication app) {
        this.app = app;
    }

    public Display getDisplay() {
        return d;
    }

    public void setDisplay(Display d) {
        this.d = d;
    }

    public Shell getShell() {
        return s;
    }

    public void setShell(Shell s) {
        this.s = s;
    }
}
