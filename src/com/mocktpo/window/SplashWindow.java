package com.mocktpo.window;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import com.mocktpo.MyApplication;
import com.mocktpo.util.ImageUtils;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.constants.RC;

public class SplashWindow {

    /* Constants */

    private static final int SHELL_WIDTH = 480;
    private static final int SHELL_HEIGHT = 290;
    private static final int BACKGROUND_WIDTH = 480;
    private static final int BACKGROUND_HEIGHT = 270;
    private static final int PROGRESS_BAR_WIDTH = 460;
    private static final int PROGRESS_BAR_HEIGHT = 10;

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Application and Display */

    protected MyApplication app;
    protected Display d;

    /* Shell */

    private Shell s;

    /* Widgets */

    private ProgressBar bar;

    /* Resources */

    private Color white;
    private Image b;
    private Image ico;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public SplashWindow(MyApplication app) {
        this.app = app;
        this.d = app.getDisplay();
        init();
    }

    private void init() {
        s = new Shell(d, SWT.TOOL | SWT.ON_TOP);
        alloc();
        global();
        initBackground();
        initProgressBar();
    }

    private void global() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ico);
        s.setSize(SHELL_WIDTH, SHELL_HEIGHT);
        s.setBackground(white);
    }

    private void initBackground() {
        final Label label = new Label(s, SWT.NONE);
        label.setBounds(0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        label.setImage(b);
    }

    private void initProgressBar() {
        bar = new ProgressBar(s, SWT.SMOOTH);
        bar.setBounds(10, BACKGROUND_HEIGHT, PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setSelection(0);
    }

    public void openAndWaitForDisposal() {
        WindowUtils.center(s);
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

    public void setVisible(boolean v) {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    s.setVisible(v);
                    if (v) {
                        s.setFocus();
                    }
                }
            });
        }
    }

    public void proceed(int status) {
        if (!d.isDisposed()) {
            d.asyncExec(new Runnable() {
                @Override
                public void run() {
                    bar.setSelection(status);
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
        white = new Color(d, 255, 255, 255);
        b = ImageUtils.load(d, RC.SPLASH_IMAGE_FILE);
        ico = ImageUtils.load(d, RC.APP_ICON_IMAGE_FILE);
    }

    private void release() {
        white.dispose();
        b.dispose();
        ico.dispose();
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
