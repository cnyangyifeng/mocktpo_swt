package com.mocktpo.widgets;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;

public class TestsHomeView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Composite tb;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public TestsHomeView(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
        initToolBar();
    }

    private void golbal() {
        setBackground(ResourceManager.getColor(MT.COLOR_LIGHT_GRAY));
        FormLayoutSet.layout(this);
    }

    private void initToolBar() {
        tb = new Composite(this, SWT.NONE);
        FormDataSet.attach(tb).atLeft().atTop().atRight().withHeight(50);
        tb.setBackground(ResourceManager.getColor(MT.COLOR_LIGHTER_GRAY));
        FormLayoutSet.layout(tb).marginWidth(10).marginHeight(5).spacing(5);

        final Label divider = new Label(this, SWT.NONE);
        FormDataSet.attach(divider).atLeft().atTopTo(tb).atRight().withHeight(1);
        divider.setBackground(ResourceManager.getColor(MT.COLOR_GRAY));

        final Button sb = new Button(tb, SWT.PUSH);
        FormDataSet.attach(sb).atLeft().atTop().atBottom();
        sb.setText(msgs.getString("sync"));
        sb.setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
    }
}
