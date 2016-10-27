package com.mocktpo.window;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.mocktpo.MyApplication;
import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.page.MainPage;
import com.mocktpo.page.TestPage;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;

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

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

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
        s.setMinimumSize(LC.SHELL_MIN_WIDTH, LC.SHELL_MIN_HEIGHT);
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        s.addShellListener(new MainWindowListener());
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

    /**************************************************
     * 
     * Page Controls
     * 
     **************************************************/

    public void toMainPage() {
        if (null == mp) {
            mp = new MainPage(s, SWT.NONE);
        }
        mp.toTestsHomeView();

        stack.topControl = mp;
        s.layout();
    }

    public void resetToMainPage(UserTest ut) {
        if (null == mp) {
            mp = new MainPage(s, SWT.NONE);
        }
        mp.resetToTestsHomeView(ut);

        stack.topControl = mp;
        s.layout();
    }

    public void toTestPage(UserTest ut) {
        if (null == tp) {
            tp = new TestPage(s, SWT.NONE);
        }
        tp.resume(ut);

        stack.topControl = tp;
        s.layout();
    }

    /**************************************************
     * 
     * Listeners
     * 
     **************************************************/

    private class MainWindowListener implements ShellListener {

        @Override
        public void shellActivated(ShellEvent e) {
        }

        @Override
        public void shellClosed(ShellEvent e) {
            close();
            System.exit(0);
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

    /**************************************************
     * 
     * Getters and Setters
     * 
     **************************************************/

    public Display getDisplay() {
        return d;
    }

    public Shell getShell() {
        return s;
    }
}
