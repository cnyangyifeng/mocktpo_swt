package com.mocktpo.widget;

import com.mocktpo.util.*;
import com.mocktpo.util.constants.MT;
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

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Label copyright;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

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
        setBackground(ResourceManager.getColor(MT.COLOR_DARK_BLUE));
        GridLayoutSet.layout(this);
    }

    private void initCopyright() {
        copyright = new Label(this, SWT.WRAP | SWT.CENTER);
        GridDataSet.attach(copyright).centerBoth().withWidth(ScreenUtils.getViewPort(d).x);
        LabelSet.decorate(copyright).setForeground(MT.COLOR_WHITE_SMOKE).setFont(MT.FONT_X_SMALL).setText(msgs.getString("copyright"));
    }
}
