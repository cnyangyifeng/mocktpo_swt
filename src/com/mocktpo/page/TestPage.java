package com.mocktpo.page;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import com.mocktpo.orm.domain.UserTest;
import com.mocktpo.util.TestViewManager;
import com.mocktpo.view.TestView;

public class TestPage extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Stack */

    protected StackLayout stack;

    /* Properties */

    protected UserTest ut;

    /**************************************************
     * 
     * Constructors
     * 
     **************************************************/

    public TestPage(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        stack = new StackLayout();
        this.setLayout(stack);
    }

    /**************************************************
     * 
     * Resume
     * 
     **************************************************/

    public void resume(UserTest ut) {
        setUserTest(ut);
        TestView tv = TestViewManager.getTestView(this);

        stack.topControl = tv;
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

    public void setUserTest(UserTest ut) {
        this.ut = ut;
    }
}
