package com.mocktpo.modules.paper.views;

import com.mocktpo.util.layout.FormLayoutSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.ResourceBundle;

public abstract class TestEditorView extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display */

    protected Display d;

    /* Paper View */

    protected TestPaperView paperView;

    /*
     * ==================================================
     *
     * Constructors
     *
     * ==================================================
     */

    public TestEditorView(SashTestPaperView paperView, int style) {
        super(paperView.getRight(), style);
        this.d = paperView.getDisplay();
        this.paperView = paperView;
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this).marginWidth(0).marginHeight(0).spacing(0);
    }

    /*
     * ==================================================
     *
     * Body Initialization
     *
     * ==================================================
     */

    protected abstract void initBody();
}
