package com.mocktpo.modules.system.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.modules.editor.TestEditorPage;
import com.mocktpo.modules.report.TestReportPage;
import com.mocktpo.modules.system.MainPage;
import com.mocktpo.modules.test.TestPage;
import com.mocktpo.orm.domain.UserTestSession;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.MT;
import com.mocktpo.vo.TestEditorVo;
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

    private MyApplication app;

    /* Display and Shell */

    private Display d;
    private Shell s;

    /* Layouts */

    private StackLayout stack;

    /* Views */

    private MainPage mainPage;

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
        WindowUtils.center(s);
        s.addShellListener(new MainWindowAdapter());
    }

    private void initViews() {
        stack = new StackLayout();
        s.setLayout(stack);
        toMainPageAndToNewTestNavContent();
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
            d.asyncExec(s::dispose);
        }
    }

    /*
     * ==================================================
     *
     * View Controls
     *
     * ==================================================
     */

    public void toMainPageAndToNewTestNavContent() {
        mainPage = new MainPage(s, SWT.NONE);
        stack.topControl = mainPage;
        s.layout();
    }

    public void toMainPageAndToTestReportNavContent() {
        mainPage = new MainPage(s, SWT.NONE);
        mainPage.toTestReportView();
        stack.topControl = mainPage;
        s.layout();
    }

    public void toMainPageAndToTestEditorNavContent() {
        mainPage = new MainPage(s, SWT.NONE);
        mainPage.toTestEditorView();
        stack.topControl = mainPage;
        s.layout();
    }

    public void toTestPage(UserTestSession userTestSession) {
        final TestPage testPage = new TestPage(s, SWT.NONE, userTestSession);
        testPage.resume();
        stack.topControl = testPage;
        s.layout();
    }

    public void toTestReportPage(UserTestSession userTestSession) {
        stack.topControl = new TestReportPage(s, SWT.NONE, userTestSession);
        s.layout();
    }

    public void toTestEditorPage() {
        stack.topControl = new TestEditorPage(s, SWT.NONE);
        s.layout();
    }

    public void toTestEditorPage(TestEditorVo testVo) {
        stack.topControl = new TestEditorPage(s, SWT.NONE, testVo);
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

    public Shell getShell() {
        return s;
    }
}
