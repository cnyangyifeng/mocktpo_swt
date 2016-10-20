package com.mocktpo.widgets;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;

import com.mocktpo.util.FormDataSet;
import com.mocktpo.util.FormLayoutSet;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.util.constants.MT;

public class CardView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Label header;
    private ProgressBar bar;

    /* Properties */

    private String title;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public CardView(Composite parent, int style, String title) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.title = title;
        init();
    }

    private void init() {
        golbal();
        initHeader();
        initProgressBar();
        initActionBar();
    }

    private void golbal() {
        setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        setCursor(ResourceManager.getCursor(MT.CURSOR_HAND));
        FormLayoutSet.layout(this).marginWidth(10).marginHeight(10);
    }

    private void initHeader() {
        header = new Label(this, SWT.NONE);
        FormDataSet.attach(header).atLeft().atTop().atRight().withHeight(40);
        header.setText(this.title);
        header.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
    }

    private void initProgressBar() {
        bar = new ProgressBar(this, SWT.SMOOTH);
        FormDataSet.attach(bar).atLeft().atTopTo(header).atRight();
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setSelection(20);
    }

    private void initActionBar() {
        final Composite ab = new Composite(this, SWT.NONE);
        FormDataSet.attach(ab).atLeft().atTopTo(bar).atRight().withHeight(50);
        ab.setBackground(ResourceManager.getColor(MT.COLOR_WHITE));
        FormLayoutSet.layout(ab).marginWidth(0).marginHeight(0).spacing(5);

        final Button cb = new Button(ab, SWT.PUSH);
        FormDataSet.attach(cb).atLeft().atTop(10).atBottom();
        cb.setText("Continue");

        final Button rb = new Button(ab, SWT.PUSH);
        FormDataSet.attach(rb).atLeftTo(cb).atTopTo(cb, 0, SWT.TOP).atBottom();
        rb.setText("Reset");
    }
}
