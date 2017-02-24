package com.mocktpo.widgets;

import com.mocktpo.events.BorderedCompositePaintListener;
import com.mocktpo.util.widgets.CompositeSet;
import com.mocktpo.util.layout.GridDataSet;
import com.mocktpo.util.layout.GridLayoutSet;
import com.mocktpo.util.widgets.ScaleSet;
import com.mocktpo.util.constants.MT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;

import java.util.ResourceBundle;

public class VolumeControl extends Composite {

    /* Logger and Messages */

    protected static final Logger logger = LogManager.getLogger();
    protected static final ResourceBundle msgs = ResourceBundle.getBundle("config.msgs");

    /* Display and Application */

    protected Display d;

    /* Widgets */

    private Scale s;

    public VolumeControl(Composite parent, int style) {
        super(parent, style);
        this.d = parent.getDisplay();
        init();
    }

    private void init() {
        golbal();
    }

    private void golbal() {
        CompositeSet.decorate(this).setBackground(MT.COLOR_DARK_BLUE);
        GridLayoutSet.layout(this);
        addPaintListener(new BorderedCompositePaintListener(MT.COLOR_GRAY40));

        s = new Scale(this, SWT.BORDER);
        ScaleSet.decorate(s).setIncrement(1).setMaximum(10).setMinimum(0).setPageIncrement(1).setSelection(10);
        GridDataSet.attach(s).fillBoth();
    }

    public void setSelection(int i) {
        s.setSelection(i);
    }

    public void addSelectionListener(SelectionListener listener) {
        s.addSelectionListener(listener);
    }
}
