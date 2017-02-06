package com.mocktpo.widgets;

import com.mocktpo.util.layout.FormLayoutSet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class StarsComposite extends Composite {

    /* Properties */

    private int count;

    public StarsComposite(Composite parent, int style, int count) {
        super(parent, style);
        this.count = count;
        init();
    }

    private void init() {
        golbal();
        initBody();
    }

    private void golbal() {
        FormLayoutSet.layout(this);
    }

    private void initBody() {
        for (int i = 0; i < count; i++) {
            final Label starLabel = new Label(this, SWT.NONE);
        }
    }
}
