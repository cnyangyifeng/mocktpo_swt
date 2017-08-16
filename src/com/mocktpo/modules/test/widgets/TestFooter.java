package com.mocktpo.modules.test.widgets;

import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class TestFooter extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    private Display d;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestFooter(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initCopyright();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_DARK_BLUE);
        GridLayoutSet.layout(this).marginWidth(0).marginHeight(0);
    }

    private void initCopyright() {
        final Label copyright = new Label(this, SWT.WRAP | SWT.CENTER);
        GridDataSet.attach(copyright).centerBoth().withWidth(ScreenUtils.getViewPort(d).x);
        LabelSet.decorate(copyright).setForeground(MT.COLOR_WHITE_SMOKE).setFont(MT.FONT_SMALL).setText(msgs.getString("copyright"));
    }
}
