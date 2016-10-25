package com.mocktpo.page;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.view.GeneralTestInfoView;

public class TestPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Stack */

    protected StackLayout stack;

    /* Widgets */

    protected GeneralTestInfoView gtiv;

    /* Properties */

    protected UserTest ut;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public TestPage(Composite parent, int style, UserTest ut) {
        super(parent, style);
        this.d = parent.getDisplay();
        this.ut = ut;
        init();
    }

    private void init() {
        golbal();
        initViews();
    }

    private void golbal() {
    }

    private void initViews() {
        stack = new StackLayout();
        this.setLayout(stack);

        // TODO Return the first view
        gtiv = new GeneralTestInfoView(this, SWT.NONE);

        stack.topControl = gtiv;
        this.layout();
    }

    public void updateViews(UserTest ut) {
        this.ut = ut;

        // TODO Return last visited view
        gtiv = new GeneralTestInfoView(this, SWT.NONE);

        stack.topControl = gtiv;
        this.layout();
    }

    /**************************************************
     * 
     * Getters and Setters
     * 
     **************************************************/

    public UserTest getUserTest() {
        return ut;
    }
}
