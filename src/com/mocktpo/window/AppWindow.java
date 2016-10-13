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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.mocktpo.MyApplication;
import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ImageUtils;
import com.mocktpo.util.constants.ResourceConstants;
import com.mocktpo.widgets.TestsHomeView;

public class AppWindow {

    /* Constants */

    private static final int SIDEBAR_ITEM_HEIGHT = 50;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    private Display d;
    private MyApplication app;

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

    /* Resources */

    private Color blue;
    private Color darkGray;
    private Color white;
    private Cursor hand;
    private Font tf;
    private Font sif;
    private Image ico;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public AppWindow(MyApplication app) {
        this.app = app;
        this.d = app.getDisplay();
        init();
    }

    private void init() {
        s = new Shell(d);
        alloc();
        golbal();
        initSidebar();
        initBody();
    }

    private void golbal() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ico);
        s.setMaximized(true);
        s.setMinimumSize(900, 600);
        FormLayoutSet.layout(s);
        s.addShellListener(new AppWindowListener());
        s.addListener(SWT.Resize, new AppWindowResizeListener());
    }

    private void initSidebar() {
        sidebar = new Composite(s, SWT.NONE);
        FormDataSet.attach(sidebar).atLeft().atTop().atBottom().withWidth(s.getBounds().width / 6);
        sidebar.setBackground(darkGray);
        FormLayoutSet.layout(sidebar);

        final CLabel title = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(title).atLeft().atTop().atRight().withHeight(80);
        title.setText(msgs.getString("app_name_alt"));
        title.setBackground(darkGray);
        title.setForeground(white);
        title.setFont(tf);
        title.setLeftMargin(10);

        tl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(tl).atLeft().atTopTo(title).atRight().withHeight(SIDEBAR_ITEM_HEIGHT);
        tl.setText(msgs.getString("tests"));
        tl.setBackground(blue);
        tl.setForeground(white);
        tl.setFont(sif);
        tl.setLeftMargin(20);
        tl.setCursor(hand);
        tl.addMouseListener(new SidebarItemListener());

        rl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(rl).atLeft().atTopTo(tl).atRight().withHeight(SIDEBAR_ITEM_HEIGHT);
        rl.setText(msgs.getString("reports"));
        rl.setBackground(darkGray);
        rl.setForeground(white);
        rl.setFont(sif);
        rl.setLeftMargin(20);
        rl.setCursor(hand);
        rl.addMouseListener(new SidebarItemListener());

        sl = new CLabel(sidebar, SWT.NONE);
        FormDataSet.attach(sl).atLeft().atTopTo(rl).atRight().withHeight(SIDEBAR_ITEM_HEIGHT);
        sl.setText(msgs.getString("settings"));
        sl.setBackground(darkGray);
        sl.setForeground(white);
        sl.setFont(sif);
        sl.setLeftMargin(20);
        sl.setCursor(hand);
        sl.addMouseListener(new SidebarItemListener());
    }

    private void initBody() {
        body = new Composite(s, SWT.NONE);
        FormDataSet.attach(body).atLeftTo(sidebar).atTop().atRight().atBottom();
        layout = new StackLayout();
        body.setLayout(layout);

        tc = new TestsHomeView(body, SWT.NONE);

        rc = new Composite(body, SWT.NONE);
        rc.setBackground(white);

        sc = new Composite(body, SWT.NONE);
        sc.setBackground(white);

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
        release();
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
     * Native Resource Operations
     * 
     **************************************************/

    private void alloc() {
        blue = new Color(d, 14, 116, 219); // #0e74db
        darkGray = new Color(d, 67, 76, 79); // #434c4f
        white = new Color(d, 255, 255, 255);
        hand = new Cursor(d, SWT.CURSOR_HAND);
        tf = new Font(d, "Roboto", 24, SWT.BOLD);
        sif = new Font(d, "Roboto", 16, SWT.NORMAL | SWT.ITALIC);
        ico = ImageUtils.load(d, ResourceConstants.APP_ICON_UNIVERSAL_FILE);
    }

    private void release() {
        /* Resources */
        blue.dispose();
        darkGray.dispose();
        white.dispose();
        hand.dispose();
        tf.dispose();
        sif.dispose();
        ico.dispose();

        /* Widgets */
        tc.dispose();
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
                tl.setBackground(blue);
                rl.setBackground(darkGray);
                sl.setBackground(darkGray);
                layout.topControl = tc;
                body.layout();
            } else if (msgs.getString("reports").equals(text)) {
                rl.setBackground(blue);
                sl.setBackground(darkGray);
                tl.setBackground(darkGray);
                layout.topControl = rc;
                body.layout();
            } else if (msgs.getString("settings").equals(text)) {
                sl.setBackground(blue);
                tl.setBackground(darkGray);
                rl.setBackground(darkGray);
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

    public Display getDisplay() {
        return d;
    }

    public void setDisplay(Display d) {
        this.d = d;
    }

    public MyApplication getApp() {
        return app;
    }

    public void setApp(MyApplication app) {
        this.app = app;
    }

    public Shell getShell() {
        return s;
    }

    public void setShell(Shell s) {
        this.s = s;
    }
}
