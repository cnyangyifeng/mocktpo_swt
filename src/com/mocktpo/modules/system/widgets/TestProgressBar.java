package com.mocktpo.modules.system.widgets;

import com.mocktpo.util.constants.MT;
import com.mocktpo.util.layout.FormDataSet;
import com.mocktpo.util.layout.FormLayoutSet;
import com.mocktpo.util.layout.RowDataSet;
import com.mocktpo.util.layout.RowLayoutSet;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.widgets.LabelSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.ResourceBundle;

public class TestProgressBar extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Properties */

    private int width;
    private int height;
    private int selection;

    public TestProgressBar(Composite parent, int style, int width, int height, int selection) {
        super(parent, style);
        this.width = width;
        this.height = height;
        this.selection = selection;
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        RowLayoutSet.layout(this).marginWidth(0).marginHeight(0);
    }

    private void initBody() {
        final Composite background = new Composite(this, SWT.NONE);
        RowDataSet.attach(background).withWidth(this.width).withHeight(this.height);
        CompositeSet.decorate(background).setBackground(MT.COLOR_WHITE_SMOKE);
        FormLayoutSet.layout(background).marginWidth(0).marginHeight(0).spacing(0);

        int selectionWidth = this.width * this.selection / 100;
        final Label selectionLabel = new Label(background, SWT.NONE);
        FormDataSet.attach(selectionLabel).atLeft().atTop().atBottom().withWidth(selectionWidth);
        LabelSet.decorate(selectionLabel).setBackground(MT.COLOR_GRAY20);
    }
}
