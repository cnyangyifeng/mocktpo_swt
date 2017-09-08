package com.mocktpo.modules.system.windows;

import com.mocktpo.MyApplication;
import com.mocktpo.util.ImageUtils;
import com.mocktpo.util.WindowUtils;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.widgets.CLabelSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import java.util.ResourceBundle;

public class SplashWindow {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Shell */

    private Display d;
    private Shell s;

    /* Widgets */

    private Label background;
    private CLabel message;

    /* Resources */

    private Color white;
    private Image b;
    private Image ico;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public SplashWindow() {
        this.d = MyApplication.get().getDisplay();
        init();
    }

    private void init() {
        s = new Shell(d, SWT.TOOL | SWT.ON_TOP);
        alloc();
        global();
        initBackground();
        initMessage();
        s.pack();
    }

    private void global() {
        s.setText(msgs.getString("app_name"));
        s.setImage(ico);
        s.setBackground(white);
        s.setBackgroundMode(SWT.INHERIT_FORCE);
        FormLayoutSet.layout(s).marginWidth(0).marginHeight(0).spacing(0);
    }

    private void initBackground() {
        background = new Label(s, SWT.NONE);
        FormDataSet.attach(background).atLeft().atTop().atRight();
        LabelSet.decorate(background).setImage(b);
    }

    private void initMessage() {
        message = new CLabel(s, SWT.CENTER);
        FormDataSet.attach(message).atLeft().atTopTo(background).atRight().atBottom(20);
        CLabelSet.decorate(message).setText(msgs.getString("launching"));
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

    public void close() {
        if (!d.isDisposed()) {
            d.asyncExec(s::dispose);
        }
    }

    public void setVisible(final boolean v) {
        if (!d.isDisposed()) {
            d.asyncExec(() -> {
                s.setVisible(v);
                if (v) {
                    s.setFocus();
                }
            });
        }
    }

    public void proceed(final String text) {
        if (!d.isDisposed()) {
            d.asyncExec(() -> message.setText(text));
        }
    }

    /*
     * ==================================================
     *
     * Native Resource Operations
     *
     * ==================================================
     */

    private void alloc() {
        white = new Color(d, 255, 255, 255);
        b = ImageUtils.load(d, "splash");
        ico = ImageUtils.load(d, "app_icon");
    }

    private void release() {
        white.dispose();
        b.dispose();
        ico.dispose();
    }
}
