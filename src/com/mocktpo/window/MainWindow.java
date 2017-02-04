package com.mocktpo.window;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.page.MainPage;
import com.mocktpo.page.ReportPage;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.MT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class MainWindow {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application */

    protected MyApplication app;

    /* Display and Shell */

    protected Display d;
    protected Shell s;

    /* Layouts */

    private StackLayout stack;

    /* Views */

    private MainPage mp;
    private TestPage tp;
    private ReportPage rp;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public MainWindow() {
        this.app = MyApplication.get();
        this.d = app.getDisplay();
        init();
    }

    private void init() {
        s = new Shell(d);
        golbal();
        initViews();
    }

    private void golbal() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ResourceManager.getImage(MT.IMAGE_APP_ICON));
        s.setMaximized(true);
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        WindowUtils.setMinimumWindowSize(s);
        s.addShellListener(new MainWindowAdapter());
    }

    private void initViews() {

        stack = new StackLayout();
        s.setLayout(stack);

        toMainPage();
    }

    public void openAndWaitForDisposal() {
        s.open();
        while (!s.isDisposed()) {
            if (!d.readAndDispatch()) {
                d.sleep();
            }
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

    /*
     * ==================================================
     *
     * Page Controls
     *
     * ==================================================
     */

    public void toMainPage() {
        if (null == mp) {
            mp = new MainPage(s, SWT.NONE);
        }
        mp.toTestsHomeView();
        stack.topControl = mp;
        s.layout();
    }

    public void toMainPage(UserTestSession ut) {
        if (null == mp) {
            mp = new MainPage(s, SWT.NONE);
        }
        mp.toTestsHomeView(ut);
        stack.topControl = mp;
        s.layout();
    }

    public void toTestPage(UserTestSession ut) {
        if (null == tp) {
            tp = new TestPage(s, SWT.NONE, ut);
        }
        tp.resume(ut);
        stack.topControl = tp;
        s.layout();
    }

    public void toReportPage(UserTestSession ut) {
        if (null == rp) {
            rp = new ReportPage(s, SWT.NONE, ut);
        }
        stack.topControl = rp;
        s.layout();
    }

    /*
     * ==================================================
     *
     * Listeners
     *
     * ==================================================
     */

    private class MainWindowAdapter extends ShellAdapter {

        @Override
        public void shellClosed(ShellEvent e) {
            close();
            System.exit(0);
        }
    }

    /*
     * ==================================================
     *
     * Getters and Setters
     *
     * ==================================================
     */

    public Display getDisplay() {
        return d;
    }
}
