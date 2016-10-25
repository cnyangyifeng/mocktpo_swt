package com.mocktpo.widget;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.util.GridDataSet;
import com.mocktpo.util.GridLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.LC;
import com.mocktpo.util.constants.MT;

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
        setBackground(ResourceManager.getColor(MT.COLOR_BLUE_PURPLE));
        GridLayoutSet.layout(this);
    }

    private void initCopyright() {
        copyright = new Label(this, SWT.WRAP);
        GridDataSet.attach(copyright).centerBoth().withWidth(LC.VIEW_PORT_WIDTH);
        copyright.setText(msgs.getString("copyright"));
        copyright.setFont(ResourceManager.getFont(MT.FONT_SMALL));
        copyright.setForeground(ResourceManager.getColor(MT.COLOR_LIGHTER_GRAY));
        copyright.setBackground(ResourceManager.getColor(MT.COLOR_BLUE_PURPLE));
    }
}
